package io.github.danieljo.stockanalyzer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.model.Stock;
import io.github.danieljo.stockanalyzer.model.StockDataEntity;
import io.github.danieljo.stockanalyzer.repository.StockDataRepository;
import io.github.danieljo.stockanalyzer.repository.SymbolRepository;

/**
 * Replaces DBConnect.java plus the DB-orchestration half of the original
 * ParseCommandLine.setTimeName: check whether pre-aggregated data already exists for the
 * requested interval, otherwise fetch 1-minute bars and aggregate them locally. Loads through
 * Spring Data JPA with parameterized queries instead of the original's raw, string-concatenated
 * JDBC SQL (which also closes a SQL-injection hole the original had via the symbol/date values).
 */
@Service
public class StockDataService {

	private static final DateTimeFormatter ENDTIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	private final SymbolRepository symbolRepository;
	private final StockDataRepository stockDataRepository;

	public StockDataService(SymbolRepository symbolRepository, StockDataRepository stockDataRepository) {
		this.symbolRepository = symbolRepository;
		this.stockDataRepository = stockDataRepository;
	}

	public List<Stock> loadBars(AnalysisRequest request) {
		LocalDateTime startDate = parseDateBound(request.getStartDate(), false);
		LocalDateTime endDate = parseDateBound(request.getEndDate(), true);

		boolean intervalExists = symbolRepository
				.findByNameAndMinIntervall(request.getSymbol(), request.getInterval())
				.isPresent();

		List<Stock> bars;
		if (intervalExists) {
			bars = toStockList(stockDataRepository.findBars(request.getInterval(), request.getSymbol(), startDate, endDate));
		} else {
			List<Stock> oneMinuteBars = toStockList(stockDataRepository.findBars(1, request.getSymbol(), startDate, endDate));
			bars = AggregateService.startAggregate(oneMinuteBars, request.getInterval(), request.getSymbol());
		}

		if (request.isTimeSet()) {
			bars = filterByTimeOfDay(bars, request.getStartTime(), request.getEndTime());
		}

		CsvImportService.stockList = bars;
		return bars;
	}

	private List<Stock> toStockList(List<StockDataEntity> rows) {
		return rows.stream()
				.map(row -> new Stock(
						row.getEndtime().format(ENDTIME_FORMAT),
						row.getSymbol().getName(),
						row.getInterval().getMinutesCount(),
						row.getOpen(),
						row.getHigh(),
						row.getLow(),
						row.getClose(),
						row.getVolume()))
				.collect(Collectors.toList());
	}

	private List<Stock> filterByTimeOfDay(List<Stock> bars, String startTime, String endTime) {
		LocalTime start = LocalTime.parse(startTime);
		LocalTime end = LocalTime.parse(endTime);
		return bars.stream()
				.filter(stock -> {
					LocalTime barTime = LocalDateTime.parse(stock.getDateTime(), ENDTIME_FORMAT).toLocalTime();
					return !barTime.isBefore(start) && !barTime.isAfter(end);
				})
				.collect(Collectors.toList());
	}

	/**
	 * The original passed the date CLI values straight into a SQL string literal and let MySQL
	 * cast them; the exact expected format was never documented. Assumes ISO-8601 (yyyy-MM-dd).
	 */
	private LocalDateTime parseDateBound(String date, boolean endOfDay) {
		if (date == null) {
			return null;
		}
		LocalDate parsed = LocalDate.parse(date);
		return endOfDay ? parsed.atTime(LocalTime.MAX) : parsed.atStartOfDay();
	}
}
