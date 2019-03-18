import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.*;
import javafx.fxml.*;
import javafx.scene.paint.*;
import java.util.ArrayList;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import java.util.*;
import javafx.scene.control.TableColumn.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import java.awt.Desktop;
import java.io.IOException;
import javafx.scene.image.*;
public class MapPanelApplication extends Application
{
    private ListingManager listingManager;
    private MapPanelEngine mpe;
    private ArrayList<Button> mapButtons;
    private AirbnbListing airbnbListing;

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
        listingTable.setRowFactory(tv -> {
          TableRow<AirbnbListing> row = new TableRow<>();
          row.setOnMouseClicked(event -> {
            if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY
              && event.getClickCount() == 2) {

            AirbnbListing clickedRow = row.getItem();
            airbnbListing = clickedRow;
            openWindowView(airbnbListing);
           }
         });
         return row ;
        });

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

    public void openWindowView(AirbnbListing aListing)
    {
      BorderPane singleListingView = new BorderPane();
      GridPane centerPane = new GridPane();

      Image logo = new Image("/images/airbnb-logo.png");
      ImageView logoView = new ImageView();
      logoView.setImage(logo);
      logoView.setPreserveRatio(true);
      logoView.setFitHeight(200);
      logoView.setFitWidth(200);
      singleListingView.setTop(logoView);
      singleListingView.setCenter(centerPane);
      centerPane.setAlignment(Pos.CENTER);
      centerPane.setPadding(new Insets(10, 10, 10, 10));
      centerPane.setPrefWidth(600);
      centerPane.setPrefHeight(600);
      centerPane.setVgap(10);
      centerPane.setHgap(10);

      Button viewOnMapButton = new Button("View on Map");

      viewOnMapButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
          viewOnMapButtonClicked();
        }
      });

      Label hostId = new Label("Host ID: " + aListing.getHost_id());
      Label calculatedHostListings = new Label("Total No. of Host Properties: " + Integer.toString(aListing.getCalculatedHostListingsCount()));
      Label propertyName = new Label("Airbnb Name: ");
      Label propertyNameContent = new Label(aListing.getName());
      Label propertyId = new Label("Airbnb ID: ");
      Label propertyIdContent = new Label(aListing.getId());
      Label hostName = new Label("Host Name: ");
      Label hostNameContent = new Label(aListing.getHost_name());
      Label roomType = new Label("Room Type: ");
      Label roomTypeContent = new Label(aListing.getRoom_type());
      Label price = new Label("Price per Night: ");
      Label priceContent = new Label(Integer.toString(aListing.getPrice()));
      Label minNights = new Label("Min Nights to Book: ");
      Label minNightsContent = new Label(Integer.toString(aListing.getMinimumNights()));
      Label availability365 = new Label("Availability365: ");
      Label availability365Content = new Label(Integer.toString(aListing.getAvailability365()));
      Label borough = new Label("Borough: ");
      Label boroughContent = new Label(aListing.getNeighbourhood());
      Label reviews = new Label("Reviews: ");
      Label reviewNum = new Label("Total No. of Reviews: ");
      Label reviewNumContent = new Label(Integer.toString(aListing.getNumberOfReviews()));
      Label reviewsPerMonth = new Label("Reviews per Month: ");
      Label reviewsPerMonthContent = new Label(Double.toString(aListing.getReviewsPerMonth()));
      Label lastReview = new Label("Last Review: ");
      Label lastReviewContent = new Label(aListing.getLastReview());
      Label nullLabel = new Label("no information");

      centerPane.add(hostId, 0, 0);
      centerPane.add(calculatedHostListings, 2, 0);
      centerPane.add(viewOnMapButton, 0, 1);
      centerPane.add(reviews, 0, 9);
      centerPane.add(reviewNum, 0, 10);
      centerPane.add(reviewNumContent, 1, 10);
      centerPane.add(reviewsPerMonth, 0, 11);
      centerPane.add(reviewsPerMonthContent, 1, 11);
      centerPane.add(lastReview, 0, 12);
      switch(aListing.getLastReview()){
          case (""):
          centerPane.add(nullLabel, 1, 12);
          default:
          centerPane.add(lastReviewContent, 1, 12);
        }
      centerPane.add(propertyName, 1, 1);
      centerPane.add(propertyNameContent, 2,1);
      centerPane.add(propertyId, 1, 2);
      centerPane.add(propertyIdContent, 2, 2);
      centerPane.add(hostName, 1, 3);
      centerPane.add(hostNameContent, 2, 3);
      centerPane.add(roomType, 1, 4);
      centerPane.add(roomTypeContent, 2, 4);
      centerPane.add(price, 1, 5);
      centerPane.add(priceContent, 2, 5);
      centerPane.add(minNights, 1, 6);
      centerPane.add(minNightsContent, 2, 6);
      centerPane.add(availability365, 1, 7);
      centerPane.add(availability365Content, 2, 7);
      centerPane.add(borough, 1, 8);
      centerPane.add(boroughContent, 2, 8);

      Stage stage = new Stage();
      Scene scene = new Scene(singleListingView, 600, 600);
      stage.setTitle(aListing.getName());
      stage.setScene(scene);
      stage.show();
    }

    private void viewOnMapButtonClicked()
    {
      try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/maps/place/" + airbnbListing.getLatitude() + "," + airbnbListing.getLongitude()));
          }
      catch (IOException e1) {
            System.out.println("Oops! Something's wrong with opening the map.");
            e1.printStackTrace();
          }
      catch (URISyntaxException e1) {
            System.out.println("Oops! Something's wrong with opening the map.");
            e1.printStackTrace();
        }
    }
 }
