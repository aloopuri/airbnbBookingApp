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
import java.util.ArrayList;

public class MapPanelApplication extends Application
{
  private ListingManager listingManager;
  private AirbnbDataLoader dataLoader;
  private Color color;
  private BackgroundFill backgroundFill;
  private Background background;
  private ArrayList<Button> listOfButtons;
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
  @FXML Button FiveToTen;
  @FXML Button TenToTwenty;
  @FXML Button TwentyToThirty;
  @FXML Button ThirtyToFourty;
  @FXML Button FortyToFifty;
  @FXML Button FiftyToOneH;
  @FXML Button OneHToThreeH;
  @FXML Button ThreeHToSixH;
  @FXML Button SixHToOneT;
  @FXML Button AllPrices;

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
  private void initialize()
  {
    showViewInRange(0, getListOfAllPrices().size());
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

  @FXML
  private void belowTenRangeClick(ActionEvent event)
  {
    showViewInRange(0, 10);
  }

  @FXML
  private void TenToTwentyRangeClick(ActionEvent event)
  {
    showViewInRange(10, 20);
  }

  @FXML
  private void TwentyToThirtyRangeClick(ActionEvent event)
  {
    showViewInRange(20, 30);
  }

  @FXML
  private void ThirtyToFourtyRangeClick(ActionEvent event)
  {
    showViewInRange(30, 40);
  }

  @FXML
  private void FourtyToFiftyRangeClick(ActionEvent event)
  {
    showViewInRange(40, 50);
  }

  @FXML
  private void FiftyToOneHRangeClick(ActionEvent event)
  {
    showViewInRange(50, 100);
  }

  @FXML
  private void OneHToThreeHRangeClick(ActionEvent event)
  {
    showViewInRange(100, 300);
  }

  @FXML
  private void ThreeHToSixHRangeClick(ActionEvent event)
  {
    showViewInRange(300, 600);
  }

  @FXML
  private void SixHToOneTRangeClick(ActionEvent event)
  {
      showViewInRange(600, 1000);
  }

  @FXML
  private void AllPricesClick(ActionEvent event)
  {
      showViewInRange(0, getListOfAllPrices().size());
  }

  private ArrayList<Integer> getListOfAllPrices()
  {
    return getListingManager().getAllPrices();
  }

  private void showViewInRange(int LowerBound, int upperBound)
  {
    //use this loading method outisde the loop so the program runs fasters
    listingManager = getListingManager();
    ArrayList<Integer> listOfSelectedPricesInBorough = new ArrayList<>();

    for(Button button: buttons()){
      for(Integer i: listingManager.pricesInBorough(nameOfBorough(button))){
        if(i >= LowerBound & i < upperBound){
          listOfSelectedPricesInBorough.add(i);
        }
      }
      ButtonColor(button, listOfSelectedPricesInBorough.size());
      setButtonColor(button);
    }
  }

  private ListingManager getListingManager()
  {
    dataLoader = new AirbnbDataLoader();
    listingManager = new ListingManager(dataLoader.load());
    return listingManager;
  }

  private void ButtonColor(Button button, int listingCount)
  {
    if(listingCount > 300 & listingCount <= 600)
    {
      color = Color.web("#ADD8E6");
    }
    else if(listingCount > 600 & listingCount <= 1000)
    {
      color = Color.web("#87CEEB");
    }
    else if(listingCount > 1000 & listingCount <= 1500)
    {
      color = Color.web("#87CEFA");
    }
    else if(listingCount > 1500 & listingCount <= 2000)
    {
      color = Color.web("#00BFFF");
    }
    else if(listingCount > 2000 & listingCount <= 2500)
    {
      color = Color.web("#1E90FF");
    }
    else if(listingCount > 2500 & listingCount <= 3500)
    {
      color = Color.web("#6495ED");
    }
    else if(listingCount >= 0 & listingCount <= 10 )
    {
      color = Color.web("#E0FFFF");
    }
    else if(listingCount > 10 & listingCount <= 20)
    {
      color = Color.web("#AFEEEE");
    }
    else if(listingCount > 20 & listingCount <= 30)
    {
      color = Color.web("#7FFFD4");
    }
    else if(listingCount > 30 & listingCount <= 40)
    {
      color = Color.web("#40E0D0");
    }
    else if(listingCount > 40 & listingCount <= 50)
    {
      color = Color.web("#48D1CC");
    }
    else if(listingCount > 50 & listingCount <= 100)
    {
      color = Color.web("#00CED1");
    }
    else if(listingCount > 100 & listingCount <= 150)
    {
      color = Color.web("#5F9EA0");
    }
    else if(listingCount > 150 & listingCount <= 200)
    {
      color = Color.web("#4682B4");
    }
    else if(listingCount > 200 & listingCount <= 250)
    {
      color = Color.web("#B0C4DE");
    }
    else if(listingCount > 250 & listingCount <= 300)
    {
      color = Color.web("#B0E0E6");
    }
    else
    {
      color = Color.web("#4169E1");
    }
  }

  private  void setButtonColor(Button button)
  {
    backgroundFill = new BackgroundFill(color, null, null);
    background = new Background(backgroundFill);
    button.setBackground(background);
  }

  private ArrayList<Button> buttons()
  {
    listOfButtons = new ArrayList<>();

    listOfButtons.add(Sutton);
    listOfButtons.add(Haringey);
    listOfButtons.add(Havering);
    listOfButtons.add(Harrow);
    listOfButtons.add(BarkingandDagenham);
    listOfButtons.add(Greenwich);
    listOfButtons.add(Bexley);
    listOfButtons.add(Enfield);
    listOfButtons.add(Barnet);
    listOfButtons.add(WalthamForest);
    listOfButtons.add(Hillingdon);
    listOfButtons.add(Ealing);
    listOfButtons.add(Brent);
    listOfButtons.add(Camden);
    listOfButtons.add(Westminster);
    listOfButtons.add(Islington);
    listOfButtons.add(Hackney);
    listOfButtons.add(TowerHamlets);
    listOfButtons.add(Redbridge);
    listOfButtons.add(KensingtonandChelsea);
    listOfButtons.add(Newham);
    listOfButtons.add(Hounslow);
    listOfButtons.add(HammersmithandFulham);
    listOfButtons.add(Wandsworth);
    listOfButtons.add(CityofLondon);
    listOfButtons.add(RichmonduponThames);
    listOfButtons.add(Merton);
    listOfButtons.add(KingstonuponThames);
    listOfButtons.add(Lambeth);
    listOfButtons.add(Southwark);
    listOfButtons.add(Croydon);
    listOfButtons.add(Lewisham);
    listOfButtons.add(Bromley);

    return listOfButtons;
  }

  @FXML
  private void ENFIClick(ActionEvent event)
  {

  }
 }
