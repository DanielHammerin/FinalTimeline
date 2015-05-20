package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DayTimeline extends Timeline
{
	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}

	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private int startYear;
	private int startMonth;
	private int startDay;
	private int endYear;
	private int endMonth;
	private int endDay;

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

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
	//Constructor for the database
	public DayTimeline(String title) {
		this.setTitle(title);
	}
	public DayTimeline(String title, String description, GregorianCalendar start, GregorianCalendar end)
	{
		super(title, description, "d");
		if (start.compareTo(end) >= 0)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error");
			alert.setContentText("The start date has to be before the end date.");

			alert.showAndWait();
			throw new IllegalArgumentException("The start date has to be before the end date.");
		}
		int startYear1 = Integer.parseInt(yearF.format(start.getTime()));
		validateDates(startYear1 ,start, end);
		startDate = start;
		endDate = end;
		startYear = start.get(Calendar.YEAR);
		startMonth = start.get(Calendar.MONTH);
		startDay = start.get(Calendar.DAY_OF_MONTH);
		endYear = end.get(Calendar.YEAR);
		endMonth = end.get(Calendar.MONTH);
		endDay = end.get(Calendar.DAY_OF_MONTH);
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
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("The time line can maximum be " + MAX_DAYS
					+ " days long.");

			alert.showAndWait();
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
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error");
			alert.setContentText("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");

			alert.showAndWait();
			
			
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
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error");
			alert.setContentText("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");

			alert.showAndWait();
			
			throw new IllegalArgumentException("The date of the event \"" + in.getTitle() + "\" is outside" +
					" the timelines start or end date.");
		}
	}


}
