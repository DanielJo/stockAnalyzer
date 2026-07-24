package io.github.danieljo.stockanalyzer.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.model.Stock;

/**
 * Replaces ParseCsv.java. Still the dead CSV-import path (never called from
 * StockAnalyzerApplication, same as in the original), kept for parity/future use.
 * <p>
 * {@link #stockList} intentionally stays a static, shared list of bars for now (same as the
 * original {@code ParseCsv.stockList}) since {@code TALibCalculationService}, the indicator
 * classes and {@code CsvExportService} all read it directly. Fully removing this static hand-off
 * is deferred until the app needs to run more than one analysis per JVM (GUI/web phase).
 */
public class CsvImportService {

	public static List<Stock> stockList = new ArrayList<>();

	public static void readFile(String fileUrl, AnalysisRequest request) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(fileUrl))) {
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				splitLine(line, request);
			}
		}
	}

	public static void splitLine(String line, AnalysisRequest request) throws IOException {
		String[] tokens = line.split(";");
		String dateTime = tokens[1];
		String open = tokens[2];
		String high = tokens[3];
		String low = tokens[4];
		String close = tokens[5];
		String volume = tokens[6];

		String oldFormat = "dd.MM.yyyy HH:mm:ss";
		String newFormat = "yyyy-MM-dd HH:mm:ss";
		String formattedTime = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
		SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
		try {
			formattedTime = sdf2.format(sdf1.parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Stock stock = new Stock(formattedTime, request.getSymbol(), request.getInterval(), open, high, low, close,
				volume);
		stockList.add(stock);
	}
}
