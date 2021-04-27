package fr.cookinghapp;

import java.util.Observable;

public class SRecetteModele extends Observable{

	private int main_note;
	
	public SRecetteModele() {
		
		this.main_note = 3;
	}
	
	public int getMain_note() {
		return main_note;
	}

	public void setMain_note(int main_note) {
		this.main_note = main_note;
		this.setChanged();
		this.notifyObservers(this.main_note);
	}


	public void setRecette(Recette recette) {
		this.setChanged();
		this.notifyObservers(recette);
	}
	
	
}
