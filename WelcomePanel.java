import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.image.*;
/**
 * Write a description of class WelcomePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WelcomePanel
{
    private BorderPane root; 
    private Label priceRange = new Label();
    /**
     * set up the main pane for WelcomePanel
     */
    public WelcomePanel()
    {
        // Create Labels and ImageViews with appropriate styling
        priceRange.setId("priceRange");

        Image logo = new Image("/images/airbnb-logo.png");
        ImageView logoView = new ImageView();
        logoView.setImage(logo);
        logoView.setPreserveRatio(true);
        logoView.setFitHeight(300);
        logoView.setFitWidth(300);

        Label welcomeLabel = new Label("Welcome to Airbnb London");
        welcomeLabel.setId("title");

        Label infoLabel = new Label("Select a price range to begin." +
                                    "\nCurrently selected price range:");
        infoLabel.setId("infoLabel");

        // Create a new VBox for the center area
        VBox centerPane = new VBox();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setSpacing(10);
        centerPane.getChildren().addAll(logoView, welcomeLabel, infoLabel, priceRange);

         // Create a BorderPane for the general scene
        root = new BorderPane();
        root.setCenter(centerPane);
        root.getStylesheets().add("WelcomeLayout.css");

        //add animation effects for all components
        addAnimation(logoView);
        addAnimation(welcomeLabel);
        addAnimation(infoLabel);
        addAnimation(priceRange);
    }
    
    /**
     * @return  the welcome pane as a BorderPane
     */
    public BorderPane getWelcomePanel()
    {
        return root;
    }
    
    /**
     * set up the animation for the welcome panel 
     * when it is first shown
     */
    private void addAnimation(Node node)
    {
        FadeTransition ft = new FadeTransition(Duration.millis(5000), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.play();
    }

    /**
     * Updates the priceRange Label's text
     */
    public void updatePriceRange(Object fromValue, Object toValue)
    {
        if (fromValue == null || toValue == null) {
            priceRange.setText("From: £- To £-");
        }
        else {
            priceRange.setText("From: £" + fromValue.toString() +
                               " To: £" + toValue.toString());
        }
    }    
}
