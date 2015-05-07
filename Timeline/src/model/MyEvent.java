package model;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
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
	

	public void updateToDatabase1 (ObjectContainer dataBase){
		
		boolean flag = false;
		
		ObjectSet retrieve = dataBase.query(MyEvent.class); // created object to retrieve info from the database

		while (retrieve.hasNext()){// THERE IS AN ERROR IN THIS LOOP
			MyEvent tmp=(MyEvent) retrieve.next();
			if (this.equals(tmp)){
				tmp.setTitle(this.getTitle());
				tmp.setDescription(this.getDescription());
				flag=true;
				break;
			}
		}
		
		if (flag){
			dataBase.store(this);
			dataBase.commit();
		}else{
			System.out.println( "edit fail");
		}	
	}
	
	
	public void updateToDatabase (ObjectContainer dataBase){
		
		ObjectSet retrieve = dataBase.query(MyEvent.class); // created object to retrieve info from the database
		boolean flag = false;
		while (retrieve.hasNext()){// THERE IS AN ERROR IN THIS LOOP
			MyEvent tmp=(MyEvent) retrieve.next();
			if (this.equals(tmp)){
				dataBase.delete(tmp);
				dataBase.commit();
				flag=true;
				break;
			}
		}
		if (!flag){
			System.out.println( "delete fail");
		}
	}

}