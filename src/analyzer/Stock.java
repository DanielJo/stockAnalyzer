package analyzer;
public class Stock {
	private String DateTime;
	private String Symbol;
	private int Intervall;
	private Double Open;
	private Double High;
	private Double Low;
	private Double Close;
	private int Volume;
	private int ZZ_Point;
	private double ZZ_Value;
	private String ZZ_HighTime;
	private String ZZ_LowTime;
	private Double PP_pp;
	private Double PP_s1;
	private Double PP_r1;
	private Double PP_s2;
	private Double PP_r2;
	private Double PP_s3;
	private Double PP_r3;

	private Double RVI_Difference;
	private Double RVI;

	public Stock(String dateTime, String symbol, int intervall, String open,
			String high, String low, String close, String volume) {
		super();
		DateTime = dateTime;
		Symbol = symbol;
		Intervall = intervall;
		Open = Double.parseDouble(open.replace(",", "."));
		High = Double.parseDouble(high.replace(",", "."));
		Low = Double.parseDouble(low.replace(",", "."));
		Close = Double.parseDouble(close.replace(",", "."));
		Volume = Integer.parseInt(volume.replace(".", "").replace(" ", "")
				.replace("-", "0"));
		RVI = 0.;
		RVI_Difference = 0.;
		ZZ_Point = 0;
		ZZ_Value = 0.;
	}

	public Stock(String dateTime, String symbol, int interval, double open,
			double high, double low, double close, int volume) {
		DateTime = dateTime;
		Symbol = symbol;
		Intervall = interval;
		Open = open;
		High = high;
		Low = low;
		Close = close;
		Volume = volume;
		RVI = 0.;
		RVI_Difference = 0.;
		ZZ_Point = 0;
		ZZ_Value = 0.;
	}

	public Double getPP_pp() {
		return PP_pp;
	}

	public void setPP_pp(Double pP_pp) {
		PP_pp = pP_pp;
	}

	public Double getPP_s1() {
		return PP_s1;
	}

	public void setPP_s1(Double pP_s1) {
		PP_s1 = pP_s1;
	}

	public Double getPP_r1() {
		return PP_r1;
	}

	public void setPP_r1(Double pP_r1) {
		PP_r1 = pP_r1;
	}

	public Double getPP_s2() {
		return PP_s2;
	}

	public void setPP_s2(Double pP_s2) {
		PP_s2 = pP_s2;
	}

	public Double getPP_r2() {
		return PP_r2;
	}

	public void setPP_r2(Double pP_r2) {
		PP_r2 = pP_r2;
	}

	public Double getPP_s3() {
		return PP_s3;
	}

	public void setPP_s3(Double pP_s3) {
		PP_s3 = pP_s3;
	}

	public Double getPP_r3() {
		return PP_r3;
	}

	public void setPP_r3(Double pP_r3) {
		PP_r3 = pP_r3;
	}

	public Double getRVI_Difference() {
		return RVI_Difference;
	}

	public void setRVI_Difference(Double rVI_Difference) {
		RVI_Difference = rVI_Difference;
	}

	public Double getRVI() {
		return RVI;
	}

	public void setRVI(Double rVI) {
		RVI = rVI;
	}

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public String getSymbol() {
		return Symbol;
	}

	public void setSymbol(String symbol) {
		Symbol = symbol;
	}

	public int getIntervall() {
		return Intervall;
	}

	public void setIntervall(int intervall) {
		Intervall = intervall;
	}

	public Double getOpen() {
		return Open;
	}

	public void setOpen(Double open) {
		Open = open;
	}

	public Double getHigh() {
		return High;
	}

	public void setHigh(Double high) {
		High = high;
	}

	public Double getLow() {
		return Low;
	}

	public void setLow(Double low) {
		Low = low;
	}

	public Double getClose() {
		return Close;
	}

	public void setClose(Double close) {
		Close = close;
	}

	public int getVolume() {
		return Volume;
	}

	public void setVolume(int volume) {
		Volume = volume;
	}

	public int getZZ_Point() {
		return ZZ_Point;
	}

	public void setZZ_Point(int zZ_Point) {
		if(zZ_Point == 1 || zZ_Point == -1 || zZ_Point == 0){
		ZZ_Point = zZ_Point;
		} else {
			System.out.println("ZZ Point ungueltig");
		}
	}

	public double getZZ_Value() {
		return ZZ_Value;
	}

	public void setZZ_Value(double zZ_Value) {
		ZZ_Value = zZ_Value;
	}

	public String getZZ_HighTime() {
		return ZZ_HighTime;
	}

	public void setZZ_HighTime(String zZ_HighTime) {
		ZZ_HighTime = zZ_HighTime;
	}

	public String getZZ_LowTime() {
		return ZZ_LowTime;
	}

	public void setZZ_LowTime(String zZ_LowTime) {
		ZZ_LowTime = zZ_LowTime;
	}

}
