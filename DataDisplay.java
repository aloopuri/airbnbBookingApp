import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.event.ActionEvent;

import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.Parent;
/**
 * Creates a GridPane which holds a title and a statistic
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
     * Adds a title and statistics 
     */
    public DataDisplay(StatisticPanel statPanel)
    {
        this.statPanel = statPanel;
        dataDisplay = new GridPane();
    }
    
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
    
    public void setIsDisplayedFalse()
    {
        isDisplayed = false;
    }
    
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
