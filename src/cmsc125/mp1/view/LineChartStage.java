package cmsc125.mp1.view;

import java.util.ArrayList;

import cmsc125.mp1.constants.ScreenConstants;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LineChartStage extends Stage {

	public ArrayList<XYChart.Series> procSeries;
	private LineChart lineChart;
    NumberAxis xAxis, yAxis;
	
    public LineChartStage(int numProcess) {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        this.setTitle("Disk Access");
        xAxis.setLabel("Time Accessed");
        yAxis.setLabel("Cylinder");

        lineChart = new LineChart(xAxis, yAxis);
        lineChart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

        procSeries = new ArrayList<XYChart.Series>();
        
        for (int i = 0; i < numProcess; i++){
			procSeries.add(new XYChart.Series<>());
			procSeries.get(i).setName("P" + Integer.toString(i));
			lineChart.getData().add(procSeries.get(i));
		}

        VBox vbox = new VBox(lineChart);    	
    	Scene scene  = new Scene(vbox);
        scene.setFill(Color.WHITESMOKE);
        scene.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        this.setScene(scene);
        this.show();
    }

    @SuppressWarnings({ "unchecked"})
    public void updateChart(String name, int accessTime, int cylinder) {
    	int processNumber = Integer.parseInt(name.substring(1));
    	Platform.runLater(() -> procSeries.get(processNumber).getData().add(new XYChart.Data<Integer, Integer>(accessTime, cylinder)));// Update on JavaFX Application Thread
    }
    
    public void renewSeries(String name) {
    	int processNumber = Integer.parseInt(name.substring(1));
    	procSeries.set(processNumber, new XYChart.Series<>());
    	procSeries.get(processNumber).setName("P"+Integer.toString(processNumber));
    }
}