

import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * This creates the layout for an individual statistic box
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticBox
{
    private Label myLabel = new Label("(//ω//)\n(⁄ ⁄•⁄ω⁄•⁄ ⁄)\n(⁄ ⁄>⁄ ▽ ⁄<⁄ ⁄)");
    
    private GridPane statBox = new GridPane();

    /**
     * The Statistic box is created
     */
    public StatisticBox()
    {      
        statBox.setPadding(new Insets(10, 10, 10, 10));
        //statBox.setGridLinesVisible(true);    // uncomment to show gridlines
               

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20);
        statBox.getColumnConstraints().addAll(col1,col2,col3);
        
        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        statBox.getRowConstraints().add(row);       

        Button leftArrow = new Button("<");        
        VBox statsDisplay = new VBox();        
        statsDisplay.setStyle("-fx-background-color: #FFFFFF");        
        Button rightArrow = new Button(">");
               
        leftArrow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        statBox.setFillHeight(leftArrow, true);
        statBox.setFillWidth(leftArrow, true);
        
        rightArrow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        statBox.setFillHeight(rightArrow, true);
        statBox.setFillWidth(rightArrow, true);
        
        statsDisplay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        statBox.setFillHeight(statsDisplay, true);
        statBox.setFillWidth(statsDisplay, true);
        
        statsDisplay.setAlignment(Pos.CENTER);
        statsDisplay.getChildren().add(myLabel);

        statBox.add(leftArrow, 0, 0);
        statBox.add(statsDisplay, 1, 0);
        statBox.add(rightArrow, 2, 0);
        
    }
    
    /**
     * This returns the statistic box
     */
    public GridPane createStatBox()
    {
        return statBox;
    }  
}
