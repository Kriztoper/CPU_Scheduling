package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChartStage;

public class AlgoSimulator {

	public GanttChartStage ganttFCFS, ganttSJF, ganttRR, ganttNPPRIO, ganttSRTF, ganttPRIO;
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
		this.visualizationSpeed = visualizationSpeed;
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
			ganttFCFS = new GanttChartStage();
			ganttFCFS.initGantt(prepareGanttInfo(), numProcess);
			ganttFCFS.setX(0);
			ganttFCFS.setY(230);
			ganttFCFS.chart.setTitle("FCFS Simulation");
			ganttFCFS.setTitle("FCFS Simulation");

			FCFSManager fcfsManager = new FCFSManager(allocatedTable, maximumTable, availableTable, timeTable, ganttFCFS);
			fcfsManager.startSimulation();
		}
		if (algos.contains("SJF")) {
			ganttSJF = new GanttChartStage();
			ganttSJF.initGantt(prepareGanttInfo(), numProcess);
			ganttSJF.setX(0);
			ganttSJF.setY(230);
			ganttSJF.chart.setTitle("SJF Simulation");
			ganttSJF.setTitle("SJF Simulation");

			SJFManager sjfManager = new SJFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttSJF);
			sjfManager.startSimulation();
		}
		if (algos.contains("RR")) {
			ganttRR = new GanttChartStage();
			ganttRR.initGantt(prepareGanttInfo(), numProcess);
			ganttRR.setX(0);
			ganttRR.setY(230);
			ganttRR.chart.setTitle("RR Simulation");
			ganttRR.setTitle("RR Simulation");

			RRManager rrManager = new RRManager(allocatedTable, maximumTable, availableTable, timeTable,
					quantumFieldText, ganttRR);
			rrManager.startSimulation();
		}
		if (algos.contains("NP PRIO")) {
			ganttNPPRIO = new GanttChartStage();
			ganttNPPRIO.initGantt(prepareGanttInfo(), numProcess);
			ganttNPPRIO.setX(0);
			ganttNPPRIO.setY(230);
			ganttNPPRIO.chart.setTitle("NP PRIO Simulation");
			ganttNPPRIO.setTitle("NP PRIO Simulation");

			NP_PRIOManager np_prioManager = new NP_PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttNPPRIO);
			np_prioManager.startSimulation();
		}
		if (algos.contains("SRTF")) {
			ganttSRTF = new GanttChartStage();
			ganttSRTF.initGantt(prepareGanttInfo(), numProcess);
			ganttSRTF.setX(0);
			ganttSRTF.setY(230);
			ganttSRTF.chart.setTitle("SRTF Simulation");
			ganttSRTF.setTitle("SRTF Simulation");

			SRTFManager srtfManager = new SRTFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttSRTF);
			srtfManager.startSimulation();
		}
		if (algos.contains("PRIO")) {
			ganttPRIO = new GanttChartStage();
			ganttPRIO.initGantt(prepareGanttInfo(), numProcess);
			ganttPRIO.setX(0);
			ganttPRIO.setY(230);
			ganttPRIO.chart.setTitle("PRIO Simulation");
			ganttPRIO.setTitle("PRIO Simulation");

			PRIOManager prioManager = new PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttPRIO);
			prioManager.startSimulation();
		}
	}
}
