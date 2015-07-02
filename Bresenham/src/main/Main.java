package main;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gui.GuiController;
import gui.GuiView;
import gui.grid.GridBuilder;

public class Main extends Application {

  private Stage primaryStage;

  public static void main(String[] args) {
    System.out.println("Hallo Cogra-Welt!");
    System.out.println("Guten Morgen Fabian");
    System.out.println("Hallo Leute!");

    launch(args);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("AntiAliasing und Bresenham");

    GuiView guiView = new GuiView(primaryStage);
    
    GridBuilder gridBuilder = new GridBuilder();
    
    GuiController gc = new GuiController(guiView, gridBuilder);
    gc.init();

  }

}
