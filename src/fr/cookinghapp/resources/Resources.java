package fr.cookinghapp.resources;

import java.net.URL;

public class Resources {
	
	public static URL getResource(String fileName) {
		return Resources.class.getResource(fileName);
	}

}
