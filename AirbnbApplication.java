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
    private ArrayList<Scene> scenes;
    private ListingManager listingManager;
    private int currentScene = 0;
    private Stage mainStage;
    private MapPanel map;

    // Controls on most panels
    private Button backButton = new Button("<");
    private Button frontButton = new Button(">");
    static ComboBox fromBox;
    static ComboBox toBox;
    private HBox topPane;
    private BorderPane bottomPane;
    private Label priceRange;
    /**
     * Class Constructor
     */
    public AirbnbApplication()
    {
        scenes = new ArrayList<Scene>();
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        listingManager = new ListingManager(listings);
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
        welcomePane.setId("welcomePane");
        Scene welcomeScene = new Scene(welcomePane, 1200, 1000);
        welcomeScene.getStylesheets().add("WelcomeLayout.css");

        map = new MapPanel(listingManager);
        BorderPane pane = map.createMap();
        Scene mapScene = new Scene(pane, 1200, 1000);

        scenes.add(welcomeScene);
        scenes.add(mapScene);

        // Set ComboBox actions
        fromBox.setOnAction(e -> comboBoxAction());
        toBox.setOnAction(e -> comboBoxAction());

        frontButton.setOnAction(e -> nextScene(e));
        backButton.setOnAction(e -> previousScene(e));

        stage.setTitle("Airbnb London");
        stage.setScene(welcomeScene);

        // Show the Stage (window)
        stage.centerOnScreen();
        stage.sizeToScene();
        stage.show();
        
        mainStage = stage;
        mainStage.getIcons().add(new Image("/images/airbnb-small.png"));
    }

    /**
     * Creates the application's Welcome Panel
     */
    private BorderPane createWelcome()
    {
        // Create Labels and ImageViews with appropriate styling
        priceRange.setId("priceRange");

        Image logo = new Image("/images/airbnb-logo.png");
        ImageView logoView = new ImageView();
        logoView.setImage(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitHeight(300);
        logoView.setFitWidth(300);

        Image icon = new Image("/images/airbnb-small.png");
        ImageView iconView = new ImageView();
        iconView.setImage(icon);
        iconView.setPreserveRatio(true);
        iconView.setFitHeight(100);
        iconView.setFitWidth(100);

        Label welcomeLabel = new Label("Welcome to Airbnb London");
        welcomeLabel.setId("title");

        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");

        Label infoLabel = new Label("Select a price range to begin." +
                                    "\nCurrently selected price range:");
        infoLabel.setId("infoLabel");

        // Set price range Labels appropriately
        updatePriceRange(fromBox.getValue(), toBox.getValue());

        fromBox.setPromptText("-");
        fromBox.setId("fromBox");
        toBox.setPromptText("-");
        toBox.setId("toBox");

        frontButton.setId("frontButton");
        backButton.setId("backButton");
        backButton.setPrefWidth(50);
        frontButton.setPrefWidth(50);
        backButton.setDisable(true);
        frontButton.setDisable(true);

        // Used to align the airbnb icon to the top-left
        Region leftRegion = new Region();

        // Create a new HBox for the top area
        topPane = new HBox();
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setSpacing(10);
        topPane.getChildren().addAll(iconView, leftRegion, fromLabel, fromBox, toLabel, toBox, new Region());
        topPane.setHgrow(leftRegion, Priority.ALWAYS);

        // Create a new VBox for the center area
        VBox centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(10);
        centerPane.getChildren().addAll(logoView, welcomeLabel, infoLabel, priceRange);

        // Creates a BorderPane for the bottom area
        bottomPane = new BorderPane();
        bottomPane.setRight(frontButton);
        bottomPane.setLeft(backButton);

        // Create a BorderPane for the general scene
        BorderPane root = new BorderPane();
        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);

        //add animation effects for all components
        addAnimation(logoView);
        addAnimation(iconView);
        addAnimation(welcomeLabel);
        addAnimation(infoLabel);
        addAnimation(priceRange);
        addAnimation(fromLabel);
        addAnimation(fromBox);
        addAnimation(toLabel);
        addAnimation(toBox);

        return root;
    }

    private void addAnimation(Node node)
    {
        FadeTransition ft = new FadeTransition(Duration.millis(5000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
    }

    /**
     * Performs the ComboBox actions when interacted with
     */
    private void comboBoxAction()
    {
        if (fromBox.getValue() != null && toBox.getValue() != null) {
            int to = getToValue();
            int from = getFromValue();
            if (to < from) {
                toBox.setValue(null);
                fromBox.setValue(null);
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Value Warning");
                alert.setHeaderText(null);
                alert.setContentText("From value is greater than To value.");
                updatePriceRange(fromBox.getValue(), toBox.getValue());
                disableNavigation();
                map.showViewInRange(0, listingManager.getListings().size());
                alert.showAndWait();
            }
            else {
                updatePriceRange(fromBox.getValue(), toBox.getValue());
                map.showViewInRange(from, to);
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
        ObservableList<Integer> options = FXCollections.observableArrayList(listingManager.getMenuOptions());
        FXCollections.sort(options);
        return options;
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

    private void nextScene(ActionEvent event)
    {
        currentScene++;
        currentScene = currentScene % scenes.size();
        moveScene(currentScene);
    }

    private void previousScene(ActionEvent event)
    {
        currentScene--;
        if (currentScene < 0) {
            currentScene = scenes.size() - 1;
        }
        moveScene(currentScene);
    }

    private void moveScene(int sceneIndex)
    {
        Scene newScene = scenes.get(sceneIndex);
        ((BorderPane)newScene.getRoot()).setTop(topPane); // uncomment to put from/to boxes on all scenes
        ((BorderPane)newScene.getRoot()).setBottom(bottomPane);
        newScene.getStylesheets().add("WelcomeLayout.css");
        mainStage.setScene(newScene);
    }
    
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
}
