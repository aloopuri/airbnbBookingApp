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

    /**
     * Constructor for objects of class User
     */
    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
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
                    updateListings(newID);
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
                if (line[2].equals(id))
                {
                    numID ++;
                }
            }
            for (String[] currLine : lines)
            {
                if (currLine[2].equals(id))
                {
                    currLine[13] = numID+"";
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
    
    public String getName()
    {
        return username;
    }
}
