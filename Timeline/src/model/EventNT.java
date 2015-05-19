package model;

import java.util.GregorianCalendar;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.EditEventPopover;

public class EventNT extends MyEvent implements Comparable<EventNT> {
	
	private GregorianCalendar dateOfEvent;
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

}

