package factories;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class DialogFactory {
	
	public static boolean acceptDialog(String titleText, String headerText,
			String contentText) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titleText);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void ErrorDialog(String titleText, String headerText,
			String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titleText);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		
		alert.show();
	}

}
