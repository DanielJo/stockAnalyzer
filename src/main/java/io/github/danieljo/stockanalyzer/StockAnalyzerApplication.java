package io.github.danieljo.stockanalyzer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.cli.ArgumentParser;
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
 * Now that {@code spring-boot-starter-web} is on the classpath (for the REST API), Spring Boot
 * would otherwise try to start an embedded web server on every run, including plain one-shot CLI
 * invocations - which don't need a listening port and would fail if one isn't free. {@link #main}
 * disables the web server whenever any argument looks like one of our CLI tokens (i.e. doesn't
 * start with {@code --}, which is how Spring's own property-override args are always written) -
 * so `java -jar stock-analyzer.jar` alone (or with only `--server.port=...`-style args) still
 * starts the REST server, while `java -jar stock-analyzer.jar name_bmw time_30 ...` runs as a
 * plain CLI command with no port involved at all.
 */
@SpringBootApplication
public class StockAnalyzerApplication implements CommandLineRunner {

	private final MarketDataService marketDataService;

	public StockAnalyzerApplication(MarketDataService marketDataService) {
		this.marketDataService = marketDataService;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StockAnalyzerApplication.class);
		if (isCliInvocation(args)) {
			app.setWebApplicationType(WebApplicationType.NONE);
		}
		app.run(args);
	}

	private static boolean isCliInvocation(String[] args) {
		for (String arg : args) {
			if (!arg.startsWith("--")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run(String... args) throws Exception {
		long start = System.currentTimeMillis();

		if (!isCliInvocation(args)) {
			// Running as the REST server (see main()) - nothing to do here.
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
