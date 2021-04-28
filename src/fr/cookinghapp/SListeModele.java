package fr.cookinghapp;

import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeMap;
import java.util.TreeSet;

public class SListeModele extends Observable{
	
	TreeMap<String, TreeSet<Ingredient>> listeIngredients;
	
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
}
