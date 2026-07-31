package io.github.danieljo.stockanalyzer.alphavantage;

import java.util.List;

/** Bars are ordered chronologically ascending (Alpha Vantage itself returns them newest-first). */
public record AlphaVantageTimeSeriesResponse(String symbol, List<AlphaVantageDailyBar> bars) {
}
