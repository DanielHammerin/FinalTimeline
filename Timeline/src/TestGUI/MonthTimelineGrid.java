package TestGUI;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.MonthTimeline;


/**
 * A class for drawing a grid for a monthly time line.
 * Created by Jakob on 2015-04-28.
 */
public class MonthTimelineGrid
{
    private MonthTimeline monthTimeline;
    /* topAndColumns are the entire time line stacked.*/
    private VBox topAndColumns;
    /* The top part with the days, months and year rectangles*/
    private VBox top;
    /* The part with all the columns representing the days */
    private HBox columns;
    /* The height of the daily columns */
    private int heightOfColumns = 100;
    /* An integer for how high the top rectangles will be */
    static int topHeight = 30;
    /* An array with the rectangles for the columns, necessary for resizing
    * the columns for many multiple events*/
    private Rectangle[] rectCols;
    /* An int for how many columns that are needed.*/
    private int noMonths;

    /* The rectangle width of all the rectangles*/
    static int rectangleWidth = 100;

    /* A string array of all the months shared for all time lines */
    public String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September"
            , "October", "November", "December"};


    /**
     * The constructor for the DayTimelineGrid
     * @param in is the daytimeline to draw
     */
    public MonthTimelineGrid(MonthTimeline in)
    {
        monthTimeline = in;
        top = drawTopPart(in);
        columns = drawColumns(noMonths);
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
        timelineContainer.setPrefHeight(300);
        timelineContainer.setPrefWidth((monthTimeline.getEndYear() - monthTimeline.getStartYear()) * 50);

        timelineContainer.setMinWidth((monthTimeline.getEndYear() - monthTimeline.getStartYear()) * 50);
        timelineContainer.setMinHeight(300);
        timelineContainer.setVisible(true);

        System.out.println("Width of scrollPane: " + timelineContainer.getWidth());

        myAnchorPane= new AnchorPane();
        myAnchorPane.prefHeightProperty().bind(timelineContainer.heightProperty());
        myAnchorPane.prefWidthProperty().bind(timelineContainer.widthProperty());

        System.out.println(myAnchorPane.getWidth());

        myAnchorPane.getChildren().add(topAndColumns);
        AnchorPane.setBottomAnchor(topAndColumns, 0.0);
        AnchorPane.setLeftAnchor(topAndColumns, 0.0);
        AnchorPane.setTopAnchor(topAndColumns, 0.0);
        AnchorPane.setRightAnchor(topAndColumns, 0.0);
        timelineContainer.setContent(myAnchorPane);
        //timelineContainer.getChildren().add(myAnchorPane);

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
    public HBox getColumns(){ return columns; }

    /**
     * A method for expanding the height of the columns. This in case that the
     * timeline has so many events simultaneously that the events can't fit
     * vertically on the grid.
     */
    public void expandColumns()
    {
        heightOfColumns += 30;
        columns.getChildren().removeAll(rectCols);
        for (int i = 0; i < noMonths; i++)
        {
            Rectangle column = new Rectangle(rectangleWidth, heightOfColumns);
            column.setFill(Color.BISQUE);
            column.setStroke(Color.BLACK);
            rectCols[i] = column;
        }
        columns.getChildren().addAll(rectCols);
    }

    public VBox drawTopPart(MonthTimeline in)
    {
        //HBox with rectangles for the years
        HBox yearsBox = new HBox();
        //HBox with rectangles for the months
        HBox monthsBox = new HBox();
        //HBox with the rectangles for the events
        HBox columns = new HBox();

        //Selfexplanatory variables
        int startYear = in.getStartYear();
        int endYear = in.getEndYear();
        /* Minus 1 because dateformat returns the month number n + 1*/
        int startMonth = in.getStartMonth() - 1;
        int endMonth = in.getEndMonth();

        //Number of years the timeline is spanning over, including the start and end year
        int noYears = endYear - startYear + 1;

        /* The number of months the timeline spanns over. The +1's are to include the
        * start and end months. */
        noMonths = noYears * 12 - startMonth + 1 - (12 - endMonth + 1);

        /* This variable more or less counts the number of months until the next year
        * so that the top HBox knows how large it should be.*/
        int yearWidth = 0;

        /* The loop for drawing the grid. Goes through all months to the end month.*/
        for (int i = startMonth; i < startMonth + noMonths; i++)
        {
            /* If i % 12 == 0 it means that the current month is january so before we draw
            * that month we need to draw the year rectangle above the lasts months that
            * got painted.*/
            if (i % 12 == 0)
            {
                /* rectangleWidthMonth + 1 because every setStroke call adds a line with the
                * width of one pixel. -1 is to avoid the offset by one pixel.*/
                Rectangle y = new Rectangle(yearWidth * (rectangleWidth + 1) - 1, topHeight);
                y.setStroke(Color.BLACK);
                y.setFill(Color.AZURE);
                //The number for the rectangle
                Text whatYear = new Text(startYear++ + "");
                /*Creating the year rectangle with both the rectangle and text*/
                StackPane oneYear = new StackPane();
                oneYear.getChildren().addAll(y, whatYear);
                yearsBox.getChildren().add(oneYear);
                /* Setting the width variable to zero again*/
                yearWidth = 0;
            }
            /* Adding rectangles for the months*/
            Rectangle m = new Rectangle(rectangleWidth, topHeight);
            m.setStroke(Color.BLACK);
            m.setFill(Color.BEIGE);
            Text monthText = new Text(months[i % 12]);
            StackPane stack = new StackPane();
            stack.getChildren().addAll(m, monthText);
            monthsBox.getChildren().add(stack);

            yearWidth++;
        }

        /* Adding the last year rectangle for the last months*/
        Rectangle y = new Rectangle(yearWidth * (rectangleWidth + 1) - 1, topHeight);
        y.setStroke(Color.BLACK);
        y.setFill(Color.AZURE);
        Text whatYear = new Text(endYear + "");
        StackPane oneYear = new StackPane();
        oneYear.getChildren().addAll(y, whatYear);
        yearsBox.getChildren().add(oneYear);

        /*Stacking all panes into one VBox and returning the VBox*/
        VBox base = new VBox();
        base.getChildren().addAll(yearsBox, monthsBox, columns);
        return base;
    }

    /**
     * Method for drawing the month columns
     * @param noMonths are the number months to be drawn
     * @return an HBox containing an array of rectangles
     */
    private HBox drawColumns(int noMonths)
    {
        HBox out  = new HBox();
        rectCols = new Rectangle[noMonths];
        for (int i = 0; i < noMonths; i++)
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
