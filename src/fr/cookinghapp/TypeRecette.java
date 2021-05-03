package fr.cookinghapp;

public enum TypeRecette {
	Aucun(-1, ""),
	Entrée(0, "entree.png"),
	Plat(1, "plat.png"),
	Dessert(2, "dessert.png"),
	Boisson(3, "boisson.png");
	
	private int num;
	private String nomImage;
	
	private TypeRecette(int num, String nomImage) {
		this.num = num;
		this.nomImage = nomImage;
	}
	
	public int getValue() {
		return this.num;
	}
	
	public String getImage() {
		return this.nomImage;
	}
}
