import java.sql.SQLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class TrafficLineChart extends Application {
	
	@Override
	public void start(Stage stage) throws SQLException {
		Platform.setImplicitExit(false);
		DatabaseConnection connection = new DatabaseConnection();
		TrafficLineGraphDataSet dataSet = connection.getDataSetfor_TrafficLineGraph();
		// Defining the x axis
		NumberAxis xAxis = new NumberAxis(dataSet.getxAxisStartNumber(), dataSet.getxAxisLastNumber(), 5);
		xAxis.setLabel(dataSet.getxAxisName());

		// Defining the y axis
		NumberAxis yAxis = new NumberAxis(dataSet.getyAxisStartNumber(), dataSet.getyAxisLastNumber(),
				dataSet.getyAxisLastNumber() + 1);
		yAxis.setLabel(dataSet.getyAxisName());

		// Creating the line chart
		LineChart linechart = new LineChart(xAxis, yAxis);

		// Prepare XYChart.Series objects by setting data
		XYChart.Series series = new XYChart.Series();
		series.setName(dataSet.getNameofChart());

		for (int i = 0; i < dataSet.getDataArray().length; i++) {
			series.getData().add(new XYChart.Data(dataSet.getDataArray()[i][0], dataSet.getDataArray()[i][1]));
		}

		// Setting the data to Line chart
		linechart.getData().add(series);

		// Creating a Group object
		Group root = new Group(linechart);

		// Creating a scene object
		Scene scene = new Scene(root, 600, 400);

		// Setting title to the Stage
		stage.setTitle("Traffic Information of the Bot for 5 days");

		// Adding scene to the stage
		stage.setScene(scene);

		// Displaying the contents of the stage
		stage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
	
	
}