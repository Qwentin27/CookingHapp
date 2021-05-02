package fr.cookinghapp;

import java.util.ArrayList;
import java.util.Observable;

public class SAjoutRecettesModele extends Observable {

	private TypeRecette type;
	private int nbPersonnes;
	private ArrayList<String> etapes;
	private ArrayList<Ingredient> ingredients;
	private String urlImage;
	
	public void setType(TypeRecette type) {
		this.type = type;
	}
	
	public void setType(int nbPersonnes) {
		this.nbPersonnes = nbPersonnes;
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
	
	public void ajouterRecette(String nom) {
		SQL.ajoutRecette(nom, type, nbPersonnes, ingredients, etapes, urlImage);
	}

}
