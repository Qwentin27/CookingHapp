package fr.cookinghapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

	private static boolean antiDoubleClic;
	
	public static void setAntiDoubleClic(boolean valeur) {
		antiDoubleClic = valeur;
	}

	public Controleur() {
		antiDoubleClic = false;
	}

	private void setMainMenuImage(String image) {
		try {
			ImageView img = new ImageView(new Image(new FileInputStream(Resources.getResource("images/main_select/"+image).getFile())));
	        img.setFitHeight(24);
	        img.setFitWidth(24);
			main_menu.setGraphic(img);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
	}

	public void clic_entrees() {
		if(!antiDoubleClic) {
			antiDoubleClic = true;
			setMainMenuImage("entree.png");
			main_menu.setText(main_select_entrees.getText());
			Vue.getModele().selectionType(TypeRecette.Entrée);
		}
	}
	public void clic_plats() {
		if(!antiDoubleClic) {
			antiDoubleClic = true;
			setMainMenuImage("plat.png");
			main_menu.setText(main_select_plats.getText());
			Vue.getModele().selectionType(TypeRecette.Plat);
		}
	}
	public void clic_desserts() {
		if(!antiDoubleClic) {
			antiDoubleClic = true;
			setMainMenuImage("dessert.png");
			main_menu.setText(main_select_desserts.getText());
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
	
	
	
}
