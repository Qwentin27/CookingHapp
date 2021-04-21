package fr.cookinghapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import fr.cookinghapp.resources.Resources;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Modele extends Observable {
	
	private boolean sens;
	private List<Button> liste;

	public Modele() {
		sens = false;
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
							try {
								img = new ImageView(r.getImage());
							}
							catch(IllegalArgumentException e) {
								img = new ImageView(Resources.getImage("images/main_select/no-picture.png"));
							}
						else
							img = new ImageView(Resources.getImage("images/main_select/no-picture.png"));
						img.setPreserveRatio(true);
						img.setFitHeight(15);
						img.setFitWidth(15);
						b.setGraphic(img);
					} catch (FileNotFoundException e) {
					}
					b.setOnAction((e) -> {
						try {
							Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_ingredientsRecettes.fxml"));
							Vue.getAppStage().setScene(new Scene(page));
							Scene scene = Vue.getAppStage().getScene();
							Label nom = (Label) scene.lookup("#nom_recette");
							nom.setText(r.getNom());
							VBox liste = (VBox) scene.lookup("#box_ingredients");
							liste.setAlignment(Pos.TOP_LEFT); //TODO A rajouter dans le fichier Scene_ingredientsRecettes.fxml
							liste.getChildren().clear(); //Facultatif, mais permet d'être sûr que la liste d'ingrédients est vide au départ
							for(Ingredient ing : r.getIngredients()) {
								CheckBox cb = new CheckBox(ing.toString());
								cb.setTextAlignment(TextAlignment.LEFT);
								liste.getChildren().add(cb);
							}
							Label noteTexte = (Label) scene.lookup("#note_texte");
							noteTexte.setText(r.formatNote());
							if(r.hasImage()) {
								ImageView img = (ImageView) scene.lookup("#image_recette");
								img.setImage(new Image(r.getImage()));
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
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
		out+=(note==Math.round(note)?"   ":"")+"["+Vue.getDf().format(note)+"]";
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
