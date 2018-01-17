package cmsc125.mp1.view;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChart.ExtraData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GanttChartStage extends Stage {

    public ArrayList<String> procNames;
    @SuppressWarnings("rawtypes")
	public ArrayList<XYChart.Series> procSeries;
    
    public GanttChart<Number,String> chart;
    NumberAxis xAxis;
    CategoryAxis yAxis;
    
    private Text time;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public GanttChartStage(int numProcess){    	
        xAxis = new NumberAxis();
        yAxis = new CategoryAxis();
        chart = new GanttChart<Number,String>(xAxis,yAxis);
        chart.setTitle("");
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
        time = new Text(0, 0, "t = 0");
        time.setFill(Color.CHOCOLATE);
        time.setFont(Font.font(java.awt.Font.SERIF, 14));
        StackPane.setAlignment(time, Pos.TOP_LEFT);
        StackPane.setMargin(time, new Insets(10, 20, 60, 5));
        rootPane.getChildren().add(time);
        
    	Scene scene  = new Scene(rootPane);
        scene.setFill(Color.WHITESMOKE);
        this.setScene(scene);
        this.show();
        
    }

	public void displayTimeAndAvailableData(int t, int[] currentAvailableTableData){
		String availableString = "";
		for (int i = 0; i < currentAvailableTableData.length; i++) {
			availableString += currentAvailableTableData[i] + ", ";
		}
		availableString = availableString.substring(0, availableString.length() - 2);
		time.setText("t = " + t + "                                "); // the extra spaces are used in order to display long strings
		//time.setText("t = " + t + "  Available = " + availableString + "                                "); // the extra spaces are used in order to display long strings
    }
	
	String totalResources = "";
	public void setTotalResourcesUsed(JTable allocatedTable, int[] currentAvailableTableData) {
		ResourcesTableModel allocatedTableModel = (ResourcesTableModel) allocatedTable.getModel();
		for (int j = 0; j < allocatedTableModel.getData()[0].length; j++) {
			int val = 0;
			for (int i = 0; i < allocatedTableModel.getData().length; i++) {
				val += Integer.parseInt(allocatedTableModel.getData()[i][j]);
			}
			val += currentAvailableTableData[j];
			totalResources += val + ", ";
		}
	}
	
	public void displayTotalResourcesUsed() {
		final StringProperty statusProperty = new SimpleStringProperty();
		statusProperty.set(time.getText() + "\n" + totalResources);
		time.textProperty().bind(statusProperty);
		System.out.println(time.getText());
//		time.setText(time.getText() + "\n" + totalResources);
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