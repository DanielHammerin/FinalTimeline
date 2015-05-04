package model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DayTimeline extends Timeline
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
	/* Same as previous*/
	SimpleDateFormat dayF = new SimpleDateFormat("dd");
	private static final int MAX_DAYS = 365;
	
	public DayTimeline(String title, String description, GregorianCalendar start, GregorianCalendar end)
	{
		super(title, description, "d");
		if (start.compareTo(end) >= 0)
		{
			throw new IllegalArgumentException("The start date has to be before the end date.");
		}
		int startYear = Integer.parseInt(yearF.format(start.getTime()));
		validateDates(startYear ,start, end);
		startDate = start;
		endDate = end;
	}

	private void validateDates(int startYear, GregorianCalendar start, GregorianCalendar end) 
	{
		GregorianCalendar startCopy = (GregorianCalendar) start.clone();
		int year = startYear + 1;
		int month = Integer.parseInt(monthF.format(start.getTime()));
		int day = Integer.parseInt(dayF.format(start.getTime()));
		startCopy.set(year, month, day);
		if (startCopy.compareTo(end) < 0)
		{
			throw new IllegalArgumentException("The time line can maximum be " + MAX_DAYS
					+ " days long.");
		}
	}

	public GregorianCalendar getStartDate() { return startDate; }

	public GregorianCalendar getEndDate() { return endDate; }


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
			super.addEventNT(in);
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
			super.addEventTime(in);
		}
		else
		{
			throw new IllegalArgumentException("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");
		}
	}
}
