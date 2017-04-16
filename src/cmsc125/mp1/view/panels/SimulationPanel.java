package cmsc125.mp1.view.panels;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;

import cmsc125.mp1.controller.AlgoSimulator;
import cmsc125.mp1.controller.utils.ResourcesTableModel;

public class SimulationPanel extends JPanel {

	private AlgoSimulator algoSimulator;
	
	public SimulationPanel() {
		algoSimulator = new AlgoSimulator();
		setBackground(Color.YELLOW);
	}
	
	public void startSimulation(ArrayList<String> algos, 
			JTable resourcesTable, JTable timeTable) {
		String[][] data = ((ResourcesTableModel)
				resourcesTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel)
				timeTable.getModel()).getData();
		System.out.print("Resources ");
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i][0]+" ");
		}
		System.out.print("\nArrival time ");
		for (int i = 0; i < timeData.length; i++) {
			System.out.print(timeData[i][0]+" ");
		}
		System.out.println();
		
		algoSimulator.startSimulation(this, algos, 
				resourcesTable, timeTable);
		
		// return to menu panel
		//TODO: return to menu panel
	}
}
