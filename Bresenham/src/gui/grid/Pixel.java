package gui.grid;

import javafx.scene.paint.Color;

public class Pixel {
  
  private int x, y, xInMap, yInMap, size;
  private Color color;

  public Pixel(int x, int y, int xInMap, int yInMap, int size, Color color) {
    super();
    this.x = x;
    this.y = y;
    this.xInMap = xInMap;
    this.yInMap = yInMap;
    this.size = size;
    this.color = color;
  }

  public int getxInMap() {
    return xInMap;
  }

  void setxInMap(int xInMap) {
    this.xInMap = xInMap;
  }

  public int getyInMap() {
    return yInMap;
  }

  void setyInMap(int yInMap) {
    this.yInMap = yInMap;
  }

  Color getColor() {
    return color;
  }

  void setColor(Color color) {
    this.color = color;
  }

  int getX() {
    return x;
  }

  void setX(int x) {
    this.x = x;
  }

  int getY() {
    return y;
  }
  

  void setY(int y) {
    this.y = y;
  }

  int getSize() {
    return size;
  }

  void setSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return "Pixel [x=" + x + ", y=" + y + ", xInMap=" + xInMap + ", yInMap=" + yInMap + ", size="
        + size + ", color=" + color + "]";
  }


}
