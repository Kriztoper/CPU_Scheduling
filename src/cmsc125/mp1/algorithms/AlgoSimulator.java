package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.constants.ScreenConstants;
import cmsc125.mp1.view.GanttChartStage;

public class AlgoSimulator {

	public GanttChartStage ganttFCFS, ganttSJF, ganttRR, ganttNPPRIO, ganttSRTF, ganttPRIO;
	private ArrayList<String> algos;
	private JTable allocatedTable, maximumTable, availableTable, timeTable;
	private String quantumFieldText;
	private int numProcess;
	public static int visualizationSpeed;
	private DiskSimulator ds;

	public AlgoSimulator(int numProcess, ArrayList<String> algos, JTable allocatedTable, JTable maximumTable,
			JTable availableTable, JTable timeTable, String quantumFieldText, int visualizationSpeed, DiskSimulator ds) {
		this.numProcess = numProcess;
		this.algos = algos;
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
		this.quantumFieldText = quantumFieldText;
		AlgoSimulator.visualizationSpeed = visualizationSpeed;
		this.ds = ds;
	}

	public void startSimulation() {
		if (algos.contains("FCFS")) {
			ganttFCFS = new GanttChartStage(numProcess);
			ganttFCFS.setHeight(ScreenConstants.HEIGHT/4 - 10);
			ganttFCFS.setWidth(ScreenConstants.WIDTH/3);
			ganttFCFS.setX(0);
			ganttFCFS.setY(0);
			ganttFCFS.chart.setTitle("");
			ganttFCFS.setTitle("CPU Scheduling: FCFS Simulation");

			FCFSManager fcfsManager = new FCFSManager(allocatedTable, maximumTable, availableTable, timeTable, ganttFCFS, ds);
			fcfsManager.startSimulation();
		}
		
		if (algos.contains("SRTF")) {
			ganttSRTF = new GanttChartStage(numProcess);
			ganttSRTF.setHeight(ScreenConstants.HEIGHT/4 - 10);
			ganttSRTF.setWidth(ScreenConstants.WIDTH/3);
			ganttSRTF.setX(0);
			ganttSRTF.setY(ScreenConstants.HEIGHT/4);
			ganttSRTF.chart.setTitle("");
			ganttSRTF.setTitle("CPU Scheduling: SRTF Simulation");

			SRTFManager srtfManager = new SRTFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttSRTF, ds);
			srtfManager.startSimulation();
		}
		
		if (algos.contains("SJF")) {
			ganttSJF = new GanttChartStage(numProcess);
			ganttSJF.setHeight(ScreenConstants.HEIGHT/4 - 10);
			ganttSJF.setWidth(ScreenConstants.WIDTH/3);
			ganttSJF.setX(0);
			ganttSJF.setY(ScreenConstants.HEIGHT*2/4);
			ganttSJF.chart.setTitle("");
			ganttSJF.setTitle("CPU Scheduling: SJF Simulation");

			SJFManager sjfManager = new SJFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttSJF, ds);
			sjfManager.startSimulation();
		}
		
		if (algos.contains("PRIO")) {
			ganttPRIO = new GanttChartStage(numProcess);
			ganttPRIO.setHeight(ScreenConstants.HEIGHT/4 - 10);
			ganttPRIO.setWidth(ScreenConstants.WIDTH/3);
			ganttPRIO.setX(ScreenConstants.WIDTH*2/3);
			ganttPRIO.setY(0);
			ganttPRIO.chart.setTitle("");
			ganttPRIO.setTitle("CPU Scheduling: PRIO Simulation");

			PRIOManager prioManager = new PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttPRIO, ds);
			prioManager.startSimulation();
		}

		if (algos.contains("NP PRIO")) {
			ganttNPPRIO = new GanttChartStage(numProcess);
			ganttNPPRIO.setHeight(ScreenConstants.HEIGHT/4 - 10);
			ganttNPPRIO.setWidth(ScreenConstants.WIDTH/3);
			ganttNPPRIO.setX(ScreenConstants.WIDTH*2/3);
			ganttNPPRIO.setY(ScreenConstants.HEIGHT/4 - 10);
			ganttNPPRIO.chart.setTitle("");
			ganttNPPRIO.setTitle("CPU Scheduling: NP PRIO Simulation");

			NP_PRIOManager np_prioManager = new NP_PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttNPPRIO, ds);
			np_prioManager.startSimulation();
		}
		
		if (algos.contains("RR")) {
			ganttRR = new GanttChartStage(numProcess);
			ganttRR.setHeight(ScreenConstants.HEIGHT/4 - 10);
			ganttRR.setWidth(ScreenConstants.WIDTH/3);
			ganttRR.setX(ScreenConstants.WIDTH*2/3);
			ganttRR.setY(ScreenConstants.HEIGHT*2/4 - 10);
			ganttRR.chart.setTitle("");
			ganttRR.setTitle("CPU Scheduling: RR Simulation");

			RRManager rrManager = new RRManager(allocatedTable, maximumTable, availableTable, timeTable, quantumFieldText, ganttRR, ds);
			rrManager.startSimulation();
		}
	}
}
