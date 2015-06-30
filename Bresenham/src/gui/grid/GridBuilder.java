package gui.grid;


import gui.grid.components.CograRectangle;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class GridBuilder {

  static int offset = 1;

  private static Pixel[][] gridArray;
  private static ArrayList<Pixel> savedPixel = new ArrayList<Pixel>();
  private static GridPainter gp;
  private EventHandler<MouseEvent> handleClickOnPixel;
  private EventHandler<MouseEvent> handleMouseDragEnteredListener;
  private EventHandler<MouseEvent> handleMouseDragLeaveListener;

  public Pane buildGrid(int pixelSize, int windowHeight, int windowWidth) {
    if(pixelSize==1)
      offset = 0;
    else
      offset = 1;

    gp = new GridPainter(windowHeight, windowWidth);

    Pane root = new Pane();

    // Dynamische berechnung wie viele Pixel man braucht.
    int pixelCountX = getCountX(pixelSize, windowHeight);
    int pixelCountY = getCountY(pixelSize, windowWidth);

    root.addEventHandler(MouseEvent.MOUSE_CLICKED, handleClickOnPixel);

    gridArray = new Pixel[pixelCountY][pixelCountX];

    int insertX = 0;
    int insertY = 0;

    updateSavedPixel(pixelSize);
    
    for (int i = 0; i < gridArray.length; i++) {
      for (int t = 0; t < gridArray[i].length; t++) {
        if(existingPixel(i,t) != null) 
          gridArray[i][t] = new Pixel(insertX, insertY, i, t, pixelSize, existingPixel(i,t).getColor());
        else
          gridArray[i][t] = new Pixel(insertX, insertY, i, t, pixelSize, Color.WHITE);

        insertY += pixelSize + offset;
      }
      insertX += pixelSize + offset;
      insertY = 0;
    }

    gp.paint(gridArray);
    root.getChildren().add(gp.getGrid());

    return root;

  }

  private Pixel existingPixel(int i, int t) {
    
    for (Pixel pixel : savedPixel) {
      if(pixel.getxInMap() == i && pixel.getyInMap() == t)
        return pixel;
    }
   
    return null; //default
  }

  private void updateSavedPixel(int pixelSize) {

    for (Pixel pixel : savedPixel) {
      pixel.setSize(pixelSize);
    }

  }


  /**
   * @param pixelSize
   * @param windowWidth
   * @return Anzahl an Pixeln in Y-Richtung
   */
  public int getCountY(int pixelSize, int windowWidth) {
    int pixelCountY = windowWidth / (pixelSize);

    if (pixelCountY * pixelSize != windowWidth)
      pixelCountY++;
    return pixelCountY;
  }

  /**
   * @param pixelSize
   * @param windowHeight
   * @return Anzahl an Pixeln in X-Richtung
   */
  public int getCountX(int pixelSize, int windowHeight) {
    int pixelCountX = windowHeight / (pixelSize);

    if (pixelCountX * pixelSize != windowHeight)
      pixelCountX++;

    return pixelCountX;
  }

  public void setCickOnPixelHandler(EventHandler<MouseEvent> handleClickOnPixel) {
    this.handleClickOnPixel = handleClickOnPixel;
  }

  public void setRectColor(Pixel pixel, Color colorRect) {
    pixel.setColor(colorRect);
    savedPixel.add(pixel);
    // System.out.println("Pixel: " + getPixelCords(pixel)[0] + " " +
    // getPixelCords(pixel)[1] + " clicked!");
  }

  /*
   * public void setPixel(CograRectangle pixel, int x, int y) { this.gridArray[x][y] = pixel;
   * savedRects.add(pixel); // System.out.println("Pixel: " + getPixelCords(pixel)[0] + " " + //
   * getPixelCords(pixel)[1] + " clicked!"); }
   */

  /**
   * Liefert die Koordinaten des übergebenen Pixels im Grid
   * 
   * @param pixel Ein Rechteck (pixel)
   * @return Die Koordinaten des Pixels im Grid als Array
   */
  /*
   * public int[] getPixelCords(Pixel pixel) { int[] pixelCords = new int[2];
   * 
   * for (int i = 0; i < gridArray.length; i++) { for (int t = 0; t < gridArray[i].length; t++) { if
   * (pixel == gridArray[i][t]) pixelCords[0] = i; // x-Wert pixelCords[1] = t; // y-Wert } }
   * 
   * return pixelCords; }
   */

  public void setPixel(int x, int y, Color color) {
    gridArray[x][y].setColor(color);
    savedPixel.add(gridArray[x][y]);
  }

  public Pixel getPixel(int x, int y) {

    for (int i = 0; i < gridArray.length; i++) {
      for (int t = 0; t < gridArray[i].length; t++) {
        if (gridArray[i][t].getX() <= x && gridArray[i][t].getY() <= y
            && (gridArray[i][t].getX() + gridArray[i][t].getSize()) >= x
            && (gridArray[i][t].getY() + gridArray[i][t].getSize()) >= y) {
          return gridArray[i][t];
        }
      }
    }

    return null;

  }

  /**
   * 
   * @param r CograRectangle
   * @param x x-Koordinate
   * @param y y-Koordinate
   * @return true, wenn erfolgreich, false, wenn gescheitert
   */
  /*
   * public boolean setRectange(CograRectangle r, int x, int y) { try { gridArray[x][y] = r; } catch
   * (Exception e) { return false; } return true;
   * 
   * }
   */

  public EventHandler<MouseEvent> getHandleClickOnPixel() {
    return handleClickOnPixel;
  }

  public void setHandleClickOnPixel(EventHandler<MouseEvent> handleClickOnPixel) {
    this.handleClickOnPixel = handleClickOnPixel;
  }

  public static int getOffset() {
    return offset;
  }

  public void addMouseDragEnteredListener(EventHandler<MouseEvent> mouseDragEnteredListener) {
    this.handleMouseDragEnteredListener = mouseDragEnteredListener;

  }

  public void addMouseDragLeaveListener(EventHandler<MouseEvent> mouseDragLeaveListener) {
    this.handleMouseDragLeaveListener = mouseDragLeaveListener;

  }

  public void clearGrid() {
    savedPixel.clear();
  }

}
