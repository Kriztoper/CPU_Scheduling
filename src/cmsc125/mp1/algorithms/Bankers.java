package cmsc125.mp1.algorithms;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class Bankers {

	private int[][] allocatedTableData;
	private int[][] maximumTableData;
	private int[][] needTableData;
	private int[][] availableTableData;
	private int[] currentAvailableTableData;
	//private int[][] needTableData;
	private int[] arrivalTimes;
	private int[] priorityNum;
	private ProcessesQueue jobQueue;
	private int index;
	private int processCount;
	private int numProcesses;
	private int numResources;
	
	public Bankers(JTable allocatedTable, JTable maximumTable,
			JTable availableTable, int[] arrivalTimes, 
			int[] priorityNum) {
		initTableData(allocatedTable, maximumTable, availableTable);
		numProcesses = allocatedTableData.length;
		numResources = allocatedTableData[0].length;
		initCurrentAvailableTableData();
		//initNeedData();
		this.arrivalTimes = arrivalTimes;
		this.priorityNum = priorityNum;
		jobQueue = new ProcessesQueue();
		index = 0;
		processCount = 0;
	}

	public void initCurrentAvailableTableData() {
		currentAvailableTableData = new int[availableTableData[0].length];
		for (int i = 0; i < availableTableData[0].length; i++) {
			currentAvailableTableData[i] = allocatedTableData[0][i];
		}
	}
	
	private void calcNeed() {
		for (int i = 0; i < numProcesses; i++) {
			for (int j = 0; j < numResources; j++) {
				// calculating need matrix
				needTableData[i][j] = maximumTableData[i][j] - allocatedTableData[i][j];
			}
		}
	}
	
	private boolean checkAllocatable(int i) {
		// checking if all resources for ith process can be allocated
		for (int j = 0; j < numResources; j++) {
			if (availableTableData[0][j] < needTableData[i][j]) {
				return false;
			}
		}

		return true;
	}
	
	public boolean isSafeState() {
		calcNeed();
		boolean done[] = new boolean[numProcesses];
		int j = 0;
		
		while (j < numProcesses) { // until all process allocated
			boolean allocated = false;
			for (int i = 0; i < numProcesses; i++) {
				if (!done[i] && checkAllocatable(i)) { // trying to allocate

					for (int k = 0; k < numResources; k++) {
						availableTableData[0][k] = availableTableData[0][k] - needTableData[i][k] + maximumTableData[i][k];
					}
					System.out.println("Allocated process : " + i);
					allocated = done[i] = true;
					j++;
				}
			}
			if (!allocated) { // no allocation
				break;
			}
		}
		if (j == numProcesses) { // all processes are allocated
			System.out.println("\nSAFE STATE!");
			return true;
		} else {
			System.out.println("DEADLOCK!");		
			return false;
		}
	}
	
	public void initTableData(JTable allocatedTable,
			JTable maximumTable, JTable availableTable) {
		String[][] allocatedData = ((ResourcesTableModel) 
				allocatedTable.getModel()).getData();
		allocatedTableData = new int[allocatedData.length][allocatedData[0].length];

		String[][] maximumData = ((ResourcesTableModel) 
				maximumTable.getModel()).getData();
		maximumTableData = new int[maximumData.length][maximumData[0].length];
		
		String[][] availableData = ((ResourcesTableModel) 
				availableTable.getModel()).getData();
		availableTableData = new int[availableData.length][availableData[0].length];
		
		needTableData = new int[allocatedData.length][allocatedData[0].length];
		
		for (int i = 0; i < allocatedData.length; i++) {
			for (int j = 0; j < allocatedData[0].length; j++) {
				allocatedTableData[i][j] = 
						Integer.parseInt(allocatedData[i][j]);
				maximumTableData[i][j] = 
						Integer.parseInt(maximumData[i][j]);
				availableTableData[i][j] = 
						Integer.parseInt(availableData[i][j]);
			}
		}
	}

	public void allocateResource(int t) {
		while (index < arrivalTimes.length) {
			if (arrivalTimes[index] == t) {
				boolean canAllocate = true;
				int[] neededData = new int[maximumTableData[index].length];
				for (int i = 0; i < maximumTableData[index].length; i++) {
					neededData[i] = maximumTableData[index][i] - allocatedTableData[index][i];
					if (neededData[i] > currentAvailableTableData[i]) {
						canAllocate = false;
						break;
					}
				}
				if (canAllocate) {
					for (int i = 0; i < currentAvailableTableData.length; i++) {
						currentAvailableTableData[i] -= neededData[i];
						allocatedTableData[index][i] += neededData[i];
					}
					jobQueue.enqueue(new Process(arrivalTimes[index], priorityNum[index], allocatedTableData[index], ("P" + processCount), ColorConstants.getColor(processCount)));
				}
			}
			
			if (index == arrivalTimes.length - 1) {
				index = -1;
			}
			
			index++;
		}
	}
	
	public ProcessesQueue getJobQueue() {
		return jobQueue;
	}
}
