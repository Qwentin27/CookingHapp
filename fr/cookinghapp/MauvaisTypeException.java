package fr.cookinghapp;

@SuppressWarnings("serial")
public class MauvaisTypeException extends Exception {

	public MauvaisTypeException(int idType) {
		super("L'id " + idType + " n'existe pas.");
	}
}
