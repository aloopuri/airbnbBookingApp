import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.*;
import javafx.fxml.*;
import java.util.ArrayList;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import javafx.scene.control.TableColumn.*;
import javafx.geometry.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import java.util.ResourceBundle;
public class MapPanel
{
    private ListingManager listingManager;
    private MapPanelEngine mpe;
    private ArrayList<Button> mapButtons;
    private ObservableList<AirbnbListing> currentPropertyCollection;
    private Scene listingScene;
    private Stage listingStage;

    private int listingIndex = -1;

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

    public MapPanel(ListingManager listingManager)
    {
        this.listingManager = listingManager;
        mpe = new MapPanelEngine();
        listingStage = new Stage();
        initializeButtons();
    }

    public BorderPane createMap() throws Exception
    {
        URL url = getClass().getResource("MapView.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(c -> { return this; });
        Parent root = loader.load();
        return (BorderPane)root;
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
    public ArrayList<Button> getButtons()
    {
        return mapButtons;
    }

    @FXML
    public void initialize()
    {
        initializeButtons();
    }

    public void showViewInRange(int lowerBound, int upperBound)
    {
        //use this loading method outisde the loop so the program runs faster
        ArrayList<Integer> selectedBoroughPrices = new ArrayList<>();
        for(Button button: getButtons()){
            selectedBoroughPrices.clear();
            for(Integer price: listingManager.getBoroughPrices(mpe.getBoroughName(button))){
                if(price >= lowerBound && price <= upperBound){
                    selectedBoroughPrices.add(price);
                }
            }
            mpe.setButtonColor(button, selectedBoroughPrices.size());
        }
    }

    /**
     * Creates and displays the borough details window
     * @param button The borough button clicked
     */
    private void openBoroughWindow(Button button)
    {
        String boroughName = mpe.getBoroughName(button);

        // Create Table - Adding listener to appropriately sort the observablelist
        TableView<AirbnbListing> listingTable = new TableView();
        listingTable.getSortOrder().addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change change)
            {
                mpe.updateTableListings(currentPropertyCollection, listingTable);
            }
        });
        listingTable.setId("listingTable");
        listingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        listingTable.setRowFactory(tv -> {
          TableRow<AirbnbListing> row = new TableRow<>();
          row.setOnMouseClicked(event -> {
            if (! row.isEmpty() && event.getButton() == MouseButton.PRIMARY
              && event.getClickCount() == 1) {
                listingScene = new Scene(getListingView(row.getItem()), 700, 450);
                listingScene.getStylesheets().addAll(this.getClass().getResource("MapLayout.css").toExternalForm());
                listingStage.setTitle("Property in " + boroughName);
                listingStage.getIcons().add(new Image("/images/airbnb-small.png"));
                listingStage.setScene(listingScene);
                listingStage.show();
              }
            });
         return row;
        });

        // Host-Name column
        TableColumn<AirbnbListing, String> hostNameCol = new TableColumn<>("Host Name");
        hostNameCol.getStyleClass().clear();
        hostNameCol.getStyleClass().add("columnStyle");
        hostNameCol.setId("hostName");
        hostNameCol.setCellValueFactory(new PropertyValueFactory<>("host_name"));
        hostNameCol.setReorderable(false);
        hostNameCol.sortTypeProperty().addListener(o -> FXCollections.reverse(currentPropertyCollection));

        // Price column
        TableColumn<AirbnbListing, Integer> priceCol = new TableColumn<>("Price");
        priceCol.getStyleClass().clear();
        priceCol.getStyleClass().add("columnStyle");
        priceCol.setId("price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setReorderable(false);
        priceCol.sortTypeProperty().addListener(o -> FXCollections.reverse(currentPropertyCollection));

        // Minimum nights column
        TableColumn<AirbnbListing, Integer> nightsCol = new TableColumn<>("Minimum Number of Nights to Book");
        nightsCol.getStyleClass().clear();
        nightsCol.getStyleClass().add("columnStyle");
        nightsCol.setId("nights");
        nightsCol.setCellValueFactory(new PropertyValueFactory<>("minimumNights"));
        nightsCol.setReorderable(false);
        nightsCol.sortTypeProperty().addListener(o -> FXCollections.reverse(currentPropertyCollection));

        // Reviews column
        TableColumn<AirbnbListing, Integer> reviewCol = new TableColumn<>("Number of Reviews");
        reviewCol.getStyleClass().clear();
        reviewCol.getStyleClass().add("columnStyle");
        reviewCol.setId("review");
        reviewCol.setCellValueFactory(new PropertyValueFactory<>("numberOfReviews"));
        reviewCol.setReorderable(false);
        reviewCol.sortTypeProperty().addListener(o -> FXCollections.reverse(currentPropertyCollection));

        // Populate table
        currentPropertyCollection = listingManager.getBoroughListings(boroughName, AirbnbApplication.getFromValue(), AirbnbApplication.getToValue());
        listingTable.getColumns().addAll(hostNameCol, priceCol, nightsCol, reviewCol);
        listingTable.setItems(currentPropertyCollection);

        // Sorting drop-down
        ComboBox sortingBox = new ComboBox();
        sortingBox.getStyleClass().add("tableViewBox");
        sortingBox.setPromptText("❤ choose a range ❤");
        sortingBox.setItems(mpe.getSortingOptions());
        sortingBox.setMaxWidth(Integer.MAX_VALUE);
        sortingBox.setOnAction(e -> mpe.tableSort(sortingBox, currentPropertyCollection, listingTable));

        // Searching field
        TextField searchText = new TextField();
        searchText.setPromptText("❤ type here ❤");
        searchText.getStyleClass().add("tableViewBox");

        ToolBar sortBar = new ToolBar();
        Label sortByLabel = new Label("Sort by:");
        Label searchLabel = new Label("Search by Host Name/Price/MinNights: ");
        sortByLabel.getStyleClass().add("singlePropertyLabel");
        searchLabel.getStyleClass().add("singlePropertyLabel");
        sortBar.getItems().addAll(sortByLabel, sortingBox, new Region(), searchLabel, searchText);
        sortBar.setId("sortBar");

        // Add search functionality
        mpe.searchTable(listingManager, boroughName, searchText, listingTable);

        //Set up the pane
        BorderPane boroughPane = new BorderPane();
        boroughPane.setId("boroughPane");
        boroughPane.setCenter(listingTable);
        boroughPane.setTop(sortBar);

        //Set up the scene and stage for this window
        Stage boroughWindow = new Stage();
        Scene scene = new Scene(boroughPane, 1000, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("MapLayout.css").toExternalForm());
        boroughWindow.setScene(scene);
        boroughWindow.setTitle(boroughName + " Properties");
        boroughWindow.getIcons().add(new Image("/images/airbnb-small.png"));
        boroughWindow.show();
    }

    /**
     * Creates the single listing view that shows when
     * a listing is selected from the table
     * @param aListing The Airbnb listing whose data will be displayed
     * @return The listing view BorderPane
     */
    public BorderPane getListingView(AirbnbListing aListing)
    {
      if (currentPropertyCollection != null) {
          listingIndex = currentPropertyCollection.indexOf(aListing);
      }

      //create panes
      BorderPane singleListingView = new BorderPane();
      singleListingView.setId("singleListingView");
      GridPane centerPane = new GridPane();

      //the GridPane is wrapped in the BorderPane in order to keep
      //the contents in the middle of the screen at all time
      singleListingView.setCenter(centerPane);
      centerPane.setAlignment(Pos.CENTER);
      centerPane.setPadding(new Insets(10, 10, 10, 10));
      centerPane.setPrefWidth(600);
      centerPane.setPrefHeight(600);
      centerPane.setVgap(10);
      centerPane.setHgap(10);

      //create new button
      Button viewOnMapButton = new Button("View on Map");
      viewOnMapButton.getStyleClass().add("singlePropertyButton");
      viewOnMapButton.setPrefSize(140,12);
      Button previousButton = new Button("Previous Property");
      previousButton.getStyleClass().add("singlePropertyButton");
      previousButton.setPrefSize(140, 12);
      Button nextButton = new Button("Next Property");
      nextButton.getStyleClass().add("singlePropertyButton");
      nextButton.setPrefSize(140, 12);

      //set button action
      viewOnMapButton.setOnAction(e -> viewOnMapButtonClicked(aListing.getLatitude(), aListing.getLongitude()));
      previousButton.setOnAction(e -> previousButtonClicked());
      nextButton.setOnAction(e -> nextButtonClicked());

      //create new labels
      Label hostId = new Label("Host ID: " + aListing.getHost_id());
      hostId.getStyleClass().add("singlePropertyLabel");
      Label calculatedHostListings = new Label("Total No. of Host Properties: " + Integer.toString(aListing.getCalculatedHostListingsCount()));
      calculatedHostListings.getStyleClass().add("singlePropertyLabel");
      Label propertyName = new Label("Airbnb Name: ");
      propertyName.getStyleClass().add("singlePropertyLabel");
      Label propertyNameContent = new Label(aListing.getName());
      propertyNameContent.getStyleClass().add("singlePropertyLabel");
      Label propertyId = new Label("Airbnb ID: ");
      propertyId.getStyleClass().add("singlePropertyLabel");
      Label propertyIdContent = new Label(aListing.getId());
      propertyIdContent.getStyleClass().add("singlePropertyLabel");
      Label hostName = new Label("Host Name: ");
      hostName.getStyleClass().add("singlePropertyLabel");
      Label hostNameContent = new Label(aListing.getHost_name());
      hostNameContent.getStyleClass().add("singlePropertyLabel");
      Label roomType = new Label("Room Type: ");
      roomType.getStyleClass().add("singlePropertyLabel");
      Label roomTypeContent = new Label(aListing.getRoom_type());
      roomTypeContent.getStyleClass().add("singlePropertyLabel");
      Label price = new Label("Price per Night: ");
      price.getStyleClass().add("singlePropertyLabel");
      Label priceContent = new Label(Integer.toString(aListing.getPrice()));
      priceContent.getStyleClass().add("singlePropertyLabel");
      Label minNights = new Label("Min Nights to Book: ");
      minNights.getStyleClass().add("singlePropertyLabel");
      Label minNightsContent = new Label(Integer.toString(aListing.getMinimumNights()));
      minNightsContent.getStyleClass().add("singlePropertyLabel");
      Label availability365 = new Label("Availability365: ");
      availability365.getStyleClass().add("singlePropertyLabel");
      Label availability365Content = new Label(Integer.toString(aListing.getAvailability365()));
      availability365Content.getStyleClass().add("singlePropertyLabel");
      Label borough = new Label("Borough: ");
      borough.getStyleClass().add("singlePropertyLabel");
      Label boroughContent = new Label(aListing.getNeighbourhood());
      boroughContent.getStyleClass().add("singlePropertyLabel");
      Label reviews = new Label("Reviews: ");
      reviews.getStyleClass().add("singlePropertyLabel");
      Label reviewNum = new Label("Total No. of Reviews: ");
      reviewNum.getStyleClass().add("singlePropertyLabel");
      Label reviewNumContent = new Label(Integer.toString(aListing.getNumberOfReviews()));
      reviewNumContent.getStyleClass().add("singlePropertyLabel");
      Label reviewsPerMonth = new Label("Reviews per Month: ");
      reviewsPerMonth.getStyleClass().add("singlePropertyLabel");
      Label reviewsPerMonthContent = new Label(Double.toString(aListing.getReviewsPerMonth()));
      reviewsPerMonthContent.getStyleClass().add("singlePropertyLabel");
      Label lastReview = new Label("Last Review: ");
      lastReview.getStyleClass().add("singlePropertyLabel");
      Label lastReviewContent = new Label(aListing.getLastReview());
      lastReviewContent.getStyleClass().add("singlePropertyLabel");
      Label nullLabel = new Label("no information");
      nullLabel.getStyleClass().add("singlePropertyLabel");

      //add labels to specific positions on centerPane
      centerPane.add(hostId, 0, 0);
      centerPane.add(calculatedHostListings, 2, 0);
      centerPane.add(viewOnMapButton, 0, 3);
      centerPane.add(previousButton, 0 , 2);
      centerPane.add(nextButton, 0 , 1);
      centerPane.add(reviews, 0, 9);
      centerPane.add(reviewNum, 0, 10);
      centerPane.add(reviewNumContent, 1, 10);
      centerPane.add(reviewsPerMonth, 0, 11);
      centerPane.add(reviewsPerMonthContent, 1, 11);
      centerPane.add(lastReview, 0, 12);
      //some lastReviews are empty
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

      return singleListingView;
    }

    private void changeListingView(BorderPane listingView)
    {
        listingScene.setRoot(listingView);
    }

    private void viewOnMapButtonClicked(double lat, double lon)
    {
        Stage mapStage = new Stage();
        Scene mapScene = new Scene(new GoogleMapView(listingManager).createGoogleMap(lat, lon));
        mapScene.getStylesheets().addAll(this.getClass().getResource("MapLayout.css").toExternalForm());
        mapStage.setScene(mapScene);
        mapStage.setTitle("Property Map Viewer");
        mapStage.getIcons().add(new Image("/images/airbnb-small.png"));
        mapStage.show();
    }

    /**
     * Move to the next property on the map
     */
    private void nextButtonClicked()
    {
      listingIndex++;
      if (listingIndex < currentPropertyCollection.size()) {
          AirbnbListing listing = currentPropertyCollection.get(listingIndex);
          changeListingView(getListingView(listing));
      }
      else {
          listingIndex = -1; // reset index
          nextButtonClicked();
      }
    }

    /**
     * Move to the previous property on the map
     */
    private void previousButtonClicked()
    {
      listingIndex--;
      if (listingIndex >= 0) {
          AirbnbListing listing = currentPropertyCollection.get(listingIndex);
          changeListingView(getListingView(listing));
      }
      else {
          listingIndex = currentPropertyCollection.size(); // wrap to end listing
          previousButtonClicked();
      }
    }

    /**
     * @return The listing scene
     */
    public Scene getListingScene()
    {
        return listingScene;
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
 }
