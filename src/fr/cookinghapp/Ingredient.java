package fr.cookinghapp;

public class Ingredient {
	
	private String nom, mesure;
	private float quantite;
	
	public Ingredient(String nom, float quantite) {
		this.nom = nom;
		this.mesure = "";
		this.quantite = quantite;
	}
	
	public Ingredient(String nom, float quantite, String mesure) {
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

	public float getQuantite() {
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
