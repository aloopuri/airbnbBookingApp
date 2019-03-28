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
import javafx.geometry.Insets;
import java.util.ArrayList;

/**
 * Write a description of class AirbnbApp here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AirbnbApplication extends Application
{
    private ArrayList<Pane> panels;
    private Pane currentPanel;
    private ListingManager listingManager;
    private ArrayList<AirbnbListing> listings;
    private int panelIndex;
    
    private BorderPane main;
    private Button backButton = new Button("<");
    private Button frontButton = new Button(">");
    private ComboBox fromBox;
    private ComboBox toBox;
    private HBox topPane;
    private BorderPane bottomPane;
    
    private WelcomePanel welcomePanel;
    private MapPanel map;
    private StatisticPanel statsPanel;
    
    /**
     * Constructor for objects of class AirbnbApp
     */
    public AirbnbApplication()
    {
        panels = new ArrayList<>();
        listings = new ArrayList<AirbnbListing>();
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        listingManager = new ListingManager(listings);
        main = new BorderPane();
        fromBox = new ComboBox(getOptionsList());
        toBox = new ComboBox(getOptionsList());        
        //int i = 0;
        // while (i < listings.size()){
            // System.out.println(listings.get(i).getNeighbourhood());
            // i++;
        // };
    }
    
    /**
     * Sets up the application's window
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        welcomePanel = new WelcomePanel();       
        map = new MapPanel(listingManager);
        statsPanel = new StatisticPanel(listings);
        
        panels.add(welcomePanel.getWelcomePanel());
        panels.add(map.createMap());
        panels.add(statsPanel.getStatisticPanel());
        

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
        fromBox.toFront();
        toBox.setPromptText("-");
        toBox.setId("toBox");
        toBox.toFront();

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
        Insets inset = new Insets(5);
        main.setMargin(topPane, inset);
        main.setMargin(bottomPane, inset);
        
        Scene scene = new Scene(main, 1200, 800);
        stage.setTitle("Airbnb London");
        stage.setScene(scene);
        scene.getStylesheets().add("WelcomeLayout.css");

        // Show the Stage (window)
        stage.show();
        stage.getIcons().add(new Image("/images/airbnb-small.png"));
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
                alert.setContentText("From value is greater than To value.");
                welcomePanel.updatePriceRange(fromBox.getValue(), toBox.getValue());
                disableNavigation();
                map.updateView(0, listingManager.getListings().size());
                alert.showAndWait();
            }
            else {
                welcomePanel.updatePriceRange(fromBox.getValue(), toBox.getValue());
                listingManager.updateUserRangeListings(from, to);
                //System.out.println(listingManager.getCurrentListings().size());
                map.updateView(from, to);
                statsPanel.updateStatistics(listingManager.getCurrentListings());
                enableNavigation();
            }
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
        
    private ObservableList<Integer> getOptionsList()
    {
        ObservableList<Integer> options = FXCollections.observableArrayList(listingManager.getMenuOptions());
        FXCollections.sort(options);
        return options;
    }
    
    private void nextPanel(ActionEvent event)
    {
        panelIndex++;
        if (panelIndex >= panels.size()) {
            panelIndex = 0;
        }
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
}
