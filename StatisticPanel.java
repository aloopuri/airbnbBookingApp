

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


/**
 * This 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class StatisticPanel extends Application
{  
    
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
           
        GridPane statBox1 =  new StatisticBox().createStatBox();        
        GridPane statBox2 =  new StatisticBox().createStatBox(); 
        GridPane statBox3 =  new StatisticBox().createStatBox();        
        GridPane statBox4 =  new StatisticBox().createStatBox();
        
        // Add the statBoxes to the panel
        pane.add(statBox1, 0, 0);
        pane.add(statBox2, 1, 0);
        pane.add(statBox3, 0, 1);
        pane.add(statBox4, 1, 1);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(pane, 500,500);
        stage.setTitle("Statistics");
        stage.setScene(scene);        

        // Show the Stage (window)
        stage.show();
    }
}
