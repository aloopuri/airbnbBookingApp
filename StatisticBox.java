import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.scene.input.*;

import java.util.List;
import java.util.ArrayList;

/**
 * This creates the layout for an individual statistic box.
 * You can click through different statistics using the arrows on the sides
 * of the boxes.
 * All objects of the class share the same data.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticBox
{
    private static List<DataDisplay> data = new ArrayList();
    
    private GridPane statBox = new GridPane();
    private GridPane statDisplay;
    private Button leftArrow;
    private Button rightArrow;

    /**
     * The Statistic box is created
     */
    public StatisticBox(List<DataDisplay> data)
    {      
        this.data = data;
        statBox.setPadding(new Insets(10, 10, 10, 10));
        //statBox.setGridLinesVisible(true);    // uncomment to show gridlines

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(10);        
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(80);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(10);
        statBox.getColumnConstraints().addAll(col1,col2,col3);
        
        RowConstraints row = new RowConstraints();
        row.setVgrow(Priority.ALWAYS);
        statBox.getRowConstraints().add(row);       

        leftArrow = new Button("<");
        rightArrow = new Button(">");
        
        statDisplay = new GridPane();
        addUndisplayedStatistic(); 
               
        leftArrow.setOnAction(this::previousStat);
        rightArrow.setOnAction(this::nextStat);
               
        leftArrow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        statBox.setFillHeight(leftArrow, true);
        statBox.setFillWidth(leftArrow, true);
        
        rightArrow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        statBox.setFillHeight(rightArrow, true);
        statBox.setFillWidth(rightArrow, true);
        
        statBox.setFillHeight(statDisplay, true);
        statBox.setFillWidth(statDisplay, true);
        
        statBox.add(leftArrow, 0, 0);
        statBox.add(statDisplay, 1, 0);
        statBox.add(rightArrow, 2, 0);        
    }
        
    /**
     * When a new statistic box is created, the statistic shown 
     * in the statistic box is one that isn't shown in the other boxes
     */
    private void addUndisplayedStatistic()
    {
        for (int i=0; i<data.size(); i++){
            if (!data.get(i).isDisplayed()){
                statDisplay = data.get(i).getData();
                data.get(i).toggleisDisplayed();
                break;
            }
        }
    }
    
    /**
     * Shows the previous statistic on the statistic box which isn't
     * shown in other boxes
     */
    private void previousStat(ActionEvent event)
    {
        for (int i=0; i<data.size(); i++){
            if (data.get(i).getData() == statDisplay){
                data.get(i).toggleisDisplayed();
                displayPreviousStat(i-1);  
                break;
            }
        }        
    }
    
    /**
     * Shows the next statistic on the statistic box which isn't
     * shown in other boxes
     */
    private void nextStat(ActionEvent event)
    {
        for (int i=0; i<data.size(); i++){
            if (data.get(i).getData() == statDisplay){
                data.get(i).toggleisDisplayed();
                displayNextStat(i+1);
                break;
            }
        }   
    }
    
    /**
     * If the previous data is displayed in another box, it recursively 
     * checks if the data before that is also in another box 
     * When it finds data which isn't displayed in another box, it displays
     * that data in the current statistic box
     */
    private void displayPreviousStat(int index)
    {
        if (index<0) {
            index = data.size() -1;
        }
        if (!data.get(index).isDisplayed()){
            addStatistic(index);
            return;
        }
        else {
            displayPreviousStat(index-1);
        }        
    }
    
    /**
     * If the next data is displayed in another box, it recursively 
     * checks if the data after that is also in another box 
     * When it finds data which isn't displayed in another box, it displays 
     * that data in the current statistic box
     */
    private void displayNextStat(int index)
    {
        if (index >= data.size()) {
            index = 0;
        }
        if (!data.get(index).isDisplayed()){
            addStatistic(index);
            return;
        }
        else {
            displayNextStat(index+1);
        }        
    }
    
    /**
     * Adds a statistic to the statistic box
     */    
    private void addStatistic(int index)
    {
        statBox.getChildren().remove(statDisplay);
        statDisplay = data.get(index).getData();
        data.get(index).toggleisDisplayed();
        statBox.add(statDisplay, 1, 0); 
    }
    
    public void disableNavigation()
    {
        leftArrow.setDisable(true);
        rightArrow.setDisable(true);
    }
    
    
    public void enableNavigation()
    {
        leftArrow.setDisable(false);
        rightArrow.setDisable(false);
    }
            
    /**
     * This returns the statistic box
     */
    public GridPane getStatBox()
    {
        return statBox;
    }    
    
    /**
     * Returns the GridPane which holds the statistic
     */
    public GridPane getStatDisplay()
    {
        return statDisplay;
    }
   
}
