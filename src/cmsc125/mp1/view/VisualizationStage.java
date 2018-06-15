package cmsc125.mp1.view;

import cmsc125.mp1.constants.ScreenConstants;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class VisualizationStage extends Stage{

	public Pane vbox;

	public VisualizationStage(String name) {
		vbox = new VBox();
		
    	Scene scene  = new Scene(vbox);
        scene.setFill(Color.WHITESMOKE);
        scene.getStylesheets().add(getClass().getResource("ganttchart.css").toExternalForm());
        this.setTitle(name);
        this.setHeight(ScreenConstants.HEIGHT*3/4); 
        this.setWidth(ScreenConstants.WIDTH/3); 
        this.setScene(scene);
        this.show();
	}
	
}
