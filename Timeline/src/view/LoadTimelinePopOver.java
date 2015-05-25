//package view;
//
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.control.ListView;
//import javafx.scene.paint.Paint;
//import javafx.scene.shape.Rectangle;
//import model.*;
//import org.controlsfx.control.PopOver;
//import controller.DAO;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//
///**
// * Created by Alexander on 27/04/2015.
// * A pop-over which handles the timeline loading process from the database
// */
//public class LoadTimelinePopOver extends PopOver {
//    private GridPane myGridPane = new GridPane();
//    private ListView<Timeline> myListview = new ListView<Timeline>();
//    private Label messageLabel = new Label();
//    private DAO dao = new DAO();
//    private myRectangleButtons loadRect;
//    private myRectangleButtons refreshRect;
//
//    /**
//     * This is the constructor for a pop-over which handles the timeline loading process from the database
//     * @param mainVBox ThThe VBox where the graphical timeline should be added to
//     */
//    public LoadTimelinePopOver(VBox mainVBox, ArrayList<Timeline> timelines){
//
//        ColumnConstraints c1 = new ColumnConstraints();
//        ColumnConstraints c2 = new ColumnConstraints();
//        c1.setPercentWidth(50);
//        c2.setPercentWidth(50);
//
//        myGridPane.getColumnConstraints().addAll(c1,c2);
//
//        loadRect = new myRectangleButtons("Load",Color.DARKGREEN);
//        refreshRect = new myRectangleButtons("Refresh",Color.GOLD);
//
//        this.setHideOnEscape(true);
//        this.setDetachable(false);
//        this.hide();
//        this.setContentNode(myGridPane);
//        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
//        myGridPane.setPrefWidth(500);
//
//        messageLabel.setTextFill(Color.DARKRED);
//        LinkedList<Timeline> allTimlines = dao.getAllTimelines();
//        myListview.getItems().addAll(allTimlines);
//
//        //This event defines the event which should be executed when a user clicks on the load-button
//        loadRect.setOnMouseClicked(loadTimeline -> {
//            dao = new DAO();
//            Timeline dede = myListview.getSelectionModel().getSelectedItem();
//
//            if (dede.isDayTimeline() == true && dede.isMonthTimeline() == false && dede.isYearTimeline() == false) {
//                DayTimelineGrid d = new DayTimelineGrid((DayTimeline) dede);
//                mainVBox.getChildren().add(d.getTimeLineBlock());
//                timelines.add(d.getDayTimeline());
//                this.hide();
//            } else if (dede.isMonthTimeline() == true && dede.isDayTimeline() == false && dede.isYearTimeline() == false) {
//                //dao.printDatabase();
//                MonthTimelineGrid m = new MonthTimelineGrid((MonthTimeline) dede);
//                mainVBox.getChildren().add(m.getTimeLineBlock());
//                timelines.add(m.getMonthTimeline());
//                this.hide();
//            } else if (dede.isYearTimeline() == true && dede.isMonthTimeline() == false && dede.isDayTimeline() == false) {
//                YearTimelineGrid y = new YearTimelineGrid((YearTimeline) dede);
//                mainVBox.getChildren().add(y.getTimeLineBlock());
//                timelines.add(y.getYearTimeline());
//                this.hide();
//            }
//        });
//        myGridPane.add(myListview,0,0,2,1);
//        myGridPane.add(loadRect,0,1);
//        myGridPane.add(refreshRect,1,1,1,1);
//        myGridPane.add(messageLabel,0,2,2,1);
//        this.setContentNode(myGridPane);
//    }
//}
///*
//░░░░░░░░░
//░░░░▄▀▀▀▀▀█▀▄▄▄▄░░░░
//░░▄▀▒▓▒▓▓▒▓▒▒▓▒▓▀▄░░
//▄▀▒▒▓▒▓▒▒▓▒▓▒▓▓▒▒▓█░
//█▓▒▓▒▓▒▓▓▓░░░░░░▓▓█░
//█▓▓▓▓▓▒▓▒░░░░░░░░▓█░
//▓▓▓▓▓▒░░░░░░░░░░░░█░
//▓▓▓▓░░░░▄▄▄▄░░░▄█▄▀░
//░▀▄▓░░▒▀▓▓▒▒░░█▓▒▒░░
//▀▄░░░░░░░░░░░░▀▄▒▒█░
//░▀░▀░░░░░▒▒▀▄▄▒▀▒▒█░
//░░▀░░░░░░▒▄▄▒▄▄▄▒▒█░
// ░░░▀▄▄▒▒░░░░▀▀▒▒▄▀░░
//░░░░░▀█▄▒▒░░░░▒▄▀░░░
//░░░░░░░░▀▀█▄▄▄▄▀
// */