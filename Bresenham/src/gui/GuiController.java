package gui;

import gui.grid.GridBuilder;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GuiController {
  
  private GuiView guiView;
  private GridBuilder gridBuilder;
  
  private int pixelSize = 15;

  public GuiController(GuiView guiView, GridBuilder gridBuilder) {
    this.guiView = guiView;
    this.gridBuilder = gridBuilder;
    
    this.guiView.addZoomInListener(new ZoomInHandler());
    this.guiView.addZoomOutListener(new ZoomOutHandler());
  }
   

  public void buildGrid() {
    guiView.setGrid(gridBuilder.buildGrid(pixelSize, guiView.getWindowHeight(), guiView.getWindowWidth()));
  }
  
  public void incPixelSize() {
    if(pixelSize < 5)
      pixelSize = 5;
    else
      pixelSize = pixelSize + 5;
    
     System.out.println("ZoomIn. New PixelSize: " + pixelSize);
     buildGrid();
  }
  public void decPixelSize() {
    if(pixelSize <= 5)
      pixelSize = 3; 
    else
      pixelSize = pixelSize - 5;
    
    System.out.println("ZoomOut. New PixelSize: " + pixelSize);
    buildGrid();
  }

  class ZoomInHandler implements EventHandler<MouseEvent>{

    @Override
    public void handle(MouseEvent event) {
      // TODO Auto-generated method stub
      incPixelSize();
      
    }
  }
  
  class ZoomOutHandler implements EventHandler<MouseEvent>{

    @Override
    public void handle(MouseEvent event) {
      // TODO Auto-generated method stub
      decPixelSize();
    }
  }
  

}
