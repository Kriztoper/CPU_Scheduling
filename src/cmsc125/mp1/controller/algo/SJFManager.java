package cmsc125.mp1.controller.algo;

import javax.swing.JTable;

import cmsc125.mp1.controller.datastructures.ProcessesQueue;
import cmsc125.mp1.view.panels.SimulationPanel;

public class SJFManager extends Thread {

	private SimulationPanel simulationPanel;
	private JTable resourcesTable;
	private JTable timeTable;
	private ProcessesQueue readyQueue;
	
	public SJFManager(SimulationPanel simulationPanel, 
			JTable resourcesTable, JTable timeTable) {
		this.simulationPanel = simulationPanel;
		this.resourcesTable = resourcesTable;
		this.timeTable = timeTable;
		readyQueue = new ProcessesQueue();
	}

	public void startSimulation() {
		//initProcessesInVector();
		//sortProcessesToReadyQueue();
		
		start();
	}
	
	@Override
	public void run() {
		
	}
}
