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
    public UserPanel()
    {
        loginSystem = new LoginSystem();
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
        
        addListingButton = new Button("Create");
        addListingButton.setOnAction(e -> addListing(
        pNameField,nameField,neighbourhoodField));
        
        newPropBox.getChildren().addAll(
        pNameField,nameField,neighbourhoodField,addListingButton);
        
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
    TextField pNameField,TextField nameField,ChoiceBox neighbourhoodField)
    {
        System.out.println(pNameField.getText());
    }
    
    private void login()
    {
        if (loginSystem.login(usernameInput.getText(),passwordInput.getText()))
        {
            System.out.println("Logged in");
            hideLogin();
            passwordInput.setText("");
            updateNameDisplay();
        }
        else
        {
            loginStatus.setText("There was an error when logging in");
        }
    }
    
    private void logout()
    {
        showLogin();
        loginSystem.removeCurrentUser();
        updateNameDisplay();
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
