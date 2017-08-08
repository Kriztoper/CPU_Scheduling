package cmsc125.mp1.controller;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import cmsc125.mp1.algorithms.AlgoSimulator;
import cmsc125.mp1.view.InputTablePanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {	
	
	@FXML private TextField numProcessField, numResourceField, quantumField, visualizationSpeed;
	@FXML private Button randProcNumBtn, randResNumBtn, randSelectAlgoBtn, randResBtn, showResBtn, startSimulationBtn, randProcessInfoBtn;
	@FXML private CheckBox fcfsCB, sjfCB, srtfCB, rrCB, npprioCB, prioCB;
	
	private InputTablePanel itp;
	private ArrayList<String> selectedAlgos;
	private JFrame frame1;
	private ArrayList<CheckBox> checkBoxList;

	public Controller(){
		itp = new InputTablePanel();
		frame1 = new JFrame();
		frame1.setTitle("Resources Table");
		frame1.setLocation(0, 230);
		frame1.setResizable(true);
		frame1.setSize(1035,380);
		frame1.setVisible(false);
		frame1.add(itp);

	}
	
	@FXML public void randNumProcesses(MouseEvent event) {
		Random random = new Random();
		int randomIndex = random.nextInt(20)+1;
		numProcessField.setText(Integer.toString(randomIndex));
		itp.numProcess = randomIndex;
		itp.setResourcesTableRowSize(itp.numProcess);
	}

	@FXML public void randNumResources(MouseEvent event) {
		Random random = new Random();
		int randomIndex = random.nextInt(10)+1;
		numResourceField.setText(Integer.toString(randomIndex));
		itp.numResource = randomIndex;
		itp.setResourcesTableColumnSize(itp.numResource);
	}

	@FXML public void randCPUSchedAlgos(MouseEvent event) {
		Random random = new Random();
		
		checkBoxList = new ArrayList<CheckBox>();
		checkBoxList.add(fcfsCB);
		checkBoxList.add(sjfCB);
		checkBoxList.add(rrCB);
		checkBoxList.add(prioCB);
		checkBoxList.add(npprioCB);
		checkBoxList.add(srtfCB);
		
		selectedAlgos = new ArrayList<String>();
		for (CheckBox cb: checkBoxList){
			int randomIndex = random.nextInt(100) + 1;
			boolean randomBool = ((randomIndex <= 50) ? (false) : (true));
			cb.setSelected(randomBool);
			if (randomBool)
				selectedAlgos.add(cb.getText());
		}
	}

	@FXML public void randResourcesTable(MouseEvent event) {
		itp.randAllocatedTable();
		itp.randAvailableTable();
		itp.randMaximumTable();
	}
	
	@FXML public void randProcessInfo(MouseEvent event){
		itp.randATPT();
	}
	
	@FXML public void startSimulation(MouseEvent event){
		checkBoxList = new ArrayList<CheckBox>();
		checkBoxList.add(fcfsCB);
		checkBoxList.add(sjfCB);
		checkBoxList.add(rrCB);
		checkBoxList.add(prioCB);
		checkBoxList.add(npprioCB);
		checkBoxList.add(srtfCB);
		selectedAlgos = new ArrayList<String>();
		for (CheckBox cb: checkBoxList){
			if (cb.isSelected())
				selectedAlgos.add(cb.getText());
		}
		
		AlgoSimulator algoSimulator = new AlgoSimulator(itp.numProcess, selectedAlgos, itp.getAllocatedTable(), itp.getMaximumTable(), itp.getAvailableTable(), itp.getTimeTable(), quantumField.getText(), Integer.parseInt(visualizationSpeed.getText()));
		algoSimulator.startSimulation();
		frame1.setVisible(false);
	}
	
	@FXML public void showResourcesTable(MouseEvent event){
		frame1.setVisible(true);
	}
	
	@FXML public void updateVisualizationSpeed(KeyEvent event){
		if (event.getCode().equals(KeyCode.ENTER))
        {
			AlgoSimulator.visualizationSpeed = Integer.parseInt(visualizationSpeed.getText());
        }
	}
	
	@FXML public void setQuantumValue(KeyEvent event){
		if (event.getCode().equals(KeyCode.ENTER))
        {
			AlgoSimulator.visualizationSpeed = Integer.parseInt(visualizationSpeed.getText());
        }
	}
	
	@FXML public void setNumProcesses(KeyEvent event){
		if (event.getCode().equals(KeyCode.ENTER))
        {
			int val = Integer.parseInt(numProcessField.getText());
			if (val > 20)
				itp.numProcess = 20;
			else if (val <0)
				itp.numProcess = 1;
			else				
				itp.numProcess = val;
			itp.setResourcesTableRowSize(itp.numProcess);
        }
	}
	
	@FXML public void setNumResources(KeyEvent event){
		if (event.getCode().equals(KeyCode.ENTER))
        {
			int val = Integer.parseInt(numResourceField.getText());
			if (val > 10)
				itp.numResource = 10;
			else if (val < 0)
				itp.numResource = 1;
			else
				itp.numResource = val;
			itp.setResourcesTableColumnSize(itp.numResource);
        }
	}
}
