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
    private PieChart pieChart;
    private String title;

    /**
     * Constructor for objects of class PieChart
     */
    public PieChartDisplay(StatisticPanel statPanel, String title, ObservableList<PieChart.Data> data)
    {
        super(statPanel);
        
        setIsDisplayedFalse();
        
        pieChart = new PieChart(data);
        pieChart.setTitle(title); 
        createPieChart();      
        
        whenStatisiticClicked();
    }
    
    private void createPieChart()
    {
        pieChart.setClockwise(true); 
        pieChart.setLabelLineLength(50); 
        pieChart.setLabelsVisible(true); 
        pieChart.setStartAngle(180); 
        getData().setVgrow(pieChart, Priority.ALWAYS);
        getData().setHgrow(pieChart, Priority.ALWAYS);
        //pieChart.setStyle("-fx-text-fill: #FFFFFF;");
        pieChart.setLegendSide(Side.RIGHT);
                        
        getData().getChildren().add(pieChart);        
    }

}
