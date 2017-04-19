package cmsc125.mp1.algorithms;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;

import cmsc125.mp1.view.SimulationPanel;

public class AlgoSimulator {

	public void startSimulation(
			SimulationPanel simulationPanel, 
			ArrayList<String> algos, 
			JTable allocatedTable,
			JTable maximumTable,
			JTable availableTable,
			JTable timeTable, 
			JTextField quantumField) {

		if (algos.contains("FCFS")) {
			FCFSManager fcfsManager = new FCFSManager(
					simulationPanel, 
					allocatedTable, 
					maximumTable,
					availableTable,
					timeTable);
			
			fcfsManager.startSimulation();
		} else if (algos.contains("SJF")) { 
			SJFManager sjfManager = new SJFManager( 
			          simulationPanel, 
			          allocatedTable,  
			          maximumTable,
			          availableTable,
			          timeTable); 
			       
			sjfManager.startSimulation(); 
		} else if (algos.contains("RR")) { 
			RRManager rrManager = new RRManager( 
			          simulationPanel, 
			          allocatedTable,  
			          maximumTable,
			          availableTable,
			          timeTable, 
			          quantumField); 
			       
			rrManager.startSimulation(); 
		} else if (algos.contains("NP PRIO")) { 
			NP_PRIOManager np_prioManager = new NP_PRIOManager( 
			          simulationPanel, 
			          allocatedTable,  
			          maximumTable,
			          availableTable,
			          timeTable); 
			       
			np_prioManager.startSimulation(); 
		} else if (algos.contains("SRTF")) { 
			SRTFManager srtfManager = new SRTFManager( 
			          simulationPanel, 
			          allocatedTable,
			          maximumTable,
			          availableTable,
			          timeTable); 
			       
			srtfManager.startSimulation(); 
		} else if (algos.contains("PRIO")) { 
			PRIOManager prioManager = new PRIOManager( 
			          simulationPanel, 
			          allocatedTable,  
			          maximumTable,
			          availableTable,
			          timeTable); 
			       
			prioManager.startSimulation(); 
		}
	}
}
