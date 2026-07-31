package io.github.danieljo.stockanalyzer.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.job.AnalysisJob;
import io.github.danieljo.stockanalyzer.service.AnalysisJobService;

/**
 * Unit tests for the controller's own request handling/validation and status-code mapping, with
 * {@link AnalysisJobService} mocked out so every {@link io.github.danieljo.stockanalyzer.job.JobStatus}
 * branch can be tested deterministically, without depending on real (asynchronous) job timing.
 * The real end-to-end pipeline is covered separately by
 * {@link io.github.danieljo.stockanalyzer.service.AnalysisJobServiceTest}.
 */
@WebMvcTest(AnalysisController.class)
class AnalysisControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private AnalysisJobService analysisJobService;

	private static MockMultipartFile csvFile(byte[] content) {
		return new MockMultipartFile("file", "bars.csv", "text/csv", content);
	}

	@Test
	void submit_validRequest_returns202WithJobId() throws Exception {
		AnalysisJob job = new AnalysisJob();
		when(analysisJobService.submit(any(byte[].class), any(AnalysisRequest.class), anyList())).thenReturn(job);

		mockMvc.perform(multipart("/api/analyses")
						.file(csvFile("header\n1;2024-01-01;1;1;1;1;1\n".getBytes()))
						.param("symbol", "bmw")
						.param("interval", "30")
						.param("indicatorIds", "1", "2"))
				.andExpect(status().isAccepted())
				.andExpect(header().string("Location", "/api/analyses/" + job.getId()))
				.andExpect(jsonPath("$.id").value(job.getId()))
				.andExpect(jsonPath("$.status").value("PENDING"));
	}

	@Test
	void submit_emptyFile_returns400() throws Exception {
		mockMvc.perform(multipart("/api/analyses")
						.file(csvFile(new byte[0]))
						.param("symbol", "bmw")
						.param("interval", "30")
						.param("indicatorIds", "1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void submit_unknownIndicatorId_returns400WithoutCallingService() throws Exception {
		mockMvc.perform(multipart("/api/analyses")
						.file(csvFile("some content".getBytes()))
						.param("symbol", "bmw")
						.param("interval", "30")
						.param("indicatorIds", "1", "9999"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Unknown indicator id: 9999"));
	}

	@Test
	void getStatus_knownId_returnsJobState() throws Exception {
		AnalysisJob job = new AnalysisJob();
		job.markRunning();
		when(analysisJobService.get(job.getId())).thenReturn(java.util.Optional.of(job));

		mockMvc.perform(get("/api/analyses/" + job.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("RUNNING"));
	}

	@Test
	void getStatus_unknownId_returns404() throws Exception {
		when(analysisJobService.get("missing")).thenReturn(java.util.Optional.empty());

		mockMvc.perform(get("/api/analyses/missing")).andExpect(status().isNotFound());
	}

	@Test
	void getResult_jobDone_returnsCsv() throws Exception {
		AnalysisJob job = new AnalysisJob();
		job.complete("Symbol;Interval\nbmw;30\n");
		when(analysisJobService.get(job.getId())).thenReturn(java.util.Optional.of(job));

		mockMvc.perform(get("/api/analyses/" + job.getId() + "/result"))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "text/csv"))
				.andExpect(header().string("Content-Disposition", "attachment; filename=\"analysis.csv\""))
				.andExpect(content().string("Symbol;Interval\nbmw;30\n"));
	}

	@Test
	void getResult_jobStillRunning_returns409() throws Exception {
		AnalysisJob job = new AnalysisJob();
		job.markRunning();
		when(analysisJobService.get(job.getId())).thenReturn(java.util.Optional.of(job));

		mockMvc.perform(get("/api/analyses/" + job.getId() + "/result")).andExpect(status().isConflict());
	}

	@Test
	void getResult_unknownId_returns404() throws Exception {
		when(analysisJobService.get("missing")).thenReturn(java.util.Optional.empty());

		mockMvc.perform(get("/api/analyses/missing/result")).andExpect(status().isNotFound());
	}
}
