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
public class MapStat
{
    ArrayList<AirbnbListing> listings;
    private WebView browser;
    private WebEngine webEngine;
    private int listingIndex = -1;
    
    public MapStat() 
    {
        listings = new AirbnbDataLoader().load();
        browser = new WebView();
        webEngine = browser.getEngine();
    }    
    
    /**
     * Creates the Map Stats
     */
    public AnchorPane createMapStats()
    {
        // Create buttons
        Button nextProperty = new Button("Next Property");
        Button previousProperty = new Button("Previous Property");
        Button directions = new Button("Show Directions");
        
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(400);
        browser.setMaxHeight(400);
        
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setAlignment(Pos.CENTER);
        
        toolbar.getChildren().add(nextProperty);
        toolbar.getChildren().add(previousProperty);
        toolbar.getChildren().add(directions);
        
        nextButton(); // move to the first property
        
        pane.getChildren().add(browser);
        pane.getChildren().add(toolbar);
        pane.setRightAnchor(toolbar, 10.0);
        pane.setTopAnchor(toolbar, 5.0);
        
        nextProperty.setOnAction(e -> nextButton());
        previousProperty.setOnAction(e -> previousButton());
        directions.setOnAction(e -> showDirections());
        return pane;
    }
    
    /**
     * Shows the directions to the property
     */
    private void showDirections() 
    {
        webEngine.load("https://maps.google.com/?saddr=My+Location&daddr="
                       + listings.get(listingIndex).getLatitude()
                       + "," + listings.get(listingIndex).getLongitude());
    }
    
    /**
     * Move to the next property on the map
     */
    private void nextButton()
    {
        listingIndex++;
        if (listingIndex != listings.size()) {
            AirbnbListing element = listings.get(listingIndex);
            webEngine.load("https://google.com/maps/place/" +
                           element.getLatitude() + "," + element.getLongitude());
        }
        else {
            listingIndex = -1; // reset index
            nextButton();
        }
    }
    
    /**
     * Move to the previous property on the map
     */
    private void previousButton() 
    {
        listingIndex--;
        if (listingIndex >= 0) {
            AirbnbListing element = listings.get(listingIndex);
            webEngine.load("https://google.com/maps/place/" +
                           element.getLatitude() + "," + element.getLongitude());                          
        }
        else {
            listingIndex = listings.size(); // wrap to end listing
            previousButton();
        }        
    }
}