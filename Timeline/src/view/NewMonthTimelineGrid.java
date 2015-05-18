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
public class NewMonthTimelineGrid  extends ScrollPane{

        private GridPane gp = new GridPane();
        private MonthTimeline monthTimeline;
        private AnchorPane myAnchorPane;
        int daysOfTimeline;
        int daysBetween;

        Label title;
        public String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
                , "October", "November", "December"};
        private double aDouble;

        public NewMonthTimelineGrid(Timeline timeline) {
            this.setPrefHeight(300);
            this.setVbarPolicy(ScrollBarPolicy.NEVER);
            title = new Label();

            title.setMinHeight(40);
            VBox myBox = new VBox();
            myBox.setAlignment(Pos.CENTER);

            RowConstraints r = new RowConstraints(50, 50, 100);

            monthTimeline = (MonthTimeline) timeline;
            title.setText(monthTimeline.getTitle());
            drawMonths();

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

        private void drawEvents() {
          if (monthTimeline != null) {
                monthTimeline.addEventTime(new EventTime("Dingdong", "dongerino", new GregorianCalendar(2015, 3, 6), new GregorianCalendar(2015, 12, 20)));
                for (EventTime et : monthTimeline.getEventTimes()) {
                    Pane p = et.getPane();
                    p.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                    p.setPrefWidth(Double.MAX_VALUE);
                    p.maxWidth(Double.MAX_VALUE);
                    p.minHeight(USE_COMPUTED_SIZE);
                    p.minWidth(USE_COMPUTED_SIZE);

                    gp.add(p, 3, 1, 9, 1);
                }
            }
        }

        public MonthTimeline getMonthTimeline() {
            return monthTimeline;
        }

        public void setMonthTimeline(MonthTimeline monthTimeline) {
            this.monthTimeline = monthTimeline;
        }
    }

