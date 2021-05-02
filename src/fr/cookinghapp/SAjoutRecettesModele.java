package fr.cookinghapp;

import java.util.ArrayList;
import java.util.Observable;

public class SAjoutRecettesModele extends Observable {

	private TypeRecette type;
	private ArrayList<String> etapes;
	private ArrayList<Ingredient> ingredients;
	private String urlImage;
	
	public void setType(TypeRecette type) {
		this.type = type;
	}

	public void ajoutIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
	
	public void ajoutEtape(String etape) {
		etapes.add(etape);
	}
	
	public void ajoutUrl(String urlImage) {
		this.urlImage =urlImage;
	}
	
	public void ajouterRecette(String nom, int nbPersonnes) {
		SQL.ajoutRecette(nom, type, nbPersonnes, ingredients, etapes, urlImage);
		this.setChanged();
		this.notifyObservers("Recette ajouté");
	}

	public void retourMessage(String message) {
		this.setChanged();
		this.notifyObservers(message);
	}

}
