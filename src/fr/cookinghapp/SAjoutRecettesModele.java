package fr.cookinghapp;

import java.util.ArrayList;
import java.util.Observable;

import javafx.concurrent.Task;

public class SAjoutRecettesModele extends Observable {

	private TypeRecette type;
	private ArrayList<String> etapes;
	private ArrayList<Ingredient> ingredients;
	private String urlImage;
	
	public SAjoutRecettesModele() {
		type = TypeRecette.Aucun;
		etapes = new ArrayList<String>();
		ingredients = new ArrayList<Ingredient>();
		urlImage = "";
	}
	
	public void setType(TypeRecette type) {
		this.type = type;
	}
	
	public boolean typePasSet() {
		return this.type == TypeRecette.Aucun;
	}

	public void ajoutIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
	
	public void ajoutEtape(String etape) {
		etapes.add(etape);
	}
	
	public boolean etapesEstVide() {
		return etapes.isEmpty();
	}
	
	public boolean ingredientsEstVide() {
		return ingredients.isEmpty();
	}
	
	public void ajoutUrl(String urlImage) {
		this.urlImage = urlImage;
		this.setChanged();
		this.notifyObservers("URL d'une photo définie!");
		clearRetourMessage();
	}
	
	public void ajouterRecette(String nom, int nbPersonnes) {
		SQL.ajoutRecette(nom, type, nbPersonnes, ingredients, etapes, urlImage);
		this.setChanged();
		this.notifyObservers("Recette ajouté");
		clearRetourMessage();
	}

	public void retourMessage(String message) {
		this.setChanged();
		this.notifyObservers(message);
		clearRetourMessage();
	}
	
	private void clearRetourMessage() {
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    			}
    			return null;
            }
        };
        task.setOnSucceeded(taskFinishEvent -> {
			Vue.getAjmodele().setChanged();
			Vue.getAjmodele().notifyObservers("");
        });
        new Thread(task).start();
	}

	public void setTransparentText() {
		this.setChanged();
		this.notifyObservers();
	}

}
