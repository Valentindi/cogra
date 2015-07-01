package gui.grid;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GridPainter {

  private Pane background;
  private Canvas canvas;
  private GraphicsContext gc;
  private int sizeX, sizeY;

  GridPainter(int sizeX, int sizeY) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;

    background = new Pane();
    background.setStyle("-fx-background-color: #f9f9f9");
    
    background.setPrefWidth(sizeX);
    background.setPrefHeight(sizeY);

    canvas = new Canvas(sizeX, sizeY);
    gc = canvas.getGraphicsContext2D();

    background.getChildren().add(canvas);
  }

  void paint(Pixel[][] gridArray) {
    for (int x = 0; x < gridArray.length; x++) {
      for (int y = 0; y < gridArray[x].length; y++) {
        paint(gridArray[x][y]);
      }
    }
  }

  void paint(Pixel pixel) {
    gc.setFill(pixel.getColor());
    gc.fillRect(pixel.getX(), pixel.getY(), pixel.getSize(), pixel.getSize());
  }

  void paint(ArrayList<Pixel> savedPixel) {
    for(Pixel pixel : savedPixel) {
      paint(pixel);
    }
  }

  Pane getGrid() {
    return background;
  }

  public void clear() {
    gc.clearRect(0, 0, sizeX, sizeY);
  }
  
}
