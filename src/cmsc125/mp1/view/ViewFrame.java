package cmsc125.mp1.view;

import javax.swing.*;
import java.awt.*;

import cmsc125.mp1.constants.ScreenConstants;

public class ViewFrame extends JFrame {
	public static JPanel cardsPanel;
	private CardLayout cards;

	public ViewFrame() {
		setTitle("MP1 Bankers and " + "CPU Scheduling Algo Simulation by " + "Garcia and Urmeneta");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setSize(ScreenConstants.WIDTH, ScreenConstants.HEIGHT);
		setResizable(true);
		setVisible(true);

		initCardsPanel();
		setContentPane(cardsPanel);
	}

	private void initCardsPanel() {
		cards = new CardLayout();
		cardsPanel = new JPanel();
		cardsPanel.setLayout(cards);
	}

	public void addPanel(JPanel panel, String panelName) {
		cardsPanel.add(panel, panelName);
	}

	public void setCurrentPanel(String panelName) {
		cards.show(cardsPanel, panelName);
		getContentPane().repaint(); 
	}

}
