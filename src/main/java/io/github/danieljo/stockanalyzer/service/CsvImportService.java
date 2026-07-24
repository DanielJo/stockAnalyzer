package io.github.danieljo.stockanalyzer.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.model.Stock;

/**
 * Replaces ParseCsv.java - now the only bar data source (the DB path is gone). Parsing itself
 * ({@link #importFromStream}) takes a plain {@link InputStream} rather than a file path, so a
 * future REST endpoint can feed it a {@code MultipartFile}'s stream (or any other source)
 * without touching this class - only the CLI-specific {@link #importFromFile} wrapper needs to
 * know about the filesystem.
 * <p>
 * {@link #stockList} intentionally stays a static, shared list of bars for now (same as the
 * original {@code ParseCsv.stockList}) since {@code TALibCalculationService}, the indicator
 * classes and {@code CsvExportService} all read it directly. Fully removing this static hand-off
 * is deferred until the app needs to run more than one analysis per JVM (GUI/web phase).
 */
public class CsvImportService {

	private static final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	private static final DateTimeFormatter STORED_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static List<Stock> stockList = new ArrayList<>();

	/** CLI convenience: resolves a file path to a stream, then delegates to {@link #importFromStream}. */
	public static List<Stock> importFromFile(String fileUrl, AnalysisRequest request) throws IOException {
		try (InputStream in = new FileInputStream(fileUrl)) {
			return importFromStream(in, request);
		}
	}

	/**
	 * Core parsing logic, source-agnostic. Closes {@code in} when done.
	 * Expected format per line: {@code <ignored>;<dateTime>;<open>;<high>;<low>;<close>;<volume>},
	 * first line treated as a header and skipped.
	 */
	public static List<Stock> importFromStream(InputStream in, AnalysisRequest request) throws IOException {
		List<Stock> imported = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				imported.add(parseLine(line, request));
			}
		}
		stockList = imported;
		return imported;
	}

	private static Stock parseLine(String line, AnalysisRequest request) {
		String[] tokens = line.split(";");
		String dateTime = tokens[1];
		String open = tokens[2];
		String high = tokens[3];
		String low = tokens[4];
		String close = tokens[5];
		String volume = tokens[6];

		String formattedTime = null;
		try {
			formattedTime = LocalDateTime.parse(dateTime, INPUT_DATE_FORMAT).format(STORED_DATE_FORMAT);
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}

		return new Stock(formattedTime, request.getSymbol(), request.getInterval(), open, high, low, close, volume);
	}
}
