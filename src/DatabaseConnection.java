import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class DatabaseConnection {

	private static final String      USERNAME                      = "fkdlunsaopdbwe";
	private static final String      PASSWORD                      = "d1ec6d177e3ef65b05c0a0ecc3961db45134149eaa91d6a8438aa1f163affa9d";
	private static final String      DB_URL                        = "jdbc:postgresql://ec2-54-228-181-43.eu-west-1.compute.amazonaws.com:5432/"
			                                                       + "d1vck26bn55skn?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	private static final String      QUERY_COUNTOF_CITIES          = "SELECT cityname, COUNT (cityname) FROM users GROUP BY cityname;";
	private static final String      QUERY_LENGHTOF_COUNTOF_CITIES = "SELECT COUNT(*) FROM (SELECT cityname, COUNT (cityname) FROM users "
			                                                       + "GROUP BY cityname) AS temp;";
	private static final String      QUERY_ROWOF_TRAFFICDB         = "SELECT * FROM trafficdb ORDER BY trafficid DESC LIMIT ";
	private static final String      QUERY_ROWBT_TRAFFICDB         = "select trafficdate, trafficcount from trafficdb where trafficid >= ";
	private static final String      QUERY_GETTINGDATE_ID          = "select trafficid from trafficdb where trafficdate = '";
	private static final String      QUERY_GET_SURAS               = "SELECT * FROM suras;";
	private static final String      QUERY_GET_REMINDCODES         = "SELECT remindcode FROM users WHERE remindcode <> '0000000';";
	private static final String      QUERY_COUNTOF_ZEROREMINDCODES = "SELECT COUNT(*) FROM users GROUP BY remindcode HAVING remindcode = '0000000';";
	private static final String      QUERY_GETNULL_CITIES          = "SELECT COUNT(*) FROM users WHERE cityname IS NULL OR cityname = '';";
	private              String      untilnowID                    = "untilnowID";
	private              String      untildayID                    = "untildayID";
	private              String      startingID                    = "startingID";
	private              String      endingID                      = "endingID";
	private static final String      first                         = "didn't share location";
	private static final String      second                        = "0 salah time";
	private static final String      third                         = "1 salah time";
	private static final String      fourth                        = "2 salah time";
	private static final String      fifth                         = "3 salah time";
	private static final String      sixth                         = "4 salah time";
	private static final String      seventh                       = "5 salah time";
	private              Preferences prefs = Preferences.userRoot().node(MainPage.class.getName());
	private              Connection connection;

	public DatabaseConnection() throws SQLException {
		this.connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

	public DatabaseMapDataSet[] getdatafor_map() throws SQLException {
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);
		DatabaseMapDataSet[] arrayDataSet = new DatabaseMapDataSet[0];
		ResultSet resultSet = statement.executeQuery(QUERY_COUNTOF_CITIES);
		ResultSet resultSetCountQuery = connection.createStatement().executeQuery(QUERY_LENGHTOF_COUNTOF_CITIES);
		while (resultSetCountQuery.next()) {
			arrayDataSet = new DatabaseMapDataSet[resultSetCountQuery.getInt("count")];
		}
		int counter = 0;
		while (resultSet.next()) {
			arrayDataSet[counter] = new DatabaseMapDataSet(resultSet.getString("cityname"), resultSet.getInt("count"));
			counter++;
		}
		return arrayDataSet;
	}
	
	public ObservableList<PieChart.Data> getdatafor_cities() throws SQLException {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ResultSet resultSet = connection.createStatement().executeQuery(QUERY_COUNTOF_CITIES);
		while(resultSet.next()) {
			pieChartData.add(new PieChart.Data(resultSet.getString("cityname") + ": " + resultSet.getInt("count") + " users", resultSet.getInt("count")));
		}
		return pieChartData;
	}

	public TrafficLineGraphDataSet getDataSetfor_TrafficLineGraph() throws SQLException {
		int[][] datafromDB = null;
		if (prefs.getBoolean(untilnowID, true)) {
			int rowNumber = prefs.getInt(untildayID, 5);
			datafromDB = new int[rowNumber][2];
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet resultSet = statement.executeQuery(QUERY_ROWOF_TRAFFICDB + rowNumber + ";");
			int counter = 0;
			while (resultSet.next()) {
				datafromDB[counter][0] = Integer.parseInt(resultSet.getString("trafficdate").substring(0, 2));
				datafromDB[counter][1] = resultSet.getInt("trafficcount");
				counter++;
			}
		} else {
			ResultSet resultSetStartingDate = connection.createStatement()
					.executeQuery(QUERY_GETTINGDATE_ID + prefs.get(startingID, "") + "';");
			int startingdate = 0;
			while (resultSetStartingDate.next()) {
				startingdate = resultSetStartingDate.getInt("trafficid");
			}
			ResultSet resultSetEndingDate = connection.createStatement()
					.executeQuery(QUERY_GETTINGDATE_ID + prefs.get(endingID, "") + "';");
			int endingdate = 0;
			while (resultSetEndingDate.next()) {
				endingdate = resultSetEndingDate.getInt("trafficid");
			}
			ResultSet resultSet = connection.createStatement()
					.executeQuery(QUERY_ROWBT_TRAFFICDB + startingdate + " and trafficid <=" + endingdate + ";");
			datafromDB = new int[endingdate - startingdate + 1][2];
			int counter = endingdate - startingdate;
			while (resultSet.next()) {
				datafromDB[counter][0] = Integer.parseInt(resultSet.getString("trafficdate").substring(0, 2));
				datafromDB[counter][1] = resultSet.getInt("trafficcount");
				counter--;
			}
		}

		return new TrafficLineGraphDataSet(datafromDB);

	}
	
	public ObservableList<PieChart.Data> getListfor_SuraPieChart() throws SQLException {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ResultSet resultSet = connection.createStatement().executeQuery(QUERY_GET_SURAS);
		while(resultSet.next()) {
			pieChartData.add(new PieChart.Data(resultSet.getString("suraname").substring(0, 1).toUpperCase() + resultSet.getString("suraname").substring(1) + ": " + resultSet.getInt("readcount"), resultSet.getInt("readcount")));
		}
		return pieChartData;
		
	}
	
	public ObservableList<PieChart.Data> getListfor_reminderPieChart() throws SQLException {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		ResultSet                     resultSet    = connection.createStatement().executeQuery(QUERY_GET_REMINDCODES);
		int                           countImsak   = 0;
		int                           countFajr    = 0;
		int                           countSunrise = 0;
		int                           countTulu    = 0;
		int                           countZuhr    = 0;
		int                           countMaghrib = 0;
		int                           countIsha    = 0;
		while(resultSet.next()) {
			if(resultSet.getString("remindcode").charAt(0) == '1')
				countImsak++;
			if(resultSet.getString("remindcode").charAt(1) == '1')
				countFajr++;
			if(resultSet.getString("remindcode").charAt(2) == '1')
				countSunrise++;
			if(resultSet.getString("remindcode").charAt(3) == '1')
				countTulu++;
			if(resultSet.getString("remindcode").charAt(4) == '1')
				countZuhr++;
			if(resultSet.getString("remindcode").charAt(5) == '1')
				countMaghrib++;
			if(resultSet.getString("remindcode").charAt(6) == '1')
				countIsha++;
			}
		System.out.println(countImsak + " - " + countFajr + " - " + countSunrise + " - " + countTulu + " - " + countZuhr + " - " + countMaghrib + " - " + countIsha);
		pieChartData.add(new PieChart.Data("Imsak: " + countImsak + " users", countImsak));
		pieChartData.add(new PieChart.Data("Fajr: " + countFajr + " users", countFajr));
		pieChartData.add(new PieChart.Data("Sunrise: " + countSunrise + " users", countSunrise));
		pieChartData.add(new PieChart.Data("Tulu: " + countTulu + " users", countTulu));
		pieChartData.add(new PieChart.Data("Zuhr: " + countZuhr + " users", countZuhr));
		pieChartData.add(new PieChart.Data("Maghrib: " + countMaghrib + " users", countMaghrib));
		pieChartData.add(new PieChart.Data("Isha: " + countIsha + " users", countIsha));
		return pieChartData;	
	}
	
	public XYChart.Series getXYChartfor_ReminderColumnChart() throws SQLException {
		XYChart.Series columnChartDataSet = new XYChart.Series();		
		int            count              = 0;
		int            count1             = 0;
		int            count2             = 0;
		int            count3             = 0;
		int            count4             = 0;
		int            count5             = 0;
		int            count6             = 0;
		ResultSet      resultSet          = connection.createStatement().executeQuery(QUERY_GETNULL_CITIES);
		while(resultSet.next()) {
			columnChartDataSet.getData().add(new XYChart.Data(first, resultSet.getInt("count")));
		}
		resultSet          = connection.createStatement().executeQuery(QUERY_COUNTOF_ZEROREMINDCODES);
		while(resultSet.next()) {
			columnChartDataSet.getData().add(new XYChart.Data(second, resultSet.getInt("count")));
		}
		resultSet          = connection.createStatement().executeQuery(QUERY_GET_REMINDCODES);
		while(resultSet.next()) {
			count = count1s(resultSet.getString("remindcode"));
			switch(count) {
			   case 1 :
				   count1++;
				   break;			   
			   case 2 :
				   count2++;
				   break;
			   case 3 :
				   count3++;
				   break;
			   case 4 :
				   count4++;
				   break;
			   case 5 :
				   count5++;
				   break;
			}
		}
		columnChartDataSet.getData().add(new XYChart.Data(third, count1));
		columnChartDataSet.getData().add(new XYChart.Data(fourth, count2));
		columnChartDataSet.getData().add(new XYChart.Data(fifth, count3));
		columnChartDataSet.getData().add(new XYChart.Data(sixth, count4));
		columnChartDataSet.getData().add(new XYChart.Data(seventh, count5));
		return columnChartDataSet;		
	}
	
	public int count1s(String word) {
		int count = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == '1') {
				count++;
			}
		}
		return count;
	}
}
