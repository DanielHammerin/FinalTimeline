package model;

import java.util.LinkedList;
/**
 * An interface for the DAO database access object class.
 * @author Mauro & Hatem
 */
public interface daoInterface {
	
	void saveToDataBase(Timeline newTimeline) throws Exception; // saves Timeline in the database
	void deleteFromDatabase(Timeline myTimeline); // deletes Timeline from the database
	boolean lookUp(String title); // returns true if the Timeline is found in the database
	Timeline getTimeline(String title) throws Exception; // retrieves a specific Timeline from the database
	void updateTimeline(Timeline myTimeline, String newTitle, String newDescription, String typeOfTimeline) throws Exception; // updates Timeline info and saves is to the database
	void printDatabase(); // prints out all Timelines in the database
	void clearDatabase(); // deletes all Timelines in the database
	boolean isEmpty(); // returns true is the database is currently empty
	LinkedList<Timeline> getAllTimelines(); // Retrieves LInkedList containing all Timelines in the database
	
}