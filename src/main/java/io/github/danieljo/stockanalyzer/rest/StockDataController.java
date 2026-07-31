package io.github.danieljo.stockanalyzer.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageClient;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageException;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageFunction;
import io.github.danieljo.stockanalyzer.alphavantage.AlphaVantageTimeSeriesResponse;
import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.model.Stock;
import io.github.danieljo.stockanalyzer.service.AlphaVantageImportService;
import io.github.danieljo.stockanalyzer.service.MarketDataService;

/**
 * Raw OHLCV bar lookup, one endpoint per data source (no global switch) - {@link #fromCsv} vs
 * {@link #fromAlphaVantage}. Unlike {@link AnalysisController}, this returns bars directly and
 * synchronously: no indicator calculation, no async job, just the parsed/fetched bars as JSON.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockDataController {

	private final MarketDataService marketDataService;
	private final AlphaVantageClient alphaVantageClient;
	private final AlphaVantageImportService alphaVantageImportService;

	public StockDataController(MarketDataService marketDataService, AlphaVantageClient alphaVantageClient,
			AlphaVantageImportService alphaVantageImportService) {
		this.marketDataService = marketDataService;
		this.alphaVantageClient = alphaVantageClient;
		this.alphaVantageImportService = alphaVantageImportService;
	}

	@PostMapping(value = "/csv/{symbol}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> fromCsv(
			@PathVariable String symbol,
			@RequestParam("file") MultipartFile file,
			@RequestParam int interval,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String delimiter) throws IOException {

		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("file must not be empty");
		}

		AnalysisRequest request = new AnalysisRequest();
		request.setSymbol(symbol);
		request.setInterval(interval);
		if (startTime != null && endTime != null) {
			request.setStartTime(startTime);
			request.setEndTime(endTime);
			request.setTimeSet(true);
		}
		if (startDate != null && endDate != null) {
			request.setStartDate(startDate);
			request.setEndDate(endDate);
			request.setDateSet(true);
		}
		if (delimiter != null && !delimiter.isBlank()) {
			request.setDelimiter(delimiter);
		}

		List<Stock> bars;
		try (InputStream in = file.getInputStream()) {
			bars = marketDataService.loadBars(in, request);
		}

		return ResponseEntity.ok(toResponse(bars));
	}

	@GetMapping("/alphavantage/{symbol}")
	public ResponseEntity<?> fromAlphaVantage(
			@PathVariable String symbol,
			@RequestParam(defaultValue = "TIME_SERIES_DAILY") AlphaVantageFunction function,
			@RequestParam(defaultValue = "compact") String outputsize) {

		AlphaVantageTimeSeriesResponse response;
		try {
			response = alphaVantageClient.fetchTimeSeries(function, symbol, outputsize);
		} catch (AlphaVantageException e) {
			HttpStatus status = e.getReason() == AlphaVantageException.Reason.RATE_LIMITED
					? HttpStatus.TOO_MANY_REQUESTS
					: HttpStatus.BAD_REQUEST;
			return ResponseEntity.status(status).body(e.getMessage());
		}

		List<Stock> bars = alphaVantageImportService.toStocks(response);
		return ResponseEntity.ok(toResponse(bars));
	}

	private List<StockBarResponse> toResponse(List<Stock> bars) {
		return bars.stream().map(StockBarResponse::from).collect(Collectors.toList());
	}
}
