package cmsc125.mp1.algorithms;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.GanttChartStage;

public class SRTFManager extends Thread {

	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JTable timeTable;
	private int[] arrivalTimes;
	private int[] priorityNumbers;
	private Vector<Process> processesVector;
	private Vector<Process> origProcessesVector;
	private ProcessesQueue processesQueue;
	private ProcessesQueue readyQueue;
	private Bankers bankers;
	private GanttChartStage ganttChart;

	public SRTFManager(JTable allocatedTable, JTable maximumTable, JTable availableTable, JTable timeTable, GanttChartStage ganttChart) {
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
		this.timeTable = timeTable;
		this.ganttChart = ganttChart;
	}

	public void startSimulation() {
		initProcessesInVector();
		sortProcessesToProcessesQueue();
		setListOfArrivalTimes();
		readyQueue = new ProcessesQueue();

		start();
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

		if (bankers.isSafeState()) {
			while (true) {
				System.out.println("At time " + t);
				bankers.updateJobQueue(t, processesQueue);
				ganttChart.displayUpdatedJobQueue(bankers.getJobQueue());
				readyQueue = bankers.requestResources(t, readyQueue);
				ganttChart.displayUpdatedReadyQueue(readyQueue);
				
//				fillReadyQueue(t);
				sortReadyQueue();
				
				// If ready queue is empty and there is no current process running
				if (processesQueue.isEmpty() && readyQueue.isEmpty() && currentProcess == null) {
					System.out.println("Ready Queue is empty!");
					break;
				} else if (!readyQueue.isEmpty() && currentProcess == null && readyQueue.get(0).getArrivalTime() <= t) {
					// there is a process waiting in ready queue available for execution and there
					// is no current process running
					
					currentProcess = readyQueue.get(0);
					currentProcess.decBurstTime();
					
					ganttChart.updateGantt(t, currentProcess.getName());
	
					System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
				}
	
				if (currentProcess != null && currentProcess.getBurstTime() == 0) {
					currentProcess.setCompletionTime(t + 1);
					currentProcess.setTurnaroundTime(currentProcess.getCompletionTime() - currentProcess.getArrivalTime());
					currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getBurstTime());
					bankers.releaseResourcesForProcess(currentProcess);
					readyQueue.dequeue();
				}
	
				currentProcess = null;
	
				try {
					this.sleep(AlgoSimulator.visualizationSpeed); //delay
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				t++;
			}
			System.out.println("Done executing SRTF!");
			
			bankers.setAvgCompletionTime(0.0);
			bankers.setAvgTurnaroundTime(0.0);
			bankers.setAvgWaitingTime(0.0);
			for (int i = 0; i < processesVector.size(); i++) {
				Process process = processesVector.get(i);
				System.out.println(process.getName() + " CT=" + process.getCompletionTime() + ", TAT=" + process.getTurnaroundTime() + ", WT=" + process.getWaitingTime());
				bankers.setAvgCompletionTime(bankers.getAvgCompletionTime() + ((double) process.getCompletionTime()));
				bankers.setAvgTurnaroundTime(bankers.getAvgTurnaroundTime() + ((double) process.getTurnaroundTime()));
				bankers.setAvgWaitingTime(bankers.getAvgWaitingTime() + ((double) process.getWaitingTime()));
			}
			
			bankers.setAvgCompletionTime((bankers.getAvgCompletionTime()) / ((double) processesVector.size()));
			bankers.setAvgTurnaroundTime(bankers.getAvgTurnaroundTime() / ((double) processesVector.size()));
			bankers.setAvgWaitingTime(bankers.getAvgWaitingTime() / ((double) processesVector.size()));
			
			System.out.println("Avg CT = " + bankers.getAvgCompletionTime() + ", Avg TAT = " + bankers.getAvgTurnaroundTime() + ", Avg WT = " + bankers.getAvgWaitingTime());
		} else {
			System.exit(0);
		}
	}

	public void setListOfArrivalTimes() {
		int size = processesVector.size();
		arrivalTimes = new int[size];
		for (int i = 0; i < size; i++) {
			arrivalTimes[i] = processesVector.get(i).getArrivalTime();
		}
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
	
	public void sortReadyQueue() {
		int size = readyQueue.getSize();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (readyQueue.get(j).getBurstTime() > readyQueue.get(j + 1).getBurstTime()) {
					Process temp = readyQueue.get(j);
					readyQueue.set(j, readyQueue.get(j + 1));
					readyQueue.set(j + 1, temp);
				}
			}
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
					temp = origProcessesVector.get(j);
					origProcessesVector.set(j, origProcessesVector.get(j + 1));
					origProcessesVector.set(j + 1, temp);
				}
			}
		}
	}

	public void initProcessesInVector() {
		processesVector = new Vector<Process>();
		origProcessesVector = new Vector<Process>();
		String[][] resourcesData = ((ResourcesTableModel) allocatedTable.getModel()).getData();
		String[][] burstData = ((ResourcesTableModel) maximumTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();

		for (int i = 0; i < timeData.length; i++) {
			resourcesData[i][0] = burstData[i][0];
			processesVector.add(new Process(Integer.parseInt(timeData[i][0]), Integer.parseInt(timeData[i][1]),
					convertToIntArray(resourcesData[i]), ("P" + (i)), ColorConstants.getColor(i)));
			origProcessesVector.add(processesVector.get(processesVector.size() - 1));
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
