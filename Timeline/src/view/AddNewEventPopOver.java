package view;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import controller.MainWindowController;
import controller.SQLDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
public class AddNewEventPopOver extends PopOver {
    private HBox hbr = new HBox();
    private HBox hb = new HBox();
    private VBox vb = new VBox();
    private SQLDAO sqldao = new SQLDAO();
    private ComboBox myComboBox = new ComboBox();
    private ToggleButton eventNT;
    private ToggleButton eventWT;
    private ToggleGroup tg = new ToggleGroup();
    private TextField titleField = new TextField();
    private TextArea descriptionField = new TextArea();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private DayTimeline timelineToAddEvent;
    private LocalDate lStart;
    private LocalDate lEnd;
    private Button saveBtn;
    private Button cancelBtn;
    /**
     * This pop-over is for adding a new event to a timeline.
     * @throws Exception
     */
    public AddNewEventPopOver(MainWindowController mwc) throws Exception{
        LinkedList<DayTimeline> tmlns = sqldao.getAllTimelines();
        myComboBox.setPrefWidth(240);
        for (DayTimeline t : tmlns) {
            myComboBox.getItems().addAll(t.getTitle());
        }

        Image create1 = new Image(getClass().getResourceAsStream("Icons/FinishEditing.png"));
        ImageView image1 = new ImageView(create1);
        Image create2 = new Image(getClass().getResourceAsStream("Icons/Cancel.png"));
        ImageView image2 = new ImageView(create2);
        image1.setFitWidth(30);
        image1.setFitHeight(30);
        image2.setFitWidth(30);
        image2.setFitHeight(30);
        saveBtn = new Button("Add event", image1);
        cancelBtn = new Button("Cancel", image2);
        startDatePicker.setPrefWidth(240.0);
        endDatePicker.setPrefWidth(240.0);
        startDatePicker.setPromptText("Event start date");
        endDatePicker.setPromptText("Event end date");

        titleField.setPrefWidth(240.0);
        descriptionField.setPrefWidth(230.0);
        descriptionField.setPrefHeight(70);
        descriptionField.setWrapText(true);
        titleField.setPromptText("Event title");
        descriptionField.setPromptText("Event description");
        eventNT = new ToggleButton("Non-Durated Event");
        eventWT = new ToggleButton("Durated Event");
        eventWT.setSelected(true);

        myComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    timelineToAddEvent = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                lStart = LocalDate.of(timelineToAddEvent.getStartDate().get(Calendar.YEAR), timelineToAddEvent.getStartDate().get(Calendar.MONTH) + 1, timelineToAddEvent.getStartDate().get(Calendar.DAY_OF_MONTH));
                lEnd = LocalDate.of(timelineToAddEvent.getEndDate().get(Calendar.YEAR), timelineToAddEvent.getEndDate().get(Calendar.MONTH) + 1, timelineToAddEvent.getEndDate().get(Calendar.DAY_OF_MONTH));
                System.out.println("In listener");
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
            }
        });
        myComboBox.getSelectionModel().selectFirst();
        timelineToAddEvent = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
        saveBtn.setOnMouseClicked(saveChanges -> {
            if (eventNT.isSelected()) {
                LocalDate startDatePickerValue = startDatePicker.getValue();
                GregorianCalendar newDateOfEvent = new GregorianCalendar();
                if(titleField.getText().isEmpty() || startDatePicker.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Insufficient information");
                    alert.setHeaderText("Error!");
                    alert.setContentText("The title and the start date of the event have to be specified");
                    alert.showAndWait();
                }
                else {
                Date d = Date.from(startDatePickerValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
                newDateOfEvent.setTime(d);
                    EventNT ent = new EventNT(titleField.getText(), descriptionField.getText(), newDateOfEvent);
                    try {
                        if (!sqldao.isThereADuplicateEvent(ent)) {
                            sqldao.saveEvent(timelineToAddEvent, ent);
                            timelineToAddEvent = sqldao.getTimelineFromEventNT(ent);
                            mwc.redrawOneTimelineAddEvent(timelineToAddEvent, ent);
                            this.hide();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Duplicated Timelines");
                            alert.setHeaderText("Error!");
                            alert.setContentText("There is already an event named '" + ent.getTitle() + "' in the database. Please choose another name.");
                            alert.showAndWait();
                        }
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
                }
            } else {
                if(titleField.getText().isEmpty() || startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Insufficient information");
                    alert.setHeaderText("Error!");
                    alert.setContentText("The title, the start date and the end date of the event have to be specified");
                    alert.showAndWait();
                }
                else {
                    LocalDate start = startDatePicker.getValue();
                    LocalDate end = endDatePicker.getValue();
                    GregorianCalendar gregorianStart = new GregorianCalendar();
                    GregorianCalendar gregorianEnd = new GregorianCalendar();
                    Date dStart = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date dEnd = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    gregorianStart.setTime(dStart);
                    gregorianEnd.setTime(dEnd);
                    EventTime ewt = new EventTime(titleField.getText(), descriptionField.getText(), gregorianStart, gregorianEnd);
                    try {
                        if (!sqldao.isThereADuplicateEvent(ewt)) {
                            sqldao.saveEvent(timelineToAddEvent, ewt);
                            timelineToAddEvent = sqldao.getTimelineFromEventTime(ewt);
                            mwc.redrawOneTimelineAddEvent(timelineToAddEvent, ewt);
                            this.hide();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Duplicated Timelines");
                            alert.setHeaderText("Error!");
                            alert.setContentText("There is already an event named '" + ewt.getTitle() + "' in the database. Please choose another name.");
                            alert.showAndWait();
                        }
                    } catch (ClassNotFoundException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Database Connection");
                        alert.setHeaderText("Error!");
                        alert.setContentText("Database connection Error");
                        alert.showAndWait();
                        e.printStackTrace();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Duplicate en");
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
                }
            }
        });
        cancelBtn.setOnMouseClicked(abort -> {
            this.hide();
        });
        hb.setSpacing(49.0);
        hbr.setSpacing(30.0);
        hb.getChildren().addAll(saveBtn, cancelBtn);
        hbr.getChildren().addAll(eventNT, eventWT);
        vb.getChildren().addAll(myComboBox, hbr, titleField, descriptionField, startDatePicker, endDatePicker, hb);
        vb.prefWidthProperty().bind(this.prefWidthProperty());
        vb.setPrefHeight(235);
        this.setContentNode(vb);
        eventNT.setToggleGroup(tg);
        eventWT.setToggleGroup(tg);
    }
}