package model;
import java.util.Date;

public abstract class MyEvent {
	private Date startDate;
	private Date endDate;
	private String title;
	private String description;

	public MyEvent(){}

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

    public void setDate(Date startDate,Date endDate){
		this.startDate=startDate;
		this.endDate= endDate;
	}
    public Date getStartDate(){
		return startDate;
    }
	public Date getEndDate(){
		return endDate;
	}
}
