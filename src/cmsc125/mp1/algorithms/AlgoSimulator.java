package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChartStage;

public class AlgoSimulator{

	private ArrayList<String> algos;
	private JTable resourcesTable;
	private JTable timeTable;
	private String quantumFieldText;
	private int numProcess;
	public static int visualizationSpeed;
	
	public AlgoSimulator(int numProcess, ArrayList<String> algos, JTable resourcesTable,
			JTable timeTable, String quantumFieldText, int visualizationSpeed) {
		this.numProcess = numProcess;
		this.algos = algos;
		this.resourcesTable = resourcesTable;
		this.timeTable = timeTable;
		this.quantumFieldText = quantumFieldText;
		this.visualizationSpeed = visualizationSpeed*1000;
	}
	
	private String prepareGanttInfo(){
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// block to print the resources and arrival time
		String string = "";
		String[][] data = ((ResourcesTableModel) resourcesTable.getModel()).getData();
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
			Main.ganttVisual.initGantt(prepareGanttInfo(),numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("FCFS Simulation");
			Main.ganttVisual.setTitle("FCFS Simulation");
			
			FCFSManager fcfsManager = new FCFSManager(resourcesTable, timeTable);
			fcfsManager.startSimulation();
		} else if (algos.contains("SJF")) {	
	        Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(),numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("SJF Simulation");
			Main.ganttVisual.setTitle("SJF Simulation");
			
			SJFManager sjfManager = new SJFManager(resourcesTable, timeTable);
			sjfManager.startSimulation();
		} else if (algos.contains("RR")) {
	        Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(),numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("RR Simulation");
			Main.ganttVisual.setTitle("RR Simulation");
			
			RRManager rrManager = new RRManager(resourcesTable, timeTable, quantumFieldText);
			rrManager.startSimulation();
		} else if (algos.contains("NP PRIO")) {
	        Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(),numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("NP PRIO Simulation");
			Main.ganttVisual.setTitle("NP PRIO Simulation");
			
			NP_PRIOManager np_prioManager = new NP_PRIOManager(resourcesTable, timeTable);
			np_prioManager.startSimulation();
		} else if (algos.contains("SRTF")) {	
	        Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(),numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("SRTF Simulation");
			Main.ganttVisual.setTitle("SRTF Simulation");
			
			SRTFManager srtfManager = new SRTFManager(resourcesTable, timeTable);
			srtfManager.startSimulation();
		} else if (algos.contains("PRIO")) {
	        Main.ganttVisual = new GanttChartStage();
			Main.ganttVisual.initGantt(prepareGanttInfo(),numProcess);
			Main.ganttVisual.setX(0);
			Main.ganttVisual.setY(230);
			Main.ganttVisual.chart.setTitle("PRIO Simulation");
			Main.ganttVisual.setTitle("PRIO Simulation");
			
			PRIOManager prioManager = new PRIOManager(resourcesTable, timeTable);
			prioManager.startSimulation();
		}
	}
}

