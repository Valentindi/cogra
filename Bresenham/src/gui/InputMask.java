package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



/**
 * Ist die Eingabemaske für die Konfiguationsdatei. Eingabe: Key und URL.
 */
public class InputMask extends Stage {

  /**
   * Grafische eingabe Maske der Konfigurationsdatei.
   */
  private String sizeX = " ", sizeY = " ";

  public InputMask() {
    super();
    setTitle("Konfigurationsmaske");
  }

  public void run() {

    /**
     * Top Pane
     */
    Label toplabel = new Label("Konfiguration der Zeichenfläche");
    toplabel.setFont(Font.font("Cambria", 28));
    HBox topBox = new HBox(toplabel);
    topBox.setAlignment(Pos.TOP_LEFT);
    topBox.setPadding(new Insets(15, 15, 15, 15));
    topBox.setSpacing(10);

    /**
     * Center Pane
     */
    final TextField sizeXTextField = new TextField();
    sizeXTextField.setMaxWidth(300);
    final TextField sizeYTextFiel = new TextField();
    sizeYTextFiel.setMaxWidth(300);

    sizeYTextFiel.setText("200");
    sizeXTextField.setText("200");
    Label lx = new Label("Breite in Pixel: ");
    Label ly = new Label("Höhe in Pixel");

    VBox centerBox = new VBox(lx, sizeXTextField, ly, sizeYTextFiel);
    centerBox.setAlignment(Pos.TOP_LEFT);
    centerBox.setPadding(new Insets(15, 15, 15, 15));
    centerBox.setSpacing(10);
    

    /**
     * Botom Pane
     */
    Button bok = new Button("Bestätigen");
    HBox botombox = new HBox(bok);
    botombox.setAlignment(Pos.BOTTOM_RIGHT);
    botombox.setPadding(new Insets(15, 15, 15, 15));
    botombox.setSpacing(10);

    bok.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        sizeX = sizeXTextField.getText();
        sizeY = sizeYTextFiel.getText();

        close();
      }
    });

    /**
     * config stage
     */
    BorderPane pane = new BorderPane();
    pane.setTop(topBox);
    pane.setCenter(centerBox);
    pane.setBottom(botombox);
    Scene scene = new Scene(pane, 450, 275);
    setScene(scene);
  }

  int getSizeX() {
    return Integer.parseInt(sizeX);
  }

  int getSizeY() {
    return Integer.parseInt(sizeY);
  }



}
