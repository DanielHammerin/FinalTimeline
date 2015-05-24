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
		System.out.println("Compareto för eventNTs datum med event med durations startdatum: "
				+ afterStartOfThisEvt);
		System.out.println("Compareto för eventNTs datum med event med durations slutdatum: "
				+ beforeEndOfThisEvt);
		return afterStartOfThisEvt >= 0 && beforeEndOfThisEvt <= 0;
	}

}

