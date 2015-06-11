package algorithm;

import java.util.Random;

import factories.DialogFactory;
import javafx.scene.paint.Color;

public class vereinfachterBresenham {

	public static Color[][] run(int beginX, int beginY, int endX,
			int endY) {
		beginX=0;
		beginY=0;
		endX=1;
		endY=1;
		Color[][] dummyRectGreyScale = new Color[(int) (endX-beginX)][(int) (endY-beginY)];
		Random r = new Random();
		System.out.println("The Dummy is running!");
		//System.out.println(beginX + "  " + beginY + "  " + endX + "  " + endY);
		
		for (int i = 0 ; i < (endX - beginX); i++) {
			for (int j = 0; j < endY-beginY; j++) {
				dummyRectGreyScale[i][j] = Color.WHITE;
			}
			
		}
		DialogFactory.ErrorDialog(null, "Noch nicht implementert", null);

		return dummyRectGreyScale;
	}
	

}
