package cmsc125.mp1.view;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import cmsc125.mp1.model.ResourcesTableModel;

public class InputTablePanel extends JPanel {

	private JLabel numProcessesLabel;
	private JComboBox<String> numProcesses;
	private JButton randNumProcessesButton;
	private JLabel numResourcesLabel;
	private JComboBox<String> numResources;
	private JButton randNumResourcesButton;
	private JLabel selectAlgorithmsLabel;
	private JPanel checkBoxAlgosPanel;
	private ArrayList<Checkbox> checkBoxAlgosList;
	private JButton randCPUSchedAlgosButton;
	private JLabel allocatedTableLabel;
	private JTable allocatedTable;
	private JButton randAllocatedTableButton;
	private JLabel maximumTableLabel;
	private JTable maximumTable;
	private JButton randMaximumTableButton;
	private JLabel availableTableLabel;
	private JTable availableTable;
	private JButton randAvailableTableButton;
	private JButton startSimulationButton;
	private JTable timeTable;
	private JLabel quantumLabel;
	private JTextField quantumField;
	
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
		// Enter number of processes label
		numProcessesLabel = new JLabel("Number of Processes: ");

		// drop-down list to choose number of processes
		String[] oneToTwenty = new String[20];
		for (int i = 1; i <= oneToTwenty.length; i++) {
			oneToTwenty[i - 1] = String.format("%s", i);
		}
		setNumProcesses(new JComboBox<String>(oneToTwenty));
		getNumProcesses().setSelectedIndex(19);
		// setProcessesCount(20);
		getNumProcesses().setVisible(true);

		// Randomize button for num processes
		randNumProcessesButton = new JButton("Rand");

		// Enter number of resources label
		numResourcesLabel = new JLabel("Number of Resources: ");

		// drop-down list to choose number of resources
		String[] oneToTen = new String[10];
		for (int i = 1; i <= oneToTen.length; i++) {
			oneToTen[i - 1] = String.format("%s", i);
		}
		setNumResources(new JComboBox<String>(oneToTen));
		getNumResources().setSelectedIndex(9);
		// setResourcesCount(10);
		getNumResources().setVisible(true);

		// Randomize button for num resources
		randNumResourcesButton = new JButton("Rand");

		// Select the CPU Scheduling algos to be used label
		selectAlgorithmsLabel = new JLabel("Select the CPU Scheduling Algorithms to use: ");

		// JPanel for grouped checkboxes for CPU Sched Algos
		checkBoxAlgosPanel = new JPanel();
		checkBoxAlgosPanel.setLayout(new GridLayout(1, 6));
		checkBoxAlgosPanel.setBackground(Color.WHITE);

		// list of algos checkboxes
		checkBoxAlgosList = new ArrayList<Checkbox>();
		checkBoxAlgosList.add(new Checkbox("FCFS", true));
		checkBoxAlgosList.add(new Checkbox("NP PRIO", false));
		checkBoxAlgosList.add(new Checkbox("PRIO", false));
		checkBoxAlgosList.add(new Checkbox("SJF", false));
		checkBoxAlgosList.add(new Checkbox("SRTF", false));
		checkBoxAlgosList.add(new Checkbox("RR", false));

		// add the checkboxes to the panel
		for (int i = 0; i < checkBoxAlgosList.size(); i++) {
			checkBoxAlgosPanel.add(checkBoxAlgosList.get(i));
		}

		// Randomize button for num processes
		randCPUSchedAlgosButton = new JButton("Rand");

		// Resources table label
		allocatedTableLabel = new JLabel("Allocated:");
		maximumTableLabel = new JLabel("Maximum: ");
		availableTableLabel = new JLabel("Available: ");
		
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
		allocatedTable = new JTable(new ResourcesTableModel(columnResources, objects));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		allocatedTable.setBackground(Color.WHITE);
		allocatedTable.setRowSelectionAllowed(true);
		allocatedTable.setColumnSelectionAllowed(true);
		allocatedTable.setCellSelectionEnabled(true);
		
		maximumTable = new JTable(new ResourcesTableModel(columnResources, objects));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		maximumTable.setBackground(Color.WHITE);
		maximumTable.setRowSelectionAllowed(true);
		maximumTable.setColumnSelectionAllowed(true);
		maximumTable.setCellSelectionEnabled(true);
		
		availableTable = new JTable(new ResourcesTableModel(columnResources, objects));
		// resourcesTable.setSize(400, 400);
		// gresourcesTable.setGridColor(Color.BLACK);
		availableTable.setBackground(Color.WHITE);
		availableTable.setRowSelectionAllowed(true);
		availableTable.setColumnSelectionAllowed(true);
		availableTable.setCellSelectionEnabled(true);
		/*
		 * DefaultTableCellRenderer centerRenderer = new
		 * DefaultTableCellRenderer(); centerRenderer.setHorizontalAlignment(
		 * JLabel.CENTER); resourcesTable.setDefaultRenderer(String.class,
		 * centerRenderer);
		 */

		// resources table randomize button
		randAllocatedTableButton = new JButton("Rand");
		randMaximumTableButton = new JButton("Rand");
		randAvailableTableButton = new JButton("Rand");
		
		// start simulation button
		startSimulationButton = new JButton("Start Simulation");

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

		// quantum label and text field
		quantumLabel = new JLabel("Quantum: ");
		setQuantumField(new JTextField("1"));

		
	}

	public void addComponents() {
		// processes
		numProcessesLabel.setSize(160, 15);
		numProcessesLabel.setLocation(5, 9);
		add(numProcessesLabel);
		getNumProcesses().setSize(50, 20);
		numProcesses.setLocation(170, 5);
		add(getNumProcesses());
		randNumProcessesButton.setSize(70, 20);
		randNumProcessesButton.setLocation(225, 5);
		add(randNumProcessesButton);

		// resources
		numResourcesLabel.setSize(160, 15);
		numResourcesLabel.setLocation(5, 34);
		add(numResourcesLabel);
		getNumResources().setSize(50, 20);
		getNumResources().setLocation(170, 30);
		add(getNumResources());
		randNumResourcesButton.setSize(70, 20);
		randNumResourcesButton.setLocation(225, 30);
		add(randNumResourcesButton);

		// CPU Scheduling Algorithms
		selectAlgorithmsLabel.setSize(325, 15);
		selectAlgorithmsLabel.setLocation(340, 9);
		add(selectAlgorithmsLabel);
		checkBoxAlgosPanel.setSize(420, 20);
		checkBoxAlgosPanel.setLocation(340, 30);
		add(checkBoxAlgosPanel);
		randCPUSchedAlgosButton.setSize(70, 20);
		randCPUSchedAlgosButton.setLocation(670, 5);
		add(randCPUSchedAlgosButton);

		// resources table
		allocatedTableLabel.setSize(80, 20);
		allocatedTableLabel.setLocation(5, 70);
		add(allocatedTableLabel);
		JScrollPane allocatedTablePane = new JScrollPane(allocatedTable);
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
		JScrollPane maximumTablePane = new JScrollPane(maximumTable);
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
		JScrollPane availableTablePane = new JScrollPane(availableTable);
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

		// start simulation
		startSimulationButton.setSize(150, 20);
		startSimulationButton.setLocation(450, 450);
		add(startSimulationButton);
		
		// quantum
		quantumLabel.setSize(80, 20);
		quantumLabel.setLocation(850, 5);
		add(quantumLabel);
		getQuantumField().setSize(70, 20);
		getQuantumField().setLocation(925, 5);
		add(getQuantumField());
	}

	public void randAllocatedTable() {
		int rowCount = allocatedTable.getModel().getRowCount();
		int colCount = allocatedTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					allocatedTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					allocatedTable.getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}
	
	public void randMaximumTable() {
		int rowCount = maximumTable.getModel().getRowCount();
		int colCount = maximumTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					maximumTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					maximumTable.getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}
	
	public void randAvailableTable() {
		int rowCount = availableTable.getModel().getRowCount();
		int colCount = availableTable.getModel().getColumnCount();
		Random random = new Random();
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (j == 0) {
					availableTable.getModel().setValueAt(Integer.toString(random.nextInt(10) + 1), i, j);
				} else {
					availableTable.getModel().setValueAt(Integer.toString(random.nextInt(10)), i, j);
				}
			}
		}
	}

	public void randNumProcesses() {
		Random random = new Random();
		int randomIndex = random.nextInt(20);
		getNumProcesses().setSelectedIndex(randomIndex);
	}

	public void setResourcesTableColumnSize(int numColumns) {
		ResourcesTableModel currentModel = ((ResourcesTableModel) allocatedTable.getModel());
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

		((ResourcesTableModel) allocatedTable.getModel()).setColumnNames(newColumns);

		allocatedTable.setModel(new ResourcesTableModel(currentModel.getColumnNames(), newTableData));
	}

	public void setResourcesTableRowSize(int numRows) {
		ResourcesTableModel currentModel = ((ResourcesTableModel) allocatedTable.getModel());
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

		allocatedTable.setModel(new ResourcesTableModel(currentModel.getColumnNames(), newTableData));

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

	public void randNumResources() {
		Random random = new Random();
		int randomIndex = random.nextInt(10);
		getNumResources().setSelectedIndex(randomIndex);
	}

	public void randCPUSchedAlgos() {
		Random random = new Random();
		for (Checkbox checkbox : checkBoxAlgosList) {
			int randomIndex = random.nextInt(100) + 1;
			boolean randomBool = ((randomIndex <= 50) ? (false) : (true));
			checkbox.setState(randomBool);
		}
	}

	public ArrayList<String> getSelectedAlgosFromCheckbox() {
		ArrayList<String> selectedCheckboxes = new ArrayList<String>();
		for (int i = 0; i < checkBoxAlgosList.size(); i++) {
			Checkbox currentCheckbox = checkBoxAlgosList.get(i);
			if (currentCheckbox.getState()) {
				selectedCheckboxes.add(currentCheckbox.getLabel());
			}
		}

		return selectedCheckboxes;
	}

	public JButton getRandNumProcessesButton() {
		return randNumProcessesButton;
	}

	public void setRandNumProcessesButton(JButton randNumProcessesButton) {
		this.randNumProcessesButton = randNumProcessesButton;
	}

	public JButton getRandNumResourcesButton() {
		return randNumResourcesButton;
	}

	public void setRandNumResourcesButton(JButton randNumResourcesButton) {
		this.randNumResourcesButton = randNumResourcesButton;
	}

	public JButton getRandCPUSchedAlgosButton() {
		return randCPUSchedAlgosButton;
	}

	public void setRandCPUSchedAlgosButton(JButton randCPUSchedAlgosButton) {
		this.randCPUSchedAlgosButton = randCPUSchedAlgosButton;
	}

	public JButton getStartSimulationButton() {
		return startSimulationButton;
	}

	public void setStartSimulationButton(JButton startSimulationButton) {
		this.startSimulationButton = startSimulationButton;
	}

	public JComboBox<String> getNumProcesses() {
		return numProcesses;
	}

	public void setNumProcesses(JComboBox<String> numProcesses) {
		this.numProcesses = numProcesses;
	}

	public JComboBox<String> getNumResources() {
		return numResources;
	}

	public void setNumResources(JComboBox<String> numResources) {
		this.numResources = numResources;
	}

	public JButton getRandAllocatedTableButton() {
		return randAllocatedTableButton;
	}

	public void setRandResourcesTableButton(JButton randResourcesTableButton) {
		this.randAllocatedTableButton = randResourcesTableButton;
	}

	public JTable getResourcesTable() {
		return allocatedTable;
	}

	public JTable getTimeTable() {
		return timeTable;
	}

	public JTextField getQuantumField() {
		return quantumField;
	}

	public void setQuantumField(JTextField quantumField) {
		this.quantumField = quantumField;
	}

	public JButton getRandMaximumTableButton() {
		return randMaximumTableButton;
	}

	public void setRandMaximumTableButton(JButton randMaximumTableButton) {
		this.randMaximumTableButton = randMaximumTableButton;
	}

	public JButton getRandAvailableTableButton() {
		return randAvailableTableButton;
	}

	public void setRandAvailableTableButton(JButton randAvailableTableButton) {
		this.randAvailableTableButton = randAvailableTableButton;
	}
}