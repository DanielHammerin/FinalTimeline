package model;

import java.util.GregorianCalendar;
import javafx.scene.shape.Circle;

public class EventNT extends MyEvent implements Comparable<EventNT> {
	
	private GregorianCalendar dateOfEvent;
	private Circle circle;
		
	public EventNT (String t, String d, GregorianCalendar date){
		super(t, d);
		dateOfEvent = date;
		circle = new Circle(20);
	}

	public Circle getCircle() { return  circle; }

	@Override
	public String toString (){
		return (this.getTitle() + ": " + this.getDescription());
	}
	
	public GregorianCalendar getDate() { return dateOfEvent; }


	public int compareTo(EventNT toCompare)
	{
		int dateC = dateOfEvent.compareTo(toCompare.getDate());
		if (dateC != 0) { return dateC;}
		int titleC = this.getTitle().compareTo(toCompare.getTitle());
		if (titleC != 0) { return titleC; }
		else { return this.getDescription().compareTo(toCompare.getDescription()); }
	}

	public boolean areSimultaneousEvents(EventTime in){
		int afterStartOfThisEvt = dateOfEvent.compareTo(in.getStartTime());
		int beforeEndOfThisEvt = dateOfEvent.compareTo(in.getFinishTime());
		return afterStartOfThisEvt >= 0 && beforeEndOfThisEvt <= 0;
	}

}

