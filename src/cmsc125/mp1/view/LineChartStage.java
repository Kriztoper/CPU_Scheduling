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

	public ArrayList<String> procNames;
	public ArrayList<XYChart.Series> procSeries;
    NumberAxis xAxis, yAxis;
	
    public LineChartStage(int numProcess) {
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();

        this.setTitle("Disk Access");
        xAxis.setLabel("Access Time");
        yAxis.setLabel("Cylinder");

        LineChart lineChart = new LineChart(xAxis, yAxis);

        procSeries = new ArrayList<XYChart.Series>();
        
        for (int i=1; i<=numProcess; i++){
			procSeries.add(new XYChart.Series<>());
			procSeries.get(i-1).setName("P"+Integer.toString(i));
			lineChart.getData().add(procSeries.get(i-1));
		}

        VBox vbox = new VBox(lineChart);    	
    	Scene scene  = new Scene(vbox,ScreenConstants.WIDTH, ScreenConstants.HEIGHT-320);
        scene.setFill(Color.WHITESMOKE);
        this.setScene(scene);
        this.show();
    }

    @SuppressWarnings({ "unchecked"})
    public void updateChart(String name, int accessTime, int cylinder) {
    	int processNumber = Integer.parseInt(name.substring(1));
    	Platform.runLater(() -> procSeries.get(processNumber-1).getData().add(new XYChart.Data<Integer, Integer>(accessTime, cylinder)));// Update on JavaFX Application Thread
    }
}