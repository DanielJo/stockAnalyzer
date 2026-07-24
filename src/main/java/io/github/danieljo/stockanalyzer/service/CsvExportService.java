package io.github.danieljo.stockanalyzer.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;
import io.github.danieljo.stockanalyzer.indicator.TALibCalculationService;

/**
 * Replaces WriteCsv.java. The indicator-label list and file-name suffix now come from the
 * {@link AnalysisRequest} passed in instead of static fields on this class.
 */
public class CsvExportService {

	private CsvExportService() {
	}

	public static void writeTest(AnalysisRequest request) throws IOException {
		String delimiter = request.getDelimiter();
		StringBuilder header = new StringBuilder();
		header.append("Symbol").append(delimiter).append("Interval").append(delimiter).append("Timestamp")
				.append(delimiter).append("Open").append(delimiter).append("High").append(delimiter).append("Low")
				.append(delimiter).append("Close").append(delimiter).append("Volume").append("\n");

		System.out.print("Writing csv");

		File file = new File(request.getSymbol() + "_" + request.getInterval() + "min_test.csv");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
			bw.write(header.toString());
			StringBuilder b = new StringBuilder();
			for (int i = 0; i < CsvImportService.stockList.size(); i++) {
				StringBuilder builder = new StringBuilder();
				builder.append(CsvImportService.stockList.get(i).getSymbol()).append(delimiter)
						.append(CsvImportService.stockList.get(i).getIntervall()).append(delimiter)
						.append(CsvImportService.stockList.get(i).getDateTime()).append(delimiter)
						.append(appendTo2(b, CsvImportService.stockList.get(i).getOpen())).append(delimiter)
						.append(appendTo2(b, CsvImportService.stockList.get(i).getHigh())).append(delimiter)
						.append(appendTo2(b, CsvImportService.stockList.get(i).getLow())).append(delimiter)
						.append(appendTo2(b, CsvImportService.stockList.get(i).getClose())).append(delimiter)
						.append(CsvImportService.stockList.get(i).getVolume()).append("\n");
				bw.write(builder.toString());
			}
		}
		System.out.println("\nWriting csv successful!");
	}

	public static String appendTo2(StringBuilder builder, double d) {
		builder.setLength(0);
		if (d < 0) {
			builder.append('-');
			d = -d;
		}
		long scaled = (long) (d * 1e6 + 0.5);
		long factor = 1000000;
		int scale = 3;
		while (factor * 10 <= scaled) {
			factor *= 10;
			scale++;
		}
		while (scale > 0) {
			if (scale == 2) {
				builder.append(',');
			}
			long c = scaled / factor % 10;
			factor /= 10;
			builder.append((char) ('0' + c));
			scale--;
		}
		return builder.toString();
	}

	/**
	 * Builds the full CSV content as a string - source/destination-agnostic, same reasoning as
	 * {@code CsvImportService.importFromStream}: the CLI writes this to a file
	 * ({@link #writeCsv}), a REST endpoint can return it directly in a response body instead.
	 */
	public static String buildCsv(List<List<Double>> resultSet, AnalysisRequest request) {
		String delimiter = request.getDelimiter();
		StringBuilder csv = new StringBuilder();
		StringBuilder b = new StringBuilder();
		csv.append(buildHeader(request));
		for (int i = 0; i < CsvImportService.stockList.size(); i++) {
			csv.append(CsvImportService.stockList.get(i).getSymbol()).append(delimiter)
					.append(CsvImportService.stockList.get(i).getIntervall()).append(delimiter)
					.append(CsvImportService.stockList.get(i).getDateTime()).append(delimiter)
					.append(appendTo2(b, CsvImportService.stockList.get(i).getOpen())).append(delimiter)
					.append(appendTo2(b, CsvImportService.stockList.get(i).getHigh())).append(delimiter)
					.append(appendTo2(b, CsvImportService.stockList.get(i).getLow())).append(delimiter)
					.append(appendTo2(b, CsvImportService.stockList.get(i).getClose())).append(delimiter)
					.append(CsvImportService.stockList.get(i).getVolume());

			for (int j = 0; j < TALibCalculationService.getIndCount(); j++) {
				if (i >= resultSet.get(j).size()) {
					csv.append(delimiter).append("0.");
				} else {
					csv.append(delimiter).append(appendTo2(b, resultSet.get(j).get(i)));
				}
			}
			csv.append("\n");
		}
		return csv.toString();
	}

	public static void writeCsv(List<List<Double>> resultSet, AnalysisRequest request) throws IOException {
		System.out.println("Writing csv");
		String csv = buildCsv(resultSet, request);
		File file = new File(request.getSymbol() + "_" + request.getInterval() + request.getFileNameAdd() + "min.csv");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
			bw.write(csv);
		}
		System.out.println("Writing csv successful!");
	}

	private static String buildHeader(AnalysisRequest request) {
		String delimiter = request.getDelimiter();
		StringBuilder builder = new StringBuilder();
		builder.append("Symbol").append(delimiter).append("Interval").append(delimiter).append("Timestamp")
				.append(delimiter).append("Open").append(delimiter).append("High").append(delimiter).append("Low")
				.append(delimiter).append("Close").append(delimiter).append("Volume");

		for (String indicator : request.getIndicators()) {
			if (indicator.equalsIgnoreCase("pivot")) {
				builder.append(delimiter).append("PP_PP").append(delimiter).append("PP_S1").append(delimiter)
						.append("PP_R1").append(delimiter).append("PP_S2").append(delimiter).append("PP_R2")
						.append(delimiter).append("PP_S3").append(delimiter).append("PP_R3");
			} else {
				builder.append(delimiter).append(indicator);
			}
		}
		builder.append("\n");
		return builder.toString();
	}
}
