package gui.grid;
 
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
 
public class gridBuilder {
    
    Rectangle[][] gridArray;
  
    public GridPane buildGrid (int  pixelSize) {
      
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        
        GridPane root = new GridPane();
        gridArray = new Rectangle[100][100];
        
        root.getColumnConstraints().add(new ColumnConstraints(0)); // column 1 is 0 wide
        
        for(int i = 0; i < gridArray.length; i++)
        {
          for(int t = 0; t < gridArray[i].length; t++)
          {
            gridArray[i][t] = new Rectangle();
            gridArray[i][t].setWidth(pixelSize);
            gridArray[i][t].setHeight(pixelSize);
            
            root.getColumnConstraints().add(new ColumnConstraints(pixelSize+1)); //next column  is 30 wide
            GridPane.setConstraints(gridArray[i][t], i, t);
            root.getChildren().add(gridArray[i][t]);
          }
          root.getRowConstraints().add(new RowConstraints(pixelSize+1));
        }
        
        return root;

    }

}