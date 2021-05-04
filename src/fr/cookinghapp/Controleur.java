package fr.cookinghapp;

import java.io.IOException;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class Controleur extends Observable {
	
	@FXML
	public MenuItem main_select_entrees;
	
	@FXML
	public MenuItem main_select_plats;
	
	@FXML
	public MenuItem main_select_desserts;
	
	@FXML
	public MenuButton main_menu;
	
	@FXML
	public Button chngmnt_sens;
	
	@FXML
	public Button button_list;
	
	@FXML
	public Button button_ajout_recette;

	private static boolean antiDoubleClic;
	
	public static void setAntiDoubleClic(boolean valeur) {
		antiDoubleClic = valeur;
	}

	public Controleur() {
		antiDoubleClic = false;
	}

	public void clic_entrees() {
		if(!antiDoubleClic) {
			antiDoubleClic = true;
			
			Vue.getModele().selectionType(TypeRecette.Entrée);
		}
	}
	public void clic_plats() {
		if(!antiDoubleClic) {
			antiDoubleClic = true;
			Vue.getModele().selectionType(TypeRecette.Plat);
		}
	}
	public void clic_desserts() {
		if(!antiDoubleClic) {
			antiDoubleClic = true;
			Vue.getModele().selectionType(TypeRecette.Dessert);
		}
	}
	public void clic_changement_sens() {
		if(!antiDoubleClic) {
			if(chngmnt_sens.getText().equals("\u25b2"))
				chngmnt_sens.setText("\u25bc");
			else
				chngmnt_sens.setText("\u25b2");
			Vue.getModele().setSens();
		}
	}
	
	public void clic_liste_menu() throws IOException{
		// Changement de scene lors du clic -> vers Liste
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_Liste.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
		Vue.getLmodele().ouvertureListeIngredients();
	}
	
	public void clic_ajout_recettes() throws IOException {
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_ajoutRecettes.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
		Vue.getAjmodele().setTransparentText();
	}
	
	
}
