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

public class MapPanelApplication extends Application
{
  private MapPanelController mapPanelController;

  @Override
  public void start(Stage stage) throws Exception
  {
    URL url = getClass().getResource("MapView.fxml");
    Parent root = FXMLLoader.load(url);
    Scene scene = new Scene(root);

    stage.setTitle("Map");
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void boroughClick(ActionEvent e)
  {
    mapPanelController.openBoroughWindow();
  }

  @FXML
  private void priceRangeClick(ActionEvent e)
  {
    mapPanelController.showSelectedPriceRangeView();
  }
}
