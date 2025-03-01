package analyzer;

public class ParseCommandLine {

	private static boolean nameSet = false;
	private static String error;

	public static boolean setTimeName(String[] args) {
		boolean isSet = false;
		boolean temp1 = false;
		boolean temp2 = false;
		while (!isSet) {
			for (String s : args) {
				String[] arguments = s.split("_");
				if (arguments[0].equalsIgnoreCase("name")) {
					StockAnalyzer.setSymbol(arguments[1]);
					// System.out.println(arguments[1]);
					temp1 = true;
					setNameSet(true);
				} else if (arguments[0].equalsIgnoreCase("time")) {
					if (arguments.length < 2) {
						error = "Time argument mismatch";
						System.out.println(error);
						StockAnalyzer.setError(error);
						System.exit(0);
					}
					int interval = Integer.parseInt(arguments[1]);
					StockAnalyzer.setInterval(Integer.parseInt(arguments[1]));
					temp2 = true;
					// System.out.println("test " + arguments.length);
					if (arguments.length == 4) {
						StockAnalyzer.setStartTime(arguments[2]);
						StockAnalyzer.setEndTime(arguments[3]);
						StockAnalyzer.setTimeSet(true);
					} 
					if (arguments.length == 6){
						StockAnalyzer.setStartTime(arguments[2]);
						StockAnalyzer.setEndTime(arguments[3]);
						StockAnalyzer.setTimeSet(true);
						String startd = arguments[4];
						String endd = arguments[5];
						StockAnalyzer.setStartDate(startd);
						StockAnalyzer.setEndDate(endd);
						StockAnalyzer.setDateSet(true);
					}
					if (nameSet) {
						if (DBConnect.checkInterval(interval,
								StockAnalyzer.getSymbol())) {
							DBConnect.ReadDb();
						} else if (!DBConnect.checkInterval(interval,
								StockAnalyzer.getSymbol())) {
							StockAnalyzer.setInterval(1);
							DBConnect.ReadDb();
							Aggregate.startAggregate(ParseCsv.stockList,
									interval, StockAnalyzer.getSymbol());
							StockAnalyzer.setInterval(interval);
						}
						StockAnalyzer.copyArray();
					} else {
						System.out.println("Name not defined");
						System.exit(0);
					}

				}
				if (temp1 && temp2) {
					isSet = true;
				}
			}
		}
		return isSet;
	}

	public static void parseCL(String[] args) {

		for (String s : args) {
			String[] arguments = s.split("_");
			if (arguments[0].equalsIgnoreCase("delimiter")) {
				if (arguments.length < 2) {
					error = "Delimiter argument mismatch, set to default delimiter \";\"";
					System.out.println(error);
					StockAnalyzer.setError(error);
					StockAnalyzer.setDelimiter(";");
					return;
				}
				StockAnalyzer.setDelimiter(arguments[1]);
			} else if (arguments[0].equalsIgnoreCase("pivot")) {
				if (arguments.length != 1) {
					error = "Pivot argument mismatch";
					System.out.println(error);
					StockAnalyzer.setError(error);
					return;
				}
				WriteCsv.indicators.add("Pivot");
				System.out.println("Calculating Pivot Points");
				TALibCalls.calcPivot();
				TALibCalls.setIndCount(TALibCalls.getIndCount() + 7);
			} else if (arguments[0].trim().equalsIgnoreCase("rvi")) {
				if (arguments.length != 2) {
					error = "RVI argument mismatch";
					System.out.println(error);
					StockAnalyzer.setError(error);
					return;
				}
				int period = Integer.parseInt(arguments[1]);
				WriteCsv.indicators.add("RVI_" + period);
				WriteCsv.indicators.add("RVI_Difference");
				System.out.println("Calculating RVI");
				TALibCalls.calcRVI(period);
				TALibCalls.setIndCount(TALibCalls.getIndCount() + 2);
				// CalculateRVI(rVI_Period);
			} else if (arguments[0].trim().equalsIgnoreCase("zz")) {
				if (arguments.length != 2) {
					error = "ZigZag argument mismatch";
					System.out.println(error);
					StockAnalyzer.setError(error);
					return;
				}
				double threshold = Double.parseDouble(arguments[1]);
				WriteCsv.indicators.add("ZigZag_" + threshold);
				WriteCsv.indicators.add("ZigZag_Value");
				System.out.println("Calculating ZigZag");
				TALibCalls.calcZZ(threshold);
				TALibCalls.setIndCount(TALibCalls.getIndCount() + 2);
				// ZigZag.CalculateZigZag(zZ_Threshold);
			} else if (arguments[0].equalsIgnoreCase("ta")) {
				TALibCalls.methodCall(arguments);
			} else if (arguments[0].equalsIgnoreCase("name")) {

			} else if (arguments[0].equalsIgnoreCase("time")) {

			} else if (arguments[0].equalsIgnoreCase("file")) {
				String temp = "";
				if (arguments.length > 2) {
					for (int i = 1; i < arguments.length; i++) {
						temp += arguments[i];
					}
					WriteCsv.setFileNameAdd(temp);
				} else if (arguments.length == 2) {
					temp = arguments[1];
					WriteCsv.setFileNameAdd(temp);
				} else {
					error = "File argument mismatch";
					System.out.println(error);
					StockAnalyzer.setError(error);
				}
			} else {
				error = "Parameter " + arguments[0] + " unknown";
				StockAnalyzer.setError(error);
			}
		}
	}

	public static boolean isNameSet() {
		return nameSet;
	}

	public static void setNameSet(boolean nameSet) {
		ParseCommandLine.nameSet = nameSet;
	}
}
