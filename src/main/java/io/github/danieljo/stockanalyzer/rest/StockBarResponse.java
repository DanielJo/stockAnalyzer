package io.github.danieljo.stockanalyzer.rest;

import io.github.danieljo.stockanalyzer.model.Stock;

/** One OHLCV bar, source-agnostic. {@code adjustedClose} is only non-null for Alpha Vantage's adjusted endpoint. */
public record StockBarResponse(String dateTime, Double open, Double high, Double low, Double close, int volume,
		Double adjustedClose) {

	public static StockBarResponse from(Stock stock) {
		return new StockBarResponse(stock.getDateTime(), stock.getOpen(), stock.getHigh(), stock.getLow(),
				stock.getClose(), stock.getVolume(), stock.getAdjustedClose());
	}
}
