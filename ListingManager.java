import java.util.*;
import javafx.collections.*;
import java.lang.*;
/**
 * Write a description of class listingManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ListingManager
{
    private ArrayList<AirbnbListing> listings;
    private int currentListing;
    private AirbnbDataLoader dataLoader;
    /**
     * Constructor for objects of class listingManager
     */
    public ListingManager(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
        currentListing = 0;
    }

    public ArrayList<AirbnbListing> getListings()
    {
        return listings;
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
    public ObservableList<AirbnbListing> getBoroughListings(String borough) 
    {
        ObservableList<AirbnbListing> observableListings = FXCollections.observableArrayList();
        
        for (AirbnbListing listing : listings) 
        {
            if (listing.getNeighbourhood().equals(borough)) {
                observableListings.add(listing);
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
