package TestGUI;

import java.util.ArrayList;

import model.DayTimeline;
import model.MonthTimeline;
import model.MyEvent;
import model.YearTimeline;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * This class represents the graphical grid/timeline where the events of timelines have to be drawn on
 * and updates have to occur
 * @author Alexander
 *
 */
public class NewGrid extends Group
{
	/* An integer for how high the top rectangles will be */
	static int topHeight = 30;

	/* Year time line variables for drawing a grid. */
	private YearTimeline yearTimeline;
	static int rectangleWidthYear = 80;

	/* Month time line variables for drawing a grid. */
	private MonthTimeline monthTimeline;
	static int rectangleWidthMonth = 130;

	/* Daily time line variables for drawing a grid. */
	private DayTimeline dayTimeline;
	static int rectangleWidthDay = 30;

	/* A string array of all the months shared in the project */
	public String[] months = {"December", "January", "February", "March", "April", "May", "June", "July", "August", "September"
			, "October", "November"};


	public NewGrid(){}
	public VBox MonthGrid(MonthTimeline timelineIn, double heightOfBox)
	{
		monthTimeline = timelineIn;
		//HBox with rectangles for the years
		HBox yearsBox = new HBox();
		//HBox with rectangles for the months
		HBox monthsBox = new HBox();
		//HBox with the rectangles for the events
		HBox columns = new HBox();

		//Selfexplanatory variables
		int startYear = timelineIn.getStartYear();
		int endYear = timelineIn.getEndYear();
		int startMonth = timelineIn.getStartMonth();
		int endMonth = timelineIn.getEndMonth();

		//Number of years the timeline is spanning over, including the start and end year
		int noYears = endYear - startYear + 1;

        /* The number of months the timeline spanns over. The +1's are to include the
        * start and end months. */
		int noMonths = noYears * 12 - startMonth + 1 - (12 - endMonth + 1);

        /* This variable more or less counts the number of months until the next year
        * so that the top HBox knows how large it should be.*/
		int yearWidth = 0;

        /* The loop for drawing the grid. Goes through all months to the end month.*/
		for (int i = startMonth; i < startMonth + noMonths; i++)
		{
            /* If i % 12 == 1 it means that the current month is january so before we draw
            * that month we need to draw the year rectangle above the lasts months that
            * got painted.*/
			if (i % 12 == 1)
			{
                /* rectangleWidthMonth + 1 because every setStroke call adds a line with the
                * width of one pixel. -1 is to avoid the offset by one pixel.*/
				Rectangle y = new Rectangle(yearWidth * (rectangleWidthMonth + 1) - 1, topHeight);
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
			Rectangle m = new Rectangle(rectangleWidthMonth, topHeight);
			m.setStroke(Color.BLACK);
			m.setFill(Color.BEIGE);
			Text monthText = new Text(months[i % 12]);
			StackPane stack = new StackPane();
			stack.getChildren().addAll(m, monthText);
			monthsBox.getChildren().add(stack);

            /* Adding the rectangles for the months to the grid below the months*/
			Rectangle column = new Rectangle(rectangleWidthMonth, heightOfBox);
			column.setFill(Color.BEIGE);
			column.setStroke(Color.BLACK);
			columns.getChildren().add(column);
			yearWidth++;
		}

        /* Adding the last year rectangle for the last months*/
		Rectangle y = new Rectangle(yearWidth * (rectangleWidthMonth + 1) - 1, topHeight);
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
	 * Constructor for the NewGrid for a yearly time line. Returns a drawn grid
	 * with the years of the time line drawn at the top and rectangles of the years
	 * below.
	 *
	 * @param timeline to be represented
	 * @param heightOfBox of the parent container (needed for height calculation)
	 */
	public NewGrid(YearTimeline timeline, double heightOfBox)
	{
		this.yearTimeline= timeline;
		HBox topLine = new HBox();
		VBox base = new VBox();

		//Header
		for (int i = timeline.getStartYear(); i < yearTimeline.getEndYear(); i++)
		{
			Rectangle r = new Rectangle(rectangleWidthYear,topHeight);
			r.setFill(Color.ANTIQUEWHITE);
			r.setStroke(Color.BLACK);
			//Adding the year to the rectangle
			Text t = new Text(i + "");
			StackPane stack = new StackPane();
			stack.getChildren().addAll(r, t);
			topLine.getChildren().add(stack);
		}

		base.getChildren().add(topLine);

		//Columns
		HBox calenderColumns = new HBox();
		Rectangle[] columns = new Rectangle[yearTimeline.getEndYear()-yearTimeline.getStartYear()];

		for (int i = 0; i < columns.length; i++)
		{
			columns[i] = new Rectangle(rectangleWidthYear, heightOfBox-topHeight);
			columns[i].setFill(Color.AZURE);
			columns[i].setStroke(Color.BLACK);
			calenderColumns.getChildren().add(columns[i]);
		}

		base.getChildren().add(calenderColumns);
		this.getChildren().add(base);

		//Iterates through all events of this Timeline and gets the geometric figures to be placed in the grid
		for(MyEvent e: timeline.getEvents())
		{
			this.getChildren().add(e.getGeometricFigure());
		}

		this.getChildren().add(addEventToGrid(new MyEvent("Hello","Description") {
		}));

	}

	//adding event rectangle older version
	public StackPane addEventToGrid(MyEvent myEvent)
	{
		Rectangle eventRec = new Rectangle(50, 40);
		eventRec.setFill(Color.RED);
		eventRec.setStroke(Color.BLACK);
		Text text = new Text("Event");
		StackPane temp = new StackPane();
		temp.getChildren().addAll(eventRec, text);
		temp.setLayoutX(7);
		temp.setLayoutY(35);
		temp.setOnMouseClicked(event->{
			System.out.println("Nu klickas din mama");
		});

		return temp;
	}

}
