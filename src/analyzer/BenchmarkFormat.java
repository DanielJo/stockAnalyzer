package analyzer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkFormat {
	static int j = 1;
	static int k = 1;
	private static DecimalFormat f = new DecimalFormat("#0.00");
	private static final List<Double> test = new ArrayList<>();
	private static final List<BigDecimal> testBD = new ArrayList<>();
	
	
	private static List<Double> generateArray(){
		for (int i = 0; i < 50000; i++){
			test.add(Math.random());
		}
		return test;
	}
	
	private static List<BigDecimal> generateBCArray(){

		return null;
	}
	
	private static void testDecimalFormat(){
		long start = System.currentTimeMillis();
		for (int i = 0; i < test.size(); i++){
		String testString = f.format(test.get(i));
		}
		System.out.println("DecimalFormat Durchlauf Nr." + j + " :" + (System.currentTimeMillis() - start) + "ms");
		j++;
	}
	
	private static void testOwnMethod(){
		
		StringBuilder b = new StringBuilder();
		long start = System.currentTimeMillis();
		for (int i = 0; i < test.size(); i++){
			appendTo2(b, test.get(i));
			if (i % 500 == 0){
				//System.out.println(b.toString() + "_" + test.get(i));
			}
		}
		System.out.println("OwnMethod Durchlauf Nr." + k + " :" +  (System.currentTimeMillis() - start) + "ms");
		k++;
	}
	
	public static void appendTo2(StringBuilder builder, double d) {
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
	}
	
	public static void initBenchmark(int runs){
		for (int i = 0; i < runs; i++){
			generateArray();
			testDecimalFormat();
			testOwnMethod();
		}
	}
}
