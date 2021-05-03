package fr.cookinghapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.cookinghapp.resources.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SAjoutRecettesControleur {
	
	@FXML
	public Button button_menu;
	
	@FXML
	public Button button_rajouter_ing;
	
	@FXML
	public Button button_ajouter_inst;
	
	@FXML
	public Button button_ajouter_recette;
	
	@FXML
	public MenuButton type_ajout_recette;
	
	@FXML
	public MenuItem ajout_recette_select_entrees;
	
	@FXML
	public MenuItem ajout_recette_select_plats;
	
	@FXML
	public MenuItem ajout_recette_select_desserts;
	
	@FXML 
	public TextArea instruction_ajout_recette;
	
	@FXML
	public TextArea ingredient_ajout_recette;
	
	@FXML
	public TextArea	nb_personne_ajout_recette;
	
	@FXML
	public TextArea retour_ajout_recette;
	
	@FXML
	public TextArea instruction_ajout_recette1;
	
	@FXML
	public Button button_ajouter_inst1;

	private void setSelectionImage(String image) {
		try {
			ImageView img = new ImageView(new Image(new FileInputStream(Resources.getResource("images/main_select/"+image).getFile())));
	        img.setFitHeight(24);
	        img.setFitWidth(24);
	        type_ajout_recette.setGraphic(img);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
	}

	public void ajout_recette_entrees() {
		setSelectionImage("entree.png");
		type_ajout_recette.setText(ajout_recette_select_entrees.getText());
		Vue.getAjmodele().setType(TypeRecette.Entrée);
	}
	public void ajout_recette_plats() {
		setSelectionImage("plat.png");
		type_ajout_recette.setText(ajout_recette_select_plats.getText());
		Vue.getAjmodele().setType(TypeRecette.Plat);
	}
	public void ajout_recette_desserts() {
		setSelectionImage("dessert.png");
		type_ajout_recette.setText(ajout_recette_select_desserts.getText());
		Vue.getAjmodele().setType(TypeRecette.Dessert);
	}
	
	public void clic_menu() throws IOException{
		Parent page = FXMLLoader.load(Resources.getResource("fxml/AppliCookingHapp.fxml"));
		Vue.getAppStage().setScene(new Scene(page));
		Vue.getModele().chargementRecettes();
	}
	
	public void clic_ajout_ing() {
		String ing = ingredient_ajout_recette.getText();
		//System.out.println(ing);
		//Vue.getAjmodele().ajoutIngredient(ingredient);
		if(ing.isEmpty()) Vue.getAjmodele().retourMessage("Aucun ingrédient");
		else {
			//13 cL de café
			String[] ingList = ing.split(" ");
			float quantite = 0;
			try {
				quantite = Float.parseFloat(ingList[0]);
			}
			catch (NumberFormatException e) {
			}
			String mesure = "";
			boolean separateur = false;
			String nom = "";
			for(int i=1; i<ingList.length; i++) {
				if(separateur || (quantite <= 0)) {
					nom = nom + ingList[i] + ((i==ingList.length-1)?"":" ");
				}
				else {
					if(ingList[i].substring(0, 1).equals("d'") || ingList[i].equals("de"))
						separateur = true;
					else
						mesure = mesure + ingList[i] + ((i==ingList.length-1)?"":" ");
				}
			}
			if((separateur == false) || (quantite <= 0)) {
				Vue.getAjmodele().ajoutIngredient(new Ingredient(nom, 0));
				Vue.getAjmodele().retourMessage("Ingrédient ajouté");
			}
			else {
				Vue.getAjmodele().ajoutIngredient(new Ingredient(nom, quantite, mesure));
				Vue.getAjmodele().retourMessage("Ingrédient ajouté");
			}
		}
	}
	
	public void clic_ajout_inst(){
		
		String inst = instruction_ajout_recette.getText();
		
		if (inst.isEmpty())
			Vue.getAjmodele().retourMessage("Saisie d'instructions incorrecte");
		else {
			Vue.getAjmodele().ajoutEtape(inst);
			Vue.getAjmodele().retourMessage("Etape ajoutée");
		}

	}
	
	public void valider_ajout_recette() {
		
		// Y ajouter la validation du nombre de personnes
		
		int nb = -1;
		String nom = retour_ajout_recette.getText();
		try {
			nb = Integer.parseInt(nb_personne_ajout_recette.getText());
		}
		catch (NumberFormatException e) {}
		
		if (nb <= 0)
			Vue.getAjmodele().retourMessage("Nombre incorrect");
		else if (nom.isEmpty())
			Vue.getAjmodele().retourMessage("Saisie du nom incorrecte");
		else if (Vue.getAjmodele().typePasSet())
			Vue.getAjmodele().retourMessage("Aucun type sélectionné");
		else if (Vue.getAjmodele().ingredientsEstVide())
			Vue.getAjmodele().retourMessage("Aucun ingrédients ajoutés");
		else if (Vue.getAjmodele().etapesEstVide())
			Vue.getAjmodele().retourMessage("Aucune étapes ajoutés");
		else
			Vue.getAjmodele().ajouterRecette(nom, nb);
	}
	
	public void clic_ajout_url(){
		
		String url = retour_ajout_recette.getText();
		
		if (url.isEmpty())
			Vue.getAjmodele().retourMessage("Saisie de l'url incorrecte");
		else
			Vue.getAjmodele().ajoutUrl(url);
	}
}
