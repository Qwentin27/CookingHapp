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
	public TextArea url_ajout_recette;
	
	@FXML
	public TextArea retour_ajout_recette;
	
	@FXML
	public Button button_ajouter_url;

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
		//Vue.getAjmodele().ajoutIngredient(ingredient);
		if(ing.isEmpty()) Vue.getAjmodele().retourMessage("Aucun ingrédient");
		else {
			int nIngAjoute = 0;
			String[] ingredients = ing.split("\n");
			for(int j=0; j<ingredients.length; j++) {
				//System.out.println(ingredients[j]);
				if(!ingredients[j].isEmpty()) {
					String[] ingList = ingredients[j].split(" ");
					float quantite = 0;
					int i = 1;
					try {
						quantite = Float.parseFloat(ingList[0]);
					}
					catch (NumberFormatException e) {
						i = 0;
					}
					String mesure = "";
					boolean separateur = false;
					String nom = "";
					for(; i<ingList.length; i++) {
						if(separateur || (quantite <= 0.0))
							nom = nom + ingList[i] + ((i==ingList.length-1)?"":" ");
						else if(ingList[i].length()>=2 && ingList[i].substring(0, 2).equals("d'")) {
							separateur = true;
							nom = nom + ingList[i].substring(2, ingList[i].length());
						}
						else if(ingList[i].equals("de"))
							separateur = true;
						else
							mesure = mesure + ingList[i] + ((i==ingList.length-1)?"":" ");
					}
					if(!mesure.isEmpty())
						while(mesure.charAt(mesure.length()-1) == ' ') mesure = mesure.substring(0, mesure.length()-1);
					if(!nom.isEmpty())
						while(nom.charAt(nom.length()-1) == ' ') nom = nom.substring(0, nom.length()-1);
					if(!separateur) {
						nom = mesure;
						mesure = "";
					}
					if(quantite <= 0) {
						quantite = 0;
						if(mesure.isEmpty() && nom.isEmpty())
							nom = ingredients[j];
					}
					Ingredient in = new Ingredient(nom, quantite, mesure);
					Vue.getAjmodele().ajoutIngredient(in);
					nIngAjoute++;
				}
			}
			if(nIngAjoute == 0)
				Vue.getAjmodele().retourMessage("Aucun ingrédient");
			else {
				ingredient_ajout_recette.setText("");
				if(nIngAjoute == 1)
					Vue.getAjmodele().retourMessage("Ingrédient ajouté");
				else
					Vue.getAjmodele().retourMessage("Ingrédients ajoutés");
			}
		}
	}
	
	public void clic_ajout_inst(){
		
		String inst = instruction_ajout_recette.getText();
		
		if (inst.isEmpty())
			Vue.getAjmodele().retourMessage("Saisie d'instructions incorrecte");
		else {
			String[] instructions = inst.split(";");
			for(int i=0; i<instructions.length; i++) {
				Vue.getAjmodele().ajoutEtape(instructions[i]);
			}
			instruction_ajout_recette.setText("");
			if(instructions.length == 1)
				Vue.getAjmodele().retourMessage("Etape ajoutée");
			else
				Vue.getAjmodele().retourMessage("Etapes ajoutées");
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
		else {
			retour_ajout_recette.setText("");
			Vue.getAjmodele().ajouterRecette(nom, nb);
		}
	}
	
	public void clic_ajout_url(){
		
		String url = url_ajout_recette.getText();
		
		if (url.isEmpty())
			Vue.getAjmodele().retourMessage("Saisie de l'url incorrecte");
		else {
			ImageView img = null;
			try {
				img = new ImageView(url);
			}catch(IllegalArgumentException|NullPointerException e ){
				Vue.getAjmodele().retourMessage("Saisie de l'url incorrecte");
			}
			if (img != null) {
				if(img.getImage().isError())
					Vue.getAjmodele().retourMessage("Saisie de l'url incorrecte");
				else {
					Vue.getAjmodele().ajoutUrl(url);
					url_ajout_recette.setText("");
				}
			}

			
		}

	}
}
