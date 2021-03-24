package fr.cookinghapp;

import java.util.ArrayList;

public class Recette {
	
	private String nom;
	private TypeRecette type;
	private ArrayList<String> etapes;
	private ArrayList<Ingredient> ingredients;
	private int note;
	private int nombre_votants;
	
	public Recette(String nom, TypeRecette type, 
			ArrayList<String> etapes, ArrayList<Ingredient> ingredients) {
		this.nom = nom;
		this.type = type;
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(0);
		this.setNombre_votants(0);
	}
	
	public Recette(String nom, TypeRecette type, 
			ArrayList<String> etapes, ArrayList<Ingredient> ingredients,
			int note, int nombre_votants) {
		this.nom = nom;
		this.type = type;
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(note);
		this.setNombre_votants(nombre_votants);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public TypeRecette getType() {
		return type;
	}

	public void setType(int idType) throws MauvaisTypeException {
		switch(idType) {
		case 0:
			this.type = TypeRecette.Entrée;
			break;
		case 1:
			this.type = TypeRecette.Plat;
			break;
		case 2:
			this.type = TypeRecette.Dessert;
			break;
		case 3:
			this.type = TypeRecette.Boisson;
			break;
		default:
			throw new MauvaisTypeException(idType);
		}
	}

	public void setType(TypeRecette type) {
		this.type = type;
	}

	public ArrayList<String> getEtapes() {
		return etapes;
	}

	public void ajoutEtapes(ArrayList<String> etapes) {
		for (String etape : etapes) {
			this.etapes.add(etape);
		}
	}

	public void ajoutEtape(String etape) {
		this.etapes.add(etape);
	}

	public void setEtapes(ArrayList<String> etapes) {
		this.etapes = etapes;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void ajoutIngredients(ArrayList<Ingredient> ingredients) {
		for (Ingredient ingrediant : ingredients) {
			this.ingredients.add(ingrediant);
		}
	}

	public void ajoutIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public int getNombre_votants() {
		return nombre_votants;
	}

	public void setNombre_votants(int nombre_votants) {
		this.nombre_votants = nombre_votants;
	}
}
