package model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * A class for creating a monthly timeline
 * @author Jakob
 *
 */
public class MonthTimeline extends Timeline
{
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	/* monthF can be used to get a numerical value from an gregorian calendar
	 * object. When you use a gregorian calendar as argument it returns a string
	 * representing the month. You have to parse it to an int to use it as a 
	 * number.*/
	SimpleDateFormat monthF = new SimpleDateFormat("MM");
	/* Same as monthF.*/
	SimpleDateFormat yearF = new SimpleDateFormat("yyyyyyyyy");
	private static final int MAX_YEARS_DIFFERENCE = 5;
	
	public MonthTimeline(String title, String description, GregorianCalendar start, GregorianCalendar end)
	{
		super(title, description);
		int startYear = Integer.parseInt(yearF.format(start.getTime()));
		int endYear = Integer.parseInt(yearF.format(end.getTime()));
		/* MAX_YEARS_DIFFERENCE - 1 because say you want a monthly timeline
		 * between year 1 to 5, that's five years: 1, 2, 3, 4, 5 but if you
		 * only take the absolute value |1-5| == 4*/
		if (Math.round(Math.abs(endYear-startYear))  > MAX_YEARS_DIFFERENCE - 1)
		{
			throw new IllegalArgumentException("A monthly timeline can span over maximum "
					+ MAX_YEARS_DIFFERENCE + " years.");
		}
		if (endYear - startYear < 0)
		{
			throw new IllegalArgumentException("The start year has to be before the end year.");
		}
		startDate = start;
		endDate = end;
	}
	
	/**
	 * Method for getting the numerical value of the start month of 
	 * the monthly scaled timeline. 
	 * @return an int 1 <= x <= 12
	 */
	public int getStartMonth()
	{
		return Integer.parseInt(monthF.format(startDate.getTime()));
	}
	
	/**
	 * Method for getting the numerical value of the end month of 
	 * the monthly scaled timeline. 
	 * @return an int 1 <= x <= 12
	 */
	public int getEndMonth()
	{
		return Integer.parseInt(monthF.format(endDate.getTime()));
	}
	
	/**
	 * Method that returns the start year of the timeline
	 * @return the start year
	 */
	public int getStartYear()
	{
		return Integer.parseInt(yearF.format(startDate.getTime()));
	}
	
	/**
	 * Method that returns the end year of the timeline
	 * @return the end year
	 */
	public int getEndYear()
	{
		return Integer.parseInt(yearF.format(endDate.getTime()));
	}

	/**
	 * Method for adding an event without duration to the treeset of the time line containing
	 * the events without duration.
	 *
	 * @param in the event to be added.
	 */
	@Override
	public void addEventNT(EventNT in)
	{
		if (in.getDate().compareTo(startDate) >= 0 && in.getDate().compareTo(endDate) <= 0)
		{
			super.getEventNTs().add(in);
		}
		else
		{
			throw new IllegalArgumentException("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");
		}
	}

	/**
	 * Method for adding an event with duration to the treeset of the time line containing
	 * the events with duration.
	 *
	 * @param in the event to be added.
	 */
	@Override
	public void addEventTime(EventTime in)
	{
		if (in.getStartTime().compareTo(startDate) >= 0 && in.getFinishTime().compareTo(endDate) <= 0)
		{
			super.getEventTimes().add(in);
		}
		else
		{
			throw new IllegalArgumentException("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");
		}
	}
}
