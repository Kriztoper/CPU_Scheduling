package cmsc125.mp1.view;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DiskChart{

	public ArrayList<String> procNames;
	public ArrayList<XYChart.Series<Number, Number>> procSeries;

	public LineChart<Number, Number> chart;
	NumberAxis xAxis, yAxis;

	public DiskChart(int numProcess) {
		xAxis = new NumberAxis();
		yAxis = new NumberAxis();

		xAxis.setLabel("Time");
		xAxis.setTickLabelFill(Color.CHOCOLATE);
		xAxis.setMinorTickCount(4);
		
		yAxis.setTickLabelFill(Color.CHOCOLATE);
//		yAxis.setLabel("Cylinder");
//		yAxis.autosize();

		chart = new LineChart<Number, Number>(xAxis, yAxis);
		chart.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
		chart.setMaxHeight(100.0);

		procSeries = new ArrayList<XYChart.Series<Number, Number>>();

		for (int i = 0; i < numProcess; i++) {
			procSeries.add(new XYChart.Series<>());
			procSeries.get(i).setName("P" + Integer.toString(i));
			chart.getData().add(procSeries.get(i));
		}

		chart.setLegendVisible(false);
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