package io.github.danieljo.stockanalyzer.indicator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.github.danieljo.stockanalyzer.service.CsvImportService;


public class Pivot {
	private static double pp_pp;
	private static double pp_s1;
	private static double pp_s2;
	private static double pp_s3;
	private static double pp_r1;
	private static double pp_r2;
	private static double pp_r3;
	
	private static final List<Double> pp_pp_l = new ArrayList<>();
	private static final List<Double> pp_s1_l = new ArrayList<>();
	private static final List<Double> pp_s2_l = new ArrayList<>();
	private static final List<Double> pp_s3_l = new ArrayList<>();
	private static final List<Double> pp_r1_l = new ArrayList<>();
	private static final List<Double> pp_r2_l = new ArrayList<>();
	private static final List<Double> pp_r3_l = new ArrayList<>();
	
	public static final List<Double> PP_PP = new ArrayList<>();
	
	public static void getPivot(int i, double currentHigh, double currentLow, double currentClose){
//		currentHigh = RVI.High.get(i);
//		currentLow = RVI.Low.get(i);
//		currentClose = RVI.Close.get(i);
//		currentHigh = Double.parseDouble(CsvImportService.stockList.get(i).getHigh().replace(",", "."));
//		currentLow = Double.parseDouble(CsvImportService.stockList.get(i).getLow().replace(",", "."));
//		currentClose = Double.parseDouble(CsvImportService.stockList.get(i).getClose().replace(",", "."));
		
		pp_pp = (currentHigh + currentLow + currentClose) / 3;
		CsvImportService.stockList.get(i).setPP_pp(pp_pp);
		PP_PP.add(pp_pp);
		pp_pp_l.add(pp_pp);
	}
	public static void getS1(int i, Double currentHigh){
		//currentHigh = RVI.High.get(i);
		pp_s1 = (2 * PP_PP.get(i) - currentHigh);
		CsvImportService.stockList.get(i).setPP_s1(pp_s1);
		//PP_S1.add(pp_s1);
		pp_s1_l.add(pp_s1);
	}
	public static void getR1(int i, Double currentLow){
		//currentLow = RVI.Low.get(i);
		pp_r1 = (2 * PP_PP.get(i) - currentLow);
		CsvImportService.stockList.get(i).setPP_r1(pp_r1);
		//PP_R1.add(pp_r1);
		pp_r1_l.add(pp_r1);
	}
	public static void getS2(int i, Double currentHigh, Double currentLow){
//		currentHigh = RVI.High.get(i);
//		currentLow = RVI.Low.get(i);
		pp_s2 = PP_PP.get(i) - (currentHigh - currentLow);
		CsvImportService.stockList.get(i).setPP_s2(pp_s2);
		//PP_S2.add(pp_s2);
		pp_s2_l.add(pp_s2);
	}
	public static void getR2(int i, Double currentHigh, Double currentLow){
//		currentHigh = RVI.High.get(i);
//		currentLow = RVI.Low.get(i);
		pp_r2 = PP_PP.get(i) + (currentHigh - currentLow);
		CsvImportService.stockList.get(i).setPP_r2(pp_r2);
		//PP_R2.add(pp_r2);
		pp_r2_l.add(pp_r2);
	}
	public static void getS3(int i, Double currentHigh, Double currentLow){
//		currentHigh = RVI.High.get(i);
//		currentLow = RVI.Low.get(i);
		pp_s3 = pp_pp - 2 * (currentHigh - currentLow);
		CsvImportService.stockList.get(i).setPP_s3(pp_s3);
		//PP_S3.add(pp_s3);
		pp_s3_l.add(pp_s3);
	}
	public static void getR3(int i, Double currentHigh, Double currentLow){
//		currentHigh = RVI.High.get(i);
//		currentLow = RVI.Low.get(i);
		pp_r3 = pp_pp + 2 * (currentHigh - currentLow);
		CsvImportService.stockList.get(i).setPP_r3(pp_r3);
		//PP_R3.add(pp_r3);
		pp_r3_l.add(pp_r3);
	}
	
	public static List<Double> getPp_pp_l() {
		return pp_pp_l;
	}
	public static List<Double> getPp_s1_l() {
		return pp_s1_l;
	}
	public static List<Double> getPp_s2_l() {
		return pp_s2_l;
	}
	public static List<Double> getPp_s3_l() {
		return pp_s3_l;
	}
	public static List<Double> getPp_r1_l() {
		return pp_r1_l;
	}
	public static List<Double> getPp_r2_l() {
		return pp_r2_l;
	}
	public static List<Double> getPp_r3_l() {
		return pp_r3_l;
	}
}
