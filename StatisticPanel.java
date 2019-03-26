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
public class StatisticPanel
{  
    private List<DataDisplay> data = new ArrayList<>();
    private List<GridPane> statBoxes = new ArrayList<>();
    private static Statistics stats;
    //private StatisticBox statisticBox;
    private GridPane statPane;

    public StatisticPanel(ArrayList<AirbnbListing> listings)
    {
        //ArrayList<AirbnbListing> listings = new ArrayList<>();
        //listings = new AirbnbDataLoader().load();
        //statisticBox = new StatisticBox(null);
        stats = new Statistics(/*listings*/);
        createStatisticPanel();
    }  

    public void createStatisticPanel() 
    {
        // Create a new grid pane
        statPane = new GridPane();
        statPane.setPadding(new Insets(10, 10, 10, 10));
        statPane.setVgap(5);
        statPane.setHgap(5);
        
        for (int i=0; i<2 ; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(50);
            column.setHgrow(Priority.ALWAYS);
            statPane.getColumnConstraints().add(column);
        }
        
        for (int i=0; i<2 ; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            row.setVgrow(Priority.ALWAYS);
            statPane.getRowConstraints().add(row);
        }        
        //pane.setGridLinesVisible(true); // uncomment to show gridlines
        
        createData();
        
        GridPane statBox1 = new StatisticBox(data).getStatBox();        
        GridPane statBox2 = new StatisticBox(data).getStatBox(); 
        GridPane statBox3 = new StatisticBox(data).getStatBox();        
        GridPane statBox4 = new StatisticBox(data).getStatBox();
        
        statBoxes.add(statBox1);        
        statBoxes.add(statBox2);  
        statBoxes.add(statBox3);  
        statBoxes.add(statBox4);          
        
        // Add the statBoxes to the panel
        statPane.add(statBox1, 0, 0);
        statPane.add(statBox2, 1, 0);
        statPane.add(statBox3, 0, 1);
        statPane.add(statBox4, 1, 1);
    }
    
    public void updateStatistics(ArrayList<AirbnbListing> listings)
    {
        stats.updateStatistics(listings);
        data.clear();
        createData();
        for (GridPane box : statBoxes) {
            statPane.getChildren().remove(box);
        }
        addToPanel();
        //statisticBox.updateData(data);
    }
    
    private void addToPanel()
    {
        GridPane statBox1 = new StatisticBox(data).getStatBox();        
        GridPane statBox2 = new StatisticBox(data).getStatBox(); 
        GridPane statBox3 = new StatisticBox(data).getStatBox();        
        GridPane statBox4 = new StatisticBox(data).getStatBox();
        
        statBoxes.add(statBox1);        
        statBoxes.add(statBox2);  
        statBoxes.add(statBox3);  
        statBoxes.add(statBox4);          
        
        // Add the statBoxes to the panel
        statPane.add(statBox1, 0, 0);
        statPane.add(statBox2, 1, 0);
        statPane.add(statBox3, 0, 1);
        statPane.add(statBox4, 1, 1);
    }
    
    /**
     * This creates the data of type DataDisplay and stores it in an
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
    
    public GridPane getStatisticPanel()
    {
        return statPane;
    }
}
