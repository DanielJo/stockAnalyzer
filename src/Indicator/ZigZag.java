package Indicator;
import java.util.ArrayList;
import java.util.List;

import analyzer.ParseCsv;

public class ZigZag {
	private static boolean CLookingFHigh = true;
	private static double threshold;
	private static double currentLastHigh = ParseCsv.stockList.get(0).getLow();
	private static double currentLastLow = ParseCsv.stockList.get(0).getLow();
	private static double previousLastHigh = ParseCsv.stockList.get(0).getLow();
	private static double previousLastLow = ParseCsv.stockList.get(0).getLow();
	private static int time_High = 0;
	private static int time_Low = 0;
	private static boolean pointFound = false;
	private static List<Double> zz_point_l = new ArrayList<>();
	private static List<Double> zz_value_l = new ArrayList<>();

	public static void CalculateZigZag(double threshold_p) {
		threshold = threshold_p / 100;
		for (int i = 1; i < ParseCsv.stockList.size(); i++) {
			previousLastHigh = currentLastHigh;
			previousLastLow = currentLastLow;
			if (pointFound) {
				CLookingFHigh = !CLookingFHigh;
				if (CLookingFHigh) {
					currentLastLow = ParseCsv.stockList.get(i).getLow();
					currentLastHigh = Math.max(ParseCsv.stockList.get(i)
							.getHigh(), previousLastHigh);
				} else {
					currentLastHigh = ParseCsv.stockList.get(i).getHigh();
					currentLastLow = Math.min(ParseCsv.stockList.get(i)
							.getLow(), previousLastLow);
				}
			} else {
				if (CLookingFHigh) {
					currentLastHigh = Math.max(ParseCsv.stockList.get(i)
							.getHigh(), previousLastHigh);
					if (currentLastHigh > previousLastHigh) {
						currentLastLow = currentLastHigh;
					} else {
						currentLastLow = Math.min(ParseCsv.stockList.get(i)
								.getLow(), previousLastLow);
					}
				} else {
					currentLastLow = Math.min(ParseCsv.stockList.get(i)
							.getLow(), previousLastLow);
					if (currentLastLow < previousLastLow) {
						currentLastHigh = currentLastLow;
					} else {
						currentLastHigh = Math.max(ParseCsv.stockList.get(i)
								.getHigh(), previousLastHigh);
					}
				}
			}
			if ((currentLastHigh != previousLastHigh)
					&& (currentLastHigh == ParseCsv.stockList.get(i).getHigh())) {
				time_High = i;
			}
			if ((currentLastLow != previousLastLow)
					&& (currentLastLow == ParseCsv.stockList.get(i).getLow())) {
				time_Low = i;
			}
			if (CLookingFHigh
					&& ((ParseCsv.stockList.get(i).getHigh() / currentLastLow-1) >= threshold)) {
				ParseCsv.stockList.get(time_High).setZZ_Point(1);
				ParseCsv.stockList.get(time_High).setZZ_Value(ParseCsv.stockList.get(time_High).getHigh());
				pointFound = true;
			} else if ((!CLookingFHigh)
					&& ((ParseCsv.stockList.get(i).getLow() / currentLastHigh-1) <= (-1 * threshold))) {
				ParseCsv.stockList.get(time_Low).setZZ_Point(-1);
				ParseCsv.stockList.get(time_Low).setZZ_Value(ParseCsv.stockList.get(time_Low).getLow());
				pointFound = true;
			}else{
				pointFound = false;
			}
			ParseCsv.stockList.get(i).setZZ_HighTime(ParseCsv.stockList.get(time_High).getDateTime());
			ParseCsv.stockList.get(i).setZZ_LowTime(ParseCsv.stockList.get(time_Low).getDateTime());
		}
		for (int i = 0; i < ParseCsv.stockList.size(); i++){
			zz_point_l.add((double)ParseCsv.stockList.get(i).getZZ_Point());
			zz_value_l.add(ParseCsv.stockList.get(i).getZZ_Value());
		}
	}

	public static List<Double> getZz_point_l() {
		return zz_point_l;
	}

	public static List<Double> getZz_value_l() {
		return zz_value_l;
	}
}
