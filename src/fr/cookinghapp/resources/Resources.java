package fr.cookinghapp.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import javafx.scene.image.Image;

public class Resources {
	
	public static URL getResource(String fileName) {
		return Resources.class.getResource(fileName);
	}
	
	public static Image getImage(String fileName) throws FileNotFoundException {
		return new Image(new FileInputStream(Resources.class.getResource(fileName).getFile()));
	}

}
