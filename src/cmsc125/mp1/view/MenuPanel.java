package cmsc125.mp1.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private JButton inputDataButton;
	private JButton aboutButton;
	private JButton creditsButton;

	public MenuPanel() {
		initPanel();
		initComponents();
		addComponents();
	}

	public void initPanel() {
		setLayout(new FlowLayout());
		setBackground(Color.CYAN);
	}

	public void initComponents() {
		inputDataButton = new JButton("Input Data and Simulate");

		aboutButton = new JButton("About");

		creditsButton = new JButton("Credits");
	}

	public void addComponents() {
		add(inputDataButton);
		add(aboutButton);
		add(creditsButton);
	}

	public JButton getInputDataButton() {
		return inputDataButton;
	}

	public void setInputDataButton(JButton inputDataButton) {
		this.inputDataButton = inputDataButton;
	}

	public JButton getAboutButton() {
		return aboutButton;
	}

	public void setAboutButton(JButton aboutButton) {
		this.aboutButton = aboutButton;
	}

	public JButton getCreditsButton() {
		return creditsButton;
	}

	public void setCreditsButton(JButton creditsButton) {
		this.creditsButton = creditsButton;
	}
}
