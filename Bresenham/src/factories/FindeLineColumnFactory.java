package factories;

public class FindeLineColumnFactory {
	/**
	 * gibt die Zeile oder Spalte zurück, in die geklickt wurde
	 */
	public static int getLineORColumn(Double pos, int PixelSize){
		int number = (int) Math.round(pos-0.5);
		number = (int) (number / PixelSize);
		System.out.println("Aktuelle Position: " + pos + " PixelSize: " + PixelSize + " result: " + number);
		return number;
	}
}
