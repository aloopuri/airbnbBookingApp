import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.util.*;
import java.lang.*;
import javafx.collections.*;
import java.util.*;
/**
 * This is the GUI for the user panel
 * This allows the user to log in and sign up
 * They can add listings and also dave listings to their favourites
 *
 * @Zain Raja
 */
public class UserPanel
{
    private BorderPane root;
    private LoginSystem loginSystem; //System for logging in and out
    private TextField usernameInput, passwordInput;
    private Button loginButton, signupButton, addListingButton;
    private Label loginStatus,nameDisplay;
    private VBox loginBox, accountBox, midBox;
    private ListView favouritesDisplay;
    /**
     * Constructor for objects of class UserPanel
     */
    public UserPanel(LoginSystem loginSystem)
    {
        this.loginSystem = loginSystem;
        root = new BorderPane();
        //Creates a box for the login/sign up components
        loginBox = new VBox();

        usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        loginBox.getChildren().addAll(new Label("Username: "),usernameInput);

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        loginBox.getChildren().addAll(new Label("Password: "),passwordInput);

        loginStatus = new Label();
        loginBox.getChildren().add(loginStatus);
        //Adds a box for the buttons
        HBox horizontalBox = new HBox();

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        signupButton = new Button("Sign Up");
        signupButton.setOnAction(e -> signUp());

        horizontalBox.getChildren().addAll(loginButton,signupButton);
        loginBox.getChildren().add(horizontalBox);
        root.setTop(loginBox);

        //Creats a box for the central components
        midBox = new VBox();

        Button logoutButt = new Button("logout");
        logoutButt.setOnAction(e -> logout());

        midBox.getChildren().add(logoutButt);

        HBox mainBox = new HBox();

        //Adds a Vbox for the favourites
        VBox favouritesBox = new VBox();
        favouritesBox.getChildren().add(new Label("FAVOURITES"));
        
        favouritesDisplay = new ListView();
        Button removeFavourite = new Button("Delete");
        Button showFavourite = new Button("Show");
        Button saveFavourite = new Button("Save");
        favouritesBox.getChildren().addAll(favouritesDisplay,
        removeFavourite,showFavourite,saveFavourite);
        showFavourite.setOnAction(e -> showFavourites(favouritesDisplay));
        removeFavourite.setOnAction(e -> deleteFavourite(favouritesDisplay));
        saveFavourite.setOnAction(e -> saveFavourites());
        mainBox.getChildren().add(favouritesBox);

        //Adds a VBox for the new property adder
        VBox newPropBox = new VBox();

        TextField pNameField = new TextField();
        pNameField.setPromptText("Listing Description");

        TextField nameField = new TextField();
        nameField.setPromptText("Your name");

        ChoiceBox neighbourhoodField = new ChoiceBox(
            FXCollections.observableArrayList("Kingston upon Thames", "Croydon",
            "Bromley","Hounslow","Ealing","Havering","Hillingdon","Harrow",
            "Brent","Barnet","Enfield","Waltham Forest","Redbridge","Sutton",
            "Lambeth","Southwark","Lewisham","Greenwich","Bexley",
            "Richmond upon Thames","Merton","Wandsworth","Hammersmith and Fulham",
            "Kensington and Chelsea","City of London","Westminister","Camden",
            "Tower Hamlets","Islington","Hackney","Haringey","Newham",
            "Barking and Dagenham"));

        TextField latitudeField = new TextField();
        latitudeField.setPromptText("Latitude");

        TextField longitudeField = new TextField();
        longitudeField.setPromptText("Longitude");

        ChoiceBox typeField = new ChoiceBox(
                FXCollections.observableArrayList(
                    "Private room","Entire home/apt","Shared room"));

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField minNightsField = new TextField();
        minNightsField.setPromptText("Minimum # of Nights");

        TextField availabilityField = new TextField();
        availabilityField.setPromptText("Days available/year");

        Label addListingStatus = new Label();

        addListingButton = new Button("Create");
        addListingButton.setOnAction(e -> addListing(
                    pNameField,nameField,neighbourhoodField,latitudeField,longitudeField,
                    typeField,priceField,minNightsField,availabilityField,addListingStatus));
        addListingButton.setDisable(true);

        newPropBox.getChildren().addAll(
            pNameField,nameField,neighbourhoodField,latitudeField,longitudeField,
            typeField,priceField,minNightsField,availabilityField,addListingStatus,
            addListingButton);

        mainBox.getChildren().add(newPropBox);
        midBox.getChildren().add(mainBox);

        root.setCenter(midBox);

        accountBox = new VBox();

        accountBox.getChildren().add(new Label("Account"));
        nameDisplay = new Label("Not logged in");
        accountBox.getChildren().add(nameDisplay);
        root.setBottom(accountBox);
    }
    
    /**
     * Saves the favourites when the button is pressed
     */
    private void saveFavourites()
    {
        if (loginSystem.getCurrentUser() != null)
        {
            loginSystem.getCurrentUser().saveFavourites();
        }
    }
    
    /**
     * Show the current Favourites
     * 
     * @param display The view from which to obtain the values to show
     */
    private void showFavourites(ListView display)
    {
        //Gets an arraylist of names and adds it to the display
        ArrayList<String> favouriteNames = new ArrayList();
        display.getItems().clear();
        if (loginSystem.getCurrentUser() != null)
        {
            for (AirbnbListing list:loginSystem.getCurrentUser().getFavourites())
            {
                favouriteNames.add(list.getName());
            }
            display.getItems().addAll(favouriteNames);
        }
    }
    
    /** 
     * Deleting a listing
     * 
     * @param display The view from which to obtain the value to delete
     */
    private void deleteFavourite(ListView display)
    {
        if (loginSystem.getCurrentUser() != null)
        {
            loginSystem.getCurrentUser().removeFavourite((String) display.
            getSelectionModel().getSelectedItem());
            showFavourites(favouritesDisplay);
        }
    }
    
    /**
     * Adds a listing
     * The parameters are the field which must be filled in
     */
    private void addListing(
    TextField pNameField,TextField nameField,ChoiceBox neighbourhoodField,
    TextField latitudeField,TextField longitudeField,ChoiceBox typeField,
    TextField priceField,TextField minNightsField,TextField availabilityField,
    Label addListingStatus)
    {
        //Checks if fields are empty and if they contain valid data
        if (pNameField.getText().length() < 4 || nameField.getText().length() < 3
        || neighbourhoodField.getSelectionModel().getSelectedItem() == null
        || typeField.getSelectionModel().getSelectedItem() == null)
        {
            addListingStatus.setText("Some fields may be blank or too short");
        }
        else
        {
            if (isValidNumber(latitudeField.getText()))
            {
                if (isValidNumber(longitudeField.getText()))
                {
                    if (isValidInt(priceField.getText(),0,999))
                    {
                        if (isValidInt(minNightsField.getText(),0,365))
                        {
                            if (isValidInt(availabilityField.getText(),0,365))
                            {
                                addListingStatus.setText(
                                    loginSystem.getCurrentUser().addListing(
                                        pNameField.getText(),
                                        nameField.getText(),
                                        (String) neighbourhoodField.
                                        getSelectionModel().getSelectedItem(),
                                        latitudeField.getText(),
                                        longitudeField.getText(),
                                        (String) typeField.
                                        getSelectionModel().getSelectedItem(),
                                        priceField.getText(),
                                        minNightsField.getText(),
                                        availabilityField.getText()));
                            }
                            else
                            {
                                addListingStatus.setText("Availability is not valid");
                            }
                        }
                        else
                        {
                            addListingStatus.setText("Minimum nights are not valid");
                        }
                    }
                    else
                    {
                        addListingStatus.setText("Price is not valid");
                    }
                }        
                else
                {
                    addListingStatus.setText("Longitude is not valid");
                }
            }
            else
            {
                addListingStatus.setText("Latitude is not valid");
            }
        }
    }

    /**
     * Allows the user to login to the application
     */
    private void login()
    {
        //Tries to log in through the loginSystem
        if (loginSystem.login(usernameInput.getText(),passwordInput.getText()))
        {
            hideLogin();
            passwordInput.setText("");
            updateNameDisplay();
            addListingButton.setDisable(false);
            showFavourites(favouritesDisplay);
        }
        else
        {
            loginStatus.setText("There was an error when logging in");
        }
    }
    
    /**
     * Checks if a string is a vlid number
     * 
     * @param text The string to check
     */
    public boolean isValidNumber(String text)
    {
        try {
            //Tried to parse the text as a double
            Double.parseDouble(text);
            return true;
        }
        catch(NumberFormatException e ) {
            return false;
        }
    }

    /**
     * Checks if a string is a valid integer
     * and within a range
     * 
     * @param text The string to test
     * @param min The minimum value
     * @param max The maximum allowed value
     */
    public boolean isValidInt(String text,int min, int max)
    {
        //Tried to parse the string as an integer and check for the range constraint
        try {
            if (Integer.parseInt(text)<min || Integer.parseInt(text)>max)
            {
                return false;
            }
            return true;
        }
        catch(NumberFormatException e ) {
            return false;
        }
    }

    /**
     * Logs the current user out of the system
     */
    private void logout()
    {
        //
        showLogin();
        loginSystem.removeCurrentUser();
        updateNameDisplay();
        addListingButton.setDisable(true);
        showFavourites(favouritesDisplay);
    }

    /**
     * Checks if the username is valid
     * 
     * @return booolean If the username is valid
     */
    private boolean checkUserName(String text)
    {
        for (char letter: text.toCharArray())
        {
            //Checks for spaces
            if (letter == ' ')
            {
                return false;
            }
        }
        if (text.length() < 5 || text.length() > 15)
        {
            return false;
        }
        return true;
    }

    /**
     * Checks if the password is valid
     * 
     * @return booolean If the password is valid
     */
    private boolean checkPassWord(String text)
    {
        if (text.length() < 5 || text.length() > 15)
        {
            return false;
        }
        return true;
    }

    /**
     * Used to create new accounts
     */
    private void signUp()
    {
        //Checks if the username and password fields are valid before creating an account
        if (checkUserName(usernameInput.getText()))
        {
            if (checkPassWord(passwordInput.getText()))
            {
                loginStatus.setText(loginSystem.addUser(usernameInput.getText(),passwordInput.getText()));
            }
            else
            {
                loginStatus.setText("Your password must be greater than 5 and less then 15 characters");
            }
        }
        else
        {
            loginStatus.setText("Your username cannot contain spaces and must be greater than 5 and less than 15 characters");
        }
    }
    
    /**
     * Hides the login from the user
     */
    public void hideLogin()
    {
        //Gets the height of the top bar
        double addedHeight = ((Pane)((BorderPane) root.getParent()).getTop()).getHeight();
        //Adds the top bar to the height of the login box
        double totalHeight = -(loginBox.getHeight() + addedHeight+5);
        moveBox(loginBox,totalHeight);
        moveBox(midBox,totalHeight+50);
    }

    /**
     * Shows the login to the user
     */
    public void showLogin()
    {
        moveBox(loginBox,0);
        moveBox(midBox,25);
    }

    /** 
     * Moves containers around in the panel
     * 
     * @param object The Pane to move
     * @param position The position to move to
     */
    public void moveBox(Pane object, double position)
    {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), object);
        tt.setToY(position);
        tt.play();
    }

    /**
     * Changes the name displayed to the user
     * depending on who is logged in
     */
    public void updateNameDisplay()
    {
        if (loginSystem.getCurrentUser() == null)
        {
            nameDisplay.setText("Not logged In");
        }
        else
        {
            nameDisplay.setText(loginSystem.getCurrentUser().getName());
            //Sets the text to the username of the current user
        }

    }

    /**
     * Accessor method to return the panel that stores all the nodes
     * 
     * @return root - The panel
     */
    public Pane getPane()
    {
        return root;
    }
}
