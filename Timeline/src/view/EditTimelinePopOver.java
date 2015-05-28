package view;
import controller.MainWindowController;
import controller.SQLDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import model.*;
import org.controlsfx.control.PopOver;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
/**
 * Created by Alexander on 08/05/2015.
 */
public class EditTimelinePopOver extends PopOver{
    private VBox vbox = new VBox();
    private ComboBox  myComboBox = new ComboBox();;
    private TextField titleTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();
    private DatePicker startDatePicker = new DatePicker();
    private DatePicker endDatePicker = new DatePicker();
    private Rectangle addBtn = new Rectangle();

    LocalDate lStart;
    LocalDate lEnd;

    DayTimeline selectedTimeline = new DayTimeline();

    public EditTimelinePopOver(MainWindowController mwc) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        SQLDAO sqldao = new SQLDAO();
        this.setHideOnEscape(true);
        this.autoHideProperty().setValue(true);
        this.setWidth(400);
        this.setHeight(200);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);

        LinkedList<DayTimeline> allDayTimelines = sqldao.getAllTimelines();
        for(Timeline t : allDayTimelines){
            myComboBox.getItems().addAll(t.getTitle());
        }

        myComboBox.getSelectionModel().selectFirst();

        try {
            selectedTimeline = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        myComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    selectedTimeline = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                titleTextField.setText(selectedTimeline.getTitle());
                descriptionTextArea.setText(selectedTimeline.getDescription());

                int monthStart = selectedTimeline.getStartDate().get((Calendar.MONTH)) % 12;
                lStart = LocalDate.of(selectedTimeline.getStartDate().get(Calendar.YEAR), monthStart, selectedTimeline.getStartDate().get(Calendar.DAY_OF_MONTH));

                int monthEnd = selectedTimeline.getEndDate().get((Calendar.MONTH)) % 12;
                lEnd = LocalDate.of(selectedTimeline.getEndDate().get(Calendar.YEAR), monthEnd, selectedTimeline.getEndDate().get(Calendar.DAY_OF_MONTH));
                startDatePicker.setValue(lStart);
                endDatePicker.setValue(lEnd);
                System.out.println("In listener");
            }
        });


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

            String timelineTitle = (String) myComboBox.getSelectionModel().getSelectedItem();
            DayTimeline dayTimeline = new DayTimeline(timelineTitle, descriptionTextArea.getText(), gregorianStart, gregorianEnd);

            try {
                DayTimeline timelineToDelete = sqldao.getTimeline(myComboBox.getSelectionModel().getSelectedItem().toString());
                sqldao.deleteTimeline(timelineToDelete.getTitle());
                sqldao.saveTimeline(dayTimeline);
                mwc.redrawOneTimeline(new NewDayTimelineGrid(dayTimeline));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.hide();
        });
        vbox.getChildren().addAll(myComboBox, titleTextField,descriptionTextArea,startDatePicker,endDatePicker,addBtn);
        this.setContentNode(vbox);
    }
}