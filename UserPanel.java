import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.animation.*;
import javafx.util.*;
import java.lang.*;
import javafx.collections.*;
/**
 * Write a description of class UserPanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class UserPanel
{
    // instance variables - replace the example below with your own
    private BorderPane root;
    private LoginSystem loginSystem;
    private TextField usernameInput, passwordInput;
    private Button loginButton, signupButton, addListingButton;
    private Label loginStatus,nameDisplay;
    private VBox loginBox, accountBox, midBox;
    /**
     * Constructor for objects of class UserPanel
     */
    public UserPanel(LoginSystem loginSystem)
    {
        this.loginSystem = loginSystem;
        root = new BorderPane();

        loginBox = new VBox();

        usernameInput = new TextField();
        usernameInput.setPromptText("Enter your username");
        loginBox.getChildren().addAll(new Label("Username: "),usernameInput);

        passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter your password");
        loginBox.getChildren().addAll(new Label("Password: "),passwordInput);

        loginStatus = new Label();
        loginBox.getChildren().add(loginStatus);

        HBox horizontalBox = new HBox();

        loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        signupButton = new Button("Sign Up");
        signupButton.setOnAction(e -> signUp());

        horizontalBox.getChildren().addAll(loginButton,signupButton);
        loginBox.getChildren().add(horizontalBox);
        root.setTop(loginBox);

        midBox = new VBox();

        Button logoutButt = new Button("logout");
        logoutButt.setOnAction(e -> logout());

        midBox.getChildren().add(logoutButt);

        HBox mainBox = new HBox();

        VBox favouritesBox = new VBox();
        favouritesBox.getChildren().add(new Label("FAVOURITES"));
        
        ListView favouritesDisplay = new ListView();
        Button removeFavourite = new Button("Delete");
        favouritesBox.getChildren().addAll(favouritesDisplay,
        removeFavourite);
        mainBox.getChildren().add(favouritesBox);

        VBox newPropBox = new VBox();

        TextField pNameField = new TextField();
        pNameField.setPromptText("Listing Description");

        TextField nameField = new TextField();
        nameField.setPromptText("Your name");

        ChoiceBox neighbourhoodField = new ChoiceBox(
            FXCollections.observableArrayList("Kingston upon Thames", "Croydon",
            "Bromley","Hounslow","Ealing","Havering","Hillingdon","Harrow",
            "Brent","Barnet","Enfield"));

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

    private void addListing(
    TextField pNameField,TextField nameField,ChoiceBox neighbourhoodField,
    TextField latitudeField,TextField longitudeField,ChoiceBox typeField,
    TextField priceField,TextField minNightsField,TextField availabilityField,
    Label addListingStatus)
    {
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

    private void login()
    {
        if (loginSystem.login(usernameInput.getText(),passwordInput.getText()))
        {
            System.out.println("Logged in");
            hideLogin();
            passwordInput.setText("");
            updateNameDisplay();
            addListingButton.setDisable(false);
        }
        else
        {
            loginStatus.setText("There was an error when logging in");
        }
    }

    public boolean isValidNumber(String text)
    {
        try {
            Double.parseDouble(text);
            return true;
        }
        catch(NumberFormatException e ) {
            return false;
        }
    }

    public boolean isValidInt(String text,int min, int max)
    {
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

    private void logout()
    {
        showLogin();
        loginSystem.removeCurrentUser();
        updateNameDisplay();

        addListingButton.setDisable(true);
    }

    private boolean checkUserName(String text)
    {
        for (char letter: text.toCharArray())
        {
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

    private boolean checkPassWord(String text)
    {
        if (text.length() < 5 || text.length() > 15)
        {
            return false;
        }
        return true;
    }

    private void signUp()
    {
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

    public void hideLogin()
    {
        double addedHeight = ((Pane)((BorderPane) root.getParent()).getTop()).getHeight();
        double totalHeight = -(loginBox.getHeight() + addedHeight);
        moveBox(loginBox,totalHeight);
        moveBox(midBox,totalHeight);
    }

    public void showLogin()
    {
        moveBox(loginBox,0);
        moveBox(midBox,0);
    }

    public void moveBox(Pane object, double position)
    {
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), object);
        tt.setToY(position);
        tt.play();
    }

    public void updateNameDisplay()
    {
        if (loginSystem.getCurrentUser() == null)
        {
            nameDisplay.setText("Not logged In");
        }
        else
        {
            nameDisplay.setText(loginSystem.getCurrentUser().getName());
        }

    }

    public Pane getPane()
    {
        return root;
    }
}
