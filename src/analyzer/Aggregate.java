package analyzer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Aggregate {

	private static List<Double> High = new ArrayList<>();
	private static List<Double> Low = new ArrayList<>();
	private static List<Double> Open = new ArrayList<>();
	private static List<Double> Close = new ArrayList<>();
	private static List<Integer> Volume = new ArrayList<>();
	private static List<String> Endtime = new ArrayList<>();
	private static boolean start = true;
	private static boolean nextDay = false;
	private static int tempVolume;
	private static Double tempOpen;
	private static Double tempHigh;
	private static Double tempLow;
	private static DateTime StartTime;
	private static int target;
	private static int startf;
	private static int tempm;
	private static List<Stock> dayTemp = new ArrayList<>();
	private static int tempd;
	private static int targetd;

	public static void startAggregate(List<Stock> stocklist, int timeframe,
			String symbol) {
		System.out.println("Aggregate");
		for (int i = 0; i < ParseCsv.stockList.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("yyyy-MM-dd HH:mm:ss.S");
			StartTime = formatter.parseDateTime(stocklist.get(i).getDateTime());
			tempm = StartTime.getMinuteOfDay();
			tempd = StartTime.getDayOfWeek();
			if (start) {
				// System.out.println(i + "_" + tempm % timeframe + "_" +
				// StartTime);
				if (tempm % timeframe == 0) {
					//System.out.println(tempm + "_" + StartTime);
					start = false;
					startf = tempm + 1;
					target = tempm + timeframe;
					if (!nextDay){
					targetd = tempd;
					}
//					System.out.println("start: " + startf + "_target: "
//							+ target);
					tempVolume = stocklist.get(i).getVolume();
					tempOpen = stocklist.get(i).getOpen();
					tempHigh = stocklist.get(i).getHigh();
					tempLow = stocklist.get(i).getLow();
				}
			} else if (!start) {
				if (i % 10000 == 0){
				//System.out.println("Day: " + tempd);
				}
				if (tempm == startf){
					tempOpen = stocklist.get(i).getOpen();
				}
				if (tempm < target) {
					tempVolume += stocklist.get(i).getVolume();

					if (stocklist.get(i).getHigh() > tempHigh) {
						tempHigh = stocklist.get(i).getHigh();
					}
					if (stocklist.get(i).getLow() < tempLow || tempLow == 0) {
						tempLow = stocklist.get(i).getLow();
					}
				} else if (tempm == target) {
					tempVolume += stocklist.get(i).getVolume();

					if (stocklist.get(i).getHigh() > tempHigh) {
						tempHigh = stocklist.get(i).getHigh();
					}
					if (stocklist.get(i).getLow() < tempLow || tempLow == 0) {
						tempLow = stocklist.get(i).getLow();
					}
					Open.add(tempOpen);
					Close.add(stocklist.get(i).getClose());
					Endtime.add(stocklist.get(i).getDateTime());
					High.add(tempHigh);
					Low.add(tempLow);
					Volume.add(tempVolume);
					tempVolume = 0;
					tempHigh = 0.;
					tempLow = 0.;
					startf = target + 1;
					target = startf + timeframe - 1;
				} else if (tempm > target) {
					startf = target + 1;
					target = startf + timeframe - 1;
					tempVolume = 0;
					tempHigh = 0.;
					tempLow = 0.;
				} if (targetd != tempd) {
					if (tempm % timeframe == 0) {
						start = true;
						startf = tempm + 1;
						target = startf + timeframe - 1;
						tempVolume = 0;
						tempHigh = 0.;
						tempLow = 0.;
						targetd = tempd;
						nextDay = true;
						i--;
					} else {
						startf = target + 1;
						target = startf + timeframe - 1;
						tempVolume = 0;
						tempHigh = 0.;
						tempLow = 0.;
						nextDay = false;
						start = true;
					}
				}
			}
		}

		ParseCsv.stockList = new ArrayList<>();
		for (int i = 0; i < Close.size(); i++) {
			Stock stock = new Stock(Endtime.get(i), symbol, timeframe,
					Open.get(i), High.get(i), Low.get(i), Close.get(i),
					Volume.get(i));
			ParseCsv.stockList.add(stock);
		}
	}
}
