package controller;
/**
 * This is an interface for the database access object implementation.
 */
import model.Timeline;
import com.db4o.*;

public interface TimelineDao {

	public void addData();	//Saves all the data in the database.
	public ObjectSet<Timeline> getData();	//Retrieves all the data from the database.
	public void deleteAll();	//Deletes all the data in the database.
	public void deleteThisData(ObjectSet<Timeline> data);	//Deletes a specific timeline from the database.
}
