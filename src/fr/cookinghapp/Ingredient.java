package fr.cookinghapp;

import java.io.Serializable;

public class Ingredient implements Comparable<Ingredient>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2139027795620988106L;
	private String nom, mesure;
	private float quantite;
	
	public Ingredient() {
		this.nom = "";
		this.mesure = "";
		this.quantite = 0;
	}
	
	public Ingredient(String nom, float quantite) {
		this.nom = nom;
		this.mesure = "";
		this.quantite = quantite;
	}
	
	public Ingredient(String nom, String quantiteFractionnaire) {
		this.nom = nom;
		this.mesure = "";
		this.quantite = parseFloat(quantiteFractionnaire);
	}
	
	public Ingredient(String nom, float quantite, String mesure) {
		this.nom = nom;
		this.mesure = mesure;
		this.quantite = quantite;
	}
	
	public Ingredient(String nom, String quantiteFractionnaire, String mesure) {
		this.nom = nom;
		this.mesure = mesure;
		this.quantite = parseFloat(quantiteFractionnaire);
	}
	
    private static float parseFloat(String ratio) {
        if (ratio.contains("/")) {
            String[] rat = ratio.split("/");
            return Float.parseFloat(rat[0]) / Float.parseFloat(rat[1]);
        } else {
            return Float.parseFloat(ratio);
        }
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public float getQuantite() {
		return quantite;
	}

	public String formatQuantite() {
		if(quantite - Math.round(quantite) > 0) return Vue.getDf().format(quantite);
		return "" + Math.round(quantite);
	}

	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}
	
	public String getMesure() {
		return mesure;
	}

	public void setMesure(String mesure) {
		this.mesure = mesure;
	}
	
	@Override
	public String toString() {
		if(quantite == 0) return nom;
		if(mesure.isEmpty()) return formatQuantite() + " " + nom;
		if(nom.charAt(0) == 'h') return formatQuantite() + " " + mesure + " d'" + nom;
		return formatQuantite() + " " + mesure + " de " + nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Ingredient) {
			Ingredient ing = (Ingredient) obj;
			return ing.getNom().equals(nom) && ing.getMesure().equals(mesure);
		}
		return false;
	}

	@Override
	public int compareTo(Ingredient ing) {
		return this.nom.compareTo(ing.nom);
	}
}
