package model;

public abstract class Event {
	
	String title = "";
	String description = "";
	
	public Event (String t, String d){ // Constructor
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
	

}
