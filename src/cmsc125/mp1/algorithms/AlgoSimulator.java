package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChartStage;

public class AlgoSimulator {

	private ArrayList<String> algos;
	private JTable allocatedTable, maximumTable, availableTable, timeTable;
	private String quantumFieldText;
	private int numProcess;
	public static int visualizationSpeed;

	public AlgoSimulator(int numProcess, ArrayList<String> algos, JTable allocatedTable, JTable maximumTable,
			JTable availableTable, JTable timeTable, String quantumFieldText, int visualizationSpeed) {
		this.numProcess = numProcess;
		this.algos = algos;
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
		this.quantumFieldText = quantumFieldText;
		this.visualizationSpeed = visualizationSpeed * 1000;
	}

	private String prepareGanttInfo() {
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// block to print the resources and arrival time
		String string = "";
		String[][] data = ((ResourcesTableModel) allocatedTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();

		string += "Resources = [";
		String resString = "";
		for (int i = 0; i < data.length; i++) {
			resString += data[i][0] + ", ";
		}
		string += resString.substring(0, resString.length() - 2) + "]    Arrival time = [";

		// arrival
		String arrivalString = "";
		for (int i = 0; i < timeData.length; i++) {
			arrivalString += timeData[i][0] + ", ";
		}
		string += arrivalString.substring(0, arrivalString.length() - 2) + "]\nPriority = [";

		// priority
		String priorityString = "";
		for (int i = 0; i < timeData.length; i++) {
			priorityString += timeData[i][1] + ", ";
		}
		string += priorityString.substring(0, priorityString.length() - 2) + "]    Quantum = ";

		// quantum
		string += quantumFieldText;

		return string;
	}

	public void startSimulation() {
		if (algos.contains("FCFS")) {
			Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(), numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("FCFS Simulation");
			Main.ganttVisual.setTitle("FCFS Simulation");

			FCFSManager fcfsManager = new FCFSManager(allocatedTable, maximumTable, availableTable, timeTable);
			fcfsManager.startSimulation();
		} else if (algos.contains("SJF")) {
			Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(), numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("SJF Simulation");
			Main.ganttVisual.setTitle("SJF Simulation");

			SJFManager sjfManager = new SJFManager(allocatedTable, maximumTable, availableTable, timeTable);
			sjfManager.startSimulation();
		} else if (algos.contains("RR")) {
			Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(), numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("RR Simulation");
			Main.ganttVisual.setTitle("RR Simulation");

			RRManager rrManager = new RRManager(allocatedTable, maximumTable, availableTable, timeTable,
					quantumFieldText);
			rrManager.startSimulation();
		} else if (algos.contains("NP PRIO")) {
			Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(), numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("NP PRIO Simulation");
			Main.ganttVisual.setTitle("NP PRIO Simulation");

			NP_PRIOManager np_prioManager = new NP_PRIOManager(allocatedTable, maximumTable, availableTable, timeTable);
			np_prioManager.startSimulation();
		} else if (algos.contains("SRTF")) {
			Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(), numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("SRTF Simulation");
			Main.ganttVisual.setTitle("SRTF Simulation");

			SRTFManager srtfManager = new SRTFManager(allocatedTable, maximumTable, availableTable, timeTable);
			srtfManager.startSimulation();
		} else if (algos.contains("PRIO")) {
			Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(), numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("PRIO Simulation");
			Main.ganttVisual.setTitle("PRIO Simulation");

			PRIOManager prioManager = new PRIOManager(allocatedTable, maximumTable, availableTable, timeTable);
			prioManager.startSimulation();
		}
	}
}
