package cmsc125.mp1.algorithms;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChartStage;

public class FCFSManager extends Thread {

	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JTable timeTable;
	private int[] arrivalTimes;
	private int[] priorityNumbers;
	private Vector<Process> processesVector;
	private ProcessesQueue jobQueue;
	private Bankers bankers;
	private GanttChartStage ganttChart;

	public FCFSManager(JTable allocatedTable, JTable maximumTable, JTable availableTable, JTable timeTable, GanttChartStage ganttChart) {
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
		this.ganttChart = ganttChart;
	}

	public void startSimulation() {
		initTimeTableData();
		initProcessesInVector();
		sortProcessesToJobQueue();

		start();
	}

	public void initTimeTableData() {
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();
		arrivalTimes = new int[timeData.length];
		priorityNumbers = new int[timeData.length];
		for (int i = 0; i < timeData.length; i++) {
			arrivalTimes[i] = Integer.parseInt(timeData[i][0]);
			arrivalTimes[i] = Integer.parseInt(timeData[i][1]);
		}
	}

	public int[] getArrivalTimes() {
		return arrivalTimes;
	}

	public int[] getPriorityNumbers() {
		return priorityNumbers;
	}

	@Override
	public void run() {
		bankers = new Bankers(allocatedTable, maximumTable, availableTable, getArrivalTimes(), getPriorityNumbers());
		Process currentProcess = null;
		int currentBurstTime = 0;
		int t = 0;

		while (true) {
			System.out.println("FCFS: At time " + t);
			// bankers.allocateResource(t);
			// bankers.getjobQueue().sortByArrivalTime();
			// jobQueue = bankers.getjobQueue();

			// If ready queue is empty and there is no current process running
			if (jobQueue.isEmpty() && currentProcess == null) {
				break;
			} else if (currentProcess == null && jobQueue.peek().getArrivalTime() <= t) {
				// there is a process waiting in ready queue available for execution and there
				// is no current process running

				currentProcess = jobQueue.dequeue();
				currentBurstTime++;
				// System.out.println("processNum increased to "
				// +processNum);

				ganttChart.updateGantt(t, currentProcess.getName());

				System.out.println("FCFS: "+currentProcess.getName() + "[" + currentBurstTime + "]");
			} else if (currentProcess != null && currentBurstTime < currentProcess.getBurstTime()) {
				// there is still a current process executing

				currentBurstTime++;

				ganttChart.updateGantt(t, currentProcess.getName());

				System.out.println("FCFS: "+currentProcess.getName() + "[" + currentBurstTime + "]");
			}

			// there is still a current process running but it has completed executing
			if (currentProcess != null && currentBurstTime == currentProcess.getBurstTime()) {
				currentProcess = null;
				currentBurstTime = 0;
			}

			try {
				this.sleep(AlgoSimulator.visualizationSpeed); // delay
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t++;
		}
		System.out.println("Done executing FCFS!");
	}

	public void sortProcessesToJobQueue() {
		sortProcessesVector();
		jobQueue = new ProcessesQueue();
		for (int i = 0; i < processesVector.size(); i++) {
			jobQueue.enqueue(processesVector.get(i));
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

	public void initProcessesInVector() {
		processesVector = new Vector<Process>();
		String[][] resourcesData = ((ResourcesTableModel) allocatedTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();

		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(Integer.parseInt(timeData[i][0]), Integer.parseInt(timeData[i][1]),
					convertToIntArray(resourcesData[i]), ("P" + (i + 1)), ColorConstants.getColor(i)));
		}
	}

	public int[] convertToIntArray(String[] resourcesData) {
		int size = resourcesData.length;
		int[] intData = new int[size];
		for (int i = 0; i < size; i++) {
			intData[i] = Integer.parseInt(resourcesData[i]);
		}

		return intData;
	}
}
