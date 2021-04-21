package fr.cookinghapp;

import java.io.IOException;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class SRecetteControleur extends Observable{
	
	@FXML
	public Button button_list;
	
	public void clic_liste() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_Liste.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_note_plus() {
		
	}
	
	public void clic_note_moins() {
		
	}
	
	public void clic_valider() {
		
	}
	
	public void clic_recette() {
		
	}
	
	public void clic_ajouter_liste() {
		
	}

}
