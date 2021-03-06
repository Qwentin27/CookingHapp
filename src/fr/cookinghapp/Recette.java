package fr.cookinghapp;

import java.util.ArrayList;

import javafx.concurrent.Task;

public class Recette implements Comparable<Recette> {
	
	private String nom;
	private TypeRecette type;
	private int nbPersonnes;
	private ArrayList<String> etapes;
	private ArrayList<Ingredient> ingredients;
	private float note;
	private int nombre_votants;
	private String urlImage;
	private static int comparateur;
	
	public Recette(String nom, TypeRecette type, int nbPersonnes,
			ArrayList<String> etapes, ArrayList<Ingredient> ingredients,
			String urlImage) {
		this(nom, type, nbPersonnes, etapes, ingredients, 0, 0, urlImage);
	}
	
	public Recette(String nom, TypeRecette type, int nbPersonnes,
			ArrayList<String> etapes, ArrayList<Ingredient> ingredients,
			float note, int nombre_votants,
			String urlImage) {
		this.nom = nom;
		this.type = type;
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(note, nombre_votants);
		this.urlImage = urlImage;
		comparateur = 0;
	}
	
	public Recette(String nom, int type, int nbPersonnes,
			ArrayList<Ingredient> ingredients, ArrayList<String> etapes, 
			float note, int nombre_votants,
			String urlImage) throws MauvaisTypeException {
		this.nom = nom;
		setType(type);
		this.nbPersonnes = nbPersonnes;
		this.etapes = etapes;
		this.ingredients = ingredients;
		this.setNote(note, nombre_votants);
		this.urlImage = urlImage;
		comparateur = 0;
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

	public ArrayList<Ingredient> getIngredients(int nbPersonnes) {
		ArrayList<Ingredient> out = new ArrayList<Ingredient>();
		for (Ingredient ing : ingredients) {
			out.add(new Ingredient(ing.getNom(), Vue.getDf().format(((float)(ing.getQuantite()*nbPersonnes))/((float)this.nbPersonnes)), ing.getMesure()));
		}
		return out;
	}

	public ArrayList<Ingredient> getIngredients() {
		return this.ingredients;
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
/*
	public String formatNote() {
		if(note - Math.round(note) > 0) return Vue.getDf().format(note);
		return "" + Math.round(note);
	}*/
	
	public String getNoteToEtoiles() {
		String out = "";
		for(int i=0; i<5; i++) {
			if(note >= (1+i)) out += "\u2605";
			else out += "\u2606";
		}
		return out;
	}
	
	public String getNoteToString() {
		return (note==Math.round(note)?Math.round(note):Vue.getDf().format(note))+"/5";
	}
	
	public String getVotantsToString() {
		if(nombre_votants >= 1000000000) return nombre_votants/1000000000 + "Mi";
		if(nombre_votants >= 1000000) return nombre_votants/1000000 + "M";
		if(nombre_votants >= 1000) return nombre_votants/1000 + "k";
		return nombre_votants+"";
	}
	
	public void addNote(int note) {
		this.note = ((this.note)*this.nombre_votants+note)/(this.nombre_votants+1);
		this.nombre_votants++;
    	Recette r = this;
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
        		SQL.editNoteRecette(r.nom, r.note, r.nombre_votants);
				return null;
            }
        };
        new Thread(task).start();
	}

	public void setNote(float note,int nombre_votants) {
		this.note = note;
		this.nombre_votants = nombre_votants;
	}

	public int getNombre_votants() {
		return nombre_votants;
	}

	public static int getComparator() {
		return comparateur;
	}

	public static void setComparator(int comparator) {
		comparateur = comparator;
	}
	
	@Override
	public String toString() {
		return this.toString(-1);
	}
	
	public String toString(int nbPersonnes) {
		String ingStr = "Ingrédients:";
		if(this.ingredients.isEmpty())
			ingStr = "Aucun ingrédient.";
		else
			for(Ingredient ing : (nbPersonnes==-1?getIngredients():getIngredients(nbPersonnes))) {
				ingStr = ingStr + "\n - " + ing.toString();
			}
		String etapes = "Etapes de la recette:";
		if(this.etapes.isEmpty())
			etapes = "Aucune étape.";
		else
			for(String e : this.etapes) {
				etapes = etapes + "\n" + e;
			}
		return this.nom + " [" + this.type.name() + "] " + this.note + "(" + this.nombre_votants + ")\n"
				+ ingStr + "\n" + etapes;
	}

	@Override
	public int compareTo(Recette r) {
		switch (comparateur) {
		case 1: //Comparaison par note
			return Float.compare(this.note, r.note);
		case 0: //Comparaison par nom
		default:
			return this.nom.compareTo(r.nom);
		}
	}

	public boolean hasImage() {
		return !this.urlImage.isEmpty();
	}

	public String getImage() {
		return this.urlImage;
	}
}
