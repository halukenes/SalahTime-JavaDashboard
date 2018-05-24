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

public class DatabaseConnection {

	private static final String USERNAME = "fkdlunsaopdbwe";
	private static final String PASSWORD = "d1ec6d177e3ef65b05c0a0ecc3961db45134149eaa91d6a8438aa1f163affa9d";
	private static final String DB_URL = "jdbc:postgresql://ec2-54-228-181-43.eu-west-1.compute.amazonaws.com:5432/"
			+ "d1vck26bn55skn?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	private static final String QUERY_COUNTOF_CITIES = "SELECT cityname, COUNT (cityname) FROM users GROUP BY cityname;";
	private static final String QUERY_LENGHTOF_COUNTOF_CITIES = "SELECT COUNT(*) FROM (SELECT cityname, COUNT (cityname) FROM users "
			+ "GROUP BY cityname) AS temp;";
	private static final String QUERY_ROWOF_TRAFFICDB = "SELECT * FROM trafficdb ORDER BY trafficid DESC LIMIT ";
	private static final String QUERY_ROWBT_TRAFFICDB = "select trafficdate, trafficcount from trafficdb where trafficid >= ";
	private static final String QUERY_GETTINGDATE_ID = "select trafficid from trafficdb where trafficdate = '";
	private static final String QUERY_GET_SURAS      = "SELECT * FROM suras;";
	private String untilnowID = "untilnowID";
	private String untildayID = "untildayID";
	private String startingID = "startingID";
	private String endingID = "endingID";
	private Preferences prefs = Preferences.userRoot().node(MainPage.class.getName());
	private Connection connection;

	public DatabaseConnection() throws SQLException {
		this.connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

	public DatabaseMapDataSet[] getdatafor_traffic() throws SQLException {
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

}
