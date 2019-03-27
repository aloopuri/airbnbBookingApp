import java.io.*;
import com.opencsv.CSVReader;
import javafx.collections.*;
import java.net.*;
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

    private String addListing(
    String propName, String name, String neighbourhood)
    {
        String returnString = "";
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
        }
        catch(IOException | URISyntaxException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return returnString;
    }

    public String getName()
    {
        return username;
    }
}
