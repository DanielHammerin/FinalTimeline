package view;

import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.*;
import org.controlsfx.control.PopOver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
/**
 * Created by Alexander on 08/05/2015.
 */
public class NewEventPopOver extends PopOver{
    private VBox vbox = new VBox();
    private ComboBox  myComboBox;
    private TextField titleTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private Rectangle addBtn = new Rectangle();

    public NewEventPopOver(ArrayList<Timeline> timelines){

        myComboBox = new ComboBox();

        for(Timeline t : timelines){
            myComboBox.getItems().add(t);
        }
        myComboBox.setPromptText("Choose the timeline you dickhead!");

        this.setHideOnEscape(true);
        this.autoHideProperty().setValue(true);
        this.autoHideProperty().setValue(true);

        this.setWidth(400);
        this.setHeight(200);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);

        titleTextField.setPromptText("Title");
        descriptionTextArea.setPromptText("Description");

        startDatePicker.prefWidthProperty().bind(vbox.widthProperty());
        endDatePicker.prefWidthProperty().bind(vbox.widthProperty());

        addBtn.setWidth(100);
        addBtn.setHeight(100);

        addBtn.setOnMouseClicked(addEvent -> {
            LocalDate localStart = startDatePicker.getValue();
            LocalDate localEnd = endDatePicker.getValue();

            GregorianCalendar gregorianStart = new GregorianCalendar();
            gregorianStart.set(localStart.getYear(), localStart.getMonthValue(), localStart.getDayOfMonth());
            GregorianCalendar gregorianEnd = new GregorianCalendar();
            gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue(), localEnd.getDayOfMonth());

            if (endDatePicker.getValue() == null) {
                EventNT newEvent = new EventNT(titleTextField.getText(), descriptionTextArea.getText(), gregorianStart);
                Timeline selectedTimeline  = (Timeline)myComboBox.getSelectionModel().getSelectedItem();
                selectedTimeline.addEventNT(newEvent);
                //yt.addEventNT(newEvent);
            } else {
                EventTime newEvent = new EventTime(titleTextField.getText(), descriptionTextArea.getText(), gregorianStart, gregorianEnd);
                Timeline selectedTimeline  = (Timeline)myComboBox.getSelectionModel().getSelectedItem();
                selectedTimeline.addEventTime(newEvent);
                //yt.addEventTime(newEvent);
            }
            this.hide();
        });

        vbox.getChildren().add(myComboBox);
        vbox.getChildren().add(titleTextField);
        vbox.getChildren().add(descriptionTextArea);
        vbox.getChildren().add(startDatePicker);
        vbox.getChildren().add(endDatePicker);
        vbox.getChildren().add(addBtn);

        this.setContentNode(vbox);
    }
}
