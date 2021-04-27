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
	
	@FXML
	public ToggleButton note_valider;
	
	public void clic_liste_recette() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_Liste.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_note_plus() { // Incrémentation de la note associée à la recette
		
		int note = Vue.getSmodele().getMain_note();
		if (note < 5) {
			Vue.getSmodele().setMain_note(note++);
		}
		//System.out.println(Vue.main_note);
		Vue.noteTexte.setText(String.valueOf(Vue.getSmodele().getMain_note())); // concaténer avec un "/5"
		
		// ACUTALISATION DE LA VUE A METTRE UNIQUEMENT DANS LA VUE
		
	}
	
	public void clic_note_moins() { // Décrémentation de la note associée à la recette
		
		int note = Vue.getSmodele().getMain_note();
		if (note > 0) {
			Vue.getSmodele().setMain_note(note--);
		}
		//System.out.println(Vue.main_note);
		Vue.noteTexte.setText(String.valueOf(Vue.getSmodele().getMain_note())); // concaténer avec un "/5"
		
		// ACUTALISATION DE LA VUE A METTRE UNIQUEMENT DANS LA VUE
	}
	
	public void clic_valider() { // Validation de la note
		//TODO envoyer à l'SQL
		
		Vue.getModele().getRecetteVisionnee().addNote(Vue.main_note);
		
	}
	
	public void clic_recette_recette() throws IOException{ // Affichage de la recette associée
		
		Vue.getSmodele().setRecette(Vue.getModele().getRecetteVisionnee());
		
	}
	
	public void clic_ajouter_liste() { // Ajout des ingrédients a la liste de course
		
		// le bouton se transforme en bouton ingrédients lorsque la recette est affichée
		// faire un test : si text == ingrédients --> telle fonction
		// 				   sinon --> fonction différente
	}

}
