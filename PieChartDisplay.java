import javafx.scene.layout.*;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import javafx.scene.chart.PieChart; 
import javafx.geometry.Side;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

/**
 * This class creates a pie chart used to display a statistic
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PieChartDisplay extends DataDisplay
{
    private PieChart pieChart;
    private String title;

    /**
     * Creates the pie chart on initialisation
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
    
    /**
     * Creates the piechart
     */
    private void createPieChart()
    {
        pieChart.setClockwise(true); 
        pieChart.setLabelLineLength(50); 
        pieChart.setLabelsVisible(true); 
        pieChart.setStartAngle(180); 
        getData().setVgrow(pieChart, Priority.ALWAYS);
        getData().setHgrow(pieChart, Priority.ALWAYS);

        pieChart.setLegendVisible(false);
        
        pieChart.getData().forEach(data -> { 
        String number = data.getName() +": " + String.format("%.0f", (data.getPieValue()));
        Tooltip toolTip = new Tooltip(number);
        Tooltip.install(data.getNode(), toolTip);
        });       // this shows the borough name and number of properties in a slice
        
        getData().getChildren().add(pieChart);        
    }

}
