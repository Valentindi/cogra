package algorithm;

import javafx.scene.paint.Color;

public class XiaolinWuLineAlgorithm {
	
//	public static Color[][] run(int beginX, int beginY, int endX, int endY) {
//		Color[][] dummyRectGreyScale = new Color[(int) (endX - beginX) + 1][(int) (endY - beginY) + 1];
//		System.out.println("The Bresenham is running!");
//
//		for (int i = 0; i < (endX - beginX); i++) {
//			for (int j = 0; j < endY - beginY; j++) {
//				dummyRectGreyScale[i][j] = Color.WHITE;
//			}
//
//
//		}
//		return dummyRectGreyScale;
//	
//
//	}
//	
//	
//	private double ipart(double x){
//		int iPart = (int) x;
//		return iPart;
//	}
//	
//	private double round(int x){
//		return ipart(x + 0.5);
//	}
//	
//	private double fpart(double x){
//		if (x < 0){
//			double fPart = 1 - (x - Math.floor(x));
//			return fPart;
//		}else{
//			double fPart = x - Math.floor(x);
//			return fPart;
//		}
//	}
//	
//	private double rfpart(double x){
//		double rfPart = 1 - fpart(x);
//		return rfPart;
//	}
//	
//	
//	
//	private void wuLine(int x0, int y0, int xn, int yn){
//		
//		if (Math.abs(yn-y0) > Math.abs(xn-x0)){
//			
//			int temp = x0;
//			x0 = y0;
//			y0 = temp;
//			int temp1 = xn;
//			xn = yn;
//			yn = temp1;
//			
//			
//		}
//		
//		if (x0 > xn){
//			
//			int temp = x0;
//			x0 = xn;
//			xn = temp;
//			int temp1 = y0;
//			y0 = yn;
//			yn = temp1;
//					
//		}
//		
//		int dy = yn - y0;
//		int dx = xn - x0;
//		
//		double gradient = dy/dx;
//		
//		double xend = round(x0);
//		double yend = y0 + gradient * (xend - x0);
//		double xgap = rfpart(x0 + 0.5);
//		double xpxl1 = xend;
//		double ypxl1 = ipart(yend);
//		
//		if (Math.abs(yn-y0) > Math.abs(xn-x0)){
//			changePixelColor(ypxl1, xpxl1, )
//		}
		
		
//}
//	
//		
//		
//		return dummyRectGreyScale;
//		
//	}
	
}
