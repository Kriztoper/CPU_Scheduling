package cmsc125.mp1.algorithms;

import javax.swing.JTable;

import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.view.CPUChart;

public class AlgoSimulator {

	public CPUChart cpuChart;
	private String CPUalgo;
	private JTable allocatedTable, maximumTable, availableTable, timeTable;
	private String quantumFieldText;
	private int numProcess;
	public static int visualizationSpeed;
	private DiskSimulator ds;

	public AlgoSimulator(int numProcess, String CPUalgo, JTable allocatedTable, JTable maximumTable,
			JTable availableTable, JTable timeTable, String quantumFieldText, int visualizationSpeed, DiskSimulator ds) {
		this.numProcess = numProcess;
		this.CPUalgo = CPUalgo;
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
		this.quantumFieldText = quantumFieldText;
		AlgoSimulator.visualizationSpeed = visualizationSpeed;
		this.ds = ds;
	}

	public void startSimulation() {
		cpuChart = new CPUChart(numProcess);
		cpuChart.chart.setTitle("");
		
		if (CPUalgo.contains("FCFS")) {
			FCFSManager fcfsManager = new FCFSManager("FCFS CPU with "+ds.diskAlgo+" Disk Sched", allocatedTable, maximumTable, availableTable, timeTable, cpuChart, ds);
			fcfsManager.startSimulation();
		} else if (CPUalgo.contains("SRTF")) {
			SRTFManager srtfManager = new SRTFManager("SRTF CPU with "+ds.diskAlgo+" Disk Sched", allocatedTable, maximumTable, availableTable, timeTable, cpuChart, ds);
			srtfManager.startSimulation();
		} else if (CPUalgo.contains("SJF")) {
			SJFManager sjfManager = new SJFManager("SJF CPU with "+ds.diskAlgo+" Disk Sched", allocatedTable, maximumTable, availableTable, timeTable, cpuChart, ds);
			sjfManager.startSimulation();
		} else if (CPUalgo.contains("NP PRIO")) {
			NP_PRIOManager np_prioManager = new NP_PRIOManager("NPPRIO CPU with "+ds.diskAlgo+" Disk Sched", allocatedTable, maximumTable, availableTable, timeTable, cpuChart, ds);
			np_prioManager.startSimulation();
		} else if (CPUalgo.contains("PRIO")) {
			PRIOManager prioManager = new PRIOManager("PRIO CPU with "+ds.diskAlgo+" Disk Sched", allocatedTable, maximumTable, availableTable, timeTable, cpuChart, ds);
			prioManager.startSimulation();
		} else if (CPUalgo.contains("RR")) {
			RRManager rrManager = new RRManager("RR CPU with "+ds.diskAlgo+" Disk Sched", allocatedTable, maximumTable, availableTable, timeTable, quantumFieldText, cpuChart, ds);
			rrManager.startSimulation();
		}
	}
}
