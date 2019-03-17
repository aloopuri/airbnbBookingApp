import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.*;
import javafx.scene.paint.*;
import java.util.ArrayList;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import java.util.*;
import javafx.scene.control.TableColumn.*;
public class MapPanelApplication extends Application
{
    private ListingManager listingManager;
    private MapPanelEngine mpe;
    private ArrayList<Button> mapButtons;

    // Map Button declarations
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

    public MapPanelApplication()
    {
        ArrayList<AirbnbListing> listings = new ArrayList<AirbnbListing>();
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        listingManager = new ListingManager(listings);
        mpe = new MapPanelEngine();
    }

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

    private void initializeButtons()
    {
        mapButtons = new ArrayList<Button>();
        mapButtons.add(Sutton);
        mapButtons.add(Haringey);
        mapButtons.add(Havering);
        mapButtons.add(Harrow);
        mapButtons.add(BarkingandDagenham);
        mapButtons.add(Greenwich);
        mapButtons.add(Bexley);
        mapButtons.add(Enfield);
        mapButtons.add(Barnet);
        mapButtons.add(WalthamForest);
        mapButtons.add(Hillingdon);
        mapButtons.add(Ealing);
        mapButtons.add(Brent);
        mapButtons.add(Camden);
        mapButtons.add(Westminster);
        mapButtons.add(Islington);
        mapButtons.add(Hackney);
        mapButtons.add(TowerHamlets);
        mapButtons.add(Redbridge);
        mapButtons.add(KensingtonandChelsea);
        mapButtons.add(Newham);
        mapButtons.add(Hounslow);
        mapButtons.add(HammersmithandFulham);
        mapButtons.add(Wandsworth);
        mapButtons.add(CityofLondon);
        mapButtons.add(RichmonduponThames);
        mapButtons.add(Merton);
        mapButtons.add(KingstonuponThames);
        mapButtons.add(Lambeth);
        mapButtons.add(Southwark);
        mapButtons.add(Croydon);
        mapButtons.add(Lewisham);
        mapButtons.add(Bromley);
    }

    /**
     * @return The list of buttons in the map panel
     */
    private ArrayList<Button> getButtons()
    {
        return mapButtons;
    }

    @FXML
    private void initialize()
    {
        showViewInRange(0, listingManager.getAllPrices().size());
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
        showViewInRange(0, listingManager.getAllPrices().size());
    }

    private void showViewInRange(int lowerBound, int upperBound)
    {
        initializeButtons();
        //use this loading method outisde the loop so the program runs faster
        ArrayList<Integer> selectedBoroughPrices = new ArrayList<>();
        for(Button button: getButtons()){
            for(Integer price: listingManager.getBoroughPrices(mpe.getBoroughName(button))){
                if(price >= lowerBound & price <= upperBound){
                    selectedBoroughPrices.add(price);
                }
            }
            mpe.getButtonColor(button, selectedBoroughPrices.size());
            mpe.setButtonColor(button);
        }
    }

    /**
     * Creates and displays the borough details window
     * @param button The borough button clicked
     */
    private void openBoroughWindow(Button button)
    {
        String boroughName = mpe.getBoroughName(button);

        TableView<AirbnbListing> listingTable = new TableView();
        listingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Host-Name column
        TableColumn<AirbnbListing, String> hostNameCol = new TableColumn<>("Host Name");
        hostNameCol.setId("hostName");
        hostNameCol.setCellValueFactory(new PropertyValueFactory<>("host_name"));
        hostNameCol.setReorderable(false);

        // Price column
        TableColumn<AirbnbListing, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setId("price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setReorderable(false);

        // Minimum nights column
        TableColumn<AirbnbListing, Integer> nightsCol = new TableColumn<>("Minimum Number of Nights to Book");
        nightsCol.setId("nights");
        nightsCol.setCellValueFactory(new PropertyValueFactory<>("minimumNights"));
        nightsCol.setReorderable(false);

        // Reviews column
        TableColumn<AirbnbListing, Integer> reviewCol = new TableColumn<>("Number of Reviews");
        reviewCol.setId("review");
        reviewCol.setCellValueFactory(new PropertyValueFactory<>("numberOfReviews"));
        reviewCol.setReorderable(false);

        // Populate table
        listingTable.getColumns().addAll(hostNameCol, priceCol, nightsCol, reviewCol);
        listingTable.setItems(listingManager.getBoroughListings(boroughName));

        ComboBox sortingBox = new ComboBox();
        sortingBox.setItems(mpe.getSortingOptions());
        sortingBox.setMaxWidth(Integer.MAX_VALUE);
        sortingBox.setOnAction(e -> mpe.tableSort(sortingBox, listingManager.getBoroughListings(boroughName), listingTable));

        ToolBar sortBar = new ToolBar();
        sortBar.getItems().add(new Label("Sort by:"));
        sortBar.getItems().add(sortingBox);

        BorderPane boroughPane = new BorderPane();
        boroughPane.setCenter(listingTable);
        boroughPane.setTop(sortBar);

        Stage boroughWindow = new Stage();
        boroughWindow.setScene(new Scene(boroughPane, 500, 500));
        boroughWindow.setTitle(boroughName + " Properties");
        boroughWindow.show();
    }

    // ----------- Following methods implement click events for each map button -----------
    @FXML
    private void ENFIClick(ActionEvent event)
    {
        openBoroughWindow(Enfield);
    }

    @FXML
    private void BARNClick(ActionEvent event)
    {
        openBoroughWindow(Barnet);
    }

    @FXML
    private void HRGYClick(ActionEvent event)
    {
        openBoroughWindow(Haringey);
    }

    @FXML
    private void WALTClick(ActionEvent event)
    {
        openBoroughWindow(WalthamForest);
    }

    @FXML
    private void HRRWClick(ActionEvent event)
    {
        openBoroughWindow(Harrow);
    }

    @FXML
    private void BRENClick(ActionEvent event)
    {
        openBoroughWindow(Brent);
    }

    @FXML
    private void CAMDClick(ActionEvent event)
    {
        openBoroughWindow(Camden);
    }

    @FXML
    private void ISLIClick(ActionEvent event)
    {
        openBoroughWindow(Islington);
    }

    @FXML
    private void HACKClick(ActionEvent event)
    {
        openBoroughWindow(Hackney);
    }

    @FXML
    private void REDBClick(ActionEvent event)
    {
        openBoroughWindow(Redbridge);
    }

    @FXML
    private void HAVEClick(ActionEvent event)
    {
        openBoroughWindow(Havering);
    }

    @FXML
    private void HILLClick(ActionEvent event)
    {
        openBoroughWindow(Hillingdon);
    }

    @FXML
    private void EALIClick(ActionEvent event)
    {
        openBoroughWindow(Ealing);
    }

    @FXML
    private void KENSClick(ActionEvent event)
    {
        openBoroughWindow(KensingtonandChelsea);
    }

    @FXML
    private void WSTMClick(ActionEvent event)
    {
        openBoroughWindow(Westminster);
    }

    @FXML
    private void TOWHClick(ActionEvent event)
    {
        openBoroughWindow(TowerHamlets);
    }

    @FXML
    private void NEWHClick(ActionEvent event)
    {
        openBoroughWindow(Newham);
    }

    @FXML
    private void BARKClick(ActionEvent event)
    {
        openBoroughWindow(BarkingandDagenham);
    }

    @FXML
    private void HOUNClick(ActionEvent event)
    {
        openBoroughWindow(Hounslow);
    }

    @FXML
    private void HAMMClick(ActionEvent event)
    {
        openBoroughWindow(HammersmithandFulham);
    }

    @FXML
    private void WANDClick(ActionEvent event)
    {
        openBoroughWindow(Wandsworth);
    }

    @FXML
    private void CITYClick(ActionEvent event)
    {
        openBoroughWindow(CityofLondon);
    }

    @FXML
    private void GWCHClick(ActionEvent event)
    {
        openBoroughWindow(Greenwich);
    }

    @FXML
    private void BEXLClick(ActionEvent event)
    {
        openBoroughWindow(Bexley);
    }

    @FXML
    private void RICHClick(ActionEvent event)
    {
        openBoroughWindow(RichmonduponThames);
    }

    @FXML
    private void MERTClick(ActionEvent event)
    {
        openBoroughWindow(Merton);
    }

    @FXML
    private void LAMBClick(ActionEvent event)
    {
        openBoroughWindow(Lambeth);
    }

    @FXML
    private void STHWClick(ActionEvent event)
    {
        openBoroughWindow(Southwark);
    }

    @FXML
    private void LEWSClick(ActionEvent event)
    {
        openBoroughWindow(Lewisham);
    }

    @FXML
    private void KINGClick(ActionEvent event)
    {
        openBoroughWindow(KingstonuponThames);
    }

    @FXML
    private void SUTTClick(ActionEvent event)
    {
        openBoroughWindow(Sutton);
    }

    @FXML
    private void CROYClick(ActionEvent event)
    {
        openBoroughWindow(Croydon);
    }

    @FXML
    private void BROMClick(ActionEvent event)
    {
        openBoroughWindow(Bromley);
    }

    public void openSingleAirbnbWindow()
    {
      TableView<AirbnbListing> listingTable = new TableView();
      listingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

      TableColumn<AirbnbListing, String> airbnbIdCol = new TableColumn<>("Airbnb ID");
      airbnbIdCol.setId("airbnbId");
      airbnbIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
      airbnbIdCol.setReorderable(false);

      TableColumn<AirbnbListing, String> airbnbNameCol = new TableColumn<>("Airbnb Name");
      airbnbNameCol.setId("airbnbName");
      airbnbNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
      airbnbNameCol.setReorderable(false);

      TableColumn<AirbnbListing, String> hostIdCol = new TableColumn<>("Host ID");
      hostIdCol.setId("hostId");
      hostIdCol.setCellValueFactory(new PropertyValueFactory<>("host_id"));
      hostIdCol.setReorderable(false);

      // Host-Name column
      TableColumn<AirbnbListing, String> hostNameCol = new TableColumn<>("Host Name");
      hostNameCol.setId("hostName");
      hostNameCol.setCellValueFactory(new PropertyValueFactory<>("host_name"));
      hostNameCol.setReorderable(false);

      TableColumn<AirbnbListing, String> boroughCol = new TableColumn<>("Borough");
      boroughCol.setId("borough");
      boroughCol.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
      boroughCol.setReorderable(false);

      TableColumn<AirbnbListing, String> latitudeCol = new TableColumn<>("Latitude");
      latitudeCol.setId("latitude");
      latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
      latitudeCol.setReorderable(false);

      TableColumn<AirbnbListing, String> longitudeCol = new TableColumn<>("Longitude");
      longitudeCol.setId("longitude");
      longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
      longitudeCol.setReorderable(false);

      TableColumn<AirbnbListing, String> roomTypeCol = new TableColumn<>("Room Type");
      roomTypeCol.setId("roomType");
      roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("room_type"));
      roomTypeCol.setReorderable(false);

      // Price column
      TableColumn<AirbnbListing, Integer> priceCol = new TableColumn<>("Price");
      priceCol.setId("price");
      priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
      priceCol.setReorderable(false);

      // Reviews column
      TableColumn<AirbnbListing, Integer> reviewNumCol = new TableColumn<>("Number of reviews");
      reviewNumCol.setId("reviewNum");
      reviewNumCol.setCellValueFactory(new PropertyValueFactory<>("numberOfReviews"));
      reviewNumCol.setReorderable(false);

      TableColumn<AirbnbListing, String> reviewsPerMonthCol = new TableColumn<>("Reviews per Month");
      reviewsPerMonthCol.setId("reviewsPerMonth");
      reviewsPerMonthCol.setCellValueFactory(new PropertyValueFactory<>("reviewsPerMonth"));
      reviewsPerMonthCol.setReorderable(false);

      TableColumn<AirbnbListing, String> lastReviewCol = new TableColumn<>("Last Review");
      lastReviewCol.setId("lastReview");
      lastReviewCol.setCellValueFactory(new PropertyValueFactory<>("lastReview"));
      lastReviewCol.setReorderable(false);

      // Minimum nights column
      TableColumn<AirbnbListing, Integer> nightsCol = new TableColumn<>("Minimum no. nights stay");
      nightsCol.setId("nights");
      nightsCol.setCellValueFactory(new PropertyValueFactory<>("minimumNights"));
      nightsCol.setReorderable(false);

      TableColumn<AirbnbListing, String> calculatedHostListingsCol = new TableColumn<>("Calculated Host Listings");
      calculatedHostListingsCol.setId("calculatedHostListings");
      calculatedHostListingsCol.setCellValueFactory(new PropertyValueFactory<>("calculatedHostListingsCount"));
      calculatedHostListingsCol.setReorderable(false);

      TableColumn<AirbnbListing, String> availability365Col = new TableColumn<>("Availability 365");
      availability365Col.setId("availability365");
      availability365Col.setCellValueFactory(new PropertyValueFactory<>("availability365"));
      availability365Col.setReorderable(false);

      // Populate table
      listingTable.getColumns().addAll(hostIdCol, hostNameCol, calculatedHostListingsCol, airbnbIdCol, airbnbNameCol, roomTypeCol, priceCol, nightsCol, availability365Col, boroughCol, latitudeCol, longitudeCol, reviewNumCol,  reviewsPerMonthCol, lastReviewCol);
      listingTable.setItems(listingManager.getBoroughListings("Sutton"));

      ComboBox sortingBox = new ComboBox();
      sortingBox.setItems(mpe.getSortingOptions());
      sortingBox.setMaxWidth(Integer.MAX_VALUE);
      sortingBox.setOnAction(e -> mpe.tableSort(sortingBox, listingManager.getBoroughListings("Sutton"), listingTable));

      ToolBar sortBar = new ToolBar();
      sortBar.getItems().add(new Label("Sort by:"));
      sortBar.getItems().add(sortingBox);

      BorderPane boroughPane = new BorderPane();
      boroughPane.setCenter(listingTable);
      boroughPane.setTop(sortBar);

      Stage boroughWindow = new Stage();
      boroughWindow.setScene(new Scene(boroughPane, 500, 500));
      boroughWindow.setTitle("Sutton" + " Properties");
      boroughWindow.show();
    }
 }
