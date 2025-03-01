package analyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockAnalyzer {
	private static String startTime;
	private static String endTime;
	private static String startDate;
	private static String endDate;
	private static boolean DateSet = false;
	private static boolean TimeSet;
	private static boolean timeOpt = true;
	private static String symbol;
	private static int interval;
	public static double[] high;
	public static double[] low;
	public static double[] volume;
	public static double[] close;
	public static TALibCalls tlc;
	private static String delimiter = ";";
	private static final List<String> error = new ArrayList<>();
	private static double[] open;

	public static void main(String[] args) throws IOException {
		//System.out.println(DBConnect.sql2);
		
		//BenchmarkFormat.initBenchmark(10);
		long start = System.currentTimeMillis();
		TimeSet = true;
		if (args.length == 0){
			System.out.println("CommandLine Argumente fehlen!");
			System.exit(0);
		} else {
			boolean isSet = false;
			TimeSet = false;
			isSet = ParseCommandLine.setTimeName(args);
			if (isSet){
			ParseCommandLine.parseCL(args);
			}
		}
		//ParseCsv.ReadFile(FileURL + ".csv");
		WriteCsv.writeCsv(TALibCalls.getResultSet());
		for(int i = 0; i < error.size(); i++){
			System.out.println(i + " " + error.get(i));
		}
		System.out.println("DecimalFormat Durchlauf Nr." + " :" + (System.currentTimeMillis() - start) + "ms");
		
//		symbol = "bmw";
//		interval = 1;
//		delimiter = ";";
//		DBConnect.ReadDb();
//		Aggregate.startAggregate(ParseCsv.stockList, 5, "bmw");
//		WriteCsv.WriteTest();
	}
	
	public static void copyArray(){
		high = new double[ParseCsv.stockList.size()];
		low = new double[ParseCsv.stockList.size()];
		close = new double[ParseCsv.stockList.size()];
		volume = new double[ParseCsv.stockList.size()];
		open = new double[ParseCsv.stockList.size()];
		for(int i = 0; i < ParseCsv.stockList.size(); i++){
			//System.out.println(i);
			open[i] = ParseCsv.stockList.get(i).getOpen();
			high[i] = ParseCsv.stockList.get(i).getHigh();
			low[i] = ParseCsv.stockList.get(i).getLow();
			close[i] = ParseCsv.stockList.get(i).getClose();
			volume[i] = (double)ParseCsv.stockList.get(i).getVolume();
		}
		tlc = new TALibCalls(open, close, high, low, volume);
	}

	public static String getStartTime() {
		return startTime;
	}

	public static String getEndTime() {
		return endTime;
	}

	public static boolean isTimeOpt() {
		return timeOpt;
	}

	public static void setStartTime(String startTime) {
		StockAnalyzer.startTime = startTime;
	}

	public static void setEndTime(String endTime) {
		StockAnalyzer.endTime = endTime;
	}

	public static void setTimeOpt(boolean timeOpt) {
		StockAnalyzer.timeOpt = timeOpt;
	}
	
	public static String getSymbol() {
		return symbol;
	}

	public static void setSymbol(String symbol) {
		StockAnalyzer.symbol = symbol;
	}

	public static int getInterval() {
		return interval;
	}

	public static void setInterval(int interval) {
		StockAnalyzer.interval = interval;
	}

	public static boolean isTimeSet() {
		return TimeSet;
	}

	public static void setTimeSet(boolean timeSet) {
		TimeSet = timeSet;
	}

	public static String getDelimiter() {
		return delimiter;
	}

	public static void setDelimiter(String delimiter) {
		StockAnalyzer.delimiter = delimiter;
	}

	public static List<String> getError() {
		return error;
	}

	public static void setError(String error) {
		StockAnalyzer.error.add(error);
	}

	public static String getStartDate() {
		return startDate;
	}

	public static void setStartDate(String startDate) {
		StockAnalyzer.startDate = startDate;
	}

	public static String getEndDate() {
		return endDate;
	}

	public static void setEndDate(String endDate) {
		StockAnalyzer.endDate = endDate;
	}

	public static boolean isDateSet() {
		return DateSet;
	}

	public static void setDateSet(boolean dateSet) {
		DateSet = dateSet;
	}


}
