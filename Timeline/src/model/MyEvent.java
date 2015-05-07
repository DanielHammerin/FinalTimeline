package model;
import java.util.Date;

public abstract class MyEvent {

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
}
