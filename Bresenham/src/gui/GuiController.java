package gui;

import factories.DialogFactory;
import factories.GreyScaleFactory;
import gui.grid.GridBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import algorithm.DummyAlgoithm;

public class GuiController {


  private GuiView guiView;
  private GridBuilder gridBuilder;

  private int beginX = -1;
  private int beginY = -1;

  private int mousePressedX = 0;
  private int mousePressedY = 0;

  public static int sizeInPixelX;
  public static int sizeInPixelY;

  public static String activeAlgorithm = "Dummy";
  private int pixelSize = 1;
  private Line helpLine;

  public GuiController(GuiView guiView, GridBuilder gridBuilder) {
    this.guiView = guiView;
    this.gridBuilder = gridBuilder;

    this.guiView.addResizeListener(new ResizeListener());
    this.gridBuilder.setCickOnPixelHandler(new ClickOnPixelHandler());
    this.gridBuilder.setMouseReleasedHandler(new MouseReleasedHandler());
    this.gridBuilder.setMousePressOnPixelHandler(new MousePressOnPixelHandler());
    this.gridBuilder.addMouseDragEnteredListener(new MouseDragEnteredListener());
    this.gridBuilder.setScrollHandler(new ScrollHandler());
    // Begonnen
    // this.gridBuilder
    // .addMouseDragLeaveListener(new MouseDragLeaveListener());
    this.guiView.miABresenham.setOnAction(new SetBresenhamHandler());
    this.guiView.miAvereinfB.setOnAction(new SetVereinfHandler());
    this.guiView.miZPlus.setOnAction(new ZoomInHandler());
    this.guiView.miZMinus.setOnAction(new ZoomOutHandler());
    this.guiView.mbZPlus.setOnAction(new ZoomInHandler());
    this.guiView.mbZMinus.setOnAction(new ZoomOutHandler());
    this.guiView.miAexamplLine.setOnAction(new SetExampleLineHandler());
    this.guiView.miClear.setOnAction(new ClearHandler());

    InputMask im = new InputMask();
    im.run();
    im.showAndWait();

    sizeInPixelX = im.getSizeX();
    sizeInPixelY = im.getSizeY();

  }

  /**
   * Baut das Grid
   */
  public void buildGrid() {
    guiView.setGrid(gridBuilder.buildGrid(pixelSize, sizeInPixelX, sizeInPixelY));
    guiView.updateMenu();
  }

  /**
   * Zoom rein
   */
  public void incPixelSize() {
    if (pixelSize != 35 || ((sizeInPixelX + sizeInPixelY) / 2) <= 60) {
      pixelSize++;

      System.out.println("ZoomIn. New PixelSize: " + pixelSize);
      gridBuilder.clearCanvas();
      gridBuilder.getChildren().remove(helpLine);
      buildGrid();

      gridBuilder
          .setTranslateX(gridBuilder.getTranslateX()
              - (((sizeInPixelX * (pixelSize + GridBuilder.offset)) - (sizeInPixelX * (pixelSize - 1 + GridBuilder.offset))) / 2));
      gridBuilder
          .setTranslateY(gridBuilder.getTranslateY()
              - (((sizeInPixelY * (pixelSize + GridBuilder.offset)) - (sizeInPixelY * (pixelSize - 1 + GridBuilder.offset))) / 2));

      System.out.println(gridBuilder.getTranslateX());
      System.out.println(gridBuilder.getTranslateY());

    }
  }

  /**
   * Zoom raus
   */
  public void decPixelSize() {
    if (pixelSize != 1) {
      pixelSize--;

      System.out.println("ZoomOut. New PixelSize: " + pixelSize);
      gridBuilder.clearCanvas();
      gridBuilder.getChildren().remove(helpLine);
      buildGrid();

      gridBuilder
          .setTranslateX(gridBuilder.getTranslateX()
              - (((sizeInPixelX * (pixelSize + GridBuilder.offset)) - (sizeInPixelX * (pixelSize + 1 + GridBuilder.offset))) / 2));
      gridBuilder
          .setTranslateY(gridBuilder.getTranslateY()
              - (((sizeInPixelY * (pixelSize + GridBuilder.offset)) - (sizeInPixelY * (pixelSize + 1 + GridBuilder.offset))) / 2));
    }
  }

  public void clearMatrix() {

    gridBuilder.clearGrid();
    buildGrid();

  }
  
  private void drawHelpline(int beginX, int beginY, int endX, int endY, Boolean changeX,
      Boolean changeY) {
    if (guiView.miShowLine.isSelected()) {
      helpLine = new Line();
      
      helpLine.setStartX(beginX * (pixelSize+GridBuilder.offset) + pixelSize/2);
      helpLine.setEndX(endX * (pixelSize+GridBuilder.offset) - pixelSize/2);

      helpLine.setStartY(beginY * (pixelSize+GridBuilder.offset) + pixelSize/2);
      helpLine.setEndY(endY * (pixelSize+GridBuilder.offset) - pixelSize/2);

      helpLine.setFill(Color.RED);
      helpLine.setStroke(Color.RED);

      gridBuilder.getChildren().add(helpLine);
    }

  }

  /**
   * 
   * Ruft die einzelnen Algorithmen auf
   * 
   * @param beginX Koordinate von dem Beginn des Algorithmus (oben links)
   * @param beginY Koordinate von dem Beginn des Algorithmus (oben links)
   * @param beginXorg Koordinate von ersten Klick (Beginn der Linie). Es empfiehlt sich damit zu
   *        arbeiten!
   * @param beginYorg Koordinate von ersten Klick (Beginn der Linie). Es empfiehlt sich damit zu
   *        arbeiten!
   * @param endX Koordinate von dem Beginn des Algorithmus (unten rechts)
   * @param endY Koordinate von dem Beginn des Algorithmus (unten rechts)
   * @param endXorg Koordinate von zweiten Klick (Ende der Linie). Es empfiehlt sich damit zu
   *        arbeiten!
   * @param endYorg Koordinate von zweiten Klick (Ende der Linie). Es empfiehlt sich damit zu
   *        arbeiten!
   * @param change true, wenn Algorithmus von unten lins nach oben rechts l�uft
   * @param changeX true, wenn endX und beginX vertauscht wurden
   * @param changeY true, wenn endY und beginY vertauscht wurden
   */
  private void runAlgs(int beginX, int beginY, int beginXorg, int beginYorg, int endX, int endY,
      int endXorg, int endYorg, Boolean change, Boolean changeX, Boolean changeY) {
    switch (activeAlgorithm) {
      case "Dummy":
        DummyAlgoithm.run(beginX, beginY, endX, endY);
        break;
      case "Bresenham":

        // Aufruf des der Bresline-Vorbereitungsfunktion mit dem
        // Originalwerten
        bresline(beginXorg, beginYorg, endXorg - 1, endYorg - 1, Color.BLACK);
        break;
      case "vereinfachterBresenham":
        bresline(beginXorg, beginYorg, endXorg - 1, endYorg - 1, Color.BLACK);
        break;

    }
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
        if (activeAlgorithm == "Bresenham") {
          bres1(x0, y0, xn, dx, dy, false);
        } else {
          wuLine(x0, y0, xn, dx, dy, false);
        }
      }

    } else {
      System.out.println("{Anstieg +45 .. 90 .. -45}");
      if (y0 > yn) {
        bresline(xn, yn, x0, y0, Color.BLACK);

      } else {
        if (activeAlgorithm == "Bresenham") {
          bres1(y0, x0, yn, dy, dx, true);
        } else {
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

    System.out.println("Bresham: " + x0 + " : " + y0 + " : " + xn + " : " + dx + " : " + dy + " : "
        + sp);
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
      gridBuilder.setPixel(x, y, Color.BLACK);

    } else {
      gridBuilder.setPixel(y, x, Color.BLACK);

    }

    while (x < xn) {

      x = x + 1;
      if (d < 0) {
        System.out.println("d_alt: " + d + "d_neu: " + (d + 1));
        d = d + d1;
      } else {
        y = y + sw;
        System.out.println("y_alt: " + y + "sw: " + sw + "y_neu: " + (y + sw));
        d = d + d2;
        System.out.println("d_alt: " + d + "d2: " + d2 + "d_neu: " + (d + d2));

      }

      if (!sp) {
        gridBuilder.setPixel(x, y, Color.BLACK);

      } else {
        gridBuilder.setPixel(y, x, Color.BLACK);

      }
    }

    buildGrid();
  }

  private void wuLine(int x0, int y0, int xn, int dx, int dy, boolean sp) {
    int x = x0;
    int y = y0;
    double d = 0;
    double incrd = 1 - (((double) dy) / ((double) dx));
    System.out
        .println("Wu: " + x0 + " : " + y0 + " : " + xn + " : " + dx + " : " + dy + " : " + sp);

    gridBuilder.setPixel(x, y, GreyScaleFactory.getGreyScale(0));

    for (int i = 0; i < dx; i++) {
      x++;
      y++;
      d = d + incrd;
      gridBuilder.setPixel(x, y, GreyScaleFactory.getGreyScale(abs(d)));
      if (d <= 0) {

        gridBuilder.setPixel(x, y + 1, GreyScaleFactory.getGreyScale(1 - abs(d)));

      } else {
        y--;
        d = d - 1;

        gridBuilder.setPixel(x, y, GreyScaleFactory.getGreyScale(abs(d)));

      }

    }

    buildGrid();

  }

  /**
   * Gibt Positiven wert zur�ck
   * 
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
   * 
   * @param number
   * @return
   */
  private static double abs(double number) {
    if (number < 0) {
      number *= -1;
    }
    return number;
  }


  public void centerGrid() {
    gridBuilder.setTranslateX((guiView.getWindowWidth() / 2)
        - ((sizeInPixelX * pixelSize + GridBuilder.offset) / 2));
    gridBuilder.setTranslateY((guiView.getWindowHeight() / 2)
        - ((sizeInPixelY * pixelSize + GridBuilder.offset) / 2));
  }

  public void init() {
    pixelSize = (int) ((guiView.getWindowHeight() / sizeInPixelY) * 0.9);

    if (pixelSize > 35 && ((sizeInPixelX + sizeInPixelY) / 2) > 60) {
      pixelSize = 35;
    }

    buildGrid();
    centerGrid();
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
      guiView.menuAlgorithmen.setText("Active Algorithm: vereinfachter Bresenham");
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

    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
        Number newSceneWidth) {
      centerGrid();
      buildGrid(); // Wenn neue Größe --> Grid neu bauen
    }
  }

  class MousePressOnPixelHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
      mousePressedX = (int) event.getX();
      mousePressedY = (int) event.getY();

    }
  }

  class MouseReleasedHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
      guiView.scene.setCursor(Cursor.DEFAULT);
    }
  }

  class ClickOnPixelHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

      if (event.getButton() == MouseButton.PRIMARY) {
        Boolean changeX = false;
        Boolean changeY = false;
        Boolean change = false;

        if (beginX > -1 && beginY > -1) {
          int endX, endY;
          try {
            endX = gridBuilder.getPixel((int) event.getX(), (int) event.getY()).getxInMap();
            endY = gridBuilder.getPixel((int) event.getX(), (int) event.getY()).getyInMap();

          } catch (Exception e) {
            DialogFactory.ErrorDialog("Error", "Drag wurde festgestellt",
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

          runAlgs(beginX, beginY, beginXorg, beginYorg, endX, endY, endXorg, endYorg, change,
              changeX, changeY);
          drawHelpline(beginXorg, beginYorg, endXorg, endYorg, changeX, changeY);

          guiView.status.setText("Klick für Zeichnen");
          beginX = Integer.MIN_VALUE;
          beginY = Integer.MIN_VALUE;
        } else {

          try {
            beginX = gridBuilder.getPixel((int) event.getX(), (int) event.getY()).getxInMap();
            beginY = gridBuilder.getPixel((int) event.getX(), (int) event.getY()).getyInMap();

            System.out.println("BeginX: " + beginX);
            System.out.println("BeginY: " + beginY);

          } catch (Exception e) {
            DialogFactory.ErrorDialog("Error",
                "Drag wurde festgestellt, Strukturen werden durch 2 Klicks erstellt",
                "Oder es wurde auf den Rand eines Pixels gedrückt!");
          }
          guiView.status.setText("Kick für Fertigstellen");
        }

      }
    }

  }

  class MouseDragEnteredListener implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {

      if (event.getButton() == MouseButton.SECONDARY) {

        gridBuilder.setTranslateX(gridBuilder.getTranslateX() + event.getX() - mousePressedX);
        gridBuilder.setTranslateY(gridBuilder.getTranslateY() + event.getY() - mousePressedY);
        gridBuilder.toBack();
        guiView.updateMenu();

        guiView.scene.setCursor(Cursor.CLOSED_HAND);

        event.consume();

      }
    }

  }

  class ScrollHandler implements EventHandler<ScrollEvent> {

    @Override
    public void handle(ScrollEvent event) {

      double notches = event.getDeltaY();
      System.out.println("ScrollEvent! " + notches);

      if (notches > 0)
        incPixelSize();
      else
        decPixelSize();

    }

  }

}
