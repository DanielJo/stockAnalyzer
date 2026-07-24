package io.github.danieljo.stockanalyzer.cli;

import io.github.danieljo.stockanalyzer.indicator.TALibCalculationService;

/**
 * Replaces ParseCommandLine.java. Parsing is now split into two phases, same as before:
 * <ol>
 *   <li>{@link #parseNameAndTime(String[])} resolves the {@code name_}/{@code time_} tokens
 *   into an {@link AnalysisRequest}. Unlike the original {@code setTimeName}, this is now pure
 *   parsing with no database access (that moved to StockDataService) and does a single clean
 *   pass over the arguments instead of the original's unbounded {@code while(!isSet)} loop,
 *   which would spin forever on malformed input missing {@code name_}/{@code time_}.</li>
 *   <li>{@link #parseOptions(String[], AnalysisRequest)} processes the remaining tokens
 *   (delimiter/pivot/rvi/zz/ta/file), same as the original {@code parseCL}.</li>
 * </ol>
 */
public final class ArgumentParser {

	private ArgumentParser() {
	}

	public static AnalysisRequest parseNameAndTime(String[] args) {
		AnalysisRequest request = new AnalysisRequest();
		boolean nameFound = false;
		boolean timeFound = false;

		for (String s : args) {
			String[] arguments = s.split("_");
			if (arguments[0].equalsIgnoreCase("name")) {
				request.setSymbol(arguments[1]);
				nameFound = true;
			} else if (arguments[0].equalsIgnoreCase("time")) {
				if (arguments.length < 2) {
					String error = "Time argument mismatch";
					System.out.println(error);
					request.addError(error);
					throw new IllegalArgumentException(error);
				}
				if (!nameFound) {
					System.out.println("Name not defined");
					throw new IllegalArgumentException("name_<symbol> must come before time_...");
				}

				int interval = Integer.parseInt(arguments[1]);
				request.setInterval(interval);
				timeFound = true;

				if (arguments.length == 4) {
					request.setStartTime(arguments[2]);
					request.setEndTime(arguments[3]);
					request.setTimeSet(true);
				}
				if (arguments.length == 6) {
					request.setStartTime(arguments[2]);
					request.setEndTime(arguments[3]);
					request.setTimeSet(true);
					request.setStartDate(arguments[4]);
					request.setEndDate(arguments[5]);
					request.setDateSet(true);
				}
			}
		}

		if (!(nameFound && timeFound)) {
			throw new IllegalArgumentException("Both name_<symbol> and time_<interval> arguments are required");
		}
		return request;
	}

	public static void parseOptions(String[] args, AnalysisRequest request) {
		for (String s : args) {
			String[] arguments = s.split("_");
			String error;
			if (arguments[0].equalsIgnoreCase("delimiter")) {
				if (arguments.length < 2) {
					error = "Delimiter argument mismatch, set to default delimiter \";\"";
					System.out.println(error);
					request.addError(error);
					request.setDelimiter(";");
					continue;
				}
				request.setDelimiter(arguments[1]);
			} else if (arguments[0].equalsIgnoreCase("pivot")) {
				if (arguments.length != 1) {
					error = "Pivot argument mismatch";
					System.out.println(error);
					request.addError(error);
					continue;
				}
				request.getIndicators().add("Pivot");
				System.out.println("Calculating Pivot Points");
				TALibCalculationService.calcPivot();
				TALibCalculationService.setIndCount(TALibCalculationService.getIndCount() + 7);
			} else if (arguments[0].trim().equalsIgnoreCase("rvi")) {
				if (arguments.length != 2) {
					error = "RVI argument mismatch";
					System.out.println(error);
					request.addError(error);
					continue;
				}
				int period = Integer.parseInt(arguments[1]);
				request.getIndicators().add("RVI_" + period);
				request.getIndicators().add("RVI_Difference");
				System.out.println("Calculating RVI");
				TALibCalculationService.calcRVI(period);
				TALibCalculationService.setIndCount(TALibCalculationService.getIndCount() + 2);
			} else if (arguments[0].trim().equalsIgnoreCase("zz")) {
				if (arguments.length != 2) {
					error = "ZigZag argument mismatch";
					System.out.println(error);
					request.addError(error);
					continue;
				}
				double threshold = Double.parseDouble(arguments[1]);
				request.getIndicators().add("ZigZag_" + threshold);
				request.getIndicators().add("ZigZag_Value");
				System.out.println("Calculating ZigZag");
				TALibCalculationService.calcZZ(threshold);
				TALibCalculationService.setIndCount(TALibCalculationService.getIndCount() + 2);
			} else if (arguments[0].equalsIgnoreCase("ta")) {
				TALibCalculationService.methodCall(arguments, request);
			} else if (arguments[0].equalsIgnoreCase("name")) {
				// already handled by parseNameAndTime
			} else if (arguments[0].equalsIgnoreCase("time")) {
				// already handled by parseNameAndTime
			} else if (arguments[0].equalsIgnoreCase("file")) {
				String temp;
				if (arguments.length > 2) {
					StringBuilder builder = new StringBuilder();
					for (int i = 1; i < arguments.length; i++) {
						builder.append(arguments[i]);
					}
					temp = builder.toString();
					request.setFileNameAdd(temp);
				} else if (arguments.length == 2) {
					temp = arguments[1];
					request.setFileNameAdd(temp);
				} else {
					error = "File argument mismatch";
					System.out.println(error);
					request.addError(error);
				}
			} else {
				error = "Parameter " + arguments[0] + " unknown";
				request.addError(error);
			}
		}
	}
}
