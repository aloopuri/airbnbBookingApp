

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;

/**
 * This Panel contains four statistic boxes which display statistics 
 * about the airbnb listings.
 * The user can click between different statistics in each box.
 * The same statistic cannot be shown in two or more separate panels 
 * at the same time.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticPanel extends Application
{  
    private List<DataDisplay> data = new ArrayList<>();
    private static Statistics stats;

    public StatisticPanel()
    {
        ArrayList<AirbnbListing> listings = new ArrayList<>();
        listings = new AirbnbDataLoader().load();
        stats = new Statistics(listings);
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a new grid pane
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(5);
        pane.setHgap(5);
        
        for (int i=0; i<2 ; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            column.setHgrow(Priority.ALWAYS);
            pane.getColumnConstraints().add(column);
        }
        
        for (int i=0; i<2 ; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            row.setVgrow(Priority.ALWAYS);
            pane.getRowConstraints().add(row);
        }        
        //pane.setGridLinesVisible(true); // uncomment to show gridlines
        
        createData();
        
        GridPane statBox1 = new StatisticBox(data).createStatBox();        
        GridPane statBox2 = new StatisticBox(data).createStatBox(); 
        GridPane statBox3 = new StatisticBox(data).createStatBox();        
        GridPane statBox4 = new StatisticBox(data).createStatBox();
        
        // Add the statBoxes to the panel
        pane.add(statBox1, 0, 0);
        pane.add(statBox2, 1, 0);
        pane.add(statBox3, 0, 1);
        pane.add(statBox4, 1, 1);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 600, 600);
        stage.setTitle("Statistics");
        stage.setScene(scene);        

        // Show the Stage (window)
        stage.show();
    }
    
    /**
     * This creates the data of type DataDisplay and adds it to an 
     * arraylist
     */
    private void createData()
    {
        data.add(new DataDisplay("Average Number of Reviews", stats.getAvgNumOfReviewsString()));
        data.add(new DataDisplay("Total Available Properties", stats.getTotalAvailPropertiesString()));
        data.add(new DataDisplay("Number of Entire Homes\nand Apartments", stats.getNumOfHomesAndAptsString()));
        data.add(new DataDisplay("Most Expensive Borough", stats.getMostExpBorough()));
        data.add(new DataDisplay("Statistic 5", "bruh#1"));
        data.add(new DataDisplay("Statistic 6", "bruh#2"));
        data.add(new DataDisplay("Statistic 7", "bruh#3"));
        data.add(new DataDisplay("Statistic 8", "bruh#4"));
    }
}
