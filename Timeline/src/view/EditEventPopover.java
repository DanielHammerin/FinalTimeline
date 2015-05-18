package view;

import controller.DAO;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.EventTime;
import org.controlsfx.control.PopOver;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 15/05/2015.
 */
public class EditEventPopover extends PopOver {

    private VBox vbox = new VBox();
    private TextField titleField = new TextField();
    private DAO dao = new DAO();
    private TextField descriptionField = new TextField();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private Rectangle saveRect = new Rectangle(25,25);
    private Rectangle abortRect = new Rectangle(25,25);

    /**
     * This pop-over makes it possible for the user to change an event
     * @param event
     */
    public EditEventPopover(EventTime event){
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());

        int monthStart = event.getStartTime().get((Calendar.MONTH)+1) % 12;
        LocalDate startDate = LocalDate.of(event.getStartTime().get(Calendar.YEAR), monthStart, event.getStartTime().get(Calendar.DAY_OF_MONTH));

        int monthEnd = event.getFinishTime().get((Calendar.MONTH)+1) % 12;
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
            gregorianStart.set(start.getYear(), start.getMonthValue(), start.getDayOfMonth());
            GregorianCalendar gregorianEnd = new GregorianCalendar();
            gregorianEnd.set(end.getYear(), end.getMonthValue(), end.getDayOfMonth());
            this.hide();
        });

        abortRect.setOnMouseClicked(abort -> {
            this.hide();
        });
    }
}