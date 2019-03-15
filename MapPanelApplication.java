import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.*;
import javafx.scene.paint.*;
import java.util.ArrayList;
import java.io.IOException;

public class MapPanelApplication extends Application
{
  private ListingManager listingManager;
  private MapPanelEngine mpe;
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
  private void initialize()
  {
    mpe = new MapPanelEngine();
    showViewInRange(0, mpe.getListOfAllPrices().size());
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
      mpe = new MapPanelEngine();
      showViewInRange(0, mpe.getListOfAllPrices().size());
  }

  private void showViewInRange(int LowerBound, int upperBound)
  {
    //use this loading method outisde the loop so the program runs fasters
    mpe = new MapPanelEngine();
    listingManager = mpe.getListingManager();
    ArrayList<Integer> listOfSelectedPricesInBorough = new ArrayList<>();

    for(Button button: buttons()){
      for(Integer i: listingManager.pricesInBorough(mpe.nameOfBorough(button))){
        if(i >= LowerBound & i < upperBound){
          listOfSelectedPricesInBorough.add(i);
        }
      }
      mpe.ButtonColor(button, listOfSelectedPricesInBorough.size());
      mpe.setButtonColor(button);
    }
  }

  @FXML
  private void ENFIClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void BARNClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HRGYClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void WALTClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HRRWClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void BRENClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void CAMDClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void ISLIClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HACKClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void REDBClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HAVEClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HILLClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void EALIClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void KENSClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void WSTMClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void TOWHClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void NEWHClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void BARKClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HOUNClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void HAMMClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void WANDClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void CITYClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void GWCHClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void BEXLClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void RICHClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void MERTClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void LAMBClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void STHWClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void LEWSClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void KINGClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void SUTTClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void CROYClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  @FXML
  private void BROMClick(ActionEvent event)
  {
    openNewWindowForListingsInBorough(Enfield);
  }

  private void openNewWindowForListingsInBorough(Button button)
  {
    try{
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListingsInBoroughView.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();

      stage.setTitle(mpe.nameOfBorough(button));
      stage.setScene(new Scene((root)));
      stage.show();
    }
    catch(Exception e){
      System.out.println("Oops! Something's wrong with opening the new window.");
    }
  }
 }
