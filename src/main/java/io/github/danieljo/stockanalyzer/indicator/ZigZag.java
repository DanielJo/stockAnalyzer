package io.github.danieljo.stockanalyzer.indicator;
import java.util.ArrayList;
import java.util.List;

import io.github.danieljo.stockanalyzer.service.CsvImportService;

public class ZigZag {
	private static boolean CLookingFHigh = true;
	private static double threshold;
	private static double currentLastHigh = CsvImportService.stockList.get(0).getLow();
	private static double currentLastLow = CsvImportService.stockList.get(0).getLow();
	private static double previousLastHigh = CsvImportService.stockList.get(0).getLow();
	private static double previousLastLow = CsvImportService.stockList.get(0).getLow();
	private static int time_High = 0;
	private static int time_Low = 0;
	private static boolean pointFound = false;
	private static List<Double> zz_point_l = new ArrayList<>();
	private static List<Double> zz_value_l = new ArrayList<>();

	public static void CalculateZigZag(double threshold_p) {
		threshold = threshold_p / 100;
		for (int i = 1; i < CsvImportService.stockList.size(); i++) {
			previousLastHigh = currentLastHigh;
			previousLastLow = currentLastLow;
			if (pointFound) {
				CLookingFHigh = !CLookingFHigh;
				if (CLookingFHigh) {
					currentLastLow = CsvImportService.stockList.get(i).getLow();
					currentLastHigh = Math.max(CsvImportService.stockList.get(i)
							.getHigh(), previousLastHigh);
				} else {
					currentLastHigh = CsvImportService.stockList.get(i).getHigh();
					currentLastLow = Math.min(CsvImportService.stockList.get(i)
							.getLow(), previousLastLow);
				}
			} else {
				if (CLookingFHigh) {
					currentLastHigh = Math.max(CsvImportService.stockList.get(i)
							.getHigh(), previousLastHigh);
					if (currentLastHigh > previousLastHigh) {
						currentLastLow = currentLastHigh;
					} else {
						currentLastLow = Math.min(CsvImportService.stockList.get(i)
								.getLow(), previousLastLow);
					}
				} else {
					currentLastLow = Math.min(CsvImportService.stockList.get(i)
							.getLow(), previousLastLow);
					if (currentLastLow < previousLastLow) {
						currentLastHigh = currentLastLow;
					} else {
						currentLastHigh = Math.max(CsvImportService.stockList.get(i)
								.getHigh(), previousLastHigh);
					}
				}
			}
			if ((currentLastHigh != previousLastHigh)
					&& (currentLastHigh == CsvImportService.stockList.get(i).getHigh())) {
				time_High = i;
			}
			if ((currentLastLow != previousLastLow)
					&& (currentLastLow == CsvImportService.stockList.get(i).getLow())) {
				time_Low = i;
			}
			if (CLookingFHigh
					&& ((CsvImportService.stockList.get(i).getHigh() / currentLastLow-1) >= threshold)) {
				CsvImportService.stockList.get(time_High).setZZ_Point(1);
				CsvImportService.stockList.get(time_High).setZZ_Value(CsvImportService.stockList.get(time_High).getHigh());
				pointFound = true;
			} else if ((!CLookingFHigh)
					&& ((CsvImportService.stockList.get(i).getLow() / currentLastHigh-1) <= (-1 * threshold))) {
				CsvImportService.stockList.get(time_Low).setZZ_Point(-1);
				CsvImportService.stockList.get(time_Low).setZZ_Value(CsvImportService.stockList.get(time_Low).getLow());
				pointFound = true;
			}else{
				pointFound = false;
			}
			CsvImportService.stockList.get(i).setZZ_HighTime(CsvImportService.stockList.get(time_High).getDateTime());
			CsvImportService.stockList.get(i).setZZ_LowTime(CsvImportService.stockList.get(time_Low).getDateTime());
		}
		for (int i = 0; i < CsvImportService.stockList.size(); i++){
			zz_point_l.add((double)CsvImportService.stockList.get(i).getZZ_Point());
			zz_value_l.add(CsvImportService.stockList.get(i).getZZ_Value());
		}
	}

	public static List<Double> getZz_point_l() {
		return zz_point_l;
	}

	public static List<Double> getZz_value_l() {
		return zz_value_l;
	}
}
