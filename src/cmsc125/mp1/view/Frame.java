package cmsc125.mp1.view;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame() {
		setTitle("MP1 Bankers and "
				+ "CPU Scheduling Algo Simulation by "
				+ "Garcia and Urmeneta");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setSize(ScreenConstants.WIDTH,
				ScreenConstants.HEIGHT);
		setResizable(true);
	}
}
