package cmsc125.mp1.algorithms;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.algorithms.disk.DiskSimulator;
import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.CPUChart;
import cmsc125.mp1.view.VisualizationStage;

public abstract class AlgoManager extends Thread {

	protected JTable allocatedTable;
	protected JTable maximumTable;
	protected JTable availableTable;
	protected JTable timeTable;
	protected int[] arrivalTimes;
	protected int[] priorityNumbers;
	protected Vector<Process> processesVector;
	protected ProcessesQueue processesQueue;
	protected ProcessesQueue readyQueue;
	protected Bankers bankers;
	protected CPUChart cpuChart;
	protected DiskSimulator ds;
	protected VisualizationStage vs;
	public static boolean isDeadlock;
	
	public AlgoManager(String name, JTable allocatedTable, JTable maximumTable, JTable availableTable, JTable timeTable, CPUChart cpuChart, DiskSimulator ds) {
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
		this.cpuChart = cpuChart;
		this.ds = ds;
		vs = new VisualizationStage(name);
		vs.vbox.getChildren().addAll(cpuChart.rootPane, cpuChart.chart, ds.diskChart.chart);
	}
	
	public int[] convertToIntArray(String[] resourcesData) {
		int size = resourcesData.length;
		int[] intData = new int[size];
		for (int i = 0; i < size; i++) {
			intData[i] = Integer.parseInt(resourcesData[i]);
		}

		return intData;
	}
	
	public void fillReadyQueue(int t) {
		int size = processesVector.size();
		int[] indicesToRemove = new int[size];
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (processesVector.get(i).getArrivalTime() <= t) {
				readyQueue.enqueue(processesVector.get(i));
				indicesToRemove[index++] = i;
			} else {
				indicesToRemove[index++] = -1;
			}
		}
		for (int i = indicesToRemove.length - 1; i >= 0; i--) {
			if (indicesToRemove[i] != -1) {
				processesVector.remove(indicesToRemove[i]);
			}
		}
	}
	
	public int[] getArrivalTimes() {
		return arrivalTimes;
	}

	public int[] getPriorityNumbers() {
		return priorityNumbers;
	}
	
	public void initProcessesInVector() {
		processesVector = new Vector<Process>();
		String[][] resourcesData = ((ResourcesTableModel) allocatedTable.getModel()).getData();
		String[][] maximumTableData = ((ResourcesTableModel) maximumTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();

		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(Integer.parseInt(timeData[i][0]), Integer.parseInt(timeData[i][1]), Integer.parseInt(maximumTableData[i][0]),
					convertToIntArray(resourcesData[i]), ("P" + (i)), ColorConstants.getColor(i)));
		}
	}
	
	public void initTimeTableData() {
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();
		arrivalTimes = new int[timeData.length];
		priorityNumbers = new int[timeData.length];
		for (int i = 0; i < timeData.length; i++) {
			arrivalTimes[i] = Integer.parseInt(timeData[i][0]);
			priorityNumbers[i] = Integer.parseInt(timeData[i][1]);
		}
	}
	
	public void setListOfArrivalTimes() {
		int size = processesVector.size();
		arrivalTimes = new int[size];
		for (int i = 0; i < size; i++) {
			arrivalTimes[i] = processesVector.get(i).getArrivalTime();
		}
	}
	
	public void sortProcessesToProcessesQueue() {
		sortProcessesVector();
		processesQueue = new ProcessesQueue();
		for (int i = 0; i < processesVector.size(); i++) {
			processesQueue.enqueue(processesVector.get(i));
		}
	}
	
	public void sortProcessesVector() {
		int size = processesVector.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (processesVector.get(j).getArrivalTime() > processesVector.get(j + 1).getArrivalTime()) {
					Process temp = processesVector.get(j);
					processesVector.set(j, processesVector.get(j + 1));
					processesVector.set(j + 1, temp);
				}
			}
		}
	}
	
	public void sortProcessesVectorByProcessNumber() {
		int size = processesVector.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (processesVector.get(j).getProcessNumber() > processesVector.get(j + 1).getProcessNumber()) {
					Process temp = processesVector.get(j);
					processesVector.set(j, processesVector.get(j + 1));
					processesVector.set(j + 1, temp);
				}
			}
		}
	}
	
	public void startSimulation() {
		initTimeTableData();
		initProcessesInVector();
		sortProcessesToProcessesQueue();
		readyQueue = new ProcessesQueue();

		start();
	}
}
