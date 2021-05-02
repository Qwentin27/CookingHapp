package fr.cookinghapp;

import java.io.IOException;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;

public class SAjoutRecettesControleur {

	
	@FXML
	public Button button_menu;
	
	@FXML
	public Button button_rajouter_ing;
	
	@FXML
	public Button button_ajouter_inst;
	
	@FXML
	public Button button_ajouter_recette;
	
	@FXML
	public MenuButton type_ajout_recette;
	
	@FXML 
	public TextArea instruction_ajout_recette;
	
	@FXML
	public TextArea ingredient_ajout_recette;
	
	@FXML
	public TextArea	nb_personne_ajout_recette;
	
	@FXML
	public TextArea retour_ajout_recette;
	
	@FXML
	public TextArea instruction_ajout_recette1;
	
	@FXML
	public Button button_ajouter_inst1;

	
	public void clic_menu() throws IOException{
		
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_ajout_ing() {
		
		String ing = ingredient_ajout_recette.getText();
		System.out.println(ing);
	}
	
	public void clic_ajout_inst(){
		
		String inst = instruction_ajout_recette.getText();
		System.out.println(inst);
	}
	
	public void valider_ajout_recette() {
		
		// Y ajouter la validation du nombre de personnes
		
		String nom = retour_ajout_recette.getText();
		System.out.println(nom);
	}
	
	public void clic_ajout_url(){
		
		String url = retour_ajout_recette.getText();
		System.out.println(url);
	}
}
