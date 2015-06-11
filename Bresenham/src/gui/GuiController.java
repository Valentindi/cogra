package gui;

import algorithm.Bresenham;
import algorithm.DummyAlgoithm;
import algorithm.exampleLine;
import algorithm.vereinfachterBresenham;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin.FakeFocusTextField;

import factories.DialogFactory;
import factories.FindeLineColumnFactory;
import gui.grid.GridBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GuiController {

	private GuiView guiView;
	private GridBuilder gridBuilder;

	private double beginX;
	private double beginY;

	public static String activeAlgorithm = "Dummy";
	private int pixelSize = 15;

	public GuiController(GuiView guiView, GridBuilder gridBuilder) {
		this.guiView = guiView;
		this.gridBuilder = gridBuilder;

		this.guiView.addResizeListener(new ResizeListener()); // Fenster
																// ResizeListener
		this.gridBuilder.setCickOnPixelHandler(new ClickOnPixelHandler()); // Pixel
																			// ClickListener
		this.gridBuilder
				.addMouseDragEnteredListener(new MouseDragEnteredListener());// Drag
																				// Begonnen
		this.gridBuilder
				.addMouseDragLeaveListener(new MouseDragLeaveListener());
		this.guiView.miADummy.setOnAction(new SetDummyHandler());
		this.guiView.miABresenham.setOnAction(new SetBresenhamHandler());
		this.guiView.miAvereinfB.setOnAction(new SetVereinfHandler());
		this.guiView.miZPlus.setOnAction(new ZoomInHandler());
		this.guiView.miZMinus.setOnAction(new ZoomOutHandler());
		this.guiView.miAexamplLine.setOnAction(new SetExampleLineHandler());

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
		System.out.println("I am at Color The Rect");
		Rectangle pixel = new Rectangle();
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
			buildGrid(); // Wenn neue Größe --> Grid neu bauen
		}
	}

	class ClickOnPixelHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			final Rectangle pixel = (Rectangle) (event.getTarget());

			// gridBuilder.setRectColor(pixel);
		}
	}

	class MouseDragEnteredListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			System.out.println("Mouse Down test");

			beginX = event.getX();
			beginY = event.getY();
			System.out.println(beginX + " BEGIN " + beginY);

		}

	}

	class MouseDragLeaveListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			System.out.println("Mouse UP test");
			double foo;
			double endY = event.getY();
			double endX = event.getX();

			if (endY < beginY) {
				foo = endY;
				endY = beginY;
				beginY = foo;
			}

			if (endX < beginX) {
				foo = endX;
				endX = beginX;
				beginX = foo;
			}

			int beginXLine = FindeLineColumnFactory.getLineORColumn(beginX,
					pixelSize);
			int beginYLine = FindeLineColumnFactory.getLineORColumn(beginY,
					pixelSize);
			int endXLine = FindeLineColumnFactory.getLineORColumn(endX,
					pixelSize) + 1;
			int endYLine = FindeLineColumnFactory.getLineORColumn(endY,
					pixelSize) + 1;

			System.out.println(beginXLine + " " + beginYLine + " " + endXLine
					+ " " + endYLine);
			Color rectColors[][] = new Color[endXLine - beginXLine][endYLine
					- beginYLine];

			switch (activeAlgorithm) {
			case "Dummy":
				rectColors = DummyAlgoithm.run(beginXLine, beginYLine,
						endXLine, endYLine);
				break;
			case "Bresenham":
				rectColors = Bresenham.run(beginXLine, beginYLine, endXLine,
						endYLine);
				break;
			case "vereinfachterBresenham":
				rectColors = vereinfachterBresenham.run(beginXLine, beginYLine,
						endXLine, endYLine);
				break;

			case "exampleLine":
				rectColors = exampleLine.run(beginXLine, beginYLine, endXLine,
						endYLine);
				break;
			// break;

			default:
				break;
			}

			colorTheRect(rectColors, beginXLine, beginYLine);
		}

	}

}
