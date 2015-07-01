package gui.grid;


import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridBuilder extends Pane {

  public static int offset = 0;

  private static Pixel[][] gridArray;
  private static ArrayList<Pixel> savedPixel = new ArrayList<Pixel>();
  private static GridPainter gp;
  private EventHandler<MouseEvent> handleClickOnPixel;
  private EventHandler<MouseEvent> handleMouseDragEnteredListener;
  private EventHandler<MouseEvent> handleMousePressOnPixel;

  public Pane buildGrid(int pixelSize, int pixelCountX, int pixelCountY) {
    
    if(!this.getChildren().isEmpty())
      this.getChildren().remove(0);
    
    if(pixelSize > 15)
      offset = 1;
    else
      offset = 0;
    
    gp = new GridPainter(pixelCountX*(pixelSize+offset)-(offset*1), pixelCountY*(pixelSize+offset)-(offset*1));
    this.setStyle("-fx-background-color: black");
  
    this.addEventHandler(MouseEvent.MOUSE_CLICKED, handleClickOnPixel);
    this.addEventHandler(MouseEvent.MOUSE_PRESSED, handleMousePressOnPixel);
    this.addEventHandler(MouseEvent.MOUSE_DRAGGED, handleMouseDragEnteredListener);

    gridArray = new Pixel[pixelCountX][pixelCountY];

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
    
    this.setMaxWidth(pixelCountX*(pixelSize+offset)-(offset*1));
    this.setMaxHeight(pixelCountY*(pixelSize+offset)-(offset*1));

    gp.paint(gridArray);
    this.getChildren().add(gp.getGrid());
    
    return this;
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

  public void addMouseDragEnteredListener(EventHandler<MouseEvent> mouseDragEnteredListener) {
    this.handleMouseDragEnteredListener = mouseDragEnteredListener;

  }

  public void setMousePressOnPixelHandler(EventHandler<MouseEvent> handleMousePressOnPixel) {
    this.handleMousePressOnPixel = handleMousePressOnPixel;

  }

  public void clearGrid() {
    savedPixel.clear();
  }

  public void clearCanvas() {
    gp.clear();
  }

}
