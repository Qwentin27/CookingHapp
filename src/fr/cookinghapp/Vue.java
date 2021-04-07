package fr.cookinghapp;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Vue extends Application{

	public static void main(String[] args) {
		launch(args);

	}
	
	
	//affiche un menu simple		
	public void start(Stage primaryStage) throws Exception {
		Parent page = FXMLLoader.load(Vue.class.getResource("AppliCookingHapp.fxml"));
		Scene scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}

}
