package model;

import java.util.Calendar;


public class EventTime extends Event{

	Calendar startTime;
	Calendar finishTime;
	
	public EventTime(String t, String d, Calendar st, Calendar ft) {
		super(t, d);
		this.setStartTime(st);
		this.setFinishTime(ft);
	}

	 public void setStartTime (Calendar date) {
		 this.startTime = date;
	 }
	 
	 public Calendar getStartTime (){
		 return startTime;
	 }
	 
	 public void setFinishTime (Calendar date) {
		 this.finishTime = date;
	 }
	 
	 public Calendar getFinishTime (){
		 return finishTime;
	 }
 
}
