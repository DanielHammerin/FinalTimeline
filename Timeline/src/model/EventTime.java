package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class EventTime extends MyEvent implements Comparable<EventTime>{

	private GregorianCalendar startTime;
	private GregorianCalendar finishTime;
	Rectangle evRect;
	
	public EventTime(String t, String d, GregorianCalendar st, GregorianCalendar ft) {
		super(t, d);
		if (st.compareTo(ft) > 0) {throw new IllegalArgumentException("The start date has to be before the end date."); }
		startTime = st;
		finishTime = ft;
		evRect = new Rectangle(20,20);
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
 
}
