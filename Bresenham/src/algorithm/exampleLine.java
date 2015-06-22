package algorithm;

import java.util.Random;

import factories.GreyScaleFactory;
import javafx.scene.paint.Color;

public class exampleLine {

	
	public Color[][] dummyRectGreyScale;

	public  Color[][] run(int beginX, int beginY, int endX, int endY) {
		Boolean[][] matrix = new Boolean[(int) (endX - beginX)][(int) (endY - beginY)];
		dummyRectGreyScale = new Color[(int) (endX - beginX)][(int) (endY - beginY)];
		System.out.println("The Line is running!");
		// System.out.println(beginX + "  " + beginY + "  " + endX + "  " +
		// endY);
		/*
		 * for (int i = 0; i < (endX - beginX); i++) { for (int j = 0; j < endY
		 * - beginY; j++) { matrix[i][j] = false; }
		 * 
		 * } matrix[0][0] = true; matrix = genMatrix(matrix); for (int i = 0; i
		 * < (endX - beginX); i++) { for (int j = 0; j < endY - beginY; j++) {
		 * if (matrix[i][j] == true) {
		 * 
		 * dummyRectGreyScale[i][j] = Color.rgb(255, 0, 0); } else {
		 * dummyRectGreyScale[i][j] = null;
		 * 
		 * } }
		 * 
		 * }
		 */
		System.out.println(matrix.length +" : " + matrix[0].length);
		double direction = 0;
		
				
		System.out.println(direction);
		//dummyRectGreyScale = nextStep(dummyRectGreyScale, direction, 1, 1);
		
		dummyRectGreyScale = nichtRekursiv();
		
		return dummyRectGreyScale;
	}




	private Color[][] nichtRekursiv() {
		// TODO Auto-generated method stub
		return null;
	}




	private Color[][] nextStep(Color[][] dummyRectGreyScale,
			double direction, int i, int j) {
		System.out.println("Iteration: " + (i+j-1) + " i: " + i + " j: " + j + " length: " + dummyRectGreyScale.length + " width: " + dummyRectGreyScale[0].length);
		if(i>=dummyRectGreyScale.length||j>=dummyRectGreyScale[0].length){
			System.out.println("Returning");
			return dummyRectGreyScale;
		}
		if(direction<(i/j)){
			System.out.println(i + " : " +j);
			System.out.println("nach rechts");
			i++;
			dummyRectGreyScale[i-1][j-1]=GreyScaleFactory.getGreyScale(1-(direction-(i/j)));
			dummyRectGreyScale= nextStep(dummyRectGreyScale, direction, i, j);
		}else{
			System.out.println(i + " : " +j);
			System.out.println("Nach unten");
			j++;
			dummyRectGreyScale[i-1][j-1]=GreyScaleFactory.getGreyScale(1);

			dummyRectGreyScale= nextStep(dummyRectGreyScale, direction, i, j);

		}
		return dummyRectGreyScale;
		
	}




	private static Boolean[][] genMatrix(Boolean[][] matrix) {
		double xy = matrix.length / matrix[0].length;
		int i = 1, j = 1;
		matrix[0][0] = true;
		while ((i < matrix.length) && (j < matrix[i].length)) {
			matrix[i][j] = true;
			if (i / j < (xy + 0.5)) {
				i++;
				// i++;
				// j++;
			} else {
				j++;
				// i++;
				// j++;
			}

		}
		return matrix;
	}

}
