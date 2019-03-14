import java.util.List;
import java.util.ArrayList;
/**
 * This holds the statistics
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Statistics
{
    // instance variables - replace the example below with your own
    private List<AirbnbListing> listings = new ArrayList<>();
    
    private double avgNumOfReviews;
    private int totalAvailProperties;
    private int numOfHomesAndApts;
    private String mostExpBorough;

    /**
     * Constructor for objects of class Statistics
     */
    public Statistics(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
        AvgNumOfReviews();
        totalAvailProperties();
        numOfHomesAndApts();
        mostExpensiveBorough();
    }
    
    /**
     * Calculates the average number of reviews
     */
    private void AvgNumOfReviews()
    {
        int count = 0;
        int reviewTotal = 0;
        for (AirbnbListing aListing : listings){
            count++;
            reviewTotal += aListing.getNumberOfReviews();
        }   
        avgNumOfReviews = reviewTotal/count;
    }
    
    /**
     * Calculates the total number of available properties
     */
    private void totalAvailProperties()
    {
        for (AirbnbListing aListing : listings){
            if (aListing.getAvailability365() > 0){
                totalAvailProperties++;
            }
        }  
    }
    
    /**
     * Calculates the number of Homes and Apartments
     */
    private void numOfHomesAndApts()
    {
        for (AirbnbListing aListing : listings){
            if (aListing.getRoom_type().equalsIgnoreCase("Entire home/apt")){
                numOfHomesAndApts++;
            }  
        }
    }
    
    /**
     * Finds the most expensive borough
     */
    private void mostExpensiveBorough()
    {
        String borough= "";
        int propertiesInBorough = 1; // this is so it doesn't crash when it runs initially
        int avgCost = 0;
        int avgCostMostExpBorough = 0;
        int totalMinPropertyPrice = 0;
        
        for (AirbnbListing aListing : listings){
            if (!aListing.getNeighbourhood().equals(borough)){
                avgCost = totalMinPropertyPrice/propertiesInBorough;
                if(avgCost > avgCostMostExpBorough){
                    mostExpBorough = borough;
                    avgCostMostExpBorough = avgCost;
                    avgCost = 0;                    
                }
                borough = aListing.getNeighbourhood();
                propertiesInBorough = 0;
            }
            totalMinPropertyPrice += aListing.getPrice() * aListing.getMinimumNights();
            propertiesInBorough ++;
        } 
    }
        
    /**
     * Returns the average number of reviews
     */
    public double getAvgNumOfReviews()
    {
        return avgNumOfReviews;
    }
    
    /**
     * Returns the average number of reviews as a string
     */
    public String getAvgNumOfReviewsString()
    {
        return "" + avgNumOfReviews;
    }
    
    /**
     * Returns the total number of available properties
     */
    public int getTotalAvailProperties()
    {
        return totalAvailProperties;
    }
    
    /**
     * Returns the total number of available properties as a string
     */
    public String getTotalAvailPropertiesString()
    {
        return "" + totalAvailProperties;
    }
    
    /**
     * Returns the number of homes and apartments
     */
    public int getNumOfHomesAndApts()
    {
        return numOfHomesAndApts;
    }
    
    /**
     * Returns the number of homes and apartments as a string
     */
    public String getNumOfHomesAndAptsString()
    {
        return "" + numOfHomesAndApts;
    }
    
    /**
     * Returns the most expensive borough
     */
    public String getMostExpBorough()
    {
        return mostExpBorough;
    }
}
