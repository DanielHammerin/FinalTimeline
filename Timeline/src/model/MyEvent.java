package model;


public abstract class MyEvent
{
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
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description;}

}
