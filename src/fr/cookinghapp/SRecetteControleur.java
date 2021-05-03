package fr.cookinghapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.TreeSet;

import fr.cookinghapp.resources.Resources;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SRecetteControleur extends Observable{
	
	@FXML
	public Button button_list_recette;
	
	@FXML
	public Button button_recette_recette;
	
	@FXML
	public ToggleButton note_plus;
	
	@FXML
	public ToggleButton note_moins;
	
	@FXML
	public ToggleButton note_valider;
	
	@FXML
	public Button ajouter_liste;
	
	public void clic_liste_recette() throws IOException{
		
		// Changement de scene lors du clic -> vers Liste
		
		Parent page = FXMLLoader.load(Resources.getResource("fxml/Scene_Liste.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
		Vue.getLmodele().ouvertureListeIngredients();
	}
	
	public void clic_note_plus() { // Incrémentation de la note associée à la recette
		
		int note = Vue.getSmodele().getMain_note();
		if (note < 5) {
			Vue.getSmodele().setMain_note(note+1);
		}
		
		// concaténer avec un "/5"

		
	}
	
	public void clic_note_moins() { // Décrémentation de la note associée à la recette
		
		int note = Vue.getSmodele().getMain_note();
		if (note > 0) {
			Vue.getSmodele().setMain_note(note-1);
		}
		
		// concaténer avec un "/5"

	}
	
	public void clic_valider() { // Validation de la note
		
		//TODO envoyer à l'SQL
		
		Vue.getModele().getRecetteVisionnee().addNote(Vue.getSmodele().getMain_note());
		Vue.getSmodele().reactuNote(Vue.getModele().getRecetteVisionnee());
		
	}
	
	public void clic_recette_recette() throws IOException{ // Affichage de la recette associée
		
		Vue.getSmodele().setRecette(Vue.getModele().getRecetteVisionnee());
		
	}
	
	public void retour_menu_principal() throws IOException {
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
		Vue.getModele().chargementRecettes();
	}
	
	public void clic_ajouter_liste() throws IOException{ // Ajout des ingrédients à la liste de course
		
		// si texte == "ajouter à la liste"
		// else : texte == "ingrédients"

		if(ajouter_liste.getText().equals("Ajouter à la liste")) {
			
			TreeSet<Ingredient> ingredientsSelectionnes = new TreeSet<Ingredient>();
			Recette r = Vue.getModele().getRecetteVisionnee();
			ArrayList<Ingredient> ingredients = r.getIngredients();
			Scene scene = Vue.getAppStage().getScene();
			VBox liste = (VBox) scene.lookup("#box_ingredients");
			int i = 0;
			boolean all = true;
			for (Node node : liste.getChildren()) {
				if(node instanceof HBox) {
					ObservableList<Node> ing = ((HBox)node).getChildren();
					if(ing.get(0) instanceof CheckBox) {
						if(((CheckBox) ing.get(0)).isSelected()) {
							all = false;
							ingredientsSelectionnes.add(ingredients.get(i));
						}
					}
				}
				i++;
			}
			if(all)
				Vue.getLmodele().ajoutIngredient(r.getNom(), new TreeSet<Ingredient>(ingredients));
			else
				Vue.getLmodele().ajoutIngredient(r.getNom(), ingredientsSelectionnes);
		}
		else if (ajouter_liste.getText().equals("Ingrédients")){
			
			Vue.getModele().passageListeVersRecette();
		}
		
		// le bouton se transforme en bouton ingrédients lorsque la recette est affichée
		// faire un test : si text == ingrédients --> telle fonction
		// 				   sinon --> fonction différente
	}

}
