package fr.cookinghapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SQL {
	public static void main(String[] args) {
    	editNoteRecette("Salade de pommes de terres", 4.5F, 3);
    	for(Recette r : listeRecettes())
    		System.out.println(r.toString());
    	editNoteRecette("Salade de pommes de terres", 3.5F, 2);
	}
    
    public static void ajoutRecette(String nom, TypeRecette type, ArrayList<Ingredient> ingredients, ArrayList<String> instructions) {
    	try {
    		Map<String,Object> params = new LinkedHashMap<>();
            params.put("nom", nom);
            params.put("type", type.getValue());
            params.put("ingredients", formatageIngredientsEnString(ingredients));
            params.put("instructions", formatageInstructionsEnString(instructions));
			requete(params);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
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
    
    public static ArrayList<Recette> listeRecettes() {
    	ArrayList<Recette> out = new ArrayList<Recette>();
    	for(String recetteStr : listeRecettesSQL()) {
    		String[] r = recetteStr.split("\\\u2016");
    		Recette recette = null;
    		try {
				recette = new Recette(r[0].replaceAll("<br />", ""), Integer.parseInt(r[1]), r.length<5?(new ArrayList<Ingredient>()):formatageIngredientsEnArray(r[4]), r.length<6?(new ArrayList<String>()):formatageInstructionsEnArray(r[5]), Float.parseFloat(r[2]), Integer.parseInt(r[3]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (MauvaisTypeException e) {
				e.printStackTrace();
			}
    		if(recette != null)
    			out.add(recette);
    		else
    			System.out.println("La recette " + r[0] + " n'a pas été chargée!");
    	}
		return out;
    }
    
    private static ArrayList<Ingredient> formatageIngredientsEnArray(String ingredients) {
    	ArrayList<Ingredient> out = new ArrayList<Ingredient>();
    	for(String ing : ingredients.split("\\|")) {
	    	String[] ingredient = ing.split("\u2807");
	    	if(ingredient.length<3)
	    		out.add(new Ingredient(ingredient[0], Integer.parseInt(ingredient[1])));
	    	else
	    		out.add(new Ingredient(ingredient[0], Integer.parseInt(ingredient[1]), ingredient[2]));
    	}
    	return out;
    }
    
    private static String formatageIngredientsEnString(ArrayList<Ingredient> ingredients) {
    	String out = "";
    	int taille = ingredients.size()-1;
    	for(int i=0; i<=taille; i++) {
    		out = out + ingredients.get(i).getNom() + "\u2807" + ingredients.get(i).getQuantite() + "\u2807" + ingredients.get(i).getMesure() + (i<taille?"\\|":"");
    	}
    	return out;
    }
    
    private static ArrayList<String> formatageInstructionsEnArray(String instructions) {
    	ArrayList<String> out = new ArrayList<String>();
    	for(String ins : instructions.split("\\|")) {
    		out.add(ins.replaceAll("<br />", "\n"));
    	}
    	return out;
    }
    
    private static String formatageInstructionsEnString(ArrayList<String> instructions) {
    	String out = "";
    	int taille = instructions.size()-1;
    	for(int i=0; i<=taille; i++) {
    		out = out + instructions.get(i) + (i<taille?"|":"");
    	}
    	return out;
    }

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

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;)
            System.out.print((char)c);
    }
    
    private static ArrayList<String> listeRecettesSQL() {
        HttpURLConnection con = null;
        ArrayList<String> content = new ArrayList<String>();
        try {
            URL url = new URL("https://cookinghapp.crystalium.eu/liste_recettes.php");
            con = (HttpURLConnection) url.openConnection();

            String out = "";

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                	out += line;
                }
                br.close();
                if(!out.isEmpty()) {
                	String[] liste = out.split("<br>"); //.split("<div>")[1].split("</div>")[0]
                	for(int i=0; i<liste.length-1; i++) //Dernière ligne vide donc il ne faut pas la rajouter
                		content.add(liste[i].trim());
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            con.disconnect();
        }
        
        return content;
        //String[] liste = new String[content.size()];
        //return content.toArray(liste);
    }
}
