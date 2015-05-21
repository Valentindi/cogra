package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GuiView {
	
	private AnchorPane rootLayout;

	public void initGuiView(Stage primaryStage) {
		
		try {
			System.out.println("TryInit");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiView.class.getResource("GuiFX.fxml"));
			rootLayout = (AnchorPane)  loader.load();
			
			
			System.out.println(rootLayout.toString());
			Scene scene = new Scene(rootLayout);
			System.out.println(scene.toString());
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
