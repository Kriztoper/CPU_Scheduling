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

	public void show() {
		frame.setVisible(true);
	}
	
	public void initCardsPanel() {
		cardsPanel = new JPanel();
		cards = new CardLayout();
		cardsPanel.setLayout(cards);
		
		// init panels to be switched as cards
		menuPanel = new MenuPanel();
		inputTablePanel = new InputTablePanel();
		simulationPanel = new SimulationPanel();
		resultsPanel = new ResultsPanel();
		
		cardsPanel.add(menuPanel, "menuPanel");
		cardsPanel.add(inputTablePanel, "inputTablePanel");
		cardsPanel.add(simulationPanel, "simulationPanel");
		cardsPanel.add(resultsPanel, "resultsPanel");
		
		// initial panel to appear is menu panel
		cards.show(cardsPanel, "menuPanel");
	}
	
	
}
