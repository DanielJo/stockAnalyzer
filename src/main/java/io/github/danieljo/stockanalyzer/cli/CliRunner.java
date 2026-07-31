package io.github.danieljo.stockanalyzer.cli;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.github.danieljo.stockanalyzer.StockAnalyzerApplication;
import io.github.danieljo.stockanalyzer.indicator.TALibCalculationService;
import io.github.danieljo.stockanalyzer.model.Stock;
import io.github.danieljo.stockanalyzer.service.CsvExportService;
import io.github.danieljo.stockanalyzer.service.MarketDataService;

/**
 * Replaces StockAnalyzer.main. Same overall flow as before: resolve name_/time_ and the CSV
 * input path first, load the bars, build the TA-Lib input arrays, then process the remaining
 * CLI options (indicators are calculated as a side effect of {@link ArgumentParser#parseOptions},
 * same as the original {@code parseCL}), then write the CSV.
 * <p>
 * Bars now come from a CSV file (no more DB). Resolving {@code csv_<path>} into a stream is the
 * only filesystem-specific part of this - {@link MarketDataService#loadBars} itself just takes
 * an {@link InputStream}, so the REST endpoint hands it a {@code MultipartFile}'s stream instead
 * without needing changes to that method.
 * <p>
 * A no-op whenever the app is running as the REST server instead (see
 * {@link StockAnalyzerApplication#isCliInvocation}).
 */
@Component
public class CliRunner implements CommandLineRunner {

	private final MarketDataService marketDataService;

	public CliRunner(MarketDataService marketDataService) {
		this.marketDataService = marketDataService;
	}

	@Override
	public void run(String... args) throws Exception {
		long start = System.currentTimeMillis();

		if (!StockAnalyzerApplication.isCliInvocation(args)) {
			return;
		}

		AnalysisRequest request;
		String csvPath;
		try {
			request = ArgumentParser.parseNameAndTime(args);
			csvPath = ArgumentParser.parseCsvPath(args);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		List<Stock> bars;
		try (InputStream csvInput = new FileInputStream(csvPath)) {
			bars = marketDataService.loadBars(csvInput, request);
		}
		TALibCalculationService.initialize(bars);

		ArgumentParser.parseOptions(args, request);

		CsvExportService.writeCsv(TALibCalculationService.getResultSet(), request);

		for (int i = 0; i < request.getErrors().size(); i++) {
			System.out.println(i + " " + request.getErrors().get(i));
		}
		System.out.println("DecimalFormat Durchlauf Nr." + " :" + (System.currentTimeMillis() - start) + "ms");
	}
}
