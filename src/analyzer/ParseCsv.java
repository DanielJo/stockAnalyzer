package analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {
	
	private static String DateTime;
	private static String Open;
	private static String High;
	private static String Low;
	private static String Close;
	private static String Volume;
	public static List<Stock> stockList = new ArrayList<>();
	
	public static void ReadFile(String fileUrl) throws IOException {
		FileReader fr = new FileReader(fileUrl);
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    SplitLine(line);
                }
            }
	}
	
	public static void SplitLine(String line) throws IOException {

		/* neues Dateiformat */

		String[] tokens = line.split(";");
		DateTime = tokens[1];
		Open = tokens[2];
		High = tokens[3];
		Low = tokens[4];
		Close = tokens[5];
		Volume = tokens[6];
		

		/* altes Dateiformat */

		// String[] tokens = line.split("\\t");
		// DateTime = tokens[0] + " " + tokens[1];
		// Open = tokens[2].replace(",", ".");
		// High = tokens[3].replace(",", ".");
		// Low = tokens[4].replace(",", ".");
		// Close = tokens[5].replace(",", ".");
		// Volume = tokens[6];
		// String Move = "";

		String oldFormat = "dd.MM.yyyy HH:mm:ss";
		// String oldFormat = "dd/MM/yyyy HH:mm:ss";
		String newFormat = "yyyy-MM-dd HH:mm:ss";
		String formattedTime = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat(oldFormat);
		SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
		try {
			formattedTime = sdf2.format(sdf1.parse(DateTime));
			// System.out.println(formattedTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(Volume);
		Stock stock = new Stock(formattedTime, StockAnalyzer.getSymbol(), StockAnalyzer.getInterval(), Open, High, Low,
				Close, Volume);
		stockList.add(stock);
	}
}
