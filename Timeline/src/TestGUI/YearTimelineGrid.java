package TestGUI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.MonthTimeline;
import model.YearTimeline;

/**
 * Created by Jakob on 2015-04-28.
 */
public class YearTimelineGrid
{
    /* topAndColumns are the entire time line stacked.*/
    private VBox topAndColumns;
    /* The top part with the days, months and year rectangles*/
    private HBox top;
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
    private int noYears;
    /* The rectangle width of all the rectangles*/
    static int rectangleWidth = 100;

    public YearTimelineGrid(YearTimeline in)
    {
        top = drawTopPart(in);
        columns = drawColumns();
        topAndColumns = new VBox(top,columns);
    }

    public VBox getGrid(){ return topAndColumns; }

    public void expandColumns()
    {
        heightOfColumns += 30;
        columns.getChildren().removeAll(rectCols);
        for (int i = 0; i < noYears; i++)
        {
            Rectangle column = new Rectangle(rectangleWidth, heightOfColumns);
            column.setFill(Color.BISQUE);
            column.setStroke(Color.BLACK);
            rectCols[i] = column;
        }
        columns.getChildren().addAll(rectCols);
    }

    public HBox drawTopPart(YearTimeline in)
    {
        HBox out  = new HBox();
        noYears = in.getEndYear() - in.getStartYear() + 1;
        for (int i = in.getStartYear(); i <= in.getEndYear(); i++)
        {
            Rectangle r = new Rectangle(rectangleWidth,topHeight);
            r.setFill(Color.ANTIQUEWHITE);
            r.setStroke(Color.BLACK);
            //Adding the year to the rectangle
            Text t = new Text(i + "");
            StackPane stack = new StackPane();
            stack.getChildren().addAll(r, t);
            out.getChildren().add(stack);
        }
        return out;
    }

    public HBox drawColumns()
    {
        HBox out = new HBox();
        rectCols = new Rectangle[noYears];
        for (int i = 0; i < noYears; i++)
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
