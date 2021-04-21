package fr.cookinghapp;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fr.cookinghapp.resources.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Vue extends Application implements Observer {

	private static Modele modele;
	private static Scene scene;
	private static Stage appStage;
	private static DecimalFormat df;

	public static DecimalFormat getDf() {
		return df;
	}

	public static Modele getModele() {
		return modele;
	}

	public static Stage getAppStage() {
		return appStage;
	}

	public static void main(String[] args) {
		df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		launch(args);
	}
	
	//affiche un menu simple		
	public void start(Stage primaryStage) throws Exception {
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		scene = new Scene(page);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("CookingHapp");
		primaryStage.getIcons().add(Resources.getImage("images/main_select/pot-chaud.png"));
		primaryStage.show();
		appStage = primaryStage;
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
		else if(arg instanceof Recette) {
			Recette r = (Recette) arg;
			try {
				Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_ingredientsRecettes.fxml"));
				Vue.getAppStage().setScene(new Scene(page));
				Scene scene = Vue.getAppStage().getScene();
				Label nom = (Label) scene.lookup("#nom_recette");
				nom.setText(r.getNom());
				VBox liste = (VBox) scene.lookup("#box_ingredients");
				liste.setAlignment(Pos.TOP_LEFT); //TODO A rajouter dans le fichier Scene_ingredientsRecettes.fxml
				liste.getChildren().clear(); //Facultatif, mais permet d'être sûr que la liste d'ingrédients est vide au départ
				for(Ingredient ing : r.getIngredients()) {
					CheckBox cb = new CheckBox(ing.toString());
					cb.setTextAlignment(TextAlignment.LEFT);
					liste.getChildren().add(cb);
				}
				Label noteTexte = (Label) scene.lookup("#note_texte");
				noteTexte.setText(r.formatNote());
				if(r.hasImage()) {
					ImageView img = (ImageView) scene.lookup("#image_recette");
					img.setImage(new Image(r.getImage()));
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
