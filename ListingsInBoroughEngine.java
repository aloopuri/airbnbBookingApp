import javafx.scene.text.Text;
import java.util.ArrayList;

public class ListingsInBoroughEngine
{
  private Text text;
  private AirbnbDataLoader dataLoader;
  private ListingManager listingManager;
  private ArrayList<AirbnbListing> airbnbListing;

  public ListingsInBoroughEngine()
  {
    dataLoader = new AirbnbDataLoader();
    listingManager = new ListingManager(dataLoader.load());
    airbnbListing = listingManager.listingsEachBorough("Enfield");
  }
  
  public void printHostInfo()
  {
    for (AirbnbListing listing: airbnbListing){
      System.out.println("Host Name: " + listing.getHost_name());
      System.out.println("Price: " + listing.getPrice());
      System.out.println("Number of Reviews: " + listing.getNumberOfReviews());
      System.out.println("Minimum Number of Nights You can Stay: " + listing.getMinimumNights());
      System.out.println("");
    }
  }
}
