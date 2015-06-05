package gui;

import gui.grid.GridBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GuiController {

  private GuiView guiView;
  private GridBuilder gridBuilder;

  private int pixelSize = 15;

  public GuiController(GuiView guiView, GridBuilder gridBuilder) {
    this.guiView = guiView;
    this.gridBuilder = gridBuilder;

    this.guiView.addZoomInListener(new ZoomInHandler()); //Button Listener ZoomIn
    this.guiView.addZoomOutListener(new ZoomOutHandler());//Button Listener ZoomOut
    this.guiView.addResizeListener(new ResizeListener()); //Fenster  ResizeListener 
    this.gridBuilder.setCickOnPixelHandler(new ClickOnPixelHandler()); //Pixel ClickListener
    
  }


  public void buildGrid() {
    guiView.setGrid(gridBuilder.buildGrid(pixelSize, guiView.getWindowHeight(),
        guiView.getWindowWidth()));
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

  class ZoomInHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
      // TODO Auto-generated method stub
      incPixelSize();
    }
  }

  class ZoomOutHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
      // TODO Auto-generated method stub
      decPixelSize();
    }
  }
  
  class ResizeListener implements ChangeListener<Number> {

    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
      buildGrid(); //Wenn neue Größe --> Grid neu bauen
    }
  }

  class ClickOnPixelHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
      // TODO Auto-generated method stub
      final Rectangle pixel = (Rectangle) (event.getTarget());
      gridBuilder.setRectColor(pixel);
    }
  }


}
