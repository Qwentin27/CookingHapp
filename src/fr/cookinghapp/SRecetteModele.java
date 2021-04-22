package fr.cookinghapp;

import java.util.Observable;

public class SRecetteModele extends Observable{

	public void setRecette(Recette recette) {
		this.setChanged();
		this.notifyObservers(recette);
	}
	
	
}
