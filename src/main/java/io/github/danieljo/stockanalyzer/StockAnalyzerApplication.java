package io.github.danieljo.stockanalyzer;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.cli.ArgumentParser;
import io.github.danieljo.stockanalyzer.indicator.TALibCalculationService;
import io.github.danieljo.stockanalyzer.model.Stock;
import io.github.danieljo.stockanalyzer.service.CsvExportService;
import io.github.danieljo.stockanalyzer.service.StockDataService;

/**
 * Replaces StockAnalyzer.main. Same overall flow as the original: resolve name_/time_ first,
 * load the bars, build the TA-Lib input arrays, then process the remaining CLI options
 * (indicators are calculated as a side effect of {@link ArgumentParser#parseOptions}, same as
 * the original {@code parseCL}), then write the CSV.
 */
@SpringBootApplication
public class StockAnalyzerApplication implements CommandLineRunner {

	private final StockDataService stockDataService;

	public StockAnalyzerApplication(StockDataService stockDataService) {
		this.stockDataService = stockDataService;
	}

	public static void main(String[] args) {
		SpringApplication.run(StockAnalyzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		long start = System.currentTimeMillis();

		if (args.length == 0) {
			System.out.println("CommandLine Argumente fehlen!");
			return;
		}

		AnalysisRequest request;
		try {
			request = ArgumentParser.parseNameAndTime(args);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		List<Stock> bars = stockDataService.loadBars(request);
		buildIndicatorInput(bars);

		ArgumentParser.parseOptions(args, request);

		CsvExportService.writeCsv(TALibCalculationService.getResultSet(), request);

		for (int i = 0; i < request.getErrors().size(); i++) {
			System.out.println(i + " " + request.getErrors().get(i));
		}
		System.out.println("DecimalFormat Durchlauf Nr." + " :" + (System.currentTimeMillis() - start) + "ms");
	}

	private void buildIndicatorInput(List<Stock> bars) {
		double[] open = new double[bars.size()];
		double[] high = new double[bars.size()];
		double[] low = new double[bars.size()];
		double[] close = new double[bars.size()];
		double[] volume = new double[bars.size()];
		for (int i = 0; i < bars.size(); i++) {
			open[i] = bars.get(i).getOpen();
			high[i] = bars.get(i).getHigh();
			low[i] = bars.get(i).getLow();
			close[i] = bars.get(i).getClose();
			volume[i] = bars.get(i).getVolume();
		}
		// Constructor assigns into TALibCalculationService's static input arrays; the instance
		// itself is unused afterwards, same as the original StockAnalyzer.copyArray()/tlc field.
		new TALibCalculationService(open, close, high, low, volume);
	}
}
