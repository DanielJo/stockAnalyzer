package io.github.danieljo.stockanalyzer.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import io.github.danieljo.stockanalyzer.model.Stock;

/**
 * Replaces Aggregate.java. Migrated from Joda-Time to java.time (getMinuteOfDay() ->
 * toLocalTime().toSecondOfDay()/60, getDayOfWeek() -> getDayOfWeek().getValue(), same 1-7
 * Monday-Sunday numbering in both). Returns the aggregated list instead of writing into
 * ParseCsv.stockList as a side effect, and reads its size from the stocklist parameter instead
 * of the (identical, at every call site) static list the original mixed in.
 * <p>
 * The scratch fields below reset at the top of {@link #startAggregate} now, since they used to
 * carry over between calls with no reset - harmless while this only ever runs once per process,
 * but latent otherwise.
 */
public class AggregateService {

	private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	private static List<Double> high = new ArrayList<>();
	private static List<Double> low = new ArrayList<>();
	private static List<Double> open = new ArrayList<>();
	private static List<Double> close = new ArrayList<>();
	private static List<Integer> volume = new ArrayList<>();
	private static List<String> endtime = new ArrayList<>();
	private static boolean start = true;
	private static boolean nextDay = false;
	private static int tempVolume;
	private static Double tempOpen;
	private static Double tempHigh;
	private static Double tempLow;
	private static int target;
	private static int startf;
	private static int tempm;
	private static int tempd;
	private static int targetd;

	public static List<Stock> startAggregate(List<Stock> stocklist, int timeframe, String symbol) {
		high = new ArrayList<>();
		low = new ArrayList<>();
		open = new ArrayList<>();
		close = new ArrayList<>();
		volume = new ArrayList<>();
		endtime = new ArrayList<>();
		start = true;
		nextDay = false;

		System.out.println("Aggregate");
		for (int i = 0; i < stocklist.size(); i++) {
			LocalDateTime startTime = LocalDateTime.parse(stocklist.get(i).getDateTime(), INPUT_FORMAT);
			tempm = startTime.toLocalTime().toSecondOfDay() / 60;
			tempd = startTime.getDayOfWeek().getValue();
			if (start) {
				if (tempm % timeframe == 0) {
					start = false;
					startf = tempm + 1;
					target = tempm + timeframe;
					if (!nextDay) {
						targetd = tempd;
					}
					tempVolume = stocklist.get(i).getVolume();
					tempOpen = stocklist.get(i).getOpen();
					tempHigh = stocklist.get(i).getHigh();
					tempLow = stocklist.get(i).getLow();
				}
			} else {
				if (tempm == startf) {
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
					open.add(tempOpen);
					close.add(stocklist.get(i).getClose());
					endtime.add(stocklist.get(i).getDateTime());
					high.add(tempHigh);
					low.add(tempLow);
					volume.add(tempVolume);
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
				}
				if (targetd != tempd) {
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

		List<Stock> aggregated = new ArrayList<>();
		for (int i = 0; i < close.size(); i++) {
			aggregated.add(new Stock(endtime.get(i), symbol, timeframe, open.get(i), high.get(i), low.get(i),
					close.get(i), volume.get(i)));
		}
		return aggregated;
	}
}
