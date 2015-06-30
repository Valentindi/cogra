package gui;

import factories.DialogFactory;
import factories.GreyScaleFactory;
import gui.grid.GridBuilder;
import gui.grid.components.CograRectangle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import algorithm.DummyAlgoithm;
import algorithm.exampleLine;
import algorithm.vereinfachterBresenham;

public class GuiController {
	
	private GuiView guiView;
	private GridBuilder gridBuilder;

	private int beginX = -1;
	private int beginY = -1;

	CograRectangle beginPixel;
	CograRectangle endPixel;


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

	public void clearMatrix() {

	    gridBuilder.clearGrid();
		buildGrid();

	}

	private void drawHelpline(int beginX, int beginY, int endX, int endY,
			Boolean changeX, Boolean changeY) {
		if (guiView.miShowLine.isSelected()) {
			Line helpLine = new Line();

			helpLine.setStartX(beginX * (pixelSize + 1) + (0.5 * pixelSize));
			helpLine.setEndX(endX * (pixelSize + 1) - (0.5 * pixelSize));

			helpLine.setStartY(beginY * (pixelSize + 1) + (0.5 * pixelSize));
			helpLine.setEndY(endY * (pixelSize + 1) - +(0.5 * pixelSize));

			helpLine.setFill(Color.RED);
			helpLine.setStroke(Color.RED);

			guiView.backgroundPane.getChildren().add(helpLine);
		}

	}
	/**
	 * 
	 * Ruft die einzelnen Algorithmen auf
	 * 
	 * @param beginX Koordinate von dem Beginn des Algorithmus (oben links)
	 * @param beginY Koordinate von dem Beginn des Algorithmus (oben links)
	 * @param beginXorg Koordinate von ersten Klick (Beginn der Linie). Es empfiehlt sich damit zu arbeiten!  
	 * @param beginYorg Koordinate von ersten Klick (Beginn der Linie). Es empfiehlt sich damit zu arbeiten!
	 * @param endX Koordinate von dem Beginn des Algorithmus (unten rechts)
	 * @param endY Koordinate von dem Beginn des Algorithmus (unten rechts)
	 * @param endXorg Koordinate von zweiten Klick (Ende der Linie). Es empfiehlt sich damit zu arbeiten!
	 * @param endYorg Koordinate von zweiten Klick (Ende der Linie). Es empfiehlt sich damit zu arbeiten!
	 * @param change true, wenn Algorithmus von unten lins nach oben rechts l�uft
	 * @param changeX true, wenn endX und beginX vertauscht wurden
	 * @param changeY true, wenn endY und beginY vertauscht wurden
	 */

	private void runAlgs(int beginX, int beginY, int beginXorg, int beginYorg,
			int endX, int endY, int endXorg, int endYorg, Boolean change,
			Boolean changeX, Boolean changeY) {
		
		

		Color rectColors[][] = new Color[endX - beginX][endY - beginY];
		switch (activeAlgorithm) {
		case "Dummy":
			rectColors = DummyAlgoithm.run(beginX, beginY, endX, endY);
			break;
		case "Bresenham":
			
			//Aufruf des der Bresline-Vorbereitungsfunktion mit dem Originalwerten
			bresline(beginXorg, beginYorg, endXorg - 1, endYorg - 1,
					Color.BLACK);
			break;
		case "vereinfachterBresenham":
			bresline(beginXorg, beginYorg, endXorg - 1, endYorg -1, Color.BLACK);
			/*
			 * Hey Lina,
			 * 
			 * ich glaube mittlerweile, das es einfacher ist erstmal den
			 * Algorithmus hier in dieser Klasse zu implementieren, da man hier
			 * z.B. da hier der GridBuilder gehalten wird, und man direkt auf
			 * das Grud zugreifen kann.
			 * 
			 * Au�erdem sind Jaegers Algorithmen darauf angelegt, das sie nicht
			 * bei unbedingt (0,0) beginnen, sondern auch irgendwo in dem Grid.
			 * Wenn man mit dem RectColors arbeitet, wie ich es urspr�ngich
			 * gedacht habe, dann h�tte man bei das nur auf einem Ausschnitt von
			 * (0,0) bis (dx, dx) machen k�nnen. Aber das funktioniert alles
			 * nicht so einfach wie ich mir das gedacht habe.
			 * 
			 * Ich hab mir gestern den Anti-Aliasing-Algorithmus in den Folien
			 * mal kurz angeschaut, und ich denke, du brauchst noch eine
			 * Funktion wie die bresline (Siehe Folie 2.14), der dem Algorithmus
			 * die richtigen Werte �bergibt und aus der dann der eigentliche
			 * Algorithmus aufgerufen wird.
			 * 
			 * Und dann, wenn erstmal alles funktioniert, dann k�nnen wir ja
			 * beim Refactoring die Algorithmen in eigene Klassen verschieben.
			 * 
			 * �brigens, kann man SetzePixel(x,y,f) aus dem Folien, mit
			 * changePixelColor(x,y, greyScaleFactory.getGreyScale(1-abs(d))
			 * umsetzen.
			 */
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
		// Zeichnet rectCOlors in die Matrix und begint bei beginX, beginY
		colorTheRect(rectColors, beginX, beginY);

	}

	/**
	 * Vorbereitung des Start des Bresline Algorithmus
	 * 
	 * @param x0
	 * @param y0
	 * @param xn
	 * @param yn
	 * @param black
	 */
	private void bresline(int x0, int y0, int xn, int yn, Color black) {

		int dx = xn - x0;
		int dy = yn - y0;

		System.out.println("abs(dx): " + abs(dx) + " abs(dy): " + abs(dy));

		if (abs(dx) >= abs(dy)) {
			System.out.println("Anstieg  -45 .. 0 .. +45");
			if (x0 > xn) {
				bresline(xn, yn, x0, y0, Color.BLACK);

			} else {
				if (activeAlgorithm == "Bresenham"){
					bres1(x0, y0, xn, dx, dy, false);
				}else{
					wuLine(x0, y0, xn, dx, dy, false);
				}
			}

		} else {
			System.out.println("{Anstieg +45 .. 90 .. -45}");
			if (y0 > yn) {
				bresline(xn, yn, x0, y0, Color.BLACK);

			} else {
				if (activeAlgorithm == "Bresenham"){
					bres1(y0, x0, yn, dy, dx, true);
				}else{
					wuLine(y0, x0, yn, dy, dx, true);
					
				}
			}
		}

	}

	/**
	 * Ausf�hrung des Bresline-Algorithmuses.
	 * 
	 * @param x0
	 * @param y0
	 * @param xn
	 * @param dx
	 * @param dy
	 * @param sp
	 */
	private void bres1(int x0, int y0, int xn, int dx, int dy, boolean sp) {
		int sw, d, d1, d2, x, y;

		System.out.println("Bresham: " + x0 + " : " + y0 + " : " + xn + " : "
				+ dx + " : " + dy + " : " + sp);
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
			changePixelColor(x, y, Color.BLACK);

		} else {
			changePixelColor(y, x, Color.BLACK);

		}

		while (x < xn) {

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
				changePixelColor(x, y, Color.BLACK);

			} else {
				changePixelColor(y, x, Color.BLACK);

			}
		}

	}
	
	private void wuLine(int x0, int y0, int xn, int dx, int dy, boolean sp){
		
		double d = 0;
		double incrd = 1 - (dy/dx);
		int yn = y0 + dy;
		
		System.out.println("Wu: " + x0 + " : " + y0 + " : " + xn + " : "
				+ dx + " : " + dy + " : " + sp);
		
		for (int i = 0; i < dx; i++){
			xn++;
			yn++;
			d = d + incrd;
			changePixelColor(xn,yn, GreyScaleFactory.getGreyScale(1-abs(d)));
			if (d <= 0){
				if (!sp){
					changePixelColor(xn,yn+1, GreyScaleFactory.getGreyScale(1-abs(d)));
				}else{
					changePixelColor(yn, xn+1, GreyScaleFactory.getGreyScale(1-abs(d)));
					
				}
				
			}else{
				yn--;
				d = d-1;
				if (!sp){
					changePixelColor(xn,yn, GreyScaleFactory.getGreyScale(1-abs(d)));
				}else{
					changePixelColor(yn, xn, GreyScaleFactory.getGreyScale(1-abs(d)));
				}
				
			}
			
		}
		
		
	}

	private void changePixelColor(int x, int y, Color color) {
		CograRectangle pixel = gridBuilder.getPixel(x, y);
		pixel.setFill(color);
		gridBuilder.setPixel(pixel, x, y);

	}
	/**
	 * Gibt Positiven wert zur�ck
	 * @param number
	 * @return
	 */
	private static int abs(int number) {
		if (number < 0) {
			number *= -1;
		}
		return number;
	}
	
	/**
	 * Gibt Positiven wert zur�ck
	 * @param number
	 * @return
	 */
	private static double abs(double number) {
		if (number < 0) {
			number *= -1;
		}
		return number;
	}

	/**
	 * 	Zeichnet rectCOlors

	 * @param colorRect Matrix mit Farben, die in Matrix �bertragen werden sollen
	 * @param beginX x-Koordinate oben-links
	 * @param beginY y-Koordinate oben-links
	 */
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
			buildGrid(); // Wenn neue Größe --> Grid neu bauen
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
				endX++;
				endY++;
				int endXorg = endX;
				int endYorg = endY;

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

				guiView.status.setText("Klick f�r Zeichnen");
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
					DialogFactory
							.ErrorDialog(
									"Error",
									"Drag wurde festgestellt, Strukturen werden durch 2 Klicks erstellt",
									"Oder es wurde auf den Rand eines Pixels gedr�ckt!");
				}
				guiView.status.setText("Kick f�r Fertigstellen");
			}

		}

	}

}
