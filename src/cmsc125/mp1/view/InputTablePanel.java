package cmsc125.mp1.view;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cmsc125.mp1.model.ResourcesTableModel;

public class InputTablePanel extends JPanel {

	private JTable allocatedTable;
	private JTable maximumTable;
	private JTable availableTable;
	private JLabel availableTableLabel;
	private JLabel maximumTableLabel;
	private JLabel allocatedTableLabel;
	private JTable timeTable;
	
	public InputTablePanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(null);
		setBackground(Color.ORANGE);
	}

	public void initComponents() {

		// drop-down list to choose number of processes
		String[] oneToTwenty = new String[20];
		for (int i = 1; i <= oneToTwenty.length; i++) {
			oneToTwenty[i - 1] = String.format("%s", i);
		}

		// drop-down list to choose number of resources
		String[] oneToTen = new String[10];
		for (int i = 1; i <= oneToTen.length; i++) {
			oneToTen[i - 1] = String.format("%s", i);
		}

		// Resources table
		String[] columnResources = { "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10" };
		String[][] allocatedObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					allocatedObjects[i][j] = "1";
				} else {
					allocatedObjects[i][j] = "0";
				}
			}
		}
		setAllocatedTable(new JTable(new ResourcesTableModel(columnResources, allocatedObjects)));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		getAllocatedTable().setBackground(Color.WHITE);
		getAllocatedTable().setRowSelectionAllowed(true);
		getAllocatedTable().setColumnSelectionAllowed(true);
		getAllocatedTable().setCellSelectionEnabled(true);
		
		String[][] maximumObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					maximumObjects[i][j] = "1";
				} else {
					maximumObjects[i][j] = "0";
				}
			}
		}
		setMaximumTable(new JTable(new ResourcesTableModel(columnResources, maximumObjects)));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		getMaximumTable().setBackground(Color.WHITE);
		getMaximumTable().setRowSelectionAllowed(true);
		getMaximumTable().setColumnSelectionAllowed(true);
		getMaximumTable().setCellSelectionEnabled(true);
		
		String[][] availableObjects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					availableObjects[i][j] = "1";
				} else {
					availableObjects[i][j] = "0";
				}
			}
		}
		setAvailableTable(new JTable(new ResourcesTableModel(columnResources, availableObjects)));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		getAvailableTable().setBackground(Color.WHITE);
		getAvailableTable().setRowSelectionAllowed(true);
		getAvailableTable().setColumnSelectionAllowed(true);
		getAvailableTable().setCellSelectionEnabled(true);
		/*
		 * DefaultTableCellRenderer centerRenderer = new
		 * DefaultTableCellRenderer(); centerRenderer.setHorizontalAlignment(
		 * JLabel.CENTER); resourcesTable.setDefaultRenderer(String.class,
		 * centerRenderer);
		 */

		// Table for arrival time, priority
		String[] columnTime = { "AT", "Priority" };
		String[][] objectsTime = new String[20][2];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 2; j++) {
				objectsTime[i][j] = "0";
			}
		}
		timeTable = new JTable(new ResourcesTableModel(columnTime, objectsTime));
		// resourcesTable.setSize(400, 400);
		// timeTable.setGridColor(Color.BLACK);
		timeTable.setBackground(Color.WHITE);
		timeTable.setRowSelectionAllowed(true);
		timeTable.setColumnSelectionAllowed(true);
		timeTable.setCellSelectionEnabled(true);
		
	}

	public void addComponents() {
		// resources table
		allocatedTableLabel.setSize(80, 20);
		allocatedTableLabel.setLocation(5, 70);
		add(allocatedTableLabel);
		JScrollPane allocatedTablePane = new JScrollPane(getAllocatedTable());
		allocatedTablePane.setSize(300, 342);
		allocatedTablePane.setLocation(5, 95);
		add(allocatedTablePane);
		
		// rand allocated table
		getRandAllocatedTableButton().setSize(70, 20);
		getRandAllocatedTableButton().setLocation(85, 70);
		add(getRandAllocatedTableButton());
		
		maximumTableLabel.setSize(80, 20);
		maximumTableLabel.setLocation(305, 70);
		add(maximumTableLabel);
		JScrollPane maximumTablePane = new JScrollPane(getMaximumTable());
		maximumTablePane.setSize(300, 342);
		maximumTablePane.setLocation(305, 95);
		add(maximumTablePane);
		
		// rand allocated table
		getRandMaximumTableButton().setSize(70, 20);
		getRandMaximumTableButton().setLocation(385, 70);
		add(getRandMaximumTableButton());
		
		availableTableLabel.setSize(80, 20);
		availableTableLabel.setLocation(605, 70);
		add(availableTableLabel);
		JScrollPane availableTablePane = new JScrollPane(getAvailableTable());
		availableTablePane.setSize(300, 342);
		availableTablePane.setLocation(605, 95);
		add(availableTablePane);

		// rand allocated table
		getRandAvailableTableButton().setSize(70, 20);
		getRandAvailableTableButton().setLocation(685, 70);
		add(getRandAvailableTableButton());
		
		// table for arrival time, priority
		JScrollPane timeTablePane = new JScrollPane(timeTable);
		timeTablePane.setSize(105, 342);
		timeTablePane.setLocation(905, 95);
		add(timeTablePane);

	}

	public void randAllocatedTable() {
		int rowCount = getAllocatedTable().getModel().getRowCount();
		int colCount = getAllocatedTable().getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					getAllocatedTable().getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					getAllocatedTable().getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}
	
	public void randMaximumTable() {
		int rowCount = getMaximumTable().getModel().getRowCount();
		int colCount = getMaximumTable().getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					getMaximumTable().getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					getMaximumTable().getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}
	
	public void randAvailableTable() {
		int rowCount = getAvailableTable().getModel().getRowCount();
		int colCount = getAvailableTable().getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					getAvailableTable().getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					getAvailableTable().getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}


	public void setResourcesTableColumnSize(int numColumns) {
		ResourcesTableModel currentAllocatedModel = ((ResourcesTableModel) getAllocatedTable().getModel());
		String[][] currentAllocatedTableData = currentAllocatedModel.getData();
		String[][] newAllocatedTableData = new String[currentAllocatedTableData.length][numColumns];

		for (int i = 0; i < currentAllocatedTableData.length; i++) {
			for (int j = 0; j < currentAllocatedTableData[i].length; j++) {
				if (currentAllocatedTableData[i][j] != null && j < numColumns) {
					newAllocatedTableData[i][j] = currentAllocatedTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAllocatedTableData.length; i++) {
			for (int j = 0; j < newAllocatedTableData[i].length; j++) {
				if (newAllocatedTableData[i][j] == null) {
					newAllocatedTableData[i][j] = "0";
				}
			}
		}

		String[] newAllocatedColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
			newAllocatedColumns[i] = "R" + (i + 1);
		}

		((ResourcesTableModel) getAllocatedTable().getModel()).setColumnNames(newAllocatedColumns);

		getAllocatedTable().setModel(new ResourcesTableModel(currentAllocatedModel.getColumnNames(), newAllocatedTableData));

		ResourcesTableModel currentMaximumModel = ((ResourcesTableModel) getMaximumTable().getModel());
		String[][] currentMaximumTableData = currentMaximumModel.getData();
		String[][] newMaximumTableData = new String[currentMaximumTableData.length][numColumns];

		for (int i = 0; i < currentMaximumTableData.length; i++) {
			for (int j = 0; j < currentMaximumTableData[i].length; j++) {
				if (currentMaximumTableData[i][j] != null && j < numColumns) {
					newMaximumTableData[i][j] = currentMaximumTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newMaximumTableData.length; i++) {
			for (int j = 0; j < newMaximumTableData[i].length; j++) {
				if (newMaximumTableData[i][j] == null) {
					newMaximumTableData[i][j] = "0";
				}
			}
		}		
		
		String[] newMaximumColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
			newMaximumColumns[i] = "R" + (i + 1);
		}

		((ResourcesTableModel) getMaximumTable().getModel()).setColumnNames(newMaximumColumns);
		
		getMaximumTable().setModel(new ResourcesTableModel(currentMaximumModel.getColumnNames(), newMaximumTableData));
		
		ResourcesTableModel currentAvailableModel = ((ResourcesTableModel) getAvailableTable().getModel());
		String[][] currentAvailableTableData = currentAvailableModel.getData();
		String[][] newAvailableTableData = new String[currentAvailableTableData.length][numColumns];

		for (int i = 0; i < currentAvailableTableData.length; i++) {
			for (int j = 0; j < currentAvailableTableData[i].length; j++) {
				if (currentAvailableTableData[i][j] != null && j < numColumns) {
					newAvailableTableData[i][j] = currentAvailableTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAvailableTableData.length; i++) {
			for (int j = 0; j < newAvailableTableData[i].length; j++) {
				if (newAvailableTableData[i][j] == null) {
					newAvailableTableData[i][j] = "0";
				}
			}
		}		

		String[] newAvailableColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
			newAvailableColumns[i] = "R" + (i + 1);
		}

		((ResourcesTableModel) getAvailableTable().getModel()).setColumnNames(newAvailableColumns);
		
		getAvailableTable().setModel(new ResourcesTableModel(currentAvailableModel.getColumnNames(), newAvailableTableData));
	}

	public void setResourcesTableRowSize(int numRows) {
		ResourcesTableModel currentAllocatedModel = ((ResourcesTableModel) getAllocatedTable().getModel());
		String[][] currentAllocatedTableData = currentAllocatedModel.getData();
		String[][] newAllocatedTableData = new String[numRows][currentAllocatedTableData[0].length];
		
		for (int i = 0; i < currentAllocatedTableData.length; i++) {
			for (int j = 0; j < currentAllocatedTableData[i].length; j++) {
				if (currentAllocatedTableData[i][j] != null && i < numRows) {
					newAllocatedTableData[i][j] = currentAllocatedTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAllocatedTableData.length; i++) {
			for (int j = 0; j < newAllocatedTableData[i].length; j++) {
				if (newAllocatedTableData[i][j] == null) {
					newAllocatedTableData[i][j] = "0";
				}
			}
		}

		getAllocatedTable().setModel(new ResourcesTableModel(currentAllocatedModel.getColumnNames(), newAllocatedTableData));

		ResourcesTableModel currentMaximumModel = ((ResourcesTableModel) getMaximumTable().getModel());
		String[][] currentMaximumTableData = currentMaximumModel.getData();
		String[][] newMaximumTableData = new String[numRows][currentMaximumTableData[0].length];
		
		for (int i = 0; i < currentMaximumTableData.length; i++) {
			for (int j = 0; j < currentMaximumTableData[i].length; j++) {
				if (currentMaximumTableData[i][j] != null && i < numRows) {
					newMaximumTableData[i][j] = currentMaximumTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newMaximumTableData.length; i++) {
			for (int j = 0; j < newMaximumTableData[i].length; j++) {
				if (newMaximumTableData[i][j] == null) {
					newMaximumTableData[i][j] = "0";
				}
			}
		}
		
		getMaximumTable().setModel(new ResourcesTableModel(currentMaximumModel.getColumnNames(), newMaximumTableData));
		
		ResourcesTableModel currentAvailableModel = ((ResourcesTableModel) getAvailableTable().getModel());
		String[][] currentAvailableTableData = currentAvailableModel.getData();
		String[][] newAvailableTableData = new String[numRows][currentAvailableTableData[0].length];
		
		for (int i = 0; i < currentAvailableTableData.length; i++) {
			for (int j = 0; j < currentAvailableTableData[i].length; j++) {
				if (currentAvailableTableData[i][j] != null && i < numRows) {
					newAvailableTableData[i][j] = currentAvailableTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newAvailableTableData.length; i++) {
			for (int j = 0; j < newAvailableTableData[i].length; j++) {
				if (newAvailableTableData[i][j] == null) {
					newAvailableTableData[i][j] = "0";
				}
			}
		}
		
		getAvailableTable().setModel(new ResourcesTableModel(currentAvailableModel.getColumnNames(), newAvailableTableData));

/*		currentModel = ((ResourcesTableModel) getAllocatedTable().getModel());
		currentTableData = currentModel.getData();
		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < currentTableData[i].length; j++) {
				System.out.print(currentTableData[i][j] + " ");
			}
			System.out.println();
		}
		
		currentModel = ((ResourcesTableModel) getMaximumTable().getModel());
		currentTableData = currentModel.getData();
		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < currentTableData[i].length; j++) {
				System.out.print(currentTableData[i][j] + " ");
			}
			System.out.println();
		}
		
		currentModel = ((ResourcesTableModel) getAvailableTable().getModel());
		currentTableData = currentModel.getData();
		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < currentTableData[i].length; j++) {
				System.out.print(currentTableData[i][j] + " ");
			}
			System.out.println();
		}*/
		
		//System.exit(1);
		resizeTimeTable(numRows);
	}

	public void resizeTimeTable(int numRows) {
		ResourcesTableModel timeTableModel = ((ResourcesTableModel) timeTable.getModel());
		String[][] objects = timeTableModel.getData();
		String[][] newStrings = new String[numRows][2];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < 2; j++) {
				if (i < objects.length && objects[i][j] != null) {
					newStrings[i][j] = objects[i][j];
				} else {
					newStrings[i][j] = "0";
				}
			}
		}

		timeTable.setModel(new ResourcesTableModel(timeTableModel.getColumnNames(), newStrings));
	}

	public JTable getResourcesTable() {
		return getAllocatedTable();
	}

	public JTable getTimeTable() {
		return timeTable;
	}

}