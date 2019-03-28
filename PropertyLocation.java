import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.web.*;
import java.util.ArrayList;
/**
 * Write a description of JavaFX class MapStat here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PropertyLocation
{
    private ListingManager listingManager;
    private WebView browser;
    private WebEngine webEngine;
    private int listingIndex = -1;
    public PropertyLocation(ListingManager listingManager)
    {
        this.listingManager = listingManager;
        browser = new WebView();
        webEngine = browser.getEngine();
    }

    /**
     * Creates the Map Stats
     */
    public AnchorPane createMapStats(double lat, double lon)
    {
        // Create buttons
        Button directions = new Button("How to Get There");
        directions.getStyleClass().add("googleMapButton");
        directions.setId("showDirectionButton");

        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(800, 700);
        browser.prefWidthProperty().bind(pane.widthProperty());
        browser.prefHeightProperty().bind(pane.heightProperty());

        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setAlignment(Pos.CENTER);

        toolbar.getChildren().add(directions);

        viewProperty(lat, lon);

        pane.getChildren().add(browser);
        pane.getChildren().add(toolbar);
        pane.setRightAnchor(toolbar, 10.0);
        pane.setTopAnchor(toolbar, 5.0);

        directions.setOnAction(e -> showDirections(lat, lon));
        return pane;
    }

    /**
     * Shows the directions to the property
     */
    private void showDirections(double lat, double lon)
    {
        webEngine.load("https://maps.google.com/?saddr=My+Location&daddr="
                       + lat + "," + lon);
    }

    private void viewProperty(double lat, double lon)
    {
        webEngine.load("https://google.com/maps/place/" + lat + "," + lon);
    }
}
