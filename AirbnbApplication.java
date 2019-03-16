import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import java.awt.Insets;
import java.util.*;
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

    private Label myLabel = new Label("Welcome - Choose a range of prices to continue"); // Temporary

    private ArrayList<Scene> scenes;
    private ListingManager listingManager;
    private int currentScene = 0;
    private Stage mainStage;
    // Controls on most panels
    private Button backButton = new Button("<");
    private Button frontButton = new Button(">");
    private ComboBox fromBox;
    private ComboBox toBox;
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
        Scene welcomeScene = new Scene(welcomePane, 500, 500);
        welcomeScene.getStylesheets().add("WelcomeLayout.css");

        scenes.add(welcomeScene);
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
        // Create Labels and ImageViews with appropriate styling
        priceRange.setId("info");

        Image logo = new Image("/images/airbnb-logo.png");
        ImageView logoView = new ImageView();
        logoView.setImage(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitHeight(200);
        logoView.setFitWidth(200);

        Image icon = new Image("/images/airbnb-small.png");
        ImageView iconView = new ImageView();
        iconView.setImage(icon);
        iconView.setPreserveRatio(true);
        iconView.setFitHeight(50);
        iconView.setFitWidth(50);

        Label welcomeLabel = new Label("Welcome to Airbnb London");
        welcomeLabel.setId("title");

        Label fromLabel = new Label("From:");
        Label toLabel = new Label("To:");
        fromLabel.setId("topControls");
        toLabel.setId("topControls");

        Label infoLabel = new Label("Select a price range to begin." +
                                    "\nCurrently selected price range:");

        // Set price range Labels appropriately
        updatePriceRange(fromBox.getValue(), toBox.getValue());

        fromBox.setPromptText("-");
        toBox.setPromptText("-");

        backButton.setPrefWidth(50);
        frontButton.setPrefWidth(50);
        backButton.setDisable(true);
        frontButton.setDisable(true);

        // Used to align the airbnb icon to the top-left
        Region leftRegion = new Region();

        // Create a new HBox for the top area
        HBox topPane = new HBox();
        topPane.setAlignment(Pos.CENTER_RIGHT);
        topPane.setSpacing(10);
        topPane.getChildren().add(iconView);
        topPane.getChildren().add(leftRegion);
        topPane.getChildren().add(fromLabel);
        topPane.getChildren().add(fromBox);
        topPane.getChildren().add(toLabel);
        topPane.getChildren().add(toBox);
        topPane.setHgrow(leftRegion, Priority.ALWAYS);

        // Create a new VBox for the center area
        VBox centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(10);
        centerPane.getChildren().add(logoView);
        centerPane.getChildren().add(welcomeLabel);
        centerPane.getChildren().add(infoLabel);
        centerPane.getChildren().add(priceRange);

        // Creates a BorderPane for the bottom area
        BorderPane bottomPane = new BorderPane();
        bottomPane.setRight(frontButton);
        bottomPane.setLeft(backButton);

        // Create a BorderPane for the general scene
        BorderPane root = new BorderPane();
        root.setTop(topPane);
        root.setCenter(centerPane);
        root.setBottom(bottomPane);

        FadeTransition ft = new FadeTransition(Duration.millis(4000), logoView);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();

        return root;
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
        ObservableList<Integer> options = FXCollections.observableArrayList(listingManager.getAllPrices());
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
}
