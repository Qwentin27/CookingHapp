package fr.cookinghapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class Controlleur {
	
	@FXML
	public TextArea test;
	
	public void clic() {
		System.out.println("Clic du bouton.");
		test.setText("Nouveau texte");
	}

}
