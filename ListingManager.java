import java.util.*;
import javafx.collections.*;
/**
 * Write a description of class listingManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ListingManager
{
    private ArrayList<AirbnbListing> listings;
    
    // used to hold the listings based on the price range the user chose
    private ArrayList<AirbnbListing> currentListings;
    
    /**
     * Constructor for objects of class listingManager
     */
    public ListingManager(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
        currentListings = new ArrayList<AirbnbListing>();
    }

    /**
     * @return The listings ArrayList
     */
    public ArrayList<AirbnbListing> getListings()
    {
        return listings;
    }
    
    /**
     * Returns current listings
     */
    public ArrayList<AirbnbListing> getCurrentListings()
    {
        return currentListings;
    }
    
    /**
     * This creates a list of all listings which are in the selected range
     */
    public void updateUserRangeListings(int from, int to)
    {
        currentListings.clear();
        for (AirbnbListing aListing : listings)
        {
            if (aListing.getPrice()>=from && aListing.getPrice()<=to){
                currentListings.add(aListing);
            }
        }
        currentListings.sort(Comparator.comparing(AirbnbListing::getNeighbourhood));
    }    
        
    /**
     * @return A list of prices incrementing by 200 up to a limit
     */
    public ArrayList<Integer> getMenuOptions() 
    {
        ArrayList<Integer> prices = getAllPrices();
        
        Integer maxPrice = Collections.max(prices);
        prices = new ArrayList();
        int counter = 0;
        while (counter <= maxPrice) {
            prices.add(counter);
            counter += 200;
        }
        
        if (counter > maxPrice) {
            prices.add(counter);
        }
        return prices;
    }    
    
    /**
     * @return A list containing all of the boroughs based on the data in current listings
     */
    public ObservableList<String> getBoroughOptions() 
    {
        if (currentListings.isEmpty()) {
            return null;
        }
        ObservableList<String> allBoroughs = FXCollections.observableArrayList();
        if (currentListings.size() == 1) {
            allBoroughs.add(currentListings.get(0).getNeighbourhood());
            return allBoroughs;
        }
        String borough = currentListings.get(0).getNeighbourhood();
        currentListings.sort(Comparator.comparing(AirbnbListing::getNeighbourhood));       
        
        for (AirbnbListing aListing : currentListings)
        {
            if (!aListing.getNeighbourhood().equalsIgnoreCase(borough)) {
                allBoroughs.add(borough);
                borough = aListing.getNeighbourhood();
            }         
        }
        return allBoroughs;
    }

    /**
     * @return A list which contains all of the prices in the listings with no
     */
    public ArrayList<Integer> getAllPrices()
    {
        ArrayList<Integer> prices = new ArrayList();
        for (AirbnbListing aListing : listings)
        {
            Integer cPrice = aListing.getPrice();
            if (!prices.contains(cPrice))
            {
                prices.add(cPrice);
            }
        }
        return prices;
    }

    /**
     * Gets borough listings as an ObservableList for view on the map panel
     * @param borough The name of the borough whose button was clicked on
     * @param lowerBound The lower bound of the selected price range
     * @param upperBound The upper bound of the selected price range
     */
    public ObservableList<AirbnbListing> getBoroughListings(String borough, int lowerBound, int upperBound)
    {
        ObservableList<AirbnbListing> observableListings = FXCollections.observableArrayList();
        for (AirbnbListing listing : listings)
        {
            if (listing.getNeighbourhood().equals(borough)) {
                if (listing.getPrice() >= lowerBound && listing.getPrice() <= upperBound) {
                    observableListings.add(listing);
                }
            }
        }

        return observableListings;
    }

    /**
     * Returns a list of all prices in a borough which is passed in as a parameter
     */
    public ArrayList<Integer> getBoroughPrices(String borough)
    {
      ArrayList<Integer> listOfPricesInBorough = new ArrayList<>();
      for (AirbnbListing aListing : listings)
      {
          if (aListing.getNeighbourhood().equals(borough))
          {
              listOfPricesInBorough.add(aListing.getPrice());
          }
      }
      return listOfPricesInBorough;
    }
}
