package io.github.danieljo.stockanalyzer.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.danieljo.stockanalyzer.model.StockDataEntity;

public interface StockDataRepository extends JpaRepository<StockDataEntity, Long> {

	/**
	 * Replaces DBConnect.ReadDb's sql1/sql2: OHLCV bars for a symbol at a given interval,
	 * optionally bounded by a date range. Parameterized (the original built these as raw
	 * concatenated SQL strings). The time-of-day (HH:mm) window from the original sql2 is
	 * applied afterwards in Java (StockDataService) rather than via a native EXTRACT() call,
	 * since that isn't portable through JPQL.
	 */
	@Query("SELECT d FROM StockDataEntity d "
			+ "WHERE d.interval.minutesCount = :minutesCount "
			+ "AND d.symbol.name = :symbolName "
			+ "AND (:startDate IS NULL OR d.endtime >= :startDate) "
			+ "AND (:endDate IS NULL OR d.endtime <= :endDate) "
			+ "ORDER BY d.endtime")
	List<StockDataEntity> findBars(@Param("minutesCount") int minutesCount,
			@Param("symbolName") String symbolName,
			@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);
}
