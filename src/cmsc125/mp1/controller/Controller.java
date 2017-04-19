package cmsc125.mp1.controller;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import cmsc125.mp1.constants.ScreenConstants;
import cmsc125.mp1.view.GanttChartStage;
import cmsc125.mp1.view.InputTablePanel;
import cmsc125.mp1.view.SimulationPanel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {	
	
	@FXML private TextField numProcessField, numResourceField, quantumField;
	@FXML private Button randProcNumBtn, randResNumBtn, randSelectAlgoBtn, randResBtn, showResBtn, startSimulationBtn, randProcessInfoBtn;
	@FXML private CheckBox fcfsCB, sjfCB, srtfCB, rrCB, npprioCB, prioCB;
	
	private InputTablePanel itp;
	private ArrayList<String> selectedAlgos;
	private JFrame frame1;

	public Controller(){
		itp = new InputTablePanel();
		frame1 = new JFrame();
		frame1.setTitle("Resources Table");
		frame1.setLocation(800, 0);
		frame1.setResizable(false);
		frame1.setSize(550,370);
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
		ArrayList<CheckBox> checkBoxList = new ArrayList<CheckBox>();
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
		itp.randResourcesTable();
	}
	
	@FXML public void randProcessInfo(MouseEvent event){
	}
	
	@FXML public void startSimulation(MouseEvent event){
		SimulationPanel p1 = new SimulationPanel();
		p1.startSimulation(selectedAlgos, itp.getResourcesTable(), itp.getTimeTable(), quantumField.getText());
		JFrame f2 = new JFrame();
		f2.setTitle("Simulation");
		f2.setLocation(0, 230);
		f2.setSize(ScreenConstants.WIDTH, ScreenConstants.HEIGHT-270);
		f2.setContentPane(p1);
		f2.setVisible(true);
		frame1.setVisible(false);
        Main.ganttVisual = new GanttChartStage();
		Main.ganttVisual.initGantt(itp.numProcess);
		Main.ganttVisual.setX(0);
		Main.ganttVisual.setY(230);
		
	}
	
	@FXML public void showResourcesTable(MouseEvent event){
		frame1.setVisible(true);
	}
}
