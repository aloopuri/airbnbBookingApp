import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
/**
 * This creates a simple display of a statistic
 * It consists of only a title and a label which holds the statistic
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class BasicStatisticDisplay extends DataDisplay
{
    private Label title = new Label();
    private Label data = new Label();

    /**
     * Creates the basic statistic display
     */
    public BasicStatisticDisplay(StatisticPanel statPanel, String title, String data)
    {
        super(statPanel);

        this.title.setText(title);
        this.data.setText(data);

        this.title.setTextAlignment(TextAlignment.CENTER);
        this.data.setTextAlignment(TextAlignment.CENTER);

        for (int i=0; i<2 ; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(50);
            row.setVgrow(Priority.ALWAYS);
            getData().getRowConstraints().add(row);
        }

        getData().setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        getData().setHalignment(this.title, HPos.CENTER);
        getData().setHalignment(this.data, HPos.CENTER);
        getData().setAlignment(Pos.CENTER);

        getData().add(this.title, 0, 0);
        getData().add(this.data, 0, 1);

    }

}
