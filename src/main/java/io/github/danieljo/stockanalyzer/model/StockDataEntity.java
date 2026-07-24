package io.github.danieljo.stockanalyzer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Maps the {@code Data} table (OHLCV bars) used by the original raw-JDBC queries in
 * DBConnect.java. Read-only: the whole application only ever SELECTs from this table.
 */
@Entity
@Table(name = "Data")
public class StockDataEntity {

	@Id
	@Column(name = "Id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "symbols")
	private SymbolEntity symbol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Intervals")
	private IntervalEntity interval;

	@Column(name = "Endtime")
	private LocalDateTime endtime;

	@Column(name = "Open")
	private Double open;

	@Column(name = "High")
	private Double high;

	@Column(name = "Low")
	private Double low;

	@Column(name = "Close")
	private Double close;

	@Column(name = "Volume")
	private Integer volume;

	public Long getId() {
		return id;
	}

	public SymbolEntity getSymbol() {
		return symbol;
	}

	public IntervalEntity getInterval() {
		return interval;
	}

	public LocalDateTime getEndtime() {
		return endtime;
	}

	public Double getOpen() {
		return open;
	}

	public Double getHigh() {
		return high;
	}

	public Double getLow() {
		return low;
	}

	public Double getClose() {
		return close;
	}

	public Integer getVolume() {
		return volume;
	}
}
