package cmsc125.mp1.algorithms;

import javax.swing.JTable;

import cmsc125.mp1.constants.ColorConstants;
import cmsc125.mp1.model.Process;
import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class Bankers {

	private int[][] allocatedTableData;
	private int[][] maximumTableData;
	private int[][] availableTableData;
	private int[] currentAvailableTableData;
	//private int[][] needTableData;
	private int[] arrivalTimes;
	private int[] priorityNum;
	private ProcessesQueue readyQueue;
	private int index;
	private int processCount;
	
	public Bankers(JTable allocatedTable, JTable maximumTable,
			JTable availableTable, int[] arrivalTimes, 
			int[] priorityNum) {
		initTableData(allocatedTable, maximumTable, availableTable);
		initCurrentAvailableTableData();
		//initNeedData();
		this.arrivalTimes = arrivalTimes;
		this.priorityNum = priorityNum;
		readyQueue = new ProcessesQueue();
		index = 0;
		processCount = 0;
	}

	public void initCurrentAvailableTableData() {
		currentAvailableTableData = new int[availableTableData[0].length];
		for (int i = 0; i < availableTableData[0].length; i++) {
			currentAvailableTableData[i] = allocatedTableData[0][i];
		}
	}
	
	/*public void initNeedData() {
		needTableData = new int[maximumTableData.length][maximumTableData[0].length];
		for (int i = 0; i < maximumTableData.length; i++) {
			for (int j = 0; j < maximumTableData[i].length; j++) {
				needTableData[i][j] = maximumTableData[i][j] - allocatedTableData[i][j];
			}
		}
	}*/
	
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
					readyQueue.enqueue(new Process(arrivalTimes[index], priorityNum[index], allocatedTableData[index], ("P" + processCount), ColorConstants.getColor(processCount)));
				}
			}
			
			if (index == arrivalTimes.length - 1) {
				index = -1;
			}
			
			index++;
		}
	}
	
	public ProcessesQueue getReadyQueue() {
		return readyQueue;
	}
}
