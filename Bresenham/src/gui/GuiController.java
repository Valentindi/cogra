package gui;

import algorithm.Bresenham;
import algorithm.DummyAlgoithm;
import algorithm.exampleLine;
import algorithm.vereinfachterBresenham;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin.FakeFocusTextField;

import factories.DialogFactory;
import factories.FindeLineColumnFactory;
import gui.grid.GridBuilder;
import gui.grid.components.CograRectangle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GuiController {

	private GuiView guiView;
	private GridBuilder gridBuilder;

	private int beginX = -1;
	private int beginY = -1;
	
	private CograRectangle beginPixel;
	private CograRectangle endPixel;

	public static String activeAlgorithm = "Dummy";
	private int pixelSize = 15;

	public GuiController(GuiView guiView, GridBuilder gridBuilder) {
		this.guiView = guiView;
		this.gridBuilder = gridBuilder;

		this.guiView.addResizeListener(new ResizeListener()); // Fenster
																// ResizeListener
		this.gridBuilder.setCickOnPixelHandler(new ClickOnPixelHandler()); // Pixel
																			// ClickListener
		// this.gridBuilder
		// .addMouseDragEnteredListener(new MouseDragEnteredListener());// Drag
		// Begonnen
		// this.gridBuilder
		// .addMouseDragLeaveListener(new MouseDragLeaveListener());
		this.guiView.miADummy.setOnAction(new SetDummyHandler());
		this.guiView.miABresenham.setOnAction(new SetBresenhamHandler());
		this.guiView.miAvereinfB.setOnAction(new SetVereinfHandler());
		this.guiView.miZPlus.setOnAction(new ZoomInHandler());
		this.guiView.miZMinus.setOnAction(new ZoomOutHandler());
		this.guiView.mbZPlus.setOnAction(new ZoomInHandler());
		this.guiView.mbZMinus.setOnAction(new ZoomOutHandler());
		this.guiView.miAexamplLine.setOnAction(new SetExampleLineHandler());
		this.guiView.miClear.setOnAction(new ClearHandler());

	}

	public void buildGrid() {

		guiView.setGrid(gridBuilder.buildGrid(pixelSize,
				guiView.getWindowHeight(), guiView.getWindowWidth()));
	}

	public void incPixelSize() {
		if (pixelSize < 5)
			pixelSize = 5;
		else
			pixelSize = pixelSize + 5;

		System.out.println("ZoomIn. New PixelSize: " + pixelSize);
		buildGrid();
	}

	public void decPixelSize() {
		if (pixelSize <= 5)
			pixelSize = 3;
		else
			pixelSize = pixelSize - 5;

		System.out.println("ZoomOut. New PixelSize: " + pixelSize);
		buildGrid();
	}

	public void colorTheRect(Color[][] colorRect, int beginX, int beginY) {

		CograRectangle pixel = new CograRectangle();
		pixel.setWidth(pixelSize);
		pixel.setHeight(pixelSize);
		for (int i = 0; i < colorRect.length; i++) {
			for (int j = 0; j < colorRect[i].length; j++) {
				pixel = gridBuilder.getPixel((beginX + i), (beginY + j));

				if (colorRect[i][j] != null) {
					gridBuilder.setRectColor(pixel, colorRect[i][j]);
				}
			}

		}

	}
	
public void clearMatrix() {
	
	Color[][] whiteColor = new Color[gridBuilder.getMatrixWidth()][gridBuilder.getMatrixHeight()];
	
	for (int i = 0; i < gridBuilder.getMatrixWidth(); i++) {
		for (int j = 0; j < gridBuilder.getMatrixHeight(); j++) {
			whiteColor[i][j]= Color.WHITE;
		}
		
	}
	
	colorTheRect(whiteColor, 0, 0);
		
	}

	class SetDummyHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			guiView.menuAlgorithmen.setText("Active Algorithm: Dummy");
			guiView.miADummy.setSelected(true);
			guiView.miABresenham.setSelected(false);
			guiView.miAvereinfB.setSelected(false);
			guiView.miAexamplLine.setSelected(false);

			activeAlgorithm = "Dummy";
			System.out.println(activeAlgorithm);
		}

	}

	class SetBresenhamHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			guiView.menuAlgorithmen.setText("Active Algorithm: Bresenham");
			guiView.miADummy.setSelected(false);
			guiView.miABresenham.setSelected(true);
			guiView.miAvereinfB.setSelected(false);
			guiView.miAexamplLine.setSelected(false);

			activeAlgorithm = "Bresenham";
			System.out.println(activeAlgorithm);

		}

	}

	class SetVereinfHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			guiView.menuAlgorithmen
					.setText("Active Algorithm: vereinfachter Bresenham");
			guiView.miADummy.setSelected(false);
			guiView.miABresenham.setSelected(false);
			guiView.miAvereinfB.setSelected(true);
			guiView.miAexamplLine.setSelected(false);

			activeAlgorithm = "vereinfachterBresenham";
			System.out.println(activeAlgorithm);

		}

	}

	class SetExampleLineHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			guiView.menuAlgorithmen.setText("Active Algorithm: Example Line");
			guiView.miADummy.setSelected(false);
			guiView.miABresenham.setSelected(false);
			guiView.miAvereinfB.setSelected(false);
			guiView.miAexamplLine.setSelected(true);
			activeAlgorithm = "exampleLine";
			System.out.println(activeAlgorithm);

		}

	}
	
	class ClearHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			clearMatrix();
		}
	}

	class ZoomInHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			incPixelSize();
		}
	}

	class ZoomOutHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			decPixelSize();
		}
	}

	class ResizeListener implements ChangeListener<Number> {

		public void changed(ObservableValue<? extends Number> observableValue,
				Number oldSceneWidth, Number newSceneWidth) {
			buildGrid(); // Wenn neue GrÃ¶ÃŸe --> Grid neu bauen
		}
	}

	class ClickOnPixelHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {

			Boolean changeX = false;
			Boolean changeY = false;
			if (beginX > -1 && beginY > -1) {
				int endX, endY;
				try {
					endX = ((CograRectangle) event.getTarget()).getPosX();
					endY = ((CograRectangle) event.getTarget()).getPosY();
					endPixel = (CograRectangle) event.getTarget();
			
				} catch (Exception e) {
					DialogFactory.ErrorDialog("Error",
							"Drag wurde festgestellt",
							"Strukturen werden durch 2 Klicks erstellt");
					return;
				}

				if (endX < beginX) {
					changeX = true;
					int foo = endX;
					endX = beginX;
					beginX = foo;
				}

				if (endY < beginY) {
					changeY = true;
					int foo = endY;
					endY = beginY;
					beginY = foo;
				}

				endX++;
				endY++;

				Color rectColors[][] = new Color[endX - beginX][endY - beginY];
				switch (activeAlgorithm) {
				case "Dummy":
					rectColors = DummyAlgoithm.run(beginX, beginY, endX, endY);
					break;
				case "Bresenham":
					rectColors = Bresenham.run(beginX, beginY, endX, endY);
					break;
				case "vereinfachterBresenham":
					rectColors = vereinfachterBresenham.run(beginX, beginY,
							endX, endY);
					break;

				case "exampleLine":
					exampleLine activeAlgortithm = new exampleLine();
					rectColors = exampleLine.run(beginX, beginY, endX,
							endY, changeX, changeY);
					break;
				// break;

				default:
					break;
				}
				// System.out.println(rectColors.toString());

				if (guiView.miShowLine.isSelected()) {
					System.out.println("ShowHelpLine");
					Line helpLine = new Line();
					if(changeX == false){
						helpLine.setStartX(beginX*(pixelSize +1 ) + (0.5*pixelSize + 1));
						helpLine.setEndX(endX * (pixelSize +1) - (0.5*pixelSize + 1));
						
					}else{
						helpLine.setEndX(beginX*(pixelSize +1 ) + (0.5*pixelSize + 1));
						helpLine.setStartX(endX * (pixelSize +1) - (0.5*pixelSize + 1));
						
					}
						
					if(changeY == false){
						helpLine.setStartY(beginY* (pixelSize+1) + (0.5*pixelSize + 1));
						helpLine.setEndY(endY * (pixelSize +1)- + (0.5*pixelSize + 1));
						
					}else{
						helpLine.setEndY(beginY* (pixelSize+1) + (0.5*pixelSize + 1));
						helpLine.setStartY(endY * (pixelSize +1)- + (0.5*pixelSize + 1));
						
					}
					helpLine.setFill(Color.RED);
					helpLine.setStroke(Color.RED);

					System.out.println(helpLine.toString());
					guiView.backgroundPane.getChildren().add(helpLine);
				}

				colorTheRect(rectColors, beginX, beginY);

				guiView.status.setText("Klick für Zeichnen");
				beginX = Integer.MIN_VALUE;
				beginY = Integer.MIN_VALUE;
			} else {

				try {
					beginX = ((CograRectangle) event.getTarget()).getPosX();
					beginY = ((CograRectangle) event.getTarget()).getPosY();
					beginPixel = (CograRectangle) event.getTarget();

					System.out.println("BeginX: " + beginX);
					System.out.println("BeginY: " + beginY);
				} catch (Exception e) {
					DialogFactory.ErrorDialog("Error",
							"Drag wurde festgestellt",
							"Strukturen werden durch 2 Klicks erstellt");
				}
				guiView.status.setText("Kick für Fertigstellen");
			}

		}
	}

	

	/*
	 * class MouseDragEnteredListener implements EventHandler<MouseEvent> {
	 * 
	 * @Override public void handle(MouseEvent event) {
	 * System.out.println("Mouse Down test"); //Zuweisung der Pixel bei Begin
	 * des Mausziehens. beginX = event.getX(); beginY = event.getY();
	 * System.out.println(beginX + " BEGIN " + beginY);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * class MouseDragLeaveListener implements EventHandler<MouseEvent> {
	 * 
	 * @Override public void handle(MouseEvent event) {
	 * System.out.println("Mouse UP test"); double foo; double endY =
	 * event.getY(); double endX = event.getX(); //Austauschen der Variablen,
	 * falls End > Begin if (endY < beginY) { foo = endY; endY = beginY; beginY
	 * = foo; }
	 * 
	 * if (endX < beginX) { foo = endX; endX = beginX; beginX = foo; }
	 * 
	 * //Berechnung des Gedrückten CograRectangle int beginXLine =
	 * FindeLineColumnFactory.getLineORColumn(beginX, pixelSize); int beginYLine
	 * = FindeLineColumnFactory.getLineORColumn(beginY, pixelSize); int endXLine
	 * = FindeLineColumnFactory.getLineORColumn(endX, pixelSize) + 1; int
	 * endYLine = FindeLineColumnFactory.getLineORColumn(endY, pixelSize) + 1;
	 * 
	 * //Erstellung des der FarbMatrix Color rectColors[][] = new Color[endXLine
	 * - beginXLine][endYLine - beginYLine];
	 * 
	 * //aufruf des aktiven Algoritmus switch (activeAlgorithm) { case "Dummy":
	 * rectColors = DummyAlgoithm.run(beginXLine, beginYLine, endXLine,
	 * endYLine); break; case "Bresenham": rectColors =
	 * Bresenham.run(beginXLine, beginYLine, endXLine, endYLine); break; case
	 * "vereinfachterBresenham": rectColors =
	 * vereinfachterBresenham.run(beginXLine, beginYLine, endXLine, endYLine);
	 * break;
	 * 
	 * case "exampleLine": rectColors = exampleLine.run(beginXLine, beginYLine,
	 * endXLine, endYLine); break; // break;
	 * 
	 * default: break; } //Zeichnen des Grids colorTheRect(rectColors,
	 * beginXLine, beginYLine); }
	 * 
	 * }
	 */
}
