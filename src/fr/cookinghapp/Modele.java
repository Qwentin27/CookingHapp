package fr.cookinghapp;

import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;

public class Modele extends Observable {
	
	private DecimalFormat df;
	private boolean sens;
	private List<Button> liste;

	public Modele() {
		sens = false;
		df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.CEILING);
		liste = new ArrayList<Button>();
	}

	public void selectionType(TypeRecette type) {
		Modele m = Vue.getModele();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
				liste.clear();
				for(Recette r : getRecettes(type)) {
					Button b = new Button(getNoteToString(r.getNote()) + " (" + r.getNombre_votants() + ") " + r.getNom());
					b.setBorder(Border.EMPTY);
					b.setAlignment(Pos.CENTER_LEFT);
					b.setPrefWidth(560);
					b.setPrefHeight(Button.USE_COMPUTED_SIZE);
					try {
					ImageView img;
					if(r.hasImage())
						img = new ImageView(r.getImage());
					else
						img = new ImageView(Resources.getImage("images/main_select/no-picture.png"));
					img.setPreserveRatio(true);
					img.setFitHeight(15);
					img.setFitWidth(15);
					b.setGraphic(img);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					liste.add(b);
				}
				return null;
            }
        };
        task.setOnSucceeded(taskFinishEvent -> {
			m.setChanged();
			m.notifyObservers(liste);
        });
        new Thread(task).start();
	}
	
	public void setSens() {
		sens=!sens;
		Collections.reverse(liste);
		this.setChanged();
		this.notifyObservers(liste);
	}
	
	private String getNoteToString(float note) {
		String out = "";
		for(int i=0; i<5; i++) {
			if(note >= (1+i)) out += "\u2605";
			else out += "\u2606";
		}
		out+=(note==Math.round(note)?"   ":"")+"["+df.format(note)+"]";
		return out;
	}

	private List<Recette> getRecettes(TypeRecette type) {
		List<Recette> out = new ArrayList<Recette>();
		for(Recette r : SQL.listeRecettes())
			if(r.getType().equals(type)) out.add(r);
		if(sens)
			Collections.sort(out, Collections.reverseOrder());
		return out;
	}
}
