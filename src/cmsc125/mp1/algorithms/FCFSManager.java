package cmsc125.mp1.algorithms;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class FCFSManager extends Thread {

	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JTable timeTable;
	private int[] arrivalTimes;
	private int[] priorityNumbers;
	private Vector<Process> processesVector;
	private ProcessesQueue readyQueue;
	private Bankers bankers;

	public FCFSManager(JTable allocatedTable, JTable maximumTable, JTable availableTable, JTable timeTable) {
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
	}

	public void startSimulation() {
		initTimeTableData();
		initProcessesInVector();
		sortProcessesToReadyQueue();

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
			System.out.println("At time " + t);
			// bankers.allocateResource(t);
			// bankers.getReadyQueue().sortByArrivalTime();
			// readyQueue = bankers.getReadyQueue();
			if (readyQueue.isEmpty() && currentProcess == null) {
				break;
			} else if (currentProcess == null && readyQueue.peek().getArrivalTime() <= t) {
				currentProcess = readyQueue.dequeue();
				currentBurstTime++;
				// System.out.println("processNum increased to "
				// +processNum);

				Main.ganttVisual.updateGantt(t, currentProcess.getName());

				System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
			} else if (currentProcess != null && currentBurstTime < currentProcess.getBurstTime()) {
				currentBurstTime++;

				Main.ganttVisual.updateGantt(t, currentProcess.getName());

				System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
			}

			if (null != currentProcess && currentBurstTime == currentProcess.getBurstTime()) {
				currentProcess = null;
				currentBurstTime = 0;
			}

			try {
				this.sleep(AlgoSimulator.visualizationSpeed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t++;
		}
		System.out.println("Done executing FCFS!");
	}

	public void sortProcessesToReadyQueue() {
		sortProcessesVector();
		readyQueue = new ProcessesQueue();
		for (int i = 0; i < processesVector.size(); i++) {
			readyQueue.enqueue(processesVector.get(i));
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
					convertToIntArray(resourcesData[i]), ("P" + i), ColorConstants.getColor(i)));
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
