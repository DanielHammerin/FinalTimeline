package model;
import java.util.Date;

public abstract class MyEvent {
	private java.util.Date startDate;
	private java.util.Date endDate;
	private String title;
	private String description;
	
	public MyEvent (String t, String d){ // Constructor
		title = t;
		description = d;
	}

	public String getTitle() { // Getters and setters
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    public void setDate(java.util.Date startDate,java.util.Date endDate){
		this.startDate=startDate;
		this. endDate= endDate;
	}
    public java.util.Date getStartDate(){
		return startDate;
    }
	public java.util.Date getEndDate(){
		return endDate;
	}
}
