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

    /**
     * The main constructor for creating the grid of the daytimeline
     * @param timeline the timeline to be drawn
     */
    public NewDayTimelineGrid(DayTimeline timeline)
    {
        dayTimeline = timeline;
        this.setPrefHeight(450);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);
        title = new Label();
        Font titleSize = new Font(20);
        title.setFont(titleSize);

        title.setMinHeight(40);
        VBox myBox = new VBox();
        myBox.setAlignment(Pos.CENTER);

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

    /**
     * The method for drawing the top part of the grid with which year
     * month and day that is.
     */
    private void drawDays()
    {
        /* Variables needed for the top part*/
        int compareDay = dayTimeline.getStartDate().get(Calendar.DAY_OF_MONTH);
        int currentMonth = dayTimeline.getStartDate().get(Calendar.MONTH);
        int currentYear = dayTimeline.getStartDate().get(Calendar.YEAR);
        int daysSinceLastMonth = 0;
        int daysPassedSinceYear = 0;
        int daysPassed = 0;
        int fontSizeYears = 16;

        /* The columns constraints for the grid*/
        ColumnConstraints c = new ColumnConstraints(50,35,Double.MAX_VALUE);
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
        gp.getRowConstraints().add(8, r3);
        gp.getRowConstraints().add(9, r3);


        /* This is needed for drawing the right amount of months*/
        boolean moreThanOneMonth = false;

        /* This can be considered as the iterator index of the loop for drawing grid.
        * It will consistently add a day to the startCopy variable until it's at the
        * end date of the dayTimeline.*/
        GregorianCalendar startCopy = (GregorianCalendar) dayTimeline.getStartDate().clone();

        /* Looping through all days and drawing them*/
        while (startCopy.compareTo(dayTimeline.getEndDate()) <= 0)
        {
            gp.getColumnConstraints().addAll(c);


            int currentDay = startCopy.get(Calendar.DAY_OF_MONTH);
            /* If the compareDay is larger than the current day than it's a new month*/
            if (compareDay > currentDay)
            {
                moreThanOneMonth = true;
                /* The name of the month*/
                Label month = new Label(months[currentMonth % 12]);
                month.setEffect(new DropShadow(50,Color.BLACK));
                /* Adding the month label to the grid*/
                gp.add(month, daysPassed - daysSinceLastMonth, 1, daysSinceLastMonth, 1);
                /* Resetting the daysSinceLastMonth variable so that the right column
                * span is for the next month*/
                daysSinceLastMonth = 0;
                currentMonth = startCopy.get(Calendar.MONTH);
                /* If the current month is 0 than we have a new year */
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
            /* This part just add the months numbers */
            Label day = new Label(startCopy.get(Calendar.DAY_OF_MONTH) + "");
            /* Increasing the iterator index*/
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
            Label year = new Label(currentYear + "");
            Font yearSize = new Font(fontSizeYears);
            year.setFont(yearSize);
            int rowIndex = daysPassed - daysPassedSinceYear;
            if (rowIndex < 0) { rowIndex = 0; }
            gp.add(year, rowIndex, 0, daysPassedSinceYear, 1);
        }
        /* Calling the method drawEvents to draw out the events in the grid pane*/
        drawEvents();
    }

    /**
     * Method for removing an event and redrawing the
     * event from the time line
     * @param toBeRemoved the event to be removed
     */
    public void redrawEventsRemoveEvent(EventTime toBeRemoved)
    {
        //Removing the event from the dayTimeline
        removeStackPanes();
        dayTimeline.removeEventTime(toBeRemoved);
        drawEvents();
    }

    /**
     * Adding an event and redrawing the grid
     * @param toBeAdded the event to be added
     */
    public void redrawEventsAddEvent(EventTime toBeAdded)
    {
        removeStackPanes();
        dayTimeline.addEventTime(toBeAdded);
        drawEvents();
    }

    /**
     * Method for removing an event and redrawing the
     * event from the time line
     * @param toBeRemoved the event to be removed
     */
    public void redrawEventsRemoveEvent(EventNT toBeRemoved)
    {
        removeStackPanes();
        //Removing the event from the dayTimeline
        dayTimeline.removeEventNT(toBeRemoved);
        drawEvents();
    }

    /**
     * Adding an event and redrawing the grid
     * @param toBeAdded the event to be added
     */
    public void redrawEventsAddEvent(EventNT toBeAdded)
    {
        removeStackPanes();
        dayTimeline.addEventNT(toBeAdded);
        drawEvents();
    }

    /**
     * Private help method for clearing the grid pane from stackpanes
     * before redrawing them
     */
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

    /**
     * This method draws out the events of a day timeline
     */
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
        if (eventTimeIterator.hasNext())
        {
            currentEventTime = eventTimeIterator.next();
            boolean done = false;
            while (!done)
            {
                /* Setting the margins for the events*/
                gp.setMargin(currentEventTime.getStackPane(), new Insets(5));
                /* If compareTo == 0 than its time to draw the event. */
                if (currentEventTime.getStartTime().compareTo(compareCal) == 0)
                {
                    /*Length of the event, necessary for how broad the rectangles
                    * should be for the events with duration.*/
                    GregorianCalendar EVTcopy = (GregorianCalendar) currentEventTime.getStartTime().clone();
                    int days = 0;
                    while (EVTcopy.compareTo(currentEventTime.getFinishTime()) <= 0)
                    {
                        EVTcopy.add(5, 1);
                        days++;
                    }

                    /* The stack pane to be drawn*/
                    StackPane eventPane = currentEventTime.getStackPane();

                    /* An iterator for going through all events*/
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
//                    System.out.println("För event " + currentEventTime.getTitle() + " finns det " +
//                           simEvents.size() + " simultana event");
                    boolean bigEnoughGapFound = false;
                    int rowIndex = 3;
                    gp.add(eventPane, daysPassed, rowIndex, days, 1);

                    TreeSet<Integer> rowIndexes = new TreeSet<Integer>();
                    /* Getting the rowindexes of the events panes to find the first free
                    * spot to insert the event.*/
                    for (EventTime compareEvent: simEvents)
                    {
                        int rowIndexOfCEvent = GridPane.getRowIndex(compareEvent.getStackPane());
                        rowIndexes.add(rowIndexOfCEvent);
                    }

                    Iterator<Integer> rowIterator = rowIndexes.iterator();

                    if (!rowIndexes.isEmpty())
                    {
                        int previousRowIndex = -1;
                        /* If the first rowindex is 3 then we need to look for
                        * the first available row index.*/
                        if (rowIndexes.first() == 3)
                        {
                            while (!bigEnoughGapFound && rowIterator.hasNext())
                            {
                                int tempRowIndex = rowIterator.next();
                                if (previousRowIndex != -1)
                                {
                                    /* If the difference is larger than 1 than it means
                                    * there is an open spot, hence bigEnoughGap = true*/
                                    if (tempRowIndex - previousRowIndex > 1)
                                    {
                                        bigEnoughGapFound = true;
                                        GridPane.setRowIndex(eventPane, previousRowIndex + 1);
                                    }
                                }
                                previousRowIndex = tempRowIndex;
                            }

                            /* If previousRowIndex == -1 than it means that the
                            * row index 3 is occupied. */
                            if (previousRowIndex == -1) {rowIndex = 4;}
                            else {rowIndex = previousRowIndex + 1;}
                            if (!bigEnoughGapFound)
                            {
                                GridPane.setRowIndex(eventPane, rowIndex);
                            }
                        }
                    }
                    /* End conditions for the loop*/
                    if (eventTimeIterator.hasNext()) {currentEventTime = eventTimeIterator.next(); }
                    else { done = true; }
                }
                else { compareCal.add(5, 1); daysPassed++; }
            }
        }

        /* Resetting the variables for the drawing of the events with no duration*/
        compareCal = (GregorianCalendar) dayTimeline.getStartDate().clone();
        daysPassed = 0;

        EventNT currentEventNT;
        /* This part draws the timelines events without duration, there are almost no comments
        * since the logic is identical to the part of drawing the events with duration. See the
        * part before this for explanation of the logic.*/
        if ( eventNTIterator.hasNext()) {
            currentEventNT = eventNTIterator.next();
            boolean done = false;
            while (!done)
            {
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

                    rowIndex = findFirstFreeRowIndex(rowIndexesEvsTime, rowIndexesEvsNOTime);
                    GridPane.setRowIndex(eventPane, rowIndex);
                    if (eventNTIterator.hasNext()) {currentEventNT = eventNTIterator.next(); }
                    else { done = true; }
                }
                else { compareCal.add(5, 1); daysPassed++; }
            }
        }
    }

    /**
     * Help method for finding the first free row index for the part that
     * draws events without duration.
     * @param in1 the treeset with the rowindexes for the simultaneous events with duration
     * @param in2 the treeset with the rowindexes for the simultaneous events without duration
     * @return the first free index
     */
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


    public DayTimeline getDayTimeline()
    {
        return dayTimeline;
    }

    public void setDayTimeline(DayTimeline dayTimeline)
    {
        this.dayTimeline = dayTimeline;
    }

}
