
package view;


import controller.MainWindowController;
import controller.SQLDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ListView;
import model.*;


import org.controlsfx.control.PopOver;

import java.util.LinkedList;


/**
 * Created by Alexander on 27/04/2015.
 * A pop-over which handles the timeline loading process from the database
 */
public class LoadTimelinePopOver extends PopOver {
    private GridPane myGridPane = new GridPane();
    private ListView<String> myListview = new ListView<String>();
    private Label messageLabel = new Label();
    private SQLDAO sqldao = new SQLDAO();
    private myRectangleButtons loadRect;
    private myRectangleButtons refreshRect;
    private ObservableList <String> timelinesLV = FXCollections.observableArrayList();

    /**
     * This is the constructor for a pop-over which handles the timeline loading process from the database
     * @param mainVBox ThThe VBox where the graphical timeline should be added to
     */

    public LoadTimelinePopOver(VBox mainVBox){
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c1.setPercentWidth(50);
        c2.setPercentWidth(50);

        myGridPane.getColumnConstraints().addAll(c1,c2);

        loadRect = new myRectangleButtons("Load",Color.DARKGREEN);
        refreshRect = new myRectangleButtons("Refresh",Color.GOLD);

        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setContentNode(myGridPane);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        myGridPane.setPrefWidth(500);

        messageLabel.setTextFill(Color.DARKRED);
        LinkedList<DayTimeline> allTimelines = sqldao.getAllTimelines();

        for (int i = 0; i < allTimelines.size(); i++){ // add timeline titles to the list view
            timelinesLV.add(allTimelines.get(i).getTitle());
        }

        // seudocode a for statement should be added here that discards all timelines that are currently open in the windows so they don't show in the listview

        myListview.getItems().addAll(timelinesLV);

        //This event defines the event which should be executed when a user clicks on the load-button

        loadRect.setOnMouseClicked(loadTimeline -> {
            String duda = myListview.getSelectionModel().getSelectedItem(); // get the timeline form the title
            try {
                NewDayTimelineGrid d = new NewDayTimelineGrid(sqldao.getTimeline(duda));
                MainWindowController.allTheTimelines.add(d.getDayTimeline());
                mainVBox.getChildren().add(d);
                this.hide();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        myGridPane.add(myListview,0,0,2,1);
        myGridPane.add(loadRect,0,1);
        myGridPane.add(refreshRect,1,1,1,1);
        myGridPane.add(messageLabel,0,2,2,1);
        this.setContentNode(myGridPane);
    }
}