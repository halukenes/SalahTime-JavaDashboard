import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {

	private static final String USERNAME                      = "fkdlunsaopdbwe";
	private static final String PASSWORD                      = "d1ec6d177e3ef65b05c0a0ecc3961db45134149eaa91d6a8438aa1f163affa9d";
	private static final String DB_URL                        = "jdbc:postgresql://ec2-54-228-181-43.eu-west-1.compute.amazonaws.com:5432/"
			                                                  + "d1vck26bn55skn?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
	private static final String QUERY_COUNTOF_CITIES          = "SELECT cityname, COUNT (cityname) FROM users GROUP BY cityname;";
	private static final String QUERY_LENGHTOF_COUNTOF_CITIES = "SELECT COUNT(*) FROM (SELECT cityname, COUNT (cityname) FROM users "
			                                                  + "GROUP BY cityname) AS temp;";

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

}
