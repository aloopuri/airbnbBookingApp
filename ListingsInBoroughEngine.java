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

}
