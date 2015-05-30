package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.DayTimeline;
import model.EventNT;
import model.EventTime;
import org.controlsfx.control.PopOver;
import javafx.scene.control.Alert.*;

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
    private TextArea descriptionField = new TextArea();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private Rectangle saveRect = new Rectangle(50,50);
    private Rectangle abortRect = new Rectangle(25,25);
    private String oldEventName;
    /**
     * This pop-over makes it possible for the user to change an event
     * @param event
     */
    public EditEventPopover(EventTime event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        /*
        Setting of the inital values from the current event, so the user can see what the old values of the event was
         */
        oldEventName = event.getTitle();
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());
        DayTimeline dayTimeline = sqldao.getTimelineFromEventTime(event);

        LocalDate lStart = LocalDate.of(dayTimeline.getStartDate().get(Calendar.YEAR), dayTimeline.getStartDate().get(Calendar.MONTH) + 1, dayTimeline.getStartDate().get(Calendar.DAY_OF_MONTH));
        LocalDate lEnd = LocalDate.of(dayTimeline.getEndDate().get(Calendar.YEAR), dayTimeline.getEndDate().get(Calendar.MONTH) + 1, dayTimeline.getEndDate().get(Calendar.DAY_OF_MONTH));
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(lStart)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #C1B2FF;");
                                }
                                if (item.isAfter(lEnd)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #C1B2FF;");
                                }
                            }
                        };
                    }
                };

        startDatePicker.setDayCellFactory(dayCellFactory);
        endDatePicker.setDayCellFactory(dayCellFactory);

        /* Plus one because the gregorian calendar objects stores the month value
        * as the month value plus one, that is june is stored as the month no 5.*/
        LocalDate startDate = LocalDate.of(event.getStartTime().get(Calendar.YEAR), event.getStartTime().get(Calendar.MONTH) + 1, event.getStartTime().get(Calendar.DAY_OF_MONTH));
        LocalDate endDate = LocalDate.of(event.getFinishTime().get(Calendar.YEAR), event.getFinishTime().get(Calendar.MONTH) + 1, event.getFinishTime().get(Calendar.DAY_OF_MONTH));

        startDatePicker.setValue(startDate);
        endDatePicker.setValue(endDate);


        vbox.getChildren().addAll(titleField, descriptionField, startDatePicker, endDatePicker, saveRect, abortRect);
        this.setContentNode(vbox);

        //Initialization of the button with routine of saving the changes
        saveRect.setOnMouseClicked(saveChanges -> {

            event.setTitle(titleField.getText());
            event.setDescription(descriptionField.getText());

            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();

            if(end.isBefore(start)){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Impossible dates");
                alert.setHeaderText("Error!");
                alert.setContentText("The start date of an event has to be before the end date!");
                alert.showAndWait();
            }else{
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
                    DayTimeline daytimeline = sqldao.getTimelineFromEventTime(event);
                    MainWindowController.mainWindowController.redrawOneTimelineEvent(daytimeline, event);
                }
                catch (ClassNotFoundException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (SQLException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (InstantiationException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                System.out.println("Ett exception");
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Database Error Connection");
                alert.setHeaderText("Error!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

                e.printStackTrace();
                }
                this.hide();
            }


        });

        abortRect.setOnMouseClicked(abort -> {
            this.hide();
        });
    }


    public EditEventPopover(EventNT event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        DayTimeline dayTimeline = sqldao.getTimelineFromEventNT(event);
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());

        LocalDate startDate = LocalDate.of(event.getDateOfEvent().get(Calendar.YEAR), event.getDateOfEvent().get(Calendar.MONTH) + 1, event.getDateOfEvent().get(Calendar.DAY_OF_MONTH));
        startDatePicker.setValue(startDate);

        vbox.getChildren().addAll(titleField, descriptionField, startDatePicker, saveRect, abortRect);
        this.setContentNode(vbox);

        LocalDate lStart = LocalDate.of(dayTimeline.getStartDate().get(Calendar.YEAR), dayTimeline.getStartDate().get(Calendar.MONTH) + 1, dayTimeline.getStartDate().get(Calendar.DAY_OF_MONTH));
        LocalDate lEnd = LocalDate.of(dayTimeline.getEndDate().get(Calendar.YEAR), dayTimeline.getEndDate().get(Calendar.MONTH) + 1, dayTimeline.getEndDate().get(Calendar.DAY_OF_MONTH));
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(lStart)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #C1B2FF;");
                                }
                                if (item.isAfter(lEnd)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #C1B2FF;");
                                }
                            }
                        };
                    }
                };

        startDatePicker.setDayCellFactory(dayCellFactory);
        endDatePicker.setDayCellFactory(dayCellFactory);

        //Saves the changes from the GUI in the event
        saveRect.setOnMouseClicked(saveChanges -> {
            oldEventName = event.getTitle();
            event.setTitle(titleField.getText());
            event.setDescription(descriptionField.getText());

            GregorianCalendar gregorianStart = new GregorianCalendar();
            LocalDate start = startDatePicker.getValue();
            Date tempStartDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
            gregorianStart.setTime(tempStartDate);
            event.setDateOfEvent(gregorianStart);
            try {
                sqldao.updateEventNT(oldEventName, event);
                DayTimeline daytimeline = sqldao.getTimelineFromEventNT(event);
                MainWindowController.mainWindowController.redrawOneTimelineEvent(daytimeline, event);
            }catch (ClassNotFoundException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (SQLException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (InstantiationException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database");
                    alert.showAndWait();

                    e.printStackTrace();
                }
                this.hide();
        });

        abortRect.setOnMouseClicked(abort -> {
            this.hide();
        });
    }

}