package fr.cookinghapp;

public class Ingredient {
	
	private String nom, mesure;
	private int quantite;
	
	public Ingredient(String nom, int quantite) {
		this.nom = nom;
		this.mesure = "";
		this.quantite = quantite;
	}
	
	public Ingredient(String nom, int quantite, String mesure) {
		this.nom = nom;
		this.mesure = mesure;
		this.quantite = quantite;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
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
		if(mesure.isEmpty()) return quantite + " " + nom;
		if(nom.charAt(0) == 'h') return quantite + " " + mesure + " d'" + nom;
		return quantite + " " + mesure + " de " + nom;
	}
}
