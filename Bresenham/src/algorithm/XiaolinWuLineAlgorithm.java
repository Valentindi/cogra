package algorithm;

import javafx.scene.paint.Color;

public class XiaolinWuLineAlgorithm {
	
	public static Color[][] run(int beginX, int beginY, int endX, int endY) {
		Color[][] dummyRectGreyScale = new Color[(int) (endX - beginX) + 1][(int) (endY - beginY) + 1];
		System.out.println("The Bresenham is running!");

		for (int i = 0; i < (endX - beginX); i++) {
			for (int j = 0; j < endY - beginY; j++) {
				dummyRectGreyScale[i][j] = Color.WHITE;
			}


		}
		return dummyRectGreyScale;
	

	}
	
	
	private static Color[][] wuLine(Color[][] dummyRectGreyScale,
			int x0, int y0, int xn, int yn, Color black) {
		
		double x1 = (double) x0;
		double x2 = (double) xn;
		
		double y1 = (double) y0;
		double y2 = (double) yn;
		
		long iPart = (long) (x2+0.5);
		double fPart = x2 - iPart;
		
		int grad;
		
		int xd = (xn - x0);
		int yd = (yn - y0);
		
		if (Math.abs(xd) > Math.abs(yd)){
			
			int temp = x0;
			x0 = y0;
			y0 = temp;
			int temp1 = xn;
			xn = yn;
			yn = temp1;
			xd = (xn - x0);
			yd = (yn - y0);
			
			
		}
		
		if (x0 > y0){
			
			int temp = x0;
			x0 = xn;
			xn = temp;
			int temp1 = y0;
			y0 = yn;
			yn = temp1;
			xd = (xn - x0);
			yd = (yn - y0);
				
			
		}
		
		grad = yd/xd;
		
		
		//int xend = iPart;
		
		return dummyRectGreyScale;
		
	}
	
}
