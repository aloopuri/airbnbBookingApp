import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
/**
 * Write a description of class BasicStatistic here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BasicStatisticDisplay extends DataDisplay
{
    private Label title = new Label();
    private Label data = new Label();

    /**
     * Constructor for objects of class BasicStatistic
     */ 
    public BasicStatisticDisplay(StatisticPanel statPanel, String title, String data)
    {
        super(statPanel);
        //dataDisplay = new GridPane();
        
        this.title.setText(title);
        this.data.setText(data);   
        
        this.title.setTextAlignment(TextAlignment.CENTER);
        this.data.setTextAlignment(TextAlignment.CENTER);
             
        setIsDisplayedFalse();
        
        for (int i=0; i<2 ; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            row.setVgrow(Priority.ALWAYS);
            getData().getRowConstraints().add(row);
        }
        

        //getData().setStyle("-fx-background-color: #FFFFFF");  //uncomment to add white background
        getData().setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getData().setHalignment(this.title, HPos.CENTER);
        getData().setHalignment(this.data, HPos.CENTER);
        getData().setAlignment(Pos.CENTER);
        
        getData().add(this.title, 0, 0);
        getData().add(this.data, 0, 1);
        
        whenStatisiticClicked();
    }

}
