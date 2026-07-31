package io.github.danieljo.stockanalyzer.alphavantage;

/** The two Alpha Vantage endpoints this app integrates with; the enum name is the {@code function} query value. */
public enum AlphaVantageFunction {
	TIME_SERIES_DAILY,
	TIME_SERIES_DAILY_ADJUSTED
}
