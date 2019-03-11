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
    
}
