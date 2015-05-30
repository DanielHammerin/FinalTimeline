package view;
import controller.MainWindowController;
import controller.SQLDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.*;
import org.controlsfx.control.PopOver;

import java.sql.SQLException;
import java.util.LinkedList;
/**
 * Created by Alexander on 08/05/2015.
 */
public class EditTimelinePopOver extends PopOver{
    private VBox vbox = new VBox();
    private ComboBox  myComboBox = new ComboBox();;
    private TextField titleTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();
    private Rectangle addBtn = new Rectangle();


    DayTimeline selectedTimeline = new DayTimeline();

    public EditTimelinePopOver(MainWindowController mwc) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SQLDAO sqldao = new SQLDAO();
        this.setHideOnEscape(true);
        this.autoHideProperty().setValue(true);
        this.setWidth(400);
        this.setHeight(200);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);

        addBtn.setWidth(100);
        addBtn.setHeight(100);

        LinkedList<DayTimeline> allDayTimelines = sqldao.getAllTimelines();
        for(Timeline t : allDayTimelines){
            myComboBox.getItems().addAll(t.getTitle());
        }

        myComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    selectedTimeline = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("Database connection error");
                    alert.showAndWait();
                    e.printStackTrace();
                }

                titleTextField.setText(selectedTimeline.getTitle());
                descriptionTextArea.setText(selectedTimeline.getDescription());
            }
        });

        myComboBox.getSelectionModel().selectFirst();

        try {
            selectedTimeline = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection");
            alert.setHeaderText("Error!");
            alert.setContentText("Database connection Error");
            alert.showAndWait();

            e.printStackTrace();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection");
            alert.setHeaderText("Error!");
            alert.setContentText("Database connection Error");
            alert.showAndWait();

            e.printStackTrace();
        } catch (InstantiationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection");
            alert.setHeaderText("Error!");
            alert.setContentText("Database connection Error");
            alert.showAndWait();

            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection");
            alert.setHeaderText("Error!");
            alert.setContentText("Database connection Error");
            alert.showAndWait();

            e.printStackTrace();
        }
        this.hide();

        try {
            selectedTimeline = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database connection");
            alert.setHeaderText("Error!");
            alert.setContentText("Database connection error");
            alert.showAndWait();

            e.printStackTrace();
        }

        addBtn.setOnMouseClicked(editTimeline -> {
            DayTimeline dayTimeline = new DayTimeline(titleTextField.getText(), descriptionTextArea.getText(), selectedTimeline.getStartDate(), selectedTimeline.getEndDate());

            try {
                DayTimeline timelineToDelete = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                dayTimeline.getEventNTs().addAll(timelineToDelete.getEventNTs());
                dayTimeline.getEventTimes().addAll(timelineToDelete.getEventTimes());
                sqldao.deleteTimeline(timelineToDelete.getTitle());
                sqldao.saveTimeline(dayTimeline);
                mwc.redrawOneTimeline(dayTimeline);
            }catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();
                    e.printStackTrace();
                }
                this.hide();
            });
            vbox.getChildren().addAll(myComboBox, titleTextField, descriptionTextArea, addBtn);
            this.setContentNode(vbox);
        }
    }