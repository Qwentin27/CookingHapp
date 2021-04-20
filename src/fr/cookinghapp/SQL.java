package fr.cookinghapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

public class SQL {
	public static void main(String[] args) {
		List<Recette> recettes = SQL.listeRecettes();
		System.out.println("Entrées:");
		for(Recette r : recettes)
			if(r.getType().equals(TypeRecette.Entrée)) System.out.println(r.toString());
		System.out.println("Plats:");
		for(Recette r : recettes)
			if(r.getType().equals(TypeRecette.Plat)) System.out.println(r.toString());
		System.out.println("Desserts:");
		for(Recette r : recettes)
			if(r.getType().equals(TypeRecette.Dessert)) System.out.println(r.toString());
	}
    
	/*
	 * Fonction d'ajout de recette à la base de données
	 * Paramètres:
	 *   nom : le nom de la recette
	 *   type : le type de recette (Entrée/Plat/Dessert/Boisson)
	 *   ingredients : liste des ingrédients de la recette, utilisez l'un des constructeurs suivant pour créer un ingrédient:
	 *      new Ingredient(nom, quantite, mesure)
	 *      new Ingredient(nom, quantite)
	 *   instructions : liste d'instructions de la recette (liste de String)
	 * Sortie: aucune
	 */
    public static void ajoutRecette(String nom, TypeRecette type, ArrayList<Ingredient> ingredients, ArrayList<String> instructions, String urlImage) {
    	try {
    		Map<String,Object> params = new LinkedHashMap<>();
            params.put("nom", nom);
            params.put("type", type.getValue());
            params.put("ingredients", formatageIngredientsEnString(ingredients));
            params.put("instructions", formatageInstructionsEnString(instructions));
            params.put("url_image", urlImage);
			requete(params);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/*
	 * Fonction d'édition de la note de la recette sur la base de données
	 * Paramètres:
	 *   nom : le nom de la recette
	 *   note : la nouvelle note de la recette
	 *   nombre_votants : le nouveau nombre de votants de la recette
	 * Sortie: aucune
	 */
    public static void editNoteRecette(String nom, float note, int nombre_votants) {
    	try {
    		Map<String,Object> params = new LinkedHashMap<>();
            params.put("nom", nom);
            params.put("note", note);
            params.put("nombre_votants", nombre_votants);
			requete(params);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/*
	 * Fonction permettant de formater les ingrédients d'une chaine de caractères en liste d'ingrédients
	 * Paramètres:
	 *   ingredients : chaine de caratères contenant les ingrédients d'une recette
	 * Sortie: Liste des ingrédients (pas triées)
	 */
    private static ArrayList<Ingredient> formatageIngredientsEnArray(String ingredients) {
    	ArrayList<Ingredient> out = new ArrayList<Ingredient>();
    	for(String ing : ingredients.split("\\|")) {
	    	String[] ingredient = ing.split("\u2807");
	    	try {
		    	if(ingredient.length<3)
		    		out.add(new Ingredient(ingredient[0], ingredient[1]));
		    	else
		    		out.add(new Ingredient(ingredient[0], ingredient[1], ingredient[2]));
		    	}
	    	catch(ArrayIndexOutOfBoundsException e) {}
    	}
    	return out;
    }
    
	/*
	 * Fonction permettant de formater une liste d'ingrédients en chaine de caractères
	 * Paramètres:
	 *   ingredients : Liste d'ingrédients d'une recette
	 * Sortie: Chaine de caratères des ingrédients (triées suivant l'insertion dans la liste)
	 */
    private static String formatageIngredientsEnString(ArrayList<Ingredient> ingredients) {
    	String out = "";
    	int taille = ingredients.size()-1;
    	for(int i=0; i<=taille; i++) {
    		out = out + ingredients.get(i).getNom() + "\u2807" + ingredients.get(i).getQuantite() + "\u2807" + ingredients.get(i).getMesure() + (i<taille?"\\|":"");
    	}
    	return out;
    }
    
	/*
	 * Fonction permettant de formater les instructions d'une chaine de caractères en liste d'instructions
	 * Paramètres:
	 *   instructions : chaine de caratères contenant les instructions d'une recette
	 * Sortie: Liste des instructions (triées suivant l'insertion dans la liste)
	 */
    private static ArrayList<String> formatageInstructionsEnArray(String instructions) {
    	ArrayList<String> out = new ArrayList<String>();
    	for(String ins : instructions.split("\\|")) {
    		out.add(ins.replaceAll("<br />", "\n"));
    	}
    	return out;
    }
    
	/*
	 * Fonction permettant de formater une liste d'instructions en chaine de caractères
	 * Paramètres:
	 *   instructions : Liste de chaine de caratères contenant les instructions d'une recette
	 * Sortie: Chaine de caratères des instructions (triées suivant l'insertion dans la liste)
	 */
    private static String formatageInstructionsEnString(ArrayList<String> instructions) {
    	String out = "";
    	int taille = instructions.size()-1;
    	for(int i=0; i<=taille; i++) {
    		out = out + instructions.get(i) + (i<taille?"|":"");
    	}
    	return out;
    }
    
	/*
	 * Fonction permettant d'envoyer une requete SQL via le script php edit_recettes.php
	 * Fonctionnement:
	 *    On encode les paramètres entrées au format d'une URL
	 *    On se connecte à l'URL (ici: https://cookinghapp.crystalium.eu/edit_recettes.php) et on écrit les paramètres via la méthode POST
	 *    Facultatif: On lit le résultat retourné et on l'affiche dans la console
	 * Paramètres:
	 *   params : Liste de couple nom du paramètre (clé), objet du paramètre (valeur)
	 * Sortie: aucune
	 */
    public static void requete(Map<String,Object> params) throws MalformedURLException, ProtocolException, IOException {
    	URL url = new URL("https://cookinghapp.crystalium.eu/edit_recettes.php");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;)
            System.out.print((char)c);
        System.out.println();
    }
    
	/*
	 * Fonction permettant de lire les recettes de la via le script php liste_recettes.php
	 * Fonctionnement:
	 *    On se connecte à l'URL (ici: https://cookinghapp.crystalium.eu/edit_recettes.php)
	 *    On lit chaque ligne et on formate le résultat suivant le retour à la ligne <br>
	 * Paramètres: aucun
	 * Sortie: Liste des recettes triées
	 */
    public static List<Recette> listeRecettes() {
    	HttpURLConnection con = null;
    	TreeSet<Recette> content = new TreeSet<Recette>();
    	try {
    		URL url = new URL("https://cookinghapp.crystalium.eu/liste_recettes.php");
    		con = (HttpURLConnection) url.openConnection();

    		try (Scanner br = new Scanner(new InputStreamReader(con.getInputStream())).useDelimiter("\\s*<br>\\s*")) {
    			while (br.hasNext()) {
    				String recep = br.next();
    				if(!recep.isEmpty()) {
    					String[] r = recep.trim().split("\\\u2016");
    					Recette recette = null;
    					try {
    						recette = new Recette(r[0].replaceAll("<br />", ""), Integer.parseInt(r[1]), r.length<5?(new ArrayList<Ingredient>()):formatageIngredientsEnArray(r[4]), r.length<6?(new ArrayList<String>()):formatageInstructionsEnArray(r[5]), Float.parseFloat(r[2]), Integer.parseInt(r[3]), r.length<7?(""):r[6]);
    					} catch (NumberFormatException e) {
    						e.printStackTrace();
    					} catch (MauvaisTypeException e) {
    						e.printStackTrace();
    					}
    					if(recette != null)
    						content.add(recette);
    					else
    						System.out.println("La recette " + r[0] + " n'a pas été chargée!");
    				}
    			}
    			br.close();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
		}
        
        return new ArrayList<Recette>(content);
    }
}
