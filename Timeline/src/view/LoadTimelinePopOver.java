package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.*;
import org.controlsfx.control.PopOver;
import controller.DAO;

import java.util.ArrayList;

/**
 * Created by Alexander on 27/04/2015.
 * A pop-over which handles the timeline loading process from the database
 */
public class LoadTimelinePopOver extends PopOver {
    private VBox vbox = new VBox();
    private TextField searchField = new TextField();
    private Label messageLabel = new Label();
    private DAO dao;
    Button loadBtn = new Button();

    /**
     * This is the constructor for a pop-over which handles the timeline loading process from the database
     * @param mainVBox ThThe VBox where the graphical timeline should be added to
     */
    public LoadTimelinePopOver(VBox mainVBox, ArrayList<Timeline> timelines){
        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setWidth(400);
        this.setHeight(200);
        this.setContentNode(vbox);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);

        messageLabel.setTextFill(Color.DARKRED);

        loadBtn.setText("Load Timeline");
        loadBtn.prefWidthProperty().bind(vbox.widthProperty());


        //This event defines the event which should be executed when a user clicks on the load-button
        loadBtn.setOnMouseClicked(loadTimeline -> {
            dao = new DAO();
            //This if-statement checks if the requested timeline is in the database
            try {
                if (dao.lookUp(searchField.getText()) == true) {
                    System.out.println(searchField.getText());
                    //If so it'll load the timeline in a general timeline object
                    //Regarding the boolean it'll cast the loaded timeline in a yearly, monthly or daily timeline
                    Timeline t = dao.getTimeline(searchField.getText());
                    if (t.isDayTimeline() == true && t.isMonthTimeline() == false && t.isYearTimeline() == false) {
                        DayTimelineGrid d = new DayTimelineGrid((DayTimeline) t);
                        mainVBox.getChildren().add(d.getTimeLineBlock());
                        timelines.add(d.getDayTimeline());
                        this.hide();
                    } else if (t.isMonthTimeline() == true && t.isDayTimeline() == false && t.isYearTimeline() == false) {
                        //dao.printDatabase();
                        MonthTimelineGrid m = new MonthTimelineGrid((MonthTimeline) t);
                        mainVBox.getChildren().add(m.getTimeLineBlock());
                        timelines.add(m.getMonthTimeline());
                        this.hide();
                    } else if (t.isYearTimeline() == true && t.isMonthTimeline() == false && t.isDayTimeline() == false) {
                        YearTimelineGrid y = new YearTimelineGrid((YearTimeline) t);
                        mainVBox.getChildren().add(y.getTimeLineBlock());
                        timelines.add(y.getYearTimeline());
                        this.hide();
                    }
                } else {
                    System.out.print("Timeline is not there");
                    messageLabel.setText("The timline " + searchField.getText() + " doesn't exist in the database");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vbox.getChildren().add(searchField);
        vbox.getChildren().add(loadBtn);
        vbox.getChildren().add(messageLabel);
        this.setContentNode(vbox);
    }
}
/*
░░░░░░░░░
░░░░▄▀▀▀▀▀█▀▄▄▄▄░░░░
░░▄▀▒▓▒▓▓▒▓▒▒▓▒▓▀▄░░
▄▀▒▒▓▒▓▒▒▓▒▓▒▓▓▒▒▓█░
█▓▒▓▒▓▒▓▓▓░░░░░░▓▓█░
█▓▓▓▓▓▒▓▒░░░░░░░░▓█░
▓▓▓▓▓▒░░░░░░░░░░░░█░
▓▓▓▓░░░░▄▄▄▄░░░▄█▄▀░
░▀▄▓░░▒▀▓▓▒▒░░█▓▒▒░░
▀▄░░░░░░░░░░░░▀▄▒▒█░
░▀░▀░░░░░▒▒▀▄▄▒▀▒▒█░
░░▀░░░░░░▒▄▄▒▄▄▄▒▒█░
 ░░░▀▄▄▒▒░░░░▀▀▒▒▄▀░░
░░░░░▀█▄▒▒░░░░▒▄▀░░░
░░░░░░░░▀▀█▄▄▄▄▀
 */