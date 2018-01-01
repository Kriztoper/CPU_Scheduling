package cmsc125.mp1.view;

import java.util.ArrayList;

import cmsc125.mp1.view.GanttChart.ExtraData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GanttChartStage extends Stage {

    public ArrayList<String> procNames;
    @SuppressWarnings("rawtypes")
	public ArrayList<XYChart.Series> procSeries;
    
    public GanttChart<Number,String> chart;
    NumberAxis xAxis;
    CategoryAxis yAxis;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public GanttChartStage(int numProcess){    	
        xAxis = new NumberAxis();
        yAxis = new CategoryAxis();
        chart = new GanttChart<Number,String>(xAxis,yAxis);
        chart.setTitle("FCFS Visualization");
        chart.setBlockHeight(15);
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

        StackPane rootPane = new StackPane();
        rootPane.getChildren().addAll(chart);
    	
    	Scene scene  = new Scene(rootPane);
        scene.setFill(Color.WHITESMOKE);
        this.setScene(scene);
        this.show();
        
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateGantt(int startTime, String name){
		int processNumber = Integer.parseInt(name.substring(1));
		Platform.runLater(() -> procSeries.get(processNumber-1).getData().add(new XYChart.Data(startTime, name, new ExtraData( 1, name))));// Update on JavaFX Application Thread
	}
	
}