package model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class YearTimeline extends Timeline
{
	private int startYear;
	private int endYear;
	SimpleDateFormat yearF = new SimpleDateFormat("yyyyyyyyy");
	//The min value possible for the chosen year
	private static final int MIN_VALUE = -250000000;
	//The max value possible for the chosen year
	private static final int MAX_VALUE = 250000000;
	
	/**
	 * Constructor for a yearly scaled time-line. 
	 * @param title the title
	 * @param description the description 
	 * @param start the start date (will take the year value)
	 * @param end the end date (will take the year value)
	 */
	public YearTimeline(String title, String description, GregorianCalendar start, GregorianCalendar end)
	{
		super(title, description, "y");
		int startTemp = Integer.parseInt(yearF.format(start.getTime()));
		int endTemp = Integer.parseInt(yearF.format(end.getTime()));
		validateYear(startTemp, endTemp);
		startYear = startTemp;
		endYear = endTemp;
	}

	/* Private help method to ensure that the input values are valid*/
	private void validateYear(int start, int end) 
	{
		if (start >= end)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("The start year has to be before or the same year as"
					+ " the end year");

			alert.showAndWait();
			
			throw new IllegalArgumentException("The start year has to be before or the same year as"
					+ " the end year");
		}
		if (start < MIN_VALUE || start > MAX_VALUE)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("The start year x has to be " + MIN_VALUE + " <= x "
					+ MAX_VALUE);

			alert.showAndWait();
			
			throw new IllegalArgumentException("The start year x has to be " + MIN_VALUE + " <= x "
					+ MAX_VALUE);
		}
		if (end < MIN_VALUE || end > MAX_VALUE)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("The end year x has to be " + MIN_VALUE + " <= x "
					+ MAX_VALUE);

			alert.showAndWait();
			
			throw new IllegalArgumentException("The end year x has to be " + MIN_VALUE + " <= x "
					+ MAX_VALUE);
		}
	}

	/**
	 * Getter for the start year of the time line
	 * @return the start year (an int)
	 */
	public int getStartYear() {	return startYear; }

	/**
	 * Getter for the end year of the time line
	 * @return the end year (an int)
	 */
	public int getEndYear() { return endYear; }

	/**
	 * Method for adding an event without duration to the treeset of the time line containing
	 * the events without duration.
	 *
	 * @param in the event to be added.
	 */
	public void addEventNT(EventNT in)
	{
		int yearOfEvent = Integer.parseInt(yearF.format(in.getDate()));
		if (startYear <= yearOfEvent && yearOfEvent <= endYear)
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
	public void addEventTime(EventTime in)
	{
		int startYearOfEvent = Integer.parseInt(yearF.format(in.getStartTime()));
		int endYearOfEvent = Integer.parseInt(yearF.format(in.getFinishTime()));
		if (startYear <= startYearOfEvent && endYearOfEvent <= endYear)
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


