package controller;

import java.util.LinkedList;
import model.DayTimeline;
import model.MonthTimeline;
import model.Timeline;
import model.YearTimeline;

/**
 * An interface for the DAO database access object class.
 * @author Mauro & Hatem
 */
public interface daoInterface {
	
	public void saveToDataBase(Timeline newTimeline) throws Exception; // saves Timeline in the database
	public void deleteFromDatabase(Timeline myTimeline); // deletes Timeline from the database
	public boolean lookUp(String title); // returns true if the Timeline is found in the database
	public YearTimeline getYearTimeline(String title) throws Exception; // retrieves a specific Timeline from the database
	public MonthTimeline getMonthTimeline(String title) throws Exception;
	public DayTimeline getDayTimeline(String title) throws Exception;
	public void updateTimeline(Timeline myTimeline, String newTitle, String newDescription, String typeOfTimeline) throws Exception; //updates Timeline info and saves is to the database
	public void printDatabase(); // prints out all Timelines in the database
	public void clearDatabase(); // deletes all Timelines in the database
	public boolean isEmpty(); // returns true is the database is currently empty
	public LinkedList<Timeline> getAllTimelines(); // Retrieves LInkedList containing all Timelines in the database
	public void updateTimelineV2(Timeline oldTimeline, Timeline newTimeline); //Updates a specific timeline.
}