package gui;

import gui.GuiController.MouseDragEnteredListener;
import gui.GuiController.MouseDragLeaveListener;
import javafx.beans.value.ChangeListener;
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
  private Button zoomInButton = new Button("+");
  private Button zoomOutButton = new Button("-");
  private VBox buttonBox;
  private Scene scene;


  public GuiView(Stage primaryStage) {
    
    this.primaryStage = primaryStage;

    try {

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(GuiView.class.getResource("GuiFX.fxml"));
      rootLayout = (AnchorPane) loader.load();

      System.out.println(rootLayout.toString());
      scene = new Scene(rootLayout);
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

    VBox buttonBox = new VBox();

    zoomInButton.setPrefWidth(25);
    zoomOutButton.setPrefWidth(25);

    buttonBox.getChildren().addAll(zoomInButton, zoomOutButton);
    backgroundPane.getChildren().add(buttonBox);

    return backgroundPane;
  }

  public int getWindowHeight() {
    return (int) rootLayout.getHeight();

  }

  public int getWindowWidth() {
    return (int) rootLayout.getWidth();

  }

  public void addZoomInListener(EventHandler<MouseEvent> handleZoomIn) {
    zoomInButton.addEventHandler(MouseEvent.MOUSE_PRESSED, handleZoomIn);
  }

  public void addZoomOutListener(EventHandler<MouseEvent> handleZoomOut) {
    zoomOutButton.addEventHandler(MouseEvent.MOUSE_PRESSED, handleZoomOut);
  }
  
  public void addResizeListener(ChangeListener<Number> resizeListener) {
    System.out.println(scene.toString());
    System.out.println(resizeListener.toString());
    
    scene.widthProperty().addListener(resizeListener);
    scene.heightProperty().addListener(resizeListener);
  }




public void addMouseDragEnteredListener(
		MouseDragEnteredListener mouseDragEnteredListener) {
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseDragEnteredListener);
	
}


public void addMouseDragLeaveListener(
		MouseDragLeaveListener mouseDragLeaveListener) {
	
		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseDragLeaveListener);
	
}
  

}
