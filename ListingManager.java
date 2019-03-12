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

    public AirbnbListing getCurrentListing()
    {
        return listings.get(currentListing);
    }

    public int numberOfListings()
    {
        return listings.size();
    }

    public int listingsInBorough(String borough)
    {
        int n = 0;
        for (AirbnbListing aListing : listings)
        {
            if (aListing.getNeighbourhood().equals(borough))
            {
                n++;
            }
        }
        return n;
    }

    public String getHostName(int index)
    {
        return listings.get(index).getHost_name();
    }

    public String getCurrentHostName()
    {
        return getHostName(currentListing);
    }
}
