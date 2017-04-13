package cmsc125.mp1.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cmsc125.mp1.view.View;

public class Controller {

	private View view;
	
	public Controller(View view) {
		this.view = view;
		addButtonListeners();
	}
	
	public void start() {
		view.show();
	}
	
	public void addButtonListeners() {
		
		view.getMenuPanel().getInputDataButton().
			addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				view.setCurrentPanel("inputTablePanel");
			}
		});
	}
}
