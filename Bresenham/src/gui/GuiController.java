package gui;

import java.util.ArrayList;

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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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

				if (colorRect[i][j] != null && colorRect[i][j] == Color.BLACK) {
					gridBuilder.setRectColor(pixel, colorRect[i][j]);
				}
			}

		}

	}

	public void colorTheRectWhite(Color[][] colorRect, int beginX, int beginY) {

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

		Color[][] whiteColor = new Color[gridBuilder.getMatrixWidth()][gridBuilder
				.getMatrixHeight()];

		for (int i = 0; i < gridBuilder.getMatrixWidth(); i++) {
			for (int j = 0; j < gridBuilder.getMatrixHeight(); j++) {
				whiteColor[i][j] = Color.WHITE;
			}

		}

		colorTheRectWhite(whiteColor, 0, 0);
		guiView.backgroundPane.getChildren().clear();
		buildGrid();

	}

	private void drawHelpline(int beginX, int beginY, int endX, int endY,
			Boolean changeX, Boolean changeY) {
		if (guiView.miShowLine.isSelected()) {
			System.out.println("ShowHelpLine");
			Line helpLine = new Line();

		
			System.out.println("DrawHelpline: " + beginX + " : " + beginY
					+ " : " + endX + " : " + endY);
			helpLine.setStartX(beginX * (pixelSize+1) + (0.5 * pixelSize));
			helpLine.setEndX(endX * (pixelSize+1) - (0.5 * pixelSize));

			helpLine.setStartY(beginY * (pixelSize+1) + (0.5 * pixelSize));
			helpLine.setEndY(endY * (pixelSize+1) - +(0.5 * pixelSize));

			/*
			 * if (changeX == false) { helpLine.setStartX(beginX * (pixelSize) +
			 * (0.5 * pixelSize)); helpLine.setEndX(endX * (pixelSize) - (0.5 *
			 * pixelSize ));
			 * 
			 * } else { helpLine.setEndX(beginX * (pixelSize) + (0.5 * pixelSize
			 * )); helpLine.setStartX(endX * (pixelSize) - (0.5 * pixelSize ));
			 * 
			 * }
			 * 
			 * if (changeY == false) { helpLine.setStartY(beginY * (pixelSize) +
			 * (0.5 * pixelSize )); helpLine.setEndY(endY * (pixelSize ) - +(0.5
			 * * pixelSize ));
			 * 
			 * } else { helpLine.setEndY(beginY * (pixelSize ) + (0.5 *
			 * pixelSize )); helpLine.setStartY(endY * (pixelSize) - +(0.5 *
			 * pixelSize));
			 * 
			 * }
			 */
			helpLine.setFill(Color.RED);
			helpLine.setStroke(Color.RED);

			// System.out.println(helpLine.toString());
			guiView.backgroundPane.getChildren().add(helpLine);
		}

	}

	private void runAlgs(int beginX, int beginY, int beginXorg, int beginYorg,
			int endX, int endY, int endXorg, int endYorg, Boolean change,
			Boolean changeX, Boolean changeY) {

		Color rectColors[][] = new Color[endX - beginX][endY - beginY];
		switch (activeAlgorithm) {
		case "Dummy":
			rectColors = DummyAlgoithm.run(beginX, beginY, endX, endY);
			break;
		case "Bresenham":
			/*
			 * rectColors = Bresenham.run(beginXorg, beginYorg, endXorg,
			 * endYorg, change);
			 */
			runBresenham(beginXorg, beginYorg, endXorg, endYorg);
			break;
		case "vereinfachterBresenham":
			rectColors = vereinfachterBresenham.run(beginX, beginY, endX, endY);
			break;

		case "exampleLine":
			exampleLine activeAlgortithm = new exampleLine();
			rectColors = exampleLine.run(beginX, beginY, endX, endY, changeX,
					changeY);
			break;
		// break;

		default:
			break;
		}
		// System.out.println(rectColors.toString());
		colorTheRect(rectColors, beginX, beginY);

	}

	private void runBresenham(int beginX, int beginY, int endX, int endY) {

		int beginXorg = beginX;
		int beginYorg = beginY;
		int endXorg = endX;
		int endYorg = endY;

		if (endX < beginX) {
			int foo = endX;
			endX = beginX;
			beginX = foo;
		}

		if (endY < beginY) {
			int foo = endY;
			endY = beginY;
			beginY = foo;
		}

		Color[][] dummyRectGreyScale = new Color[(int) (endX - beginX) + 1][(int) (endY - beginY) + 1];
		System.out.println("The Bresenham is running!");

		for (int i = 0; i < (endX - beginX); i++) {
			for (int j = 0; j < endY - beginY; j++) {
				dummyRectGreyScale[i][j] = Color.WHITE;
			}

		}
		// Implementierung nach Skript von Prof. Jaeger
		bresline(dummyRectGreyScale, beginXorg, beginYorg, endXorg, endYorg,
				Color.BLACK);

		// Valentins angepasste Interpretation
		/*
		 * dummyRectGreyScale = valentinsBresenham(dummyRectGreyScale, 0, 0,
		 * (endX - beginX), (endY - beginY), Color.BLACK, change);
		 */

	}

	private Color[][] bresline(Color[][] dummyRectGreyScale, int x0, int y0,
			int xn, int yn, Color black) {

		int dx = xn - x0;
		int dy = yn - y0;

		// System.out.println("BRESLINE");
		System.out.println("abs(dx): " + abs(dx) + " abs(dy): " + abs(dy));

		if (abs(dx) >= abs(dy)) {
			System.out.println("Anstieg  -45 .. 0 .. +45");
			if (x0 > xn) {
				dummyRectGreyScale = bresline(dummyRectGreyScale, xn, yn, x0,
						y0, Color.BLACK);
			} else {
				dummyRectGreyScale = bres1(dummyRectGreyScale, x0, y0, xn, dx,
						dy, false);
			}

		} else {
			System.out.println("{Anstieg +45 .. 90 .. -45}");
			if (y0 > yn) {
				dummyRectGreyScale = bresline(dummyRectGreyScale, xn, yn, x0,
						y0, Color.BLACK);
			} else {
				/*
				 * dummyRectGreyScale = bres1(dummyRectGreyScale, y0, x0, yn,
				 * dy, dx, true);
				 */
			}
		}

		return dummyRectGreyScale;
	}

	private Color[][] bres1(Color[][] dummyRectGreyScale, int x0, int y0,
			int xn, int dx, int dy, boolean sp) {
		int sw, d, d1, d2, x, y;
		CograRectangle pixel;
		if (dy < 0) {
			sw = -1;
			dy = -dy;

		} else {
			sw = 1;
		}

		d = 2 * dy - dx;
		d1 = 2 * dy;
		d2 = 2 * (dy - dx);
		x = x0;
		y = y0;
		if (!sp) {
			System.out.println("Paint: " + x + " : " + y);
			pixel = gridBuilder.getPixel(x, y);
			pixel.setFill(Color.BLACK);
			gridBuilder.setPixel(pixel, x, y);

		} else {
			System.out.println("Paint: " + y + " : " + x);
			pixel = gridBuilder.getPixel(y, x);
			pixel.setFill(Color.BLACK);
			gridBuilder.setPixel(pixel, y, x);

			dummyRectGreyScale[y][x] = Color.BLACK;
		}

		while (x < (xn - 1)) {
			x = x + 1;
			if (d < 0) {
				System.out.println("d_alt: " + d + "d_neu: " + (d + 1));
				d = d + d1;
			} else {
				y = y + sw;
				System.out.println("y_alt: " + y + "sw: " + sw + "y_neu: "
						+ (y + sw));
				d = d + d2;
				System.out.println("d_alt: " + d + "d2: " + d2 + "d_neu: "
						+ (d + d2));

			}

			if (!sp) {
				System.out.println("Paint: " + x + " : " + y);
				pixel = gridBuilder.getPixel(x, y);
				pixel.setFill(Color.BLACK);
				gridBuilder.setPixel(pixel, x, y);

			} else {
				System.out.println("Paint: " + y + " : " + x);
				pixel = gridBuilder.getPixel(y, x);
				pixel.setFill(Color.BLACK);
				gridBuilder.setPixel(pixel, y, x);

			}
		}

		return dummyRectGreyScale;

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
			// System.out.println(activeAlgorithm);
		}

	}

	private static int sgn(int number) {
		if (number < 0) {
			return -1;
		}
		return 1;
	}

	private static int abs(int number) {
		if (number < 0) {
			number *= -1;
		}
		return number;
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
			// System.out.println(activeAlgorithm);

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
			// System.out.println(activeAlgorithm);

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
			// System.out.println(activeAlgorithm);

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
			Boolean change = false;

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

				// Koordinaten die nicht sortiert sortiert werden
				int beginXorg = beginX;
				int beginYorg = beginY;
				int endXorg = endX + 1;
				int endYorg = endY + 1;

				endX++;
				endY++;

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

				if ((changeX || changeY) && (!(changeX && changeY))) {
					System.out.println("CHANGE");
					change = true;
				}

				System.out.println("EndX: " + endX);
				System.out.println("EndY: " + endY);

				runAlgs(beginX, beginY, beginXorg, beginYorg, endX, endY,
						endXorg, endYorg, change, changeX, changeY);
				drawHelpline(beginXorg, beginYorg, endXorg, endYorg, changeX,
						changeY);

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

}
