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
		itp.randResourcesTable();
	}
	
	@FXML public void randProcessInfo(MouseEvent event){
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
		
		AlgoSimulator algoSimulator = new AlgoSimulator(itp.numProcess, selectedAlgos, itp.getResourcesTable(), itp.getTimeTable(), quantumField.getText(), Integer.parseInt(visualizationSpeed.getText()));
		algoSimulator.startSimulation();
		frame1.setVisible(false);
	}
	
	@FXML public void showResourcesTable(MouseEvent event){
		frame1.setVisible(true);
	}
	
	@FXML public void updateVisualizationSpeed(KeyEvent event){
		if (event.getCode().equals(KeyCode.ENTER))
        {
			AlgoSimulator.visualizationSpeed = Integer.parseInt(visualizationSpeed.getText())*1000;
        }
		
	}
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmsc125.mp1.view.InputTablePanel;
import cmsc125.mp1.view.MenuPanel;
import cmsc125.mp1.view.ResultsPanel;
import cmsc125.mp1.view.SimulationPanel;
import cmsc125.mp1.view.ViewFrame;

public class Controller {

	private ViewFrame view;

	public Controller(ViewFrame view) {
		this.view = view;
		addButtonListeners();
		view.setCurrentPanel("menuPanel");
	}

	public void addButtonListeners() {
		MenuPanel menuPanel = new MenuPanel();
		InputTablePanel inputTablePanel = new InputTablePanel();
		SimulationPanel simulationPanel = new SimulationPanel();
		ResultsPanel resultsPanel = new ResultsPanel();

		view.add(menuPanel, "menuPanel");
		view.add(inputTablePanel, "inputTablePanel");
		view.add(simulationPanel, "simulationPanel");
		view.add(resultsPanel, "resultsPanel");

		menuPanel.getInputDataButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("inputTablePanel");
			}
		});

		inputTablePanel.getRandNumProcessesButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randNumProcesses();
				inputTablePanel.setResourcesTableRowSize(inputTablePanel.getNumProcesses().getSelectedIndex() + 1);
			}
		});

		inputTablePanel.getRandNumResourcesButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randNumResources();
				inputTablePanel.setResourcesTableColumnSize(inputTablePanel.getNumResources().getSelectedIndex() + 1);
			}
		});

		inputTablePanel.getRandCPUSchedAlgosButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randCPUSchedAlgos();
			}
		});

		inputTablePanel.getStartSimulationButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("simulationPanel");
				simulationPanel.startSimulation(inputTablePanel.getSelectedAlgosFromCheckbox(),
						inputTablePanel.getResourcesTable(), inputTablePanel.getTimeTable(),
						inputTablePanel.getQuantumField());
				// view.setCurrentPanel("menuPanel");
			}
		});

		inputTablePanel.getNumProcesses().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.setResourcesTableRowSize(
						Integer.parseInt((String) inputTablePanel.getNumProcesses().getSelectedItem()));

			}
		});

		inputTablePanel.getNumResources().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.setResourcesTableColumnSize(
						Integer.parseInt((String) inputTablePanel.getNumResources().getSelectedItem()));

			}
		});

		inputTablePanel.getRandResourcesTableButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTablePanel.randResourcesTable();
			}
		});
	}
}
