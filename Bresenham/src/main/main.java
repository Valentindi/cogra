package main;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gui.GuiView;

public class main extends Application {

	private Stage primaryStage;
	
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("Hallo Cogra-Welt!");
    System.out.println("Guten Morgen Fabian");
    System.out.println("Hallo Leute!");
    
    launch(args);

  }



@Override
public void start(Stage primaryStage) throws Exception {

	this.primaryStage = primaryStage;
	//this.primaryStage.setTitle("AntiAliasing und Bresenham");
	
	GuiView guiView = new GuiView();
	guiView.initGuiView(primaryStage);
}

}
