package fr.cookinghapp;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class SListeModele extends Observable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6630493009588856177L;
	
	private TreeMap<String, TreeSet<Ingredient>> listeIngredients;
	
	public void setListeIngredients(TreeMap<String, TreeSet<Ingredient>> listeIngredients) {
		this.listeIngredients = listeIngredients;
	}

	public TreeMap<String, TreeSet<Ingredient>> getListeIngredients() {
		return listeIngredients;
	}
	
	public void clearListeIngredients() {
		listeIngredients.clear();
	}

	@SuppressWarnings("unchecked")
	public SListeModele(String fileName) {
		XMLDecoder decoder = null;
		try {
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			decoder = new XMLDecoder(bis);
			
			Object in = decoder.readObject();
			if(in != null) {
				listeIngredients = (TreeMap<String, TreeSet<Ingredient>>) in;
				System.out.println("Chargement effectuée!");
			}
			else
				listeIngredients = new TreeMap<String, TreeSet<Ingredient>>();
		} catch(IOException e) {
			listeIngredients = new TreeMap<String, TreeSet<Ingredient>>();
			System.out.println("Chargement impossible!");
		} finally {
			if(decoder != null) decoder.close();
		}
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
	
	public void reactListeIngredients(String k2) {
		if(listeIngredients.containsKey(k2)) {
			Iterator<String> it = listeIngredients.keySet().iterator();
			ArrayList<Ingredient> liste = null;
			if(it.hasNext()) {
				do {
					String k = it.next();
					if(k.equals(k2))
						liste = new ArrayList<Ingredient>(listeIngredients.get(k));
				}while(it.hasNext() && liste == null);
				System.out.println(k2 + ":");
				for(Ingredient ing : liste)
					System.out.println("\t" + ing.toString());
				this.setChanged();
				this.notifyObservers(liste);
			}
		}
	}

	public void animationAjoutIngredient() {
		this.setChanged();
		this.notifyObservers(" +");
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
			Vue.getLmodele().setChanged();
			Vue.getLmodele().notifyObservers("");
        });
        new Thread(task).start();
	}
	
}
