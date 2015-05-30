package view;
import controller.MainWindowController;
import controller.SQLDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
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

    private HBox hb = new HBox();
    private VBox vb = new VBox();
    private SQLDAO sqldao = new SQLDAO();
    private ComboBox myComboBox = new ComboBox();
    private TextField titleField = new TextField();
    private TextArea descriptionField = new TextArea();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private DayTimeline timelineToAddEvent;
    LocalDate lStart;
    LocalDate lEnd;
    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");
    /**
     * This pop-over is for adding a new event to a timeline.
     * @throws Exception
     */
    public AddNewEventPopOver(MainWindowController mwc) throws Exception{
        LinkedList<DayTimeline> allTimelines = sqldao.getAllTimelines();
        for (DayTimeline t : allTimelines) {
            myComboBox.getItems().add(t.getTitle());
        }

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
            if(endDatePicker.getValue() == null) {
                LocalDate startDatePickerValue = startDatePicker.getValue();
                GregorianCalendar newDateOfEvent = new GregorianCalendar();

                Date d = Date.from(startDatePickerValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
                newDateOfEvent.setTime(d);
                EventNT ent = new EventNT(titleField.getText(), descriptionField.getText(), newDateOfEvent);

                try {
                    sqldao.saveEvent(timelineToAddEvent,ent);
                    timelineToAddEvent = sqldao.getTimelineFromEventNT(ent);
                    mwc.redrawOneTimelineEvent(timelineToAddEvent, ent);
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
            }
            else{
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
                    sqldao.saveEvent(timelineToAddEvent,ewt);
                    timelineToAddEvent = sqldao.getTimelineFromEventTime(ewt);
                    mwc.redrawOneTimelineEvent(timelineToAddEvent, ewt);
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
            }
        });
        cancelBtn.setOnMouseClicked(abort -> {
            this.hide();
        });
        hb.getChildren().addAll(saveBtn, cancelBtn);
        vb.getChildren().addAll(myComboBox, titleField, descriptionField, startDatePicker, endDatePicker, hb);
        this.setContentNode(vb);
    }
}