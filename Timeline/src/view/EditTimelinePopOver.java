package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;
import org.controlsfx.control.PopOver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Alexander, Hatem and Mauro on 30/05/2015.
 */
public class EditTimelinePopOver extends PopOver{
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();
    private ComboBox  myComboBox = new ComboBox();
    private TextField titleTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();
    private Button addBtn;
    private Button deleteButton;

    DayTimeline selectedTimeline = new DayTimeline();

    public EditTimelinePopOver(MainWindowController mwc) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SQLDAO sqldao = new SQLDAO();
        this.setHideOnEscape(true);
        this.autoHideProperty().setValue(true);
        this.setWidth(140.0);
        this.setHeight(100);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        titleTextField.setPrefWidth(140.0);
        descriptionTextArea.setPrefWidth(140.0);
        descriptionTextArea.setPrefHeight(150);
        descriptionTextArea.setWrapText(true);
        myComboBox.setPrefWidth(140.0);

        LinkedList<DayTimeline> allDayTimelines = sqldao.getAllTimelines();

        for (Timeline t : allDayTimelines) {
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

        ImageView image2 =  new ImageView(new Image(getClass().getResourceAsStream("Icons/Delete.png")));
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("Icons/FinishEditing.png")));
        image.setFitHeight(30);
        image.setFitWidth(30);
        image2.setFitHeight(30);
        image2.setFitWidth(30);
        addBtn = new Button("", image);
        deleteButton = new Button("", image2);
        hbox.getChildren().addAll(addBtn, deleteButton);
        hbox.setSpacing(45.0);

        addBtn.setOnMouseEntered(event -> {
            addBtn.setTooltip(new Tooltip("Finish editing"));
        });

        deleteButton.setOnMouseEntered(event -> {
            deleteButton.setTooltip(new Tooltip("Delete timeline"));
        });

        addBtn.setOnMouseClicked(editTimeline -> {
            if(titleTextField.getText().length() > 30) {
                this.hide();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Too big title");
                alert.setHeaderText("Warning");
                alert.setContentText("The title has to be at max 30 characters long.");
                alert.showAndWait();
                throw new IllegalArgumentException("The title has to be at max 30 characters long.");
            }
            if (titleTextField.getText().isEmpty()) {
                this.hide();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Insufficient information");
                alert.setHeaderText("Warning");
                alert.setContentText("The title has to be filled to create a timeline");
                alert.showAndWait();
                throw new IllegalArgumentException("The title has to be filled to create a timeline");
            }

            DayTimeline dayTimeline = new DayTimeline(titleTextField.getText(), descriptionTextArea.getText(), selectedTimeline.getStartDate(), selectedTimeline.getEndDate());

            try {
                DayTimeline timelineToDelete = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                dayTimeline.getEventNTs().addAll(timelineToDelete.getEventNTs());
                dayTimeline.getEventTimes().addAll(timelineToDelete.getEventTimes());
                sqldao.deleteTimeline(timelineToDelete.getTitle());
                sqldao.saveTimeline(dayTimeline);
                mwc.redrawOneTimeline(timelineToDelete.getTitle(), dayTimeline);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error Connection");
                alert.setHeaderText("Error!");
                alert.setContentText("There was an error trying to connect to the database");
                alert.showAndWait();
                e.printStackTrace();
            }
            this.hide();
        });
        deleteButton.setOnMouseClicked(event -> {
            try {
                selectedTimeline = MainWindowController.mainWindowController.getThisTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                MainWindowController.mainWindowController.redrawRemoveTimeline(selectedTimeline);
                if (selectedTimeline.getTitle() == null) {
                    sqldao.deleteTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                } else {
                    sqldao.deleteTimeline(selectedTimeline.getTitle());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        vbox.getChildren().addAll(myComboBox, titleTextField, descriptionTextArea, hbox);
        vbox.setPrefHeight(240.0);
        vbox.setPrefWidth(140.0);
        this.setContentNode(vbox);
    }
}