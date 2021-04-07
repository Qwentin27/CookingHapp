package fr.cookinghapp;

import java.util.ArrayList;

public class Recette {
	
	private String nom;
	private TypeRecette type;
	private ArrayList<String> etapes;
	private ArrayList<Ingredient> ingredients;
	private float note;
	private int nombre_votants;
	
	public Recette(String nom, TypeRecette type, 
			ArrayList<String> etapes, ArrayList<Ingredient> ingredients) {
		this.nom = nom;
		this.type = type;
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(0, nombre_votants);
	}
	
	public Recette(String nom, TypeRecette type, 
			ArrayList<String> etapes, ArrayList<Ingredient> ingredients,
			float note, int nombre_votants) {
		this.nom = nom;
		this.type = type;
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(note, nombre_votants);
	}
	
	public Recette(String nom, int type,
			ArrayList<Ingredient> ingredients, ArrayList<String> etapes, 
			float note, int nombre_votants) throws MauvaisTypeException {
		this.nom = nom;
		setType(type);
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(note, nombre_votants);
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
		for (Ingredient ingredient : ingredients) {
			this.ingredients.add(ingredient);
		}
	}

	public void ajoutIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public float getNote() {
		return note;
	}
	
	public void addNote(int note) {
		this.note = (this.note+note)/(this.nombre_votants+1);
		this.nombre_votants++;
	}

	public void setNote(float note,int nombre_votants) {
		this.note = note;
		this.nombre_votants = nombre_votants;
	}

	public int getNombre_votants() {
		return nombre_votants;
	}
	
	@Override
	public String toString() {
		String ingStr = "Ingrédients:";
		if(this.ingredients.isEmpty())
			ingStr = "Aucun ingrédient.";
		else
			for(Ingredient ing : this.ingredients) {
				ingStr = ingStr + "\n - " + ing.toString();
			}
		String etapes = "Etapes de la recette:";
		if(this.ingredients.isEmpty())
			etapes = "Aucune étape.";
		else
			for(String e : this.etapes) {
				etapes = etapes + "\n" + e;
			}
		return this.nom + " [" + this.type.name() + "] " + this.note + "(" + this.nombre_votants + ")\n"
				+ ingStr + "\n" + etapes;
	}
}