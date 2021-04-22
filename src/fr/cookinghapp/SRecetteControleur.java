package fr.cookinghapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class SRecetteControleur extends Observable{
	
	@FXML
	public Button button_list;
	
	public void clic_liste() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_Liste.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_note_plus() { // Incrémentation de la note associée à la recette
		
	}
	
	public void clic_note_moins() { // Décrémentation de la note associée à la recette
		
	}
	
	public void clic_valider() { // Validation de la note
		
	}
	
	public void clic_recette() { // Affichage de la recette associée
		
		Scene scene = Vue.getAppStage().getScene();
		Label nom = (Label) scene.lookup("#titre");
		nom.setText("Etapes :");
		VBox liste = (VBox) scene.lookup("#box_ingredients");
		liste.getChildren().clear();
		
		/*
		for(Etape step : r.getEtapes()) {
			Label etape = step.toString();
			//CheckBox cb = new CheckBox(step.toString());
			cb.setTextAlignment(TextAlignment.LEFT);
			cb.getStyleClass().add("box_ingredients");
			liste.getChildren().add(etape);
		}
		*/
	}
	
	public void clic_ajouter_liste() { // Ajout des ingrédients a la liste de course
		
	}

}
