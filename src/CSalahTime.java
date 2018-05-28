
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
public class CSalahTime  extends Application {
    final static String first  = "Shared location";
    final static String second = "Didn't share location";
    
    @Override public void start(Stage stage) throws SQLException {
    	DatabaseConnection connection = new DatabaseConnection();
        stage.setTitle("Salah Time");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Number of users who choosen \"Salah Time\" from bot");
         
        xAxis.setLabel("Category");       
        yAxis.setLabel("Number of users");
 
        XYChart.Series series1 = connection.getDataSetfor_SalahTimeColumnChartLocation();
        series1.setName("Number of users who wanted salah time");
        
        XYChart.Series series2 = connection.getDataSetfor_SalahTimeColumnChartLocationwithLocaiton();
        series2.setName("Number of users who wanted salah time with shared location");
        
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        bc.getData().addAll(series2);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}