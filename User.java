import java.io.*;
import com.opencsv.CSVReader;
import javafx.collections.*;
import java.net.*;
import java.util.*;
import java.util.Random;
/**
 * Represents a logged in user in the system
 * Has favourite properties which can be
 * added/deleted/updated in the file
 * Can add properties to the file
 */
public class User
{
    // instance variables - replace the example below with your own
    private String username, password;
    private ArrayList<AirbnbListing> favourites; 
    private int maxSize;
    /**
     * Constructor for objects of class User
     * @param username The name of the user
     * @param password The password of the user
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        favourites = new ArrayList();
        maxSize = 10;
    }
    
    /**
     * Adds a listing to the list of favourites
     * 
     * @param listing The listing to add
     */
    public void addFavourite(AirbnbListing listing)
    {
        if (!(favourites.contains(listing))) //f it isnt already in
        {
            if (favourites.size() <= maxSize)
            {
                favourites.add(listing);
                System.out.println(favourites.size());
            }
        }
    }
    
    /**
     * Remove a listing from the favourites
     * 
     * @param THe listing to remove
     */
    public void removeFavourite(String listingName)
    {
        AirbnbListing remove = null; //The listing to remove
        //Checks the favourite for the listing
        for (AirbnbListing listing:favourites)
        {
            if (listing.getName().equals(listingName))
            {
                remove = listing;
            }
        }
        //If the listing is found, it is removed
        if (remove != null)
        {
            favourites.remove(remove);
        }
    }
    
    /**
     * Saves the favourites ot the user file
     */
    public void saveFavourites()
    {
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            ArrayList<String[]> lines = new ArrayList(); 
            int numID = 0;
            //Copies all the users into an Array List
            while ((line = reader.readNext()) != null) {
                if (line != null)
                {
                    lines.add(line);
                }
            }
            //Writes all the users back into the file
            try (FileWriter writer = new FileWriter("users.csv",false))
            {
                for (String[] currLine : lines)
                {
                   StringBuilder sb = new StringBuilder();
                   if (currLine != null)
                   {
                       //If the current line is the users entry
                       //Update the file with the listings
                       //Otherwise copy the original line from file
                       if (!currLine[0].equals(username))
                       {
                           for (String elem: currLine)
                           {
                               sb.append(elem + ',');
                           }
                       }
                       else
                       {
                           sb.append(currLine[0] + ',' + currLine[1] + ',');
                           for (AirbnbListing list : favourites)
                           {
                               sb.append(list.getId() + ',');
                           }
                       }
                       sb.append('\n');
                       writer.write(sb.toString()); 
                   }
                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }
    
    /**
     * Adds a listing the user would like to add, to the file
     * The parameters are the fields stored about the listing
     * @return String shows if the function was successful or not
     */
    public String addListing(
    String propName, String name, String neighbourhood,String latitude,
    String longitude,String type,String price,String minNights,
    String availability)
    {
        String returnString = "";
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            ArrayList<String> listingIDs = new ArrayList<String>();
            //Adds all the IDs to a list
            while ((line = reader.readNext()) != null) {
                listingIDs.add(line[0]);
            }
            Random rand = new Random();
            Boolean taken = true;
            String newID = "";
            //Generates a random ID, checking if it is already taken
            while (taken)
            {
                newID = "u" + (100+rand.nextInt(9999999));
                taken = false;
                for (String entry: listingIDs)
                {
                    if (newID == entry)
                    {
                        taken = true;
                    }
                }
            }
            //Writes all the information to the file.
            try (FileWriter writer = new FileWriter("airbnb-london.csv",true))
            {
                StringBuilder sb = new StringBuilder();
                sb.append(newID + ',');
                sb.append(propName + ',');
                sb.append(username + ',');
                sb.append(name + ',');
                sb.append(neighbourhood + ',');
                sb.append(latitude + ',');
                sb.append(longitude + ',');
                sb.append(type + ',');
                sb.append(price + ',');
                sb.append(minNights + ',');
                sb.append("" + ',' +"" + ',' +"" + ',' +"" + ',');
                sb.append(availability + ',');
                sb.append('\n');
                writer.write(sb.toString());
                updateListings(username);
                returnString = "Listing added : ";
                //Updates the return text
                if (propName.length() > 12)
                {
                    returnString += propName.substring(0,12) + "...";
                }
                else
                {
                    returnString += propName;
                }
            }
            catch (FileNotFoundException e)
            {
                System.out.println(e.getMessage());
            }
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return returnString;
    }
    
    /**
     * Goes through the listings and updates the field which stores
     * how many listings each user has added
     * 
     * @param id The id/username of the user
     */
    private void updateListings(String id)
    {
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            ArrayList<String[]> lines = new ArrayList(); 
            int numID = 0;
            //Checks all the lines in the files and every time
            //a Listing created by the user occurs, increase
            //a counter. Copy the lines into an arraylist
            while ((line = reader.readNext()) != null) {
                lines.add(line);
                if (line[2].equals(id))
                {
                    numID ++;
                }
            }
            //for each listing, change the field where the id matches
            for (String[] currLine : lines)
            {
                if (currLine[2].equals(id))
                {
                    currLine[13] = numID+"";
                }
            }
            //Write the array list back into the file
            try (FileWriter writer = new FileWriter("airbnb-london.csv",false))
            {
                StringBuilder sb;
                for (String[] currLine : lines)
                {
                    sb = new StringBuilder();
                    for (int i=0;i<currLine.length;i++)
                    {
                        //Gets rid of any forbidden characters
                        currLine[i] = currLine[i].replaceAll(","," ");
                        currLine[i] = currLine[i].replaceAll(";"," ");
                        currLine[i] = currLine[i].replaceAll("\""," ");
                        sb.append(currLine[i] + ',');
                    }
                    sb.append('\n');
                    writer.write(sb.toString());
                }
            }
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }
    
    /**
     * Accessor method which returns the array list of favourites
     * @return favourites An ArrayList of favourites
     */
    public ArrayList<AirbnbListing> getFavourites()
    {
        return favourites;
    }
    
    /**
     * Accessor which returns the user's name
     * @return username The name of the user
     */
    public String getName()
    {
        return username;
    }
}
