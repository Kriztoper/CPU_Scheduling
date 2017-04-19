package cmsc125.mp1.view;

import java.awt.Color;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import cmsc125.mp1.model.ResourcesTableModel;

public class InputTablePanel extends JPanel {

	public int numProcess, numResource;
	private JTable resourcesTable;
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
		String[][] objects = new String[20][10];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (j == 0) {
					objects[i][j] = "1";
				} else {
					objects[i][j] = "0";
				}
			}
		}
		resourcesTable = new JTable(new ResourcesTableModel(columnResources, objects));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		resourcesTable.setBackground(Color.WHITE);
		resourcesTable.setRowSelectionAllowed(true);
		resourcesTable.setColumnSelectionAllowed(true);
		resourcesTable.setCellSelectionEnabled(true);
		/*
		 * DefaultTableCellRenderer centerRenderer = new
		 * DefaultTableCellRenderer(); centerRenderer.setHorizontalAlignment(
		 * JLabel.CENTER); resourcesTable.setDefaultRenderer(String.class,
		 * centerRenderer);
		 */

		// Table for arrival time, priority
		String[] columnTime = { "Arrival Time", "Priority" };
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
		JScrollPane resourcesTablePane = new JScrollPane(resourcesTable);
		resourcesTablePane.setSize(400, 342);
		resourcesTablePane.setLocation(0, 0);
		add(resourcesTablePane);

		// table for arrival time, priority
		JScrollPane timeTablePane = new JScrollPane(timeTable);
		timeTablePane.setSize(155, 342);
		timeTablePane.setLocation(405, 0);
		add(timeTablePane);

	}

	public void randResourcesTable() {
		int rowCount = resourcesTable.getModel().getRowCount();
		int colCount = resourcesTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					resourcesTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					resourcesTable.getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}


	public void setResourcesTableColumnSize(int numColumns) {
		ResourcesTableModel currentModel = ((ResourcesTableModel) resourcesTable.getModel());
		String[][] currentTableData = currentModel.getData();
		String[][] newTableData = new String[currentTableData.length][numColumns];

		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < currentTableData[i].length; j++) {
				if (currentTableData[i][j] != null && j < numColumns) {
					newTableData[i][j] = currentTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newTableData.length; i++) {
			for (int j = 0; j < newTableData[i].length; j++) {
				if (newTableData[i][j] == null) {
					newTableData[i][j] = "0";
				}
			}
		}

		String[] newColumns = new String[numColumns];
		for (int i = 0; i < numColumns; i++) {
			newColumns[i] = "R" + (i + 1);
		}

		((ResourcesTableModel) resourcesTable.getModel()).setColumnNames(newColumns);

		resourcesTable.setModel(new ResourcesTableModel(currentModel.getColumnNames(), newTableData));
	}

	public void setResourcesTableRowSize(int numRows) {
		ResourcesTableModel currentModel = ((ResourcesTableModel) resourcesTable.getModel());
		String[][] currentTableData = currentModel.getData();
		String[][] newTableData = new String[numRows][currentTableData[0].length];

		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < currentTableData[i].length; j++) {
				if (currentTableData[i][j] != null && i < numRows) {
					newTableData[i][j] = currentTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newTableData.length; i++) {
			for (int j = 0; j < newTableData[i].length; j++) {
				if (newTableData[i][j] == null) {
					newTableData[i][j] = "0";
				}
			}
		}

		resourcesTable.setModel(new ResourcesTableModel(currentModel.getColumnNames(), newTableData));

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
		return resourcesTable;
	}

	public JTable getTimeTable() {
		return timeTable;
	}

}