import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import java.text.DecimalFormat;
import javafx.scene.chart.PieChart; 

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
    }
    
    public void updateStatistics(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
        resetStatistics();
        AvgNumOfReviews();
        totalAvailProperties();
        numOfHomesAndApts();
        mostExpensiveBorough();
    }
    
    /**
     * Resets all the statistics to zero/empty strings
     */
    private void resetStatistics()
    {
        avgNumOfReviews = 0;
        totalAvailProperties = 0;
        numOfHomesAndApts = 0;
        mostExpBorough = "";
    }
    
    /**
     * Calculates the average number of reviews
     */
    private void AvgNumOfReviews()
    {
        int count = 0;
        double reviewTotal = 0;
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
    
    public ObservableList<PieChart.Data> propertiesInBorough()
    {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String borough= listings.get(0).getNeighbourhood();
        int propertiesInBorough = 0;
        for (AirbnbListing aListing : listings){
            if (!aListing.getNeighbourhood().equals(borough)) {
                data.add(new PieChart.Data(borough, propertiesInBorough));
                borough = aListing.getNeighbourhood();  
                propertiesInBorough = 0;
            }
            propertiesInBorough ++;            
        }
        return data;
    }
    
    public void getAvailailityDistribution()
    {
        
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
        DecimalFormat number = new DecimalFormat("#.00");
        return "" + number.format(avgNumOfReviews);
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
