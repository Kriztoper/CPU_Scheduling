package cmsc125.mp1.algorithms; 
 
import java.awt.Color;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;
import cmsc125.mp1.view.SimulationPanel;

public class SJFManager extends Thread {

	private SimulationPanel simulationPanel;
	private JTable resourcesTable;
	private JTable timeTable;
	private ProcessesQueue jobQueue;
	private Vector<Process> readyQueue;
	private Vector<Process> processesVector;
	private Vector<Process> origProcessesVector;
	private int[] arrivalTimes;
	private int processIndex;
	private int xProcess;
	private int yProcess;
	
	public SJFManager(SimulationPanel simulationPanel, 
			JTable resourcesTable, JTable timeTable) {
		this.simulationPanel = simulationPanel;
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
		
		JLabel[] processLabels = new JLabel[processesVector.size()];
		int x = 5;
		int y = 80;
		jobQueue = new ProcessesQueue();
		for (int i = 0; i < processesVector.size(); i++) {
			processLabels[i] = new JLabel(
					processesVector.get(i).getName());
			processLabels[i].setBorder(
					new LineBorder(Color.BLACK));
			processLabels[i].setBackground(
					processesVector.get(i).getColor());
			processLabels[i].setOpaque(true);
			processLabels[i].setSize(30, 50);
			processLabels[i].setLocation(x, y);
			x += processLabels[i].getWidth() + 1;
			simulationPanel.add(processLabels[i]);
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
		long increment = 200;//0;
		int t = 0;
		int processNum = 0;
		int processesVectorSize = processesVector.size();
		Process currentProcess = null;
		int currentBurstTime = 0;
		
		JLabel[] processLabels = new JLabel[processesVector.size()];
		xProcess = 5;
		yProcess = 200;
		processIndex = 0;
		
		while (processNum <= processesVectorSize) {
			System.out.println("At time " + t);
			fillReadyQueue(t);
			sortReadyQueue();
			if (processesVector.isEmpty() && 
					readyQueue.isEmpty() &&
					currentProcess == null) {
				break;
			} else if (currentProcess == null && 
					readyQueue.get(0).
					getArrivalTime() <= t) {
				currentProcess = readyQueue.remove(0);
				currentBurstTime++;
				processIndex = processNum;
				processNum++;
				
				addProcessLabel(processLabels, currentProcess);
				
				System.out.println(
						currentProcess.getName() +
						"[" + currentBurstTime + "]");
			} else if (currentProcess != null && 
					currentBurstTime < 
					currentProcess.getBurstTime()) {
				currentBurstTime++;
				
				addProcessLabel(processLabels, currentProcess);
				
				System.out.println(
						currentProcess.getName() +
						"[" + currentBurstTime + "]");
			}
			
			if (null != currentProcess &&
					currentBurstTime == 
					currentProcess.getBurstTime()) {
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
		System.out.println("Done executing SJF!");
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
	
	public void addProcessLabel(JLabel[] processLabels, 
			Process process) {
		processLabels[processIndex] = new JLabel(
				process.getName());
		processLabels[processIndex].setBackground(
				process.getColor());
		processLabels[processIndex].setBorder(
				new LineBorder(Color.BLACK));
		processLabels[processIndex].setOpaque(true);
		processLabels[processIndex].setSize(30, 50);
		processLabels[processIndex].setLocation(xProcess, yProcess);
		xProcess += processLabels[processIndex].getWidth() + 1;
		simulationPanel.add(processLabels[processIndex]);
		simulationPanel.repaint();
	}
}
 