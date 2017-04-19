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
		} else if (algos.contains("RR")) { 
			RRManager rrManager = new RRManager( 
			          simulationPanel, resourcesTable,  
			          timeTable, quantumField); 
			       
			rrManager.startSimulation(); 
		} else if (algos.contains("NP PRIO")) { 
			NP_PRIOManager np_prioManager = new NP_PRIOManager( 
			          simulationPanel, resourcesTable,  
			          timeTable); 
			       
			np_prioManager.startSimulation(); 
		} else if (algos.contains("SRTF")) { 
			SRTFManager srtfManager = new SRTFManager( 
			          simulationPanel, resourcesTable,  
			          timeTable); 
			       
			srtfManager.startSimulation(); 
		} else if (algos.contains("PRIO")) { 
			PRIOManager prioManager = new PRIOManager( 
			          simulationPanel, resourcesTable,  
			          timeTable); 
			       
			prioManager.startSimulation(); 
		}
	}
}
