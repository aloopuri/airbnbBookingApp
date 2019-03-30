import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList; 
import java.text.DecimalFormat;
import javafx.scene.chart.*;
import java.util.Comparator;


/**
 * This class contains all the statistics used in the statistics panel
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Statistics
{
    private List<AirbnbListing> listings = new ArrayList<>();
    
    private double avgNumOfReviews;
    private int totalAvailProperties;
    private int numOfHomesAndApts;
    private String mostExpBorough;
    private ObservableList<PieChart.Data> prprtiesInEachBrgh;

    /**
     * Constructor for objects of class Statistics
     */
    public Statistics(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
        prprtiesInEachBrgh = FXCollections.observableArrayList();
    }
    
    /**
     * Updates the statistics 
     */
    public void updateStatistics(ArrayList<AirbnbListing> listings)
    {
        this.listings = listings;
        resetStatistics();
        AvgNumOfReviews();
        totalAvailProperties();
        numOfHomesAndApts();
        mostExpensiveBorough();
        propertiesInEachBorough();
    }
    
    /**
     * Resets all the statistics
     */
    private void resetStatistics()
    {
        avgNumOfReviews = 0;
        totalAvailProperties = 0;
        numOfHomesAndApts = 0;
        mostExpBorough = "";
        prprtiesInEachBrgh.clear();
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
     * Calculates the total number of available properties where the availability is
     * greater than zero
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
        if (listings.isEmpty()) {
            mostExpBorough = "No properties in this price range";
            return;
        }
        
        listings = sortByBoroughAndRoom(listings);        
        int propertiesInBorough = 1; 
        int avgCost = 0;
        int avgCostMostExpBorough = 0;
        int totalMinPropertyPrice = 0;
        String borough= listings.get(0).getNeighbourhood();
        if(listings.size() == 1) {
            mostExpBorough = borough;
        }
        else{
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
        
    }
    
    /**
     * Calculates the number of properties in borough
     */
    private void propertiesInEachBorough()
    {
        if (listings.isEmpty()) {
            prprtiesInEachBrgh = null;
            return;
        }
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(); 
        String borough= listings.get(0).getNeighbourhood();        
        int propertiesInBorough = 1;
        if (listings.size() == 1) {
            data.add(new PieChart.Data(borough, propertiesInBorough));
        }
        listings = sortByBoroughAndRoom(listings);
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
        prprtiesInEachBrgh = data;
    }
    
    /**
     * Returns a list of data containing the number of properties in a borough
     */
    public ObservableList<PieChart.Data> getpropertiesInEachBorough() 
    {
        return prprtiesInEachBrgh;
    }
    
    /**
     * Calculates the number of different types of rooms and creates them as a bar to be
     * part of a bar chart
     */
    public ObservableList<XYChart.Series> getRoomTypeDistribution(String boroughName)
    {
        ArrayList<AirbnbListing> sortedList = getBoroughListings(boroughName); 
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        
        String roomType = sortedList.get(0).getRoom_type();
        int roomTypeCount = 1;
        for (AirbnbListing aListing : sortedList){            
            if (!aListing.getRoom_type().equals(roomType)) {               
                data.add(addSeries(roomType, roomTypeCount));
                roomType = aListing.getRoom_type();
                roomTypeCount = 0;
             }            
            else {
                roomTypeCount ++;
            }             
        }
        data.add(addSeries(roomType, roomTypeCount));
        return data;
    }
    
    /**
     * Calculates the availability of properties in a borough and categorises them
     * into 4 bars, 0-99, 100-99, 200-299, and 300+
     * It then turns them into bars to be used to form a bar chart
     */
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
        }
        
        ArrayList<Integer> rangeCount = new ArrayList();
        rangeCount.add(first100);
        rangeCount.add(scnd100);
        rangeCount.add(third100);
        rangeCount.add(three100plus);
        int num = 0;
        for (String range : ranges) {
            data.add(addSeries(range, rangeCount.get(num)));
            num++;
        }
        return data;
    }
    
    /**
     * Creates a series for a barchart and sets the name of the series as type
     */
    private XYChart.Series addSeries(String type, int typeCount)
    {
        XYChart.Series series = new XYChart.Series();
        series.setName(type);
        series.getData().add(new XYChart.Data<String, Number>(type, typeCount));
        return series;
    }
    
    /**
     * Filters a list by a borough passed in and then sorts its by room type 
     * in alphabetical order
     */
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
    
    /**
     * This creates an and returns an arraylist containing the common features
     * between the top 5 properties in a borough
     */
    public ArrayList<String> mostPopListType(String boroughName)
    {
        ArrayList<AirbnbListing> sortedList = getBoroughListings(boroughName); 
        sortedList = getTopListings(sortedList);
        if (sortedList.isEmpty()) {
            ArrayList<String> empty = new ArrayList();
            return empty;
        }
        
        double avgPrice = 0.0;
        double avgMinNights = 0.0;
        int privateRm = 0;
        int entireHme = 0;
        int sharedRm = 0;
        for (AirbnbListing aListing : sortedList){ 
            switch (aListing.getRoom_type()) {
                case "Private room":
                    privateRm ++;   
                    break;
                case"Entire home/apt":
                    entireHme ++;
                    break;
                case "Shared room":
                    sharedRm ++;
                    break;
            }
            avgPrice += aListing.getPrice();
            avgMinNights += aListing.getMinimumNights();
        }
       
        ArrayList<String> common = new ArrayList();        
        if (avgPrice>0.0) {
            avgPrice = avgPrice/sortedList.size();
            common.add("Average Price: " + avgPrice);
        }
        else {
            common.add("Average Price: No Properties" );
        }
        
        if (avgPrice>0.0) {
            avgMinNights = avgMinNights/sortedList.size();
            common.add("Average Minimum Nights: " + avgMinNights);
        }
        else {
            common.add("Average Minimum Nights: No Properties" );
        }
        
        if (privateRm > entireHme && privateRm > sharedRm) {
            common.add("Most Popular Room Type: Private room");
        }
        else if (entireHme > privateRm && entireHme > sharedRm) {
            common.add("Most Popular Room Type: Entire home/apt");
        }
        else if (sharedRm > privateRm && sharedRm > entireHme) {
            common.add("Most Popular Room Type: Shared room");
        }
        return common;
    }
    
    /**
     * Returns an arraylist of the top 5 listings 
     * If the list passed in has less than 5 listings, it returns the list
     */
    private ArrayList<AirbnbListing> getTopListings(ArrayList<AirbnbListing> listings)
    {
        listings.sort(Comparator.comparing(AirbnbListing::getReviewsPerMonth));
        ArrayList<AirbnbListing> topListings = new ArrayList();
        if (listings.size() >=5) {
            int count = 5;
            while (count > 0) {
                topListings.add(listings.get((listings.size()-1) - count));
                count --;
            }
            return topListings;
        }
        else {
            return listings;
        }
    }
    
    /**
     * Sorts a list by Neighbourhood first and then room type
     */
    private List<AirbnbListing> sortByBoroughAndRoom(List<AirbnbListing> listing)
    {
        listing.sort(Comparator.comparing(AirbnbListing::getNeighbourhood).
                thenComparing(AirbnbListing::getRoom_type));        
        return listing;
    }
        
    /**
     * Returns the average number of reviews
     */
    public double getAvgNumOfReviews()
    {
        return avgNumOfReviews;
    }
    
    /**
     * Returns the average number of reviews as a string to 2 decimal places
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
