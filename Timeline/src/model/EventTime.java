package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import view.EditEventPopover;


public class EventTime extends MyEvent implements Comparable<EventTime>{

	private GregorianCalendar startTime;
	private GregorianCalendar finishTime;
	Pane eventPane;
	EditEventPopover editEventPopover;
	
	public EventTime(String t, String d, GregorianCalendar st, GregorianCalendar ft) {
		super(t, d);
		if (st.compareTo(ft) > 0) {throw new IllegalArgumentException("The start date has to be before the end date."); }
		this.startTime = st;
		this.finishTime = ft;
		eventPane = new Pane();
		eventPane.setPrefHeight(50);

		eventPane.setOnMouseClicked(rightClick ->{
			if(editEventPopover != null){
				editEventPopover.hide();
				editEventPopover = null;
			 }
			else{
				if (rightClick.getButton() == MouseButton.SECONDARY) {
					editEventPopover = new EditEventPopover(this);
					editEventPopover.show(eventPane);
			 }
			}
		});
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

	public Pane getPane(){return eventPane; }

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

	public boolean areSimultaneousEvents(EventTime in) {
		boolean isOverlapping = false;
		if (startTime.compareTo(in.getStartTime()) == 0 || startTime.compareTo(in.getFinishTime()) == 0
				|| finishTime.compareTo(in.getStartTime()) == 0 || finishTime.compareTo(in.getFinishTime()) == 0) {
			isOverlapping = true;
		} else if (startTime.compareTo(in.getStartTime()) > 0) {
			if (startTime.compareTo(in.getFinishTime()) < 0) {
				isOverlapping = true;
			}
		} else if (startTime.compareTo(in.getStartTime()) < 0) {
			if (startTime.compareTo(in.getFinishTime()) > 0) {
				isOverlapping = true;
			}
		}
		return isOverlapping;
	}

}
