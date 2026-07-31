package io.github.danieljo.stockanalyzer.rest;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.indicator.IndicatorType;
import io.github.danieljo.stockanalyzer.job.AnalysisJob;
import io.github.danieljo.stockanalyzer.job.JobStatus;
import io.github.danieljo.stockanalyzer.service.AnalysisJobService;

/**
 * REST equivalent of the CLI flow (name_/time_/csv_/indicators_ arguments). No auth/access
 * control yet - explicitly out of scope for this first step.
 * <p>
 * Analysis runs asynchronously (see {@link AnalysisJobService} for why: it's a single-threaded
 * queue, not true concurrency, because of static state shared by the underlying calculation
 * classes) - submit a job here, then poll {@link #getStatus} until it's done.
 */
@RestController
@RequestMapping("/api/analyses")
public class AnalysisController {

	private final AnalysisJobService analysisJobService;

	public AnalysisController(AnalysisJobService analysisJobService) {
		this.analysisJobService = analysisJobService;
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> submit(
			@RequestParam("file") MultipartFile file,
			@RequestParam String symbol,
			@RequestParam int interval,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String delimiter,
			@RequestParam List<Integer> indicatorIds) throws IOException {

		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("file must not be empty");
		}
		if (indicatorIds.isEmpty()) {
			return ResponseEntity.badRequest().body("indicatorIds must not be empty");
		}
		for (Integer id : indicatorIds) {
			try {
				IndicatorType.fromId(id);
			} catch (IllegalArgumentException e) {
				return ResponseEntity.badRequest().body("Unknown indicator id: " + id);
			}
		}

		AnalysisRequest request = new AnalysisRequest();
		request.setSymbol(symbol);
		request.setInterval(interval);
		if (startTime != null && endTime != null) {
			request.setStartTime(startTime);
			request.setEndTime(endTime);
			request.setTimeSet(true);
		}
		if (startDate != null && endDate != null) {
			request.setStartDate(startDate);
			request.setEndDate(endDate);
			request.setDateSet(true);
		}
		if (delimiter != null && !delimiter.isBlank()) {
			request.setDelimiter(delimiter);
		}

		// Read fully now - the job runs later, on a background thread, after this request (and
		// the temp file backing the multipart upload) is gone.
		AnalysisJob job = analysisJobService.submit(file.getBytes(), request, indicatorIds);

		return ResponseEntity.accepted()
				.location(URI.create("/api/analyses/" + job.getId()))
				.body(AnalysisJobResponse.from(job));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AnalysisJobResponse> getStatus(@PathVariable String id) {
		return analysisJobService.get(id)
				.map(job -> ResponseEntity.ok(AnalysisJobResponse.from(job)))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping(value = "/{id}/result")
	public ResponseEntity<String> getResult(@PathVariable String id) {
		AnalysisJob job = analysisJobService.get(id).orElse(null);
		if (job == null) {
			return ResponseEntity.notFound().build();
		}
		if (job.getStatus() != JobStatus.DONE) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Job is " + job.getStatus() + ", not ready yet");
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"analysis.csv\"")
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(job.getResult());
	}
}
