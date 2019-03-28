import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import javafx.geometry.Pos;
import javafx.event.*;
import javafx.scene.image.*;

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
    private GridPane statPane;
    private StackPane stackPane;
    private GridPane enlargedStatBox;   // This is used to store the box which had the statistic in it previously
    private final VBox enlargedStatMessage = new VBox();
    
    public StatisticPanel(ArrayList<AirbnbListing> listings)
    {
        stackPane = new StackPane();
        statPane = new GridPane();  
        enlargedStatBox = null;
        stats = new Statistics(listings);
        createStatisticPanel();
    }  

    public void createStatisticPanel() 
    {
        // Create a new grid pane
        statPane = new GridPane();
        statPane.setPadding(new Insets(0, 10, 0, 10));
        statPane.setVgap(2);
        statPane.setHgap(2);
        
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
        
        createStatisticBoxes();
        
        enlargedStatMessage();
        
        stackPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        stackPane.getChildren().add(statPane);
    }
    
    public void updateStatistics(ArrayList<AirbnbListing> listings)
    {
        stats.updateStatistics(listings);
        data.clear();
        createData();
        for (GridPane box : statBoxes) {
            statPane.getChildren().remove(box);
        }
        createStatisticBoxes();
    }
    
    private void createStatisticBoxes()
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
        data.add(new BasicStatisticDisplay(this,"Average Number of Reviews", stats.getAvgNumOfReviewsString()));
        data.add(new BasicStatisticDisplay(this,"Total Available Properties", stats.getTotalAvailPropertiesString()));
        data.add(new BasicStatisticDisplay(this,"Number of Entire Homes\nand Apartments", stats.getNumOfHomesAndAptsString()));
        data.add(new BasicStatisticDisplay(this,"Most Expensive Borough", stats.getMostExpBorough()));
        data.add(new PieChartDisplay(this, stats.propertiesInBorough()));
        data.add(new BasicStatisticDisplay(this, "Statistic 6", "bruh#2"));
        data.add(new BasicStatisticDisplay(this, "Statistic 7", "bruh#3"));
        data.add(new BasicStatisticDisplay(this, "Statistic 8", "bruh#4"));
    }
    
    public void enlargedStatMessage()
    {
        Image icon = new Image("/images/pepega.png");
        ImageView iconView = new ImageView();
        iconView.setImage(icon);
        iconView.setPreserveRatio(true);
        iconView.setFitHeight(100);
        iconView.setFitWidth(100);
                
        final Label tempLabel = new Label("This statistic is currently enlarged");
        tempLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tempLabel.setAlignment(Pos.CENTER);
        enlargedStatMessage.setVgrow(tempLabel, Priority.ALWAYS);
        enlargedStatMessage.setAlignment(Pos.CENTER);
        enlargedStatMessage.getChildren().addAll(tempLabel, iconView);   
    }
    
    public StatisticPanel getStatisticPanelClass()
    {
        return this;
    }    
    
    public void showEnlargedStat(GridPane stat)
    {
        boolean done = false;
                
        for (GridPane box : statBoxes){
            ObservableList<Node> list = FXCollections.observableArrayList();
            list = (ObservableList<Node>)box.getChildren();
            for (Node node : list){
                if (node instanceof GridPane) {
                    GridPane node1 = (GridPane) node;
                    if (node1 == stat) {
                        //node.setVisible(false);                        
                        box.getChildren().remove(stat);                        
                        box.add(enlargedStatMessage, 1, 0); 
                        enlargedStatBox = box;
                        done = true;
                    }
                    break;
                }
            } 
            if (done){
                break;
            }
        }       
        
        stat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);        
        VBox container = new VBox(); 
        GridPane backgroundDim = new GridPane();
        backgroundDim.setPrefSize(statPane.getWidth(), statPane.getHeight());
        backgroundDim.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2)");
        GridPane largeStat = new GridPane();        
        
        largeStat.setMaxSize((statPane.getWidth() * 0.75), (statPane.getHeight() * 0.75));
        largeStat.getChildren().add(stat);
        largeStat.toFront();
        largeStat.setVgrow(stat, Priority.ALWAYS);
        largeStat.setHgrow(stat, Priority.ALWAYS);
        largeStat.setAlignment(Pos.CENTER);        
        largeStat.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9)");         
        
        Button close = new Button("Close");
        close.setAlignment(Pos.CENTER);
        close.setPrefSize(100, 30);
        close.setOnAction(e -> removeLargeStat(stat));
        
        container.setMaxSize((statPane.getWidth() * 0.75), (statPane.getHeight() * 0.75));
        container.setPrefSize((statPane.getWidth() * 0.75), (statPane.getHeight() * 0.75));
        container.toFront();
        container.setVgrow(largeStat, Priority.ALWAYS);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(largeStat, close);
        
        
        if (stackPane.getChildren().size() == 1)
        {
            showLargeStat(backgroundDim, container);
        }
        else{
            removeStackLayers();
            showLargeStat(backgroundDim, container);
        }     
    }
    
    private void removeLargeStat(Pane stat)
    {
        if (stackPane.getChildren().size() >= 2) {
            removeStackLayers();          
        }
        enlargedStatBox.getChildren().remove(enlargedStatMessage);
        enlargedStatBox.add(stat, 1, 0);
    }
    
    private void showLargeStat(GridPane backgroundDim, VBox container)
    {
        stackPane.getChildren().add(backgroundDim);
        stackPane.getChildren().add(container);
    }
    
    private void removeStackLayers()
    {
        int num = stackPane.getChildren().size() - 1;
        while (stackPane.getChildren().size() > 1) {
            stackPane.getChildren().remove(num);
            num--;            
        }
    }
    
    public StackPane getStatisticPanel()
    {
        return stackPane;
    }
}
