import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import java.util.ArrayList;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.SortType;
import javafx.collections.transformation.*;
public class MapPanelEngine
{
    private Button button;
    private BackgroundFill backgroundFill;
    private Background background;
    public String getBoroughName(Button button)
    {
      switch(button.getId()){
        case "BarkingandDagenham":
          return "Barking and Dagenham";

        case "WalthamForest":
          return "Waltham Forest";

        case "TowerHamlets":
          return "Tower Hamlets";

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

    public void setButtonColor(Button button, int listingCount)
    {
      Color color;
      if(listingCount >= 0 && listingCount <= 10 )
      {
        color = Color.web("#E0FFFF");
      }
      else if(listingCount > 10 && listingCount <= 20)
      {
        color = Color.web("#AFEEEE");
      }
      else if(listingCount > 20 && listingCount <= 30)
      {
        color = Color.web("#7FFFD4");
      }
      else if(listingCount > 30 && listingCount <= 40)
      {
        color = Color.web("#40E0D0");
      }
      else if(listingCount > 40 && listingCount <= 50)
      {
        color = Color.web("#48D1CC");
      }
      else if(listingCount > 50 && listingCount <= 100)
      {
        color = Color.web("#00CED1");
      }
      else if(listingCount > 100 && listingCount <= 150)
      {
        color = Color.web("#5F9EA0");
      }
      else if(listingCount > 150 && listingCount <= 200)
      {
        color = Color.web("#4682B4");
      }
      else if(listingCount > 200 && listingCount <= 250)
      {
        color = Color.web("#B0C4DE");
      }
      else if(listingCount > 250 && listingCount <= 300)
      {
        color = Color.web("#B0E0E6");
      }
      else if(listingCount > 300 && listingCount <= 600)
      {
        color = Color.web("#ADD8E6");
      }
      else if(listingCount > 600 && listingCount <= 1000)
      {
        color = Color.web("#87CEEB");
      }
      else if(listingCount > 1000 && listingCount <= 1500)
      {
        color = Color.web("#87CEFA");
      }
      else if(listingCount > 1500 && listingCount <= 2000)
      {
        color = Color.web("#00BFFF");
      }
      else if(listingCount > 2000 && listingCount <= 2500)
      {
        color = Color.web("#1E90FF");
      }
      else if(listingCount > 2500 && listingCount <= 3500)
      {
        color = Color.web("#6495ED");
      }      
      else
      {
        color = Color.web("#4169E1");
      }
      
      backgroundFill = new BackgroundFill(color, null, null);
      background = new Background(backgroundFill);
      button.setBackground(background);
    }

    /**
     * Create an ObservableList that holds the sorting options for the drop-down box
     * @return sortOptions The list of sorting options
     */
    public ObservableList<String> getSortingOptions()
    {
        ObservableList<String> sortOptions = FXCollections.observableArrayList(
                    "Host Name Alphabetical Order",
                    "Price",
                    "Number of Reviews"
        );
        return sortOptions;
    }
    
    /**
     * Implements search functionality to search the Borough Table
     * Reference: https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
     */
    public void searchTable(ListingManager listingManager, String boroughName, TextField searchText, TableView table) 
    {        
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<AirbnbListing> filteredData = new FilteredList<>(listingManager.getBoroughListings(boroughName, AirbnbApplication.getFromValue(), AirbnbApplication.getToValue()), p -> true);
        
        // Set the filter Predicate whenever the filter changes.
        searchText.textProperty().addListener((change, oldValue, newValue) -> {
            filteredData.setPredicate(airbnbListing -> {
                // Compare host name of every property with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                // If filter text is empty, display all properties.
                return (newValue == null || newValue.isEmpty()
                        //if filter text contains or euquals to the property info, display the list
                        ||airbnbListing.getHost_name().toLowerCase().contains(lowerCaseFilter))
                        || (Integer.toString(airbnbListing.getPrice()).equals(lowerCaseFilter))
                        || (Integer.toString(airbnbListing.getMinimumNights()).equals(lowerCaseFilter));
            });
        });
        
        //Wrap the FilteredList in a SortedList
        SortedList<AirbnbListing> sortedData = new SortedList<>(filteredData);
        
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        
        table.setItems(sortedData);
    }
    
    /**
     * Sorts the table data based on drop-down box selection
     */
    public void tableSort(ComboBox sortBox, ObservableList<AirbnbListing> boroughListings, TableView table)
    {
        ObservableList<TableColumn> columns = table.getColumns();
        table.getSortOrder().removeAll(columns);
        switch(sortBox.getSelectionModel().getSelectedItem().toString()) {

            case "Host Name Alphabetical Order":
                for (TableColumn col : columns) {
                    if (col.getId().equals("hostName")) {
                        col.setSortType(SortType.ASCENDING);
                        table.getSortOrder().add(col);
                    }
                }
                break;

            case "Price":
                for (TableColumn col : columns) {
                    if (col.getId().equals("price")) {
                        col.setSortType(SortType.ASCENDING);
                        table.getSortOrder().add(col);
                    }
                }
                break;

            case "Number of Reviews":
                for (TableColumn col : columns) {
                    if (col.getId().equals("review")) {
                        col.setSortType(SortType.ASCENDING);
                        table.getSortOrder().add(col);
                    }
                }
                break;

            default:
                System.out.println("Column not found! No Action");
                break;
        }
        updateTableListings(boroughListings, table);
    }
    
    /**
     * Re-orders the ObservableList to the order shown in the
     * table view
     * @param boroughListings The ObservableList to be re-ordered
     * @param table The TableView that the ObservableList belongs to
     */
    public void updateTableListings(ObservableList<AirbnbListing> boroughListings, TableView table) 
    {
        boroughListings.clear();
        for (Object obj : table.getItems()) {
            AirbnbListing listing = (AirbnbListing) obj;
            System.out.println(listing.getHost_name());
            boroughListings.add(listing);
        }        
    }    
}
