package fr.cookinghapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class SListeModele extends Observable{
	
	TreeMap<String, TreeSet<Ingredient>> listeIngredients;
	
	public TreeMap<String, TreeSet<Ingredient>> getListeIngredients() {
		return listeIngredients;
	}

	public SListeModele() {
		listeIngredients = new TreeMap<String, TreeSet<Ingredient>>();
	}
	
	public void ajoutIngredient(String nomRecette, TreeSet<Ingredient> ingredients) {
		if(listeIngredients.containsKey(nomRecette)) {
			TreeSet<Ingredient> lIngredients = listeIngredients.get(nomRecette);
			for (Ingredient ingredient : ingredients) {
				float quantiteIng = 0;
				if(lIngredients.contains(ingredient)) {
					ArrayList<Ingredient> toRemove = new ArrayList<Ingredient>();
					for (Ingredient ing : lIngredients) {
						if(ing.equals(ingredient)) {
							quantiteIng += ing.getQuantite();
							toRemove.add(ing);
						}
					}
					lIngredients.removeAll(toRemove);
				}
				lIngredients.add(new Ingredient(ingredient.getNom(), ingredient.getQuantite()+quantiteIng, ingredient.getMesure()));
			}
			listeIngredients.replace(nomRecette, lIngredients);
		}
		else
			listeIngredients.put(nomRecette, ingredients);
		for (String k : listeIngredients.keySet()) {
			System.out.println(k + ":");
			for(Ingredient ing : listeIngredients.get(k))
				System.out.println("\t" + ing.toString());
		}
	}
	
	public void ouvertureListeIngredients() {
		ArrayList<Button> buttons = new ArrayList<Button>();
		for (String k : listeIngredients.keySet()) {
			Button b = new Button(k);
			b.setBorder(new Border(new BorderStroke(Color.rgb(187, 185, 185), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			b.setOnAction((e) -> {
				reactListeIngredients(k);
			});
			buttons.add(b);
		}
		this.setChanged();
		this.notifyObservers(buttons);
	}
	
	public void reactListeIngredients(String nomRecette) {
		if(listeIngredients.containsKey(nomRecette)) {
			Iterator<String> it = listeIngredients.keySet().iterator();
			ArrayList<Ingredient> liste = null;
			if(it.hasNext()) {
				do {
					String k = it.next();
					if(k.equals(nomRecette))
						liste = new ArrayList<Ingredient>(listeIngredients.get(k));
				}while(it.hasNext() && liste == null);
				System.out.println(nomRecette + ":");
				for(Ingredient ing : liste)
					System.out.println("\t" + ing.toString());
				this.setChanged();
				this.notifyObservers(liste);
			}
		}
	}
	
}
