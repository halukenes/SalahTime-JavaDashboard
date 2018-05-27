import java.sql.SQLException;

import javafx.application.Application; 
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.chart.PieChart; 
         
public class ReminderPieChart extends Application {  
   @Override 
   public void start(Stage stage) throws SQLException { 
      //Preparing ObservbleList object       
	  DatabaseConnection            connection   = new DatabaseConnection();
      ObservableList<PieChart.Data> pieChartData = connection.getListfor_reminderPieChart();
       
      //Creating a Pie chart 
      PieChart pieChart = new PieChart(pieChartData); 
              
      //Setting the title of the Pie chart 
      pieChart.setTitle("Which Time Is Reminded More?"); 
       
      //setting the direction to arrange the data 
      pieChart.setClockwise(true); 
       
      //Setting the length of the label line 
      pieChart.setLabelLineLength(50); 

      //Setting the labels of the pie chart visible  
      pieChart.setLabelsVisible(true); 
       
      //Setting the start angle of the pie chart  
      pieChart.setStartAngle(180);     
         
      //Creating a Group object  
      Group root = new Group(pieChart); 
         
      //Creating a scene object 
      Scene scene = new Scene(root, 500, 400);  
      
      //Setting title to the Stage 
      stage.setTitle("City Pie Chart"); 
         
      //Adding scene to the stage 
      stage.setScene(scene); 
         
      //Displaying the contents of the stage 
      stage.show();         
   }     
   public static void main(String args[]){ 
      launch(args); 
   } 
} 