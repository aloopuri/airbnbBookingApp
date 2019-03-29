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
    private StatisticPanel statPanel;
    private Label title = new Label();
    private ComboBox boroughListBox;
    private VBox container;

    /**
     * Constructor for objects of class ListDisplay
     */
    public ListDisplay(StatisticPanel statPanel, String title, ObservableList<String> allBoroughs)
    {
        super(statPanel);
        this.statPanel = statPanel;
        this.title.setText(title);
        
        container = new VBox();
        
        Label message = new Label();        
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
    
    private void createDisplay(String bToString, ArrayList<String> data) 
    {
        int count = 0;
        for (String stat : data) {
            container.getChildren().add(new Label(data.get(count).toString()));
            count++;
        }
    }
    
    private void selectBorough()
    {
        String bToString = (String) boroughListBox.getValue().toString();
        ArrayList<String> data =  statPanel.getStatistics().mostPopListType(bToString);
        createDisplay(bToString, data);
    }
}
