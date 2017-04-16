package cmsc125.mp1.view.panels;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import cmsc125.mp1.controller.AlgoSimulator;
import cmsc125.mp1.controller.utils.ResourcesTableModel;

public class SimulationPanel extends JPanel {

	private AlgoSimulator algoSimulator;
	private JPanel panel;
	
	public SimulationPanel() {
		setLayout(null);
		algoSimulator = new AlgoSimulator();
		//setBackground(Color.YELLOW);
	}
	
	public void startSimulation(ArrayList<String> algos, 
			JTable resourcesTable, JTable timeTable) {

		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// block to print the resources and arrival time
		String string = "";
		String[][] data = ((ResourcesTableModel)
				resourcesTable.getModel()).getData();
		String[][] timeData = ((ResourcesTableModel)
				timeTable.getModel()).getData();
		string += "Resources = [";
		String resString = "";
		for (int i = 0; i < data.length; i++) {
			resString += data[i][0] + ", ";
		}
		string += resString.substring(0, 
				resString.length() - 2) + "]\nArrival time = [";
		String arrivalString = "";
		for (int i = 0; i < timeData.length; i++) {
			arrivalString += timeData[i][0] + ", ";
		}
		string += arrivalString.substring(0, 
				arrivalString.length() - 2) + "]\n";
		JLabel label = new JLabel(
				"<html><div style='text-align: center;'>" + 
						string + "</div></html>");
		label.setFont(new Font("Verdana",1,20));
		label.setSize(1000, 50);
		label.setLocation(5, 5);
		label.setBorder(new LineBorder(Color.BLACK));
		add(label);
		System.out.print(string);
		
		// init panel for animation
		setPanel(new JPanel());
		getPanel().setBackground(Color.YELLOW);
		getPanel().setPreferredSize(getPreferredSize());
		add(getPanel());
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		algoSimulator.startSimulation(this, algos, 
				resourcesTable, timeTable);
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
}
