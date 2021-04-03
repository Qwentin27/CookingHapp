package fr.cookinghapp;

public enum TypeRecette {
	Entrée(0),
	Plat(1),
	Dessert(2),
	Boisson(3);
	
	private int num;
	
	private TypeRecette(int num) {
		this.num = num;
	}
	
	public int getValue() {
		return this.num;
	}
}
