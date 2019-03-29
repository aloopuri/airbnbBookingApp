import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.animation.*;
import javafx.util.*;

import java.util.ArrayList;
/**
 * Write a description of JavaFX class Application here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbApplication extends Application
{
    private Label myLabel = new Label("Welcome"); // Temporary
    
    private ArrayList<Scene> scenes;
    private ListingManager listingManager;
    private LoginSystem loginSystem;
    // Controls on most panels
    private Button backButton = new Button("<");
    private Button frontButton = new Button(">");
    private ComboBox fromBox;
    private ComboBox toBox;
    
    /**
     * Class Constructor
     */
    public AirbnbApplication() 
    {
        loginSystem = new LoginSystem();
        scenes = new ArrayList<Scene>();
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        listingManager = new ListingManager(listings);
        fromBox = new ComboBox(getOptionsList());
        toBox = new ComboBox(getOptionsList());
    }
    
    /**
     * Sets up the application's window
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");

        fromBox.setPromptText("-");
        toBox.setPromptText("-");
        
        backButton.setPrefWidth(50);
        frontButton.setPrefWidth(50);
        backButton.setDisable(true);
        frontButton.setDisable(true);
        
        // Create a new HBox
        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setSpacing(10);
        topPane.getChildren().add(fromLabel);
        topPane.getChildren().add(fromBox);
        topPane.getChildren().add(toLabel);
        topPane.getChildren().add(toBox);
        
        BorderPane bottomPane = new BorderPane();
        bottomPane.setRight(frontButton);
        bottomPane.setLeft(backButton);

        // Create a new border pane
        BorderPane root = new BorderPane();
        root.setTop(topPane);
        UserPanel xxx = new UserPanel(loginSystem);
        root.setCenter(xxx.getPane());
        root.setBottom(bottomPane);
        
        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene welcomeScene = new Scene(root, 500, 500);
        scenes.add(welcomeScene);
        stage.setTitle("Airbnb London");
        stage.setScene(welcomeScene);
        
        // Set ComboBox actions
        fromBox.setOnAction(e -> comboBoxAction());
        toBox.setOnAction(e -> comboBoxAction());
        
        // Show the Stage (window)
        stage.show();
    }
    
    private void hideLogins()
    {
        //TranslateTransition tt = new TranslateTransition(Duration.millis(300),);
        //tt.setToX(-70);
        //tt.play();
    }
    
    /**
     * Performs the ComboBox actions when interacted with
     */
    private void comboBoxAction() 
    {
        if (fromBox.getValue() != null && toBox.getValue() != null) {
            Integer toValue = (Integer) toBox.getValue();
            Integer fromValue = (Integer) fromBox.getValue();
            int to = toValue.intValue();
            int from = fromValue.intValue();
            if (to < from) {
                toBox.setValue(null);
                fromBox.setValue(null);                
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Value Warning");
                alert.setHeaderText(null);
                alert.setContentText("From value is greater than To value.");
                alert.showAndWait();
                disableNavigation();
            }
            else {
                enableNavigation();
            }
        }
    }
    
    /**
     * Sets up and returns the list of options for use in combo boxes
     * @return options The list of combo box options
     */
    private ObservableList<Integer> getOptionsList() 
    {
        ObservableList<Integer> options = FXCollections.observableArrayList(listingManager.getAllPrices());
        
        /**for (AirbnbListing aListing : listingManager.getListings()) {
            //if (!options.contains(aListing))
            {
                options.add(aListing.getPrice());
            }
        }
        listingManager.getAllPrices();**/
        FXCollections.sort(options);
        return options;
    }
    
    /**
     * Enables the navigation buttons at the bottom of the window
     */
    private void enableNavigation() 
    {
        frontButton.setDisable(false);
        backButton.setDisable(false);        
    }
    
    /**
     * Disables the navigation buttons at the bottom of the window
     */
    private void disableNavigation() 
    {
        frontButton.setDisable(true);
        backButton.setDisable(true);
    }
}