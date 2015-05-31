package model;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import view.EditEventPopover;

public class EventNT extends MyEvent implements Comparable<EventNT>{

	private GregorianCalendar dateOfEvent;



	private DayTimeline dayTimeline;
	StackPane eventPane;
	EditEventPopover editEventPopover;
	Circle eventCircle;

	public EventNT (String t, String d, GregorianCalendar date,DayTimeline dayTimeline){
		super(t, d);
		this.dayTimeline = dayTimeline;
		dateOfEvent = date;
		eventCircle = new Circle(13);
		eventCircle.setFill(Color.valueOf("#71E874"));

		eventPane = new StackPane(eventCircle);
		eventPane.setPrefHeight(50);
		eventPane.setOnMouseClicked(rightClick ->{
			if(editEventPopover != null){
				editEventPopover.hide();
				editEventPopover = null;
			}
			else{
				if(rightClick.getButton() == MouseButton.SECONDARY){
					try {
						editEventPopover = new EditEventPopover(this);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					editEventPopover.show(eventPane);
				}
			}
		});
	}

	public StackPane getStackPane(){return eventPane; }

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

	public boolean areSimultaneousEvents(EventTime in)
	{
		int afterStartOfThisEvt = dateOfEvent.compareTo(in.getStartTime());
		int beforeEndOfThisEvt = dateOfEvent.compareTo(in.getFinishTime());
		return afterStartOfThisEvt >= 0 && beforeEndOfThisEvt <= 0;
	}

	public GregorianCalendar getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(GregorianCalendar dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}

	public DayTimeline getDayTimeline() {
		return dayTimeline;
	}

	public void setDayTimeline(DayTimeline dayTimeline) {
		this.dayTimeline = dayTimeline;
	}
}
