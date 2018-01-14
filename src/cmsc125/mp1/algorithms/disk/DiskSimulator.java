package cmsc125.mp1.algorithms.disk;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.LineChartStage;

public class DiskSimulator {

	private ArrayList<String> algos;
	private JTable allocatedTable, maximumTable, availableTable, timeTable;
	private String quantumFieldText;
	private int numProcess;
	public static int visualizationSpeed;
	private LineChartStage FCFSdisk, SJFdisk, RRdisk, NPPRIOdisk, PRIOdisk, SRTFdisk;

	public DiskSimulator(int numProcess, ArrayList<String> algos, JTable allocatedTable, JTable maximumTable,
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

	public void startSimulation() {
		if (algos.contains("FCFS"))
			FCFSdisk = new LineChartStage(numProcess);
		if (algos.contains("SJF"))
			SJFdisk = new LineChartStage(numProcess);
		if (algos.contains("RR"))
			RRdisk = new LineChartStage(numProcess);
		if (algos.contains("NP PRIO"))
			NPPRIOdisk = new LineChartStage(numProcess);
		if (algos.contains("SRTF"))
			SRTFdisk = new LineChartStage(numProcess);
		if (algos.contains("PRIO"))
			PRIOdisk = new LineChartStage(numProcess);
	}
}
