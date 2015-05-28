package model;

import java.util.GregorianCalendar;

import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import view.EditEventPopover;


public class EventTime extends MyEvent implements Comparable<EventTime>
{

	private GregorianCalendar startTime;
	private GregorianCalendar finishTime;
	Pane backGroundPane;
	StackPane eventPane;
	EditEventPopover editEventPopover;


	private int startYear;
	private int startMonth;
	private int startDay;
	private int endYear;
	private int endMonth;
	private int endDay;

	public EventTime(String t, String d, GregorianCalendar st, GregorianCalendar ft) {
		super(t, d);
		if (st.compareTo(ft) > 0) {throw new IllegalArgumentException("The start date has to be before the end date."); }
		this.startTime = st;
		this.finishTime = ft;
		backGroundPane = new Pane();
		backGroundPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		eventPane = new StackPane(backGroundPane, new Text(t));
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

	public StackPane getStackPane() { return eventPane ; }

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

	public boolean areSimultaneousEvents(EventTime in)
	{
		boolean isOverlapping = false;
		if (startTime.compareTo(in.getStartTime()) == 0 || startTime.compareTo(in.getFinishTime()) == 0
				|| finishTime.compareTo(in.getStartTime()) == 0 || finishTime.compareTo(in.getFinishTime()) == 0) {
			return true;
		}
		else if (startTime.compareTo(in.getStartTime()) > 0)
		{
			if (startTime.compareTo(in.getFinishTime()) < 0)
			{
				return true;
			}
		}
		else if (finishTime.compareTo(in.getStartTime()) > 0)
		{
			if (finishTime.compareTo(in.getFinishTime()) < 0)
			{
				return true;
			}
		}
		return isOverlapping;
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

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

}
