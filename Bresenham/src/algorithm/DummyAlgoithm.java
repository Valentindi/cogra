package algorithm;

import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import factories.GreyScaleFactory;
import gui.grid.GridBuilder;

public class DummyAlgoithm {
	/**
	 * TestAlgorithmus
	 * @param beginX
	 * @param beginY
	 * @param endX
	 * @param endY
	 * @return 
	 */
	public static Color[][] run(int beginX, int beginY, int endX, int endY){
		Color[][] dummyRectGreyScale = new Color[(int) (endX-beginX)][(int) (endY-beginY)];
		Random r = new Random();
		System.out.println("The Dummy is running!");
		System.out.println(beginX + "  " + beginY + "  " + endX + "  " + endY);
		
		for (int i = 0 ; i < (endX - beginX); i++) {
			for (int j = 0; j < endY-beginY; j++) {
				dummyRectGreyScale[i][j] = GreyScaleFactory.getGreyScale(r.nextDouble());
			}
			
		}
		
		return dummyRectGreyScale;
	}
	
}
