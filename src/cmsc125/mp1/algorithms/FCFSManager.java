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

public class FCFSManager extends Thread {

	private SimulationPanel simulationPanel;
	private JTable resourcesTable;
	private JTable timeTable;
	private Vector<Process> processesVector;
	private ProcessesQueue processesQueue;
	private int xProcess; 
	private int yProcess; 
	private int processIndex; 

	public FCFSManager(SimulationPanel simulationPanel, JTable resourcesTable, JTable timeTable) {
		this.simulationPanel = simulationPanel;
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
		long increment = 2000;
		Process currentProcess = null;
		int currentBurstTime = 0;
		int t = 0;
		int processNum = 0;
		int queueSize = processesQueue.getSize();

		JLabel[] processLabels = new JLabel[processesVector.size()];
	    xProcess = 5; 
	    yProcess = 200; 
	    processIndex = 0; 

		while (processNum <= queueSize) {
			System.out.println("At time " + t);
			if (processesQueue.isEmpty() && currentProcess == null) {
				break;
			} else if (currentProcess == null && processesQueue.peek().getArrivalTime() <= t) {
				currentProcess = processesQueue.dequeue();
				currentBurstTime++;
				processNum++;
				processIndex = processNum - 1; 
				// System.out.println("processNum increased to "
				// +processNum);

				addProcessLabel(processLabels);

				System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
			} else if (currentProcess == null && processesQueue.peek().getArrivalTime() > t) {
				// TODO: Implement this special case...
			} else if (currentProcess != null && currentBurstTime < currentProcess.getResources()[0]) {
				currentBurstTime++;

				addProcessLabel(processLabels); 

				System.out.println(currentProcess.getName() + "[" + currentBurstTime + "]");
			}

			if (null != currentProcess && currentBurstTime == currentProcess.getResources()[0]) {
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

	 public void addProcessLabel(JLabel[] processLabels) { 
		    ColorConstants colorConstants = new ColorConstants(); 
		     
		    processLabels[processIndex] = new JLabel( 
		        processesVector.get(processIndex).getName()); 
		    processLabels[processIndex].setBackground( 
		        colorConstants.getColors()[processIndex]); 
		    processLabels[processIndex].setBorder( 
		        new LineBorder(Color.BLACK)); 
		    processLabels[processIndex].setOpaque(true); 
		    processLabels[processIndex].setSize(30, 50); 
		    processLabels[processIndex].setLocation(xProcess, yProcess); 
		    xProcess += processLabels[processIndex].getWidth() + 1; 
		    simulationPanel.add(processLabels[processIndex]); 
		    simulationPanel.repaint(); 
		  } 
	
	public void sortProcessesToReadyQueue() {
		sortProcessesVector();

		JLabel[] processLabels = new JLabel[processesVector.size()];
		ColorConstants colorConstants = new ColorConstants();
		int x = 5;
		int y = 80;
		processesQueue = new ProcessesQueue();
		for (int i = 0; i < processesVector.size(); i++) {
			processLabels[i] = new JLabel(processesVector.get(i).getName());
			processLabels[i].setBorder(new LineBorder(Color.BLACK));
			processLabels[i].setBackground(colorConstants.getColors()[i]);
			processLabels[i].setOpaque(true);
			processLabels[i].setSize(30, 50);
			processLabels[i].setLocation(x, y);
			x += processLabels[i].getWidth() + 1;
			simulationPanel.add(processLabels[i]);
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

	public void initProcessesInVector() {
		processesVector = new Vector<Process>();
		String[][] resourcesData = ((ResourcesTableModel) resourcesTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel) timeTable.getModel()).getData();

		for (int i = 0; i < timeData.length; i++) {
			processesVector.add(new Process(Integer.parseInt(timeData[i][0]), convertToIntArray(resourcesData[i]),
					("P" + (i + 1))));
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
