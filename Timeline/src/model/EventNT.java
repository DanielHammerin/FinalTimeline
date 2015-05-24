package model;

import java.util.GregorianCalendar;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class EventNT extends MyEvent implements Comparable<EventNT> {

	private GregorianCalendar dateOfEvent;
	private Circle circle;
	Pane backGroundPane;
	StackPane eventPane;


	private int startYear;
	private int startMonth;
	private int startDay;
		
	public EventNT (String t, String d, GregorianCalendar date){
		super(t, d);
		dateOfEvent = date;
		circle = new Circle(20);
		backGroundPane = new Pane();
		backGroundPane.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
		Text title = new Text(t);
		title.setWrappingWidth(backGroundPane.getWidth());
		eventPane = new StackPane(backGroundPane, title);
		eventPane.setPrefHeight(50);

	}

	public StackPane getStackPane(){return eventPane; }

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

	public boolean areSimultaneousEvents(EventTime in)
	{
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

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public GregorianCalendar getDateOfEvent() {
		return dateOfEvent;
	}

	public void setDateOfEvent(GregorianCalendar dateOfEvent) {
		this.dateOfEvent = dateOfEvent;
	}


}

