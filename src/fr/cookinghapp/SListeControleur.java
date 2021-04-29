package fr.cookinghapp;

import java.io.IOException;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SListeControleur extends Observable {
	
	@FXML
	public Button button_menu;
	
	@FXML
	public Button button_recette;
	
	// Faire le bouton supprimer 
	
	public void clic_menu() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_recette_liste() throws IOException{ // Affichage de la recette correspondante
		Vue.getModele().passageListeVersRecette();
	}
	
	public void clic_impression() throws IOException {
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
}
