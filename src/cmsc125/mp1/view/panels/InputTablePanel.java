package cmsc125.mp1.view.panels;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InputTablePanel extends JPanel {

	private JLabel numProcessesLabel;
	private JComboBox<String> numProcesses;
	private JLabel numResourcesLabel;
	private JComboBox<String> numResources;
	private JLabel selectAlgorithmsLabel;
	private JPanel checkBoxAlgosPanel;
	private ArrayList<Checkbox> checkBoxAlgosList;
	
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
		numProcesses.setVisible(true);
		
		// Enter number of resources label
		numResourcesLabel = new JLabel("Number of Resources: ");
		
		// drop-down list to choose number of resources
		String[] oneToTen = new String[10];
		for (int i = 1; i <= oneToTen.length; i++) {
			oneToTwenty[i-1] = String.format("%s", i);
		}
		numResources = new JComboBox<String>(oneToTen);
		numResources.setVisible(true);
		
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
	
	public void addComponents() {
		// processes
		add(numProcessesLabel);
		add(numProcesses);
		
		// resources
		add(numResourcesLabel);
		add(numResources);
		
		// CPU Scheduling Algorithms
		add(selectAlgorithmsLabel);
		add(checkBoxAlgosPanel);
		
		
	}
}
