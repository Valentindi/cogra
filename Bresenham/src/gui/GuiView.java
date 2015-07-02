package gui;

import gui.GuiController.CloseWindowEventHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuiView {

	private AnchorPane rootLayout;
	public Pane backgroundPane;
	private Stage primaryStage;
	private SplitPane bottom;

	public Scene scene;

	MenuBar menubar = new MenuBar();
	Menu menuDatei = new Menu("File");
	Menu menuAlgorithmen = new Menu("Algorithmen");
	Menu menuZoom = new Menu("Zoom");
	MenuItem miBeenden = new MenuItem("Beenden");
	MenuItem miClear = new MenuItem("Clear");

	RadioMenuItem miShowLine = new RadioMenuItem("Hilfslinie anzeigen");
	RadioMenuItem miADummy = new RadioMenuItem("Dummy");
	RadioMenuItem miABresenham = new RadioMenuItem(
			"Rasterkonvertierung Linie - Bresenham-Algorithmus");
	RadioMenuItem miAvereinfB = new RadioMenuItem(
			"Antialising Linie - abgewandelter Bresenham-Algorithmus");
	RadioMenuItem miAexamplLine = new RadioMenuItem("Example Line");
	SeparatorMenuItem mSeperator = new SeparatorMenuItem();
	MenuItem miZPlus = new MenuItem("Zoom +");
	MenuItem miZMinus = new MenuItem("Zoom -");

	final StackPane stZoomPlus = new StackPane();
	final StackPane stZoomMinus = new StackPane();
	final StackPane stStatusLabel = new StackPane();

	final SplitPane topSP = new SplitPane();

	public Label status = new Label(
			"Auf Raster Klicken um Startpunkt zu wÃ¤hlen");
	Button mbZPlus = new Button("Zoom +");
	Button mbZMinus = new Button("Zoom -");

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
		
		miBeenden.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				System.exit(0);

			}
		});
		
		menuDatei.getItems().addAll(miShowLine, miClear, miBeenden);
		menuAlgorithmen.getItems().addAll(/* miADummy, */miABresenham,
				miAvereinfB,
				/* miAexamplLine, */mSeperator);
		menuZoom.getItems().addAll(miZPlus, miZMinus);

		menubar.getMenus().addAll(menuDatei, menuAlgorithmen);
		menubar.setLayoutX(scene.getWidth());

		stZoomPlus.getChildren().add(mbZPlus);
		stZoomMinus.getChildren().add(mbZMinus);
		bottom = new SplitPane();
		bottom.getItems().addAll(stStatusLabel, mbZPlus, mbZMinus);
		bottom.setDividerPositions(0.5f, 0.75f);

		stStatusLabel.getChildren().add(status);

		menubar.setLayoutX(200);

		topSP.getItems().addAll(menubar, bottom);
		topSP.setDividerPositions(0.4f);
		((BorderPane) rootLayout.getChildren().get(0)).setTop(topSP);

	}

	
	public void setGrid(Pane pane) {
		backgroundPane = new Pane();
		backgroundPane.setStyle("-fx-background-color: #E6E6E6");
		backgroundPane.getChildren().add(pane);
		((BorderPane) rootLayout.getChildren().get(0))
				.setCenter(backgroundPane);
	}

	public int getWindowHeight() {
		return (int) (rootLayout.getHeight());

	}

	public int getWindowWidth() {
		return (int) rootLayout.getWidth();

	}

	public void addResizeListener(ChangeListener<Number> resizeListener) {
		System.out.println(scene.toString());
		System.out.println(resizeListener.toString());

		scene.widthProperty().addListener(resizeListener);
		scene.heightProperty().addListener(resizeListener);
	}

	public void updateMenu() {
		((BorderPane) rootLayout.getChildren().get(0)).getChildren().remove(
				topSP);
		((BorderPane) rootLayout.getChildren().get(0)).setTop(topSP);
	}

	public void setCloseEvent(CloseWindowEventHandler closeWindowEventHandler) {
		System.out.println("Schade und auf Wiedersehen");
		primaryStage.setOnCloseRequest(closeWindowEventHandler);

	}

	/*
	 * public void addMouseDragEnteredListener( MouseDragEnteredListener
	 * mouseDragEnteredListener) {
	 * scene.addEventHandler(MouseEvent.MOUSE_PRESSED,
	 * mouseDragEnteredListener);
	 * 
	 * }
	 * 
	 * public void addMouseDragLeaveListener( MouseDragLeaveListener
	 * mouseDragLeaveListener) {
	 * 
	 * scene.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseDragLeaveListener);
	 * 
	 * }
	 */

}
