package gui;

import gui.grid.gridBuilder;

public class GuiController {
  
  GuiView guiView;

  public void startGridBuilder(int pixelSize) {
    gridBuilder gb = new gridBuilder();
    guiView.setGrid(gb.buildGrid(pixelSize, guiView.getWindowHeight(), guiView.getWindowWidth()));
  }

  public void setGui(GuiView guiView) {
    this.guiView = guiView;
  }

}