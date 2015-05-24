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
import java.util.*;

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
            this.setPrefHeight(450);
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
            gp.getRowConstraints().add(3, r3);
            gp.getRowConstraints().add(4, r3);
            gp.getRowConstraints().add(5, r3);
            gp.getRowConstraints().add(6, r3);
            gp.getRowConstraints().add(7, r3);

            boolean moreThanOneMonth = false;


            GregorianCalendar startCopy = (GregorianCalendar) dayTimeline.getStartDate().clone();
            GregorianCalendar end = (GregorianCalendar) dayTimeline.getEndDate().clone();

            while (startCopy.compareTo(dayTimeline.getEndDate()) <= 0)
            {
                gp.getColumnConstraints().addAll(c);


                int currentDay = startCopy.get(Calendar.DAY_OF_MONTH);
                /* New month*/
                if (compareDay > currentDay)
                {
                    moreThanOneMonth = true;
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
            if (moreThanOneMonth) { daysPassed--; }
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
            drawEvents();

//            Pane p = new Pane();
//            p.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
//            gp.add(p,5,3,7,1);

        }

    public void redrawEventsRemoveEvent(EventTime toBeRemoved)
    {
        removeStackPanes();
        //Removing the event from the dayTimeline
        dayTimeline.removeEventTime(toBeRemoved);
        drawEvents();
    }

    public void redrawEventsAddEvent(EventTime toBeAdded)
    {
        removeStackPanes();
        dayTimeline.addEventTime(toBeAdded);
        drawEvents();
    }

    public void redrawEventsRemoveEvent(EventNT toBeRemoved)
    {
        removeStackPanes();
        //Removing the event from the dayTimeline
        dayTimeline.removeEventNT(toBeRemoved);
        drawEvents();
    }

    public void redrawEventsAddEvent(EventNT toBeAdded)
    {
        removeStackPanes();
        dayTimeline.addEventNT(toBeAdded);
        drawEvents();
    }

    private void removeStackPanes()
    {
        //Removing all eventspanes with duration and without duration
        ArrayList<StackPane> eventsSPs = new ArrayList<StackPane>();
        for (EventTime ev: dayTimeline.getEventTimes())
        {
            eventsSPs.add(ev.getStackPane());
        }
        for (EventNT ev: dayTimeline.getEventNTs())
        {
            eventsSPs.add(ev.getStackPane());
        }
        gp.getChildren().removeAll(eventsSPs);
    }

    /*------------------------------------Drawing events------------------------------------*/
    public void drawEvents()
    {
        /* Get all */
        TreeSet<EventTime> eventTimes = dayTimeline.getEventTimes();
        TreeSet<EventNT> eventNTs = dayTimeline.getEventNTs();

        Iterator<EventTime> eventTimeIterator = eventTimes.iterator();
        Iterator<EventNT> eventNTIterator = eventNTs.iterator();

        /* This calendar object is the equivalent of an incrementing int
        * in a for loop, it increases with one day each iteration. */
        GregorianCalendar compareCal = (GregorianCalendar) dayTimeline.getStartDate().clone();

        /* This variable is needed to set the layoutX property of the rectangles
         * should be, that is the distance from the start of the timeline. */
        int daysPassed = 0;
        EventTime currentEventTime;


        /* This part draws the timelines events with duration*/
        if (eventTimeIterator.hasNext()) {
            currentEventTime = eventTimeIterator.next();
            boolean done = false;
            while (!done) {
                gp.setMargin(currentEventTime.getStackPane(), new Insets(5));
                /* If compareTo == 0 than its time to draw the event. */
                if (currentEventTime.getStartTime().compareTo(compareCal) == 0) {
                    /*Length of the event, necessary for how broad the rectangles
                    * should be.*/
                    GregorianCalendar EVTcopy = (GregorianCalendar) currentEventTime.getStartTime().clone();
                    int days = 0;
                    while (EVTcopy.compareTo(currentEventTime.getFinishTime()) <= 0)
                    {
                        EVTcopy.add(5, 1);
                        days++;
                    }

                    StackPane eventPane = currentEventTime.getStackPane();


                    Iterator<EventTime> iterator = eventTimes.iterator();
                    /* The AList simEvents stores all events in the timeline that are simultaneous
                    * to the currentEventTime, this is to be able to draw everything as high as
                    * possible.*/
                    ArrayList<EventTime> simEvents = new ArrayList<EventTime>();
                    while (iterator.hasNext())
                    {
                        EventTime compareEvent = iterator.next();
                        /* Since we don't want to iterate past the current event
                        * we simply break when we reach the current event. */
                        if (compareEvent.equals(currentEventTime))
                        {
                            break;
                        }
                        if (currentEventTime.areSimultaneousEvents(compareEvent))
                        {
                            simEvents.add(compareEvent);
                        }
                    }
                    System.out.println("För event " + currentEventTime.getTitle() + " finns det " +
                           simEvents.size() + " simultana event");
                    boolean bigEnoughGapFound = false;
                    int rowIndex = 3;
                    gp.add(eventPane, daysPassed, rowIndex, days, 1);

                    TreeSet<Integer> rowIndexes = new TreeSet<Integer>();
                    for (EventTime compareEvent: simEvents)
                    {
                        int rowIndexOfCEvent = GridPane.getRowIndex(compareEvent.getStackPane());
                        rowIndexes.add(rowIndexOfCEvent);
                    }

                    System.out.println(rowIndexes.toString());

                    Iterator<Integer> rowIterator = rowIndexes.iterator();

                    if (!rowIndexes.isEmpty())
                    {
                        /* If rowIndexes first element is equal to three there's no need to
                        * change the row index och the pane because it means there's an open
                        * spot at the top*/
                        System.out.println("rowindexes är inte tom");
                        int previousRowIndex = -1;
                        if (rowIndexes.first() == 3)
                        {
                            System.out.println("Första index är 3");
                            while (!bigEnoughGapFound && rowIterator.hasNext())
                            {
                                System.out.println();
                                int tempRowIndex = rowIterator.next();
                                if (previousRowIndex != -1)
                                {
                                    if (tempRowIndex - previousRowIndex > 1)
                                    {
                                        bigEnoughGapFound = true;
                                        GridPane.setRowIndex(eventPane, previousRowIndex + 1);
                                    }
                                }
                                previousRowIndex = tempRowIndex;
                            }


                            if (previousRowIndex == -1) {rowIndex = 4;}
                            else {rowIndex = previousRowIndex + 1;}
                            System.out.println("previousRowIndex = " + previousRowIndex + "    och rowIndex är = " + rowIndex);
                            if (!bigEnoughGapFound)
                            {
                                System.out.println("Tillräckliget stort gap har ej funnits");
                                GridPane.setRowIndex(eventPane, rowIndex);
                            }
                        }
                    }
                    if (eventTimeIterator.hasNext()) {currentEventTime = eventTimeIterator.next(); }
                    else { done = true; }
                }
                else { compareCal.add(5, 1); daysPassed++; }
            }
        }

        compareCal = (GregorianCalendar) dayTimeline.getStartDate().clone();
        daysPassed = 0;

        EventNT currentEventNT;
        /* This part draws the timelines events without duration*/
        if ( eventNTIterator.hasNext()) {
            currentEventNT = eventNTIterator.next();
            boolean done = false;
            while (!done)
            {
                System.out.println("inne i loopen");
                /* If compareTo == 0 than its time to draw the event. */
                if (currentEventNT.getDate().compareTo(compareCal) == 0) {

                    StackPane eventPane = currentEventNT.getStackPane();
                    gp.setMargin(eventPane, new Insets(5));

                    Iterator<EventTime> iterator = eventTimes.iterator();
                    /* The AList simEvents stores all events in the timeline that are simultaneous
                    * to the currentEventTime, this is to be able to draw everything as high as
                    * possible.*/
                    ArrayList<EventTime> simEventsTime = new ArrayList<EventTime>();
                    while (iterator.hasNext())
                    {
                        EventTime compareEvent = iterator.next();
                        if (currentEventNT.areSimultaneousEvents(compareEvent))
                        {
                            simEventsTime.add(compareEvent);
                        }
                    }

                    System.out.println("Det finns " + eventTimes.size() + " event med duration i tidslinjen.");

                    ArrayList<EventNT> simEventsNOTime = new ArrayList<EventNT>();

                    Iterator<EventNT> iterator1 = eventNTs.iterator();
                    while (iterator1.hasNext())
                    {
                        EventNT compareEvent = iterator1.next();
                        if (currentEventNT.compareTo(compareEvent) == 0)
                        {
                            break;
                        }
                        if (currentEventNT.getDate().compareTo(compareEvent.getDate()) == 0)
                        {
                            simEventsNOTime.add(compareEvent);
                        }
                    }

                    System.out.println("För event " + currentEventNT.getTitle() + " finns det " +
                            simEventsTime.size() + " simultana event med duration och "
                    + simEventsNOTime.size() + " simultana event utan duration.");
                    boolean bigEnoughGapFound = false;
                    int rowIndex = 3;
                    gp.add(eventPane, daysPassed, rowIndex);

                    TreeSet<Integer> rowIndexesEvsTime = new TreeSet<Integer>();
                    for (EventTime compareEvent: simEventsTime)
                    {
                        int rowIndexOfCEvent = GridPane.getRowIndex(compareEvent.getStackPane());
                        rowIndexesEvsTime.add(rowIndexOfCEvent);
                    }

                    TreeSet<Integer> rowIndexesEvsNOTime = new TreeSet<Integer>();
                    for (EventNT compareEvent: simEventsNOTime)
                    {
                        int rowIndexOfCEvent = GridPane.getRowIndex(compareEvent.getStackPane());
                        rowIndexesEvsNOTime.add(rowIndexOfCEvent);
                    }

                    System.out.println(rowIndexesEvsTime.toString());

                    Iterator<Integer> rowIterator = rowIndexesEvsTime.iterator();

                    rowIndex = findFirstFreeRowIndex(rowIndexesEvsTime, rowIndexesEvsNOTime);
                    GridPane.setRowIndex(eventPane, rowIndex);
                    if (eventNTIterator.hasNext()) {currentEventNT = eventNTIterator.next(); }
                    else { done = true; }
                }
                else { compareCal.add(5, 1); daysPassed++; }
            }
        }
    }

    private int findFirstFreeRowIndex(TreeSet<Integer> in1, TreeSet<Integer> in2)
    {
        TreeSet<Integer> both = new TreeSet<Integer>();
        both.addAll(in1);
        both.addAll(in2);
        Iterator<Integer> iterator = both.iterator();
        if (both.size() == 0 || both.first() != 3) { return 3;}
        else if (both.size() == 1) {return 4; }
        else
        {
            int previousNr = iterator.next();
            int currentNr = iterator.next();
            while (iterator.hasNext())
            {
                if (currentNr - previousNr > 1) { return previousNr + 1; }
                previousNr = currentNr;
                currentNr = iterator.next();
            }
            return currentNr + 1;
        }
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
