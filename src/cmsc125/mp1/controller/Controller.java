package cmsc125.mp1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmsc125.mp1.view.View;
import cmsc125.mp1.view.panels.InputTablePanel;
import cmsc125.mp1.view.panels.MenuPanel;
import cmsc125.mp1.view.panels.SimulationPanel;

public class Controller {

	private View view;
	
	public Controller(View view) {
		this.view = view;
		addButtonListeners();
	}
	
	public void start() {
		view.show();
	}
	
	public void addButtonListeners() {
		MenuPanel menuPanel = view.getMenuPanel();
		InputTablePanel inputTablePanel = view.getInputTablePanel();
		SimulationPanel simulationPanel = view.getSimulationPanel();
		
		menuPanel.getInputDataButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("inputTablePanel");
			}
		});
		
		inputTablePanel.getRandNumProcessesButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randNumProcesses();
				inputTablePanel.setResourcesTableRowSize(
						inputTablePanel.
						getNumProcesses().
						getSelectedIndex() + 1);
			}
		});
		
		inputTablePanel.getRandNumResourcesButton().
			addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randNumResources();
				inputTablePanel.setResourcesTableColumnSize(
						inputTablePanel.
						getNumResources().
						getSelectedIndex() + 1);
			}
		});
		
		inputTablePanel.getRandCPUSchedAlgosButton().
			addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randCPUSchedAlgos();
			}
		});
		
		inputTablePanel.getStartSimulationButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				view.setCurrentPanel("simulationPanel");
				simulationPanel.startSimulation(
						inputTablePanel.getSelectedAlgosFromCheckbox(),
						inputTablePanel.getResourcesTable(),
						inputTablePanel.getTimeTable());
			}
		});
		
		inputTablePanel.getNumProcesses().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.
					setResourcesTableRowSize(
						Integer.parseInt((String) inputTablePanel.
							getNumProcesses().getSelectedItem()));
				
			}
		});
		
		inputTablePanel.getNumResources().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.
					setResourcesTableColumnSize(
						Integer.parseInt((String) inputTablePanel.
							getNumResources().getSelectedItem()));
				
			}
		});
		
		inputTablePanel.getRandResourcesTableButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inputTablePanel.randResourcesTable();
			}
		});
	}
}
