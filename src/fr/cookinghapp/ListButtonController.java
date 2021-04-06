package fr.cookinghapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ListButtonController implements EventHandler<ActionEvent>{

	private Modele model;
	private Vue view;
	
	
	public ListButtonController(Modele model, Vue view) {
		super();
		this.model = model;
		this.view = view;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	// Code executé lorsque l'event survient
	@Override
	public void handle(ActionEvent event) {
		// Récupère les infos du modèle 
		// met à jour la vue 
		
		// --> affichage de la stage avec la liste de courses <--
		
		
		// On peut obtenir des valeurs de la vue et modifier le modèle en conséquence 
		
		// Possibilité de mettre le code du controlleur directement dans la vue 
		// si ce dernier est court (fonction lambda)
		
		// Mise à jour de la vue avec les valeurs de sortie du modèle 
		
	}

}
