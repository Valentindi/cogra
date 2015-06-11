package gui;

import factories.DialogFactory;
import gui.GuiController.MouseDragEnteredListener;
import gui.GuiController.MouseDragLeaveListener;
import gui.GuiController.SetBresenhamHandler;
import gui.GuiController.SetDummyHandler;
import gui.GuiController.SetVereinfHandler;
import gui.GuiController.ZoomInHandler;
import gui.GuiController.ZoomOutHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Group;

public class GuiView {

	private AnchorPane rootLayout;
	private Pane backgroundPane;
	private Stage primaryStage;

	private Scene scene;

	MenuBar menubar = new MenuBar();
	Menu menuDatei = new Menu("File");
	Menu menuAlgorithmen = new Menu("Algorithmen");
	Menu menuZoom = new Menu("Zoom");
	MenuItem miBeenden = new MenuItem("Beenden");

	RadioMenuItem miADummy = new RadioMenuItem("Dummy");
	RadioMenuItem miABresenham = new RadioMenuItem(
			"Rasterkonvertierung Linie - Bresenham-Algorithmus");
	RadioMenuItem miAvereinfB = new RadioMenuItem(
			"Antialising Linie - abgewandelter Bresenham-Algorithmus");

	MenuItem miZPlus = new MenuItem("Zoom +");
	MenuItem miZMinus = new MenuItem("Zoom -");

	public GuiView(Stage primaryStage) {

		this.primaryStage = primaryStage;

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiView.class.getResource("GuiFX.fxml"));
			rootLayout = (AnchorPane) loader.load();

			System.out.println(rootLayout.toString());
			scene = new Scene(rootLayout);

			addMenu();

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addMenu() {
		addEventHandler();

		

		menuDatei.getItems().addAll(miBeenden);
		menuAlgorithmen.getItems().addAll(miADummy, miABresenham, miAvereinfB);
		menuZoom.getItems().addAll(miZPlus, miZMinus);

		menubar.getMenus().addAll(menuDatei, menuAlgorithmen, menuZoom);
		menubar.setLayoutX(scene.getWidth());
		// try {
		((BorderPane) rootLayout.getChildren().get(0)).setTop(menubar);
		// scene.getRoot().getChildrenUnmodifiable().add(menubar);
		/*
		 * } catch (Exception e) { System.out.println("Failed Build Menu");
		 * System.out.println(e); }
		 */

	}

	private void addEventHandler() {
		miBeenden.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (DialogFactory.acceptDialog(null, "Beenden", null)) {
					System.exit(0);
				}

			}
		});
		miADummy.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				menuAlgorithmen.setText("Active Algorithm: Dummy" );
				miADummy.setSelected(true);
				miABresenham.setSelected(false);
				miAvereinfB.setSelected(false);
			}
		});
		
		miABresenham.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				menuAlgorithmen.setText("Active Algorithm: Bresenham" );
				miADummy.setSelected(false);
				miABresenham.setSelected(true);
				miAvereinfB.setSelected(false);

				
			}
		});
		
		miAvereinfB.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				menuAlgorithmen.setText("Active Algorithm: vereinfachter Bresenham");
				miADummy.setSelected(false);
				miABresenham.setSelected(false);
				miAvereinfB.setSelected(true);
			}
		});
		
		
		
	}

	public void setGrid(GridPane grid) {
		backgroundPane = new Pane();
		backgroundPane.setStyle("-fx-background-color: Black");
		backgroundPane.getChildren().add(grid);
		((BorderPane) rootLayout.getChildren().get(0))
				.setCenter(backgroundPane);
	}


	public int getWindowHeight() {
		return (int) rootLayout.getHeight();

	}

	public int getWindowWidth() {
		return (int) rootLayout.getWidth();

	}

	public void addZoomInListener(EventHandler<ActionEvent> zoomInHandler) {
		//miZPlus.addEventHandler(MouseEvent.MOUSE_PRESSED, handleZoomIn);
		miZPlus.setOnAction(zoomInHandler);
	}

	public void addZoomOutListener(EventHandler<ActionEvent> zoomOutHandler) {
		miZMinus.setOnAction(zoomOutHandler);
	}

	public void addResizeListener(ChangeListener<Number> resizeListener) {
		System.out.println(scene.toString());
		System.out.println(resizeListener.toString());

		scene.widthProperty().addListener(resizeListener);
		scene.heightProperty().addListener(resizeListener);
	}

	public void addMouseDragEnteredListener(
			MouseDragEnteredListener mouseDragEnteredListener) {
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED,
				mouseDragEnteredListener);

	}

	public void addMouseDragLeaveListener(
			MouseDragLeaveListener mouseDragLeaveListener) {

		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseDragLeaveListener);

	}

	public void close() {
		System.out.println("Close");
	}

	public void addSetDummyHandler(SetDummyHandler setDummyHandler) {
		miADummy.setOnAction(setDummyHandler);
		
	}

	public void addSetBresenhamHandler(SetBresenhamHandler setBresenhamHandler) {
		miABresenham.setOnAction(setBresenhamHandler);
		
	}

	public void addSetvereinfHandler(SetVereinfHandler setVereinfHandler) {
		miABresenham.setOnAction(setVereinfHandler);
		
	}

}
