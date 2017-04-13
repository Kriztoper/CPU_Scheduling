package cmsc125.mp1.view.panels;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

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
	private JLabel resourcesTableLabel;
	private JTable resourcesTable;
	
	private int processesCount;
	private int resourcesCount;
	
	public InputTablePanel() {
		initPanel();
		initComponents();
		addComponents();
	}
	
	public void initPanel() {
		setLayout(new FlowLayout());
		setBackground(Color.orange);
	}
	
	public void initComponents() {
		// Enter number of processes label
		numProcessesLabel = new JLabel("Number of Processes: ");
		
		// drop-down list to choose number of processes
		String[] oneToTwenty = new String[20];
		for (int i = 1; i <= oneToTwenty.length; i++) {
			oneToTwenty[i-1] = String.format("%s", i);
		}
		numProcesses = new JComboBox<String>(oneToTwenty);
		numProcesses.setSelectedIndex(0);
		setProcessesCount(1);
		numProcesses.setVisible(true);
		
		// Randomize button for num processes
		randNumProcessesButton = new JButton("Rand");
		
		// Enter number of resources label
		numResourcesLabel = new JLabel("Number of Resources: ");
		
		// drop-down list to choose number of resources
		String[] oneToTen = new String[10];
		for (int i = 1; i <= oneToTen.length; i++) {
			oneToTen[i-1] = String.format("%s", i);
		}
		numResources = new JComboBox<String>(oneToTen);
		numResources.setSelectedIndex(0);
		setResourcesCount(1);
		numResources.setVisible(true);
		
		// Randomize button for num resources
		randNumResourcesButton = new JButton("Rand");
		
		// Select the CPU Scheduling algos to be used label
		selectAlgorithmsLabel = new JLabel(
				"Select the CPU Scheduling Algorithms to use: ");
		
		// JPanel for grouped checkboxes for CPU Sched Algos
		checkBoxAlgosPanel = new JPanel();
		checkBoxAlgosPanel.setLayout(new GridLayout(6, 1));
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
		resourcesTableLabel = new JLabel("Resources Table:");
		
		// Resources table
		Object[][] objects = {
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
				{"1","2","3","4","5","6","7","8","9","10"},
		};
		resourcesTable = new JTable(
				new ResourcesTableModel(new String[10],
						objects));//new Object[20][10]));
		//resourcesTable.setSize(400, 400);
		resourcesTable.setGridColor(Color.BLACK);
		resourcesTable.setBackground(Color.WHITE);
		
		// 
	}

	public void addComponents() {
		// processes
		add(numProcessesLabel);
		add(numProcesses);
		add(randNumProcessesButton);
		
		// resources
		add(numResourcesLabel);
		add(numResources);
		add(randNumResourcesButton);
		
		// CPU Scheduling Algorithms
		add(selectAlgorithmsLabel);
		add(checkBoxAlgosPanel);
		add(randCPUSchedAlgosButton);
		
		// resources table
		add(resourcesTableLabel);
		add(new JScrollPane(resourcesTable));
	}
	
	public void randNumProcesses() {
		Random random = new Random();
		int randomIndex = random.nextInt(20);
		numProcesses.setSelectedIndex(randomIndex);
		setProcessesCount(randomIndex + 1);
	}
	
	public void setResourcesTableColumnSize(int numColumns) {
		ResourcesTableModel currentModel = 
				((ResourcesTableModel) resourcesTable.
				getModel());
		Object[][] currentTableData = 
				currentModel.getData();
		Object[][] newTableData = 
				new Object[currentTableData.length][numColumns];
		
		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < 
					currentTableData[i].length; j++) {
				if (currentTableData[i][j] != null &&
						j < numColumns) {
					newTableData[i][j] = currentTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newTableData.length; i++) {
			for (int j = 0; j < 
					newTableData[i].length; j++) {
				if (newTableData[i][j] == null) {
					newTableData[i][j] = "0";
				}
			}
		}
		
		
		String[] currentColumns = ((ResourcesTableModel) 
				resourcesTable.getModel()).getColumnNames();
		String[] newColumns = new String[numColumns];
		
		for (int i = 0; i < currentColumns.length; i++) {
			if (currentColumns[i] != null && i < numColumns) {
				newColumns[i] = currentColumns[i]; 
			}
		}
		
		for (int i = 0; i < newColumns.length; i++) {
			if (newColumns[i] == null) {
				newColumns[i] = "0";
			}
		}
		
		((ResourcesTableModel) resourcesTable.getModel()).
				setColumnNames(newColumns);
		
		
		
		resourcesTable.setModel(
				new ResourcesTableModel(
						currentModel.getColumnNames(), 
						newTableData));
		
		
	}
	
	public void setResourcesTableRowSize(int numRows) {
		ResourcesTableModel currentModel = 
				((ResourcesTableModel) resourcesTable.
				getModel());
		Object[][] currentTableData = 
				currentModel.getData();
		Object[][] newTableData = 
				new Object[numRows][currentTableData[0].length];
		
		for (int i = 0; i < currentTableData.length; i++) {
			for (int j = 0; j < 
					currentTableData[i].length; j++) {
				if (currentTableData[i][j] != null &&
						i < numRows) {
					newTableData[i][j] = currentTableData[i][j];
				}
			}
		}

		for (int i = 0; i < newTableData.length; i++) {
			for (int j = 0; j < 
					newTableData[i].length; j++) {
				if (newTableData[i][j] == null) {
					newTableData[i][j] = "0";
				}
			}
		}
		
		resourcesTable.setModel(
				new ResourcesTableModel(
						currentModel.getColumnNames(), 
						newTableData));
	}
	
	public void randNumResources() {
		Random random = new Random();
		int randomIndex = random.nextInt(10);
		numResources.setSelectedIndex(randomIndex);
		setResourcesCount(randomIndex + 1);
	}
	
	public void randCPUSchedAlgos() {
		Random random = new Random();
		for (Checkbox checkbox: checkBoxAlgosList) {
			int randomIndex = random.nextInt(100) + 1;
			boolean randomBool = ((randomIndex<=50)?(false):(true));
			checkbox.setState(randomBool);
		}
	}
	
	public ArrayList<Checkbox> getSelectedAlgosFromCheckbox() {
		ArrayList<Checkbox> selectedCheckboxes = 
				new ArrayList<Checkbox>();
		for (int i = 0; i < checkBoxAlgosList.size(); i++) {
			Checkbox currentCheckbox = checkBoxAlgosList.get(i);
			if (currentCheckbox.getState()) {
				selectedCheckboxes.add(currentCheckbox);
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
	
	public int getProcessesCount() {
		return processesCount;
	}

	public void setProcessesCount(int processesCount) {
		this.processesCount = processesCount;
	}

	public int getResourcesCount() {
		return resourcesCount;
	}

	public void setResourcesCount(int resourcesCount) {
		this.resourcesCount = resourcesCount;
	}

	class ResourcesTableModel extends AbstractTableModel {

		private String[] columnNames;
		private Object[][] data;
		
		public ResourcesTableModel(
				String[] columnNames, Object[][] data) {
			this.setColumnNames(columnNames);
			this.setData(data);
		}
		
		public ResourcesTableModel() {
			
		}

		@Override
		public int getColumnCount() {
			return getColumnNames().length;
		}

		@Override
		public int getRowCount() {
			return getData().length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			return getData()[row][column];
		}

		public Object[][] getData() {
			return data;
		}

		public void setData(Object[][] data) {
			this.data = data;
		}

		public String[] getColumnNames() {
			return columnNames;
		}

		public void setColumnNames(String[] columnNames) {
			this.columnNames = columnNames;
		}
	}
}
