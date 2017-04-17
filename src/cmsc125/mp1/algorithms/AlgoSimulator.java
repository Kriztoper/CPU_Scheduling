package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;

import cmsc125.mp1.view.SimulationPanel;
import cmsc125.mp1.algorithms.SJFManager; 
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
		} else if (algos.contains("SJF")) { 
			SJFManager sjfManager = new SJFManager( 
			          simulationPanel, resourcesTable,  
			          timeTable); 
			       
			sjfManager.startSimulation(); 
		} 
	}
}
