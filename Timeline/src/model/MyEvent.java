package model;
import java.util.Date;

public abstract class MyEvent extends Timeline {
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
	public void setAddEvent(String title,String description){
		this.title = title;
		this.description = description;
	}
	public void setEditEvent(String title,String description){
		this.title = title;
		this.description = description;

	}
    public void setDeleteEvent(String title,String description){
    	this.title = title;
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
	public void addEventNT (){
		
		super.addEventNT(this);	
	}
	public void removeEventNT (){
		
		super.removeEventNT(this);	
	}
	
	public boolean equals(Object obj) {
		if(obj == this){return true;}
		if(obj instanceof MyEvent == false){return false;}
		MyEvent my=(MyEvent)obj;
		return title.equals(my.title);
	}
}
