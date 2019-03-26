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

    /**
     * Constructor for objects of class listingManager
     */
    public ListingManager(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
    }

    public ArrayList<AirbnbListing> getListings()
    {
        return listings;
    }
    
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
