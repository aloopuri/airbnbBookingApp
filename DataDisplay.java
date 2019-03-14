import java.util.List;
import java.util.ArrayList;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

/**
 * Creates a GridPane which holds a title and a statistic
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DataDisplay
{
    private GridPane dataDisplay;
    private Label title = new Label();
    private Label data = new Label();
    
    private boolean isDisplayed;

    /**
     * Adds a title and statistics 
     */
    public DataDisplay(String title, String data)
    {
        dataDisplay = new GridPane();
        
        this.title.setText(title);
        this.data.setText(data);   
        
        this.title.setTextAlignment(TextAlignment.CENTER);
        this.data.setTextAlignment(TextAlignment.CENTER);
             
        isDisplayed = false;
        
        for (int i=0; i<2 ; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            row.setVgrow(Priority.ALWAYS);
            dataDisplay.getRowConstraints().add(row);
        }
        

        dataDisplay.setStyle("-fx-background-color: #FFFFFF"); 
        dataDisplay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        dataDisplay.setHalignment(this.title, HPos.CENTER);
        dataDisplay.setHalignment(this.data, HPos.CENTER);
        dataDisplay.setAlignment(Pos.CENTER);
        
        dataDisplay.add(this.title, 0, 0);
        dataDisplay.add(this.data, 0, 1);

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
     * Toggles isDisplayed between true/false
     */
    public void toggleisDisplayed()
    {
        isDisplayed = !isDisplayed;
    }
}
