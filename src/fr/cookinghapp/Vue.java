package fr.cookinghapp;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fr.cookinghapp.resources.Resources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Vue extends Application implements Observer {

	private static Modele modele;
	private static SRecetteModele smodele;
	private static SListeModele lmodele;
	private static SAjoutRecettesModele ajmodele;
	private static Stage appStage;
	private static DecimalFormat df;

	public static DecimalFormat getDf() {
		return df;
	}

	public static Modele getModele() {
		return modele;
	}

	public static SRecetteModele getSmodele() {
		return smodele;
	}
	
	public static SAjoutRecettesModele getAjmodele() {
		return ajmodele;
	}

	public static SListeModele getLmodele() {
		return lmodele;
	}

	public static Stage getAppStage() {
		return appStage;
	}

	public static void main(String[] args) {
		df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		launch(args);
	}
	
	//affiche un menu simple		
	public void start(Stage primaryStage) throws Exception {
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		primaryStage.setScene(new Scene(page));
		primaryStage.setResizable(false);
		primaryStage.setTitle("CookingHapp");
		try {
			primaryStage.getIcons().add(Resources.getImage("images/main_select/pot-chaud.png"));
		}
		catch (FileNotFoundException e) {}
		primaryStage.show();
		appStage = primaryStage;
		modele = new Modele();
		modele.addObserver(this);
		smodele = new SRecetteModele();
		smodele.addObserver(this);
		lmodele = new SListeModele();
		lmodele.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Scene scene = Vue.getAppStage().getScene();
		if(arg instanceof ArrayList) {
			if (o instanceof SListeModele) {
				if(!((ArrayList<?>)arg).isEmpty()) {
					if(((ArrayList<?>)arg).get(0) instanceof Button) {
						VBox liste = (VBox) scene.lookup("#liste_courses_recettes");
						liste.getChildren().clear();
						for (Object bouton : (ArrayList<?>)arg) {
							Button b = (Button) bouton;
							b.setPrefWidth(liste.getPrefWidth());
							liste.getChildren().add(b);
						}
					}
					else {
						VBox liste = (VBox) scene.lookup("#liste_courses_ingredients");
						liste.getChildren().clear();
						for (Object ingredient : (ArrayList<?>)arg) {
							Ingredient ing = (Ingredient) ingredient;
							Text t = new Text(ing.toString() + "\n");
							t.setWrappingWidth(liste.getWidth()-12);
							t.setFocusTraversable(false);
							t.getStyleClass().add("box_ingredients");
							liste.getChildren().add(t);
						}
					}
				}
			}
			
			else {
				VBox liste = (VBox) scene.lookup("#recettes_vbox");
				if(liste != null) {
					liste.getChildren().clear();
					for(Object hb : (ArrayList<?>)arg)
						liste.getChildren().add((HBox)hb);
				}
			}	
			
		}
		else if(arg instanceof Integer) {
			Label noteTexte = (Label) scene.lookup("#note_texte");
			noteTexte.setText(arg+""); // concaténer avec un "/5"
		}
		else if(arg instanceof String) {
			VBox liste = (VBox) scene.lookup("#recettes_vbox");
			if(liste != null) {
				liste.getChildren().clear();
				Text t = new Text((String) arg);
				t.setFocusTraversable(false);
				t.getStyleClass().add("grille_four");
				liste.getChildren().add(t);
			}
		}
		else if(arg instanceof Recette) {
			Recette r = (Recette) arg;
			if(o instanceof SRecetteModele) {
				ScrollPane sp = (ScrollPane) scene.lookup("#scroll_ingredients");
				sp.setHbarPolicy(ScrollBarPolicy.NEVER);
				sp.setPrefHeight(ScrollPane.USE_COMPUTED_SIZE);
				Label nom = (Label) scene.lookup("#titre");
				nom.setText("Etapes :");
				Button ajouterListe = (Button) scene.lookup("#ajouter_liste");
				ajouterListe.setText("Ingrédients");
				VBox liste = (VBox) scene.lookup("#box_ingredients");
				liste.setAlignment(Pos.TOP_LEFT);
				liste.getChildren().clear();
				for(String e : r.getEtapes()) {
					Text t = new Text(e + "\n");
					t.setWrappingWidth(liste.getWidth()-12);
					t.setFocusTraversable(false);
					t.getStyleClass().add("box_ingredients");
					liste.getChildren().add(t);
				}
				
			}
			
			else {
				try {
					Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_ingredientsRecettes.fxml"));
					Vue.getAppStage().setScene(new Scene(page));
					scene = Vue.getAppStage().getScene();
					Label nom = (Label) scene.lookup("#nom_recette");
					nom.setText(r.getNom());
					VBox liste = (VBox) scene.lookup("#box_ingredients");
					liste.setAlignment(Pos.TOP_LEFT); //TODO A rajouter dans le fichier Scene_ingredientsRecettes.fxml
					liste.getChildren().clear(); //Facultatif, mais permet d'être sûr que la liste d'ingrédients est vide au départ
					ScrollPane sp = (ScrollPane) scene.lookup("#scroll_ingredients");
					sp.setHbarPolicy(ScrollBarPolicy.NEVER);
					sp.setPrefHeight(ScrollPane.USE_COMPUTED_SIZE);
					for(Ingredient ing : r.getIngredients()) {
						Text t = new Text(ing.toString() + "\n");
						t.setTextAlignment(TextAlignment.LEFT);
						t.setWrappingWidth(liste.getWidth()-42);
						t.getStyleClass().add("box_ingredients");
						CheckBox cb = new CheckBox();
						cb.setAlignment(Pos.CENTER_RIGHT);
						cb.getStyleClass().add("box_ingredients");
						HBox hb = new HBox(cb, t);
						liste.getChildren().add(hb);
					}
					Label noteTexte = (Label) scene.lookup("#note_texte");
					noteTexte.setText(String.valueOf(Vue.getSmodele().getMain_note())); // concaténer avec un "/5"
					Label noteGlobale = (Label) scene.lookup("#noteGlobale");
					noteGlobale.setText("Note : " + r.getNoteToString() + " (" + r.getNombre_votants() + ")");
					if(r.hasImage()) {
						ImageView img = (ImageView) scene.lookup("#image_recette");
						img.setImage(new Image(r.getImage()));
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		
			
		}
		else if(arg instanceof String[]) {
			String[] n = (String []) arg;
			Label noteGlobale = (Label) scene.lookup("#noteGlobale");
			noteGlobale.setText("Note : " + n[0] + " (" + n[1] + ")");
		}
		
	}

}
