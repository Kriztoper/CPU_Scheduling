package cmsc125.mp1.view;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChart.ExtraData;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

    StackPane rootPane;
    private Text time;
    private Text jobQueue;
    private Text readyQueue;
    ArrayList<Rectangle> jqProcesses;
    ArrayList<Rectangle> rqProcesses;

    // stats field types
    private Text statsTableText;

    // bankers stats field types
    private Text partialStatsText;

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

        rootPane = new StackPane();
        StackPane.setAlignment(chart, Pos.BOTTOM_CENTER);
//        StackPane.setMargin(chart, new Insets(10, 10, 10, 10));
        rootPane.getChildren().addAll(chart);

        // init stats table
        initStats(rootPane);

        // time t label
        time = new Text(0, 0, "t = 0");
        time.setFill(Color.CHOCOLATE);
        time.setFont(Font.font(java.awt.Font.SANS_SERIF, 14));
        StackPane.setAlignment(time, Pos.TOP_LEFT);
        StackPane.setMargin(time, new Insets(10, 20, 60, 5));
        rootPane.getChildren().add(time);

        // bankers stats text
    	partialStatsText = new Text(0, 0, "");
    	partialStatsText.setFill(Color.CHOCOLATE);
    	partialStatsText.setFont(Font.font(java.awt.Font.SANS_SERIF, 12));
        StackPane.setAlignment(partialStatsText, Pos.TOP_RIGHT);
        StackPane.setMargin(partialStatsText, new Insets(30, 20, 60, 0));
        rootPane.getChildren().add(partialStatsText);

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
		Platform.runLater(
			() -> {
				String availableString = "";
				for (int i = 0; i < currentAvailableTableData.length; i++) {
					availableString += currentAvailableTableData[i] + ", ";
				}
				availableString = availableString.substring(0, availableString.length() - 2);
				time.setText("t = " + t + "  Avail = " + availableString + "                                "); // the extra spaces are used in order to display long strings
			}
		);
	}

	public void displayPartialStats(String stats) {
		Platform.runLater(() -> { partialStatsText.setText(stats); });
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
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateGantt(int startTime, String name){
		int processNumber = Integer.parseInt(name.substring(1));
		System.out.println("processNumber = " + processNumber + " name = " + name);
		Platform.runLater(() -> procSeries.get(0).getData().add(new XYChart.Data(startTime, "P", new ExtraData( 1, name))));// Update on JavaFX Application Thread
	}

	public void displayUpdatedJobQueue(ProcessesQueue jobQueue) {
		Platform.runLater(
			() -> {
				for (int i = 0, j = 0; i < 20; i++) {
					((Node) jqProcesses.get(i)).setVisible(false);

					if (i < jobQueue.getSize() && !jobQueue.get(i).isAllocated()) {
						int procNumber = jobQueue.get(i).getProcessNumber();
						jqProcesses.get(j).setFill(ColorConstants.getColorFX(procNumber));
						//TODO: Display process number inside rectangle
						((Node) jqProcesses.get(j)).setVisible(true);
						j++;
					}
				}

				// NOTE: The color of the square for the process is the color it is assigned in the ganttchart
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
		);
	}

	public void displayUpdatedReadyQueue(ProcessesQueue readyQueue) {
		Platform.runLater(
			() -> {
				for (int i = 0; i < 20; i++) {
					if (i < readyQueue.getSize()) {
						int procNumber = readyQueue.get(i).getProcessNumber();
						rqProcesses.get(i).setFill(ColorConstants.getColorFX(procNumber));
						//TODO: Display process number inside rectangle
						((Node) rqProcesses.get(i)).setVisible(true);
					} else {
						rqProcesses.get(i).setFill(Color.WHITESMOKE);
						((Node) rqProcesses.get(i)).setVisible(false);
					}
				}

				// NOTE: The color of the square for the process is the color it is assigned in the ganttchart
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
		);
	}

	private void initStats(StackPane rootPane) {
        statsTableText = new Text(0, 0, "");
        statsTableText.setFill(Color.WHITESMOKE);
        statsTableText.setFont(Font.font(java.awt.Font.SANS_SERIF, 12));
        StackPane.setAlignment(statsTableText, Pos.TOP_LEFT);
        StackPane.setMargin(statsTableText, new Insets(40, 20, 60, 5));
        rootPane.getChildren().add(statsTableText);
	}

	public void displayStats(String[][] statsTableData) {
		Platform.runLater(
			() -> {
				jobQueue.setText("");
				readyQueue.setText("");
				partialStatsText.setText("");
				statsTableText.setFill(Color.CHOCOLATE);

				String stats = "";
				int height = statsTableData.length;
				int width = statsTableData[0].length;
				String[] statsColumnNames = {"CT","TAT","WT","RT"};
				if (height - 2 <= 10) {								// if num process <= 10 display in one line
					// display P{#} as column names
					for (int i = 0; i < height - 1; i++) {
						if (i == 0) {
							stats += "\t";
						} else {
							if (i < height - 2) {
								stats += "P" + (i-1) + "\t";
							} else {
								stats += "P" + (i-1) + "\n";
							}
						}
					}
					// display values of processes in CT, TAT, WT, RT
					for (int j = 0; j < width; j++) {
						stats += statsColumnNames[j] + "\t";
						for (int i = 0; i < height - 1; i++) {
							if (i == height - 2) {
								stats += statsTableData[i][j] + " = " + statsTableData[i+1][j] + "\n";
							} else {
								stats += statsTableData[i][j] + "\t";
							}
						}
					}
				} else {											// if num process > 10 display in two lines
					// display P{#} as column names for 1st line. Range:[0-10]
					for (int i = 0; i < height/2 + 1; i++) {
						if (i == 0) {
							stats += "\t";
						} else {
							if (i < height/2) {
								stats += "P" + (i-1) + "\t";
							} else {
								stats += "P" + (i-1) + "\n";
							}
						}
					}
					// display values of processes in CT, TAT, WT, RT for 1st line. Range:[0-10]
					for (int j = 0; j < width; j++) {
						stats += statsColumnNames[j] + "\t";
						for (int i = 0; i < height/2; i++) {
							if (i < height/2 - 1) {
								stats += statsTableData[i][j] + "\t";
							} else {
								stats += statsTableData[i][j] + "\n";
							}
						}
					}
					// display P{#} as column names for 2nd line. Range:[11-19]
					for (int i = height/2; i < height - 1; i++) {
						if (i == height/2) {
							stats += "\t";
						} else {
							if (i < height - 2) {
								stats += "P" + (i-1) + "\t";
							} else {
								stats += "P" + (i-1) + "\n";
							}
						}
					}
					// display values of processes in CT, TAT, WT, RT for 1st line. Range:[11-19]
					for (int j = 0; j < width; j++) {
						stats += statsColumnNames[j] + "\t";
						for (int i = height/2; i < height - 1; i++) {
							if (i == height - 2) {
								stats += statsTableData[i][j] + "=" + statsTableData[i+1][j] + "\n";
							} else {
								stats += statsTableData[i][j] + "\t";
							}
						}
					}
				}
				statsTableText.setText(stats);
			}
		);
	}
}