import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import java.text.DecimalFormat;
import javafx.scene.chart.*;
import java.util.Comparator;


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
        sortByBoroughAndRoom();
        int j = 0;
        String borough= listings.get(0).getNeighbourhood();
        int propertiesInBorough = 1; 
        int avgCost = 0;
        int avgCostMostExpBorough = 0;
        int totalMinPropertyPrice = 0;
        
        for (AirbnbListing aListing : listings){
            if (!aListing.getNeighbourhood().equalsIgnoreCase(borough)){
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
        sortByBoroughAndRoom();
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        String borough= listings.get(0).getNeighbourhood();
        int propertiesInBorough = 1;
        for (AirbnbListing aListing : listings){
            if (!aListing.getNeighbourhood().equalsIgnoreCase(borough)) {                
                if (propertiesInBorough >0){
                    data.add(new PieChart.Data(borough, propertiesInBorough));
                }
                borough = aListing.getNeighbourhood();  
                propertiesInBorough = 0;
            }
            else {
                propertiesInBorough ++; 
            }                       
        }
        return data;
    }
    
    public ObservableList<XYChart.Series> getRoomTypeDistribution(String boroughName)
    {
        ArrayList<AirbnbListing> sortedList = getBoroughListings(boroughName); 
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        
        String roomType = sortedList.get(0).getRoom_type();
        int roomTypeCount = 1;
        for (AirbnbListing aListing : sortedList){            
            if (!aListing.getRoom_type().equals(roomType)) {
                XYChart.Series series = new XYChart.Series();
                series.setName(roomType);
                series.getData().add(new XYChart.Data<String, Number>(roomType, roomTypeCount));
                data.add(series);
                roomType = aListing.getRoom_type();
                roomTypeCount = 0;
             }            
            else {
                roomTypeCount ++;
            }             
        }
        XYChart.Series series = new XYChart.Series();
        series.setName(roomType);
        series.getData().add(new XYChart.Data<String, Number>(roomType, roomTypeCount));
        data.add(series);
        return data;
    }
    
    public ObservableList<XYChart.Series> getAvailDistribution(String boroughName)
    {
        ArrayList<AirbnbListing> sortedList = getBoroughListings(boroughName);
        sortedList.sort(Comparator.comparing(AirbnbListing::getAvailability365));        
        String zero_99 = "0-99";
        int first100 = 0;
        String hundred_199 = "100-199";
        int scnd100 = 0;
        String twohundred_299 = "200-299";
        int third100 = 0;
        String threehundred = "300+";
        int three100plus = 0;
        ArrayList<String> ranges = new ArrayList();
        ranges.add(zero_99);
        ranges.add(hundred_199);
        ranges.add(twohundred_299);
        ranges.add(threehundred);
        
        
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        for (AirbnbListing aListing : sortedList){ 
            
            if (aListing.getAvailability365() <= 99){ 
                first100 ++;                         
            }
            else if (aListing.getAvailability365() >= 100 && aListing.getAvailability365() <= 199){ 
                scnd100 ++;                         
            }
            else if (aListing.getAvailability365() >= 200 && aListing.getAvailability365() <= 299){ 
                third100 ++;                         
            }
            else if (aListing.getAvailability365() >= 300){ 
                three100plus ++;                         
            } 
            else {
                System.out.println(aListing.getAvailability365());
            }
        }
        
        ArrayList<Integer> rangeCount = new ArrayList();
        rangeCount.add(first100);
        rangeCount.add(scnd100);
        rangeCount.add(third100);
        rangeCount.add(three100plus);
        int num = 0;
        for (String range : ranges) {
            XYChart.Series series = new XYChart.Series();
            series.setName(range);
            series.getData().add(new XYChart.Data<String, Number>(range, rangeCount.get(num)));
            data.add(series);
            num++;
        }
        return data;
    }
    
    private ArrayList<AirbnbListing> getBoroughListings(String boroughName) 
    {
        ArrayList<AirbnbListing> tempList = new ArrayList();
        for (AirbnbListing aListing : listings){ 
            if (aListing.getNeighbourhood().equalsIgnoreCase(boroughName)){
                tempList.add(aListing);
            }
        }
        tempList.sort(Comparator.comparing(AirbnbListing::getRoom_type));
        return tempList;
    }
    
    private void sortByBoroughAndRoom()
    {
        listings.sort(Comparator.comparing(AirbnbListing::getNeighbourhood).
                thenComparing(AirbnbListing::getRoom_type));
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
        DecimalFormat number = new DecimalFormat("#0.00");
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
