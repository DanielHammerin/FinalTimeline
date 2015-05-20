package model;

import java.util.TreeSet;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Super class for the different kinds of timelines. Holds a string
 * for description of a timeline, a string for the title of the of
 * the timeline aswell as an linked list with the events of the timeline.
 * @author Jakob
 *
 */
public abstract class Timeline
{
	private String title;
	private String description;
	private static final int MAX_CHARS_TITLE = 30;
    private boolean inEditMode;
	private boolean isYearTimeline = false;
	private boolean isMonthTimeline = false;
	private boolean isDayTimeline = false;

	public void setEventTimes(TreeSet<EventTime> eventTimes) {
		this.eventTimes = eventTimes;
	}

	public void setEventNTs(TreeSet<EventNT> eventNTs) {
		this.eventNTs = eventNTs;
	}

	//For the different types of events
	private TreeSet<EventNT> eventNTs;
	protected TreeSet<EventTime> eventTimes;
	protected TreeSet<MyEvent> myEvents;
	public Timeline () { // Note: I need this constructor for the DAO ;) puss puss Mauro <3
    }

    /**
     * The super constructor for all timelines. Takes a string containing the description
     * of the event and a string containing the title as arguments.
     * @param t the title
     * @param d
	 * @param typeOfTimeline a string for determining what type of timeline it is, "y"
	 *                       for year time line, "m" for month time line and "d" for
	 *                       a daily time line.
     */
	public Timeline(String t, String d, String typeOfTimeline)	{
		if (t.length() > MAX_CHARS_TITLE) {
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("The title "
					+ " can be maximum " + MAX_CHARS_TITLE + " characters long.");
			alert.showAndWait();
		
			throw new IllegalArgumentException("The title "
				+ " can be maximum " + MAX_CHARS_TITLE + " characters long.");}
		title = t;
		description = d;
        inEditMode = false;

		if (typeOfTimeline.equals("y")) { isYearTimeline = true; }
		else if (typeOfTimeline.equals("m")) {isMonthTimeline = true; }
		else {isDayTimeline = true; }

		eventNTs = new TreeSet<EventNT>();
		eventTimes = new TreeSet<EventTime>();
		myEvents = new TreeSet<MyEvent>();
	}

	/**
	 * Getter for the title of the time-line.
	 * @return the title
	 */
	public String getTitle() { return title; }

	/**
	 * Setter for the title of a time-line.
	 * @param title
	 */
	public void setTitle(String title)
	{
		if (title.length() > MAX_CHARS_TITLE) {
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("The title "
					+ " can be maximum " + MAX_CHARS_TITLE + " characters long.");
			alert.showAndWait();
			
			throw new IllegalArgumentException("The title "
				+ " can be maximum " + MAX_CHARS_TITLE + " characters long.");}
		this.title = title;
	}

	/**
	 * Getter for the description of the time-line.
	 * @return
	 */
	public String getDescription() { return description; }

	/**
	 * Setter for the description of the time-line.
	 * @param description
	 */
	public void setDescription(String description) { this.description = description; }

	/**
	 * Method for adding an event without duration to the treeset of the time line containing
	 * the events without duration.
	 * @param in the event to be added.
	 */
	public void addEventNT(EventNT in) { eventNTs.add(in); }
	
	public void addEventNT(MyEvent in) { myEvents.add(in); }

	public void removeEventNT(MyEvent in) { myEvents.remove(in); }

	public void addEventTime(EventTime in) { eventTimes.add(in); }

	public TreeSet<EventNT> getEventNTs() { return eventNTs; }

	public TreeSet<EventTime> getEventTimes() { return eventTimes; }

	public void removeEventNT(EventNT toRemove) {eventNTs.remove(toRemove); }

	public void removeEventTime(EventTime toRemove) {eventTimes.remove(toRemove); }

    public void edit() {inEditMode = true; }

    public void stopEditing() { inEditMode = false; }

    public boolean isInEditMode(){return inEditMode; }

	public boolean isMonthTimeline() { return isMonthTimeline; }

	public boolean isYearTimeline() { return isYearTimeline; }

	public boolean isDayTimeline() { return isDayTimeline; }
}
