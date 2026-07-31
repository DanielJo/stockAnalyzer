package io.github.danieljo.stockanalyzer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageDailyBar;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageTimeSeriesResponse;
import io.github.danieljo.stockanalyzer.model.Stock;

class AlphaVantageImportServiceTest {

	private final AlphaVantageImportService service = new AlphaVantageImportService();

	@Test
	void toStocks_mapsDateSymbolAndDailyInterval() {
		AlphaVantageDailyBar bar = new AlphaVantageDailyBar(LocalDate.of(2024, 1, 15), 100.0, 101.0, 99.0, 100.5, null, 900L);
		AlphaVantageTimeSeriesResponse response = new AlphaVantageTimeSeriesResponse("ibm", List.of(bar));

		List<Stock> stocks = service.toStocks(response);

		assertThat(stocks).hasSize(1);
		Stock stock = stocks.get(0);
		assertThat(stock.getSymbol()).isEqualTo("ibm");
		assertThat(stock.getDateTime()).isEqualTo("2024-01-15 00:00:00");
		assertThat(stock.getIntervall()).isEqualTo(1440);
		assertThat(stock.getClose()).isEqualTo(100.5);
		assertThat(stock.getVolume()).isEqualTo(900);
		assertThat(stock.getAdjustedClose()).isNull();
	}

	@Test
	void toStocks_carriesAdjustedCloseWhenPresent() {
		AlphaVantageDailyBar bar = new AlphaVantageDailyBar(LocalDate.of(2024, 1, 15), 100.0, 101.0, 99.0, 100.5, 100.4, 900L);
		AlphaVantageTimeSeriesResponse response = new AlphaVantageTimeSeriesResponse("ibm", List.of(bar));

		Stock stock = service.toStocks(response).get(0);

		assertThat(stock.getAdjustedClose()).isEqualTo(100.4);
	}
}
