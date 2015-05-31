package model;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class for the day timeline to store the logic of the time line.
 */
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
	private boolean isShown = false;
	boolean imADummy = false;

	public DayTimeline() { imADummy = true; }
	public boolean isImADummy() {return imADummy; }

	public DayTimeline(String title, String description, GregorianCalendar start, GregorianCalendar end)
	{
		super(title, description, "d");
		if (start.compareTo(end) >= 0)
		{
			//Mauro: alerts removed
			throw new IllegalArgumentException("The start date has to be before the end date.");
		}
		imADummy = false;
		int startYear1 = Integer.parseInt(yearF.format(start.getTime()));
		validateDates(startYear1 ,start, end);
		startDate = start;
		endDate = end;
	}

	/**
	 * Private help method that checks the dates for validity
	 * @param startYear
	 * @param start
	 * @param end
	 */
	private void validateDates(int startYear, GregorianCalendar start, GregorianCalendar end)
	{
		GregorianCalendar startCopy = (GregorianCalendar) start.clone();
		int year = startYear + 1;
		int month = Integer.parseInt(monthF.format(start.getTime()));
		int day = Integer.parseInt(dayF.format(start.getTime()));
		startCopy.set(year, month, day);
		if (startCopy.compareTo(end) < 0)
		{
			//Mauro: alerts removed
			throw new IllegalArgumentException("The time line can maximum be " + MAX_DAYS
					+ " days long.");
		}
	}

	public GregorianCalendar getStartDate() { return startDate; }
	public GregorianCalendar getEndDate() { return endDate; }
	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(GregorianCalendar startDate) {	this.startDate = startDate; }

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
			//Mauro: Alert removed
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
			//Mauro: alert removed
			throw new IllegalArgumentException("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");
		}
	}

	public boolean isShown() {
		if(isShown == true){return true;}
		else{ return false;}
	}

	public void isShown(boolean isShown) {
		this.isShown = isShown;
	}
}