package cmsc125.mp1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmsc125.mp1.view.InputTablePanel;
import cmsc125.mp1.view.MenuPanel;
import cmsc125.mp1.view.ResultsPanel;
import cmsc125.mp1.view.SimulationPanel;
import cmsc125.mp1.view.ViewFrame;

public class Controller {

	private ViewFrame view;

	public Controller(ViewFrame view) {
		this.view = view;
		addButtonListeners();
		view.setCurrentPanel("menuPanel");
	}

	public void addButtonListeners() {
		MenuPanel menuPanel = new MenuPanel();
		InputTablePanel inputTablePanel = new InputTablePanel();
		SimulationPanel simulationPanel = new SimulationPanel();
		ResultsPanel resultsPanel = new ResultsPanel();

		view.add(menuPanel, "menuPanel");
		view.add(inputTablePanel, "inputTablePanel");
		view.add(simulationPanel, "simulationPanel");
		view.add(resultsPanel, "resultsPanel");

		menuPanel.getInputDataButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("inputTablePanel");
			}
		});

		inputTablePanel.getRandNumProcessesButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randNumProcesses();
				inputTablePanel.setResourcesTableRowSize(inputTablePanel.getNumProcesses().getSelectedIndex() + 1);
			}
		});

		inputTablePanel.getRandNumResourcesButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randNumResources();
				inputTablePanel.setResourcesTableColumnSize(inputTablePanel.getNumResources().getSelectedIndex() + 1);
			}
		});

		inputTablePanel.getRandCPUSchedAlgosButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randCPUSchedAlgos();
			}
		});

		inputTablePanel.getStartSimulationButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("simulationPanel");
				simulationPanel.startSimulation(inputTablePanel.getSelectedAlgosFromCheckbox(),
						inputTablePanel.getResourcesTable(), inputTablePanel.getTimeTable(),
						inputTablePanel.getQuantumField());
				// view.setCurrentPanel("menuPanel");
			}
		});

		inputTablePanel.getNumProcesses().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.setResourcesTableRowSize(
						Integer.parseInt((String) inputTablePanel.getNumProcesses().getSelectedItem()));

			}
		});

		inputTablePanel.getNumResources().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.setResourcesTableColumnSize(
						Integer.parseInt((String) inputTablePanel.getNumResources().getSelectedItem()));

			}
		});

		inputTablePanel.getRandResourcesTableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randResourcesTable();
			}
		});
	}
}
