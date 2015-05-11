package view;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.DayTimeline;
import model.EventNT;
import model.EventTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Class for a day time line grid.
 * Created by Jakob on 2015-04-23.
 */
public class DayTimelineGrid
{
    private DayTimeline dayTimeline;
    /* topAndColumns are the entire time line stacked.*/
    private VBox topAndColumns;
    /* The top part with the days, months and year rectangles*/
    private VBox top;
    /* The part with all the columns representing the days */
    private Group columns;
    /* The height of the daily columns */
    private int heightOfColumns = 100;
    /* An int keeping track on how many columns to draw, simplifies
    * the resizing of the day columns.*/
    private int daysToDraw;
    /* An integer for how high the top rectangles will be */
    static int topHeight = 30;
    /* An array with the rectangles for the columns, necessary for resizing
    * the columns for many multiple events*/
    private Rectangle[] rectCols;

    /* The rectangle width of all the rectangles*/
    private static int rectangleWidth = 30;
    /* The width of the stroke of all rectangles*/
    private static double rectStrokeWidth = 1;
    /* Horizontal space between the events */
    private static double horSpace = 5;

    /* A string array of all the months shared for all time lines */
    public String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
            , "October", "November", "December"};

    /* Simple date formats for the dates.*/
    SimpleDateFormat yearF = new SimpleDateFormat("yyyyyyy");
    SimpleDateFormat monthF = new SimpleDateFormat("MM");
    SimpleDateFormat dayF = new SimpleDateFormat("dd");

    /**
     * The constructor for the DayTimelineGrid
     * @param in is the daytimeline to draw
     */
    public DayTimelineGrid(DayTimeline in)
    {
        dayTimeline = in;
        top = drawTopPart(in);
        columns = drawColumns(daysToDraw);
        System.out.println("column");
        columns.setOnMouseClicked(event -> {
            System.out.println("X: " + event.getX() + "    Y: " + event.getY());
        });
        topAndColumns = new VBox(top,columns);
    }

    /**
     * Method for getting the entire grid
     * @return a VBox with the top and then columns
     */
    public VBox getGrid() { return topAndColumns; }


    public ScrollPane getTimeLineBlock(){
        ScrollPane timelineContainer = new ScrollPane();
        AnchorPane myAnchorPane;
        timelineContainer.setPrefHeight(500);
		long diff = dayTimeline.getEndDate().getTime().getTime() -dayTimeline.getStartDate().getTime().getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
        timelineContainer.setPrefWidth((int) diffDays * 50);
        timelineContainer.setMinWidth((int) diffDays * 50);
        timelineContainer.setMinHeight(500);

	    myAnchorPane= new AnchorPane();
		myAnchorPane.prefHeightProperty().bind(timelineContainer.heightProperty());
		myAnchorPane.prefWidthProperty().bind(timelineContainer.widthProperty());

		myAnchorPane.getChildren().add(topAndColumns);
		AnchorPane.setBottomAnchor(topAndColumns, 0.0);
	    AnchorPane.setLeftAnchor(topAndColumns, 0.0);
		AnchorPane.setTopAnchor(topAndColumns, 0.0);
		AnchorPane.setRightAnchor(topAndColumns, 0.0);
        timelineContainer.setContent(myAnchorPane);

        return timelineContainer;
    }
    /**
     * Getter for the VBox top
     * @return a VBox of the day, month and year rectangles
     */
    public VBox getTop() { return top; }

    /**
     * Getter for the columns HBox
     * @return a HBox with rectangles representing the days
     */
    public Group getColumns(){ return columns; }

    /**
     * A method for expanding the height of the columns. This in case that the
     * timeline has so many events simultaneously that the events can't fit
     * vertically on the grid.
     */
    public void expandColumns()
    {
        heightOfColumns += 30;
        columns.getChildren().remove(0);
        HBox newCols = new HBox();
//        columns.getChildren().removeAll(rectCols);
        for (int i = 0; i < daysToDraw; i++)
        {
            Rectangle column = new Rectangle(rectangleWidth, heightOfColumns);
            column.setFill(Color.BISQUE);
            column.setStroke(Color.BLACK);
            rectCols[i] = column;
        }
        newCols.getChildren().addAll(rectCols);
        columns.getChildren().add(0, newCols);
//        columns.getChildren().addAll(rectCols);
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
        boolean drawnEventsTime = false;

        /* This part draws the timelines events with duration*/
        if (eventTimeIterator.hasNext())
        {
            drawnEventsTime = true;
            currentEventTime = eventTimeIterator.next();
            boolean done = false;
            while (!done)
            {
                /* If compareTo == 0 than its time to draw the event. */
                if (currentEventTime.getStartTime().compareTo(compareCal) == 0)
                {
                    /*Length of the event, necessary for how broad the rectangles
                    * should be.*/
                    GregorianCalendar EVTcopy = (GregorianCalendar) currentEventTime.getStartTime().clone();
                    int days = 0;
                    while (EVTcopy.compareTo(currentEventTime.getFinishTime()) <= 0)
                    {
                        EVTcopy.add(5, 1);
                        days++;
                    }

                    Rectangle evRect = currentEventTime.getRectangle();
                    evRect.setHeight(topHeight);
                    evRect.setWidth(days * (rectangleWidth + rectStrokeWidth));
                    evRect.setStroke(Color.BLACK);
                    evRect.setFill(Color.CORAL);
                    evRect.setLayoutX(daysPassed * (rectangleWidth + rectStrokeWidth));
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
                        if (compareEvent.equals(currentEventTime)) { break; }
                        if (currentEventTime.areSimultaneousEvents(compareEvent))
                        {
                            simEvents.add(compareEvent);
                        }
                    }
                    boolean bigEnoughGapFound = false;
                    int i = 0;
                    /* The Y layout for the figure to be drawn. */
                    double layoutY = horSpace;
                    while (!bigEnoughGapFound && i < simEvents.size())
                    {
                        bigEnoughGapFound = true;
                        layoutY = horSpace + (horSpace + topHeight) * i;
                        evRect.setLayoutY(layoutY);
                        /* This part is comparing with the other simultaneous
                        * events to see whether it intersects with them. If it
                        * doesn't it just draws the event. This leads to the
                        * events being drawn as high up on the time line as
                        * possible.*/
                        for (EventTime compareEvent: simEvents)
                        {
                            if (compareEvent.getRectangle().getBoundsInParent().intersects(evRect.getBoundsInParent()))
                            {
                                bigEnoughGapFound = false;
                            }
                        }
                        i++;
                    }
                    if (!bigEnoughGapFound) {layoutY = horSpace + (horSpace + topHeight) * i; }

                    while (layoutY + topHeight > heightOfColumns) { this.expandColumns(); }
                    evRect.setLayoutY(layoutY);
                    columns.getChildren().add(evRect);
                    if (eventTimeIterator.hasNext()) {currentEventTime = eventTimeIterator.next(); }
                    else { done = true; }
                }
                else {compareCal.add(5,1); daysPassed++;}
            }
        }

        /* Resetting the compareCal for the drawing of the events with no duration */
        compareCal = (GregorianCalendar) dayTimeline.getStartDate().clone();

        /* Resetting the variable for drawing the events with no duration */
        daysPassed = 0;

        EventNT currentEventNOTime;

        /* This next part draws the events that have no duration*/
        if (eventNTIterator.hasNext())
        {
            currentEventNOTime = eventNTIterator.next();
            boolean done = false;
            while (!done)
            {
                /* If compareTo == 0 than its time to draw the event. */
                if (currentEventNOTime.getDate().compareTo(compareCal) == 0)
                {
                    Circle evCircle = currentEventNOTime.getCircle();
                    evCircle.setRadius(topHeight / 2 - 1);
                    evCircle.setStroke(Color.BLACK);
                    evCircle.setFill(Color.CORAL);
                    evCircle.setLayoutX(daysPassed * (rectangleWidth + rectStrokeWidth));
                    System.out.println("Days passed: " + daysPassed);
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
                        if (currentEventNOTime.getDate().compareTo(compareEvent.getStartTime()) == 0) {break; }
                        if (currentEventNOTime.areSimultaneousEvents(compareEvent))
                        {
                            simEvents.add(compareEvent);
                        }
                    }
                    boolean gapFound = false;
                    int i = 0;
                    /* The Y layout for the figure to be drawn. */
                    double layoutY = horSpace;
                    while (!gapFound && i < simEvents.size())
                    {
                        gapFound = true;
                        layoutY = horSpace + (horSpace + topHeight) * i;
                        evCircle.setLayoutY(layoutY);
                        /* This part is comparing with the other simultaneous
                        * events to see whether it intersects with them. If it
                        * doesn't it just draws the event. This leads to the
                        * events being drawn as high up on the time line as
                        * possible.*/
                        for (EventTime compareEvent: simEvents)
                        {
                            if (compareEvent.getRectangle().getBoundsInParent().intersects(evCircle.getBoundsInParent()))
                            {
                                gapFound = false;
                            }
                        }
                        i++;
                    }
                    if (!gapFound) {layoutY = horSpace + (horSpace + topHeight) * i; }

                    while (layoutY + topHeight > heightOfColumns) { this.expandColumns(); }
                    evCircle.setLayoutY(layoutY);
                    columns.getChildren().add(evCircle);
                    if (eventTimeIterator.hasNext()) {currentEventTime = eventTimeIterator.next(); }
                    else { done = true; }
                }
                else {compareCal.add(5,1); daysPassed++;}
            }
        }
    }
    /*-----------------------------------End of Drawing events-----------------------------------*/

    /**
     * This is a method for drawing the top part of the grid with the day, month and
     * year rectangles.
     * @param in the day timeline to draw out.
     * @return a VBox with the rectangles.
     */
    private VBox drawTopPart(DayTimeline in)
    {
        /* HBoxes for each top row*/
        HBox yearsBox = new HBox();
        HBox monthsBox = new HBox();
        HBox daysBox = new HBox();

        /* Integers needed for the logic of the drawing*/
        int startDay = Integer.parseInt(dayF.format(in.getStartDate().getTime()));
        int compareDay = startDay;
        /* Start month = the date - 1 because the gregorian calendar takes the year and
        * day integers in the constructor as regular arguments but the month integer
        * starts at zero.*/
        int startMonth = Integer.parseInt(monthF.format(in.getStartDate().getTime())) - 1;
        int currentMonth = startMonth;
        int startYear = Integer.parseInt(yearF.format(in.getStartDate().getTime()));
        /* A comparison gregorian calendar for the drawing of the grid. */
        GregorianCalendar temp = new GregorianCalendar(startYear, startMonth, startDay);
        /* daysPassed are basically just to keep track of the iterations and it will be
        * copied to the daysToDraw variable for redrawing the day columns.*/
        int daysPassed = 0;
        /* These following variables are necessary for drawing out the month and year
        * rectangles.*/
        int daysSinceNewMonth = 0;
        int daysSinceNewYear = 0;

        /* The loop for drawing out all the days and all but the last month.*/
        while (temp.compareTo(in.getEndDate()) <= 0)
        {
            /* The daily rectangle*/
            int currentDay = Integer.parseInt(dayF.format(temp.getTime()));
            Rectangle dayRect = new Rectangle(rectangleWidth, topHeight);
            dayRect.setStroke(Color.BLACK);
            dayRect.setStrokeWidth(rectStrokeWidth);
            dayRect.setFill(Color.BEIGE);
            StackPane dayPane = new StackPane();
            Text dayNo = new Text(currentDay + "");
            dayPane.getChildren().addAll(dayRect, dayNo);
            /* Adding it to the HBox daysBox*/
            daysBox.getChildren().add(dayPane);


            /* New month, means that the last month has get a rectangle drawn on top of the days*/
            if (compareDay > currentDay)
            {
                /* Creating the month rectangle. (rectangleWidth + 1) - 1 because the stroke of a
                * rectangle adds one pixel but we need to remove the last pixel so we don't get
                * any offset.*/
                Rectangle monthRect = new Rectangle(daysSinceNewMonth * (rectangleWidth + rectStrokeWidth) - rectStrokeWidth, topHeight);
                monthRect.setStroke(Color.BLACK);
                monthRect.setStrokeWidth(rectStrokeWidth);
                monthRect.setFill(Color.BLUE);
                Text monthName = new Text(months[currentMonth%12]);
                StackPane monthPane = new StackPane();
                monthPane.getChildren().addAll(monthRect, monthName);
                monthsBox.getChildren().add(monthPane);
                /* -1 due to differences in SimpleDateFormat and the gregorian calendar class*/
                currentMonth = Integer.parseInt(monthF.format(temp.getTime())) - 1;
                /* Need to reset the days to multiply the width to.*/
                daysSinceNewMonth = 0;
                /* New year, we need to add another rectangle to the year HBox.*/
                if (currentMonth == 0)
                {
                    daysSinceNewYear = daysPassed;
                    Rectangle yearRect = new Rectangle(daysPassed * (rectangleWidth + rectStrokeWidth) - rectStrokeWidth, topHeight);
                    yearRect.setStroke(Color.BLACK);
                    yearRect.setStrokeWidth(rectStrokeWidth);
                    yearRect.setFill(Color.ANTIQUEWHITE);
                    Text yearText = new Text(Integer.parseInt(yearF.format(temp.getTime())) - 1 + "");
                    StackPane yearPane = new StackPane();
                    yearPane.getChildren().addAll(yearRect, yearText);
                    yearsBox.getChildren().add(yearPane);
                }
            }

            daysPassed++;
            daysSinceNewMonth++;
            /* For each iteration we simply add one day.*/
            temp.set(startYear, startMonth, startDay + daysPassed);
            compareDay = currentDay;
        }

        /* Adding the last month pane, same code as in the loop*/
        Rectangle monthRect = new Rectangle(daysSinceNewMonth * (rectangleWidth + rectStrokeWidth) - rectStrokeWidth, topHeight);
        monthRect.setStroke(Color.BLACK);
        monthRect.setStrokeWidth(rectStrokeWidth);
        monthRect.setFill(Color.BLUE);
        Text monthName = new Text(months[currentMonth%12]);
        StackPane monthPane = new StackPane();
        monthPane.getChildren().addAll(monthRect, monthName);
        monthsBox.getChildren().add(monthPane);

        /* Adding the last year rectangle.*/
        Rectangle yearRect = new Rectangle((daysPassed - daysSinceNewYear) * (rectangleWidth + rectStrokeWidth) - rectStrokeWidth, topHeight);
        yearRect.setStroke(Color.BLACK);
        yearRect.setStrokeWidth(rectStrokeWidth);
        yearRect.setFill(Color.ANTIQUEWHITE);
        Text yearText = new Text(Integer.parseInt(yearF.format(temp.getTime())) + "");
        StackPane yearPane = new StackPane();
        yearPane.getChildren().addAll(yearRect, yearText);
        yearsBox.getChildren().add(yearPane);


        /* Updating the daysToDraw variable.*/
        daysToDraw = daysPassed;
        VBox base = new VBox();
        base.getChildren().addAll(yearsBox, monthsBox, daysBox);
        return base;
    }

    /**
     * Method for drawing the
     * @param columnsToDraw
     * @return
     */
    private Group drawColumns(int columnsToDraw)
    {
        Group out  = new Group();
        HBox rectangles = new HBox();
        rectCols = new Rectangle[columnsToDraw];
        for (int i = 0; i < daysToDraw; i++)
        {
            Rectangle column = new Rectangle(rectangleWidth, heightOfColumns);
            column.setFill(Color.BISQUE);
            column.setStroke(Color.BLACK);
            column.setStrokeWidth(rectStrokeWidth);
            rectCols[i] = column;
        }
        rectangles.getChildren().addAll(rectCols);
        out.getChildren().add(rectangles);
        return out;
    }
}
