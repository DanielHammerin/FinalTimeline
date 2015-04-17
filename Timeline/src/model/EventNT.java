package model;

import java.util.Calendar;
import javafx.scene.shape.Circle;

public class EventNT extends MyEvent {
	
	private Calendar dateOfEvent;
		
	public EventNT (String t, String d, Calendar date){
		super (t,d);
		dateOfEvent = date;
		Circle c = new Circle(20);		
		geometricFigure.setStyle("-fx-background-color: coral;");
		geometricFigure.getChildren().add(c);		
	}
	
	@Override
	public String toString (){
		return (this.title + ": " + this.description);
	}
	
	public Calendar getDate()
	{
		return dateOfEvent;
	}

	public boolean equals(EventNT in) 
	{
		boolean descriptions = super.getDescription().equals(in.getDescription());
		boolean sameDate = in.getDate().equals(dateOfEvent);
		boolean sameTitle = in.getTitle().equals(super.title);
		return descriptions && sameDate && sameTitle;
	}

}

