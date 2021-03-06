package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private HBox hbox = new HBox();
    private TextField titleField = new TextField();
    private SQLDAO sqldao = new SQLDAO();
    private TextArea descriptionField = new TextArea();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private Button saveButton;
    private Button abortButton;
    private Button deleteButton;
    private String oldEventName;
    private EventTime currentEventTime;
    private EventNT currentEventNT;
    /**
     * This pop-over makes it possible for the user to change an event
     * @param event
     */
    public EditEventPopover(EventTime event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        currentEventTime = event;
        /*
        Setting of the inital values from the current event, so the user can see what the old values of the event was
         */
        Image edit = new Image(getClass().getResourceAsStream("Icons/FinishEditing.png"));
        ImageView image1 = new ImageView(edit);
        Image cancel = new Image(getClass().getResourceAsStream("Icons/Cancel.png"));
        ImageView image2 = new ImageView(cancel);
        Image delete = new Image(getClass().getResourceAsStream("Icons/Delete.png"));
        ImageView image3 =  new ImageView(delete);
        image1.setFitWidth(30);
        image1.setFitHeight(30);
        image2.setFitWidth(30);
        image2.setFitHeight(30);
        image3.setFitWidth(30);
        image3.setFitHeight(30);
        saveButton = new Button("", image1);
        abortButton = new Button("", image2);
        deleteButton = new Button("", image3);

        oldEventName = event.getTitle();
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());
        descriptionField.setPrefHeight(110.0);
        descriptionField.setWrapText(true);
        startDatePicker.setPrefWidth(240.0);
        endDatePicker.setPrefWidth(240.0);

        saveButton.setOnMouseEntered(event1 -> {
            saveButton.setTooltip(new Tooltip("Save editing"));
        });
        deleteButton.setOnMouseEntered(event1 -> {
            deleteButton.setTooltip(new Tooltip("Delete event"));
        });
        abortButton.setOnMouseEntered(event1 -> {
            abortButton.setTooltip(new Tooltip("Cancel"));
        });

        DayTimeline dayTimeline = event.getDayTimeline();

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

        hbox.getChildren().addAll(saveButton, deleteButton, abortButton);
        hbox.setSpacing(30.0);
        vbox.getChildren().addAll(titleField, descriptionField, startDatePicker, endDatePicker, hbox);
        vbox.setPrefWidth(200.0);
        vbox.setPrefHeight(227.0);
        this.setContentNode(vbox);

        //Initialization of the button with routine of saving the changes
        saveButton.setOnMouseClicked(saveChanges -> {
            if(titleField.getText().isEmpty() || startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Insufficient information");
                alert.setHeaderText("Error!");
                alert.setContentText("The title, the start date and the end date of the event have to be specified.");
                alert.showAndWait();
            }
            else {
                event.setTitle(titleField.getText());
                event.setDescription(descriptionField.getText());

                LocalDate start = startDatePicker.getValue();
                LocalDate end = endDatePicker.getValue();

                if (end.isBefore(start)) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Impossible dates");
                    alert.setHeaderText("Error!");
                    alert.setContentText("The start date of an event has to be before the end date!");
                    alert.showAndWait();
                } else {
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
                        DayTimeline daytimeline = event.getDayTimeline();
                        MainWindowController.mainWindowController.redrawOneTimelineAddEvent(daytimeline, event);
                    } catch (ClassNotFoundException e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error Connection");
                        alert.setHeaderText("Error!");
                        alert.setContentText("There was an error trying to connect to the database.");
                        alert.showAndWait();

                        e.printStackTrace();
                    } catch (SQLException e) {

                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error Connection");
                        alert.setHeaderText("Error!");
                        alert.setContentText("There was an error trying to connect to the database.");
                        alert.showAndWait();

                        e.printStackTrace();
                    } catch (InstantiationException e) {

                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error Connection");
                        alert.setHeaderText("Error!");
                        alert.setContentText("There was an error trying to connect to the database.");
                        alert.showAndWait();

                        e.printStackTrace();
                    } catch (IllegalAccessException e) {

                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error Connection");
                        alert.setHeaderText("Error!");
                        alert.setContentText("There was an error trying to connect to the database.");
                        alert.showAndWait();

                        e.printStackTrace();
                    }
                    this.hide();
                }
            }
        });

        deleteButton.setOnMouseClicked(event1 -> {
            if (currentEventTime != null){
                try {
                    DayTimeline current = currentEventTime.getDayTimeline();
                    MainWindowController.mainWindowController.redrawOneTimelineRemoveEvent(current, currentEventTime);
                    sqldao.deleteEvent(currentEventTime);
                    this.hide();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            this.hide();
        });

        abortButton.setOnMouseClicked(abort -> {
            this.hide();
        });
    }


    public EditEventPopover(EventNT event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        currentEventNT = event;
        Image edit = new Image(getClass().getResourceAsStream("Icons/FinishEditing.png"));
        ImageView image1 = new ImageView(edit);
        Image cancel = new Image(getClass().getResourceAsStream("Icons/Cancel.png"));
        ImageView image2 = new ImageView(cancel);
        Image delete = new Image(getClass().getResourceAsStream("Icons/Delete.png"));
        ImageView image3 =  new ImageView(delete);
        image1.setFitWidth(30);
        image1.setFitHeight(30);
        image2.setFitWidth(30);
        image2.setFitHeight(30);
        image3.setFitWidth(30);
        image3.setFitHeight(30);
        saveButton = new Button("", image1);
        abortButton = new Button("", image2);
        deleteButton = new Button("", image3);

        descriptionField.setPrefHeight(110.0);
        descriptionField.setWrapText(true);
        startDatePicker.setPrefWidth(200.0);

        saveButton.setOnMouseEntered(event1 -> {
            saveButton.setTooltip(new Tooltip("Save editing"));
        });
        deleteButton.setOnMouseEntered(event1 -> {
            deleteButton.setTooltip(new Tooltip("Delete event"));
        });
        abortButton.setOnMouseEntered(event1 -> {
            abortButton.setTooltip(new Tooltip("Cancel"));
        });

        DayTimeline dayTimeline = event.getDayTimeline();
        titleField.setText(event.getTitle());
        descriptionField.setText(event.getDescription());

        LocalDate startDate = LocalDate.of(event.getDateOfEvent().get(Calendar.YEAR), event.getDateOfEvent().get(Calendar.MONTH) + 1, event.getDateOfEvent().get(Calendar.DAY_OF_MONTH));
        startDatePicker.setValue(startDate);

        hbox.getChildren().addAll(saveButton, deleteButton, abortButton);
        hbox.setSpacing(30.0);
        vbox.getChildren().addAll(titleField, descriptionField, startDatePicker, hbox);
        vbox.setPrefWidth(200.0);
        vbox.setPrefHeight(200.0);
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

        //Saves the changes from the GUI in the event
        saveButton.setOnMouseClicked(saveChanges -> {
            if(titleField.getText().isEmpty() || startDatePicker.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Insufficient information");
                alert.setHeaderText("Error!");
                alert.setContentText("The title and the start date of the event have to be specified.");
                alert.showAndWait();
            }
            else {
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
                    DayTimeline daytimeline = event.getDayTimeline();
                    MainWindowController.mainWindowController.redrawOneTimelineAddEvent(daytimeline, event);
                } catch (ClassNotFoundException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database.");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (SQLException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database.");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (InstantiationException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database.");
                    alert.showAndWait();

                    e.printStackTrace();
                } catch (IllegalAccessException e) {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error Connection");
                    alert.setHeaderText("Error!");
                    alert.setContentText("There was an error trying to connect to the database.");
                    alert.showAndWait();

                    e.printStackTrace();
                }
            }
            this.hide();
        });

        deleteButton.setOnMouseClicked(event1 -> {
            if (currentEventNT != null) {
                try {
                    DayTimeline current = currentEventNT.getDayTimeline();
                    MainWindowController.mainWindowController.redrawOneTimelineRemoveEvent(current, currentEventNT);
                    sqldao.deleteEvent(currentEventNT);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            this.hide();
        });

        abortButton.setOnMouseClicked(abort -> {
            this.hide();
        });
    }
}