package factories;

import javafx.scene.paint.Color;

public class GreyScaleFactory {
	/**
	 * 
	 * @param value Wert zwischen 0 und 1 für Graustufe ( 0 Schwarz, 1 Weiß)
	 * @return null falls falscher Wert; Color die Berechnet wurde
	 * @author Valentin
	 */
	public static Color getGreyScale(double value){
		if(value>1 || value <0){
			return null;
		}
		
		int colRGB = (int) (value * 256);
		Color color = Color.rgb(colRGB, colRGB, colRGB);
		
		return color;
		
	}

}
