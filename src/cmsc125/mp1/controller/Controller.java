package cmsc125.mp1.controller;

import cmsc125.mp1.view.View;

public class Controller {

	private View view;
	
	public Controller(View view) {
		this.view = view;
	}
	
	public void start() {
		view.show();
	}
}
