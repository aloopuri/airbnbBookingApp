import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.*;

public class MapPanelApplication extends Application
{
  //@FXML
  //private ComboBox<String> priceRange;

  //@FXML // fx:id="selectedPriceRange"
  //private Label selectedPriceRange; // Value injected by FXMLLoader
  @Override
  public void start(Stage stage) throws Exception
  {
    //assert priceRange != null : "fx:id=\"priceRange\" was not injected: check your FXML file 'MapView.fxml'.";
    //assert selectedPriceRange != null : "fx:id=\"selectedPriceRange\" was not injected: check your FXML file 'MapView.fxml'.";
    
    // populate the price combo box with item choices.
    //priceRange.getItems().setAll("5", "6");
    
    // bind the selected price range label to the selected price range in the combo box.
    //selectedPriceRange.textProperty().bind(priceRange.getSelectionModel().selectedItemProperty());
    
    URL url = getClass().getResource("MapView.fxml");
    Parent root = FXMLLoader.load(url);
    Scene scene = new Scene(root);

    stage.setTitle("Map");
    stage.setScene(scene);
    stage.show();
  }
}
