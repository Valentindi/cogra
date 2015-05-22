package gui.grid;
 
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
 
public class gridBuilder {
    
    Rectangle[][] gridArray;
    static final int offset = 1;
  
    public GridPane buildGrid (int  pixelSize, int windowHeight, int windowWidth) {
      
        int pixelCountX = getCountX(pixelSize, windowHeight);
        
        int pixelCountY = getCountY(pixelSize, windowWidth);

        
        GridPane root = new GridPane();
        gridArray = new Rectangle[pixelCountY][pixelCountX];
        
        root.getColumnConstraints().add(new ColumnConstraints(0)); // column 1 is 0 wide
        
        for(int i = 0; i < gridArray.length; i++)
        {
          for(int t = 0; t < gridArray[i].length; t++)
          {
            gridArray[i][t] = new Rectangle();
            gridArray[i][t].setWidth(pixelSize);
            gridArray[i][t].setHeight(pixelSize);
            
            root.getColumnConstraints().add(new ColumnConstraints(pixelSize+offset)); //next column  is 30 wide
            GridPane.setConstraints(gridArray[i][t], i, t);
            root.getChildren().add(gridArray[i][t]);
          }
          root.getRowConstraints().add(new RowConstraints(pixelSize+offset));
        }
        
        return root;

    }

    private int getCountY(int pixelSize, int windowWidth) {
      int pixelCountY = windowWidth / (pixelSize);
      
      if (pixelCountY * pixelSize != windowWidth)
        pixelCountY++;
      return pixelCountY;
    }

    private int getCountX(int pixelSize, int windowHeight) {
      int pixelCountX = getCountY(pixelSize, windowHeight);
      
      return pixelCountX;
    }

}