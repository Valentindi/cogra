package gui;

import java.util.Collection;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuiView {
	
	private AnchorPane rootLayout;
    private Stage primaryStage;

	public void initGuiView(Stage primaryStage) {
	  this.primaryStage = primaryStage;
		
		try {
		  		  
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiView.class.getResource("GuiFX.fxml"));
			rootLayout = (AnchorPane)  loader.load();
			
			
			System.out.println(rootLayout.toString());
			Scene scene = new Scene(rootLayout);
			System.out.println(scene.toString());
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

  public void setGrid(GridPane grid) {
    ((BorderPane) rootLayout.getChildren().get(0)).setCenter(grid);
  } 
  
  public int getWindowHeight () {
    System.out.println( (int) rootLayout.getHeight());
    return (int) rootLayout.getHeight();
    
  }
  
  public int getWindowWidth () {
    System.out.println( (int) rootLayout.getWidth());
    return (int) rootLayout.getWidth();
    
  }
	
	
}
