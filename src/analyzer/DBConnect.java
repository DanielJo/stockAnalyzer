package analyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
	static String Url = "jdbc:mysql://REDACTED-HOST:3306/Valideon_test?useServerPrepStmts=false&rewriteBatchedStatements=true";
	static String user = "REDACTED-USER";
	static String pass = "REDACTED-PASSWORD";
	static int interv;
	static String symb;
	static int i = 0;
	private static Connection con = null;
	private static Statement stmt = null;


	// static String sql =
	// "SELECT symbols.Name, intervals.MinutesCount, data.Endtime, data.Open, data.High, data.Low, data.Close, data.Volume\n"
	// + "From Symbols symbols, Intervals intervals, Data data\n"
	// + "WHERE intervals.MinutesCount=" + interv + "\n"
	// + "AND symbols.Name=" + symb;

	public static void ReadDb() {
		
		String sql1 = "SELECT symbols.Name, intervals.MinutesCount, data.Endtime, data.Open, data.High, data.Low, data.Close, data.Volume\n"
				+ "From Symbols symbols INNER JOIN Data data ON symbols.Id = data.symbols INNER JOIN Intervals intervals ON data.Intervals = intervals.Id\n"
				+ "WHERE intervals.MinutesCount="
				+ StockAnalyzer.getInterval()
				+ "\n"
				+ "AND symbols.Name='" + StockAnalyzer.getSymbol() + "'";

		String sql2 = "SELECT symbols.Name, intervals.MinutesCount, data.Endtime, data.Open, data.High, data.Low, data.Close, data.Volume\n"
				+ "From Symbols symbols INNER JOIN Data data ON symbols.Id = data.symbols INNER JOIN Intervals intervals ON data.Intervals = intervals.Id\n"
				+ "WHERE intervals.MinutesCount="
				+ StockAnalyzer.getInterval()
				+ "\n"
				+ "AND symbols.Name='"
				+ StockAnalyzer.getSymbol()
				+ "'"
				+ "\n"
				+ "AND EXTRACT(HOUR_MINUTE FROM data.Endtime) BETWEEN "
				+ StockAnalyzer.getStartTime().replace(":", "")
				+ " and "
				+ StockAnalyzer.getEndTime().replace(":", "");

		if (StockAnalyzer.isDateSet()){
			System.out.println("test");
			sql1 += "\nAND data.Endtime >= '" + StockAnalyzer.getStartDate() + "' AND data.Endtime <= '" + StockAnalyzer.getEndDate() + "'";
			sql2 += "\nAND data.Endtime >= '" + StockAnalyzer.getStartDate() + "' AND data.Endtime <= '" + StockAnalyzer.getEndDate() + "'";
		}
		System.out.println(sql2);
		ResultSet rs;
		//System.out.println(StockAnalyzer.getStartTime());

		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			con = DriverManager.getConnection(Url, user, pass);
			System.out.println("Connected database successfully...");
			stmt = con.createStatement();
			if (!StockAnalyzer.isTimeSet()) {
				//System.out.println("opt 1 " + StockAnalyzer.getSymbol() + " " + StockAnalyzer.getInterval());
				System.out.println(sql1);
				rs = stmt.executeQuery(sql1);
			} else {
				//System.out.println("opt 2 " + StockAnalyzer.getSymbol() + " " + StockAnalyzer.getInterval());
				rs = stmt.executeQuery(sql2);
			}
			System.out.println(sql2);
			if (!rs.next()) {
				System.out.println("ResultSet is empty");
			}
			System.out.print("Fetching data");
			while (rs.next()) {
				if(i % 10000 == 0){
					System.out.print(".");
				}
				String symbol_t = rs.getString("Name");
				int interv_t = rs.getInt("MinutesCount");
				String endtime_t = rs.getString("EndTime");
				Double open_t = rs.getDouble("Open");
				Double high_t = rs.getDouble("High");
				Double low_t = rs.getDouble("Low");
				Double close_t = rs.getDouble("Close");
				int volume_t = rs.getInt("Volume");
				Stock stock = new Stock(endtime_t, symbol_t, interv_t, open_t,
						high_t, low_t, close_t, volume_t);
				ParseCsv.stockList.add(stock);
				i++;
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		System.out.println("\nDB fetching finished");

	}
	
	public static boolean checkInterval(int interv, String symb){
		boolean chk = false;
		ResultSet rs;
		String sqlc = "SELECT EXISTS(SELECT 1 FROM Symbols WHERE name = '" + symb + "' AND `Min Intervall` = " + interv + ") AS Cv";
		int checkResult = 0;
		
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// STEP 3: Open a connection
			con = DriverManager.getConnection(Url, user, pass);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlc);
			while(rs.next()){
				checkResult = rs.getInt("Cv");
			}
			if (checkResult == 1){
				chk = true;
			}	
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (con != null)
					con.close();
				//System.out.println("con closed");
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try
		//System.out.println("\nDB fetching finished");
		//System.out.println(chk);
		return chk;
	}
}
