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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
	public void initGantt(String details, int numProcess){
    	Label label = new Label();
    	label.setText(details);
    	
        xAxis = new NumberAxis();
        yAxis = new CategoryAxis();
        chart = new GanttChart<Number,String>(xAxis,yAxis);
        chart.setTitle("FCFS Visualization");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);
        chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        chart.setLayoutX(0);
        chart.setLayoutY(100);
        
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
        Pane pane1 = new Pane();
        pane1.getChildren().add(label);
        rootPane.getChildren().addAll(chart,pane1);
    	
    	Scene scene  = new Scene(rootPane,ScreenConstants.WIDTH, ScreenConstants.HEIGHT-320);
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