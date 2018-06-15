package cmsc125.mp1.view;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LineChartStage extends Stage {

	public ArrayList<String> procNames;
	public ArrayList<XYChart.Series<Number, Number>> procSeries;

	private LineChart<Number, Number> lineChart;
	NumberAxis xAxis, yAxis;

	public LineChartStage(int numProcess) {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();

		this.setTitle("Disk Access");
		xAxis.setLabel("Time");
		yAxis.setLabel("Cylinder");

		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());

		procSeries = new ArrayList<XYChart.Series<Number, Number>>();

		for (int i = 0; i < numProcess; i++) {
			procSeries.add(new XYChart.Series<>());
			procSeries.get(i).setName("P" + Integer.toString(i));
			lineChart.getData().add(procSeries.get(i));
		}

		lineChart.setLegendVisible(false);
		Scene scene = new Scene(lineChart);
		scene.setFill(Color.WHITESMOKE);
		scene.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
		this.setScene(scene);
		this.show();
	}

	public void updateChart(String name, int accessTime, int cylinder) {
		int processNumber = Integer.parseInt(name.substring(1));
		Data<Number, Number> data = new Data<Number, Number>(accessTime, cylinder);
		Node container = data.getNode();
		container = new Pane();
		data.setNode(container);
		container.getStyleClass().add(name);
		Platform.runLater(() -> procSeries.get(processNumber).getData().add(data));
	}

	public void renewSeries(String name) {
		int processNumber = Integer.parseInt(name.substring(1));
		procSeries.set(processNumber, new XYChart.Series<>());
		procSeries.get(processNumber).setName(name);
	}
}