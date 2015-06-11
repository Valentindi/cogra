package algorithm;

import java.util.Random;

import factories.GreyScaleFactory;
import javafx.scene.paint.Color;

public class exampleLine {

	public static Color[][] run(int beginX, int beginY, int endX, int endY) {
		Color[][] dummyRectGreyScale = new Color[(int) (endX - beginX)][(int) (endY - beginY)];
		Boolean[][] matrix = new Boolean[(int) (endX - beginX)][(int) (endY - beginY)];

		System.out.println("The Line is running!");
		// System.out.println(beginX + "  " + beginY + "  " + endX + "  " +
		// endY);

		for (int i = 0; i < (endX - beginX); i++) {
			for (int j = 0; j < endY - beginY; j++) {
				matrix[i][j] = false;
			}

		}
		matrix[0][0] = true;
		matrix = genMatrix(matrix);
		for (int i = 0; i < (endX - beginX); i++) {
			for (int j = 0; j < endY - beginY; j++) {
				if (matrix[i][j] == true) {

					dummyRectGreyScale[i][j] = Color.rgb(255, 0, 0);
				} else {
					dummyRectGreyScale[i][j] = null;

				}
			}

		}

		return dummyRectGreyScale;
	}

	private static Boolean[][] genMatrix(Boolean[][] matrix) {
		double xy = matrix.length / matrix[0].length;
		int i = 1, j = 1;
		matrix[0][0] = true;
		while ((i < matrix.length) && (j < matrix[i].length)) {
			matrix[i][j]=true;
			if(i/j<(xy+0.5)){
				i++;
				i++;
				j++;
			}else{
				j++;
				i++;
				j++;
			}
			
		}
		return matrix;
	}

}
