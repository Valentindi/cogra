package gui;

import algorithm.DummyAlgoithm;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin.FakeFocusTextField;

import gui.grid.GridBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GuiController {

  private GuiView guiView;
  private GridBuilder gridBuilder;
  
  private Boolean mouseDown = false;
  private double beginX;
  private double beginY;

  private int pixelSize = 15;

  public GuiController(GuiView guiView, GridBuilder gridBuilder) {
    this.guiView = guiView;
    this.gridBuilder = gridBuilder;

    this.guiView.addZoomInListener(new ZoomInHandler()); //Button Listener ZoomIn
    this.guiView.addZoomOutListener(new ZoomOutHandler());//Button Listener ZoomOut
    this.guiView.addResizeListener(new ResizeListener()); //Fenster  ResizeListener 
    this.gridBuilder.setCickOnPixelHandler(new ClickOnPixelHandler()); //Pixel ClickListener
    this.guiView.addMouseDragEnteredListener(new MouseDragEnteredListener());//Drag Begonnen
    this.guiView.addMouseDragLeaveListener(new MouseDragLeaveListener());
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
      incPixelSize();
    }
  }

  class ZoomOutHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent event) {
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
      final Rectangle pixel = (Rectangle) (event.getTarget());
      gridBuilder.setRectColor(pixel);
    }
  }
  
  
  class MouseDragEnteredListener implements EventHandler<MouseEvent>{

	@Override
	public void handle(MouseEvent event) {
		System.out.println("Mouse Down test");
		if(mouseDown == false){
			mouseDown = true;
			beginX = event.getX();
			beginY = event.getY();
		}
		
		
	}
	  
  }
  
  
  class MouseDragLeaveListener implements EventHandler<MouseEvent>{

	@Override
	public void handle(MouseEvent event) {
		System.out.println("Mouse UP test");
		Rectangle pixel = new Rectangle();
		double foo;
		double endY = event.getY();
		double endX = event.getX();
		if (beginY>endY) {
			foo = beginY;
			beginY = endY;
			endY = foo;
		}
		if (beginX>endX) {
			foo = beginX;
			beginX = endX;
			endX = foo;
		}
		if(mouseDown==true){
			mouseDown = false;
			Color[][] dummyRectColor = DummyAlgoithm.run(beginX, beginY, endX, endY);
			for (int i = 0; i < dummyRectColor.length; i++) {
				for (int j = 0; j < dummyRectColor[i].length; j++) {
					pixel = gridBuilder.getPixel(1, 1);
					pixel.setFill(dummyRectColor[i][j]);
					System.out.println((int)(beginX + i) + "  " + (int)( beginY + j) + "  " + dummyRectColor[i][j].toString());
					gridBuilder.setRectange(pixel, (int)(beginX + i),(int)( beginY + j));
				}
				
			}
		}
		
		
	}
	  
  }


}
