package model;

import java.util.Date;


public class EventWithTime extends Event{

	Date startTime;
	Date finishTime;
	
	public EventWithTime(String t, String d, Date st, Date ft) {
		super(t, d);
		this.setStartTime(st);
		this.setFinishTime(ft);
	}

	 public void setStartTime (Date date) {
		 this.startTime = date;
	 }
	 
	 public Date getStartTime (){
		 return startTime;
	 }
	 
	 public void setFinishTime (Date date) {
		 this.finishTime = date;
	 }
	 
	 public Date getFinishTime (){
		 return finishTime;
	 }
 
}
