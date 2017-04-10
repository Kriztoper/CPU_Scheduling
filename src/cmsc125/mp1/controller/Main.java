package cmsc125.mp1.controller;

import cmsc125.mp1.view.View;

public class Main {

	public static void main(String[] args) {
		View view = new View();
		Controller controller = new Controller(view);
		controller.start();
	}
}
