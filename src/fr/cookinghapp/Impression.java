package fr.cookinghapp;

import java.io.IOException;
import java.util.ArrayList;

import fr.cookinghapp.resources.Resources;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.print.Printer.MarginType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Impression {
	
	/*
	 * Exemple d'utilisation:
	 * new Impression(Vue.getAppStage(), "Liste des recettes");
	 */
	
	public Impression(Stage stage, String nomImpression) {
		this(stage.getScene().getRoot(), nomImpression);
	}
	
	public Impression(Node node, String nomImpression) {
        PrinterJob job = PrinterJob.createPrinterJob();
		job.getJobSettings().setJobName(nomImpression);
        PageLayout pl = Printer.getDefaultPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, MarginType.HARDWARE_MINIMUM);
        try {
			VBox vb = new VBox();
			ScrollPane sb = new ScrollPane(vb);
			sb.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        	Scene s = new Scene(sb, 300, 200);
			for(Printer p : Printer.getAllPrinters()) {
	        	Button b = new Button(p.getName());
				b.setBorder(Border.EMPTY);
				b.setAlignment(Pos.CENTER);
				b.setPrefWidth(s.getWidth()-15);
				b.setPrefHeight(Button.USE_COMPUTED_SIZE);
				b.setOnAction((event) -> {
					int i=0;
					boolean imprTrouvee = false;
		        	ArrayList<Printer> printers = new ArrayList<Printer>(Printer.getAllPrinters());
		        	while(i<printers.size() && imprTrouvee == false) {
		        		if(printers.get(i).getName().equals(b.getText())) {
		        			imprTrouvee = true;
		        			job.setPrinter(printers.get(i));
		        		}
		        		i++;
		        	}
		        	Stage stage  = (Stage) b.getScene().getWindow();
		        	stage.close();
					if (job != null)
			        {
			            if (job.printPage(pl, node))
			            	job.endJob();
			        }
				});
	        	vb.getChildren().add(b);
	        }
            Stage stage = new Stage();
            stage.setTitle("Sélection d'imprimante");
            stage.setScene(s);
            stage.setResizable(false);
            stage.getIcons().add(Resources.getImage("images/main_select/pot-chaud.png"));
            stage.show();
        }
        catch (IOException e) {
        	int i=0;
			boolean imprTrouvee = false;
        	ArrayList<Printer> printers = new ArrayList<Printer>(Printer.getAllPrinters());
        	while(i<printers.size() && imprTrouvee == false) {
        		if(printers.get(i).getName().toLowerCase().contains("pdf")) {
        			imprTrouvee = true;
        			job.setPrinter(printers.get(i));
        		}
        	}
            if (job != null)
	        {
	            if (job.printPage(pl, node))
	            	job.endJob();
	        }
        }
    }
}
