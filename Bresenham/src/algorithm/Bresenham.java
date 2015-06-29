package algorithm;

import java.util.Random;

import factories.DialogFactory;
import factories.GreyScaleFactory;
import javafx.scene.paint.Color;

public class Bresenham {

	public static Color[][] run(int beginX, int beginY, int endX, int endY,
			Boolean change) {

		int beginXorg = beginX;
		int beginYorg = beginY;
		int endXorg = endX;
		int endYorg = endY;

		if (endX < beginX) {
			int foo = endX;
			endX = beginX;
			beginX = foo;
		}

		if (endY < beginY) {
			int foo = endY;
			endY = beginY;
			beginY = foo;
		}

		Color[][] dummyRectGreyScale = new Color[(int) (endX - beginX) + 1][(int) (endY - beginY) + 1];
		System.out.println("The Bresenham is running!");

		for (int i = 0; i < (endX - beginX); i++) {
			for (int j = 0; j < endY - beginY; j++) {
				dummyRectGreyScale[i][j] = Color.WHITE;
			}

		}
		// Implementierung nach Skript von Prof. Jaeger
		dummyRectGreyScale = bresline(dummyRectGreyScale, beginXorg, beginYorg,
				endXorg, endYorg, Color.BLACK);

		// Valentins angepasste Interpretation
		/*
		 * dummyRectGreyScale = valentinsBresenham(dummyRectGreyScale, 0, 0,
		 * (endX - beginX), (endY - beginY), Color.BLACK, change);
		 */
		return dummyRectGreyScale;
	}

	private static Color[][] bresline(Color[][] dummyRectGreyScale, int x0,
			int y0, int xn, int yn, Color black) {

		int dx = xn - x0;
		int dy = yn - y0;

		// System.out.println("BRESLINE");
		System.out.println("abs(dx): " + abs(dx) + " abs(dy): " + abs(dy));

		if (abs(dx) >= abs(dy)) {
			System.out.println("Anstieg  -45 .. 0 .. +45");
			if (x0 > xn) {
				dummyRectGreyScale = bresline(dummyRectGreyScale, xn, yn, x0,
						y0, Color.BLACK);
			} else {
				dummyRectGreyScale = bres1(dummyRectGreyScale, x0, y0, xn, dx,
						dy, false);
			}

		} else {
			System.out.println("{Anstieg +45 .. 90 .. -45}");
			if (y0 > yn) {
				dummyRectGreyScale = bresline(dummyRectGreyScale, xn, yn, x0,
						y0, Color.BLACK);
			} else {
				dummyRectGreyScale = bres1(dummyRectGreyScale, y0, x0, yn, dy,
						dx, true);
			}
		}

		return dummyRectGreyScale;
	}

	private static Color[][] bres1(Color[][] dummyRectGreyScale, int x0,
			int y0, int xn, int dx, int dy, boolean sp) {
		int sw, d, d1, d2, x, y;

		if (dy < 0) {
			sw = -1;
			dy = -dy;

		} else {
			sw = 1;
		}

		d = 2 * dy - dx;
		d1 = 2 * dy;
		d2 = 2 * (dy - dx);
		x = x0;
		y = y0;
		if (!sp) {
			System.out.println("Paint: " + x + " : " + y);

			
			dummyRectGreyScale[x][y] = Color.BLACK;
		} else {
			System.out.println("Paint: " + y + " : " + x);

			dummyRectGreyScale[y][x] = Color.BLACK;
		}

		while (x < (xn - 1)) {
			x = x + 1;
			if (d < 0) {
				System.out.println("d_alt: " + d + "d_neu: " + (d + 1));
				d = d + d1;
			} else {
				y = y + sw;
				System.out.println("y_alt: " + y + "sw: " + sw + "y_neu: "
						+ (y + sw));
				d = d + d2;
				System.out.println("d_alt: " + d + "d2: " + d2 + "d_neu: "
						+ (d + d2));

			}

			if (!sp) {
				System.out.println("Paint: " + x + " : " + y);
				dummyRectGreyScale[x][y] = Color.BLACK;
			} else {
				System.out.println("Paint: " + y + " : " + x);
				dummyRectGreyScale[y][x] = Color.BLACK;
			}
		}

		return dummyRectGreyScale;

	}

	private static int sgn(int number) {
		if (number < 0) {
			return -1;
		}
		return 1;
	}

	private static int abs(int number) {
		if (number < 0) {
			number *= -1;
		}
		return number;
	}

	private static Color[][] invertMatrix(Color[][] dummyRectGreyScale) {
		System.out.println("INVERT");

		Color[][] oldMatrix = dummyRectGreyScale;
		int x = oldMatrix.length;
		int y = oldMatrix[0].length;
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				oldMatrix[i][j] = dummyRectGreyScale[(x - i) - 1][(y - j) - 1];
			}
		}

		return dummyRectGreyScale;
	}

	private static Color[][] valentinsBresenham(Color[][] dummyRectGreyScale,
			int x0, int y0, int xn, int yn, Color black, Boolean change) {
		int dx = abs(xn - x0);
		int sx = sgn(xn - x0);
		int dy = -abs(yn - y0);
		int sy = sgn(yn - y0);

		int x = 0;
		int y = 0;

		int err = dx + dy, e2;

		double ascending = ((double) dx / (double) dy) * 2;
		double actascnding = 0;
		dummyRectGreyScale[x][y] = Color.BLACK;
		while (x < dx) {
			// System.out.println(x + " : " + y);
			dummyRectGreyScale[x][y] = Color.BLACK;

			e2 = 2 * err;
			if (e2 > dy) {
				// System.out.println("e2 > dy " + e2 + " err: " + err);
				err = err + dy;
				x = x + sx;
			}
			if (e2 < dx) {
				// System.out.println("e2 < dx" + e2 + " err: " + err);
				err = err + dx;
				y = y + sy;
			}
		}

		System.out.println(x0 + " : " + xn + " : " + y0 + " : " + yn);
		if (change) {
			dummyRectGreyScale = invertMatrix(dummyRectGreyScale);
		}

		return dummyRectGreyScale;
	}

}

/*
 * // bei Implementation bitte Löschen
 * 
 * beginX = 0; beginY = 0; endX = 1; endY = 1; Color[][] dummyRectGreyScale =
 * new Color[(int) (endX - beginX)][(int) (endY - beginY)]; Random r = new
 * Random(); System.out.println("The Bresenham is running!"); //
 * System.out.println(beginX + "  " + beginY + "  " + endX + "  " + // endY);
 * 
 * for (int i = 0; i < (endX - beginX); i++) { for (int j = 0; j < endY -
 * beginY; j++) { dummyRectGreyScale[i][j] = Color.WHITE; }
 * 
 * }
 * 
 * dummyRectGreyScale = bres1(dummyRectGreyScale, 0, 0, (endX - beginX), (endY -
 * beginY), Color.BLACK); // DialogFactory.ErrorDialog(null,
 * "Noch nicht implementert", null);
 */
