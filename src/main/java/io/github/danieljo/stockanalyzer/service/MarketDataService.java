package io.github.danieljo.stockanalyzer.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.model.Stock;

/**
 * Replaces StockDataService.java now that bars come from CSV instead of a database - no more
 * "does a precomputed interval already exist" check, since there's no DB to hold one; the CSV
 * file is assumed to already be at the requested interval (same assumption the original, dead
 * ParseCsv path made). The date-range and time-of-day filters that used to be pushed into the
 * SQL query are re-applied here in Java instead, against the imported list.
 * <p>
 * Deliberately takes a plain {@link InputStream} rather than a file path: the CLI resolves
 * {@code csv_<path>} to a stream and calls this the same way a future REST endpoint would with
 * a {@code MultipartFile}'s stream - this method doesn't need to change either way.
 */
@Service
public class MarketDataService {

	private static final DateTimeFormatter STORED_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public List<Stock> loadBars(InputStream csvInput, AnalysisRequest request) throws IOException {
		List<Stock> bars = CsvImportService.importFromStream(csvInput, request);

		if (request.isDateSet()) {
			bars = filterByDateRange(bars, request.getStartDate(), request.getEndDate());
		}
		if (request.isTimeSet()) {
			bars = filterByTimeOfDay(bars, request.getStartTime(), request.getEndTime());
		}

		CsvImportService.stockList = bars;
		return bars;
	}

	private List<Stock> filterByDateRange(List<Stock> bars, String startDate, String endDate) {
		LocalDateTime start = LocalDate.parse(startDate).atStartOfDay();
		LocalDateTime end = LocalDate.parse(endDate).atTime(LocalTime.MAX);
		return bars.stream()
				.filter(stock -> {
					LocalDateTime dt = LocalDateTime.parse(stock.getDateTime(), STORED_DATE_FORMAT);
					return !dt.isBefore(start) && !dt.isAfter(end);
				})
				.collect(Collectors.toList());
	}

	private List<Stock> filterByTimeOfDay(List<Stock> bars, String startTime, String endTime) {
		LocalTime start = LocalTime.parse(startTime);
		LocalTime end = LocalTime.parse(endTime);
		return bars.stream()
				.filter(stock -> {
					LocalTime barTime = LocalDateTime.parse(stock.getDateTime(), STORED_DATE_FORMAT).toLocalTime();
					return !barTime.isBefore(start) && !barTime.isAfter(end);
				})
				.collect(Collectors.toList());
	}
}
