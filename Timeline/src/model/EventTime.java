package model;

import java.sql.SQLException;
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

	public EventTime(String t, String d, GregorianCalendar st, GregorianCalendar ft)
	{
		super(t, d);
		if (st.compareTo(ft) > 0) {throw new IllegalArgumentException("The start date has to be before the end date."); }
		this.startTime = st;
		this.finishTime = ft;
		backGroundPane = new Pane();
		backGroundPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		eventPane = new StackPane(backGroundPane, new Text(getTitleText(t, st, ft)));
		eventPane.setPrefHeight(50);
		eventPane.setOnMouseClicked(rightClick ->{
			if(editEventPopover != null){
				editEventPopover.hide();
				editEventPopover = null;
			}
			else{
				if (rightClick.getButton() == MouseButton.SECONDARY) {
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

	private String getTitleText(String title, GregorianCalendar startDate, GregorianCalendar endDate)
	{
		if (startDate.compareTo(endDate) == 0)
		{
			if (title.length() < 6) { return title; }
			else { return title.substring(0,3) + "..." ;}
		}
		GregorianCalendar startDateCopy = (GregorianCalendar) startDate.clone();
		startDateCopy.add(5, 1);
		if (startDateCopy.compareTo(endDate) == 0)
		{
			if (title.length() < 12) { return title; }
			else { return title.substring(0, 9) + "..." ;}
		}
		startDateCopy.add(5, 1);
		if (startDateCopy.compareTo(endDate) == 0)
		{
			if (title.length() < 18) { return title; }
			else { return title.substring(0, 15) + "..." ;}
		}
		startDateCopy.add(5, 1);
		if (startDateCopy.compareTo(endDate) == 0)
		{
			if (title.length() < 24) { return title; }
			else { return title.substring(0, 21) + "..." ;}
		}
		return title;
	}

	public void setStartTime (GregorianCalendar date)
	{
		this.startTime = date;
		updateStackPane();
	}

	public GregorianCalendar getStartTime (){
		return startTime;
	}

	public void setFinishTime (GregorianCalendar date) {
		this.finishTime = date;
		updateStackPane();
	}

	private void updateStackPane()
	{
		eventPane.getChildren().set(1, new Text(getTitleText(this.getTitle(), startTime, finishTime))) ;
	}

	public GregorianCalendar getFinishTime (){ return finishTime; }

	public StackPane getStackPane() { return eventPane ; }

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
			if (startTime.compareTo(in.getFinishTime()) < 0){ return true;}
		}
		else if (finishTime.compareTo(in.getStartTime()) > 0)
		{
			if (finishTime.compareTo(in.getFinishTime()) < 0) { return true; }
		}
		return isOverlapping;
	}

}