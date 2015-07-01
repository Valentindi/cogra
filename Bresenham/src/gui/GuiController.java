package gui;

import factories.DialogFactory;
import gui.grid.GridBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
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

  private int mousePressedX = 0;
  private int mousePressedY = 0;

  public static int sizeInPixelX;
  public static int sizeInPixelY;

  public static String activeAlgorithm = "Dummy";
  private int pixelSize = 1;

  public GuiController(GuiView guiView, GridBuilder gridBuilder) {
    this.guiView = guiView;
    this.gridBuilder = gridBuilder;

    this.guiView.addResizeListener(new ResizeListener());
    this.gridBuilder.setCickOnPixelHandler(new ClickOnPixelHandler());
    this.gridBuilder.setMousePressOnPixelHandler(new MousePressOnPixelHandler());
    this.gridBuilder.addMouseDragEnteredListener(new MouseDragEnteredListener());
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

    InputMask im = new InputMask();
    im.run();
    im.showAndWait();

    sizeInPixelX = im.getSizeX();
    sizeInPixelY = im.getSizeY();

  }

  public void buildGrid() {

    guiView.setGrid(gridBuilder.buildGrid(pixelSize, sizeInPixelX, sizeInPixelY));
  }

  public void incPixelSize() {
    pixelSize++;

    System.out.println("ZoomIn. New PixelSize: " + pixelSize);
    gridBuilder.clearCanvas();
    buildGrid();
  }

  public void decPixelSize() {
    pixelSize--;

    if (pixelSize <= 0)
      pixelSize = 1;

    System.out.println("ZoomOut. New PixelSize: " + pixelSize);
    gridBuilder.clearCanvas();
    buildGrid();
  }

  public void clearMatrix() {

    gridBuilder.clearGrid();
    buildGrid();

  }

  private void drawHelpline(int beginX, int beginY, int endX, int endY, Boolean changeX,
      Boolean changeY) {
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



    Color rectColors[][] = new Color[endX - beginX][endY - beginY];
    switch (activeAlgorithm) {
      case "Dummy":
        rectColors = DummyAlgoithm.run(beginX, beginY, endX, endY);
        break;
      case "Bresenham":

        // Aufruf des der Bresline-Vorbereitungsfunktion mit dem Originalwerten
        bresline(beginXorg, beginYorg, endXorg - 1, endYorg - 1, Color.BLACK);
        break;
      case "vereinfachterBresenham":
        rectColors = vereinfachterBresenham.run(beginX, beginY, endX, endY);
        /*
         * Hey Lina,
         * 
         * ich glaube mittlerweile, das es einfacher ist erstmal den Algorithmus hier in dieser
         * Klasse zu implementieren, da man hier z.B. da hier der GridBuilder gehalten wird, und man
         * direkt auf das Grud zugreifen kann.
         * 
         * Au�erdem sind Jaegers Algorithmen darauf angelegt, das sie nicht bei unbedingt (0,0)
         * beginnen, sondern auch irgendwo in dem Grid. Wenn man mit dem RectColors arbeitet, wie
         * ich es urspr�ngich gedacht habe, dann h�tte man bei das nur auf einem Ausschnitt von
         * (0,0) bis (dx, dx) machen k�nnen. Aber das funktioniert alles nicht so einfach wie ich
         * mir das gedacht habe.
         * 
         * Ich hab mir gestern den Anti-Aliasing-Algorithmus in den Folien mal kurz angeschaut, und
         * ich denke, du brauchst noch eine Funktion wie die bresline (Siehe Folie 2.14), der dem
         * Algorithmus die richtigen Werte �bergibt und aus der dann der eigentliche Algorithmus
         * aufgerufen wird.
         * 
         * Und dann, wenn erstmal alles funktioniert, dann k�nnen wir ja beim Refactoring die
         * Algorithmen in eigene Klassen verschieben.
         * 
         * �brigens, kann man SetzePixel(x,y,f) aus dem Folien, mit changePixelColor(x,y,
         * greyScaleFactory.getGreyScale(1-abs(d)) umsetzen.
         */
        break;

      case "exampleLine":
        exampleLine activeAlgortithm = new exampleLine();
        rectColors = exampleLine.run(beginX, beginY, endX, endY, changeX, changeY);
        break;
      // break;

      default:
        break;
    }
    // Zeichnet rectCOlors in die Matrix und begint bei beginX, beginY
    // colorTheRect(rectColors, beginX, beginY);

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
        bres1(x0, y0, xn, dx, dy, false);
      }

    } else {
      System.out.println("{Anstieg +45 .. 90 .. -45}");
      if (y0 > yn) {
        bresline(xn, yn, x0, y0, Color.BLACK);

      } else {

        bres1(y0, x0, yn, dy, dx, true);

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

  /*
   * private void changePixelColor(int x, int y, Color color) { CograRectangle pixel =
   * gridBuilder.getPixel(x, y); pixel.setFill(color); gridBuilder.setPixel(pixel, x, y);
   * 
   * }
   */
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

  /**
   * Zeichnet rectCOlors
   * 
   * @param colorRect Matrix mit Farben, die in Matrix �bertragen werden sollen
   * @param beginX x-Koordinate oben-links
   * @param beginY y-Koordinate oben-links
   */
  /*
   * public void colorTheRect(Color[][] colorRect, int beginX, int beginY) {
   * 
   * CograRectangle pixel = new CograRectangle(); pixel.setWidth(pixelSize);
   * pixel.setHeight(pixelSize); for (int i = 0; i < colorRect.length; i++) { for (int j = 0; j <
   * colorRect[i].length; j++) { pixel = gridBuilder.getPixel((beginX + i), (beginY + j));
   * 
   * if (colorRect[i][j] != null && colorRect[i][j] == Color.BLACK) {
   * gridBuilder.setRectColor(pixel, colorRect[i][j]); } }
   * 
   * }
   * 
   * }
   */

  /*
   * public void colorTheRectWhite(Color[][] colorRect, int beginX, int beginY) {
   * 
   * CograRectangle pixel = new CograRectangle(); pixel.setWidth(pixelSize);
   * pixel.setHeight(pixelSize); for (int i = 0; i < colorRect.length; i++) { for (int j = 0; j <
   * colorRect[i].length; j++) { pixel = gridBuilder.getPixel((beginX + i), (beginY + j));
   * 
   * if (colorRect[i][j] != null) { gridBuilder.setRectColor(pixel, colorRect[i][j]); } }
   * 
   * }
   * 
   * }
   */

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

        gridBuilder.toBack();
        gridBuilder.setTranslateX(gridBuilder.getTranslateX() + event.getX() - mousePressedX);
        gridBuilder.setTranslateY(gridBuilder.getTranslateY() + event.getY() - mousePressedY);
        gridBuilder.toBack();

        event.consume();

      }
    }

  }

}
