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
 * Created by Alexander on 18/05/2015.
 */
public class NewYearTimelineGrid extends ScrollPane {


        private GridPane gp = new GridPane();
        private YearTimeline yearTimeline;
        private AnchorPane myAnchorPane;
        int daysOfTimeline;
        int daysBetween;

        Label title;
        public String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
                , "October", "November", "December"};
        private double aDouble;

        public NewYearTimelineGrid(Timeline timeline) {
            this.setPrefHeight(300);
            this.setVbarPolicy(ScrollBarPolicy.NEVER);
            title = new Label();

            title.setMinHeight(40);
            VBox myBox = new VBox();
            myBox.setAlignment(Pos.CENTER);

            RowConstraints r = new RowConstraints(50, 50, 100);

            yearTimeline = (YearTimeline) timeline;
                title.setText(yearTimeline.getTitle());
                drawYears();


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
            if (yearTimeline != null) {
                yearTimeline.addEventTime(new EventTime("Dingdong", "dongerino", new GregorianCalendar(2015, 3, 6), new GregorianCalendar(2026, 12, 20)));
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

        public YearTimeline getYearTimeline() {
            return yearTimeline;
        }

        public void setYearTimeline(YearTimeline yearTimeline) {
            this.yearTimeline = yearTimeline;
        }

    }
