import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;

public class SuraPieChart extends Application {
	private Stage mainStage;

	@Override
	public void start(Stage stage) throws SQLException {
		mainStage = stage;
		Platform.setImplicitExit(false);
		// Preparing ObservbleList object
		DatabaseConnection connection = new DatabaseConnection();
		ObservableList<PieChart.Data> pieChartData = connection.getListfor_SuraPieChart();

		// Creating a Pie chart
		PieChart pieChart = new PieChart(pieChartData);

		// Setting the title of the Pie chart
		pieChart.setTitle("Which Sura Was Read More?");

		// setting the direction to arrange the data
		pieChart.setClockwise(true);

		// Setting the length of the label line
		pieChart.setLabelLineLength(50);

		// Setting the labels of the pie chart visible
		pieChart.setLabelsVisible(true);

		// Setting the start angle of the pie chart
		pieChart.setStartAngle(180);

		// Creating a Group object
		Group root = new Group(pieChart);

		// Creating a scene object
		Scene scene = new Scene(root, 500, 400);

		// Setting title to the Stage
		mainStage.setTitle("Sura Pie Chart");

		// Adding scene to the stage
		mainStage.setScene(scene);

		// Displaying the contents of the stage
		mainStage.show();
		
		Platform.runLater(() -> {
            mainStage.sizeToScene();
            mainStage.show();
        });
	}

	public static void main(String args[]) {
		launch(args);
	}
}