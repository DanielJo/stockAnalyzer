package Indicator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import analyzer.ParseCsv;

public class RVI {
	private static double Value1;
	private static double Value2;

	private static double Num;
	private static double Denom;
	private static List<Double> RVI1 = new ArrayList<Double>();
	private static double RVI;
	private static double previousRVI = 0.;
	private static double tempSig;
	private static double tempdifference;
	private static double Difference;
	private static List<Double> rvi_l = new ArrayList<Double>();
	private static List<Double> rvi_diff_l = new ArrayList<Double>();
	
	static int k = 0;
	public static List<Double> Open = new ArrayList<Double>();
	public static List<Double> Close = new ArrayList<Double>();
	public static List<Double> High = new ArrayList<Double>();
	public static List<Double> Low = new ArrayList<Double>();
	static DecimalFormat f = new DecimalFormat("#0.00");
	
	public static void getRVI(int i, int rVI_Period) {
		int PERIOD = rVI_Period;
		Denom = 0;
		Num = 0;
		if (i >= (PERIOD + 3)) {
			for (int j = 0; j < PERIOD; j++) {	
							
				Value1 = (((Close.get(i - j) - Open.get(i - j)) + 2 * (Close.get(i - j - 1) - Open.get(i - j - 1)) + 2
						* (Close.get(i - j - 2) - Open.get(i - j - 2)) + (Close.get(i - j - 3) - Open.get(i - j  - 3))) / 6);
				Value2 = (((High.get(i - j) - Low.get(i - j)) + 2 * (High.get(i - j - 1) - Low.get(i - j - 1)) + 2
						* (High.get(i - j - 2) - Low.get(i - j - 2)) + (High.get(i - j - 3) - Low.get(i - j - 3))) / 6);

				Num += Value1;
				Denom += Value2;
			}
			if(Denom != 0){
				RVI1.add(Num / Denom);
			} else {
				RVI1.add(previousRVI);
			}
			RVI = (RVI1.get(k) * 100);

			if(RVI1.size()>=4){
			
				tempSig = ((RVI1.get(k) + 2 * RVI1.get(k-1) + 2 * RVI1.get(k-2) + RVI1.get(k-3))/6 * 100);
				tempdifference = RVI1.get(k) * 100 - tempSig;
				Difference = tempdifference;
				ParseCsv.stockList.get(i).setRVI(RVI);
				ParseCsv.stockList.get(i).setRVI_Difference(Difference);
				rvi_l.add(RVI);
				rvi_diff_l.add(Difference);
			}else{
				rvi_l.add(0.);
				rvi_diff_l.add(0.);
			}
			k++;
		}
		else{
			rvi_l.add(0.);
			rvi_diff_l.add(0.);
		}
	}

	public static List<Double> getRvi_l() {
		return rvi_l;
	}

	public static List<Double> getRvi_diff_l() {
		return rvi_diff_l;
	}

	public static void setRvi_l(double value) {
		rvi_l.add(value);
	}

	public static void setRvi_diff_l(double value) {
		rvi_diff_l.add(value);
	}
	
	public static int getRvi_size(){
		return rvi_l.size();
	}
}
