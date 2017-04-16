package cmsc125.mp1.controller.algo;

import java.util.Vector;

import javax.swing.JTable;

import cmsc125.mp1.controller.datastructures.ProcessesQueue;
import cmsc125.mp1.controller.utils.ResourcesTableModel;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.view.panels.SimulationPanel;

public class FCFSManager {

	private SimulationPanel simulationPanel;
	private JTable resourcesTable;
	private JTable timeTable;
	private Vector<Process> processesVector;
	private ProcessesQueue processesQueue;
	
	public FCFSManager(SimulationPanel simulationPanel, 
			JTable resourcesTable, JTable timeTable) {
		this.simulationPanel = simulationPanel;
		this.resourcesTable = resourcesTable;
		this.timeTable = timeTable;
	}

	public void startSimulation() {
		initProcessesInVector();
		sortProcessesToReadyQueue();
		
		start();
	}

	public void start() {
		long increment = 2000;
		Process currentProcess = null;
		int currentBurstTime = 0;
		int t = 0;
		int processNum = 0;
		int queueSize = processesQueue.getSize();
		
		while (processNum <= queueSize) {
			System.out.println("At time " + t);
			if (processesQueue.isEmpty() &&
					currentProcess == null) {
				break;
			} else if (currentProcess == null &&
					processesQueue.peek().
					getArrivalTime() <= t) {
				currentProcess = processesQueue.dequeue();
				currentBurstTime++;
				processNum++;
				System.out.println("processNum increased to "
						+processNum);
			
				System.out.println(
						currentProcess.getName() +
						"[" + currentBurstTime + "]");
			} else if (currentProcess == null &&
					processesQueue.peek().
					getArrivalTime() > t) {
				
			} else if (currentProcess != null && 
					currentBurstTime < 
					currentProcess.getResources()[0]) {
				currentBurstTime++;
				
				System.out.println(
						"P" + processNum +
						"[" + currentBurstTime + "]");
			}
			

			if (null != currentProcess &&
					currentBurstTime == 
					currentProcess.getResources()[0]) {
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
		System.out.println("Brexit!");
	}
	
	public void sortProcessesToReadyQueue() {
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
				if (processesVector.get(j).
						getArrivalTime() > 
						processesVector.get(j + 1).
						getArrivalTime()) {
					Process temp = processesVector.get(j);
					processesVector.set(j, 
							processesVector.get(j + 1));
					processesVector.set(j + 1, temp);
				}
			}
		}
	}
	
	public void initProcessesInVector() {
		processesVector = new Vector<Process>();
		String[][] resourcesData = ((ResourcesTableModel) 
				resourcesTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) 
				timeTable.getModel()).getData();
		
		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(
					Integer.parseInt(timeData[i][0]),
					convertToIntArray(resourcesData[i]),
					("P" + (i + 1))));
		}
		
		for (Process process: processesVector) {
			System.out.println(process);
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
