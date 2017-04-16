package cmsc125.mp1.controller;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.controller.algo.FCFSManager;
import cmsc125.mp1.view.panels.SimulationPanel;

public class AlgoSimulator {

	/*private SimulationPanel simulationPanel;
	private JTable resourcesTable;
	private JTable timeTable;*/
	
	public AlgoSimulator() {
		
	}
	
	public void startSimulation(
			SimulationPanel simulationPanel, 
			ArrayList<String> algos, 
			JTable resourcesTable, JTable timeTable) {
		/*this.simulationPanel = simulationPanel;
		this.resourcesTable = resourcesTable;
		this.timeTable = timeTable;*/

		if (algos.contains("FCFS")) {
			FCFSManager fcfsManager = new FCFSManager(
					simulationPanel, resourcesTable, 
					timeTable);
			
			fcfsManager.startSimulation();
		}
	}
}
