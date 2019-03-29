import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.event.ActionEvent;

import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.Parent;
/**
 * Abstract class which stores the shared properties of the different types 
 * of ways a statistics is displayed
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class DataDisplay
{
    private GridPane dataDisplay;    
    private boolean isDisplayed;
    private static StatisticPanel statPanel;

    /**
     * Creates a GridPane
     */
    public DataDisplay(StatisticPanel statPanel)
    {
        this.statPanel = statPanel;
        dataDisplay = new GridPane();
        dataDisplay.setStyle("-fx-background-color: rgba(239, 9, 205, 0.7)");
    }
    
    /**
     * When a statistic is clicked, it shows an enlarged version of it in the statistic panel
     */
    protected void whenStatisiticClicked()
    {
        dataDisplay.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {
                statPanel.showEnlargedStat(dataDisplay);
            } 
        });    
    }   
    
    /**
     * Returns dataDisplay with the title and statistic
     */
    public GridPane getData()
    {
        return dataDisplay;
    }
    
    /**
     * Returns isDisplayed
     */
    public boolean isDisplayed()
    {
        return isDisplayed;
    }
    
    /**
     * Sets isDisplayed to false
     */
    public void setIsDisplayedFalse()
    {
        isDisplayed = false;
    }
    
    /**
     * Sets isDisplayed to false
     */
    public void setIsDisplayedTrue()
    {
        isDisplayed = true;
    }
    
    /**
     * Toggles isDisplayed between true/false
     */
    public void toggleisDisplayed()
    {
        isDisplayed = !isDisplayed;
    }
}
