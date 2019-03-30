import static org.junit.Assert.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import javafx.application.*;
import javafx.scene.control.Button;
import javafx.embed.swing.JFXPanel;
/**
 * This is the Map Panel's unit testing class.
 * It tests all of the public methods from the
 * map panel class for null values, array sizes,
 * and proper initialization
 */
public class MapPanelTest
{
    private AirbnbDataLoader airbnbDa1;
    private ListingManager listingM1;
    private MapPanel mapPanel1;
    /**
     * Default constructor for test class MapPanelTest
     */
    public MapPanelTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        JFXPanel fxPanel = new JFXPanel();
        Platform.runLater(() -> {
            airbnbDa1 = new AirbnbDataLoader();
            listingM1 = new ListingManager(airbnbDa1.load());
            mapPanel1 = new MapPanel(listingM1);
        });
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    /**
     * Tests to ensure that there are no null values being
     * returned.
     */
    @Test
    public void nullTests()
    {
        Platform.runLater(() -> {
            assertNotNull(mapPanel1.getButtons());
            
            try {
                assertNotNull(mapPanel1.createMap());
            }
            catch (Exception e) {
                fail("Exception should not have been thrown.");
            }
            
            // Check method return value for all possible listing parameters
            for (AirbnbListing listing : listingM1.getListings()) {
                assertNotNull(mapPanel1.getListingView(listing));
            }
            
            // Check all buttons have been correctly linked to FXML
            for (Button button : mapPanel1.getButtons()) {
                assertNotNull(button);
            }
        });
    }
    
    /**
     * Test to ensure the map buttons have been correctly
     * initialized.
     */
    @Test
    public void sizeTest() 
    {
        Platform.runLater(() -> {
            assertEquals(33, mapPanel1.getButtons().size());
        });
    }
    
    /**
     * Test for any thrown exceptions from the
     * map panel's showViewInRange method
     */
    @Test 
    public void exceptionTest() 
    {
        Platform.runLater(() -> {
            try {
                mapPanel1.createMap();
                mapPanel1.showViewInRange(100, 200);
                mapPanel1.showViewInRange(200, 100);
                mapPanel1.showViewInRange(100, 100);
                mapPanel1.showViewInRange(-10, 0);
            }
            catch (Exception e) {
                fail("Exception should not have been thrown.");
            }
        });
    }
}
