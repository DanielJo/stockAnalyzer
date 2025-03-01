package analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WriteCsv {
	static DecimalFormat f = new DecimalFormat("#0.00");
	public static List<String> indicators = new ArrayList<>();
	private static String fileNameAdd = "";
	
	
	public static void WriteTest() throws IOException{
		String delimiter = StockAnalyzer.getDelimiter();
		StringBuilder builder1 = new StringBuilder();
		builder1.append("Symbol").append(delimiter).append("Interval")
				.append(delimiter).append("Timestamp").append(delimiter)
				.append("Open").append(delimiter).append("High")
				.append(delimiter).append("Low").append(delimiter)
				.append("Close").append(delimiter).append("Volume").append("\n");
		
		System.out.print("Writing csv");

		File file = new File(StockAnalyzer.getSymbol() + "_"
				+ StockAnalyzer.getInterval() + "min_test.csv");
		FileWriter fw = new FileWriter(file, false);

		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(builder1.toString());
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < ParseCsv.stockList.size(); i++){
			StringBuilder builder = new StringBuilder();
			builder.append(ParseCsv.stockList.get(i).getSymbol())
					.append(delimiter)
					.append(ParseCsv.stockList.get(i).getIntervall())
					.append(delimiter)
					.append(ParseCsv.stockList.get(i).getDateTime())
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getOpen()))
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getHigh()))
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getLow()))
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getClose()))
					.append(delimiter)
					.append(ParseCsv.stockList.get(i).getVolume()).append("\n");
			bw.write(builder.toString());
		}
		bw.flush();
		bw.close();
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
	        if (scale == 2)
	            builder.append(',');
	        long c = scaled / factor % 10;
	        factor /= 10;
	        builder.append((char) ('0' + c));
	        scale--;
	    }
	    return builder.toString();
	}
	
	public static void writeCsv(List<List<Double>> resultSet)
			throws IOException {
		System.out.print("Writing csv");
		String delimiter = StockAnalyzer.getDelimiter();

		File file = new File(StockAnalyzer.getSymbol() + "_"
				+ StockAnalyzer.getInterval() + fileNameAdd + "min.csv");
		FileWriter fw = new FileWriter(file, false);

		BufferedWriter bw = new BufferedWriter(fw);
		StringBuilder b = new StringBuilder();
		bw.write(buildHeader());
		for (int i = 0; i < ParseCsv.stockList.size(); i++) {
			if (i % 10000 == 0) {
				System.out.print(".");
			}
			StringBuilder builder = new StringBuilder();
			builder.append(ParseCsv.stockList.get(i).getSymbol())
					.append(delimiter)
					.append(ParseCsv.stockList.get(i).getIntervall())
					.append(delimiter)
					.append(ParseCsv.stockList.get(i).getDateTime())
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getOpen()))
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getHigh()))
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getLow()))
					.append(delimiter)
					.append(appendTo2(b, ParseCsv.stockList.get(i).getClose()))
					.append(delimiter)
					.append(ParseCsv.stockList.get(i).getVolume());

			for (int j = 0; j < TALibCalls.getIndCount(); j++) {
				if (i >= resultSet.get(j).size()) {
					builder.append(delimiter).append("0.");
					
				} else {
					builder.append(delimiter).append(appendTo2(b, resultSet.get(j).get(i)));
				}
			}
			builder.append("\n");

			bw.write(builder.toString());
		}
		bw.flush();
		bw.close();
		System.out.println("\nWriting csv successful!");
	}

	private static String buildHeader() {
		String delimiter = StockAnalyzer.getDelimiter();
		StringBuilder builder = new StringBuilder();
		builder.append("Symbol").append(delimiter).append("Interval")
				.append(delimiter).append("Timestamp").append(delimiter)
				.append("Open").append(delimiter).append("High")
				.append(delimiter).append("Low").append(delimiter)
				.append("Close").append(delimiter).append("Volume");

		for (int i = 0; i < indicators.size(); i++) {
			if (indicators.get(i).equalsIgnoreCase("pivot")) {
				builder.append(delimiter).append("PP_PP").append(delimiter)
						.append("PP_S1").append(delimiter).append("PP_R1")
						.append(delimiter).append("PP_S2").append(delimiter)
						.append("PP_R2").append(delimiter).append("PP_S3")
						.append(delimiter).append("PP_R3");
			} else {
				builder.append(delimiter).append(indicators.get(i));
			}
		}
		builder.append("\n");
		return builder.toString();
	}

	public static String getFileNameAdd() {
		return fileNameAdd;
	}

	public static void setFileNameAdd(String fileNameAdd) {
		WriteCsv.fileNameAdd = fileNameAdd;
	}
}
