import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.image.*;
import java.util.ArrayList;
/**
 * Write a description of JavaFX class Application here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbApplication extends Application
{
    private ArrayList<Pane> panels;
    private Pane currentPanel;
    private int panelIndex;
    
    private ListingManager listingManager;
    
    // Controls on most panels
    private BorderPane main;
    private Button backButton = new Button("<");
    private Button frontButton = new Button(">");
    private static ComboBox fromBox;
    private static ComboBox toBox;
    private HBox topPane;
    private BorderPane bottomPane;
    
    // Application panels
    private WelcomePanel welcomePanel;
    private MapPanel map;
    
    /**
     * Class Constructor
     */
    public AirbnbApplication()
    {
        panels = new ArrayList<Pane>();
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        listingManager = new ListingManager(listings);
        
        main = new BorderPane();
        main.setId("welcomePane");
        
        fromBox = new ComboBox(getOptionsList());
        fromBox.setCellFactory(c -> new FromBoxCell());
        
        toBox = new ComboBox(getOptionsList());
        toBox.setCellFactory(c -> new ToBoxCell());
    }

    /**
     * Sets up the application's window
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        welcomePanel = new WelcomePanel();
        map = new MapPanel(listingManager);
        
        panels.add(welcomePanel.getWelcomePanel());
        panels.add(map.createMap());
        
        // Set ComboBox actions
        fromBox.setOnAction(e -> comboBoxAction());
        toBox.setOnAction(e -> comboBoxAction());

        frontButton.setOnAction(e -> nextPanel(e));
        backButton.setOnAction(e -> previousPanel(e));

        Image icon = new Image("/images/airbnb-small.png");
        ImageView iconView = new ImageView();
        iconView.setImage(icon);
        iconView.setPreserveRatio(true);
        iconView.setFitHeight(60);
        iconView.setFitWidth(60);
        
        Region leftRegion = new Region();
        
        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");
        
        // Set ComboBox actions
        fromBox.setOnAction(e -> comboBoxAction());
        toBox.setOnAction(e -> comboBoxAction());  
        
        fromBox.setPromptText("-");
        fromBox.setId("fromBox");
        toBox.setPromptText("-");
        toBox.setId("toBox");

        topPane = new HBox();
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setSpacing(10);
        topPane.getChildren().addAll(iconView, leftRegion, fromLabel, fromBox, toLabel, toBox, new Region());
        topPane.setHgrow(leftRegion, Priority.ALWAYS);
        
        frontButton.setId("frontButton");
        backButton.setId("backButton");
        backButton.setPrefWidth(50);
        frontButton.setPrefWidth(50);
        disableNavigation();

        frontButton.setOnAction(e -> nextPanel(e));
        backButton.setOnAction(e -> previousPanel(e));
        
        bottomPane = new BorderPane();
        bottomPane.setRight(frontButton);
        bottomPane.setLeft(backButton);

        main.setCenter(panels.get(0));
        main.setTop(topPane);        
        main.setBottom(bottomPane);        
        
        Scene scene = new Scene(main, 1200, 900);
        stage.setTitle("Airbnb London");
        stage.setScene(scene);
        scene.getStylesheets().add("WelcomeLayout.css");

        // Show the Stage (window)
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.show();
        stage.getIcons().add(new Image("/images/airbnb-small.png"));
    }

    /**
     * Performs the ComboBox actions when interacted with
     */
    private void comboBoxAction()
    {   
        if (fromBox.getValue() != null && toBox.getValue() != null) {
            int to = getToValue();
            int from = getFromValue();
            welcomePanel.updatePriceRange(fromBox.getValue(), toBox.getValue());
            map.showViewInRange(from, to);
            enableNavigation();
        }
    }

    /**
     * Sets up and returns the list of options for use in combo boxes
     * @return options The list of combo box options
     */
    private ObservableList<Integer> getOptionsList()
    {
        ObservableList<Integer> options = FXCollections.observableArrayList(listingManager.getMenuOptions());
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

    private void nextPanel(ActionEvent event)
    {
        panelIndex++;
        if (panelIndex >= panels.size()) {
            panelIndex = 0;
        }
        //panelIndex = panelIndex % panels.size();
        changePanel(panelIndex);
    }

    private void previousPanel(ActionEvent event)
    {
        panelIndex--;
        if (panelIndex < 0) {
            panelIndex = panels.size() - 1;
        }
        changePanel(panelIndex);
    }

    private void changePanel(int panelIndex)
    {
        main.setCenter(null);
        main.setCenter(panels.get(panelIndex));
    }
    
    /**
     * @return The int value of the selected FromBox value
     */
    public static int getFromValue() 
    {
        if (fromBox.getValue() != null) {
            Integer fromValue = (Integer) fromBox.getValue();
            int from = fromValue.intValue();
            return from;
        }
        else {
            return -1;
        }
    }
    
    /**
     * @return The int value of the selected ToBox value
     */
    public static int getToValue() 
    {
        if (toBox.getValue() != null) {
            Integer toValue = (Integer) toBox.getValue();
            int to = toValue.intValue();
            return to;
        }
        else {
            return -1;
        }
    }
    
    /**
     * FromBox cell factory class
     */
    private class FromBoxCell extends ListCell<Integer> {
        /**
         * Adds listener that listens for a change in the value of the toBox 
         * and updates the relevant disabled cells
         */
        private FromBoxCell() 
        {
            toBox.valueProperty().addListener((change, oldValue, newValue) -> updateDisabled());
        }
        
        /**
         * Updates each cell's appearance
         */
        @Override
        protected void updateItem(Integer item, boolean empty) 
        {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.toString());
                updateDisabled();
            } else {
                setText(null);
            }
        }
        
        /**
         * Updates the disabled property of a cell
         */
        private void updateDisabled() 
        {
            boolean isDisabled = false;
            if (getItem() != null && toBox.getValue() != null) {
                isDisabled = getItem().intValue() > (Integer)toBox.getValue();
            }
            setDisable(isDisabled);
        }        
    }
    
    /**
     * ToBox cell factory class
     */
    private class ToBoxCell extends ListCell<Integer> 
    {
        /**
         * Adds listener that listens for a change in the value of the toBox 
         * and updates the relevant disabled cells
         */
        private ToBoxCell()
        {
            fromBox.valueProperty().addListener((change, oldValue, newValue) -> updateDisabled());
        }

        /**
         * Updates each cell's appearance
         */
        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.toString());
                updateDisabled();
            } else {
                setText(null);
            }
        }
        
        /**
         * Updates the disabled property of a cell
         */
        private void updateDisabled() {
            boolean isDisabled = false;
            if (getItem() != null && fromBox.getValue() != null) {
                isDisabled = getItem().intValue() < (Integer)fromBox.getValue();
            }
            setDisable(isDisabled);
        }        
    }
}