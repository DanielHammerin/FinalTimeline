package model;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import javafx.scene.layout.StackPane;

public abstract class MyEvent {
	
	String title = "";
	String description = "";
	StackPane geometricFigure;
	
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
	public void deleteEvent(String title,String description){
		
	}
	public void editEvent(String title,String description){
		
	}

	public StackPane getGeometricFigure() {
		return geometricFigure;
	}

	public void setGeometricFigure(StackPane geometricFigure) {
		this.geometricFigure = geometricFigure;
	}
	public boolean equals(Object obj) {
		//there are compare rules
		return true;
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

//	public void updateToDatabase (ObjectContainer dataBase){
//		
//		boolean flag = false;
//		
//		ObjectSet retrieve = dataBase.query(MyEvent.class); // created object to retrieve info from the database
//
//		while (retrieve.hasNext()){// THERE IS AN ERROR IN THIS LOOP
//			MyEvent tmp=retrieve.next();
//			if (this.equals(tmp)){
//				tmp.setTitle(this.getTitle());
//				tmp.setDescription(this.getDescription());
//				flag=true;
//				this=tmp;
//				break;
//			}
//		}
//		
//		if (flag){
//			dataBase.store(this);
//			dataBase.commit();
//		}else{
//			System.out.println( "edit fail");
//		}
//		
//		
//	}
//	
//	public void updateToDatabase (ObjectContainer dataBase){
//		
//		ObjectSet retrieve = dataBase.query(MyEvent.class); // created object to retrieve info from the database
//		boolean flag = false;
//		while (retrieve.hasNext()){// THERE IS AN ERROR IN THIS LOOP
//			MyEvent tmp=retrieve.next();
//			if (this.equals(tmp)){
//				dataBase.delete(tmp);
//				dataBase.commit();
//				flag=true;
//				break;
//			}
//		}
//		if (!flag){
//			System.out.println( "delete fail!");
//		}
//	}
//}

}
