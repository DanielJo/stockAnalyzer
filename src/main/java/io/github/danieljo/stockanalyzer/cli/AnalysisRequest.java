package io.github.danieljo.stockanalyzer.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds the parameters and running state of a single analysis run.
 * <p>
 * Replaces the static fields that used to live on {@code StockAnalyzer} (symbol, interval,
 * times, dates, delimiter, error list) plus the indicator-label list that used to live on
 * {@code WriteCsv} and the file-name suffix. Keeping this as one instance per run (instead of
 * static/global state) is what makes it safe to eventually run more than one analysis in the
 * same JVM, e.g. from a GUI.
 */
public class AnalysisRequest {

	private String symbol;
	private int interval;

	private String startTime;
	private String endTime;
	private boolean timeSet;
	private boolean timeOpt = true;

	private String startDate;
	private String endDate;
	private boolean dateSet;

	private String delimiter = ";";
	private String fileNameAdd = "";

	private final List<String> indicators = new ArrayList<>();
	private final List<String> errors = new ArrayList<>();

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isTimeSet() {
		return timeSet;
	}

	public void setTimeSet(boolean timeSet) {
		this.timeSet = timeSet;
	}

	public boolean isTimeOpt() {
		return timeOpt;
	}

	public void setTimeOpt(boolean timeOpt) {
		this.timeOpt = timeOpt;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isDateSet() {
		return dateSet;
	}

	public void setDateSet(boolean dateSet) {
		this.dateSet = dateSet;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getFileNameAdd() {
		return fileNameAdd;
	}

	public void setFileNameAdd(String fileNameAdd) {
		this.fileNameAdd = fileNameAdd;
	}

	public List<String> getIndicators() {
		return indicators;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}
}
