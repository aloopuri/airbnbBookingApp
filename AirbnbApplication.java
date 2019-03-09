import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.collections.*;

import java.util.ArrayList;
/**
 * Write a description of JavaFX class Application here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbApplication extends Application
{
    private ArrayList<Scene> scenes;
    private ArrayList<AirbnbListing> listings;
    
    // Controls on most panels
    private Button backButton = new Button("<");
    private Button frontButton = new Button(">");
    private ComboBox fromBox;
    private ComboBox toBox;
    private Label priceRange;
    
    /**
     * Class Constructor
     */
    public AirbnbApplication() 
    {
        scenes = new ArrayList<Scene>();
        listings = new ArrayList<AirbnbListing>();
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        fromBox = new ComboBox(getOptionsList());
        toBox = new ComboBox(getOptionsList());
        priceRange = new Label();
    }
    
    /**
     * Sets up the application's window
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane welcomePane = createWelcome();
        
        // Create Welcome scene
        Scene welcomeScene = new Scene(welcomePane, 500, 500);
        scenes.add(welcomeScene);
        
        // Create Map scene
        //Scene mapScene = new Scene(mapPane, 500, 500);
        //scenes.add(mapScene);
        
        stage.setTitle("Airbnb London");
        stage.setScene(welcomeScene);
        
        // Set ComboBox actions
        fromBox.setOnAction(e -> comboBoxAction());
        toBox.setOnAction(e -> comboBoxAction());
        
        // Show the Stage (window)
        stage.show();
    }
    
    /**
     * Creates the application's Welcome Panel
     */
    private BorderPane createWelcome() 
    {
        Label welcomeLabel = new Label();
        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");
        
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeLabel.setText("Welcome to Airbnb London!");
        
        Label infoLabel = new Label("Select a price range to begin." +
                                    "\nCurrently selected price range:");
        infoLabel.setFont(Font.font("Arial", FontPosture.ITALIC, 18));
        
        priceRange.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        updatePriceRange(fromBox.getValue(), toBox.getValue());
        
        fromBox.setPromptText("-");
        toBox.setPromptText("-");
        
        backButton.setPrefWidth(50);
        frontButton.setPrefWidth(50);
        backButton.setDisable(true);
        frontButton.setDisable(true);
        
        // Create a new HBox for the top area
        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setSpacing(10);
        topPane.getChildren().add(fromLabel);
        topPane.getChildren().add(fromBox);
        topPane.getChildren().add(toLabel);
        topPane.getChildren().add(toBox);
        
        // Create a new VBox for the center area
        VBox centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(10);
        centerPane.getChildren().add(welcomeLabel);
        centerPane.getChildren().add(infoLabel);
        centerPane.getChildren().add(priceRange);
        
        BorderPane bottomPane = new BorderPane();
        bottomPane.setRight(frontButton);
        bottomPane.setLeft(backButton);

        // Create a new border pane
        BorderPane root = new BorderPane();
        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);
        
        return root;
    }
    
    /**
     * Create Map Panel
     */
    private void createMap() 
    {
        // to do...
    }
    
    /**
     * Performs the ComboBox actions when interacted with
     */
    private void comboBoxAction() 
    {
        if (fromBox.getValue() != null && toBox.getValue() != null) {
            Integer toValue = (Integer)toBox.getValue();
            Integer fromValue = (Integer)fromBox.getValue();
            int to = toValue.intValue();
            int from = fromValue.intValue();
            if (to < from) {
                toBox.setValue(null);
                fromBox.setValue(null);                
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Value Warning");
                alert.setHeaderText(null);
                alert.setContentText("From value is greater than To value.");
                updatePriceRange(fromBox.getValue(), toBox.getValue());
                disableNavigation();
                alert.showAndWait();
            }
            else {
                updatePriceRange(fromBox.getValue(), toBox.getValue());
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
        ObservableList<Integer> options = FXCollections.observableArrayList();
        
        for (AirbnbListing aListing : listings) {
            options.add(aListing.getPrice());
        }
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
     * Updates the priceRange Label's text
     */
    private void updatePriceRange(Object fromValue, Object toValue)
    {
        if (fromValue == null || toValue == null) {
            priceRange.setText("From: £- To £-");
        }
        else {
            priceRange.setText("From: £" + fromValue.toString() +
                               " To: £" + toValue.toString());
        }
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