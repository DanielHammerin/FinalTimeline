package controller;
/**
 * This is an interface for the database access object implementation.
 */

import java.util.ArrayList;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import model.MyEvent;
import model.Timeline;

public interface DaoInterface {

	public void addData (ObjectContainer database ,Timeline addTimeline);	//Saves all the data in the database.
	public ArrayList<ObjectSet<Timeline>> getTimeline(String title);	//Retrieves all the data from the database.
	public void deleteAll();	//Deletes all the data in the database.
	public void deleteTimeline(Timeline data);	//Deletes a specific timeline from the database.
	public void deleteEvent(MyEvent data);	//Deletes a specific event from the database.
}
