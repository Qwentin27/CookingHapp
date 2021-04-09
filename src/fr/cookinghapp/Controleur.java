package fr.cookinghapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
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

	private void setMainMenuImage(String image) {
		try {
			ImageView img = new ImageView(new Image(new FileInputStream(Resources.getResource("images/main_select/"+image).getFile())));
	        img.setFitHeight(24);
	        img.setFitWidth(24);
			main_menu.setGraphic(img);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void clic_entrees() {
		setMainMenuImage("entree.png");
		main_menu.setText(main_select_entrees.getText());
		Vue.getModele().selectionType(TypeRecette.Entrée);
	}
	public void clic_plats() {
		setMainMenuImage("plat.png");
		main_menu.setText(main_select_plats.getText());
		Vue.getModele().selectionType(TypeRecette.Plat);
	}
	public void clic_desserts() {
		setMainMenuImage("dessert.png");
		main_menu.setText(main_select_desserts.getText());
		Vue.getModele().selectionType(TypeRecette.Dessert);
	}
	public void clic_changement_sens() {
		if(chngmnt_sens.getText().equals("\u25b2"))
			chngmnt_sens.setText("\u25bc");
		else
			chngmnt_sens.setText("\u25b2");
		Vue.getModele().setSens();
	}
	
}
