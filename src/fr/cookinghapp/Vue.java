package fr.cookinghapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fr.cookinghapp.resources.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Vue extends Application implements Observer {

	private static Modele modele;
	private static Scene scene;

	public static Modele getModele() {
		return modele;
	}

	public static void main(String[] args) {
		launch(args);

	}
	
	//affiche un menu simple		
	public void start(Stage primaryStage) throws Exception {
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("CookingHapp.");
		//primaryStage.getIcons().add(new Image("images/main_select/pot-chaud.png"));
		primaryStage.show();
		modele = new Modele();
		modele.addObserver(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof ArrayList) {
			VBox liste = (VBox) scene.lookup("#recettes_vbox");
			liste.getChildren().clear();
			for(Button b : (ArrayList<Button>) arg)
				liste.getChildren().add(b);
		}
	}

}
