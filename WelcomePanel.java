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
 * The first panel the user encounters when openning the GUI
 *
 * @author Zain, Talal, Xinran, Eamonn
 */
public class WelcomePanel
{
    private BorderPane root;
    
    private Label priceRange = new Label();
    /**
     * Constructor for objects of class WelcomePanel
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
     * @return The welcome panel's root pane
     */
    public BorderPane getWelcomePanel()
    {
        return root;
    }
    
    /**
     * Applies a fade-in animation to a provided node
     * @param node The node to apply the animation onto
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
