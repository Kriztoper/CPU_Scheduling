package cmsc125.mp1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmsc125.mp1.view.View;
import cmsc125.mp1.view.panels.InputTablePanel;

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
		
		view.getMenuPanel().getInputDataButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("inputTablePanel");
			}
		});
		
		view.getInputTablePanel().getRandNumProcessesButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InputTablePanel inputTablePanel =
						view.getInputTablePanel();
				inputTablePanel.randNumProcesses();
				inputTablePanel.setResourcesTableRowSize(
						inputTablePanel.getProcessesCount());
			}
		});
		
		view.getInputTablePanel().getRandNumResourcesButton().
			addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				view.getInputTablePanel().randNumResources();
			}
		});
		
		view.getInputTablePanel().getRandCPUSchedAlgosButton().
			addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				view.getInputTablePanel().randCPUSchedAlgos();
			}
		});
	}
}
