package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.DayTimeline;
import org.controlsfx.control.PopOver;

import javafx.scene.control.Alert.*;

import java.time.LocalDate;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 29/04/2015.
 * This popover defines the structure of the controls which the user can use to create a new timeline
 */
public class CreateTimelinePopOver extends PopOver{

    private VBox vbox;
    private SQLDAO sqldao = new SQLDAO();
    private TextField titleTxt = new TextField();
    private javafx.scene.control.TextArea descriptionTxt = new javafx.scene.control.TextArea();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private Button createButton;

    /**
     * Constructor of the pop-over which handles the creation of a new timeline
     * @param vBoxMain The VBox where the graphical timeline should be added to
     */
    public CreateTimelinePopOver(VBox vBoxMain) {
        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setWidth(150);
        this.setHeight(300);

        titleTxt.setPromptText("Timeline title");
        titleTxt.setPrefWidth(150);
        descriptionTxt.setPromptText("Timeline description");
        descriptionTxt.setPrefWidth(150);
        descriptionTxt.setWrapText(true);
        startDatePicker.setPromptText("Timeline start date");
        endDatePicker.setPromptText("Timeline end date");

        Image create = new Image(getClass().getResourceAsStream("Icons/createTimeline.png"));
        ImageView image = new ImageView(create);
        image.setFitWidth(40.0);
        image.setFitHeight(40.0);
        createButton = new Button("Create Timeline", image);

        //Event which initializes the creation of a TimelineGrid and the Timeline
        createButton.setOnMouseClicked(CreateTimeline -> {
            if (titleTxt.getText().isEmpty() || startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Insufficient information");
                alert.setHeaderText("Warning");
                alert.setContentText("The title, start date and end date fields have to be filled to create a timeline");
                alert.showAndWait();
                throw new IllegalArgumentException("The title, start date and end date fields have to be filled to create a timeline");
            }
            if (titleTxt.getText().length() > 30) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Too big title");
                alert.setHeaderText("Warning");
                alert.setContentText("The title has to be at max 30 characters long.");
                alert.showAndWait();
                throw new IllegalArgumentException("The title has to be at max 30 characters long.");
            }


            LocalDate localStart = startDatePicker.getValue();
            LocalDate localEnd = endDatePicker.getValue();

            if (localStart.isAfter(localEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Time span error");
                alert.setHeaderText("Error!");
                alert.setContentText("The start date has to be before the end date");
                alert.showAndWait();
                throw new IllegalArgumentException("The start date has to be before the end date");
            }
            GregorianCalendar gregorianStart = new GregorianCalendar();
            gregorianStart.set(localStart.getYear(), localStart.getMonthValue() - 1, localStart.getDayOfMonth());

            GregorianCalendar gregorianEnd = new GregorianCalendar();
            gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue() - 1, localEnd.getDayOfMonth());

            try {
                NewDayTimelineGrid d = new NewDayTimelineGrid(new DayTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));
                if (!sqldao.isThereADuplicateTimeline(d.getDayTimeline())) {
                    try {
                        sqldao.saveTimeline(d.getDayTimeline());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MainWindowController.allTheTimelines.add(d.getDayTimeline());
                    vBoxMain.getChildren().add(d);
                    this.hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Duplicated Timelines");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There is already a timeline named " + d.getDayTimeline().getTitle() + " in the database. Please choose another name.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Database Error Connection");
                alert.setHeaderText("Error!");
                alert.setContentText("There was an error trying to connect to the database, the Timeline could not be saved properly");
                alert.showAndWait();
                e.printStackTrace();
            }
        });

        vbox = new VBox();
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        HBox hbox = new HBox();
        hbox.getChildren().addAll(createButton);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        vbox.getChildren().addAll(titleTxt, startDatePicker, endDatePicker, descriptionTxt, hbox);
        vbox.setPrefHeight(306.0);
        this.setContentNode(vbox);
    }
}