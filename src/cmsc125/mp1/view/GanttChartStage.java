package cmsc125.mp1.view;

import java.util.ArrayList;

import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.view.GanttChart.ExtraData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        chart.setBlockHeight(30);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        
        xAxis.setLabel("Time");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

    	
    	procNames = new ArrayList<String>();
    	procSeries = new ArrayList<XYChart.Series>();
    	
    	
    	procNames.add("Processes");
    	procSeries.add(new XYChart.Series<>());
    	chart.getData().add(procSeries.get(0));
    	
//        for (int i=1; i<=numProcess; i++){
//			procNames.add("P"+Integer.toString(i-1));
//			procSeries.add(new XYChart.Series<>());
//	        chart.getData().add(procSeries.get(i-1));
//		}
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
		System.out.println("processNumber = " + processNumber + " name = " + name);
		Platform.runLater(() -> procSeries.get(0).getData().add(new XYChart.Data(startTime, "Processes", new ExtraData( 1, name))));// Update on JavaFX Application Thread
	}
	
	public void displayUpdatedJobQueue(ProcessesQueue jobQueue) {
		//TODO: display all the current processes in the job queue, NOTE: The color of the square for the process is the color it is assigned in the ganttchart
		// Design:
		/**
		 * 		Job Queue
		 * 		-----------
		 * 		|    |	  |
		 * 		| P0 | P1 |
		 * 		|	 |    |
		 * 		-----------
		 *        
		 * 
		 * 
		 * */
	}
	
	public void displayUpdatedReadyQueue(ProcessesQueue readyQueue) {
		//TODO: display all the current processes in the ready queue, NOTE: The color of the square for the process is the color it is assigned in the ganttchart
		// Design:
		/**
		 * 		Ready Queue
		 * 		-----------
		 * 		|    |	  |
		 * 		| P0 | P1 |
		 * 		|	 |    |
		 * 		-----------
		 *        
		 * 
		 * 
		 * */
		
		// Positioning in the panel (but this can be subject to change if mayda mas better na layout and mayda ig-aadd na components)
		/**
		 * 
		 *    ----------------------------------
		 *    |          FCFS Simulation       |
		 *    | t = 1                          |
		 *    | Job Queue                      |
		 *    | -----------                    |
		 * 	  | |    |	  |                    |
		 * 	  | | P0 | P1 |                    |
		 * 	  | |	 |    |                    |
		 * 	  | -----------                    |
		 *    | Ready Queue                    |
		 *    | -----------                    |
		 * 	  | |    |	  |                    |
		 * 	  | | P0 | P1 |                    |
		 * 	  | |	 |    |                    |
		 * 	  | -----------                    |
		 *    | Gantt Chart                    |
		 *    | -----------                    |
		 * 	  | |    |	  |                    |
		 * 	  | |    |    |                    |
		 * 	  | |	 |    |                    |
		 * 	  | -----------                    |
		 *    | 0    5    10                   |
		 *    ----------------------------------
		 * */
	}
}