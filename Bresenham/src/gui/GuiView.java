package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiView {
	
	private AnchorPane rootLayout;
    private Pane backgroundPane;
    private Stage primaryStage;
    private Button zoomIn = new Button("+");
    private Button zoomOut = new Button("-");
    private VBox buttonBox = new VBox();


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
    backgroundPane = new Pane();
    backgroundPane.setStyle("-fx-background-color: Black");
    backgroundPane.getChildren().add(grid);
    backgroundPane = addZoomButton(backgroundPane);
    ((BorderPane) rootLayout.getChildren().get(0)).setCenter(backgroundPane);
  } 
  
  private Pane addZoomButton(Pane backgroundPane) {
    // TODO Auto-generated method stub

    VBox buttonBox = new VBox();
    
    zoomIn.setPrefWidth(25);
    zoomOut.setPrefWidth(25);
    
    buttonBox.getChildren().addAll(zoomIn,zoomOut);
    backgroundPane.getChildren().add(buttonBox);
    
    return backgroundPane;
  }

  public int getWindowHeight () {
    return (int) rootLayout.getHeight();
    
  }
  
  public int getWindowWidth () {
    return (int) rootLayout.getWidth();
    
  }
  
  void addZoomInListener(EventHandler<MouseEvent> handleZoomIn){
    zoomIn.addEventHandler(MouseEvent.MOUSE_PRESSED, handleZoomIn);
  }
  
  void addZoomOutListener(EventHandler<MouseEvent> handleZoomOut){
    zoomOut.addEventHandler(MouseEvent.MOUSE_PRESSED, handleZoomOut);
  }
  
	
}
