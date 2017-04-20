package cmsc125.mp1.view;

import java.util.ArrayList;

import cmsc125.mp1.constants.ScreenConstants;
import cmsc125.mp1.view.GanttChart.ExtraData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// TODO: use date for x-axis
public class GanttChartStage extends Stage {

    public ArrayList<String> procNames;
    @SuppressWarnings("rawtypes")
	public ArrayList<XYChart.Series> procSeries;
    
    GanttChart<Number,String> chart;
    NumberAxis xAxis;
    CategoryAxis yAxis;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void initGantt(int numProcess){
    	this.setTitle("Simulator");
        
        xAxis = new NumberAxis();
        yAxis = new CategoryAxis();
        chart = new GanttChart<Number,String>(xAxis,yAxis);
        chart.setTitle("FCFS Visualization");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        
        xAxis.setLabel("Time");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

    	
    	procNames = new ArrayList<String>();
    	procSeries = new ArrayList<XYChart.Series>();
    	
        for (int i=1; i<=numProcess; i++){
			procNames.add("P"+Integer.toString(i));
			procSeries.add(new XYChart.Series<>());
	        chart.getData().add(procSeries.get(i-1));
		}
    	yAxis.setCategories(FXCollections.<String>observableArrayList(procNames));
        Scene scene  = new Scene(chart,ScreenConstants.WIDTH, ScreenConstants.HEIGHT-320);
        this.setScene(scene);
        this.show();
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateGantt(int startTime, int processNumber){
		Platform.runLater(() -> procSeries.get(processNumber-1).getData().add(new XYChart.Data(startTime, "P"+Integer.toString(processNumber), new ExtraData( 1, "status-red"))));// Update on JavaFX Application Thread
	}
	
}