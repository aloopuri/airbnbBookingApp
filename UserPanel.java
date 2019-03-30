import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.util.*;
import java.lang.*;
import javafx.collections.*;
import java.util.*;
import javafx.geometry.*;
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
    private GridPane topPane, centerPane, bottomPane;
    private ListView favouritesDisplay;
    /**
     * Constructor for objects of class UserPanel
     */
    public UserPanel(LoginSystem loginSystem)
    {
        this.loginSystem = loginSystem;
        root = new BorderPane();
        centerPane = new GridPane();
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(10, 10, 10, 10));
        centerPane.setPrefWidth(600);
        centerPane.setPrefHeight(600);
        centerPane.setVgap(10);
        centerPane.setHgap(10);
        topPane = new GridPane();
        topPane.setAlignment(Pos.TOP_LEFT);

        usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        Label userName = new Label("Username: ");

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        Label password = new Label("Password: ");

        loginStatus = new Label();

        HBox horizontalBox = new HBox();

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());
        signupButton = new Button("Sign Up");
        signupButton.setOnAction(e -> signUp());
        Button logoutButt = new Button("logout");
        logoutButt.setOnAction(e -> logout());

        horizontalBox.getChildren().addAll(signupButton, new Label(" "), loginButton, new Label(" "));
        topPane.add(userName, 0, 0);
        topPane.add(usernameInput, 0, 1);
        topPane.add(password, 0, 2);
        topPane.add(passwordInput, 0 ,3);
        topPane.add(loginStatus, 0, 4);
        topPane.add(horizontalBox, 0, 5);
        root.setTop(topPane);

        Label favTitle = new Label("FAVOURITES");
        favouritesDisplay = new ListView();
        Button removeFavourite = new Button("Delete");
        Button showFavourite = new Button("Show");
        Button saveFavourite = new Button("Save");
        showFavourite.setOnAction(e -> showFavourites(favouritesDisplay));
        removeFavourite.setOnAction(e -> deleteFavourite(favouritesDisplay));
        saveFavourite.setOnAction(e -> saveFavourites());

        Label createProperty = new Label("CREATE YOUR PROPERTY");
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
        typeField.setId("typeField");
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

        GridPane innerPane = new GridPane();
        innerPane.setPadding(new Insets(10, 10, 10, 10));
        innerPane.setPrefWidth(600);
        innerPane.setPrefHeight(600);
        innerPane.setVgap(10);
        innerPane.setHgap(10);

        innerPane.add(pNameField, 0, 0);
        innerPane.add(nameField, 0, 1);
        innerPane.add(priceField, 0, 2);
        innerPane.add(neighbourhoodField, 0, 3);
        innerPane.add(latitudeField, 0, 4);
        innerPane.add(longitudeField, 0, 5);
        innerPane.add(typeField, 0, 6);
        innerPane.add(minNightsField, 0, 7);
        innerPane.add(availabilityField, 0, 8);
        innerPane.add(addListingButton, 0, 9);
        innerPane.add(addListingStatus, 0, 10);

        centerPane.add(new Label("                       "), 0, 0);
        centerPane.add(favTitle, 1, 0);
        centerPane.add(favouritesDisplay, 1, 1);
        centerPane.add(showFavourite, 1, 2);
        centerPane.add(removeFavourite, 1, 3);
        centerPane.add(saveFavourite, 1, 4);
        centerPane.add(new Label("                       "), 2, 0);
        centerPane.add(createProperty, 3, 0);
        centerPane.add(innerPane, 3, 1);

        root.setCenter(centerPane);

        bottomPane = new GridPane();
        Label account = new Label("Account");
        nameDisplay = new Label("Not logged in");
        bottomPane.add(account, 0, 0);
        bottomPane.add(nameDisplay, 0, 1);
        bottomPane.add(new Label("   "), 1, 1);
        bottomPane.add(logoutButt, 2, 1);
        root.setBottom(bottomPane);

        root.getStylesheets().addAll(this.getClass().getResource("UserLayout.css").toExternalForm());
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
        double totalHeight = -(topPane.getHeight() + addedHeight+5);
        moveBox(topPane,totalHeight);
        moveBox(centerPane,totalHeight+50);
    }

    /**
     * Shows the login to the user
     */
    public void showLogin()
    {
        moveBox(topPane,0);
        moveBox(centerPane,25);
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
