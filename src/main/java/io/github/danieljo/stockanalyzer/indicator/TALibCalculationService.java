package io.github.danieljo.stockanalyzer.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.tictactec.ta.lib.CandleSettingType;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;

import io.github.danieljo.stockanalyzer.model.Stock;
import io.github.danieljo.stockanalyzer.service.CsvImportService;

/**
 * @author Frejia
 * 
 */
public class TALibCalculationService {
	private static double resultSetD1[];
	private static double resultSetD2[];
	private static double resultSetD3[];
	private static int resultSetI;
	
	private static double open[];
	private static double close[];
	private static double high[];
	private static double low[];
	private static double volume[];
	private static double adOutput[];
	private static double adoscOutput[];
	private static double adxOutput[];
	private static double adxrOutput[];
	private static double emaOutput[];
	private static double smaOutput[];
	private static double rsiOutput[];
	private static double macdOutput[];
	private static double macdSignal[];
	private static double macdHist[];
	private static MInteger begin;
	private static MInteger length;
	private static Core c;
	private static int lookback;
	private static int indCount;
	private static String error;
	private static List<List<Double>> resultSet = new ArrayList<List<Double>>();
	private static double[] apoOutput;
	private static double[] aroonOutputDown;
	private static double[] aroonOutputUp;
	private static double[] aroonOscOutput;
	private static double[] atrOutput;
	private static double[] avgpriceOutput;
	private static double[] bbandsOutRealUpperBand;
	private static double[] bbandsOutRealMiddleBand;
	private static double[] bbandsOutRealLowerBand;
	private static double[] bopOutput;
	private static double[] cciOutput;
	private static int[] cdl2crowsOutput;
	private static int[] cdl3blackcrows;
	private static int[] cdl3insideOutput;
	private static int[] cdl3linestrikeOutput;
	private static int[] cdl3outsideOutput;
	private static int[] cdl3whitesoldiersOutput;
	private static int[] cdlabandonedbabyOutput;
	private static int calcLength;
	private static int[] cdladvanceblockOutput;
	private static int[] cdlbeltholdOutput;
	private static int[] cdlbreakawayOutput;
	private static int[] cdlclosingmarubozuOutput;
	private static int[] cdlconcealbabyswallOutput;
	private static int[] cdlcounterattackOutput;
	private static int[] cdldarkcloudcoverOutput;
	private static int[] cdldojiOutput;
	private static int[] cdldojistarOutput;
	private static int[] cdldragonflydojiOutput;
	private static int[] cdlengulfingOutput;
	private static int[] cdleveningdojistarOutput;
	private static int[] cdleveningstarOutput;
	private static int[] cdlgapsidesidewhite;
	private static int[] cdlgravestonedoji;
	private static int[] cdlhammerOutput;
	private static int[] cdlhangingmanOutput;
	private static int[] cdlharamiOutput;
	private static int[] cdlharamicrossOutput;
	private static int[] cdlhighwaveOutput;
	private static int[] cdlhikkake;
	private static int[] cdlhikkakemod;
	private static int[] cdlhomingpigeon;
	private static int[] cdlidentical3crows;
	private static int[] cdlinneck;
	private static int[] cdlinvertedhammer;
	private static int[] cdlkicking;
	private static int[] cdlkickingbylength;
	private static int[] cdlladderbottom;
	private static int[] cdllongleggeddoji;
	private static int[] cdllongline;
	private static int[] cdlmarubozu;
	private static int[] cdlmatchinglow;
	private static int[] cdlmathold;
	private static int[] cdlmorningdojistar;
	private static int[] cdlmorningstar;
	private static int[] cdlonneck;
	private static int[] cdlpiercing;
	private static int[] cdlrickshawman;
	private static int[] cdlrisefall3methods;
	private static int[] cdlseparatinglines;
	private static int[] cdlshootingstar;
	private static int[] cdlshortline;
	private static int[] cdlspinningtop;
	private static int[] cdlstalledpattern;
	private static int[] cdlsticksandwich;
	private static int[] cdltakuri;
	private static int[] cdltasukigap;
	private static int[] cdlthrusting;
	private static int[] cdltristar;
	private static int[] cdlunique3river;
	private static int[] cdlupsidegap2crow;
	private static int[] cdlxsidegap3methods;
	private static double[] cmo;
	private static double[] dema;
	private static double[] dx;
	private static double[] htdcperiod;
	private static double[] htdcphase;
	private static double[] htphasor_inphase;
	private static double[] htphasor_outquad;
	private static double[] htsine;
	private static double[] htleadsine;
	private static double[] httrendline;
	private static int[] httrendmode;
	private static double[] kama;
	private static double[] linearreg;
	private static double[] lrregangle;
	private static double[] lrregintercept;
	private static double[] lrregslope;
	private static double[] macd;
	private static double[] macdS;
	private static double[] macdH;
	private static double[] mama;
	private static double[] fama;
	private static double[] max;
	private static int[] maxindex;
	private static double[] medprice;
	private static double[] mfi;
	private static double[] midpoint;
	private static double[] midprice;
	private static double[] min;
	private static int[] minindex;
	private static double[] mmmin;
	private static double[] mmmax;
	private static int[] mmminindex;
	private static int[] mmmaxindex;
	private static double[] minusdi;
	private static double[] minusdm;
	private static double[] mom;
	private static double[] natr;
	private static double[] obv;
	private static double[] plusdi;
	private static double[] plusdm;
	private static double[] ppo;
	private static double[] roc;
	private static double[] rocp;
	private static double[] rocr;
	private static double[] rocr100;
	private static double[] sar;
	private static double[] sarext;
	private static double[] stddev;
	private static double[] stochslowk;
	private static double[] stochslowd;
	private static double[] stochfastk;
	private static double[] stochfastd;
	private static double[] strsifastk;
	private static double[] strsifastd;
	private static double[] t3;
	private static double[] tema;
	private static double[] trange;
	private static double[] trima;
	private static double[] trix;
	private static double[] tsf;
	private static double[] typprice;
	private static double[] ultosc;
	private static double[] var;
	private static double[] wclprice;
	private static double[] willr;
	private static double[] wma;
	private static int[] cdl3stars;

	public TALibCalculationService(double[] open, double[] close, double[] high,
			double[] low, double[] volume2) {
		super();
		TALibCalculationService.open = open;
		TALibCalculationService.close = close;
		TALibCalculationService.high = high;
		TALibCalculationService.low = low;
		TALibCalculationService.volume = volume2;

		begin = new MInteger();
		length = new MInteger();

		calcLength = close.length - 1;

		begin.value = -1;
		length.value = -1;

		c = new Core();
	}

	/**
	 * Builds the open/high/low/close/volume input arrays from the loaded bars and constructs a
	 * (deliberately discarded) instance to populate this class's static input arrays - was
	 * StockAnalyzer.copyArray()/StockAnalyzerApplication.buildIndicatorInput(), pulled in here so
	 * both the CLI and the REST job pipeline can call it without duplicating the loop.
	 * <p>
	 * Also resets {@code resultSet}/{@code indCount}, which otherwise just keep accumulating
	 * across calls - harmless for the CLI (one run per process) but a real bug once more than one
	 * analysis runs in the same JVM (the REST API): without this, a second analysis's output
	 * columns would be appended after the first one's instead of starting fresh. This does *not*
	 * make concurrent calls safe, only sequential ones - see AnalysisJobService's single-threaded
	 * executor for why concurrent access is still avoided entirely for now.
	 */
	public static void initialize(List<Stock> bars) {
		resultSet.clear();
		indCount = 0;

		double[] open = new double[bars.size()];
		double[] high = new double[bars.size()];
		double[] low = new double[bars.size()];
		double[] close = new double[bars.size()];
		double[] volume = new double[bars.size()];
		for (int i = 0; i < bars.size(); i++) {
			open[i] = bars.get(i).getOpen();
			high[i] = bars.get(i).getHigh();
			low[i] = bars.get(i).getLow();
			close[i] = bars.get(i).getClose();
			volume[i] = bars.get(i).getVolume();
		}
		new TALibCalculationService(open, close, high, low, volume);
	}

	public static void writeExt(double[] tempRS) {
		List<Double> tempList = new ArrayList<>();
		int j = 0;
		for (int i = 0; i < CsvImportService.stockList.size(); i++) {
			if (i > lookback) {
				tempList.add(tempRS[j++]);
			} else {
				tempList.add(0.);
			}
		}
		resultSet.add(tempList);
	}

	public static void writeExt(int[] tempRS) {
		List<Double> tempList = new ArrayList<>();
		int j = 0;
		for (int i = 0; i < CsvImportService.stockList.size(); i++) {
			if (i > lookback) {
				tempList.add((double) tempRS[j++]);
			} else {
				tempList.add(0.);
			}
		}
		resultSet.add(tempList);
	}

	public static void writeExt(List<Double> tempRS) {
		resultSet.add(tempRS);
	}

	public static double extractValueD(int i, String[] arguments, io.github.danieljo.stockanalyzer.cli.AnalysisRequest request){
		double value;
		try{
			value = Double.parseDouble(arguments[i]);
		} catch(NumberFormatException ex) {
		      value = 0.0;
		      error = "wrong value format at " + arguments[1] + "; Value set to 0.0";
		      System.out.println(error);
		      request.addError(error);
	    }
		return value;
	}
	
	public static int extractValueI(int i, String[] arguments, io.github.danieljo.stockanalyzer.cli.AnalysisRequest request){
		int value;
		try{
			value = Integer.parseInt(arguments[i]);
		} catch(NumberFormatException ex) {
		      value = 0;
		      error = "wrong value format at " + arguments[1] + "; Value set to 0";
		      System.out.println(error);
		      request.addError(error);
	    }
		return value;
	}
	
	public static void methodCall(String[] arguments, io.github.danieljo.stockanalyzer.cli.AnalysisRequest request) {
		if (arguments[1].equalsIgnoreCase("ema")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("EMA_" + period);
				System.out.println("Calculating EMA");
				writeExt(calcEMA(period));
				indCount++;
				// writeToList(arguments[1], calcEMA(period));
			} else {
				error = "EMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("sma")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("SMA_" + period);
				System.out.println("Calculating SMA");
				writeExt(calcSMA(period));
				indCount++;
				// writeToList(arguments[1], calcSMA(period));
			} else {
				error = "SMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("rsi")) {
			if (arguments.length == 4) {
//				int period = Integer.parseInt(arguments[2]);
//				int smooth = Integer.parseInt(arguments[3]);
				int period = extractValueI(2, arguments, request);
				int smooth = extractValueI(3, arguments, request);
				request.getIndicators().add("RSI_" + period + "_" + smooth
						+ "_(14_3)");
				System.out.println("Calculating RSI");
				writeExt(calcRSI(period, smooth));
				indCount++;
				// writeToList(arguments[1], calcRSI(period, smooth));
			} else {
				error = "RSI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("macd")) {
			if (arguments.length == 5) {
//				int fast = Integer.parseInt(arguments[2]);
//				int slow = Integer.parseInt(arguments[3]);
//				int smooth = Integer.parseInt(arguments[4]);
				int fast = extractValueI(2, arguments, request);
				int slow = extractValueI(3, arguments, request);
				int smooth = extractValueI(4, arguments, request);
				request.getIndicators().add("MACD_" + fast + "_" + slow + "_"
						+ smooth + "_(12_26_9)");
				System.out.println("Calculating MACD");
				writeExt(calcMACD(fast, slow, smooth));
				indCount++;
				// writeToList(arguments[1], calcMACD(fast, slow, smooth));
			} else {
				error = "MACD parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("ad")) {
			if (arguments.length == 2) {
				request.getIndicators().add("AD");
				System.out.println("Calculating AD");
				writeExt(calcAD());
				indCount++;
			} else {
				error = "AD parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("adosc")) {
			if (arguments.length == 4) {
//				int fast = Integer.parseInt(arguments[2]);
//				int slow = Integer.parseInt(arguments[3]);
				int fast = extractValueI(2, arguments, request);
				int slow = extractValueI(3, arguments, request);
				request.getIndicators().add("adosc_" + fast + "_" + slow
						+ "_(3_10)");
				System.out.println("Calculating ADOSC");
				writeExt(calcADOSC(fast, slow));
				indCount++;
			} else {
				error = "ADOSC parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("adx")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("adx_" + period + "_(14)");
				System.out.println("Calculating ADX");
				writeExt(calcADX(period));
				indCount++;
			} else {
				error = "ADX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("adxr")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("adxr_" + period + "_(14)");
				System.out.println("Calculating ADXR");
				writeExt(calcADXR(period));
				indCount++;
			} else {
				error = "ADX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("apo")) {
			if (arguments.length == 4) {
//				int fastperiod = Integer.parseInt(arguments[2]);
//				int slowperiod = Integer.parseInt(arguments[3]);
				int fastperiod = extractValueI(2, arguments, request);
				int slowperiod = extractValueI(3, arguments, request);
				request.getIndicators().add("apo_" + fastperiod + "_" + slowperiod
						+ "_(12_26)");
				System.out.println("Calculating APO");
				writeExt(calcAPO(fastperiod, slowperiod));
				indCount++;
			} else {
				error = "APO parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("aroon")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("aroon_down_" + period + "_(14)");
				request.getIndicators().add("aroon_up");
				System.out.println("Calculating AROON");
				calcAROON(period);
				indCount = indCount + 2;
				writeExt(aroonOutputDown);
				writeExt(aroonOutputUp);
			} else {
				error = "AROON parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("aroonosc")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("aroonosc_" + period + "_(14)");
				System.out.println("Calculating AROONOSC");
				indCount++;
				writeExt(calcAROONOSC(period));
			} else {
				error = "AROONOSC parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("atr")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("atr_" + period + "_(14)");
				System.out.println("Calculating ATR");
				indCount++;
				writeExt(calcATR(period));
			} else {
				error = "ATR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("avgprice")) {
			if (arguments.length == 2) {
				request.getIndicators().add("avgprice");
				System.out.println("Calculating AVGPRICE");
				indCount++;
				writeExt(calcAVGPRICE());
			} else {
				error = "AVGPRICE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("bbands")) {
			if (arguments.length == 5) {
//				int period = Integer.parseInt(arguments[2]);
//				double upperLimit = Double.parseDouble(arguments[3]);
//				double lowerLimit = Double.parseDouble(arguments[3]);
				int period = extractValueI(2, arguments, request);
				double upperLimit = extractValueD(3, arguments, request);
				double lowerLimit = extractValueD(4, arguments, request);
				request.getIndicators().add("BBANDS_UpperBand_" + period + "_"
						+ upperLimit + "_" + lowerLimit + "_(5_2_2)");
				request.getIndicators().add("BBANDS_Middleband");
				request.getIndicators().add("BBANDS_Lowerband");
				System.out.println("Calculating BBANDS");
				indCount = indCount + 3;
				calcBBANDS(period, upperLimit, lowerLimit);
				writeExt(bbandsOutRealUpperBand);
				writeExt(bbandsOutRealMiddleBand);
				writeExt(bbandsOutRealLowerBand);
			} else {
				error = "BBANDS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("bop")) {
			if (arguments.length == 2) {
				request.getIndicators().add("BOP");
				System.out.println("Calculating BOP");
				indCount++;
				writeExt(calcBOP());
			} else {
				error = "BOP parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cci")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("CCI_" + period + "_(14)");
				System.out.println("Calculating CCI");
				indCount++;
				writeExt(calcCCI(period));
			} else {
				error = "CCI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl2crows")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl2crows");
				System.out.println("Calculating CDL2CROWS");
				indCount++;
				writeExt(calcCDL2CROWS());
			} else {
				error = "CDL2CROWS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl3blackcrows")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl3blackcrows");
				System.out.println("Calculating CDL3BLACKCROWS");
				indCount++;
				writeExt(calcCDL3BLACKCROWS());
			} else {
				error = "CDL3BLACKCROWS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl3inside")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl3inside");
				System.out.println("Calculating CDL3INSIDE");
				indCount++;
				writeExt(calcCDL3INSIDE());
			} else {
				error = "CDL3INSIDE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl3linestrike")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl3linestrike");
				System.out.println("Calculating CDL3LINESTRIKE");
				indCount++;
				writeExt(calcCDL3LINESTRIKE());
			} else {
				error = "CDL3LINESTRIKE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl3outside")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl3outside");
				System.out.println("Calculating CDL3OUTSIDE");
				indCount++;
				writeExt(calcCDL3OUTSIDE());
			} else {
				error = "CDL3OUTSIDE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl3starsinsouth")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl3starsinsouth");
				System.out.println("Calculating CDL3STARINSOUTH");
				indCount++;
				writeExt(calcCDL3STARSINSOUTH());
			} else {
				error = "CDL3STARSINSOUTH parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdl3whitesoldiers")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdl3whitesoldiers");
				System.out.println("Calculating CDL3WHITESOLDIERS");
				indCount++;
				writeExt(calcCDL3WHITESOLDIERS());
			} else {
				error = "CDL3WHITESOLDIERS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlabandonedbaby")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdlabandonedbaby_" + penetration
						+ "_(0.3)");
				System.out.println("Calculating CDLABANDONEDBABY");
				indCount++;
				writeExt(calcCDLABANDONEDBABY(penetration));
			} else {
				error = "CDLABANDONEDBABY parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdladvancedblock")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdladvancedblock");
				System.out.println("Calculating CDLADVANCEDBLOCK");
				indCount++;
				writeExt(calcCDLADVANCEBLOCK());
			} else {
				error = "CDLADVANCEDBLOCK parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlbelthold")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlbelthold");
				System.out.println("Calculating CDLBELTHOLD");
				indCount++;
				writeExt(calcCDLBELTHOLD());
			} else {
				error = "CDLBELTHOLD parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlbreakaway")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlbreakaway");
				System.out.println("Calculating CDLBREAKAWAY");
				indCount++;
				writeExt(calcCDLBREAKAWAY());
			} else {
				error = "CDLBREAKAWAY parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlclosingmarubozu")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlclosingmarubozu");
				System.out.println("Calculating CDLCLOSINGMARUBOZU");
				indCount++;
				writeExt(calcCDLCLOSINGMARUBOZU());
			} else {
				error = "CDLCLOSINGMARUBOZU parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlconcealbabysw")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlconcealbabyswall");
				System.out.println("Calculating CDLCONCEALBABYSWALLOW");
				indCount++;
				writeExt(calcCDLCONCEALBABYSWALL());
			} else {
				error = "CDLCONCEALBABYSWALL parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlcounterattack")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlcounterattack");
				System.out.println("Calculating CDLCOUNTERATTACK");
				indCount++;
				writeExt(calcCDLCOUNTERATTACK());
			} else {
				error = "CDLCOUNTERATTACK parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdldarkcloudcover")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdldarkcloudcover_" + penetration
						+ "_(0.5)");
				System.out.println("Calculating CDLDARKCLOUDCOVER");
				indCount++;
				writeExt(calcCDLDARKCLOUDCOVER(penetration));
			} else {
				error = "CDLDARKCLOUDCOVER parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdldoji")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdldoji");
				System.out.println("Calculating CDLDOJI");
				indCount++;
				writeExt(calcCDLDOJI());
			} else {
				error = "CDLDOJI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdldojistar")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdldojistar");
				System.out.println("Calculating CDLDOJISTAR");
				indCount++;
				writeExt(calcCDLDOJISTAR());
			} else {
				error = "CDLDOJISTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdldragonflydoji")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdldragonflydoji");
				System.out.println("Calculating CDLDRAGONFLYDOJI");
				indCount++;
				writeExt(calcCDLDRAGONFLYDOJI());
			} else {
				error = "CDLDRAGONFLYDOJI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlengulfing")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlengulfing");
				System.out.println("Calculating CDLENGULFING");
				indCount++;
				writeExt(calcCDLENGULFING());
			} else {
				error = "CDLENGULFING parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdleveningdojistar")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdleveningdojistar_" + penetration
						+ "_(0.3)");
				System.out.println("Calculating CDLEVENINGDOJISTAR");
				indCount++;
				writeExt(calcCDLEVENINGDOJISTAR(penetration));
			} else {
				error = "CDLEVENINGDOJISTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdleveningstar")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdleveningstar_" + penetration
						+ "_(0.3)");
				System.out.println("Calculating CDLEVENINGSTAR");
				indCount++;
				writeExt(calcCDLEVENINGSTAR(penetration));
			} else {
				error = "CDLEVENINGSTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlgapsidesidewhite")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlgapsidesidewhite");
				System.out.println("Calculating CDLGAPSIDESIDEWHITE");
				indCount++;
				writeExt(calcCDLGAPSIDESIDEWHITE());
			} else {
				error = "CDLGAPSIDESIDEWHITE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlgravestonedoji")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlgravestonedoji");
				System.out.println("Calculating CDLGRAVESTONEDOJI");
				indCount++;
				writeExt(calcCDLGRAVESTONEDOJI());
			} else {
				error = "CDLGRAVESTONEDOJI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlhammer")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlhammer");
				System.out.println("Calculating CDLHAMMER");
				indCount++;
				writeExt(calcCDLHAMMER());
			} else {
				error = "CDLHAMMER parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlhangingman")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlhangingman");
				System.out.println("Calculating CDLHANGINGMAN");
				indCount++;
				writeExt(calcCDLHANGINGMAN());
			} else {
				error = "CDLHANGINGMAN parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlharami")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlharami");
				System.out.println("Calculating CDLHARAMI");
				indCount++;
				writeExt(calcCDLHARAMI());
			} else {
				error = "CDLHARAMI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlharamicross")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlharamicross");
				System.out.println("Calculating CDLHARAMICROSS");
				indCount++;
				writeExt(calcCDLHARAMICROSS());
			} else {
				error = "CDLHARAMICROSS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlhighwave")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlhighwave");
				System.out.println("Calculating CDLHIGHWAVE");
				indCount++;
				writeExt(calcCDLHIGHWAVE());
			} else {
				error = "CDLHIGHWAVE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlhikkake")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlhikkake");
				System.out.println("Calculating CDLHIKKAKE");
				indCount++;
				writeExt(calcCDLHIKKAKE());
			} else {
				error = "CDLHIKKAKE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlhikkakemod")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlhikkakemod");
				System.out.println("Calculating CDLHIKKAKEMOD");
				indCount++;
				writeExt(calcCDLHIKKAKEMOD());
			} else {
				error = "CDLHIKKAKEMOD parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlhomingpigeon")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlhomingpigeon");
				System.out.println("Calculating CDLHOMINGPIGEON");
				indCount++;
				writeExt(calcCDLHOMINGPIGEON());
			} else {
				error = "CDLHOMINGPIGEON parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlidentical3crows")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlidentical3crows");
				System.out.println("Calculating CDLIDENTICAL3CROWS");
				indCount++;
				writeExt(calcCDLIDENTICAL3CROWS());
			} else {
				error = "CDLIDENTICAL3CROWS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlinneck")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlinneck");
				System.out.println("Calculating CDLINNECK");
				indCount++;
				writeExt(calcCDLINNECK());
			} else {
				error = "CDLEVENINGDOJISTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlinvertedhammer")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlinvertedhammer");
				System.out.println("Calculating CDLINVERTEDHAMMER");
				indCount++;
				writeExt(calcCDLINVERTEDHAMMER());
			} else {
				error = "CDLINVERTEDHAMMER parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlkicking")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlkicking");
				System.out.println("Calculating CDLKICKING");
				indCount++;
				writeExt(calcCDLKICKING());
			} else {
				error = "CDLKICKING parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlkickingbylength")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlkickingbylength");
				System.out.println("Calculating CDLKICKINGBYLENGTH");
				indCount++;
				writeExt(calcCDLKICKINGBYLENGTH());
			} else {
				error = "CDLKICKINGBYLENGTH parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlladderbottom")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlladderbottom");
				System.out.println("Calculating CDLLADDERBOTTOM");
				indCount++;
				writeExt(calcCDLLADDERBOTTOM());
			} else {
				error = "CDLLADDERBOTTOM parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdllongleggeddoji")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdllongleggeddoji");
				System.out.println("Calculating CDLLONGLEGGEDDOJI");
				indCount++;
				writeExt(calcCDLLONGLEGGEDDOJI());
			} else {
				error = "CDLLONGLEGGEDDOJI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdllongline")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdllongline");
				System.out.println("Calculating CDLLONGLINE");
				indCount++;
				writeExt(calcCDLLONGLINE());
			} else {
				error = "CDLLONGLINE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlmarubozu")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlmarubozu");
				System.out.println("Calculating CDLMARUBOZU");
				indCount++;
				writeExt(calcCDLMARUBOZU());
			} else {
				error = "CDLMARUBOZU parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlmatchinglow")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlmatchinglow");
				System.out.println("Calculating CDLMATCHINGLOW");
				indCount++;
				writeExt(calcCDLMATCHINGLOW());
			} else {
				error = "CDLMATCHINGLOW parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlmathold")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdlmathold_" + penetration + "_(0.5)");
				System.out.println("Calculating CDLMATHOLD");
				indCount++;
				writeExt(calcCDLMATHOLD(penetration));
			} else {
				error = "CDLMATHOLD parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlmorningdojistar")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdlmorningdojistar_" + penetration
						+ "_(0.3)");
				System.out.println("Calculating CDLMORNINGDOJISTAR");
				indCount++;
				writeExt(calcCDLMORNINGDOJISTAR(penetration));
			} else {
				error = "CDLMORNINGDOJISTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlmorningstar")) {
			if (arguments.length == 3) {
				//double penetration = Double.parseDouble(arguments[2]);
				double penetration = extractValueD(2, arguments, request);
				request.getIndicators().add("cdlmorningstar_" + penetration
						+ "_(0.3)");
				System.out.println("Calculating CDLMORNINGSTAR");
				indCount++;
				writeExt(calcCDLMORNINGSTAR(penetration));
			} else {
				error = "CDLMORNINGSTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlonneck")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlonneck");
				System.out.println("Calculating CDLONNECK");
				indCount++;
				writeExt(calcCDLONNECK());
			} else {
				error = "CDLONNECK parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlpiercing")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlpiercing");
				System.out.println("Calculating CDLPIERCING");
				indCount++;
				writeExt(calcCDLPIERCING());
			} else {
				error = "CDLPIERCING parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlrickshawman")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlrickshawman");
				System.out.println("Calculating CDLRICKSHAWMAN");
				indCount++;
				writeExt(calcCDLRICKSHAWMAN());
			} else {
				error = "CDLRICKSHAWMAN parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlrisefall3methods")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlrisefall3methods");
				System.out.println("Calculating CDLRISEFALL3METHODS");
				indCount++;
				writeExt(calcCDLRISEFALL3METHODS());
			} else {
				error = "CDLRISEFALL3METHODS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlseparatinglines")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlseparatinglines");
				System.out.println("Calculating CDLSEPARATINGLINES");
				indCount++;
				writeExt(calcCDLSEPARATINGLINES());
			} else {
				error = "CDLSEPARATINGLINES parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlshootingstar")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlshootingstar");
				System.out.println("Calculating CDLSHOOTINGSTAR");
				indCount++;
				writeExt(calcCDLSHOOTINGSTAR());
			} else {
				error = "CDLSHOOTINGSTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlshortline")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlshortline");
				System.out.println("Calculating CDLSHORTLINE");
				indCount++;
				writeExt(calcCDLSHORTLINE());
			} else {
				error = "CDLSHORTLINE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlspinningtop")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlspinningtop");
				System.out.println("Calculating CDLSPINNINGTOP");
				indCount++;
				writeExt(calcCDLSPINNINGTOP());
			} else {
				error = "CDLSPINNINGTOP parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlstalledpattern")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlstalledpattern");
				System.out.println("Calculating CDLSTALLEDPATTERN");
				indCount++;
				writeExt(calcCDLSTALLEDPATTERN());
			} else {
				error = "CDLSTALLEDPATTERN parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlsticksandwich")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlsticksandwich");
				System.out.println("Calculating CDLSTICKSANDWICH");
				indCount++;
				writeExt(calcCDLSTICKSANDWICH());
			} else {
				error = "CDLSTICKSANDWICH parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdltakuri")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdltakuri");
				System.out.println("Calculating CDLTAKURI");
				indCount++;
				writeExt(calcCDLTAKURI());
			} else {
				error = "CDLTAKURI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdltasukigap")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdltakusigap");
				System.out.println("Calculating CDLTAKUSIGAP");
				indCount++;
				writeExt(calcCDLTASUKIGAP());
			} else {
				error = "CDLSHOOTINGSTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlthrusting")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlthrusting");
				System.out.println("Calculating CDLTHRUSTING");
				indCount++;
				writeExt(calcCDLTHRUSTING());
			} else {
				error = "CDLTHRUSTING parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdltristar")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdltristar");
				System.out.println("Calculating CDLTRISTAR");
				indCount++;
				writeExt(calcCDLTRISTAR());
			} else {
				error = "CDLTRISTAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlunique3river")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlunique3river");
				System.out.println("Calculating CDLUNIQUE3RIVER");
				indCount++;
				writeExt(calcCDLUNIQUE3RIVER());
			} else {
				error = "CDLUNIQUE3RIVER parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlupsidegap2crows")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlupsidegap2crows");
				System.out.println("Calculating CDLUPSIDEGAP2CROWS");
				indCount++;
				writeExt(calcCDLUPSIDEGAP2CROW());
			} else {
				error = "CDLUPSIDEGAP2CROW parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cdlxsidegap3methods")) {
			if (arguments.length == 2) {
				request.getIndicators().add("cdlxsidegap3methods");
				System.out.println("Calculating CDLXSIDEGAP3METHODS");
				indCount++;
				writeExt(calcCDLXSIDEGAP3METHODS());
			} else {
				error = "CDLXSIDEGAP3METHODS parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("cmo")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("cmo_" + period + "_(14)");
				System.out.println("Calculating CMO");
				indCount++;
				writeExt(calcCMO(period));
			} else {
				error = "CMO parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("dema")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("dema_" + period + "_(30)");
				System.out.println("Calculating DEMA");
				indCount++;
				writeExt(calcDEMA(period));
			} else {
				error = "DEMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("dx")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("dx_" + period + "_(14)");
				System.out.println("Calculating DX");
				indCount++;
				writeExt(calcDX(period));
			} else {
				error = "DX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("htdcperiod")) {
			if (arguments.length == 2) {
				request.getIndicators().add("htdcperiod");
				System.out.println("Calculating HTDCPERIOD");
				indCount++;
				writeExt(calcHTDCPERIOD());
			} else {
				error = "HTDCPERIOD parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("htdcphase")) {
			if (arguments.length == 2) {
				request.getIndicators().add("htdcphase");
				System.out.println("Calculating HTDCPHASE");
				indCount++;
				writeExt(calcHTDCPHASE());
			} else {
				error = "HTDCPHASE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("htphasor")) {
			if (arguments.length == 2) {
				request.getIndicators().add("htphasor_inphase");
				request.getIndicators().add("htphasor_quadrature");
				System.out.println("Calculating HTPHASOR");
				indCount = indCount + 2;
				calcHTPHASOR();
				writeExt(htphasor_inphase);
				writeExt(htphasor_outquad);
			} else {
				error = "HTPHASOR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("htsine")) {
			if (arguments.length == 2) {
				request.getIndicators().add("htsine_sine");
				request.getIndicators().add("htsine_leadsine");
				System.out.println("Calculating HTSINE");
				indCount = indCount + 2;
				calcHTSINE();
				writeExt(htsine);
				writeExt(htleadsine);
			} else {
				error = "HTSINE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("httrendline")) {
			if (arguments.length == 2) {
				request.getIndicators().add("httrendline");
				System.out.println("Calculating HTTRENDLINE");
				indCount++;
				writeExt(calcHTTRENDLINE());
			} else {
				error = "HTTRENDLINE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("httrendmode")) {
			if (arguments.length == 2) {
				request.getIndicators().add("httrendmode");
				System.out.println("Calculating HTTRENDMODE");
				indCount++;
				writeExt(calcHTTRENDMODE());
			} else {
				error = "HTTRENDMODE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("kama")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("kama_" + period + "_(30)");
				System.out.println("Calculating KAMA");
				indCount++;
				writeExt(calcKAMA(period));
			} else {
				error = "KAMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("linearreg")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("linearreg_" + period + "_(14)");
				System.out.println("Calculating LINEARREG");
				indCount++;
				writeExt(calcLINEARREG(period));
			} else {
				error = "LINEARREG parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("linearregangle")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("linearreg_angle_" + period + "_(14)");
				System.out.println("Calculating LINEARREG_ANGLE");
				indCount++;
				writeExt(calcLINEARREG_ANGLE(period));
			} else {
				error = "LINEARREG_ANGLE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("linearregintercept")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("linearreg_intercept_" + period
						+ "_(14)");
				System.out.println("Calculating LINEARREG_INTERCEPT");
				indCount++;
				writeExt(calcLINEARREG_INTERCEPT(period));
			} else {
				error = "LINEARREG_INTERCEPT parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("linearregslope")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("linearreg_slope_" + period + "_(14)");
				System.out.println("Calculating LINEARREG_SLOPE");
				indCount++;
				writeExt(calcLINEARREG_SLOPE(period));
			} else {
				error = "LINEARREG_SLOPE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("macdext")) {
			if (arguments.length == 8) {
//				int fast = Integer.parseInt(arguments[2]);
//				int slow = Integer.parseInt(arguments[3]);
//				int smooth = Integer.parseInt(arguments[4]);
				int fast = extractValueI(2, arguments, request);
				int slow = extractValueI(3, arguments, request);
				int smooth = extractValueI(4, arguments, request);
				boolean flag1 = false;
				boolean flag2 = false;
				boolean flag3 = false;
				if (arguments[5].equals("1")) {
					flag1 = true;
				}
				if (arguments[6].equals("1")) {
					flag2 = true;
				}
				if (arguments[7].equals("1")) {
					flag3 = true;
				}
				request.getIndicators().add("macdext_" + fast + "_" + slow + "_"
						+ smooth + "_" + arguments[5] + "_" + arguments[6]
						+ "_" + arguments[7] + "_(12_26_9_0_0_0)");
				System.out.println("Calculating MACDEXT");
				indCount++;
				writeExt(calcMACDEXT(fast, slow, smooth, flag1, flag2, flag3));
			} else {
				error = "MACDEXT parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("macdfix")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("macdfix_" + period + "_(9)");
				System.out.println("Calculating MACDFIX");
				indCount++;
				writeExt(calcMACDFIX(period));
			} else {
				error = "MACDFIX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("mama")) {
			if (arguments.length == 4) {
//				double fast = Double.parseDouble(arguments[2]);
//				double slow = Double.parseDouble(arguments[3]);
				double fast = extractValueD(2, arguments, request);
				double slow = extractValueD(3, arguments, request);
				request.getIndicators().add("mama_" + fast + "_" + slow
						+ "_(0.5_0.05)");
				request.getIndicators().add("mama_fama");
				System.out.println("Calculating MAMA");
				indCount = indCount + 2;
				calcMAMA(fast, slow);
				writeExt(mama);
				writeExt(fama);
			} else {
				error = "MAMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("max")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("max_" + period + "_(30)");
				System.out.println("Calculating MAX");
				indCount++;
				writeExt(calcMAX(period));
			} else {
				error = "MAX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("maxindex")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("maxindex_" + period + "_(30)");
				System.out.println("Calculating MAXINDEX");
				indCount++;
				writeExt(calcMAXINDEX(period));
			} else {
				error = "MAXINDEX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("medprice")) {
			if (arguments.length == 2) {
				request.getIndicators().add("medprice");
				System.out.println("Calculating MEDPRICE");
				indCount++;
				writeExt(calcMEDPRICE());
			} else {
				error = "MEDPRICE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("mfi")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("mfi_" + period + "_(14)");
				System.out.println("Calculating MFI");
				indCount++;
				writeExt(calcMFI(period));
			} else {
				error = "MFI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("midpoint")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("midpoint_" + period + "_(14)");
				System.out.println("Calculating MIDPOINT");
				indCount++;
				writeExt(calcMIDPOINT(period));
			} else {
				error = "MIDPOINT parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("midprice")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("midprice_" + period + "_(14)");
				System.out.println("Calculating MIDPRICE");
				indCount++;
				writeExt(calcMIDPRICE(period));
			} else {
				error = "MIDPRICE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("min")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("min_" + period + "_(30)");
				System.out.println("Calculating MIN");
				indCount++;
				writeExt(calcMIN(period));
			} else {
				error = "MIN parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("minindex")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("minindex_" + period + "_(30)");
				System.out.println("Calculating MININDEX");
				indCount++;
				writeExt(calcMININDEX(period));
			} else {
				error = "MININDEX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("minmax")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("minmax_min_" + period + "_(30)");
				request.getIndicators().add("minmax_max");
				System.out.println("Calculating MINMAX");
				indCount = indCount + 2;
				calcMINMAX(period);
				writeExt(mmmin);
				writeExt(mmmax);
			} else {
				error = "MINMAX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("minmaxindex")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("minmaxindex_min_" + period + "_(30)");
				request.getIndicators().add("minmaxindex_max_");
				System.out.println("Calculating MINMAXINDEX");
				indCount = indCount + 2;
				calcMINMAXINDEX(period);
				writeExt(mmminindex);
				writeExt(mmmaxindex);
			} else {
				error = "MINMAXINDEX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("minusdi")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("minusdi_" + period + "_(14)");
				System.out.println("Calculating MINUSDI");
				indCount++;
				writeExt(calcMINUSDI(period));
			} else {
				error = "MINUSDI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("minusdm")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("minusdm_" + period + "_(14)");
				System.out.println("Calculating MINUSDM");
				indCount++;
				writeExt(calcMINUSDM(period));
			} else {
				error = "MINUSDM parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("mom")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("mom_" + period + "_(10)");
				System.out.println("Calculating MOM");
				indCount++;
				writeExt(calcMOM(period));
			} else {
				error = "MOM parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("natr")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("natr_" + period + "_(14)");
				System.out.println("Calculating NATR");
				indCount++;
				writeExt(calcNATR(period));
			} else {
				error = "NATR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("obv")) {
			if (arguments.length == 2) {
				request.getIndicators().add("obv");
				System.out.println("Calculating OBV");
				indCount++;
				writeExt(calcOBV());
			} else {
				error = "OBV parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("plusdi")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("plusdi_" + period + "_(14)");
				System.out.println("Calculating PLUSDI");
				indCount++;
				writeExt(calcPLUSDI(period));
			} else {
				error = "PLUSDI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("plusdm")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("plusdm_" + period + "_(14)");
				System.out.println("Calculating PLUSDM");
				indCount++;
				writeExt(calcPLUSDM(period));
			} else {
				error = "PLUSDM parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("ppo")) {
			if (arguments.length == 4) {
//				int fast = Integer.parseInt(arguments[2]);
//				int slow = Integer.parseInt(arguments[3]);
				int fast = extractValueI(2, arguments, request);
				int slow = extractValueI(3, arguments, request);
				request.getIndicators()
						.add("ppo_" + fast + "_" + slow + "_(12_26)");
				System.out.println("Calculating PPO");
				indCount++;
				writeExt(calcPPO(fast, slow));
			} else {
				error = "PPO parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("roc")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("roc_" + period + "_(10)");
				System.out.println("Calculating ROC");
				indCount++;
				writeExt(calcROC(period));
			} else {
				error = "ROC parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("rocp")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("rocp_" + period + "_(10)");
				System.out.println("Calculating ROCP");
				indCount++;
				writeExt(calcROCP(period));
			} else {
				error = "ROCP parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("rocr")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("rocr_" + period + "_(10)");
				System.out.println("Calculating ROCR");
				indCount++;
				writeExt(calcROCR(period));
			} else {
				error = "ROCR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("rocr100")) {
			if (arguments.length == 3) {
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("rocr100_" + period + "_(10)");
				System.out.println("Calculating ROCR100");
				indCount++;
				writeExt(calcROCR100(period));
			} else {
				error = "ROCR100 parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("sar")) {
			if (arguments.length == 4) {
//				double acceleration = Double.parseDouble(arguments[2]);
//				double maximum = Double.parseDouble(arguments[3]);
				double acceleration = extractValueD(2, arguments, request);
				double maximum = extractValueD(3, arguments, request);
				request.getIndicators().add("sar_" + acceleration + "_" + maximum
						+ "_(0.02_0.2");
				System.out.println("Calculating SAR");
				indCount++;
				writeExt(calcSAR(acceleration, maximum));
			} else {
				error = "ROCR100 parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("sarext")) {
			if (arguments.length == 10) {
//				int startvalue = Integer.parseInt(arguments[2]);
//				int offsetonreverse = Integer.parseInt(arguments[3]);
//				double accelerationinitlong = Double.parseDouble(arguments[4]);
//				double accelerationlong = Double.parseDouble(arguments[5]);
//				double accelerationmaxlong = Double.parseDouble(arguments[6]);
//				double accelerationinitshort = Double.parseDouble(arguments[7]);
//				double accelerationshort = Double.parseDouble(arguments[8]);
//				double accelerationmaxshort = Double.parseDouble(arguments[9]);
				int startvalue = extractValueI(2, arguments, request);
				int offsetonreverse = extractValueI(3, arguments, request);
				double accelerationinitlong = extractValueD(4, arguments, request);
				double accelerationlong = extractValueD(5, arguments, request);
				double accelerationmaxlong = extractValueD(6, arguments, request);
				double accelerationinitshort = extractValueD(7, arguments, request);
				double accelerationshort = extractValueD(8, arguments, request);
				double accelerationmaxshort = extractValueD(9, arguments, request);
				request.getIndicators().add("sarext_" + startvalue + "_"
						+ offsetonreverse + "_" + accelerationinitlong + "_"
						+ accelerationlong + "_" + accelerationmaxlong + "_"
						+ accelerationinitshort + "_" + accelerationshort + "_"
						+ accelerationmaxshort
						+ "_(0_0_0.02_0.02_0.2_0.02_0.02_0.2)");
				System.out.println("Calculating SAREXT");
				indCount++;
				writeExt(calcSAREXT(startvalue, offsetonreverse, accelerationinitlong, accelerationlong, accelerationmaxlong, accelerationinitshort, accelerationshort, accelerationmaxshort));
			} else {
				error = "SAREXT parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("stddev")){
			if (arguments.length == 4){
//				int period = Integer.parseInt(arguments[2]);
//				int nbdev = Integer.parseInt(arguments[3]);
				int period = extractValueI(2, arguments, request);
				int nbdev = extractValueI(3, arguments, request);
				request.getIndicators().add("stddev_" + period + "_" + nbdev + "_(5_1)");
				System.out.println("Calculating STDDEV");
				indCount++;
				writeExt(calcSTDDEV(period, nbdev));
			} else {
				error = "STDDEV parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("stoch")){
			if (arguments.length == 5){
//				int fastk = Integer.parseInt(arguments[2]);
//				int slowk = Integer.parseInt(arguments[3]);
//				int slowd = Integer.parseInt(arguments[4]);
				int fastk = extractValueI(2, arguments, request);
				int slowk = extractValueI(3, arguments, request);
				int slowd = extractValueI(4, arguments, request);
				request.getIndicators().add("stoch_slowk_" + fastk + "_" + slowk + "_" + slowd + "_(5_3_3)");
				request.getIndicators().add("stoch_slowd");
				System.out.println("Calculating STOCH");
				indCount = indCount + 2;
				calcSTOCH(fastk, slowk, slowd);
				writeExt(stochslowk);
				writeExt(stochslowd);
			} else {
				error = "STOCH parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("stochf")){
			if (arguments.length == 4){
//				int fastk = Integer.parseInt(arguments[2]);
//				int fastd = Integer.parseInt(arguments[3]);
				int fastk = extractValueI(2, arguments, request);
				int fastd = extractValueI(3, arguments, request);
				request.getIndicators().add("stochf_fastk_" + fastk + "_" + fastd + "_(5_3)");
				request.getIndicators().add("stochf_fastd");
				System.out.println("Calculating STOCHF");
				indCount = indCount + 2;
				calcSTOCHF(fastk, fastd);
				writeExt(stochfastk);
				writeExt(stochfastd);
			} else {
				error = "STOCHF parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("stochrsi")){
			if (arguments.length == 5){
//				int period = Integer.parseInt(arguments[2]);
//				int fastk = Integer.parseInt(arguments[3]);
//				int fastd = Integer.parseInt(arguments[4]);
				int period = extractValueI(2, arguments, request);
				int fastk = extractValueI(3, arguments, request);
				int fastd = extractValueI(4, arguments, request);
				request.getIndicators().add("stochrsi_fastk_" + period + "_" + fastk + "_" + fastd + "_(14_5_3)");
				request.getIndicators().add("stochrsi_fastd");
				System.out.println("Calculating STOCHRSI");
				indCount = indCount + 2;
				calcSTOCHRSI(period, fastk, fastd);
				writeExt(strsifastk);
				writeExt(strsifastd);
			} else {
				error = "STOCHRSI parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("t3")){
			if (arguments.length == 4){
//				int period = Integer.parseInt(arguments[2]);
//				double vfactor = Double.parseDouble(arguments[3]);
				int period = extractValueI(2, arguments, request);
				double vfactor = extractValueD(3, arguments, request);
				request.getIndicators().add("t3_" + period + "_" + vfactor + "_(5_0.7)");
				System.out.println("Calculating T3");
				indCount++;
				writeExt(calcT3(period, vfactor));
			} else {
				error = "T3 parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("tema")){
			if (arguments.length == 3){
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("tema_" + period + "_(30)");
				System.out.println("Calculating TEMA");
				indCount++;
				writeExt(calcTEMA(period));
			} else {
				error = "TEMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("trange")){
			if (arguments.length == 2){
				request.getIndicators().add("trange");
				System.out.println("Calculating TRANGE");
				indCount++;
				writeExt(calcTRANGE());
			} else {
				error = "TRANGE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("trima")){
			if (arguments.length == 3){
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("trima_" + period + "_(30)");
				System.out.println("Calculating TRIMA");
				indCount++;
				writeExt(calcTRIMA(period));
			} else {
				error = "TRIMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("trix")){
			if (arguments.length == 3){
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("trix_" + period + "_(30)");
				System.out.println("Calculating TRIX");
				indCount++;
				writeExt(calcTRIX(period));
			} else {
				error = "TRIX parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("tsf")){
			if (arguments.length == 3){
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("tsf_" + period + "_(14)");
				System.out.println("Calculating TSF");
				indCount++;
				writeExt(calcTSF(period));
			} else {
				error = "TSF parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("typprice")){
			if (arguments.length == 2){
				request.getIndicators().add("typprice");
				System.out.println("Calculating TYPPRICE");
				indCount++;
				writeExt(calcTYPPRICE());
			} else {
				error = "TYPPRICE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("ultosc")){
			if (arguments.length == 5){
//				int period1 = Integer.parseInt(arguments[2]);
//				int period2 = Integer.parseInt(arguments[3]);
//				int period3 = Integer.parseInt(arguments[4]);
				int period1 = extractValueI(2, arguments, request);
				int period2 = extractValueI(3, arguments, request);
				int period3 = extractValueI(4, arguments, request);
				request.getIndicators().add("ultosc_" + period1 + "_" + period2 + "_" + period3 + "_(7_14_28)");
				System.out.println("Calculating ULTOSC");
				indCount++;
				writeExt(calcULTOSC(period1, period2, period3));
			} else {
				error = "ULTOSC parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("var")){
			if (arguments.length == 4){
//				int period = Integer.parseInt(arguments[2]);
//				int nbdev = Integer.parseInt(arguments[3]);
				int period = extractValueI(2, arguments, request);
				int nbdev = extractValueI(3, arguments, request);
				request.getIndicators().add("var_" + period + "_" + nbdev + "_(5_1)");
				System.out.println("Calculating VAR");
				indCount++;
				writeExt(calcVAR(period, nbdev));
			} else {
				error = "VAR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("wclprice")){
			if (arguments.length == 2){
				request.getIndicators().add("wclprice");
				System.out.println("Calculating WCLPRICE");
				indCount++;
				writeExt(calcWCLPRICE());
			} else {
				error = "WCLPRICE parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("willr")){
			if (arguments.length == 3){
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("willr_" + period + "_(14)");
				System.out.println("Calculating WILLR");
				indCount++;
				writeExt(calcWILLR(period));
			} else {
				error = "WILLR parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else if (arguments[1].equalsIgnoreCase("wma")){
			if (arguments.length == 3){
				//int period = Integer.parseInt(arguments[2]);
				int period = extractValueI(2, arguments, request);
				request.getIndicators().add("wma_" + period + "_(30)");
				System.out.println("Calculating WMA");
				indCount++;
				writeExt(calcWMA(period));
			} else {
				error = "WMA parameter mismatch";
				request.addError(error);
				System.out.println(error);
			}
		} else {
			error = "unknown command " + arguments[1];
			request.addError(error);
			System.out.println(error);
		}
	}

	public static List<List<Double>> getResultSet() {
		return resultSet;
	}

	public static double[] calcAD() {
		adOutput = new double[close.length];
		lookback = 0;
		RetCode retcode = c.ad(0, calcLength, high, low, close, volume, begin,
				length, adOutput);
		return adOutput;
	}

	public static double[] calcADOSC(int fast, int slow) {
		adoscOutput = new double[close.length];
		lookback = c.adOscLookback(fast, slow);
		RetCode retcode = c.adOsc(0, calcLength, high, low, close, volume,
				fast, slow, begin, length, adoscOutput);
		return adoscOutput;
	}

	public static double[] calcADX(int period) {
		adxOutput = new double[close.length];
		lookback = c.adxLookback(period);
		RetCode retcode = c.adx(0, calcLength, high, low, close, period, begin,
				length, adxOutput);
		return adxOutput;
	}

	public static double[] calcADXR(int period) {
		adxrOutput = new double[close.length];
		lookback = c.adxrLookback(period);
		RetCode retcode = c.adxr(0, calcLength, high, low, close, period,
				begin, length, adxrOutput);
		return adxrOutput;
	}

	public static double[] calcAPO(int fast, int slow) {
		apoOutput = new double[close.length];
		lookback = c.apoLookback(fast, slow, MAType.Ema);
		RetCode retcode = c.apo(0, calcLength, close, fast, slow, MAType.Ema,
				begin, length, apoOutput);
		return apoOutput;
	}

	public static void calcAROON(int period) {
		aroonOutputDown = new double[close.length];
		aroonOutputUp = new double[close.length];
		lookback = c.aroonLookback(period);
		RetCode retcode = c.aroon(0, calcLength, high, low, period, begin,
				length, aroonOutputDown, aroonOutputUp);
	}

	public static double[] calcAROONOSC(int period) {
		aroonOscOutput = new double[close.length];
		lookback = c.aroonOscLookback(period);
		RetCode retcode = c.aroonOsc(0, calcLength, high, low, period, begin,
				length, aroonOscOutput);
		return aroonOscOutput;
	}

	public static double[] calcATR(int period) {
		atrOutput = new double[close.length];
		lookback = c.atrLookback(period);
		RetCode retcode = c.atr(0, calcLength, high, low, close, period, begin,
				length, atrOutput);
		return atrOutput;
	}

	public static double[] calcAVGPRICE() {
		avgpriceOutput = new double[close.length];
		lookback = c.avgPriceLookback();
		RetCode retcode = c.avgPrice(0, calcLength, open, high, low, close,
				begin, length, avgpriceOutput);
		return avgpriceOutput;
	}

	/*
	 * optInUp and down standardvalue 2.0
	 */
	public static void calcBBANDS(int period, double upperLimit,
			double lowerLimit) {
		bbandsOutRealUpperBand = new double[close.length];
		bbandsOutRealMiddleBand = new double[close.length];
		bbandsOutRealLowerBand = new double[close.length];
		lookback = c.bbandsLookback(period, upperLimit, lowerLimit, MAType.Sma);
		RetCode retcode = c.bbands(0, calcLength, close, period, upperLimit,
				lowerLimit, MAType.Sma, begin, length, bbandsOutRealUpperBand,
				bbandsOutRealMiddleBand, bbandsOutRealLowerBand);
	}

	// public static double[] calcBETA(int period){
	// betaOutput = new double[close.length];
	// lookback = c.betaLookback(period);
	// RetCode retcode = c.beta(0, close.length - 1, inReal0, inReal1, period,
	// begin, length, betaOutput);
	// return betaOutput;
	// }

	public static double[] calcBOP() {
		bopOutput = new double[close.length];
		lookback = c.bopLookback();
		RetCode retcode = c.bop(0, calcLength, open, high, low, close, begin,
				length, bopOutput);
		return bopOutput;
	}

	public static double[] calcCCI(int period) {
		cciOutput = new double[close.length];
		lookback = c.cciLookback(period);
		RetCode retcode = c.cci(0, calcLength, high, low, close, period, begin,
				length, cciOutput);
		return cciOutput;
	}

	public static int[] calcCDL2CROWS() {
		cdl2crowsOutput = new int[close.length];
		lookback = c.cdl2CrowsLookback();
		RetCode retcode = c.cdl2Crows(0, calcLength, open, high, low, close,
				begin, length, cdl2crowsOutput);
		return cdl2crowsOutput;
	}

	public static int[] calcCDL3BLACKCROWS() {
		cdl3blackcrows = new int[close.length];
		lookback = c.cdl3BlackCrowsLookback();
		RetCode retcode = c.cdl3BlackCrows(0, calcLength, open, high, low,
				close, begin, length, cdl3blackcrows);
		return cdl3blackcrows;
	}

	public static int[] calcCDL3INSIDE() {
		cdl3insideOutput = new int[close.length];
		lookback = c.cdl3InsideLookback();
		RetCode retcode = c.cdl3Inside(0, calcLength, open, high, low, close,
				begin, length, cdl3insideOutput);
		return cdl3insideOutput;
	}

	public static int[] calcCDL3LINESTRIKE() {
		cdl3linestrikeOutput = new int[close.length];
		lookback = c.cdl3LineStrikeLookback();
		RetCode retcode = c.cdl3LineStrike(0, calcLength, open, high, low,
				close, begin, length, cdl3linestrikeOutput);
		return cdl3linestrikeOutput;
	}

	public static int[] calcCDL3OUTSIDE() {
		cdl3outsideOutput = new int[close.length];
		lookback = c.cdl3OutsideLookback();
		RetCode retcode = c.cdl3Outside(0, calcLength, open, high, low, close,
				begin, length, cdl3outsideOutput);
		return cdl3outsideOutput;
	}

	public static int[] calcCDL3STARSINSOUTH() {
		cdl3stars = new int[close.length];
		lookback = c.cdl3StarsInSouthLookback();
		RetCode retcode = c.cdl3StarsInSouth(0, calcLength, open, high, low,
				close, begin, length, cdl3stars);
		return cdl3stars;
	}

	public static int[] calcCDL3WHITESOLDIERS() {
		cdl3whitesoldiersOutput = new int[close.length];
		lookback = c.cdl3WhiteSoldiersLookback();
		RetCode retcode = c.cdl3WhiteSoldiers(0, calcLength, open, high, low,
				close, begin, length, cdl3whitesoldiersOutput);
		return cdl3whitesoldiersOutput;
	}

	public static int[] calcCDLABANDONEDBABY(double penetration) {
		cdlabandonedbabyOutput = new int[close.length];
		lookback = c.cdlAbandonedBabyLookback(penetration);
		RetCode retcode = c.cdlAbandonedBaby(0, calcLength, open, high, low,
				close, penetration, begin, length, cdlabandonedbabyOutput);
		return cdlabandonedbabyOutput;
	}

	public static int[] calcCDLADVANCEBLOCK() {
		cdladvanceblockOutput = new int[close.length];
		lookback = c.cdlAdvanceBlockLookback();
		RetCode retcode = c.cdlAdvanceBlock(0, calcLength, open, high, low,
				close, begin, length, cdladvanceblockOutput);
		return cdladvanceblockOutput;
	}

	public static int[] calcCDLBELTHOLD() {
		cdlbeltholdOutput = new int[close.length];
		lookback = c.cdlBeltHoldLookback();
		RetCode retcode = c.cdlBeltHold(0, calcLength, open, high, low, close,
				begin, length, cdlbeltholdOutput);
		return cdlbeltholdOutput;
	}

	public static int[] calcCDLBREAKAWAY() {
		cdlbreakawayOutput = new int[close.length];
		lookback = c.cdlBreakawayLookback();
		RetCode retcode = c.cdlBreakaway(0, calcLength, open, high, low, close,
				begin, length, cdlbreakawayOutput);
		return cdlbreakawayOutput;
	}

	public static int[] calcCDLCLOSINGMARUBOZU() {
		cdlclosingmarubozuOutput = new int[close.length];
		lookback = c.cdlClosingMarubozuLookback();
		RetCode retcode = c.cdlClosingMarubozu(0, calcLength, open, high, low,
				close, begin, length, cdlclosingmarubozuOutput);
		return cdlclosingmarubozuOutput;
	}

	public static int[] calcCDLCONCEALBABYSWALL() {
		cdlconcealbabyswallOutput = new int[close.length];
		lookback = c.cdlConcealBabysWallLookback();
		RetCode retcode = c.cdlConcealBabysWall(0, calcLength, open, high, low,
				close, begin, length, cdlconcealbabyswallOutput);
		return cdlconcealbabyswallOutput;
	}

	public static int[] calcCDLCOUNTERATTACK() {
		cdlcounterattackOutput = new int[close.length];
		lookback = c.cdlCounterAttackLookback();
		RetCode retcode = c.cdlCounterAttack(0, calcLength, open, high, low,
				close, begin, length, cdlcounterattackOutput);
		return cdlcounterattackOutput;
	}

	public static int[] calcCDLDARKCLOUDCOVER(double penetration) {
		cdldarkcloudcoverOutput = new int[close.length];
		lookback = c.cdlDarkCloudCoverLookback(penetration);
		RetCode retcode = c.cdlDarkCloudCover(0, calcLength, open, high, low,
				close, penetration, begin, length, cdldarkcloudcoverOutput);
		return cdldarkcloudcoverOutput;
	}

	public static int[] calcCDLDOJI() {
		cdldojiOutput = new int[close.length];
		lookback = c.cdlDojiLookback();
		RetCode retcode = c.cdlDoji(0, calcLength, open, high, low, close,
				begin, length, cdldojiOutput);
		return cdldojiOutput;
	}

	public static int[] calcCDLDOJISTAR() {
		cdldojistarOutput = new int[close.length];
		lookback = c.cdlDojiStarLookback();
		RetCode retcode = c.cdlDojiStar(0, calcLength, open, high, low, close,
				begin, length, cdldojistarOutput);
		return cdldojistarOutput;
	}

	public static int[] calcCDLDRAGONFLYDOJI() {
		cdldragonflydojiOutput = new int[close.length];
		lookback = c.cdlDragonflyDojiLookback();
		RetCode retcode = c.cdlDragonflyDoji(0, calcLength, open, high, low,
				close, begin, length, cdldragonflydojiOutput);
		return cdldragonflydojiOutput;
	}

	public static int[] calcCDLENGULFING() {
		cdlengulfingOutput = new int[close.length];
		lookback = c.cdlEngulfingLookback();
		RetCode retcode = c.cdlEngulfing(0, calcLength, open, high, low, close,
				begin, length, cdlengulfingOutput);
		return cdlengulfingOutput;
	}

	public static int[] calcCDLEVENINGDOJISTAR(double penetration) {
		cdleveningdojistarOutput = new int[close.length];
		lookback = c.cdlEveningDojiStarLookback(penetration);
		RetCode retcode = c.cdlEveningDojiStar(0, calcLength, open, high, low,
				close, penetration, begin, length, cdleveningdojistarOutput);
		return cdleveningdojistarOutput;
	}

	public static int[] calcCDLEVENINGSTAR(double penetration) {
		cdleveningstarOutput = new int[close.length];
		lookback = c.cdlEveningStarLookback(penetration);
		RetCode retcode = c.cdlEveningStar(0, calcLength, open, high, low,
				close, penetration, begin, length, cdleveningstarOutput);
		return cdleveningstarOutput;
	}

	public static int[] calcCDLGAPSIDESIDEWHITE() {
		cdlgapsidesidewhite = new int[close.length];
		lookback = c.cdlGapSideSideWhiteLookback();
		RetCode retcode = c.cdlGapSideSideWhite(0, calcLength, open, high, low,
				close, begin, length, cdlgapsidesidewhite);
		return cdlgapsidesidewhite;
	}

	public static int[] calcCDLGRAVESTONEDOJI() {
		cdlgravestonedoji = new int[close.length];
		lookback = c.cdlGravestoneDojiLookback();
		RetCode retcode = c.cdlGravestoneDoji(0, calcLength, open, high, low,
				close, begin, length, cdlgravestonedoji);
		return cdlgravestonedoji;
	}

	public static int[] calcCDLHAMMER() {
		cdlhammerOutput = new int[close.length];
		lookback = c.cdlHammerLookback();
		RetCode retcode = c.cdlHammer(0, calcLength, open, high, low, close,
				begin, length, cdlhammerOutput);
		return cdlhammerOutput;
	}

	public static int[] calcCDLHANGINGMAN() {
		cdlhangingmanOutput = new int[close.length];
		lookback = c.cdlHangingManLookback();
		RetCode retcode = c.cdlHangingMan(0, calcLength, open, high, low,
				close, begin, length, cdlhangingmanOutput);
		return cdlhangingmanOutput;
	}

	public static int[] calcCDLHARAMI() {
		cdlharamiOutput = new int[close.length];
		lookback = c.cdlHaramiLookback();
		RetCode retcode = c.cdlHarami(0, calcLength, open, high, low, close,
				begin, length, cdlharamiOutput);
		return cdlharamiOutput;
	}

	public static int[] calcCDLHARAMICROSS() {
		cdlharamicrossOutput = new int[close.length];
		lookback = c.cdlHaramiCrossLookback();
		RetCode retcode = c.cdlHaramiCross(0, calcLength, open, high, low,
				close, begin, length, cdlharamicrossOutput);
		return cdlharamicrossOutput;
	}

	public static int[] calcCDLHIGHWAVE() {
		cdlhighwaveOutput = new int[close.length];
		lookback = c.cdlHignWaveLookback();
		RetCode retcode = c.cdlHignWave(0, calcLength, open, high, low, close,
				begin, length, cdlhighwaveOutput);
		return cdlhighwaveOutput;
	}

	public static int[] calcCDLHIKKAKE() {
		cdlhikkake = new int[close.length];
		lookback = c.cdlHikkakeLookback();
		RetCode retcode = c.cdlHikkake(0, calcLength, open, high, low, close,
				begin, length, cdlhikkake);
		return cdlhikkake;
	}

	public static int[] calcCDLHIKKAKEMOD() {
		cdlhikkakemod = new int[close.length];
		lookback = c.cdlHikkakeModLookback();
		RetCode retcode = c.cdlHikkakeMod(0, calcLength, open, high, low,
				close, begin, length, cdlhikkakemod);
		return cdlhikkakemod;
	}

	public static int[] calcCDLHOMINGPIGEON() {
		cdlhomingpigeon = new int[close.length];
		lookback = c.cdlHomingPigeonLookback();
		RetCode retcode = c.cdlHomingPigeon(0, calcLength, open, high, low,
				close, begin, length, cdlhomingpigeon);
		return cdlhomingpigeon;
	}

	public static int[] calcCDLIDENTICAL3CROWS() {
		cdlidentical3crows = new int[close.length];
		lookback = c.cdlIdentical3CrowsLookback();
		RetCode retcode = c.cdlIdentical3Crows(0, calcLength, open, high, low,
				close, begin, length, cdlidentical3crows);
		return cdlidentical3crows;
	}

	public static int[] calcCDLINNECK() {
		cdlinneck = new int[close.length];
		lookback = c.cdlInNeckLookback();
		RetCode retcode = c.cdlInNeck(0, calcLength, open, high, low, close,
				begin, length, cdlinneck);
		return cdlinneck;
	}

	public static int[] calcCDLINVERTEDHAMMER() {
		cdlinvertedhammer = new int[close.length];
		lookback = c.cdlInvertedHammerLookback();
		RetCode retcode = c.cdlInvertedHammer(0, calcLength, open, high, low,
				close, begin, length, cdlinvertedhammer);
		return cdlinvertedhammer;
	}

	public static int[] calcCDLKICKING() {
		cdlkicking = new int[close.length];
		lookback = c.cdlKickingLookback();
		RetCode retcode = c.cdlKicking(0, calcLength, open, high, low, close,
				begin, length, cdlkicking);
		return cdlkicking;
	}

	public static int[] calcCDLKICKINGBYLENGTH() {
		cdlkickingbylength = new int[close.length];
		lookback = c.cdlKickingByLengthLookback();
		RetCode retcode = c.cdlKickingByLength(0, calcLength, open, high, low,
				close, begin, length, cdlkickingbylength);
		return cdlkickingbylength;
	}

	public static int[] calcCDLLADDERBOTTOM() {
		cdlladderbottom = new int[close.length];
		lookback = c.cdlLadderBottomLookback();
		RetCode retcode = c.cdlLadderBottom(0, calcLength, open, high, low,
				close, begin, length, cdlladderbottom);
		return cdlladderbottom;
	}

	public static int[] calcCDLLONGLEGGEDDOJI() {
		cdllongleggeddoji = new int[close.length];
		lookback = c.cdlLongLeggedDojiLookback();
		RetCode retcode = c.cdlLongLeggedDoji(0, calcLength, open, high, low,
				close, begin, length, cdllongleggeddoji);
		return cdllongleggeddoji;
	}

	public static int[] calcCDLLONGLINE() {
		cdllongline = new int[close.length];
		lookback = c.cdlLongLineLookback();
		RetCode retcode = c.cdlLongLine(0, calcLength, open, high, low, close,
				begin, length, cdllongline);
		return cdllongline;
	}

	public static int[] calcCDLMARUBOZU() {
		cdlmarubozu = new int[close.length];
		lookback = c.cdlMarubozuLookback();
		RetCode retcode = c.cdlMarubozu(0, calcLength, open, high, low, close,
				begin, length, cdlmarubozu);
		return cdlmarubozu;
	}

	public static int[] calcCDLMATCHINGLOW() {
		cdlmatchinglow = new int[close.length];
		lookback = c.cdlMatchingLowLookback();
		RetCode retcode = c.cdlMatchingLow(0, calcLength, open, high, low,
				close, begin, length, cdlmatchinglow);
		return cdlmatchinglow;
	}

	public static int[] calcCDLMATHOLD(double penetration) {
		cdlmathold = new int[close.length];
		lookback = c.cdlMatHoldLookback(penetration);
		RetCode retcode = c.cdlMatHold(0, calcLength, open, high, low, close,
				penetration, begin, length, cdlmathold);
		return cdlmathold;
	}

	public static int[] calcCDLMORNINGDOJISTAR(double penetration) {
		cdlmorningdojistar = new int[close.length];
		lookback = c.cdlMorningDojiStarLookback(penetration);
		RetCode retcode = c.cdlMorningDojiStar(0, calcLength, open, high, low,
				close, penetration, begin, length, cdlmorningdojistar);
		return cdlmorningdojistar;
	}

	public static int[] calcCDLMORNINGSTAR(double penetration) {
		cdlmorningstar = new int[close.length];
		lookback = c.cdlMorningStarLookback(penetration);
		RetCode retcode = c.cdlMorningStar(0, calcLength, open, high, low,
				close, penetration, begin, length, cdlmorningstar);
		return cdlmorningstar;
	}

	public static int[] calcCDLONNECK() {
		cdlonneck = new int[close.length];
		lookback = c.cdlOnNeckLookback();
		RetCode retcode = c.cdlOnNeck(0, calcLength, open, high, low, close,
				begin, length, cdlonneck);
		return cdlonneck;
	}

	public static int[] calcCDLPIERCING() {
		cdlpiercing = new int[close.length];
		lookback = c.cdlPiercingLookback();
		RetCode retcode = c.cdlPiercing(0, calcLength, open, high, low, close,
				begin, length, cdlpiercing);
		return cdlpiercing;
	}

	public static int[] calcCDLRICKSHAWMAN() {
		cdlrickshawman = new int[close.length];
		lookback = c.cdlRickshawManLookback();
		RetCode retcode = c.cdlRickshawMan(0, calcLength, open, high, low,
				close, begin, length, cdlrickshawman);
		return cdlrickshawman;
	}

	public static int[] calcCDLRISEFALL3METHODS() {
		cdlrisefall3methods = new int[close.length];
		lookback = c.cdlRiseFall3MethodsLookback();
		RetCode retcode = c.cdlRiseFall3Methods(0, calcLength, open, high, low,
				close, begin, length, cdlrisefall3methods);
		return cdlrisefall3methods;
	}

	public static int[] calcCDLSEPARATINGLINES() {
		cdlseparatinglines = new int[close.length];
		lookback = c.cdlSeperatingLinesLookback();
		RetCode retcode = c.cdlSeperatingLines(0, calcLength, open, high, low,
				close, begin, length, cdlseparatinglines);
		return cdlseparatinglines;
	}

	public static int[] calcCDLSHOOTINGSTAR() {
		cdlshootingstar = new int[close.length];
		lookback = c.cdlShootingStarLookback();
		RetCode retcode = c.cdlShootingStar(0, calcLength, open, high, low,
				close, begin, length, cdlshootingstar);
		return cdlshootingstar;
	}

	public static int[] calcCDLSHORTLINE() {
		cdlshortline = new int[close.length];
		lookback = c.cdlShortLineLookback();
		RetCode retcode = c.cdlShortLine(0, calcLength, open, high, low, close,
				begin, length, cdlshortline);
		return cdlshortline;
	}

	public static int[] calcCDLSPINNINGTOP() {
		cdlspinningtop = new int[close.length];
		lookback = c.cdlSpinningTopLookback();
		RetCode retcode = c.cdlSpinningTop(0, calcLength, open, high, low,
				close, begin, length, cdlspinningtop);
		return cdlspinningtop;
	}

	public static int[] calcCDLSTALLEDPATTERN() {
		cdlstalledpattern = new int[close.length];
		lookback = c.cdlStalledPatternLookback();
		RetCode retcode = c.cdlStalledPattern(0, calcLength, open, high, low,
				close, begin, length, cdlstalledpattern);
		return cdlstalledpattern;
	}

	public static int[] calcCDLSTICKSANDWICH() {
		cdlsticksandwich = new int[close.length];
		lookback = c.cdlStickSandwhichLookback();
		RetCode retcode = c.cdlStickSandwhich(0, calcLength, open, high, low,
				close, begin, length, cdlsticksandwich);
		return cdlsticksandwich;
	}

	public static int[] calcCDLTAKURI() {
		cdltakuri = new int[close.length];
		lookback = c.cdlTakuriLookback();
		RetCode retcode = c.cdlTakuri(0, calcLength, open, high, low, close,
				begin, length, cdltakuri);
		return cdltakuri;
	}

	public static int[] calcCDLTASUKIGAP() {
		cdltasukigap = new int[close.length];
		lookback = c.cdlTasukiGapLookback();
		RetCode retcode = c.cdlTasukiGap(0, calcLength, open, high, low, close,
				begin, length, cdltasukigap);
		return cdltasukigap;
	}

	public static int[] calcCDLTHRUSTING() {
		cdlthrusting = new int[close.length];
		lookback = c.cdlThrustingLookback();
		RetCode retcode = c.cdlThrusting(0, calcLength, open, high, low, close,
				begin, length, cdlthrusting);
		return cdlthrusting;
	}

	public static int[] calcCDLTRISTAR() {
		cdltristar = new int[close.length];
		lookback = c.cdlTristarLookback();
		RetCode retcode = c.cdlTristar(0, calcLength, open, high, low, close,
				begin, length, cdltristar);
		return cdltristar;
	}

	public static int[] calcCDLUNIQUE3RIVER() {
		cdlunique3river = new int[close.length];
		lookback = c.cdlUnique3RiverLookback();
		RetCode retcode = c.cdlUnique3River(0, calcLength, open, high, low,
				close, begin, length, cdlunique3river);
		return cdlunique3river;
	}

	public static int[] calcCDLUPSIDEGAP2CROW() {
		cdlupsidegap2crow = new int[close.length];
		lookback = c.cdlUpsideGap2CrowsLookback();
		RetCode retcode = c.cdlUpsideGap2Crows(0, calcLength, open, high, low,
				close, begin, length, cdlupsidegap2crow);
		return cdlupsidegap2crow;
	}

	public static int[] calcCDLXSIDEGAP3METHODS() {
		cdlxsidegap3methods = new int[close.length];
		lookback = c.cdlXSideGap3MethodsLookback();
		RetCode retcode = c.cdlXSideGap3Methods(0, calcLength, open, high, low,
				close, begin, length, cdlxsidegap3methods);
		return cdlxsidegap3methods;
	}

	public static double[] calcCMO(int period) {
		cmo = new double[close.length];
		lookback = c.cmoLookback(period);
		RetCode retcode = c.cmo(0, calcLength, close, period, begin, length,
				cmo);
		return cmo;
	}

	// public static void calcCORREL(int period){
	// correl = new double[close.length];
	// lookback = c.correlLookback(period);
	// RetCode retcode = c.correl(0, calcLength, close, inReal1, period, begin,
	// length, correl);
	// return correl;
	// }

	public static double[] calcDEMA(int period) {
		dema = new double[close.length];
		lookback = c.demaLookback(period);
		RetCode retcode = c.dema(0, period, close, period, begin, length, dema);
		return dema;
	}

	public static double[] calcDX(int period) {
		dx = new double[close.length];
		lookback = c.dxLookback(period);
		RetCode retcode = c.dx(0, calcLength, high, low, close, period, begin,
				length, dx);
		return dx;
	}

	public static double[] calcEMA(int period) {
		lookback = 0;
		emaOutput = new double[close.length];
		lookback = c.emaLookback(period);
		RetCode retcode = c.ema(0, close.length - 1, close, period, begin, length, emaOutput);
		return emaOutput;
	}

	public static double[] calcHTDCPERIOD() {
		htdcperiod = new double[close.length];
		lookback = c.htDcPeriodLookback();
		RetCode retcode = c.htDcPeriod(0, calcLength, close, begin, length,
				htdcperiod);
		return htdcperiod;
	}

	public static double[] calcHTDCPHASE() {
		htdcphase = new double[close.length];
		lookback = c.htDcPhaseLookback();
		RetCode retcode = c.htDcPhase(0, calcLength, close, begin, length,
				htdcphase);
		return htdcphase;
	}

	public static void calcHTPHASOR() {
		htphasor_inphase = new double[close.length];
		htphasor_outquad = new double[close.length];
		lookback = c.htPhasorLookback();
		RetCode retcode = c.htPhasor(0, calcLength, close, begin, length,
				htphasor_inphase, htphasor_outquad);
	}

	public static void calcHTSINE() {
		htsine = new double[close.length];
		htleadsine = new double[close.length];
		lookback = c.htSineLookback();
		RetCode retcode = c.htSine(0, calcLength, close, begin, length, htsine,
				htleadsine);
	}

	public static double[] calcHTTRENDLINE() {
		httrendline = new double[close.length];
		lookback = c.htTrendlineLookback();
		RetCode retcode = c.htTrendline(indCount, calcLength, close, begin,
				length, httrendline);
		return httrendline;
	}

	public static int[] calcHTTRENDMODE() {
		httrendmode = new int[close.length];
		lookback = c.htTrendModeLookback();
		RetCode retcode = c.htTrendMode(0, calcLength, close, begin, length,
				httrendmode);
		return httrendmode;
	}

	public static double[] calcKAMA(int period) {
		kama = new double[close.length];
		lookback = c.kamaLookback(period);
		RetCode retcode = c.kama(0, calcLength, close, period, begin, length,
				kama);
		return kama;
	}

	public static double[] calcLINEARREG(int period) {
		linearreg = new double[close.length];
		lookback = c.linearRegLookback(period);
		RetCode retcode = c.linearReg(0, calcLength, close, period, begin,
				length, linearreg);
		return linearreg;
	}

	public static double[] calcLINEARREG_ANGLE(int period) {
		lrregangle = new double[close.length];
		lookback = c.linearRegAngleLookback(period);
		RetCode retcode = c.linearRegAngle(0, calcLength, close, period, begin,
				length, lrregangle);
		return lrregangle;
	}

	public static double[] calcLINEARREG_INTERCEPT(int period) {
		lrregintercept = new double[close.length];
		lookback = c.linearRegInterceptLookback(period);
		RetCode retcode = c.linearRegIntercept(0, calcLength, close, period,
				begin, length, lrregintercept);
		return lrregintercept;
	}

	public static double[] calcLINEARREG_SLOPE(int period) {
		lrregslope = new double[close.length];
		lookback = c.linearRegSlopeLookback(period);
		RetCode retcode = c.linearRegSlope(0, calcLength, close, period, begin,
				length, lrregslope);
		return lrregslope;
	}

	public static double[] calcMACD(int fast, int slow, int smooth) {
		lookback = c.macdLookback(fast, slow, smooth);
		macdOutput = new double[close.length];
		macdSignal = new double[close.length];
		macdHist = new double[close.length];
		lookback = c.macdLookback(fast, slow, smooth);
		c.macd(0, close.length - 1, close, fast, slow, smooth, begin, length,
				macdOutput, macdSignal, macdHist);
		return macdOutput;
	}

	/**
	 * @param fast
	 *            = fast period
	 * @param slow
	 * @param smooth
	 * @param flag1
	 *            true = Ema, false = Sma
	 * @param flag2
	 * @param flag3
	 * @return
	 */
	public static double[] calcMACDEXT(int fast, int slow, int smooth,
			boolean flag1, boolean flag2, boolean flag3) {
		macd = new double[close.length];
		macdS = new double[close.length];
		macdH = new double[close.length];
		MAType optInFastMAType = MAType.Sma;
		MAType optInSlowMAType = MAType.Sma;
		MAType optInSignalMAType = MAType.Sma;
		if (flag1) {
			optInFastMAType = MAType.Ema;
		} else if (flag2) {
			optInSlowMAType = MAType.Ema;
		} else if (flag3) {
			optInSignalMAType = MAType.Ema;
		}
		lookback = c.macdExtLookback(fast, optInFastMAType, slow,
				optInSlowMAType, smooth, optInSignalMAType);
		RetCode retcode = c.macdExt(0, calcLength, close, fast,
				optInFastMAType, slow, optInSlowMAType, smooth,
				optInSignalMAType, begin, length, macd, macdS, macdH);
		return macd;
	}

	public static double[] calcMACDFIX(int period) {
		macd = new double[close.length];
		macdS = new double[close.length];
		macdH = new double[close.length];
		lookback = c.macdFixLookback(period);
		RetCode retcode = c.macdFix(0, calcLength, close, period, begin,
				length, macd, macdS, macdH);
		return macd;
	}

	public static void calcMAMA(double fast, double slow) {
		mama = new double[close.length];
		fama = new double[close.length];
		lookback = c.mamaLookback(fast, slow);
		RetCode retcode = c.mama(0, calcLength, close, fast, slow, begin,
				length, mama, fama);
	}

	public static double[] calcMAX(int period) {
		max = new double[close.length];
		lookback = c.maxLookback(period);
		RetCode retcode = c.max(0, calcLength, close, period, begin, length,
				max);
		return max;
	}

	public static int[] calcMAXINDEX(int period) {
		maxindex = new int[close.length];
		lookback = c.maxIndexLookback(period);
		RetCode retcode = c.maxIndex(0, calcLength, close, period, begin,
				length, maxindex);
		return maxindex;
	}

	public static double[] calcMEDPRICE() {
		medprice = new double[close.length];
		lookback = c.medPriceLookback();
		RetCode retcode = c.medPrice(0, calcLength, high, low, begin, length,
				medprice);
		return medprice;
	}

	public static double[] calcMFI(int period) {
		mfi = new double[close.length];
		lookback = c.mfiLookback(period);
		RetCode retcode = c.mfi(0, calcLength, high, low, close, volume,
				period, begin, length, mfi);
		return mfi;
	}

	public static double[] calcMIDPOINT(int period) {
		midpoint = new double[close.length];
		lookback = c.midPointLookback(period);
		RetCode retcode = c.midPoint(0, calcLength, close, period, begin,
				length, midpoint);
		return midpoint;
	}

	public static double[] calcMIDPRICE(int period) {
		midprice = new double[close.length];
		lookback = c.midPriceLookback(period);
		RetCode retcode = c.midPrice(0, calcLength, high, low, period, begin,
				length, midprice);
		return midprice;
	}

	public static double[] calcMIN(int period) {
		min = new double[close.length];
		lookback = c.minLookback(period);
		RetCode retcode = c.min(0, calcLength, close, period, begin, length,
				min);
		return min;
	}

	public static int[] calcMININDEX(int period) {
		minindex = new int[close.length];
		lookback = c.minIndexLookback(period);
		RetCode retcode = c.minIndex(0, calcLength, close, period, begin,
				length, minindex);
		return minindex;
	}

	public static void calcMINMAX(int period) {
		mmmin = new double[close.length];
		mmmax = new double[close.length];
		lookback = c.minMaxLookback(period);
		RetCode retcode = c.minMax(0, calcLength, close, period, begin, length,
				mmmin, mmmax);
	}

	public static void calcMINMAXINDEX(int period) {
		mmminindex = new int[close.length];
		mmmaxindex = new int[close.length];
		lookback = c.minMaxIndexLookback(period);
		RetCode retcode = c.minMaxIndex(0, calcLength, close, period, begin,
				length, mmminindex, mmmaxindex);
	}

	public static double[] calcMINUSDI(int period) {
		minusdi = new double[close.length];
		lookback = c.minusDILookback(period);
		RetCode retcode = c.minusDI(0, calcLength, high, low, close, period,
				begin, length, minusdi);
		return minusdi;
	}

	public static double[] calcMINUSDM(int period) {
		minusdm = new double[close.length];
		lookback = c.minusDMLookback(period);
		RetCode retcode = c.minusDM(0, calcLength, high, low, period, begin,
				length, minusdm);
		return minusdm;
	}

	public static double[] calcMOM(int period) {
		mom = new double[close.length];
		lookback = c.momLookback(period);
		RetCode retcode = c.mom(0, calcLength, close, period, begin, length,
				mom);
		return mom;
	}

	public static double[] calcNATR(int period) {
		natr = new double[close.length];
		lookback = c.natrLookback(period);
		RetCode retcode = c.natr(0, calcLength, high, low, close, period,
				begin, length, natr);
		return natr;
	}

	public static double[] calcOBV() {
		obv = new double[close.length];
		lookback = c.obvLookback();
		RetCode retcode = c.obv(0, calcLength, close, volume, begin, length,
				obv);
		return obv;
	}

	public static double[] calcPLUSDI(int period) {
		plusdi = new double[close.length];
		lookback = c.plusDILookback(period);
		RetCode retcode = c.plusDI(0, calcLength, high, low, close, period,
				begin, length, plusdi);
		return plusdi;
	}

	public static double[] calcPLUSDM(int period) {
		plusdm = new double[close.length];
		lookback = c.plusDMLookback(period);
		RetCode retcode = c.plusDM(0, calcLength, high, low, period, begin,
				length, plusdm);
		return plusdm;
	}

	public static double[] calcPPO(int fast, int slow) {
		ppo = new double[close.length];
		lookback = c.ppoLookback(fast, slow, MAType.Sma);
		RetCode retcode = c.ppo(0, calcLength, close, fast, slow, MAType.Sma,
				begin, length, ppo);
		return ppo;
	}

	public static double[] calcROC(int period) {
		roc = new double[close.length];
		lookback = c.rocLookback(period);
		RetCode retcode = c.roc(0, calcLength, close, period, begin, length,
				roc);
		return roc;
	}

	public static double[] calcROCP(int period) {
		rocp = new double[close.length];
		lookback = c.rocPLookback(period);
		RetCode retcode = c.rocP(0, calcLength, close, period, begin, length,
				rocp);
		return rocp;
	}

	public static double[] calcROCR(int period) {
		rocr = new double[close.length];
		lookback = c.rocRLookback(period);
		RetCode retcode = c.rocR(0, calcLength, close, period, begin, length,
				rocr);
		return rocr;
	}

	public static double[] calcROCR100(int period) {
		rocr100 = new double[close.length];
		lookback = c.rocR100Lookback(period);
		RetCode retcode = c.rocR100(0, calcLength, close, period, begin,
				length, rocr100);
		return rocr100;
	}

	public static double[] calcSMA(int period) {
		lookback = 0;
		smaOutput = new double[close.length];
		lookback = c.smaLookback(period);
		c.sma(0, close.length - 1, close, period, begin, length, smaOutput);
		return smaOutput;
	}

	public static double[] calcRSI(int period, int smooth) {
		lookback = 0;
		rsiOutput = new double[close.length];
		lookback = c.rsiLookback(period);
		c.rsi(0, close.length - 1, close, period, begin, length, rsiOutput);
		return rsiOutput;
	}

	public static double[] calcSAR(double acceleration, double maximum) {
		sar = new double[close.length];
		lookback = c.sarLookback(acceleration, maximum);
		RetCode retcode = c.sar(0, calcLength, high, low, acceleration,
				maximum, begin, length, sar);
		return sar;
	}

	public static double[] calcSAREXT(int startvalue, int offsetonreverse,
			double accelerationinitlong, double accelerationlong,
			double accelerationmaxlong, double accelerationinitshort,
			double accelerationshort, double accelerrationmaxshort) {
		sarext = new double[close.length];
		lookback = c
				.sarExtLookback(startvalue, offsetonreverse,
						accelerationinitlong, accelerationlong,
						accelerationmaxlong, accelerationinitshort,
						accelerationshort, accelerrationmaxshort);
		RetCode retcode = c.sarExt(0, calcLength, high, low, startvalue,
				offsetonreverse, accelerationinitlong, accelerationlong,
				accelerationmaxlong, accelerationinitshort, accelerationshort,
				accelerrationmaxshort, begin, length, sarext);
		return sarext;
	}

	public static double[] calcSTDDEV(int period, double nbdev) {
		stddev = new double[close.length];
		lookback = c.stdDevLookback(period, nbdev);
		RetCode retcode = c.stdDev(0, calcLength, close, period, nbdev, begin,
				length, stddev);
		return stddev;
	}

	public static void calcSTOCH(int fastk, int slowk, int slowd) {
		stochslowk = new double[close.length];
		stochslowd = new double[close.length];
		lookback = c.stochLookback(fastk, slowk, MAType.Sma, slowd, MAType.Sma);
		RetCode retcode = c.stoch(0, calcLength, high, low, close, fastk,
				slowk, MAType.Sma, slowd, MAType.Sma, begin, length,
				stochslowk, stochslowd);
	}

	public static void calcSTOCHF(int fastk, int fastd) {
		stochfastk = new double[close.length];
		stochfastd = new double[close.length];
		lookback = c.stochFLookback(fastk, fastd, MAType.Sma);
		RetCode retcode = c.stochF(0, calcLength, high, low, close, fastk,
				fastd, MAType.Sma, begin, length, stochfastk, stochfastd);
	}

	public static void calcSTOCHRSI(int period, int fastk, int fastd) {
		strsifastk = new double[close.length];
		strsifastd = new double[close.length];
		lookback = c.stochRsiLookback(period, fastk, fastd, MAType.Sma);
		RetCode retcode = c.stochRsi(0, calcLength, close, period, fastk,
				fastd, MAType.Sma, begin, length, strsifastk, strsifastd);
	}

	public static double[] calcT3(int period, double vfactor) {
		t3 = new double[close.length];
		lookback = c.t3Lookback(period, vfactor);
		RetCode retcode = c.t3(0, calcLength, close, period, vfactor, begin,
				length, t3);
		return t3;
	}

	public static double[] calcTEMA(int period) {
		tema = new double[close.length];
		lookback = c.temaLookback(period);
		RetCode retcode = c.tema(0, calcLength, close, period, begin, length,
				tema);
		return tema;
	}

	public static double[] calcTRANGE() {
		trange = new double[close.length];
		lookback = c.trueRangeLookback();
		RetCode retcode = c.trueRange(0, calcLength, high, low, close, begin,
				length, trange);
		return trange;
	}

	public static double[] calcTRIMA(int period) {
		trima = new double[close.length];
		lookback = c.trimaLookback(period);
		RetCode retcode = c.trima(0, calcLength, close, period, begin, length,
				trima);
		return trima;
	}

	public static double[] calcTRIX(int period) {
		trix = new double[close.length];
		lookback = c.trixLookback(period);
		RetCode retcode = c.trix(0, calcLength, close, period, begin, length,
				trix);
		return trix;
	}

	public static double[] calcTSF(int period) {
		tsf = new double[close.length];
		lookback = c.tsfLookback(period);
		RetCode retcode = c.tsf(0, calcLength, close, period, begin, length,
				tsf);
		return tsf;
	}

	public static double[] calcTYPPRICE() {
		typprice = new double[close.length];
		lookback = c.typPriceLookback();
		RetCode retcode = c.typPrice(0, calcLength, high, low, close, begin,
				length, typprice);
		return typprice;
	}

	public static double[] calcULTOSC(int period1, int period2, int period3) {
		ultosc = new double[close.length];
		lookback = c.ultOscLookback(period1, period2, period3);
		RetCode retcode = c.ultOsc(0, calcLength, high, low, close, period1,
				period2, period3, begin, length, ultosc);
		return ultosc;
	}

	public static double[] calcVAR(int period, int nbdev) {
		var = new double[close.length];
		lookback = c.varianceLookback(period, nbdev);
		RetCode retcode = c.variance(0, calcLength, close, period, nbdev,
				begin, length, var);
		return var;
	}

	public static double[] calcWCLPRICE() {
		wclprice = new double[close.length];
		lookback = c.wclPriceLookback();
		RetCode retcode = c.wclPrice(0, calcLength, high, low, close, begin,
				length, wclprice);
		return wclprice;
	}

	public static double[] calcWILLR(int period) {
		willr = new double[close.length];
		lookback = c.willRLookback(period);
		RetCode retcode = c.willR(0, calcLength, high, low, close, period,
				begin, length, willr);
		return willr;
	}

	public static double[] calcWMA(int period) {
		wma = new double[close.length];
		lookback = c.wmaLookback(period);
		RetCode retcode = c.wma(0, calcLength, close, period, begin, length,
				wma);
		return wma;
	}

	public static void calcPivot() {
		for (int i = 0; i < close.length; i++) {
			Pivot.getPivot(i, high[i], low[i], close[i]);
			Pivot.getR1(i, low[i]);
			Pivot.getR2(i, high[i], low[i]);
			Pivot.getR3(i, high[i], low[i]);
			Pivot.getS1(i, high[i]);
			Pivot.getS2(i, high[i], low[i]);
			Pivot.getS3(i, high[i], low[i]);
		}
		writeExt(Pivot.getPp_pp_l());
		writeExt(Pivot.getPp_s1_l());
		writeExt(Pivot.getPp_r1_l());
		writeExt(Pivot.getPp_s2_l());
		writeExt(Pivot.getPp_r2_l());
		writeExt(Pivot.getPp_s3_l());
		writeExt(Pivot.getPp_r3_l());

	}

	public static void calcRVI(int period) {
		for (int i = 0; i < CsvImportService.stockList.size(); i++) {
			RVI.Open.add(CsvImportService.stockList.get(i).getOpen());
			RVI.Close.add(CsvImportService.stockList.get(i).getClose());
			RVI.High.add(CsvImportService.stockList.get(i).getHigh());
			RVI.Low.add(CsvImportService.stockList.get(i).getLow());
		}
		// for (int j = 0; j < size_diff; j++){
		// RVI.setRvi_l(0.);
		// RVI.setRvi_diff_l(0.);
		// }
		for (int i = 0; i < close.length; i++) {
			RVI.getRVI(i, period);
		}
		writeExt(RVI.getRvi_l());
		writeExt(RVI.getRvi_diff_l());
	}

	public static void calcZZ(double threshold) {
		ZigZag.CalculateZigZag(threshold);
		writeExt(ZigZag.getZz_point_l());
		writeExt(ZigZag.getZz_value_l());
	}

	public static int getIndCount() {
		return indCount;
	}

	public static void setIndCount(int indCount) {
		TALibCalculationService.indCount = indCount;
	}
}
