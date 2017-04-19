package cmsc125.mp1.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class Main extends Application {
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/cmsc125/mp1/view/GUI.fxml"));
        Scene scene = new Scene(root, 780, 190);
        primaryStage.setResizable(false);
        primaryStage.setTitle("CMSC125-MP1: Simulation of CPU Scheduling Algorithm and Bankers Algorithm");
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
}
