package gui.grid;


import gui.grid.components.CograRectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class GridBuilder {

	static final int offset = 1;

	private CograRectangle[][] gridArray;
	private EventHandler<MouseEvent> handleClickOnPixel;
	private EventHandler<MouseEvent> handleMouseDragEnteredListener;
	private EventHandler<MouseEvent> handleMouseDragLeaveListener;

	public GridPane buildGrid(int pixelSize, int windowHeight, int windowWidth) {

		// Dynamische berechnung wie viele Pixel man braucht.
		int pixelCountX = getCountX(pixelSize, windowHeight);
		int pixelCountY = getCountY(pixelSize, windowWidth);

		GridPane root = new GridPane();

		root.addEventHandler(MouseEvent.MOUSE_CLICKED, handleClickOnPixel);
		gridArray = new CograRectangle[pixelCountY][pixelCountX];

		for (int i = 0; i < gridArray.length; i++) {
			for (int t = 0; t < gridArray[i].length; t++) {
				// Hier wird das gridArray erstellt und mit weißen Pixeln
				// gefüllt

				gridArray[i][t] = new CograRectangle();
				gridArray[i][t].setWidth(pixelSize);
				gridArray[i][t].setHeight(pixelSize);
				gridArray[i][t].setFill(Color.WHITE);
				gridArray[i][t].setPosX(i);
				gridArray[i][t].setPosY(t);
				
				root.getColumnConstraints().add(
						new ColumnConstraints(pixelSize + offset));
				GridPane.setConstraints(gridArray[i][t], i, t);
				root.getChildren().add(gridArray[i][t]);
			}
			root.getRowConstraints()
					.add(new RowConstraints(pixelSize + offset));
		}
		return root;

	}

	private int getCountY(int pixelSize, int windowWidth) {
		int pixelCountY = windowWidth / (pixelSize);

		if (pixelCountY * pixelSize != windowWidth)
			pixelCountY++;
		return pixelCountY;
	}

	private int getCountX(int pixelSize, int windowHeight) {
		int pixelCountX = windowHeight / (pixelSize);

		if (pixelCountX * pixelSize != windowHeight)
			pixelCountX++;

		return pixelCountX;
	}

	public void setCickOnPixelHandler(
			EventHandler<MouseEvent> handleClickOnPixel) {
		this.handleClickOnPixel = handleClickOnPixel;
	}

	public void setRectColor(CograRectangle pixel, Color colorRect) {

		pixel.setFill(colorRect);
		// System.out.println("Pixel: " + getPixelCords(pixel)[0] + " " +
		// getPixelCords(pixel)[1] + " clicked!");
	}

	public void setPixel(CograRectangle pixel, int x, int y) {
		this.gridArray[x][y] = pixel;
		// System.out.println("Pixel: " + getPixelCords(pixel)[0] + " " +
		// getPixelCords(pixel)[1] + " clicked!");
	}

	/**
	 * Liefert die Koordinaten des übergebenen Pixels im Grid
	 * 
	 * @param pixel
	 *            Ein Rechteck (pixel)
	 * @return Die Koordinaten des Pixels im Grid als Array
	 */
	public int[] getPixelCords(CograRectangle pixel) {
		int[] pixelCords = new int[2];

		for (int i = 0; i < gridArray.length; i++) {
			for (int t = 0; t < gridArray[i].length; t++) {
				if (pixel == gridArray[i][t])
					pixelCords[0] = i; // x-Wert
				pixelCords[1] = t; // y-Wert
			}
		}

		return pixelCords;
	}

	public void paintAPixel(int x, int y, Color color) {
		gridArray[x][y].setFill(color);
	}

	public CograRectangle getPixel(int x, int y) {
		return gridArray[x][y];
	}

	/**
	 * 
	 * @param r
	 *            CograRectangle
	 * @param x
	 *            x-Koordinate
	 * @param y
	 *            y-Koordinate
	 * @return true, wenn erfolgreich, false, wenn gescheitert
	 */
	public boolean setRectange(CograRectangle r, int x, int y) {
		try {
			gridArray[x][y] = r;
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	public EventHandler<MouseEvent> getHandleClickOnPixel() {
		return handleClickOnPixel;
	}

	public void setHandleClickOnPixel(
			EventHandler<MouseEvent> handleClickOnPixel) {
		this.handleClickOnPixel = handleClickOnPixel;
	}

	public static int getOffset() {
		return offset;
	}

	public void addMouseDragEnteredListener(
			EventHandler<MouseEvent> mouseDragEnteredListener) {
		this.handleMouseDragEnteredListener = mouseDragEnteredListener;

	}

	public void addMouseDragLeaveListener(
			EventHandler<MouseEvent> mouseDragLeaveListener) {
		this.handleMouseDragLeaveListener = mouseDragLeaveListener;

	}

}
