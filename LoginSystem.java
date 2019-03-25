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
    /**
     * Constructor for objects of class LoginSystem
     */
    public LoginSystem()
    {

    }

    public void addUser(String name, String pass)
    {
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            boolean taken = false;
            while ((line = reader.readNext()) != null)
            {
                System.out.println(line[0] +"  " + name);
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
                }
                catch (FileNotFoundException e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                System.out.println("This username is taken");
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }
    
    public void login(String name, String pass)
    {
        try{
            URL url = getClass().getResource("users.csv");
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            boolean found = false;
            String deCipheredPass;
            while ((line = reader.readNext()) != null)
            {
                deCipheredPass = cipherText(line[1],line[0],true);
                System.out.println(line[1]+">"+deCipheredPass);
                if (line[0].equals(name) && deCipheredPass.equals(pass))
                {
                    System.out.println("LOGGED IN");
                    found = true;
                }
            }
        }
        catch(IOException | URISyntaxException e)
        {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
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
}
