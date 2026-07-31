package io.github.danieljo.stockanalyzer.rest;

import io.github.danieljo.stockanalyzer.indicator.IndicatorType;

public record IndicatorSummary(int id, String displayName) {

	public static IndicatorSummary from(IndicatorType type) {
		return new IndicatorSummary(type.getId(), type.getDisplayName());
	}
}
