package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;

import cmsc125.mp1.view.SimulationPanel;

public class AlgoSimulator {

	/*private SimulationPanel simulationPanel;
	private JTable resourcesTable;
	private JTable timeTable;*/
	
	public AlgoSimulator() {
		
	}
	
	public void startSimulation(
			SimulationPanel simulationPanel, 
			ArrayList<String> algos, 
			JTable resourcesTable, JTable timeTable, JTextField quantumField) {
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
		} else if (algos.contains("NP PRIO")) { 
			NPPRIOManager npprioManager = new NPPRIOManager( 
			          simulationPanel, resourcesTable,  
			          timeTable, quantumField); 
			       
			npprioManager.startSimulation(); 
		}
	}
}
