import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import java.util.ArrayList;

/**
 * Write a description of class BarChartDisplay here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BarChartDisplay extends DataDisplay
{
    private StatisticPanel statPanel;
    private BarChart<String, Number> barchart;
    private VBox container;
    private ComboBox boroughListBox;
    private String title;
    private String bcData;

    /**
     * Constructor for objects of class BarChartDisplay
     */
    public BarChartDisplay(StatisticPanel statPanel, String title, String bcData,
        ObservableList<String> allBoroughs)
         
    {
        super(statPanel);
        this.statPanel = statPanel;
        this.title = title;
        this.bcData = bcData;
        
        Label message = new Label();
        if (bcData.equalsIgnoreCase("roomtypes")){
            message.setText("Room Types In Borough");
        }
        else if (bcData.equalsIgnoreCase("availability")){
            message.setText("Avavilability In Borough");
        }
        message.setAlignment(Pos.CENTER);
        message.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        FXCollections.sort(allBoroughs);     
        
        boroughListBox = new ComboBox(allBoroughs);   
        boroughListBox.setOnAction(e -> selectBorough());
        boroughListBox.setPromptText("Choose a Borough");        
        
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.getChildren().add(boroughListBox);     
      
        container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setVgrow(message, Priority.ALWAYS);
        container.getChildren().addAll(topBar, message);
        
        getData().setHgrow(container, Priority.ALWAYS);
        getData().setVgrow(container, Priority.ALWAYS);
        getData().getChildren().add(container);        
        
        setIsDisplayedFalse();       
        
        whenStatisiticClicked();
        
    }
    
    private void createBarChart(String boroughName, ObservableList<XYChart.Series> data)
    {
        final CategoryAxis boroughs = new CategoryAxis();
        final NumberAxis numOfProperties = new NumberAxis();
        barchart = new BarChart<String, Number>(boroughs, numOfProperties);
        barchart.setTitle(title);
        boroughs.setLabel(bcData);
        numOfProperties.setLabel("Number of Properties");
        
        for (XYChart.Series series : data) {
            barchart.getData().add(series);
        }
    }
    
    private void selectBorough()
    {
        String bToString = (String) boroughListBox.getValue().toString();
        //ObservableList<XYChart.Series> data = statPanel.getStatistics().getRoomTypeDistribution(bToString);
        ObservableList<XYChart.Series> data = getStatistic(bToString);
        createBarChart(bToString, data);
        showGraph();        
    }
    
    private ObservableList<XYChart.Series> getStatistic(String borough)
    {
        //ObservableList<XYChart.Series> data = statPanel.getStatistics().getRoomTypeDistribution(bToString);
        if (bcData.equalsIgnoreCase("roomtypes")) {
            ObservableList<XYChart.Series> data = statPanel.getStatistics().getRoomTypeDistribution(borough);
            return data;
        }
        else if (bcData.equalsIgnoreCase("availability")) {
            ObservableList<XYChart.Series> data = statPanel.getStatistics().getAvailDistribution(borough);
            return data;
        }        
        return null;
    }
    
    private void showGraph()
    {
        container.getChildren().remove(1);
        container.getChildren().add(barchart);
        container.setVgrow(barchart, Priority.ALWAYS);
    }
}
