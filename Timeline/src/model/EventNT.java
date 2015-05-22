package model;

import java.util.GregorianCalendar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.EditEventPopover;

public class EventNT extends MyEvent implements Comparable<EventNT> {

	public GregorianCalendar getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(GregorianCalendar dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	private GregorianCalendar dateOfEvent;
	private int startYear;
	private int startMonth;
	private int startDay;
	Pane eventPane;
	EditEventPopover editEventPopover;
	
	public EventNT (String t, String d, GregorianCalendar date){
		super(t, d);
		dateOfEvent = date;

	    eventPane = new Pane();
		eventPane.setPrefWidth(50);
		eventPane.setPrefHeight(50);
		eventPane.setOnMouseClicked(rightClick ->{
			if(editEventPopover != null){
				editEventPopover.hide();
				editEventPopover = null;
			}
			else{
				if(rightClick.getButton() == MouseButton.SECONDARY){
					editEventPopover = new EditEventPopover(this);
					editEventPopover.show(eventPane);
				}
			}
		});
	}

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
}

