package cmsc125.mp1.controller;

import javax.swing.SwingUtilities;

import cmsc125.mp1.view.View;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
			public void run() {
		    	View view = new View();
		    	Controller controller = new Controller(view);
		    	controller.start();
		    }
		});
	}
}
