package io.github.danieljo.stockanalyzer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.job.AnalysisJob;
import io.github.danieljo.stockanalyzer.job.JobStatus;

/**
 * Exercises the real async job pipeline end-to-end (no mocks) - the automated version of the
 * manual curl smoke test used while building the REST API: submit real CSV bytes, wait for the
 * background executor to actually run the calculation, then check the produced CSV.
 */
class AnalysisJobServiceTest {

	private static final String SAMPLE_CSV = "Idx;DateTime;Open;High;Low;Close;Volume\n"
			+ "0;15.01.2024 08:00:00;80.00;81.20;79.20;79.40;1000\n"
			+ "1;15.01.2024 08:30:00;79.40;80.60;78.60;79.10;1013\n"
			+ "2;15.01.2024 09:00:00;79.10;80.30;78.30;79.10;1026\n"
			+ "3;15.01.2024 09:30:00;79.10;80.30;78.30;79.40;1039\n"
			+ "4;15.01.2024 10:00:00;79.40;80.60;78.60;80.00;1052\n";

	private final AnalysisJobService service = new AnalysisJobService(new MarketDataService());

	@Test
	void submit_runsAsynchronously_andProducesCsv() {
		AnalysisRequest request = new AnalysisRequest();
		request.setSymbol("bmw");
		request.setInterval(30);

		AnalysisJob job = service.submit(csvBytes(), request, List.of(1));
		assertThat(job.getStatus()).isIn(JobStatus.PENDING, JobStatus.RUNNING, JobStatus.DONE);

		AnalysisJob completed = awaitTerminal(job.getId());

		assertThat(completed.getStatus()).isEqualTo(JobStatus.DONE);
		assertThat(completed.getResult()).startsWith("Symbol;Interval;Timestamp;Open;High;Low;Close;Volume;SMA_20");
		assertThat(completed.getResult()).contains("bmw;30;2024-01-15 08:00:00");
	}

	@Test
	void submit_unknownIndicatorId_marksJobFailedInsteadOfCrashing() {
		AnalysisRequest request = new AnalysisRequest();
		request.setSymbol("bmw");
		request.setInterval(30);

		AnalysisJob job = service.submit(csvBytes(), request, List.of(9999));

		AnalysisJob completed = awaitTerminal(job.getId());

		assertThat(completed.getStatus()).isEqualTo(JobStatus.FAILED);
		assertThat(completed.getErrorMessage()).contains("9999");
	}

	@Test
	void get_unknownId_returnsEmpty() {
		assertThat(service.get("does-not-exist")).isEmpty();
	}

	@Test
	void twoJobsSubmittedBackToBack_doNotCrossContaminateResults() {
		AnalysisRequest bmwRequest = new AnalysisRequest();
		bmwRequest.setSymbol("bmw");
		bmwRequest.setInterval(30);
		AnalysisRequest siemensRequest = new AnalysisRequest();
		siemensRequest.setSymbol("siemens");
		siemensRequest.setInterval(30);

		// SMA only for job 1, RSI only for job 2 - if TALibCalculationService's static
		// resultSet/indCount weren't reset between runs, job 2's result would still contain
		// job 1's SMA column too.
		AnalysisJob job1 = service.submit(csvBytes(), bmwRequest, List.of(1));
		AnalysisJob job2 = service.submit(csvBytes(), siemensRequest, List.of(2));

		AnalysisJob completed1 = awaitTerminal(job1.getId());
		AnalysisJob completed2 = awaitTerminal(job2.getId());

		assertThat(completed1.getResult()).contains("SMA_20").doesNotContain("RSI");
		assertThat(completed2.getResult()).contains("RSI_14_3").doesNotContain("SMA");
	}

	private static byte[] csvBytes() {
		return SAMPLE_CSV.getBytes(StandardCharsets.UTF_8);
	}

	private AnalysisJob awaitTerminal(String id) {
		Instant deadline = Instant.now().plusSeconds(5);
		while (Instant.now().isBefore(deadline)) {
			AnalysisJob job = service.get(id).orElseThrow();
			if (job.getStatus() == JobStatus.DONE || job.getStatus() == JobStatus.FAILED) {
				return job;
			}
			sleep();
		}
		throw new AssertionError("Job " + id + " did not reach a terminal state within 5s");
	}

	private static void sleep() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException(e);
		}
	}
}
