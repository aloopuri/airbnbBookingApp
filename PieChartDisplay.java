import javafx.scene.layout.*;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import javafx.scene.chart.PieChart; 
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.geometry.Pos;


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
    private ObservableList<PieChart.Data> data;

    /**
     * Creates the pie chart on initialisation
     */
    public PieChartDisplay(StatisticPanel statPanel, String title, ObservableList<PieChart.Data> data)
    {
        super(statPanel);
        
        this.title = title;
        this.data = data;
        
        createPieChart();
    }
    
    /**
     * Creates the piechart
     */
    private void createPieChart()
    {
        if (data == null) {
            errormessage();
            return;
        }
        pieChart = new PieChart(data);
        pieChart.setTitle(title); 
        pieChart.setClockwise(true); 
        pieChart.setLabelLineLength(40); 
        pieChart.setLabelsVisible(true); 
        pieChart.setStartAngle(90); 
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
    
    /**
     * Creates an error message if there is no data
     */
    private void errormessage()
    {
        GridPane error = new GridPane();
        error.setAlignment(Pos.CENTER);
        Label msg = new Label("No data in this range");
        getData().setVgrow(error, Priority.ALWAYS);
        getData().setHgrow(error, Priority.ALWAYS);
        getData().getChildren().add(error);
    }

}
