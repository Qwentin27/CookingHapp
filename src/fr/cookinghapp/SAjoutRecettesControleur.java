package fr.cookinghapp;

import java.io.IOException;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

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
	
	public void clic_menu() throws IOException{
		
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
	}
	
	public void clic_ajout_ing() {
		
	}
	
	public void clic_ajout_inst(){
		
	}
	
	public void valider_ajout_recette() {
		
	}
}
