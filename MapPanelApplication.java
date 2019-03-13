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
import javafx.scene.paint.*;

public class MapPanelApplication extends Application
{
  private ListingManager listingManager;
  private AirbnbDataLoader dataLoader;
  private Color color;
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
  @FXML Button Merton;
  @FXML MenuItem FiveToTen;
  @FXML MenuItem TenToTwenty;
  @FXML MenuItem TwentyToThirty;
  @FXML MenuItem ThirtyToFourty;
  @FXML MenuItem FortyToFifty;
  @FXML MenuItem FiftyToOneH;
  @FXML MenuItem OneHToThreeH;
  @FXML MenuItem ThreeHToSixH;
  @FXML MenuItem SixHToOneT;

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

  private void openBoroughWindow()
  {
      //Cannot be in Constructor as this is before the MapView is created
      dataLoader = new AirbnbDataLoader();
      listingManager = new ListingManager(dataLoader.load());

      setButtonColor(Sutton);
      setButtonColor(Haringey);
      setButtonColor(Havering);
      setButtonColor(Harrow);
      setButtonColor(Greenwich);
      setButtonColor(Bexley);
      setButtonColor(Enfield);
      setButtonColor(Barnet);
      setButtonColor(Hillingdon);
      setButtonColor(Ealing);
      setButtonColor(Camden);
      setButtonColor(Islington);
      setButtonColor(Hackney);
      setButtonColor(Redbridge);
      setButtonColor(Newham);
      setButtonColor(Hounslow);
      setButtonColor(Wandsworth);
      setButtonColor(Merton);
      setButtonColor(Lambeth);
      setButtonColor(Southwark);
      setButtonColor(Croydon);
      setButtonColor(Lewisham);
      setButtonColor(Brent);
      setButtonColor(Westminster);
      setButtonColor(Bromley);
      setButtonColor(BarkingandDagenham);
      setButtonColor(WalthamForest);
      setButtonColor(TowerHamlets);
      setButtonColor(KensingtonandChelsea);
      setButtonColor(HammersmithandFulham);
      setButtonColor(CityofLondon);
      setButtonColor(RichmonduponThames);
      setButtonColor(KingstonuponThames);
  }

  private int NoOfListingsEachBorough(Button button)
  {
    return listingManager.listingsInBorough(nameOfBorough(button));
  }

  private String nameOfBorough(Button button)
  {
    switch(button.getId()){
      case "BarkingandDagenham":
        return "Barking and Dagenham";

      case "WalthamForest":
        return "Waltham Forest";

      case "TowerHamlets":
        return "Waltham Forest";

      case "KensingtonandChelsea":
        return "Kensington and Chelsea";

      case "HammersmithandFulham":
        return "Hammersmith and Fulham";

      case "CityofLondon":
        return "City of London";

      case "RichmonduponThames":
        return "Richmond upon Thames";

      case "KingstonuponThames":
        return "Kingston upon Thames";

      default:
        return button.getId();
    }
  }

  private Color individualBoroughColor(Button button)
  {
    int listingCount = NoOfListingsEachBorough(button);

    if(listingCount <= 300)
    {
      Color c = Color.web("#B0C4DE");
      return c;
    }
    else if(listingCount > 300 & listingCount <= 600)
    {
      Color c = Color.web("#B0E0E6");
      return c;
    }
    else if(listingCount > 600 & listingCount <= 1000)
    {
      Color c = Color.web("#ADD8E6");
      return c;
    }
    else if(listingCount > 1000 & listingCount <= 1500)
    {
      Color c = Color.web("#87CEEB");
      return c;
    }
    else if(listingCount > 1500 & listingCount <= 2000)
    {
      Color c = Color.web("#87CEFA");
      return c;
    }
    else if(listingCount > 2000 & listingCount <= 2500)
    {
      Color c = Color.web("#00BFFF");
      return c;
    }
    else if(listingCount > 2500 & listingCount <= 3500)
    {
      Color c = Color.web("#1E90FF");
      return c;
    }
    else
    {
      Color c = Color.web("#6495ED");
      return c;
    }
  }

  private void setButtonColor(Button button)
  {
    button.setBackground(new Background(new BackgroundFill(individualBoroughColor(button), null, null)));
  }

  @FXML
  private void priceRangeClick(ActionEvent event)
  {
    System.out.println("Test");
  }
}
