package io.github.danieljo.stockanalyzer.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageDailyBar;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageTimeSeriesResponse;
import io.github.danieljo.stockanalyzer.model.Stock;

/**
 * Maps an Alpha Vantage response into the same {@link Stock} model {@link CsvImportService}
 * produces, so both data sources feed the rest of the pipeline identically. Alpha Vantage's daily
 * bars carry no time-of-day, so each bar is stamped at midnight with a fixed 1440-minute (one
 * day) interval - the existing interval field's unit is minutes.
 */
@Service
public class AlphaVantageImportService {

	private static final DateTimeFormatter STORED_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final int DAILY_INTERVAL_MINUTES = 1440;

	public List<Stock> toStocks(AlphaVantageTimeSeriesResponse response) {
		return response.bars().stream()
				.map(bar -> toStock(response.symbol(), bar))
				.collect(Collectors.toList());
	}

	private Stock toStock(String symbol, AlphaVantageDailyBar bar) {
		String dateTime = bar.date().atStartOfDay().format(STORED_DATE_FORMAT);
		Stock stock = new Stock(dateTime, symbol, DAILY_INTERVAL_MINUTES, bar.open(), bar.high(), bar.low(),
				bar.close(), Math.toIntExact(bar.volume()));
		stock.setAdjustedClose(bar.adjustedClose());
		return stock;
	}
}
