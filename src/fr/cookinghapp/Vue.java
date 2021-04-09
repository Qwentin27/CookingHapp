package fr.cookinghapp;


import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Vue extends Application{

	public static void main(String[] args) {
		launch(args);

	}
	
	
	//affiche un menu simple		
	public void start(Stage primaryStage) throws Exception {
		Parent page = FXMLLoader.load(Vue.class.getResource("AppliCookingHapp.fxml"));
		Scene scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setTitle("CookingHapp");
		primaryStage.setResizable(false);
		primaryStage.show();	
		
	}

}
