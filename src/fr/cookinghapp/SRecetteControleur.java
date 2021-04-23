package fr.cookinghapp;

import java.io.IOException;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;


public class SRecetteControleur extends Observable{
	
	@FXML
	public Button button_list_recette;
	
	@FXML
	public Button button_recette_recette;
	
	@FXML
	public ToggleButton note_plus;
	
	@FXML
	public ToggleButton note_moins;
	
	public void clic_liste_recette() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_Liste.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_note_plus() { // Incrémentation de la note associée à la recette
		//TODO test <= 5
		if (Vue.main_note < 5) {
			Vue.main_note++;
		}
		System.out.println(Vue.main_note);
		
		
	}
	
	public void clic_note_moins() { // Décrémentation de la note associée à la recette
		//TODO test >= 0
		if (Vue.main_note > 0) {
			Vue.main_note--;
		}
		System.out.println(Vue.main_note);
	}
	
	public void clic_valider() { // Validation de la note
		//TODO envoyer à l'SQL
	}
	
	public void clic_recette_recette() throws IOException{ // Affichage de la recette associée
		
		Vue.getSmodele().setRecette(Vue.getModele().getRecetteVisionnee());
		
	}
	
	public void clic_ajouter_liste() { // Ajout des ingrédients a la liste de course
		
	}

}
