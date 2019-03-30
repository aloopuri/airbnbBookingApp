import java.io.*;
import com.opencsv.CSVReader;
import javafx.collections.*;
import java.net.*;
import java.util.*;

/**
 * Controls the creating of users and the process of logging in
 *
 *@Zain Raja
 */
public class LoginSystem
{
    private User currentUser;
    private ListingManager listingManager;
    /**
     * Constructor for objects of class LoginSystem
     */
    public LoginSystem(ListingManager listingMan)
    {
        this.listingManager = listingMan;
    }
    
    /**
     * Adds a user to the system
     * @param name The Username
     * @param pass The password
     * 
     * @returns If the adding was successful
     */
    public String addUser(String name, String pass)
    {
        String returnString = "Adding";
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            boolean taken = false;
            //Checks each line to see if the name is taken
            while ((line = reader.readNext()) != null)
            {
                if (line[0].equals(name))
                {
                    taken = true;
                }
            }
            //If the name is available addes the username and 
            //password to the file
            if (!taken)
            {
                try (FileWriter writer = new FileWriter("users.csv",true))
                {
                    String newPass = cipherText(pass,name,false);
                    if 
                    (!(newPass.contains("\"")
                    ||newPass.contains(",")
                    ||newPass.contains(";")))
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append(name);
                        sb.append(',');
                        //Encrypts the password before storing
                        sb.append(cipherText(pass,name,false));
                        sb.append(",1,2,3,4,5,6,7,8,9,0");
                        sb.append('\n');
                        System.out.println(name + " " + cipherText(pass,name,false));
                        writer.write(sb.toString());
                        returnString = "Account Created";
                        writer.close();
                    }
                    else
                    {
                        returnString = "Please choose another username or password";
                    }
                }
                catch (FileNotFoundException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                returnString = "This username is taken";
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return returnString;
    }
    
    /**
     * Allows users to log in to the system
     * 
     * @param name The Username
     * @param pass The password
     * 
     * @returns If the user is found
     */
    public boolean login(String name, String pass)
    {
        boolean found = false;
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            reader.readNext();
            String deCipheredPass;
            //Checks each line against the file to check if
            //the username and password match
            while ((line = reader.readNext()) != null)
            {
                deCipheredPass = cipherText(line[1],line[0],true);
                if (line[0].equals(name) && deCipheredPass.equals(pass))
                {
                    found = true;
                    //Creates a user based on the information in the file
                    currentUser = new User(line[0],deCipheredPass);
                    ArrayList<String> faveListingS = new ArrayList();
                    //Adds the favourite listing Strings to an arraylist
                    for (int ind = 2;ind<line.length;ind++)
                    {
                        if (line[ind] != null)
                        {
                            if (line[ind].length() > 2)
                            {
                                faveListingS.add(line[ind]);
                            }
                        }
                    }
                    ArrayList<String> faveListings = new ArrayList();
                    //Checks the listings to match the strings 
                    //against real listings
                    for (AirbnbListing listing : listingManager.getListings())
                    {
                        System.out.println(listing);
                        if (listing != null)
                        {
                            for (String lName : faveListingS)
                            {
                                if (lName.equals(listing.getId()))
                                {
                                    currentUser.addFavourite(listing);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(IOException | URISyntaxException e)
        {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
        return found;
    }
    
    /**
     * A simple cipher to encrypt data before storing it
     * 
     * @param plainText the phrase to cipher
     * @param The key used to cipher
     * @param deCipher whether to cipher or decipher the text
     */
    public String cipherText(String plainText,String key,boolean deCipher)
    {
        StringBuilder finalString = new StringBuilder();
        char finalArray[] = plainText.toCharArray();
        int size = finalArray.length; 
        for (int i=0;i<size;i++)
        {
            //The value to add is the character in the key which 
            //corresponds to the position in the text
            int add = (int) key.charAt(i % key.length());
            int newLet;
            //If it is deciphering, subtract the key and check for negatives
            //If it is ciphering, add the key and check for the max value
            if (deCipher)
            {
                newLet = (int) finalArray[i] - add;
                while (newLet < 0)
                {
                    newLet += 126;
                }
            }
            else
            {
                newLet = ((int) finalArray[i] + add) % 126;
            }
            finalString.append((char) newLet);
        }
        return finalString.toString();
    }
    
    /**
     * Test to see if the encrption and decryption work correctly
     */
    public void test(String u,String p)
    {
        String n = cipherText(p,u,false);
        System.out.println("1:" + n);
        System.out.println("2:" + cipherText(n,u,true));
        System.out.println("3:" + p);
    }
    
    /**
     * Sets the current user to null
     */
    public void removeCurrentUser()
    {
        currentUser = null;
    }
    
    /**
     * Returns the current user
     * 
     * @currentUser The user that is logged in
     */
    public User getCurrentUser()
    {
        return currentUser;
    }
}
