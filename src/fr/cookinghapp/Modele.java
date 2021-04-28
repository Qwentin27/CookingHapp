package fr.cookinghapp;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class Modele extends Observable {
	
	private Recette recetteVisionnee;
	
	public Recette getRecetteVisionnee() {
		return recetteVisionnee;
	}

	private boolean sens;
	private List<HBox> liste;

	public Modele() {
		sens = false;
		liste = new ArrayList<HBox>();
	}
	
	public void passageListeVersRecette() {
		Modele m = Vue.getModele();
		m.setChanged();
		m.notifyObservers(recetteVisionnee);
	}

	public void selectionType(TypeRecette type) {
		Modele m = Vue.getModele();
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
				liste.clear();
				for(Recette r : getRecettes(type)) {
					Button b = new Button(r.getNom());
					b.setBorder(Border.EMPTY);
					b.setAlignment(Pos.CENTER_LEFT);
					b.setPrefWidth(310);
					b.setPrefHeight(Button.USE_COMPUTED_SIZE);
					Button image = new Button();
					image.setAlignment(Pos.CENTER_RIGHT);
					image.setPrefWidth(20);
					Button noteEtoile = new Button(r.getNoteToEtoiles());
					noteEtoile.setAlignment(Pos.CENTER_LEFT);
					noteEtoile.setTextAlignment(TextAlignment.LEFT);
					noteEtoile.setPrefWidth(85);
					Button note = new Button(r.getNoteToString());
					note.setAlignment(Pos.CENTER_LEFT);
					note.setTextAlignment(TextAlignment.LEFT);
					note.setPrefWidth(70);
					Button nbVotants = new Button("(" + r.getVotantsToString() + ")");
					nbVotants.setAlignment(Pos.CENTER_LEFT);
					nbVotants.setTextAlignment(TextAlignment.LEFT);
					nbVotants.setPrefWidth(70);
					ImageView img;
					try {
						if(r.hasImage())
							try {
								img = new ImageView(r.getImage());
							}
							catch(IllegalArgumentException e) {
								img = new ImageView(Resources.getImage("images/main_select/no-picture.png"));
							}
						else
							img = new ImageView(Resources.getImage("images/main_select/no-picture.png"));
						img.setPreserveRatio(true);
						img.setFitWidth(15);
						image.setGraphic(img);
					} catch (FileNotFoundException e) {
					}
					EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							recetteVisionnee = r;
							m.setChanged();
							m.notifyObservers(recetteVisionnee);
						}
					};
					image.setOnAction(action);
					b.setOnAction(action);
					noteEtoile.setOnAction(action);
					note.setOnAction(action);
					nbVotants.setOnAction(action);
					HBox hb = new HBox(5, image, b, noteEtoile, note, nbVotants);
					hb.setBorder(new Border(new BorderStroke(Color.rgb(187, 185, 185), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
					hb.setPrefWidth(555);
					hb.setPadding(new Insets(0, 0, 5, 0));
					liste.add(hb);
				}
				return null;
            }
        };
        task.setOnSucceeded(taskFinishEvent -> {
        	Controleur.setAntiDoubleClic(false);
			m.setChanged();
			m.notifyObservers(liste);
        });
        new Thread(task).start();
		m.setChanged();
		m.notifyObservers("Chargement des recettes en cours...");
	}
	
	public void setSens() {
		sens=!sens;
		Collections.reverse(liste);
		this.setChanged();
		this.notifyObservers(liste);
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
