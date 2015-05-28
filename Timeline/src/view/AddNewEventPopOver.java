package view;
import controller.DAO;
import controller.MainWindowController;
import controller.SQLDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import model.DayTimeline;
import model.EventNT;
import model.EventTime;
import model.Timeline;
import org.controlsfx.control.PopOver;

import java.time.Instant;
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
    private RadioButton eventNT = new RadioButton();
    private RadioButton eventWT = new RadioButton();
    private ToggleGroup tg = new ToggleGroup();
    private TextField titleField = new TextField();
    private TextField descriptionField = new TextField();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private DayTimeline timelineToAddEvent;
    // private DayTimeline oldTimeline;
    LocalDate lStart;
    LocalDate lEnd;
    // private Rectangle saveRect = new Rectangle(25,25);
// private Rectangle abortRect = new Rectangle(25,25);
    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");
    /**
     * This pop-over is for adding a new event to a timeline.
     * @throws Exception
     */
    public AddNewEventPopOver(MainWindowController mwc) throws Exception{
        SQLDAO sqldao = new SQLDAO();
        LinkedList<DayTimeline> tmlns = sqldao.getAllTimelines();
        for (DayTimeline t : tmlns) {
            myComboBox.getItems().addAll(t.getTitle());
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
                                            setStyle("-fx-background-color: #FF3E31;");
                                        }
                                        if (item.isAfter(lEnd)) {
                                            setDisable(true);
                                            setStyle("-fx-background-color: #FF3E31;");
                                        }
                                    }
                                };
                            }
                        };
                startDatePicker.setDayCellFactory(dayCellFactory);
                endDatePicker.setDayCellFactory(dayCellFactory);
            }
        });
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
                                    setStyle("-fx-background-color: #FF3E31;");
                                }
                                if(item.isAfter(lEnd)){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FF3E31;");
                                }
                            }
                        };
                    }
                };

        myComboBox.getSelectionModel().selectFirst();

        /*
        System.out.println(myComboBox.getSelectionModel().getSelectedItem().toString());
        Saves the changes from the GUI in the event
        */
        saveBtn.setOnMouseClicked(saveChanges -> {
            if(tg.getSelectedToggle() == eventNT) {
                LocalDate startDatePickerValue = startDatePicker.getValue();
                endDatePicker.hide();
                GregorianCalendar gregorianStart = new GregorianCalendar();

                Date d = Date.from(startDatePickerValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
                gregorianStart.setTime(d);
                EventNT ent = new EventNT(titleField.getText(), descriptionField.getText(), gregorianStart);

                NewDayTimelineGrid newDayTimelineGrid = new NewDayTimelineGrid(timelineToAddEvent);
                newDayTimelineGrid.redrawEventsAddEvent(ent);
                mwc.redrawOneTimeline(newDayTimelineGrid);
                this.hide();
            }
            else if(tg.getSelectedToggle() == eventWT) {
                LocalDate start = startDatePicker.getValue();
                LocalDate end = endDatePicker.getValue();

                GregorianCalendar gregorianStart = new GregorianCalendar();
                GregorianCalendar gregorianEnd = new GregorianCalendar();

                Date dStart = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date dEnd = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
                gregorianStart.setTime(dStart);
                gregorianEnd.setTime(dEnd);

                EventTime ewt = new EventTime(titleField.getText(), descriptionField.getText(), gregorianStart, gregorianEnd);
                NewDayTimelineGrid newDayTimelineGrid = new NewDayTimelineGrid(timelineToAddEvent);
                newDayTimelineGrid.redrawEventsAddEvent(ewt);
                mwc.redrawOneTimeline(newDayTimelineGrid);
                this.hide();
            }
        });
        cancelBtn.setOnMouseClicked(abort -> {
            this.hide();
        });
        hb.getChildren().addAll(saveBtn, cancelBtn);
        hbr.getChildren().addAll(eventNT, eventWT);
        vb.getChildren().addAll(myComboBox, hbr, titleField, descriptionField, startDatePicker, endDatePicker, hb);
        this.setContentNode(vb);
        eventNT.setToggleGroup(tg);
        eventWT.setToggleGroup(tg);
    }
}