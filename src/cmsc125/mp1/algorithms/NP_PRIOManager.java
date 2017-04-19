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

public class NP_PRIOManager extends Thread {

	private SimulationPanel simulationPanel;
	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JTable timeTable;
	private ProcessesQueue jobQueue;
	private Vector<Process> readyQueue;
	private Vector<Process> processesVector;
	private Vector<Process> origProcessesVector;
	private int[] arrivalTimes;
	private int xProcess;
	private int yProcess;
	private Bankers bankers;
	
	public NP_PRIOManager(SimulationPanel simulationPanel, 
			JTable allocatedTable, 
			JTable maximumTable,
			JTable availableTable,
			JTable timeTable) {
		this.simulationPanel = simulationPanel;
		this.allocatedTable = allocatedTable;
		this.maximumTable = maximumTable;
		this.availableTable = availableTable;
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
				allocatedTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) 
				timeTable.getModel()).getData();
		
		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(
					Integer.parseInt(timeData[i][0]),
					Integer.parseInt(timeData[i][1]),
					convertToIntArray(resourcesData[i]),
					("P" + i),
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
	
	public int[] getArrivalTimes() {
		String[][] timeData = ((ResourcesTableModel) 
				timeTable.getModel()).getData();
		int[] arrivalTimes = new int[timeData.length];
		for (int i = 0; i < timeData.length; i++) {
			arrivalTimes[i] = Integer.parseInt(timeData[i][0]);
		}
		
		return arrivalTimes;
	}
	
	@Override
	public void run() {
		bankers = new Bankers(allocatedTable, maximumTable, 
				availableTable, getArrivalTimes());
		long increment = 200;//0;
		int t = 0;
		Process currentProcess = null;
		int currentBurstTime = 0;
		xProcess = 5;
		yProcess = 200;
		
		while (true) {
			System.out.println("At time " + t);
			fillReadyQueue(t);
			sortReadyQueue();
			if (readyQueue.isEmpty() &&
					currentProcess == null) {
				break;
			} else if (currentProcess == null && 
					readyQueue.get(0).
					getArrivalTime() <= t) {
				currentProcess = readyQueue.remove(0);
				currentBurstTime++;
				
				addProcessLabel(currentProcess);
				
				System.out.println(
						currentProcess.getName() +
						"[" + currentBurstTime + "]");
			} else if (currentProcess != null && 
					currentBurstTime < 
					currentProcess.getBurstTime()) {
				currentBurstTime++;
				
				addProcessLabel(currentProcess);
				
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
		System.out.println("Done executing NP PRIO!");
	}
	
	public void sortReadyQueue() {
		int size = readyQueue.size();
		for (int i = 0; i < (size - 1); i++) {
			for (int j = 0; j < size - i - 1; j++) {
				if (readyQueue.get(j).
						getPriorityNum() > 
					readyQueue.get(j + 1).
						getPriorityNum()) {
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
	
	public void addProcessLabel(Process process) {
		JLabel processLabel = new JLabel(process.getName());
		processLabel.setBackground(process.getColor());
		processLabel.setBorder(new LineBorder(Color.BLACK));
		processLabel.setOpaque(true);
		processLabel.setSize(30, 50);
		processLabel.setLocation(xProcess, yProcess);
		xProcess += processLabel.getWidth() + 1;
		simulationPanel.add(processLabel);
		simulationPanel.repaint();
	}
}
