package cmsc125.mp1.algorithms;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.controller.Main;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class FCFSManager extends Thread {

	private JTable resourcesTable;
	private JTable timeTable;
	private Vector<Process> processesVector;
	private ProcessesQueue jobQueue;
	
	public FCFSManager(JTable resourcesTable, JTable timeTable) {
		this.resourcesTable = resourcesTable;
		this.timeTable = timeTable;
	}

	public void startSimulation() {
		initProcessesInVector();
		sortProcessesToReadyQueue();

		start();
	}

	@Override
	public void run() {
		long increment = 2000;// 0;
		Process currentProcess = null;
		int currentBurstTime = 0;
		int t = 0;

		while (true) {
			System.out.println("At time " + t);
			if (jobQueue.isEmpty() && currentProcess == null) {
				break;
			} else if (currentProcess == null && jobQueue.peek().getArrivalTime() <= t) {
				currentProcess = jobQueue.dequeue();
				currentBurstTime++;
				//System.out.println("processNum increased to "
				//		+processNum);
				
	        	Main.ganttVisual.updateGantt(t, currentProcess.getName());
				
				System.out.println(
						currentProcess.getName() +
						"[" + currentBurstTime + "]");
			} else if (currentProcess != null && 
					currentBurstTime < 
					currentProcess.getBurstTime()) {
				currentBurstTime++;

				Main.ganttVisual.updateGantt(t, currentProcess.getName());

				System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
			}

			if (null != currentProcess && currentBurstTime == currentProcess.getBurstTime()) {
				currentProcess = null;
				currentBurstTime = 0;
			}

			try {
				Thread.sleep(increment);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			t++;
		}
		System.out.println("Done executing FCFS!");
	}


	public void sortProcessesToReadyQueue() {
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
		String[][] resourcesData = ((ResourcesTableModel) resourcesTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();

		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(Integer.parseInt(timeData[i][0]), Integer.parseInt(timeData[i][1]),
					convertToIntArray(resourcesData[i]), ("P" + (i + 1)), ColorConstants.getColor(i)));
		}

		/*
		 * for (Process process: processesVector) { System.out.println(process);
		 * }
		 */
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
