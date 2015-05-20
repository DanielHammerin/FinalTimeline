package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 13/05/2015.
 */
public class NewTimelineGrid extends ScrollPane {

    private GridPane gp = new GridPane();


    private DayTimeline dayTimeline;
    private MonthTimeline monthTimeline;
    private YearTimeline yearTimeline;
    private AnchorPane myAnchorPane;
    int daysOfTimeline;
    int daysBetween;

    Label title;
    public String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
            , "October", "November", "December"};
    private double aDouble;

    public NewTimelineGrid(Timeline timeline) {
        this.setPrefHeight(300);
        title = new Label();

        title.setMinHeight(40);
        VBox myBox = new VBox();
        myBox.setAlignment(Pos.CENTER);

        RowConstraints r = new RowConstraints(50, 50, 100);

        if (timeline.isDayTimeline()) {
            dayTimeline = (DayTimeline) timeline;
            title.setText(dayTimeline.getTitle());
            drawDays();
        } else if (timeline.isMonthTimeline()) {
            monthTimeline = (MonthTimeline) timeline;
            title.setText(monthTimeline.getTitle());
            drawMonths();
        } else {
            yearTimeline = (YearTimeline) timeline;
            title.setText(yearTimeline.getTitle());
            drawYears();
        }

        gp.getRowConstraints().add(r);
        gp.setGridLinesVisible(true);
        gp.prefWidthProperty().bind(this.widthProperty());
        gp.prefHeightProperty().bind(this.heightProperty());

        myAnchorPane = new AnchorPane();
        myAnchorPane.setPrefWidth(this.getWidth()-this.getWidth()*0.1);
        myAnchorPane.getChildren().add(gp);

        myBox.getChildren().add(title);
        myBox.getChildren().add(myAnchorPane);
        this.setContent(myBox);
    }

    public int daysBetween(Date d1, Date d2) {
        return (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24  ));
    }


    private void drawDays() {
        ColumnConstraints c = new ColumnConstraints(10, 50, Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHgrow(Priority.ALWAYS);
        //c.setHalignment(HPos.CENTER);

        Date d = dayTimeline.getEndDate().getTime();
        Date d1 = dayTimeline.getStartDate().getTime();
        daysOfTimeline = daysBetween(d, d1);
        for (int i = 1; i <= daysOfTimeline; i++) {
            gp.getColumnConstraints().addAll(c);
            Label l = new Label("" + i);
            gp.add(l, i - 1, 0);
        }
        drawEvents();
    }

    private void drawMonths() {
        ColumnConstraints c = new ColumnConstraints(50, 50, Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHalignment(HPos.CENTER);

        LocalDate startDate = LocalDate.of(monthTimeline.getStartDate().get(Calendar.YEAR), monthTimeline.getStartDate().get(Calendar.MONTH), monthTimeline.getStartDate().get(Calendar.DAY_OF_MONTH));
        LocalDate endDate = LocalDate.of(monthTimeline.getEndDate().get(Calendar.YEAR), monthTimeline.getEndDate().get(Calendar.MONTH), monthTimeline.getEndDate().get(Calendar.DAY_OF_MONTH));

        long months = ChronoUnit.MONTHS.between(startDate, endDate);

        for (int i = 1; i <= months; i++) {
            gp.getColumnConstraints().addAll(c);

            Label l = new Label("" + monthArray[i % 12]);
            gp.add(l, i - 1, 0);
        }
        drawEvents();
    }

    private void drawYears() {
        ColumnConstraints c = new ColumnConstraints(50, 50, Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHalignment(HPos.CENTER);
        int diff = yearTimeline.getEndYear() - yearTimeline.getStartYear();

        for (int i = 1; i <= diff; i++) {
            gp.getColumnConstraints().addAll(c);
            int temp = yearTimeline.getStartYear() + i;
            Label l = new Label("" + temp);
            gp.add(l, i - 1, 0);
        }
        drawEvents();
    }

    private void drawEvents() {
        if (dayTimeline != null) {


            for (EventTime et : dayTimeline.getEventTimes()) {
                Date d = et.getFinishTime().getTime();
                Date d1 = et.getStartTime().getTime();
                daysBetween = daysBetween(d, d1);
                Pane p = et.getPane();
                p.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                p.setPrefWidth(Double.MAX_VALUE);
                p.maxWidth(Double.MAX_VALUE);
                p.minHeight(USE_COMPUTED_SIZE);
                p.minWidth(USE_COMPUTED_SIZE);
                gp.add(p, et.getStartTime().get(Calendar.DAY_OF_MONTH), 1, daysBetween, 1);

            }
        } else if (monthTimeline != null) {

            for (EventTime et : monthTimeline.getEventTimes()) {
                Pane p = et.getPane();
                p.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                p.setPrefWidth(Double.MAX_VALUE);
                p.maxWidth(Double.MAX_VALUE);
                p.minHeight(USE_COMPUTED_SIZE);
                p.minWidth(USE_COMPUTED_SIZE);

                gp.add(p, 3, 1, 9, 1);
            }
        } else if (yearTimeline != null) {

            for (EventTime et : yearTimeline.getEventTimes()) {
                Pane p = et.getPane();
                p.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                p.setPrefWidth(Double.MAX_VALUE);
                p.maxWidth(Double.MAX_VALUE);
                p.minHeight(USE_COMPUTED_SIZE);
                p.minWidth(USE_COMPUTED_SIZE);

                gp.add(p, 1, 1, 11, 1);
            }
        }
    }

    public DayTimeline getDayTimeline() {
        return dayTimeline;
    }

    public void setDayTimeline(DayTimeline dayTimeline) {
        this.dayTimeline = dayTimeline;
    }

    public MonthTimeline getMonthTimeline() {
        return monthTimeline;
    }

    public void setMonthTimeline(MonthTimeline monthTimeline) {
        this.monthTimeline = monthTimeline;
    }

    public YearTimeline getYearTimeline() {
        return yearTimeline;
    }

    public void setYearTimeline(YearTimeline yearTimeline) {
        this.yearTimeline = yearTimeline;
    }

}
