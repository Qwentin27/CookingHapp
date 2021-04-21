package fr.cookinghapp;

public class Ingredient {
	
	private String nom, mesure;
	private float quantite;
	
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
		if(mesure.isEmpty()) return formatQuantite() + " " + nom;
		if(nom.charAt(0) == 'h') return formatQuantite() + " " + mesure + " d'" + nom;
		return formatQuantite() + " " + mesure + " de " + nom;
	}
}
