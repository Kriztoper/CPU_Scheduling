package cmsc125.mp1.view;

import java.awt.CardLayout;

import javax.swing.JPanel;

import cmsc125.mp1.view.panels.InputTablePanel;
import cmsc125.mp1.view.panels.MenuPanel;
import cmsc125.mp1.view.panels.ResultsPanel;
import cmsc125.mp1.view.panels.SimulationPanel;

public class View {

	private Frame frame;
	private JPanel cardsPanel;
	private CardLayout cards;
	private MenuPanel menuPanel;
	private InputTablePanel inputTablePanel;
	private SimulationPanel simulationPanel;
	private ResultsPanel resultsPanel;
	
	public View() {
		frame = new Frame();
		initCardsPanel();
		frame.setContentPane(cardsPanel);
	}

	public void setCurrentPanel(String panelName) {
		cards.show(cardsPanel, panelName);
		frame.getContentPane().repaint();
	}

	public void show() {
		frame.setVisible(true);
	}
	
	public void initCardsPanel() {
		cardsPanel = new JPanel();
		cards = new CardLayout();
		cardsPanel.setLayout(cards);
		
		// init panels to be switched as cards
		setMenuPanel(new MenuPanel());
		setInputTablePanel(new InputTablePanel());
		setSimulationPanel(new SimulationPanel());
		setResultsPanel(new ResultsPanel());
		
		cardsPanel.add(getMenuPanel(), "menuPanel");
		cardsPanel.add(getInputTablePanel(), "inputTablePanel");
		cardsPanel.add(getSimulationPanel(), "simulationPanel");
		cardsPanel.add(getResultsPanel(), "resultsPanel");
		
		// initial panel to appear is menu panel
		cards.show(cardsPanel, "menuPanel");
	}

	public MenuPanel getMenuPanel() {
		return menuPanel;
	}

	public void setMenuPanel(MenuPanel menuPanel) {
		this.menuPanel = menuPanel;
	}

	public InputTablePanel getInputTablePanel() {
		return inputTablePanel;
	}

	public void setInputTablePanel(InputTablePanel inputTablePanel) {
		this.inputTablePanel = inputTablePanel;
	}

	public SimulationPanel getSimulationPanel() {
		return simulationPanel;
	}

	public void setSimulationPanel(SimulationPanel simulationPanel) {
		this.simulationPanel = simulationPanel;
	}

	public ResultsPanel getResultsPanel() {
		return resultsPanel;
	}

	public void setResultsPanel(ResultsPanel resultsPanel) {
		this.resultsPanel = resultsPanel;
	}
	
	
}
