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
 * This is the class for the map view of a single property.
 * This GUI will be shown when user clicks on the "view on map" button 
 * on the single property view
 *
 * @author Eamonn, Xinran
 */
public class GoogleMapView
{
    private ListingManager listingManager;
    private WebView browser;
    private WebEngine webEngine;
    private int listingIndex = -1;
    
    public GoogleMapView(ListingManager listingManager)
    {
        this.listingManager = listingManager;
        browser = new WebView();
        webEngine = browser.getEngine();
    }

    /**
     * Creates the Google Map view
     */
    public AnchorPane createGoogleMap(double lat, double lon)
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
    
    /**
     * map view of the first property
     */
    private void viewProperty(double lat, double lon)
    {
        webEngine.load("https://google.com/maps/place/" + lat + "," + lon);
    }
}
