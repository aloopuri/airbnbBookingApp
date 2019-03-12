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
  private ListingManager listingManager;
  
  @FXML Button Sutton;
  @FXML Button Haringey;
  @FXML Button Havering;
  @FXML Button Harrow;
  @FXML Button BarkingandDagenham;
  @FXML Button Greenwich;
  @FXML Button Bexley;
  @FXML Button Enfield;
  @FXML Button Barnet;
  @FXML Button WalthamForest;
  @FXML Button Hillingdon;
  @FXML Button Ealing;
  @FXML Button Brent;
  @FXML Button Camden;
  @FXML Button Westminster;
  @FXML Button Islington;
  @FXML Button Hackney;
  @FXML Button TowerHamlets;
  @FXML Button Redbridge;
  @FXML Button KensingtonandChelsea;
  @FXML Button Newham;
  @FXML Button Hounslow;
  @FXML Button HammersmithandFulham;
  @FXML Button Wandsworth;
  @FXML Button CityofLondon;
  @FXML Button RichmonduponThames;
  @FXML Button KingstonuponThames;
  @FXML Button Lambeth;
  @FXML Button Southwark;
  @FXML Button Croydon;
  @FXML Button Lewisham;
  @FXML Button Bromley;

  @Override
  public void start(Stage stage) throws Exception
  {
    URL url = getClass().getResource("MapView.fxml");
    Parent root = FXMLLoader.load(url);
    Scene scene = new Scene(root);

    stage.setTitle("Map");
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void initialize()
  {
        openBoroughWindow();
  }

  public void openBoroughWindow()
  {   
      
      Sutton.setDisable(true);
  }
}
