import java.io.*;
import com.opencsv.CSVReader;
import javafx.collections.*;
import java.net.*;

/**
 * Write a description of class LoginSystem here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LoginSystem
{
    private User currentUser;
    /**
     * Constructor for objects of class LoginSystem
     */
    public LoginSystem()
    {

    }

    public String addUser(String name, String pass)
    {
        String returnString = "";
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            boolean taken = false;
            while ((line = reader.readNext()) != null)
            {
                if (line[0].equals(name))
                {
                    taken = true;
                }
            }
            if (!taken)
            {
                try (FileWriter writer = new FileWriter("users.csv",true))
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append(name);
                    sb.append(',');
                    sb.append(cipherText(pass,name,false));
                    sb.append('\n');
                    writer.write(sb.toString());
                    returnString = "Account Created";
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
    
    public boolean login(String name, String pass)
    {
        boolean found = false;
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            String deCipheredPass;
            while ((line = reader.readNext()) != null)
            {
                deCipheredPass = cipherText(line[1],line[0],true);
                if (line[0].equals(name) && deCipheredPass.equals(pass))
                {
                    found = true;
                    currentUser = new User(line[0],deCipheredPass);
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
    
    public String cipherText(String plainText,String key,boolean deCipher)
    {
        StringBuilder finalString = new StringBuilder();
        char finalArray[] = plainText.toCharArray();
        int size = finalArray.length;
        for (int i=0;i<size;i++)
        {
            int add = (int) key.charAt(i % key.length());
            int newLet;
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
    
    public void test(String u,String p)
    {
        String n = cipherText(p,u,false);
        System.out.println("1:" + n);
        System.out.println("2:" + cipherText(n,u,true));
        System.out.println("3:" + p);
    }
    
    public void removeCurrentUser()
    {
        currentUser = null;
    }
    
    public User getCurrentUser()
    {
        return currentUser;
    }
}
