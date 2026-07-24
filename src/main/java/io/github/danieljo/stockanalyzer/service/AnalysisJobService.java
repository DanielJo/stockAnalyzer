package io.github.danieljo.stockanalyzer.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.indicator.IndicatorType;
import io.github.danieljo.stockanalyzer.indicator.TALibCalculationService;
import io.github.danieljo.stockanalyzer.job.AnalysisJob;
import io.github.danieljo.stockanalyzer.model.Stock;

/**
 * Runs analyses submitted via the REST API asynchronously, tracked in memory by job id.
 * <p>
 * Deliberately backed by a single-threaded executor: {@code TALibCalculationService} and
 * {@code CsvImportService} still hold their working state in static fields (a known,
 * previously-documented limitation of the CLI-era design). Running two jobs at once would let
 * them silently corrupt each other's results, so jobs are processed strictly one at a time for
 * now instead. Removing this constraint requires de-static-ing that shared state first - not
 * attempted here.
 * <p>
 * Jobs are kept in an unbounded in-memory map with no eviction - fine for a first step / low
 * traffic, but would need a TTL/cleanup (or a real store) before this runs unattended for long.
 */
@Service
public class AnalysisJobService {

	private final Map<String, AnalysisJob> jobs = new ConcurrentHashMap<>();
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private final MarketDataService marketDataService;

	public AnalysisJobService(MarketDataService marketDataService) {
		this.marketDataService = marketDataService;
	}

	/**
	 * {@code csvBytes} must already be fully read from the incoming request (e.g.
	 * {@code MultipartFile.getBytes()}) before calling this - the job runs later, on a
	 * background thread, after the HTTP request that submitted it has already completed.
	 */
	public AnalysisJob submit(byte[] csvBytes, AnalysisRequest request, List<Integer> indicatorIds) {
		AnalysisJob job = new AnalysisJob();
		jobs.put(job.getId(), job);
		executor.submit(() -> run(job, csvBytes, request, indicatorIds));
		return job;
	}

	public Optional<AnalysisJob> get(String id) {
		return Optional.ofNullable(jobs.get(id));
	}

	private void run(AnalysisJob job, byte[] csvBytes, AnalysisRequest request, List<Integer> indicatorIds) {
		job.markRunning();
		try (ByteArrayInputStream csvInput = new ByteArrayInputStream(csvBytes)) {
			List<Stock> bars = marketDataService.loadBars(csvInput, request);
			TALibCalculationService.initialize(bars);

			for (Integer id : indicatorIds) {
				IndicatorType.fromId(id).calculate(request);
			}

			String csv = CsvExportService.buildCsv(TALibCalculationService.getResultSet(), request);
			job.complete(csv);
		} catch (Exception e) {
			job.fail(e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
		}
	}

	@PreDestroy
	void shutdown() {
		executor.shutdownNow();
	}
}
