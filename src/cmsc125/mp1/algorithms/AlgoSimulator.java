package cmsc125.mp1.algorithms;

import javax.swing.JTable;

import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.constants.ScreenConstants;
import cmsc125.mp1.view.GanttChartStage;

public class AlgoSimulator {

	public GanttChartStage ganttChart;
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
		ganttChart = new GanttChartStage(numProcess);
		ganttChart.chart.setTitle("");
		
		if (CPUalgo.contains("FCFS")) {
			ganttChart.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setWidth(ScreenConstants.WIDTH/3);
			ganttChart.setX(0);
			ganttChart.setY(0);
			ganttChart.setTitle("CPU Scheduling: FCFS Simulation");

			FCFSManager fcfsManager = new FCFSManager(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
			fcfsManager.startSimulation();
		} else if (CPUalgo.contains("SRTF")) {
			ganttChart.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setWidth(ScreenConstants.WIDTH/3);
			ganttChart.setX(ScreenConstants.WIDTH/3);
			ganttChart.setY(0);
			ganttChart.setTitle("CPU Scheduling: SRTF Simulation");

			SRTFManager srtfManager = new SRTFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
			srtfManager.startSimulation();
		} else if (CPUalgo.contains("SJF")) {
			ganttChart.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setWidth(ScreenConstants.WIDTH/3);
			ganttChart.setX(ScreenConstants.WIDTH*2/3);
			ganttChart.setY(0);
			ganttChart.setTitle("CPU Scheduling: SJF Simulation");

			SJFManager sjfManager = new SJFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
			sjfManager.startSimulation();
		} else if (CPUalgo.contains("NP PRIO")) {
			ganttChart.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setWidth(ScreenConstants.WIDTH/3);
			ganttChart.setX(ScreenConstants.WIDTH/3);
			ganttChart.setY(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setTitle("CPU Scheduling: NP PRIO Simulation");

			NP_PRIOManager np_prioManager = new NP_PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
			np_prioManager.startSimulation();
		} else if (CPUalgo.contains("PRIO")) {
			ganttChart.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setWidth(ScreenConstants.WIDTH/3);
			ganttChart.setX(0);
			ganttChart.setY(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setTitle("CPU Scheduling: PRIO Simulation");

			PRIOManager prioManager = new PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttChart, ds);
			prioManager.startSimulation();
		}
		
		if (CPUalgo.contains("RR")) {
			ganttChart.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setWidth(ScreenConstants.WIDTH/3);
			ganttChart.setX(ScreenConstants.WIDTH*2/3);
			ganttChart.setY(ScreenConstants.HEIGHT/2 - 10);
			ganttChart.setTitle("CPU Scheduling: RR Simulation");

			RRManager rrManager = new RRManager(allocatedTable, maximumTable, availableTable, timeTable, quantumFieldText, ganttChart, ds);
			rrManager.startSimulation();
		}
	}
}
