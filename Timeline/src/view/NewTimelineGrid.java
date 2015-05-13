package view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import model.DayTimeline;
import model.MonthTimeline;
import model.Timeline;
import model.YearTimeline;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 13/05/2015.
 */
public class NewTimelineGrid extends ScrollPane {

    GridPane gp = new GridPane();
    DayTimeline dayTimeline;
    MonthTimeline monthTimeline;
    YearTimeline yearTimeline;
    AnchorPane myAnchorPane;
    Label title;
    public String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
            , "October", "November", "December"};

    public NewTimelineGrid(Timeline timeline){
        this.setPrefHeight(300);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        title = new Label();

        title.setMinHeight(40);
        VBox myBox = new VBox();
        myBox.setAlignment(Pos.CENTER);

        RowConstraints r = new RowConstraints(50,50,100);

        if(timeline.isDayTimeline()){
            dayTimeline = (DayTimeline)timeline;
            title.setText(dayTimeline.getTitle());
            drawDays();
        }else if(timeline.isMonthTimeline()){
            monthTimeline = (MonthTimeline)timeline;
            title.setText(monthTimeline.getTitle());
            drawMonths();
        }else{
            yearTimeline = (YearTimeline)timeline;
            title.setText(yearTimeline.getTitle());
            drawYears();
        }

        gp.getRowConstraints().add(r);
        gp.setGridLinesVisible(true);
        gp.prefWidthProperty().bind(this.widthProperty());
        gp.prefHeightProperty().bind(this.heightProperty());

        myAnchorPane= new AnchorPane();
        myAnchorPane.prefHeightProperty().bind(this.heightProperty());
        myAnchorPane.prefWidthProperty().bind(this.widthProperty());
        myAnchorPane.getChildren().add(gp);

        myBox.getChildren().add(title);
        myBox.getChildren().add(myAnchorPane);
        this.setContent(myBox);
    }

    public int daysBetween(Date d1, Date d2)
    {
        return (int)( (d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
    }


    private void drawDays(){
        ColumnConstraints c = new ColumnConstraints(50,50,Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHalignment(HPos.CENTER);

        Date d = dayTimeline.getEndDate().getTime();
        Date d1 = dayTimeline.getStartDate().getTime();
        int daysBetween= daysBetween(d,d1);
        for(int i =1;i<=daysBetween;i++){
            gp.getColumnConstraints().addAll(c);
            Label l = new Label(""+i);
            gp.add(l,i-1,0);
        }
    }

    public void drawMonths(){
        ColumnConstraints c = new ColumnConstraints(50,50,Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHalignment(HPos.CENTER);

        LocalDate startDate = LocalDate.of(monthTimeline.getStartDate().get(Calendar.YEAR),monthTimeline.getStartDate().get(Calendar.MONTH),monthTimeline.getStartDate().get(Calendar.DAY_OF_MONTH));
        LocalDate endDate = LocalDate.of(monthTimeline.getEndDate().get(Calendar.YEAR), monthTimeline.getEndDate().get(Calendar.MONTH), monthTimeline.getEndDate().get(Calendar.DAY_OF_MONTH));

        long months = ChronoUnit.MONTHS.between(startDate, endDate);

        for(int i =1;i<=months;i++){
            gp.getColumnConstraints().addAll(c);
            Label l = new Label(""+monthArray[i % 12]);
            gp.add(l,i-1,0);
        }
    }

    private void drawYears(){
        ColumnConstraints c = new ColumnConstraints(50,50,Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHalignment(HPos.CENTER);
        int diff = yearTimeline.getEndYear()-yearTimeline.getStartYear();

        for(int i =1;i<=diff;i++){
            gp.getColumnConstraints().addAll(c);
            int temp = yearTimeline.getStartYear()+i;
            Label l = new Label(""+temp);
            gp.add(l,i-1,0);
        }

    }

}
