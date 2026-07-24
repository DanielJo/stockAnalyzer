package io.github.danieljo.stockanalyzer.indicator;

import io.github.danieljo.stockanalyzer.cli.AnalysisRequest;

/**
 * Catalog of every indicator already wired into {@link TALibCalculationService} (plus Pivot,
 * RVI and ZigZag), each keyed by a stable numeric id - e.g. via the CLI's
 * {@code indicators_1,3,5} argument, or later the equivalent REST query/body param - instead of
 * spelling out {@code ta_<name>_<params>} (or {@code pivot}/{@code rvi_<period>}/
 * {@code zz_<threshold>}) individually.
 * <p>
 * Each entry runs with fixed default parameters - mostly the traditional/textbook values that
 * were already documented as comments in {@code TALibCalculationService}'s dispatch table (e.g.
 * RSI's "_(14_3)", MACD's "_(12_26_9)"). Where no default was documented there (EMA, SMA, RVI,
 * ZigZag), a common textbook value was chosen instead - noted per entry below. This is purely an
 * additional, more compact selection mechanism on top of the existing dispatch - the verbose
 * {@code ta_}/{@code pivot}/{@code rvi_}/{@code zz_} syntax is still there for anyone who needs
 * a non-default parameter.
 * <p>
 * Most entries just forward to {@link TALibCalculationService#methodCall} with a synthesized
 * {@code ta_<name>_<params>} argument array - identical to what the CLI already does, just
 * built programmatically instead of parsed from a raw string. Pivot/RVI/ZigZag don't go through
 * {@code methodCall} (they're their own top-level CLI tokens), so they're special-cased in
 * {@link #calculate}.
 */
public enum IndicatorType {

	SMA(1, "Simple Moving Average", "sma", "20"),
	RSI(2, "Relative Strength Index", "rsi", "14", "3"),
	MACD(3, "Moving Average Convergence/Divergence", "macd", "12", "26", "9"),
	BOLLINGER_BANDS(4, "Bollinger Bands", "bbands", "5", "2", "2"),

	EMA(5, "Exponential Moving Average", "ema", "9"),
	AD(6, "Chaikin A/D Line", "ad"),
	ADOSC(7, "Chaikin A/D Oscillator", "adosc", "3", "10"),
	ADX(8, "Average Directional Movement Index", "adx", "14"),
	ADXR(9, "Average Directional Movement Index Rating", "adxr", "14"),
	APO(10, "Absolute Price Oscillator", "apo", "12", "26"),
	AROON(11, "Aroon", "aroon", "14"),
	AROONOSC(12, "Aroon Oscillator", "aroonosc", "14"),
	ATR(13, "Average True Range", "atr", "14"),
	AVGPRICE(14, "Average Price", "avgprice"),
	BOP(15, "Balance Of Power", "bop"),
	CCI(16, "Commodity Channel Index", "cci", "14"),

	CDL2CROWS(17, "Two Crows", "cdl2crows"),
	CDL3BLACKCROWS(18, "Three Black Crows", "cdl3blackcrows"),
	CDL3INSIDE(19, "Three Inside Up/Down", "cdl3inside"),
	CDL3LINESTRIKE(20, "Three-Line Strike", "cdl3linestrike"),
	CDL3OUTSIDE(21, "Three Outside Up/Down", "cdl3outside"),
	CDL3STARSINSOUTH(22, "Three Stars In The South", "cdl3starsinsouth"),
	CDL3WHITESOLDIERS(23, "Three Advancing White Soldiers", "cdl3whitesoldiers"),
	CDLABANDONEDBABY(24, "Abandoned Baby", "cdlabandonedbaby", "0.3"),
	CDLADVANCEDBLOCK(25, "Advance Block", "cdladvancedblock"),
	CDLBELTHOLD(26, "Belt-hold", "cdlbelthold"),
	CDLBREAKAWAY(27, "Breakaway", "cdlbreakaway"),
	CDLCLOSINGMARUBOZU(28, "Closing Marubozu", "cdlclosingmarubozu"),
	CDLCONCEALBABYSWALL(29, "Concealing Baby Swallow", "cdlconcealbabysw"),
	CDLCOUNTERATTACK(30, "Counterattack", "cdlcounterattack"),
	CDLDARKCLOUDCOVER(31, "Dark Cloud Cover", "cdldarkcloudcover", "0.5"),
	CDLDOJI(32, "Doji", "cdldoji"),
	CDLDOJISTAR(33, "Doji Star", "cdldojistar"),
	CDLDRAGONFLYDOJI(34, "Dragonfly Doji", "cdldragonflydoji"),
	CDLENGULFING(35, "Engulfing Pattern", "cdlengulfing"),
	CDLEVENINGDOJISTAR(36, "Evening Doji Star", "cdleveningdojistar", "0.3"),
	CDLEVENINGSTAR(37, "Evening Star", "cdleveningstar", "0.3"),
	CDLGAPSIDESIDEWHITE(38, "Up/Down-gap Side-by-Side White Lines", "cdlgapsidesidewhite"),
	CDLGRAVESTONEDOJI(39, "Gravestone Doji", "cdlgravestonedoji"),
	CDLHAMMER(40, "Hammer", "cdlhammer"),
	CDLHANGINGMAN(41, "Hanging Man", "cdlhangingman"),
	CDLHARAMI(42, "Harami Pattern", "cdlharami"),
	CDLHARAMICROSS(43, "Harami Cross Pattern", "cdlharamicross"),
	CDLHIGHWAVE(44, "High-Wave Candle", "cdlhighwave"),
	CDLHIKKAKE(45, "Hikkake Pattern", "cdlhikkake"),
	CDLHIKKAKEMOD(46, "Modified Hikkake Pattern", "cdlhikkakemod"),
	CDLHOMINGPIGEON(47, "Homing Pigeon", "cdlhomingpigeon"),
	CDLIDENTICAL3CROWS(48, "Identical Three Crows", "cdlidentical3crows"),
	CDLINNECK(49, "In-Neck Pattern", "cdlinneck"),
	CDLINVERTEDHAMMER(50, "Inverted Hammer", "cdlinvertedhammer"),
	CDLKICKING(51, "Kicking", "cdlkicking"),
	CDLKICKINGBYLENGTH(52, "Kicking (by longer marubozu)", "cdlkickingbylength"),
	CDLLADDERBOTTOM(53, "Ladder Bottom", "cdlladderbottom"),
	CDLLONGLEGGEDDOJI(54, "Long Legged Doji", "cdllongleggeddoji"),
	CDLLONGLINE(55, "Long Line Candle", "cdllongline"),
	CDLMARUBOZU(56, "Marubozu", "cdlmarubozu"),
	CDLMATCHINGLOW(57, "Matching Low", "cdlmatchinglow"),
	CDLMATHOLD(58, "Mat Hold", "cdlmathold", "0.5"),
	CDLMORNINGDOJISTAR(59, "Morning Doji Star", "cdlmorningdojistar", "0.3"),
	CDLMORNINGSTAR(60, "Morning Star", "cdlmorningstar", "0.3"),
	CDLONNECK(61, "On-Neck Pattern", "cdlonneck"),
	CDLPIERCING(62, "Piercing Pattern", "cdlpiercing"),
	CDLRICKSHAWMAN(63, "Rickshaw Man", "cdlrickshawman"),
	CDLRISEFALL3METHODS(64, "Rising/Falling Three Methods", "cdlrisefall3methods"),
	CDLSEPARATINGLINES(65, "Separating Lines", "cdlseparatinglines"),
	CDLSHOOTINGSTAR(66, "Shooting Star", "cdlshootingstar"),
	CDLSHORTLINE(67, "Short Line Candle", "cdlshortline"),
	CDLSPINNINGTOP(68, "Spinning Top", "cdlspinningtop"),
	CDLSTALLEDPATTERN(69, "Stalled Pattern", "cdlstalledpattern"),
	CDLSTICKSANDWICH(70, "Stick Sandwich", "cdlsticksandwich"),
	CDLTAKURI(71, "Takuri", "cdltakuri"),
	CDLTASUKIGAP(72, "Tasuki Gap", "cdltasukigap"),
	CDLTHRUSTING(73, "Thrusting Pattern", "cdlthrusting"),
	CDLTRISTAR(74, "Tristar Pattern", "cdltristar"),
	CDLUNIQUE3RIVER(75, "Unique 3 River", "cdlunique3river"),
	CDLUPSIDEGAP2CROWS(76, "Upside Gap Two Crows", "cdlupsidegap2crows"),
	CDLXSIDEGAP3METHODS(77, "Upside/Downside Gap Three Methods", "cdlxsidegap3methods"),

	CMO(78, "Chande Momentum Oscillator", "cmo", "14"),
	DEMA(79, "Double Exponential Moving Average", "dema", "30"),
	DX(80, "Directional Movement Index", "dx", "14"),
	HTDCPERIOD(81, "Hilbert Transform - Dominant Cycle Period", "htdcperiod"),
	HTDCPHASE(82, "Hilbert Transform - Dominant Cycle Phase", "htdcphase"),
	HTPHASOR(83, "Hilbert Transform - Phasor Components", "htphasor"),
	HTSINE(84, "Hilbert Transform - SineWave", "htsine"),
	HTTRENDLINE(85, "Hilbert Transform - Instantaneous Trendline", "httrendline"),
	HTTRENDMODE(86, "Hilbert Transform - Trend vs Cycle Mode", "httrendmode"),
	KAMA(87, "Kaufman Adaptive Moving Average", "kama", "30"),
	LINEARREG(88, "Linear Regression", "linearreg", "14"),
	LINEARREG_ANGLE(89, "Linear Regression Angle", "linearregangle", "14"),
	LINEARREG_INTERCEPT(90, "Linear Regression Intercept", "linearregintercept", "14"),
	LINEARREG_SLOPE(91, "Linear Regression Slope", "linearregslope", "14"),
	MACDEXT(92, "MACD with controllable MA type", "macdext", "12", "26", "9", "0", "0", "0"),
	MACDFIX(93, "MACD Fix 12/26", "macdfix", "9"),
	MAMA(94, "MESA Adaptive Moving Average", "mama", "0.5", "0.05"),
	MAX(95, "Highest value over a period", "max", "30"),
	MAXINDEX(96, "Index of highest value over a period", "maxindex", "30"),
	MEDPRICE(97, "Median Price", "medprice"),
	MFI(98, "Money Flow Index", "mfi", "14"),
	MIDPOINT(99, "MidPoint over period", "midpoint", "14"),
	MIDPRICE(100, "Midpoint Price over period", "midprice", "14"),
	MIN(101, "Lowest value over a period", "min", "30"),
	MININDEX(102, "Index of lowest value over a period", "minindex", "30"),
	MINMAX(103, "Lowest and highest values over a period", "minmax", "30"),
	MINMAXINDEX(104, "Indexes of lowest and highest values over a period", "minmaxindex", "30"),
	MINUS_DI(105, "Minus Directional Indicator", "minusdi", "14"),
	MINUS_DM(106, "Minus Directional Movement", "minusdm", "14"),
	MOM(107, "Momentum", "mom", "10"),
	NATR(108, "Normalized Average True Range", "natr", "14"),
	OBV(109, "On Balance Volume", "obv"),
	PLUS_DI(110, "Plus Directional Indicator", "plusdi", "14"),
	PLUS_DM(111, "Plus Directional Movement", "plusdm", "14"),
	PPO(112, "Percentage Price Oscillator", "ppo", "12", "26"),
	ROC(113, "Rate of change", "roc", "10"),
	ROCP(114, "Rate of change Percentage", "rocp", "10"),
	ROCR(115, "Rate of change ratio", "rocr", "10"),
	ROCR100(116, "Rate of change ratio 100 scale", "rocr100", "10"),
	SAR(117, "Parabolic SAR", "sar", "0.02", "0.2"),
	SAREXT(118, "Parabolic SAR - Extended", "sarext", "0", "0", "0.02", "0.02", "0.2", "0.02", "0.02", "0.2"),
	STDDEV(119, "Standard Deviation", "stddev", "5", "1"),
	STOCH(120, "Stochastic", "stoch", "5", "3", "3"),
	STOCHF(121, "Stochastic Fast", "stochf", "5", "3"),
	STOCHRSI(122, "Stochastic RSI", "stochrsi", "14", "5", "3"),
	T3(123, "Triple Exponential Moving Average (T3)", "t3", "5", "0.7"),
	TEMA(124, "Triple Exponential Moving Average", "tema", "30"),
	TRANGE(125, "True Range", "trange"),
	TRIMA(126, "Triangular Moving Average", "trima", "30"),
	TRIX(127, "1-day ROC of a Triple Smooth EMA", "trix", "30"),
	TSF(128, "Time Series Forecast", "tsf", "14"),
	TYPPRICE(129, "Typical Price", "typprice"),
	ULTOSC(130, "Ultimate Oscillator", "ultosc", "7", "14", "28"),
	VAR(131, "Variance", "var", "5", "1"),
	WCLPRICE(132, "Weighted Close Price", "wclprice"),
	WILLR(133, "Williams' %R", "willr", "14"),
	WMA(134, "Weighted Moving Average", "wma", "30"),

	/** Not part of TA-Lib / methodCall - dispatched directly in {@link #calculate}. */
	PIVOT(135, "Pivot Points", null),
	/** Default period of 10: not documented anywhere in the original code, chosen as a common textbook value. */
	RVI(136, "Relative Vigor Index", null, "10"),
	/** Default threshold of 5 (percent): not documented anywhere in the original code, chosen as a common textbook value. */
	ZIGZAG(137, "ZigZag", null, "5");

	private final int id;
	private final String displayName;
	private final String taLibName;
	private final String[] defaultParams;

	IndicatorType(int id, String displayName, String taLibName, String... defaultParams) {
		this.id = id;
		this.displayName = displayName;
		this.taLibName = taLibName;
		this.defaultParams = defaultParams;
	}

	public int getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	/** Runs this indicator's calculation with its default parameters against the current data. */
	public void calculate(AnalysisRequest request) {
		switch (this) {
			case PIVOT:
				request.getIndicators().add("Pivot");
				TALibCalculationService.calcPivot();
				TALibCalculationService.setIndCount(TALibCalculationService.getIndCount() + 7);
				return;
			case RVI: {
				int period = Integer.parseInt(defaultParams[0]);
				request.getIndicators().add("RVI_" + period);
				request.getIndicators().add("RVI_Difference");
				TALibCalculationService.calcRVI(period);
				TALibCalculationService.setIndCount(TALibCalculationService.getIndCount() + 2);
				return;
			}
			case ZIGZAG: {
				double threshold = Double.parseDouble(defaultParams[0]);
				request.getIndicators().add("ZigZag_" + threshold);
				request.getIndicators().add("ZigZag_Value");
				TALibCalculationService.calcZZ(threshold);
				TALibCalculationService.setIndCount(TALibCalculationService.getIndCount() + 2);
				return;
			}
			default:
				String[] arguments = new String[defaultParams.length + 2];
				arguments[0] = "ta";
				arguments[1] = taLibName;
				System.arraycopy(defaultParams, 0, arguments, 2, defaultParams.length);
				TALibCalculationService.methodCall(arguments, request);
		}
	}

	public static IndicatorType fromId(int id) {
		for (IndicatorType type : values()) {
			if (type.id == id) {
				return type;
			}
		}
		throw new IllegalArgumentException("Unknown indicator id: " + id);
	}
}
