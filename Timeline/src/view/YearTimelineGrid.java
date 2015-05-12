package view;

import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
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
    private YearTimeline yearTimeline;
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

    private NewEventPopOver newEventPopOver;


    public YearTimelineGrid(YearTimeline in)
    {
        yearTimeline = in;
        top = drawTopPart(in);
        columns = drawColumns();
        topAndColumns = new VBox(top,columns);
    }

    public VBox getGrid(){ return topAndColumns; }

    public ScrollPane getTimeLineBlock(){
        ScrollPane timelineContainer = new ScrollPane();

        /*
        timelineContainer.setOnMouseClicked(openContextMenu -> {
            MouseButton button = openContextMenu.getButton();
            if(button == MouseButton.SECONDARY) {
                ContextMenu cm = new ContextMenu();
                MenuItem mi = new MenuItem();
                cm.getItems().add(new MenuItem("Remove"));
                cm.getItems().add(new MenuItem("Edit"));
                cm.getItems().add(new MenuItem("Add Event"));

                cm.getItems().get(0).setOnAction(removeTimeline -> {

                });

                cm.getItems().get(1).setOnAction(editTimeline -> {

                });

                cm.getItems().get(2).setOnAction(addEvent -> {
                    if (newEventPopOver != null && newEventPopOver.isShowing()) {
                        newEventPopOver.hide();
                        newEventPopOver = null;
                    } else {
                        newEventPopOver = new NewEventPopOver();
                        newEventPopOver.show(timelineContainer);
                    }
                });
            }
            });
            */
        VBox myBox = new VBox();
        myBox.setAlignment(Pos.CENTER);
        Label title = new Label(yearTimeline.getTitle());
        AnchorPane myAnchorPane;
        timelineContainer.setPrefHeight(300);
        timelineContainer.setPrefWidth((yearTimeline.getEndYear() - yearTimeline.getStartYear()) * 50);
        timelineContainer.setMinWidth((yearTimeline.getEndYear() - yearTimeline.getStartYear()) * 50);
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

        myBox.getChildren().add(title);
        myBox.getChildren().add(myAnchorPane);
        timelineContainer.setContent(myBox);

        return timelineContainer;
    }



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

    public YearTimeline getYearTimeline() {
        return yearTimeline;
    }

    public void setYearTimeline(YearTimeline yearTimeline) {
        this.yearTimeline = yearTimeline;
    }
}
