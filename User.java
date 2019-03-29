import java.io.*;
import com.opencsv.CSVReader;
import javafx.collections.*;
import java.net.*;
import java.util.*;
import java.util.Random;
/**
 * Write a description of class User here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class User
{
    // instance variables - replace the example below with your own
    private String username, password;
    private ArrayList<AirbnbListing> favourites; 
    private int maxSize;
    /**
     * Constructor for objects of class User
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
        favourites = new ArrayList();
        maxSize = 10;
    }
    
    public void addFavourite(AirbnbListing listing)
    {
        if (!(favourites.contains(listing)))
        {
            System.out.println(favourites.size()+"  "+maxSize);
            if (favourites.size() <= maxSize)
            {
                favourites.add(listing);
                System.out.println(favourites.size());
            }
        }
    }
    
    public void removeFavourite(String listingName)
    {
        System.out.println(listingName);
        AirbnbListing remove = null;
        for (AirbnbListing listing:favourites)
        {
            if (listing.getName().equals(listingName))
            {
                remove = listing;
            }
        }
        if (remove != null)
        {
            favourites.remove(remove);
        }
    }
    
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
            while ((line = reader.readNext()) != null) {
                listingIDs.add(line[0]);
                System.out.println(line);
            }
            Random rand = new Random();
            Boolean taken = true;
            String newID = "";
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
            System.out.println("Read" + newID);
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
    
    private void updateListings(String id)
    {
        try{
            URL url = getClass().getResource("airbnb-london.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            ArrayList<String[]> lines = new ArrayList(); 
            int numID = 0;
            while ((line = reader.readNext()) != null) {
                lines.add(line);
                System.out.println(line[2] + " " + id);
                    if (line[2].equals(id))
                    {
                        numID ++;
                        System.out.println(line[1]);
                    }
            }
            System.out.println(numID);
            for (String[] currLine : lines)
            {
                if (currLine[2].equals(id))
                {
                    currLine[13] = numID+"";
                    System.out.println(currLine[1]);
                }
            }
            try (FileWriter writer = new FileWriter("airbnb-london.csv",false))
            {
                StringBuilder sb;
                for (String[] currLine : lines)
                {
                    sb = new StringBuilder();
                    for (int i=0;i<currLine.length;i++)
                    {
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
    public ArrayList<AirbnbListing> getFavourites()
    {
        return favourites;
    }
    public String getName()
    {
        return username;
    }
}
