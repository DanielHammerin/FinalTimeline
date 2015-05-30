package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;
import org.controlsfx.control.PopOver;
import java.sql.SQLException;
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
    private Button loadButton;
    private Button refreshButton;
    private ObservableList <String> timelinesLV = FXCollections.observableArrayList();

    /**
     * This is the constructor for a pop-over which handles the timeline loading process from the database
     * @param mainVBox ThThe VBox where the graphical timeline should be added to
     */

    public LoadTimelinePopOver(VBox mainVBox) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        c1.setPercentWidth(50);
        c2.setPercentWidth(50);

        myGridPane.getColumnConstraints().addAll(c1, c2);

        ImageView image1 = new ImageView(new Image(getClass().getResourceAsStream("Icons/LoadTimeline.png")));
        ImageView image2 = new ImageView(new Image(getClass().getResourceAsStream("Icons/Refresh.png")));
        image1.setFitHeight(40);
        image1.setFitWidth(50);
        image2.setFitHeight(40);
        image2.setFitWidth(40);
        loadButton = new Button("Load timeline",image1);
        refreshButton = new Button("Refresh",image2);
        refreshButton.setAlignment(Pos.BOTTOM_RIGHT);

        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setContentNode(myGridPane);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        myGridPane.setPrefHeight(300);
        myGridPane.setPrefWidth(300);

        messageLabel.setTextFill(Color.DARKRED);
        LinkedList<DayTimeline> allTimelines = sqldao.getAllTimelines();

        for (int i = 0; i < allTimelines.size(); i++){ // add timeline titles to the list view
            timelinesLV.add(allTimelines.get(i).getTitle());
        }
        myListview.getItems().addAll(timelinesLV);
        
        /**
         * Handles the event which occures when the user presses the loadButton
         */
        loadButton.setOnMouseClicked(loadTimeline -> {
            String selectedItem = myListview.getSelectionModel().getSelectedItem(); // get the timeline form the title
            try {
                DayTimeline dayTimeline = sqldao.getTimeline(selectedItem);
                NewDayTimelineGrid d = new NewDayTimelineGrid(dayTimeline);
                MainWindowController.allTheTimelines.add(d.getDayTimeline());
                mainVBox.getChildren().add(d);
                this.hide();

            }catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait(); e.printStackTrace();
            }
        });

        myGridPane.add(myListview,0,0,2,1);
        myGridPane.add(loadButton,0,1);
        myGridPane.add(refreshButton,1,1,1,1);
        myGridPane.add(messageLabel,0,2,2,1);
        myGridPane.setPrefHeight(250.0);
        this.setContentNode(myGridPane);
    }
}