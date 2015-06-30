package gui.grid;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class GridPainter {
  int windowHeight;
  int windowWidth;
  Pane background;
  final Canvas canvas;
  GraphicsContext gc;

  GridPainter(int windowHeight, int windowWidth) {
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;

    background = new Pane();
    background.setStyle("-fx-background-color: black");

    canvas = new Canvas(windowWidth, windowHeight);
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
  
}
