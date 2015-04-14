package model;
import com.db4o.*;

public abstract class MyEvent {
	
	String title = "";
	String description = "";
	
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
	
    public void addEevnt(String title,String description){
		
	}
	public void deleteEvent(String title,String description){
		 
	}
	public void editEvent(String title,String description){
		
	}
	
	public void addToDatabase (ObjectContainer dataBase){
		
		boolean flag = true;
		
		ObjectSet retrieve = dataBase.query(MyEvent.class); // created object to retrieve info from the database

		while (retrieve.hasNext()){// THERE IS AN ERROR IN THIS LOOP
			if (this.equals(retrieve.next())){
				flag = false;
			}
		}
		
		if (flag){
			dataBase.store(this);
			dataBase.commit();
		} else {
			System.out.println( "The event has been already added in the timeline!");
		}
		
		
	}
	

}
