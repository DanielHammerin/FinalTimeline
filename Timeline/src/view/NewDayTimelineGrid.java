
        package view;

        import javafx.geometry.HPos;
        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.control.Label;
        import javafx.scene.control.ScrollPane;
        import javafx.scene.effect.DropShadow;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Font;
        import model.*;

        import java.text.SimpleDateFormat;
        import java.time.LocalDate;
        import java.time.temporal.ChronoUnit;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;

/**
 * Created by Alexander on 18/05/2015.
 */
public class NewDayTimelineGrid extends ScrollPane{

    private GridPane gp = new GridPane();
    private DayTimeline dayTimeline;
    private AnchorPane myAnchorPane;
    /* Simple date formats for the dates.*/
    SimpleDateFormat yearF = new SimpleDateFormat("yyyyyyy");
    SimpleDateFormat monthF = new SimpleDateFormat("MM");
    SimpleDateFormat dayF = new SimpleDateFormat("dd");
    public String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
            , "October", "November", "December"};

    Label title;
    public String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
            , "October", "November", "December"};
    private double aDouble;

    public NewDayTimelineGrid(Timeline timeline) {
        this.setPrefHeight(300);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        title = new Label();
        Font titleSize = new Font(20);
        title.setFont(titleSize);

        title.setMinHeight(40);
        VBox myBox = new VBox();
        myBox.setAlignment(Pos.CENTER);


        dayTimeline = (DayTimeline) timeline;
        title.setText(dayTimeline.getTitle());
        drawDays();

        gp.setGridLinesVisible(false);
        gp.prefWidthProperty().bind(this.widthProperty());
        gp.prefHeightProperty().bind(this.heightProperty());

        myAnchorPane = new AnchorPane();
        myAnchorPane.setPrefWidth(this.getWidth()-this.getWidth()*0.1);
        myAnchorPane.getChildren().add(gp);

        myBox.getChildren().add(title);
        myBox.getChildren().add(myAnchorPane);
        this.setContent(myBox);
    }


    private void drawDays() {

        int compareDay = dayTimeline.getStartDate().get(Calendar.DAY_OF_MONTH);
        int currentMonth = dayTimeline.getStartDate().get(Calendar.MONTH);
        int currentYear = dayTimeline.getStartYear();
        int daysSinceLastMonth = 0;
        int daysPassedSinceYear = 0;
        int daysPassed = 0;
        int fontSizeYears = 16;
        ColumnConstraints c = new ColumnConstraints(50,50,Double.MAX_VALUE);
        c.setPercentWidth(100);
        c.setHalignment(HPos.CENTER);
        RowConstraints r1 = new RowConstraints(50, 50, 100);
        gp.getRowConstraints().add(r1);
        RowConstraints r2 = new RowConstraints(30, 35, 100);
        gp.getRowConstraints().add(r2);
        RowConstraints r3 = new RowConstraints(30, 35, 100);
        gp.getRowConstraints().add(2, r3);


        GregorianCalendar startCopy = (GregorianCalendar) dayTimeline.getStartDate().clone();
        GregorianCalendar end = (GregorianCalendar) dayTimeline.getEndDate().clone();

        while (startCopy.compareTo(dayTimeline.getEndDate()) <= 0)
        {
            gp.getColumnConstraints().addAll(c);


            int currentDay = startCopy.get(Calendar.DAY_OF_MONTH);
                /* New month*/
            if (compareDay > currentDay)
            {
                System.out.println("compareDay = " + compareDay + "    currentDay = " + currentDay);
                Label month = new Label(months[currentMonth % 12]);
                month.setEffect(new DropShadow(50,Color.BLACK));
                gp.add(month, daysPassed - daysSinceLastMonth, 1, daysSinceLastMonth, 1);
                daysSinceLastMonth = 0;
                currentMonth = startCopy.get(Calendar.MONTH);
                System.out.println(currentMonth);
                    /* New year */
                if (currentMonth == 0)
                {
                        /* Minus one because the year have passed and it needs to subtract one year*/
                    Label year = new Label(startCopy.get(Calendar.YEAR) - 1 + "");
                    Font yearSize = new Font(fontSizeYears);
                    year.setFont(yearSize);
                    gp.add(year, daysPassed - daysPassedSinceYear, 0, daysPassedSinceYear, 1);
                    daysPassedSinceYear = 0;
                }
            }

            Label day = new Label(startCopy.get(Calendar.DAY_OF_MONTH) + "");
            startCopy.add(5,1);
            gp.add(day, daysPassed, 2);
            daysPassed++;
            daysSinceLastMonth++;
            daysPassedSinceYear++;
            compareDay = currentDay;
        }
        daysPassed--;
            /*Adding the last month*/
        Label month = new Label(months[currentMonth % 12]);
        gp.add(month, daysPassed - daysSinceLastMonth, 1, daysSinceLastMonth, 1);
            /* Adding the last year*/
        if (daysPassedSinceYear != 0)
        {
            currentYear = startCopy.get(Calendar.YEAR);
            Label year = new Label(startCopy.get(Calendar.YEAR) + "");
            Font yearSize = new Font(fontSizeYears);
            year.setFont(yearSize);
            gp.add(year, daysPassed - daysPassedSinceYear, 0, daysPassedSinceYear, 1);
        }
        System.out.println(daysPassed - daysSinceLastMonth);
//            drawEvents();


    }

//        private void drawEvents() {
//            if (dayTimeline != null) {
//                dayTimeline.addEventTime(new EventTime("Dingdong", "dongerino", new GregorianCalendar(2015, 12, 6), new GregorianCalendar(2015, 12, 20)));
//
//                for (EventTime et : dayTimeline.getEventTimes()) {
//                    Date d = et.getFinishTime().getTime();
//                    Date d1 = et.getStartTime().getTime();
//                    daysBetween = daysBetween(d, d1);
//                    Pane p = et.getPane();
//                    p.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
//                    p.setPrefWidth(Double.MAX_VALUE);
//                    p.maxWidth(Double.MAX_VALUE);
//                    p.minHeight(USE_COMPUTED_SIZE);
//                    p.minWidth(USE_COMPUTED_SIZE);
//                    gp.add(p, et.getStartTime().get(Calendar.DAY_OF_MONTH), 1, daysBetween, 1);
//
//                }
//            }
//        }

    public DayTimeline getDayTimeline() {
        return dayTimeline;
    }

    public void setDayTimeline(DayTimeline dayTimeline) {
        this.dayTimeline = dayTimeline;
    }





}


