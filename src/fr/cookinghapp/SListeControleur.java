package fr.cookinghapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SListeControleur extends Observable {
	
	@FXML
	public Button button_menu;
	
	@FXML
	public Button button_recette;
	
	@FXML
	public Button button_supprimer;
	
	public void clic_menu() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_recette_liste() throws IOException{ // Affichage de la recette correspondante
		Vue.getModele().passageListeVersRecette();
	}
	
	public void clic_supprimer() {
		Scene scene = Vue.getAppStage().getScene();
		VBox liste = (VBox) scene.lookup("#liste_courses_recettes");
		liste.getChildren().clear();
		VBox panneau = (VBox) scene.lookup("#liste_courses_ingredients");
		panneau.getChildren().clear();
		
	}
	
	public void clic_impression() throws IOException {
		TreeMap<String, TreeSet<Ingredient>> listeRecettes = Vue.getLmodele().getListeIngredients();
		ArrayList<Text> listeAImprimer = new ArrayList<Text>();
		for(String nom : listeRecettes.keySet()) {
			Text txt = new Text(nom + ":\n");
			for(Ingredient ing : listeRecettes.get(nom)) {
				txt.setText(txt.getText() + "\t" + ing.toString() + "\n");
			}
			listeAImprimer.add(txt);
		}
		Text[] lAImprimer = new Text[listeAImprimer.size()];
		for (int i=0; i<listeAImprimer.size(); i++) {
			lAImprimer[i]=listeAImprimer.get(i);
		}
		VBox hb = new VBox(lAImprimer);
		Scene scene = new Scene(hb);
		
		new Impression(scene.getRoot(), "Liste de courses");
		
	}
}
