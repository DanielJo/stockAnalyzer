package io.github.danieljo.stockanalyzer.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.danieljo.stockanalyzer.indicator.IndicatorType;

/** Lets a client (e.g. the planned Angular UI) discover which indicators it can select. */
@RestController
@RequestMapping("/api/indicators")
public class IndicatorController {

	@GetMapping
	public List<IndicatorSummary> list() {
		return Arrays.stream(IndicatorType.values())
				.map(IndicatorSummary::from)
				.collect(Collectors.toList());
	}
}
