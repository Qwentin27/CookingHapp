package fr.cookinghapp;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Vue extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// définir la troupe d'acteurs 
		Group root = new Group();
		Group root1 = new Group();
		// définir la scène (largeur, hauteur, fond)
		Scene scene = new Scene(root, 800, 600, Color.WHITE);
		Scene scene1 = new Scene(root1, 200, 200, Color.TRANSPARENT);
		primaryStage.setTitle("Title test");
		primaryStage.setScene(scene);
		//primaryStage.setScene(scene1);
		
		//définir un rectangle a peu près centré SUR LA SCENE
		
		Rectangle rectangle = new Rectangle(400, 300, 40, 30);
		rectangle.setFill(Color.RED);
		root.getChildren().add(rectangle);
		
		
		//définir un rectangle a peu près centré SUR LA SCENE1
		
		Rectangle rectangle1 = new Rectangle(0, 0, 50, 50);
		rectangle.setFill(Color.GREEN);
		root1.getChildren().add(rectangle1);
		
		//afficher le theatre
		
		//primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitHint("");
		primaryStage.show();
		
		
	}

}
