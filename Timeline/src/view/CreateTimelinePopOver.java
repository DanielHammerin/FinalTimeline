//package view;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.*;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//import controller.DAO;
//import model.DayTimeline;
//import model.MonthTimeline;
//import model.Timeline;
//import model.YearTimeline;
//import org.controlsfx.control.PopOver;
//
//import java.awt.*;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//
///**
// * Created by Alexander on 29/04/2015.
// * This popover defines the structure of the controls which the user can use to create a new timeline
// */
//public class CreateTimelinePopOver extends PopOver{
//
//    private VBox vbox;
//    private TextField searchField = new TextField();
//    private DAO dao = new DAO();
//    private TextField titleTxt = new TextField();
//    private Button cancelBtn = new Button();
//    private RadioButton annualBtn = new RadioButton();
//    private RadioButton monthlyBtn = new RadioButton();
//    private RadioButton dailyBtn = new RadioButton();
//    private Button addBtn = new Button();
//    private TextField descriptionTxt = new TextField();
//    private DatePicker endDatePicker = new DatePicker();
//    private DatePicker startDatePicker = new DatePicker();
//    private HBox radioBtnBox = new HBox();
//    private ToggleGroup tg = new ToggleGroup();
//    private javafx.scene.shape.Rectangle rect = new Rectangle();
//
//    /**
//     * Constructor of the pop-over which handles the creation of a new timeline
//     * @param vBoxMain The VBox where the graphical timeline should be added to
//     */
//    public CreateTimelinePopOver(VBox vBoxMain, ArrayList<Timeline> timelines){
//        this.setHideOnEscape(true);
//        this.setDetachable(false);
//        this.hide();
//        this.setWidth(500);
//        this.setHeight(300);
//
//        rect.setWidth(50);
//        rect.setHeight(50);
//        rect.setFill(Color.YELLOWGREEN);
//
//        //Event which initializes the creation of a TimelineGrid and the Timeline
//        rect.setOnMouseClicked(CreateTimeline -> {
//            LocalDate localStart = startDatePicker.getValue();
//            LocalDate localEnd = endDatePicker.getValue();
//
//            GregorianCalendar gregorianStart = new GregorianCalendar();
//            gregorianStart.set(localStart.getYear(), localStart.getMonthValue(), localStart.getDayOfMonth());
//
//            GregorianCalendar gregorianEnd = new GregorianCalendar();
//            gregorianEnd.set(localEnd.getYear(), localEnd.getMonthValue(), localEnd.getDayOfMonth());
//
//            System.out.println(gregorianStart.getTime());
//            System.out.println(gregorianEnd.getTime());
//
//            //This if-statement handles the choice of an annual,monthly or daily timeline
//            if(tg.getSelectedToggle() == annualBtn){
//                YearTimelineGrid y = new YearTimelineGrid(new YearTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));
//                try {
//                    dao.saveV2(y.getYearTimeline());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                vBoxMain.getChildren().add(y.getTimeLineBlock());
//                timelines.add(y.getYearTimeline());
//            }else if(tg.getSelectedToggle() == monthlyBtn){
//                MonthTimelineGrid m = new MonthTimelineGrid(new MonthTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));
//                try {
//                    dao.saveV2(m.getMonthTimeline());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                vBoxMain.getChildren().add(m.getTimeLineBlock());
//                timelines.add(m.getMonthTimeline());
//            }else{
//                DayTimelineGrid d = new DayTimelineGrid(new DayTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd));
//                try {
//                    dao.saveV2(d.getDayTimeline());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                vBoxMain.getChildren().add(d.getTimeLineBlock());
//                timelines.add(d.getDayTimeline());
//            }
//            this.hide();
//        });
//
//        vbox = new VBox();
//        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
//        vbox.getChildren().add(titleTxt);
//        vbox.getChildren().add(startDatePicker);
//        vbox.getChildren().add(endDatePicker);
//        vbox.getChildren().add(descriptionTxt);
//        vbox.getChildren().add(radioBtnBox);
//        vbox.getChildren().add(rect);
//
//        radioBtnBox.getChildren().add(dailyBtn);
//        radioBtnBox.getChildren().add(monthlyBtn);
//        radioBtnBox.getChildren().add(annualBtn);
//
//        dailyBtn.setText("Daily");
//        monthlyBtn.setText("Monthly");
//        annualBtn.setText("Annual");
//
//        dailyBtn.setToggleGroup(tg);
//        monthlyBtn.setToggleGroup(tg);
//        annualBtn.setToggleGroup(tg);
//
//        this.setContentNode(vbox);
//    }
//
//}
