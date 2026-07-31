package io.github.danieljo.stockanalyzer.alphavantage;

import java.time.LocalDate;

/**
 * One parsed bar from either endpoint. {@code adjustedClose} is only present for
 * {@link AlphaVantageFunction#TIME_SERIES_DAILY_ADJUSTED} and {@code null} otherwise.
 */
public record AlphaVantageDailyBar(LocalDate date, double open, double high, double low, double close,
		Double adjustedClose, long volume) {
}
