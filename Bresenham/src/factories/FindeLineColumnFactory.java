package factories;

public class FindeLineColumnFactory {
	/**
	 * gibt die Zeile oder Spalte zurück, in die geklickt wurde
	 */
	public static int getLineORColumn(Double pos, int PixelSize){
		int foo = (int) (pos - pos%PixelSize);
		int number = (int) (foo / PixelSize);
		System.out.println("Pos: " + pos + "(" + foo +")" + " / " + PixelSize + " = " + number);
		return number;
	}
}
