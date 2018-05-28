
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CReadingSura extends Application {
	final static String first = "How many sura is read";
	final static String second = "How many people read";

	@Override
	public void start(Stage stage) throws SQLException {
		DatabaseConnection connection = new DatabaseConnection();
		stage.setTitle("Reading Sura");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Reading Sura");
		xAxis.setLabel("Days");
		yAxis.setLabel("Number of people");

		XYChart.Series series1 = connection.getDataSetfor_SuraColumnChart();
		series1.setName("How many sura is read");

		Scene scene = new Scene(bc, 800, 600);
		bc.getData().addAll(series1);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}