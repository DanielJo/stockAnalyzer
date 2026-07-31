package io.github.danieljo.stockanalyzer.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageClient;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageException;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageFunction;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageTimeSeriesResponse;
import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.model.Stock;
import io.github.danieljo.stockanalyzer.service.AlphaVantageImportService;
import io.github.danieljo.stockanalyzer.service.MarketDataService;

@WebMvcTest(StockDataController.class)
class StockDataControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MarketDataService marketDataService;
	@MockitoBean
	private AlphaVantageClient alphaVantageClient;
	@MockitoBean
	private AlphaVantageImportService alphaVantageImportService;

	private static MockMultipartFile csvFile(byte[] content) {
		return new MockMultipartFile("file", "bars.csv", "text/csv", content);
	}

	private static Stock stock() {
		return new Stock("2024-01-15 08:00:00", "bmw", 30, 80.0, 81.2, 79.2, 79.4, 1000);
	}

	@Test
	void fromCsv_validRequest_returnsBars() throws Exception {
		when(marketDataService.loadBars(any(), any(AnalysisRequest.class))).thenReturn(List.of(stock()));

		mockMvc.perform(multipart("/api/stocks/csv/bmw")
						.file(csvFile("header\n1;2024-01-01;1;1;1;1;1\n".getBytes()))
						.param("interval", "30"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].dateTime").value("2024-01-15 08:00:00"))
				.andExpect(jsonPath("$[0].close").value(79.4))
				.andExpect(jsonPath("$[0].adjustedClose").doesNotExist());
	}

	@Test
	void fromCsv_emptyFile_returns400() throws Exception {
		mockMvc.perform(multipart("/api/stocks/csv/bmw")
						.file(csvFile(new byte[0]))
						.param("interval", "30"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void fromAlphaVantage_validRequest_returnsBars() throws Exception {
		AlphaVantageTimeSeriesResponse avResponse = new AlphaVantageTimeSeriesResponse("ibm", List.of());
		when(alphaVantageClient.fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY, "ibm", "compact")).thenReturn(avResponse);
		when(alphaVantageImportService.toStocks(avResponse)).thenReturn(List.of(stock()));

		mockMvc.perform(get("/api/stocks/alphavantage/ibm"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].close").value(79.4));

		verify(alphaVantageClient).fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY, "ibm", "compact");
	}

	@Test
	void fromAlphaVantage_passesFunctionAndOutputsizeThrough() throws Exception {
		when(alphaVantageClient.fetchTimeSeries(eq(AlphaVantageFunction.TIME_SERIES_DAILY_ADJUSTED), eq("ibm"), eq("full")))
				.thenReturn(new AlphaVantageTimeSeriesResponse("ibm", List.of()));
		when(alphaVantageImportService.toStocks(any())).thenReturn(List.of());

		mockMvc.perform(get("/api/stocks/alphavantage/ibm")
						.param("function", "TIME_SERIES_DAILY_ADJUSTED")
						.param("outputsize", "full"))
				.andExpect(status().isOk());

		verify(alphaVantageClient).fetchTimeSeries(AlphaVantageFunction.TIME_SERIES_DAILY_ADJUSTED, "ibm", "full");
	}

	@Test
	void fromAlphaVantage_invalidSymbol_returns400() throws Exception {
		when(alphaVantageClient.fetchTimeSeries(any(), eq("bogus"), any()))
				.thenThrow(new AlphaVantageException(AlphaVantageException.Reason.INVALID_REQUEST, "Invalid API call."));

		mockMvc.perform(get("/api/stocks/alphavantage/bogus"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void fromAlphaVantage_rateLimited_returns429() throws Exception {
		when(alphaVantageClient.fetchTimeSeries(any(), eq("ibm"), any()))
				.thenThrow(new AlphaVantageException(AlphaVantageException.Reason.RATE_LIMITED, "25 requests per day limit reached."));

		mockMvc.perform(get("/api/stocks/alphavantage/ibm"))
				.andExpect(status().isTooManyRequests());
	}
}
