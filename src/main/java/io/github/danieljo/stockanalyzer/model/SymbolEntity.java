package io.github.danieljo.stockanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Maps the {@code Symbols} table used by the original raw-JDBC queries in DBConnect.java.
 * Column names (including the space in "Min Intervall") are taken as-is from that SQL;
 * not verified against a live schema.
 */
@Entity
@Table(name = "Symbols")
public class SymbolEntity {

	@Id
	@Column(name = "Id")
	private Long id;

	@Column(name = "Name")
	private String name;

	@Column(name = "`Min Intervall`")
	private Integer minIntervall;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getMinIntervall() {
		return minIntervall;
	}
}
