package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.DAO;
import model.DayTimeline;
import model.MonthTimeline;
import model.YearTimeline;
import org.controlsfx.control.PopOver;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 29/04/2015.
 */
public class CreateTimelinePopOver extends PopOver{

    private VBox vbox;
    private TextField searchField = new TextField();
    private DAO dao = new DAO();
    private TextField titleTxt = new TextField();
    private Button cancelBtn = new Button();
    private RadioButton annualBtn = new RadioButton();
    private RadioButton monthlyBtn = new RadioButton();
    private RadioButton dailyBtn = new RadioButton();
    private Button addBtn = new Button();
    private TextField descriptionTxt = new TextField();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private HBox radioBtnBox = new HBox();
    private ToggleGroup tg = new ToggleGroup();
    private javafx.scene.shape.Rectangle rect = new Rectangle();

    public CreateTimelinePopOver(VBox vBoxMain){
        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setWidth(500);
        this.setHeight(300);

        rect.setWidth(50);
        rect.setHeight(50);
        rect.setFill(Color.YELLOWGREEN);
        rect.setOnMouseClicked(CreateTimeline -> {
            LocalDate localStart = startDatePicker.getValue();
            LocalDate localEnd = endDatePicker.getValue();

            GregorianCalendar gregorianStart = new GregorianCalendar();
            gregorianStart.set(localStart.getYear(), localStart.getMonthValue(), localStart.getDayOfMonth());

            GregorianCalendar gregorianEnd = new GregorianCalendar();
            gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue(), localEnd.getDayOfMonth());

            System.out.println(gregorianStart.getTime());
            System.out.println(gregorianEnd.getTime());

            if(tg.getSelectedToggle() == annualBtn){
                YearTimelineGrid y = new YearTimelineGrid(new YearTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));
                vBoxMain.getChildren().add(y.getTimeLineBlock());
            }else if(tg.getSelectedToggle() == monthlyBtn){
                MonthTimeline m = new MonthTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
            }else{
                DayTimeline d = new DayTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
            }
            this.hide();
        });

        vbox = new VBox();
        this.setContentNode(vbox);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        vbox.getChildren().add(titleTxt);
        vbox.getChildren().add(startDatePicker);
        vbox.getChildren().add(endDatePicker);
        vbox.getChildren().add(descriptionTxt);
        vbox.getChildren().add(radioBtnBox);
        vbox.getChildren().add(rect);

        radioBtnBox.getChildren().add(dailyBtn);
        radioBtnBox.getChildren().add(monthlyBtn);
        radioBtnBox.getChildren().add(annualBtn);

        dailyBtn.setText("Daily");
        monthlyBtn.setText("Monthly");
        annualBtn.setText("Annual");


        dailyBtn.setToggleGroup(tg);
        monthlyBtn.setToggleGroup(tg);
        annualBtn.setToggleGroup(tg);

        this.setContentNode(vbox);
    }

    @FXML
    void addTimeline(ActionEvent event) throws IOException {
        LocalDate localStart = startDatePicker.getValue();
        LocalDate localEnd = endDatePicker.getValue();

        GregorianCalendar gregorianStart = new GregorianCalendar();
        gregorianStart.set(localStart.getYear(), localStart.getMonthValue(), localStart.getDayOfMonth());

        GregorianCalendar gregorianEnd = new GregorianCalendar();
        gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue(), localEnd.getDayOfMonth());

        System.out.println(gregorianStart.getTime());
        System.out.println(gregorianEnd.getTime());

        if(tg.getSelectedToggle() == annualBtn){
            YearTimelineGrid y = new YearTimelineGrid(new YearTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));
            vbox.getChildren().add(y.getTimeLineBlock());

            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }else if(tg.getSelectedToggle() == monthlyBtn){
            MonthTimeline m = new MonthTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
        }else{
            DayTimeline d = new DayTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
        }
    }
}
