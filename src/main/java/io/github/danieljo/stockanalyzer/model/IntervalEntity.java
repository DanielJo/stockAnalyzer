package io.github.danieljo.stockanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Maps the {@code Intervals} table used by the original raw-JDBC queries in DBConnect.java.
 */
@Entity
@Table(name = "Intervals")
public class IntervalEntity {

	@Id
	@Column(name = "Id")
	private Long id;

	@Column(name = "MinutesCount")
	private Integer minutesCount;

	public Long getId() {
		return id;
	}

	public Integer getMinutesCount() {
		return minutesCount;
	}
}
