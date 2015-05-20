package view;

import controller.DAO;
import controller.MainWindowController;
import javafx.scene.Node;
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
import java.util.LinkedList;

/**
 * Created by Alexander on 08/05/2015.
 */
public class EditTimelinePopOver extends PopOver{
    private VBox vbox = new VBox();
    private ComboBox  myComboBox;
    private TextField titleTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private Rectangle addBtn = new Rectangle();

    public EditTimelinePopOver(MainWindowController mwc){
        DAO myDao = new DAO();

        myComboBox = new ComboBox();

        LinkedList<DayTimeline> allDayTimelines = myDao.getAllTimelines();
        for(Timeline t : allDayTimelines){
            myComboBox.getItems().addAll(t.getTitle());
        }
        myComboBox.setPromptText("Choose the timeline you dickhead!");
        //String timelineTitle = (String)myComboBox.getSelectionModel().getSelectedItem();
        //titleTextField.setPromptText(timelineTitle);

        this.setHideOnEscape(true);
        this.autoHideProperty().setValue(true);
        this.autoHideProperty().setValue(true);

        this.setWidth(400);
        this.setHeight(200);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);


       // titleTextField.setText(timelineTitle);

        myComboBox.prefWidthProperty().bind(startDatePicker.widthProperty());
        startDatePicker.prefWidthProperty().bind(vbox.widthProperty());
        endDatePicker.prefWidthProperty().bind(vbox.widthProperty());

        addBtn.setWidth(100);
        addBtn.setHeight(100);

        addBtn.setOnMouseClicked(editTimeline -> {

            LocalDate localStart = startDatePicker.getValue();
            LocalDate localEnd = endDatePicker.getValue();

            GregorianCalendar gregorianStart = new GregorianCalendar();
            gregorianStart.set(localStart.getYear(), localStart.getMonthValue(), localStart.getDayOfMonth());
            GregorianCalendar gregorianEnd = new GregorianCalendar();
            gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue(), localEnd.getDayOfMonth());
            String timelineTitle = (String)myComboBox.getSelectionModel().getSelectedItem();
            DayTimeline dayTimeline = new DayTimeline(timelineTitle, descriptionTextArea.getText(), gregorianStart, gregorianEnd);
            DAO dao = new DAO();
            try {
                dao.updateTimelineV2(dao.getDayTimeline(timelineTitle), dayTimeline);
                mwc.redrawTimelines();
            } catch (Exception e) {
                e.printStackTrace();
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