import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import javafx.geometry.Pos;
import java.util.ArrayList;
/**
 * Write a description of class ListDisplay here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ListDisplay extends DataDisplay
{
    private Label title = new Label();
    private ComboBox boroughListBox;
    private VBox container;

    /**
     * Creates a combo box of boroughs and shows a default message 
     * When option in combo box selected, updates and show stats
     */
    public ListDisplay(StatisticPanel statPanel, String title, ObservableList<String> allBoroughs)
    {
        super(statPanel);
        this.title.setText(title);
        
        container = new VBox();
        
        //Label message = new Label();  
        //this.title.setText("Common Features of Top Rated Listings");
        this.title.setAlignment(Pos.CENTER);
        this.title.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        FXCollections.sort(allBoroughs);     
        
        boroughListBox = new ComboBox(allBoroughs);
        boroughListBox.setOnAction(e -> selectBorough());
        boroughListBox.setPromptText("Choose a Borough"); 
        
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_LEFT);
        topBar.getChildren().add(boroughListBox);
        
        container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setVgrow(this.title, Priority.ALWAYS);
        container.getChildren().addAll(topBar, this.title);
        
        getData().setHgrow(container, Priority.ALWAYS);
        getData().setVgrow(container, Priority.ALWAYS);
        getData().getChildren().add(container);            
    }
    
    /**
     * This creates the data as labels places them in a VBox and into the display
     */
    private void createDisplay(String bToString, ArrayList<String> data) 
    {
        removeLabels();
        VBox labelHold = new VBox();
        labelHold.setAlignment(Pos.CENTER);
        labelHold.getChildren().add(new Label("Common Features of Top Listings: " + bToString));
        int count = 0;
        for (String stat : data) {
            labelHold.getChildren().add(new Label(count+1 + ") " + data.get(count).toString()));
            count++;
        }
        container.getChildren().add(labelHold);
        container.setVgrow(labelHold, Priority.ALWAYS);
    }
    
    /**
     * This removes all of the labels in the display
     */
    private void removeLabels()
    {
        int num = container.getChildren().size()-1;
        while (container.getChildren().size() > 1) {
            container.getChildren().remove(num);
            num--;
        }
    }
    
    /**
     * This
     */
    private void selectBorough()
    {
        String bToString = (String) boroughListBox.getValue().toString();
        ArrayList<String> data =  getStatPanel().getStatistics().mostPopListType(bToString);
        createDisplay(bToString, data);
    }
}
