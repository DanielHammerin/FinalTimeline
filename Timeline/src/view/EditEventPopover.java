package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.EventNT;
import model.EventTime;
import org.controlsfx.control.PopOver;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 15/05/2015.
 */
public class EditEventPopover extends PopOver {

    private VBox vbox = new VBox();
    private TextField titleField = new TextField();
    private SQLDAO sqldao = new SQLDAO();
    private TextField descriptionField = new TextField();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private Rectangle saveRect = new Rectangle(50,50);
    private Rectangle abortRect = new Rectangle(25,25);
    private String oldEventName;


    /**
     * This pop-over makes it possible for the user to change an event
     * @param event
     */
    public EditEventPopover(EventTime event){
        oldEventName = event.getTitle();
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());

        int monthStart = event.getStartTime().get((Calendar.MONTH)) % 12;
        LocalDate startDate = LocalDate.of(event.getStartTime().get(Calendar.YEAR), monthStart, event.getStartTime().get(Calendar.DAY_OF_MONTH));

        int monthEnd = event.getFinishTime().get((Calendar.MONTH)) % 12;
        LocalDate endDate = LocalDate.of(event.getFinishTime().get(Calendar.YEAR), monthEnd, event.getFinishTime().get(Calendar.DAY_OF_MONTH));
        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);

        vbox.getChildren().addAll(titleField, descriptionField, startDatePicker, endDatePicker, saveRect, abortRect);
        this.setContentNode(vbox);

        //Saves the changes from the GUI in the event
        saveRect.setOnMouseClicked(saveChanges -> {
            event.setTitle(titleField.getText());
            event.setDescription(descriptionField.getText());

            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();

            GregorianCalendar gregorianStart = new GregorianCalendar();
            Date tempStartDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
            gregorianStart.setTime(tempStartDate);
            event.setStartTime(gregorianStart);

            GregorianCalendar gregorianEnd = new GregorianCalendar();
            Date tempEndDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
            gregorianEnd.setTime(tempEndDate);
            event.setFinishTime(gregorianEnd);
            try {
                sqldao.updateEventTime(oldEventName, event);
                String daytimelineTitle = sqldao.getTimelineFromEventTime(event);
                MainWindowController.mainWindowController.redrawOneTimeline(new NewDayTimelineGrid(sqldao.getTimeline(daytimelineTitle)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            this.hide();
        });

        abortRect.setOnMouseClicked(abort -> {
            this.hide();
        });
    }

    public EditEventPopover(EventNT event){
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());

        int monthStart = event.getDateOfEvent().get((Calendar.MONTH)) % 12;
        LocalDate startDate = LocalDate.of(event.getDateOfEvent().get(Calendar.YEAR), monthStart, event.getDateOfEvent().get(Calendar.DAY_OF_MONTH));

        startDatePicker.setValue(startDate);

        vbox.getChildren().addAll(titleField, descriptionField, startDatePicker, saveRect, abortRect);
        this.setContentNode(vbox);

        //Saves the changes from the GUI in the event
        saveRect.setOnMouseClicked(saveChanges -> {
            oldEventName = event.getTitle();
            event.setTitle(titleField.getText());
            event.setDescription(descriptionField.getText());

            LocalDate start = startDatePicker.getValue();
            GregorianCalendar gregorianStart = new GregorianCalendar();
            Date tempStartDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
            gregorianStart.setTime(tempStartDate);
            event.setDateOfEvent(gregorianStart);
            try {
                sqldao.updateEventNT(oldEventName, event);
                String daytimelineTitle = sqldao.getTimelineFromEventNT(event);
                MainWindowController.mainWindowController.redrawOneTimeline(new NewDayTimelineGrid(sqldao.getTimeline(daytimelineTitle)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            this.hide();
        });

        abortRect.setOnMouseClicked(abort -> {
            this.hide();
        });
    }
}