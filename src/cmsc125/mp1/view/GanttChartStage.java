package cmsc125.mp1.view;

import java.util.ArrayList;

import javax.swing.JTable;

import com.sun.prism.ps.Shader;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChart.ExtraData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
    private Text jobQueue;
    private Text readyQueue;
    ArrayList<Rectangle> jqProcesses;
    ArrayList<Rectangle> rqProcesses;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public GanttChartStage(int numProcess){    	
        xAxis = new NumberAxis();
        yAxis = new CategoryAxis();
        chart = new GanttChart<Number,String>(xAxis,yAxis);
        chart.setTitle("");
        chart.setMaxHeight(50.0);
        chart.setBlockHeight(50);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        
        xAxis.setLabel("Time");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);

    	
    	procNames = new ArrayList<String>();
    	procSeries = new ArrayList<XYChart.Series>();
    	
    	procNames.add("P");
    	procSeries.add(new XYChart.Series<>());
    	chart.getData().add(procSeries.get(0));
    	
//        for (int i=1; i<=numProcess; i++){
//			procNames.add("P"+Integer.toString(i-1));
//			procSeries.add(new XYChart.Series<>());
//	        chart.getData().add(procSeries.get(i-1));
//		}
    	yAxis.setCategories(FXCollections.<String>observableArrayList(procNames));

        StackPane rootPane = new StackPane();
        StackPane.setAlignment(chart, Pos.BOTTOM_CENTER);
//        StackPane.setMargin(chart, new Insets(10, 10, 10, 10));
        rootPane.getChildren().addAll(chart);

        // time t label
        time = new Text(0, 0, "t = 0");
        time.setFill(Color.CHOCOLATE);
        time.setFont(Font.font(java.awt.Font.SANS_SERIF, 14));
        StackPane.setAlignment(time, Pos.TOP_LEFT);
        StackPane.setMargin(time, new Insets(10, 20, 60, 5));
        rootPane.getChildren().add(time);
        
        // job queue label
        jobQueue = new Text(0, 0, "Job Queue");
        jobQueue.setFill(Color.CHOCOLATE);
        jobQueue.setFont(Font.font(java.awt.Font.SANS_SERIF, 14));
        StackPane.setAlignment(jobQueue, Pos.TOP_LEFT);
        StackPane.setMargin(jobQueue, new Insets(30, 20, 60, 5));
        rootPane.getChildren().add(jobQueue);
        
        // ready queue label
        readyQueue = new Text(0, 0, "Ready Queue");
        readyQueue.setFill(Color.CHOCOLATE);
        readyQueue.setFont(Font.font(java.awt.Font.SANS_SERIF, 14));
        StackPane.setAlignment(readyQueue, Pos.TOP_LEFT);
        StackPane.setMargin(readyQueue, new Insets(120, 20, 60, 5));
        rootPane.getChildren().add(readyQueue);
        
        // gantt chart label
        Text ganttChartLbl = new Text(0, 0, "Gantt Chart");
        ganttChartLbl.setFill(Color.CHOCOLATE);
        ganttChartLbl.setFont(Font.font(java.awt.Font.SANS_SERIF, 14));
        StackPane.setAlignment(ganttChartLbl, Pos.TOP_LEFT);
        StackPane.setMargin(ganttChartLbl, new Insets(210, 20, 60, 5));
        rootPane.getChildren().add(ganttChartLbl);
        
        // Job Queue Rectangles
        jqProcesses = new ArrayList<Rectangle>();

        for (int i = 0; i < 20; i++) {
        	//Drawing a Rectangle
        	Rectangle rectangle = new Rectangle();  

        	//Setting the properties of the rectangle 
        	rectangle.setX(0);
        	rectangle.setY(0);
        	rectangle.setWidth(15.0f); 
        	rectangle.setHeight(50.0f);
//        	rectangle.setFill(ColorConstants.getColorFX(i));
        	rectangle.setFill(Color.WHITESMOKE);
        	
        	// Add rectangle to list
        	jqProcesses.add(rectangle);
        	StackPane.setAlignment(rectangle, Pos.TOP_LEFT);
        	StackPane.setMargin(rectangle, new Insets(60, 10, 10, 10 + i * 15));
        	rootPane.getChildren().add(rectangle);
        }
        
        // Ready Queue Rectangles
        rqProcesses = new ArrayList<Rectangle>();

        for (int i = 0; i < 20; i++) {
        	//Drawing a Rectangle
        	Rectangle rectangle = new Rectangle();

        	//Setting the properties of the rectangle 
        	rectangle.setX(0);
        	rectangle.setY(0);
        	rectangle.setWidth(15.0f); 
        	rectangle.setHeight(50.0f);
//        	rectangle.setFill(ColorConstants.getColorFX(i));
        	rectangle.setFill(Color.WHITESMOKE);
        	
        	// Add rectangle to list
        	rqProcesses.add(rectangle);
        	StackPane.setAlignment(rectangle, Pos.CENTER_LEFT);
        	StackPane.setMargin(rectangle, new Insets(10, 10, 10, 10 + i * 15));
        	rootPane.getChildren().add(rectangle);
        }
        
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
		/*if (t == 19) {
//			ObservableList<Node> list = this.getScene().getRoot().getChildrenUnmodifiable();
//			Node node = list.get(list.size() - 1);
//			node.setVisible(false);
			((Node) rqProcesses.get(5)).setVisible(false);
//			System.out.println(node.getClass().toString());
//			System.exit(0);
//			list.remove(list.size() - 1);
		}*/
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
		Platform.runLater(() -> procSeries.get(0).getData().add(new XYChart.Data(startTime, "P", new ExtraData( 1, name))));// Update on JavaFX Application Thread
	}
	
	public void displayUpdatedJobQueue(ProcessesQueue jobQueue) {
		for (int i = 0, j = 0; i < 20; i++) {
			((Node) jqProcesses.get(i)).setVisible(false);

			if (i < jobQueue.getSize() && !jobQueue.get(i).isAllocated()) {
				java.awt.Color color = jobQueue.get(i).getColor();
				((Rectangle) jqProcesses.get(j)).setFill(new javafx.scene.paint.Color(color.getRed()/255.0, color.getGreen()/255.0, color.getBlue()/255.0, 1.0));
				((Node) jqProcesses.get(j)).setVisible(true);
				j++;
			}
		}
		
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
		for (int i = 0; i < 20; i++) {
			if (i < readyQueue.getSize()) {
				java.awt.Color color = readyQueue.get(i).getColor();
				((Rectangle) rqProcesses.get(i)).setFill(new javafx.scene.paint.Color(color.getRed()/255.0, color.getGreen()/255.0, color.getBlue()/255.0, 1.0));
				((Node) rqProcesses.get(i)).setVisible(true);
			} else {
				((Node) rqProcesses.get(i)).setVisible(false);
			}
		}
		
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