package cmsc125.mp1.controller;

import javax.swing.SwingUtilities;

import cmsc125.mp1.view.ViewFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ViewFrame view = new ViewFrame();
				Controller controller = new Controller(view);
			}
		});
	}
}
