package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class EventTime extends MyEvent implements Comparable<EventTime>{

	private GregorianCalendar startTime;
	private GregorianCalendar finishTime;
	/* monthF can be used to get a numerical value from an gregorian calendar
	 * object. When you use a gregorian calendar as argument it returns a string
	 * representing the month. You have to parse it to an int to use it as a
	 * number.*/
	SimpleDateFormat monthF = new SimpleDateFormat("MM");
	/* Same as monthF.*/
	SimpleDateFormat yearF = new SimpleDateFormat("yyyyyyyyy");
	Rectangle evRect;
	
	public EventTime(String t, String d, GregorianCalendar st, GregorianCalendar ft) {
		super(t, d);
		if (st.compareTo(ft) > 0) {throw new IllegalArgumentException("The start date has to be before the end date."); }
		startTime = st;
		finishTime = ft;
		evRect = new Rectangle(20,20);
	}

	/**
	 * Method for getting the numerical value of the start month of
	 * the monthly scaled timeline.
	 * @return an int 1 <= x <= 12
	 */
	public int getStartMonth()
	{
		return Integer.parseInt(monthF.format(startTime.getTime()));
	}

	/**
	 * Method for getting the numerical value of the end month of
	 * the monthly scaled timeline.
	 * @return an int 1 <= x <= 12
	 */
	public int getEndMonth()
	{
		return Integer.parseInt(monthF.format(finishTime.getTime()));
	}

	/**
	 * Method that returns the start year of the timeline
	 * @return the start year
	 */
	public int getStartYear()
	{
		return Integer.parseInt(yearF.format(startTime.getTime()));
	}

	/**
	 * Method that returns the end year of the timeline
	 * @return the end year
	 */
	public int getEndYear()
	{
		return Integer.parseInt(yearF.format(finishTime.getTime()));
	}

	 public void setStartTime (GregorianCalendar date) {
		 this.startTime = date;
	 }
	 
	 public GregorianCalendar getStartTime (){
		 return startTime;
	 }
	 
	 public void setFinishTime (GregorianCalendar date) {
		 this.finishTime = date;
	 }
	 
	 public GregorianCalendar getFinishTime (){
		 return finishTime;
	 }

	public Rectangle getRectangle(){return evRect; }

	public int compareTo(EventTime toCompare)
	{
		int startDateC = startTime.compareTo(toCompare.startTime);
		if (startDateC != 0) { return startDateC;}
		int endDateC = finishTime.compareTo(toCompare.finishTime);
		if (endDateC != 0) { return endDateC; }
		int titleC = this.getTitle().compareTo(toCompare.getTitle());
		if (titleC != 0) { return titleC; }
		else { return this.getDescription().compareTo(toCompare.getDescription()); }
	}

	public boolean areSimultaneousEvents(EventTime in) {
		boolean isOverlapping = false;
		if (startTime.compareTo(in.getStartTime()) == 0 || startTime.compareTo(in.getFinishTime()) == 0
				|| finishTime.compareTo(in.getStartTime()) == 0 || finishTime.compareTo(in.getFinishTime()) == 0) {
			isOverlapping = true;
		} else if (startTime.compareTo(in.getStartTime()) > 0) {
			if (startTime.compareTo(in.getFinishTime()) < 0) {
				isOverlapping = true;
			}
		} else if (startTime.compareTo(in.getStartTime()) < 0) {
			if (startTime.compareTo(in.getFinishTime()) > 0) {
				isOverlapping = true;
			}
		}
		return isOverlapping;
	}

}
