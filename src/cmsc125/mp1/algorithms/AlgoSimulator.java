package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.constants.ScreenConstants;
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
		AlgoSimulator.visualizationSpeed = visualizationSpeed;
	}

	public void startSimulation() {
		if (algos.contains("FCFS")) {
			ganttFCFS = new GanttChartStage(numProcess);
			ganttFCFS.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttFCFS.setWidth(ScreenConstants.WIDTH/3);
			ganttFCFS.setX(0);
			ganttFCFS.setY(0);
			ganttFCFS.chart.setTitle("FCFS Simulation");
			ganttFCFS.setTitle("CPU Scheduling: FCFS Simulation");

			FCFSManager fcfsManager = new FCFSManager(allocatedTable, maximumTable, availableTable, timeTable, ganttFCFS);
			fcfsManager.startSimulation();
		}
		
		if (algos.contains("SRTF")) {
			ganttSRTF = new GanttChartStage(numProcess);
			ganttSRTF.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttSRTF.setWidth(ScreenConstants.WIDTH/3);
			ganttSRTF.setX(ScreenConstants.WIDTH/3);
			ganttSRTF.setY(0);
			ganttSRTF.chart.setTitle("SRTF Simulation");
			ganttSRTF.setTitle("CPU Scheduling: SRTF Simulation");

			SRTFManager srtfManager = new SRTFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttSRTF);
			srtfManager.startSimulation();
		}
		
		if (algos.contains("SJF")) {
			ganttSJF = new GanttChartStage(numProcess);
			ganttSJF.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttSJF.setWidth(ScreenConstants.WIDTH/3);
			ganttSJF.setX(ScreenConstants.WIDTH*2/3);
			ganttSJF.setY(0);
			ganttSJF.chart.setTitle("SJF Simulation");
			ganttSJF.setTitle("CPU Scheduling: SJF Simulation");

			SJFManager sjfManager = new SJFManager(allocatedTable, maximumTable, availableTable, timeTable, ganttSJF);
			sjfManager.startSimulation();
		}
		
		if (algos.contains("PRIO")) {
			ganttPRIO = new GanttChartStage(numProcess);
			ganttPRIO.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttPRIO.setWidth(ScreenConstants.WIDTH/3);
			ganttPRIO.setX(0);
			ganttPRIO.setY(ScreenConstants.HEIGHT/2 - 10);
			ganttPRIO.chart.setTitle("PRIO Simulation");
			ganttPRIO.setTitle("CPU Scheduling: PRIO Simulation");

			PRIOManager prioManager = new PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttPRIO);
			prioManager.startSimulation();
		}

		if (algos.contains("NP PRIO")) {
			ganttNPPRIO = new GanttChartStage(numProcess);
			ganttNPPRIO.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttNPPRIO.setWidth(ScreenConstants.WIDTH/3);
			ganttNPPRIO.setX(ScreenConstants.WIDTH/3);
			ganttNPPRIO.setY(ScreenConstants.HEIGHT/2 - 10);
			ganttNPPRIO.chart.setTitle("NP PRIO Simulation");
			ganttNPPRIO.setTitle("CPU Scheduling: NP PRIO Simulation");

			NP_PRIOManager np_prioManager = new NP_PRIOManager(allocatedTable, maximumTable, availableTable, timeTable, ganttNPPRIO);
			np_prioManager.startSimulation();
		}
		
		if (algos.contains("RR")) {
			ganttRR = new GanttChartStage(numProcess);
			ganttRR.setHeight(ScreenConstants.HEIGHT/2 - 10);
			ganttRR.setWidth(ScreenConstants.WIDTH/3);
			ganttRR.setX(ScreenConstants.WIDTH*2/3);
			ganttRR.setY(ScreenConstants.HEIGHT/2 - 10);
			ganttRR.chart.setTitle("RR Simulation");
			ganttRR.setTitle("CPU Scheduling: RR Simulation");

			RRManager rrManager = new RRManager(allocatedTable, maximumTable, availableTable, timeTable,
					quantumFieldText, ganttRR);
			rrManager.startSimulation();
		}
	}
}
