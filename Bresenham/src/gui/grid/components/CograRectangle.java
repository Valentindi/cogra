package gui.grid.components;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class CograRectangle extends Rectangle implements EventHandler<MouseEvent>{
	
	
	
	private int posX;
	private int posY;
	


	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

}
