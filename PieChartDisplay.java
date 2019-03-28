import javafx.scene.layout.*;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import javafx.scene.chart.PieChart; 
import javafx.geometry.Side;

/**
 * Write a description of class PieChart here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PieChartDisplay extends DataDisplay
{


    /**
     * Constructor for objects of class PieChart
     */
    public PieChartDisplay(StatisticPanel statPanel, ObservableList<PieChart.Data> data)
    {
        super(statPanel);
        
        setIsDisplayedFalse();
        
        //dataDisplay = new GridPane();
        PieChart pieChart = new PieChart(data);
        pieChart.setTitle("Number Of Properties in each borough"); 
        pieChart.setClockwise(true); 
        pieChart.setLabelLineLength(50); 
        pieChart.setLabelsVisible(true); 
        pieChart.setStartAngle(180); 
        //pieChart.setLegendVisible(false);
        //pieChart.getParent().gets
        //pieChart.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getData().setVgrow(pieChart, Priority.ALWAYS);
        getData().setHgrow(pieChart, Priority.ALWAYS);
        pieChart.setLegendSide(Side.RIGHT);
        
                        
        getData().getChildren().add(pieChart);
        
        whenStatisiticClicked();
    }

}
