package view;

import controller.MainWindowController;
import controller.SQLDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import controller.DAO;
import model.DayTimeline;
import model.Timeline;
import org.controlsfx.control.PopOver;

import java.awt.*;
import java.awt.TextArea;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;

/**
 * Created by Alexander on 29/04/2015.
 * This popover defines the structure of the controls which the user can use to create a new timeline
 */
public class CreateTimelinePopOver extends PopOver{

    private VBox vbox;
    private TextField searchField = new TextField();
    private DAO dao = new DAO();
    private SQLDAO sqldao = new SQLDAO();
    private TextField titleTxt = new TextField();
    private Button cancelBtn = new Button();
    private RadioButton annualBtn = new RadioButton();
    private RadioButton monthlyBtn = new RadioButton();
    private RadioButton dailyBtn = new RadioButton();
    private Button addBtn = new Button();
    private javafx.scene.control.TextArea descriptionTxt = new javafx.scene.control.TextArea();
    private DatePicker endDatePicker = new DatePicker();
    private DatePicker startDatePicker = new DatePicker();
    private HBox radioBtnBox = new HBox();
    private ToggleGroup tg = new ToggleGroup();
    private Rectangle rect = new Rectangle();

    /**
     * Constructor of the pop-over which handles the creation of a new timeline
     * @param vBoxMain The VBox where the graphical timeline should be added to
     */
    public CreateTimelinePopOver(VBox vBoxMain){
        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setWidth(500);
        this.setHeight(300);

        rect.setWidth(50);
        rect.setHeight(50);
        rect.setFill(Color.YELLOWGREEN);

        //Event which initializes the creation of a TimelineGrid and the Timeline
        rect.setOnMouseClicked(CreateTimeline -> {
            LocalDate localStart = startDatePicker.getValue();
            LocalDate localEnd = endDatePicker.getValue();

            GregorianCalendar gregorianStart = new GregorianCalendar();
            gregorianStart.set(localStart.getYear(), localStart.getMonthValue(), localStart.getDayOfMonth());

            GregorianCalendar gregorianEnd = new GregorianCalendar();
            gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue(), localEnd.getDayOfMonth());

            System.out.println(gregorianStart.getTime());
            System.out.println(gregorianEnd.getTime());

            NewDayTimelineGrid d = new NewDayTimelineGrid(new DayTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));

            if(!sqldao.isThereADuplicate(d.getDayTimeline())){
                try {
                    sqldao.saveTimeline(d.getDayTimeline());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainWindowController.allTheTimelines.add(d.getDayTimeline());
                vBoxMain.getChildren().add(d);
                this.hide();
            }

        });

        vbox = new VBox();
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        vbox.getChildren().addAll(titleTxt,startDatePicker,endDatePicker,descriptionTxt,radioBtnBox,rect);

        radioBtnBox.getChildren().addAll(dailyBtn,monthlyBtn,annualBtn);

        dailyBtn.setText("Daily");
        monthlyBtn.setText("Monthly");
        annualBtn.setText("Annual");

        dailyBtn.setToggleGroup(tg);
        monthlyBtn.setToggleGroup(tg);
        annualBtn.setToggleGroup(tg);

        this.setContentNode(vbox);
    }

}
