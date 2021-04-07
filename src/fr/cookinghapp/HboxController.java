package fr.cookinghapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HboxController implements EventHandler<ActionEvent> {

	private Modele model;
	private Vue view;
	
	public HboxController(Modele model, Vue view) {
		super();
		this.model = model;
		this.view = view;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Code executé lorsque l'event survient
	
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		// Gestion lorsque l'utilisateur clique sur un menu
		
	}

}
