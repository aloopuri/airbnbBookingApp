import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.*;
import javafx.scene.paint.*;

public class ListingsEachBorough extends Application
{
  @Override
  public void start(Stage stage) throws Exception
  {
    URL url = getClass().getResource("ListingsInBoroughView.fxml");
    Parent root = FXMLLoader.load(url);
    Scene scene = new Scene(root);

    stage.setTitle("Map");
    stage.setScene(scene);
    stage.show();
  }
}
