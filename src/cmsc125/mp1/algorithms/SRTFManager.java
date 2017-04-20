package cmsc125.mp1.algorithms;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class SRTFManager extends Thread {

	private JTable resourcesTable;
	private JTable timeTable;
	private ProcessesQueue jobQueue;
	private Vector<Process> readyQueue;
	private Vector<Process> processesVector;
	private Vector<Process> origProcessesVector;
	private int[] arrivalTimes;
	
	public SRTFManager(JTable resourcesTable, JTable timeTable) {
		this.resourcesTable = resourcesTable;
		this.timeTable = timeTable;
		readyQueue = new Vector<Process>();
	}

	public void startSimulation() {
		initProcessesInVector();
		sortProcessesToJobQueue();
		setListOfArrivalTimes();
		
		start();
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
				if (processesVector.get(j).
						getArrivalTime() > 
						processesVector.get(j + 1).
						getArrivalTime()) {
					Process temp = processesVector.get(j);
					processesVector.set(j, 
							processesVector.get(j + 1));
					processesVector.set(j + 1, temp);
					temp = origProcessesVector.get(j);
					origProcessesVector.set(j, 
							origProcessesVector.get(j + 1));
					origProcessesVector.set(j + 1, temp);
				}
			}
		}
	}
	
	public void setListOfArrivalTimes() {
		int size = processesVector.size();
		arrivalTimes = new int[size];
		for (int i = 0; i< size; i++) {
			arrivalTimes[i] = 
					processesVector.get(i).getArrivalTime();
		}
	}
	
	public void initProcessesInVector() {
		processesVector = new Vector<Process>();
		origProcessesVector = new Vector<Process>();
		String[][] resourcesData = ((ResourcesTableModel) 
				resourcesTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) 
				timeTable.getModel()).getData();
		
		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(
					Integer.parseInt(timeData[i][0]),
					Integer.parseInt(timeData[i][1]),
					convertToIntArray(resourcesData[i]),
					("P" + (i + 1)),
					ColorConstants.getColor(i)));
			origProcessesVector.add(processesVector.
					get(processesVector.size() - 1));
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
	
	@Override
	public void run() {
		long increment = 2000;//0;
		int t = 0;
		Process currentProcess = null;
		int currentBurstTime = 0;
		
		while (true) {
			System.out.println("At time " + t);
			fillReadyQueue(t);
			sortReadyQueue();
			if (readyQueue.isEmpty() &&
					currentProcess == null) {
				break;
			} else if (readyQueue.get(0).
					getArrivalTime() <= t) {
				currentProcess = readyQueue.get(0);
				currentProcess.decBurstTime();
				
				Main.ganttVisual.updateGantt(t, currentProcess.getName());
				
				System.out.println(
						currentProcess.getName() +
						"[" + currentBurstTime + "]");
			}
			
			if (currentProcess.getBurstTime() == 0) {
				readyQueue.remove(0);
			}
			
			currentProcess = null;

			try {
				Thread.sleep(increment);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			t++;
		}
		System.out.println("Done executing SRTF!");
	}
	
	public void sortReadyQueue() {
		int size = readyQueue.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (readyQueue.get(j).
						getBurstTime() > 
					readyQueue.get(j + 1).
						getBurstTime()) {
					Process temp = readyQueue.get(j);
					readyQueue.set(j, 
							readyQueue.get(j + 1));
					readyQueue.set(j + 1, temp);
				}
			}
		}
	}
	
	public void fillReadyQueue(int t) {
		int size = processesVector.size();
		int[] indicesToRemove = new int[size];
		int index = 0;
		for (int i = 0; i < size; i++) {
			if (processesVector.get(i).
					getArrivalTime() <= t) {
				readyQueue.add(processesVector.get(i));
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
}

