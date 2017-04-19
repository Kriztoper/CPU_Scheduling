package cmsc125.mp1.algorithms;

import javax.swing.JTable;

import cmsc125.mp1.model.ProcessesQueue;
import cmsc125.mp1.model.ResourcesTableModel;

public class Bankers {

	private int[][] allocatedTableData;
	private int[][] maximumTableData;
	private int[][] availableTableData;
	private int[] arrivalTimes;
	private ProcessesQueue readyQueue;
	
	public Bankers(JTable allocatedTable, JTable maximumTable,
			JTable availableTable, int[] arrivalTimes) {
		initTableData(allocatedTable, maximumTable, availableTable);
		this.arrivalTimes = arrivalTimes;
		readyQueue = new ProcessesQueue();
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
		
	}
}
