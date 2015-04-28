package TestGUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.DayTimeline;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class for a day time line grid.
 * Created by Jakob on 2015-04-23.
 */
public class DayTimelineGrid
{
    /* topAndColumns are the entire time line stacked.*/
    private VBox topAndColumns;
    /* The top part with the days, months and year rectangles*/
    private VBox top;
    /* The part with all the columns representing the days */
    private HBox columns;
    /* The height of the daily columns */
    private int heightOfColumns = 120;
    /* An int keeping track on how many columns to draw, simplifies
    * the resizing of the day columns.*/
    private int daysToDraw;
    /* An integer for how high the top rectangles will be */
    static int topHeight = 30;
    /* An array with the rectangles for the columns, necessary for resizing
    * the columns for many multiple events*/
    private Rectangle[] rectCols;

    /* The rectangle width of all the rectangles*/
    static int rectangleWidth = 30;

    /* A string array of all the months shared in the project */
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
        top = drawTopPart(in);
        columns = drawColumns(daysToDraw);
        topAndColumns = new VBox(top,columns);
    }

    /**
     * Method for getting the entire grid
     * @return a VBox with the top and then columns
     */
    public VBox getGrid() { return topAndColumns; }

    /**
     * Getter for the VBox top
     * @return a VBox of the day, month and year rectangles
     */
    public VBox getTop() { return top; }

    /**
     * Getter for the columns HBox
     * @return a HBox with rectangles representing the days
     */
    public HBox getColumns(){ return columns; }

    /**
     * A method for expanding the height of the columns. This in case that the
     * timeline has so many events simultaneously that the events can't fit
     * vertically on the grid.
     * OBSERVE: Currently not working correctly!!!! Jakob is working on it
     */
    public void expandColumns()
    {
        heightOfColumns += 30;
        columns.getChildren().removeAll(rectCols);
        for (int i = 0; i < daysToDraw; i++)
        {
            Rectangle column = new Rectangle(rectangleWidth, heightOfColumns);
            column.setFill(Color.BISQUE);
            column.setStroke(Color.BLACK);
            rectCols[i] = column;
        }
        columns.getChildren().addAll(rectCols);
    }

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
                Rectangle monthRect = new Rectangle(daysSinceNewMonth * (rectangleWidth + 1) - 1, topHeight);
                monthRect.setStroke(Color.BLACK);
                monthRect.setFill(Color.RED);
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
                    Rectangle yearRect = new Rectangle(daysPassed * (rectangleWidth + 1) - 1, topHeight);
                    yearRect.setStroke(Color.BLACK);
                    yearRect.setFill(Color.AQUA);
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
        Rectangle monthRect = new Rectangle(daysSinceNewMonth * (rectangleWidth + 1) - 1, topHeight);
        monthRect.setStroke(Color.BLACK);
        monthRect.setFill(Color.RED);
        Text monthName = new Text(months[currentMonth%12]);
        StackPane monthPane = new StackPane();
        monthPane.getChildren().addAll(monthRect, monthName);
        monthsBox.getChildren().add(monthPane);

        /* Adding the last year rectangle.*/
        Rectangle yearRect = new Rectangle((daysPassed - daysSinceNewYear) * (rectangleWidth + 1) - 1, topHeight);
        yearRect.setStroke(Color.BLACK);
        yearRect.setFill(Color.AQUA);
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
    private HBox drawColumns(int columnsToDraw)
    {
        HBox out  = new HBox();
        rectCols = new Rectangle[columnsToDraw];
        for (int i = 0; i < daysToDraw; i++)
        {
            Rectangle column = new Rectangle(rectangleWidth, heightOfColumns);
            column.setFill(Color.BISQUE);
            column.setStroke(Color.BLACK);
            rectCols[i] = column;
        }
        out.getChildren().addAll(rectCols);
        return out;
    }
}
