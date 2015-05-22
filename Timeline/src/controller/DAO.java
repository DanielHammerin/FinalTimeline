package controller;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.TreeSet;

import model.*;
import com.db4o.*;

/**
 * A database access object class to connect between the view, controller and database.
 * @author Mauro & Hatem
 *
 */
public class DAO implements daoInterface {
	/**
	 * Database access object constructor.
	 */
	public DAO() {}
	/**
	 * Saves the timeline in the database.
	 * @param newTimeline timeline to be added.
	 * @exception Exception for opening or creating the database file.
	 */
	public void saveToDataBase(Timeline newTimeline) throws Exception {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet <Timeline> retriever = db.query(Timeline.class);
			boolean flag = false;
			//Checks that there isn't any timelines in the database with the same title.
			while (retriever.hasNext()){
				if (newTimeline.getTitle().equalsIgnoreCase(retriever.next().getTitle())){
					flag = true;
				}
			}
			if (flag == false){ // this line checks if the Timeline is already in the database
				db.store(newTimeline);
				db.commit();
				System.out.println ("\nMessage: Month timeline is succesfully saved in the database!");
			} else {
				throw new Exception("A timeline with the same title already exists! Please change your "
						+ "timeline title.");
			}
		}
		finally {
			db.close();
		}
	}
	public void saveV2(DayTimeline timeline) throws Exception{
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
				if(timeline.getEventNTs().size() != 0) {
					for (EventNT eventNT : timeline.getEventNTs()) {
						eventNT.setStartDay((eventNT.getDate().get(Calendar.DAY_OF_MONTH)));
						eventNT.setStartMonth((eventNT.getDate().get(Calendar.MONTH)));
						eventNT.setStartYear(((eventNT.getDate().get(Calendar.YEAR))));
					}
				}
				if(timeline.getEventTimes().size() != 0) {
					for (EventTime eventTime : timeline.getEventTimes()) {
						eventTime.setStartDay((eventTime.getStartTime().get(Calendar.DAY_OF_MONTH)));
						eventTime.setStartMonth((eventTime.getStartTime().get(Calendar.MONTH)));
						eventTime.setStartYear(((eventTime.getStartTime().get(Calendar.YEAR))));
						eventTime.setEndDay((eventTime.getFinishTime().get(Calendar.DAY_OF_MONTH)));
						eventTime.setEndMonth((eventTime.getFinishTime().get(Calendar.MONTH)));
						eventTime.setEndYear(((eventTime.getFinishTime().get(Calendar.YEAR))));
					}
				}

			db.store(timeline);
			db.commit();
			System.out.println ("\nMessage: The timeline is succesfully saved in the database!");
		}
		finally {
			db.close();
		}
	}
	/**
	 * Gets the timeline from the database.
	 * @param title title of the timeline to be retrieved.
	 * @return The required timeline.
	 * @throws Exception
	 */
	public Timeline getTimeline(String title) throws Exception {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try{
			Timeline aux = new Timeline(title, "", ""){};  //Creates an auxiliary timeline with the required title.
			ObjectSet<Timeline> retriever = db.query(Timeline.class); //Puts all the timelines from the database in an ObjectSet.
			//Searches in the ObjectSet for the timeline with the same title.
			for(int i = 0; i<retriever.size(); i++) {
				if(retriever.get(i).getTitle().equalsIgnoreCase(aux.getTitle())) {
					return retriever.get(i);
				}
			}
			throw new Exception("There are no timelines with this title.");
		}
		finally {
			db.close();
		}
	}
	public DayTimeline getDayTimeline(String title) throws Exception {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try{
			DayTimeline aux = new DayTimeline(title);  //Creates an auxiliary timeline with the required title.
			ObjectSet<DayTimeline> retriever = db.queryByExample(DayTimeline.class); //Puts all the timelines from the database in an ObjectSet.
			//Searches in the ObjectSet for the timeline with the same title.
			for(int i = 0; i<retriever.size(); i++) {
				if(retriever.get(i).getTitle().equalsIgnoreCase(aux.getTitle())) {
					DayTimeline day = retriever.get(i);
					int startMonth = day.getStartMonth();
					GregorianCalendar gregorianStart = new GregorianCalendar(day.getStartYear(), day.getStartMonth(), day.getStartDay());
					GregorianCalendar gregorianEnd = new GregorianCalendar(day.getEndYear(), day.getEndMonth(), day.getEndDay());
					DayTimeline newDay = new DayTimeline(day.getTitle(), day.getDescription(), gregorianStart, gregorianEnd);
					if(retriever.get(i).getEventNTs().size() != 0) {
						for (EventNT eventNT : retriever.get(i).getEventNTs()) {
							eventNT.setDateOfEvent(new GregorianCalendar(eventNT.getStartYear(), eventNT.getStartMonth(), eventNT.getStartDay()));
						}
					}
					if(retriever.get(i).getEventTimes().size() != 0) {
						for (EventTime eventTime : retriever.get(i).getEventTimes()) {
							eventTime.setStartTime(new GregorianCalendar(eventTime.getStartYear(), eventTime.getStartMonth(), eventTime.getStartDay()));
							eventTime.setFinishTime(new GregorianCalendar(eventTime.getEndYear(), eventTime.getEndMonth(), eventTime.getEndDay()));
						}
					}
					return newDay;
				}
			}
			throw new Exception("There are no timelines with this title.");
		}
		finally {
			db.close();
		}
	}
	/**
	 * Searches the database for a specific timeline.
	 * @param title title of the timeline to be retrieved.
	 * @return true if the timeline exists in the database.
	 */
	@Override
	public boolean lookUp(String title) {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet <Timeline> retriever = db.query(Timeline.class); //Puts all the timelines from the database in an ObjectSet.
			boolean flag = false;
			//Searches in the ObjectSet for the timeline with the same title.
			while (retriever.hasNext()){
				if (title.equalsIgnoreCase(retriever.next().getTitle())){
					flag = true;
				}
			}
			return flag;
		}
		finally {
			db.close();
		}
	}
	/**
	 * Deletes a specific timeline from the database.
	 * @param myTimeline timeline to be deleted.
	 */
	@Override
	public void deleteFromDatabase(Timeline myTimeline) {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet <Timeline> retriever = db.queryByExample(myTimeline);
			if (retriever.hasNext()){ // check if the Timeline is in the database
				db.delete(retriever.next());
				db.commit(); // info about these methods and more on db4oBasics.java file
				System.out.println ("\nMessage: The timeline has been deleted from the database!");
			} else {
				System.out.println ("\nError!: Timeline not found in the database!.");
			}
		}  finally {
			db.close();
		}
	}
	/**
	 * Clears the database completely.
	 */
	@Override
	public void clearDatabase() {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet <DayTimeline> retriever = db.queryByExample(DayTimeline.class);
			while (retriever.hasNext()){
				db.delete(retriever.next());
			}
			db.commit();
			System.out.println ("\nMessage: All timelines have been deleted from the database!");
		}
		finally {
			db.close();
		}
	}
	/**
	 * Updates a specific timeline.
	 * @param newTitle timeline to be updated, its title and description.
	 * @throws Exception
	 */
	@Override
	public void updateTimeline (Timeline myTimeline, String newTitle, String newDescription, String typeOfTimeline) throws Exception {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		boolean flag = false;
		try {
			ObjectSet <Timeline> retriever = db.queryByExample(myTimeline);
			if (retriever.hasNext()){
				Timeline updatedTimeline = new Timeline(newTitle, newDescription, typeOfTimeline) {};
				retriever = db.queryByExample(updatedTimeline);
				if (!retriever.hasNext()){
					System.out.println ("\nMessage: " + myTimeline.getTitle() + " updated to " + newTitle +
							" by " + newDescription);
					db.store(updatedTimeline);
					db.commit();
					flag = true;
				} else {
					throw new Exception(newTitle + " is already in the the database!");
				}
			} else {
				throw new Exception(myTimeline.getTitle() + " not found in the database!");
			}
			if (flag){
			}
			retriever = db.queryByExample(myTimeline);
			if (retriever.hasNext()){ // check if the Timeline is in the database
				db.delete(retriever.next());
				db.commit(); // info about this methods and more on db4oBasics.java file inside dbTesting package
			}
		}
		finally {
			db.close();
		}
	}

	public void updateTimelineV2(DayTimeline oldTimeline, DayTimeline newTimeline) {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet<DayTimeline> retriever = db.queryByExample(DayTimeline.class);
			//Searches in the ObjectSet for the timeline with the same title.
			for(int i = 0; i<retriever.size(); i++) {
				if (retriever.get(i).getTitle().equalsIgnoreCase(oldTimeline.getTitle())) {

					db.delete(retriever.next());
					db.commit();
				}
				if (newTimeline.getEventNTs().size() != 0) {
					for (EventNT eventNT : newTimeline.getEventNTs()) {
						eventNT.setStartDay((eventNT.getDate().get(Calendar.DAY_OF_MONTH)));
						eventNT.setStartMonth((eventNT.getDate().get(Calendar.MONTH)));
						eventNT.setStartYear(((eventNT.getDate().get(Calendar.YEAR))));
					}
				}
				if (newTimeline.getEventTimes().size() != 0) {
					for (EventTime eventTime : newTimeline.getEventTimes()) {
						eventTime.setStartDay((eventTime.getStartTime().get(Calendar.DAY_OF_MONTH)));
						eventTime.setStartMonth((eventTime.getStartTime().get(Calendar.MONTH)));
						eventTime.setStartYear(((eventTime.getStartTime().get(Calendar.YEAR))));
						eventTime.setEndDay((eventTime.getFinishTime().get(Calendar.DAY_OF_MONTH)));
						eventTime.setEndMonth((eventTime.getFinishTime().get(Calendar.MONTH)));
						eventTime.setEndYear(((eventTime.getFinishTime().get(Calendar.YEAR))));
					}
				}
			}
			db.store(newTimeline);
			db.commit();
			System.out.println("The timeline is updated.");
		}
		finally {
			db.close();
		}
	}
	/**
	 * Prints out all the timelines in the database.
	 */
	@Override
	public void printDatabase() {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet <Timeline> retriever = db.query(Timeline.class);
			Timeline aux = new Timeline() {};
			if (!retriever.hasNext()){
				System.out.println ("\nMessage: The database is currently empty!");
			} else {
				System.out.println ("\nLIST OF TIMELINES IN THE DATABASE");
				while (retriever.hasNext()){
					aux = retriever.next();
					if(aux.isDayTimeline()){
						DayTimeline auxDay = (DayTimeline) aux;
						System.out.println (auxDay.getTitle() + ": " + auxDay.getDescription() +", Start date: "
								+auxDay.getStartDate()+", End date: "+auxDay.getEndDate());
					}
					else if(aux.isMonthTimeline()){
						MonthTimeline auxDay = (MonthTimeline) aux;
						System.out.println (auxDay.getTitle() + ": " + auxDay.getDescription() +", Start date: "
								+auxDay.getStartMonth()+", End date: "+auxDay.getEndMonth());
					}
					else if(aux.isYearTimeline()){
						YearTimeline auxDay = (YearTimeline) aux;
						System.out.println (auxDay.getTitle() + ": " + auxDay.getDescription() +", Start date: "
								+auxDay.getStartYear()+", End date: "+auxDay.getEndYear());
					}
				}
			}
		}
		finally {
			db.close();
		}
	}
	/**
	 * Returns all the timelines in the database.
	 * @return a linked list of timelines.
	 */
// public LinkedList <DayTimeline> getAllTimelines() {
//
//    LinkedList <DayTimeline> findAll = new LinkedList <DayTimeline> ();
//    ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
//
//    try{
//       ObjectSet <DayTimeline> retriever = db.query(DayTimeline.class);
//       if (retriever.hasNext()){ // check if there're any Timelines to retrieve
//          while (retriever.hasNext()){ // retrieves all Timelines in the database
//                findAll.add(retriever.next());
//          }
//          return findAll;
//
//       } else {
//          System.out.println ("\nMessage: " + "The database is currently empty!.");
//          return null;
//       }
//    }
//    finally {
//       db.close();
//    }
// }
	public LinkedList<DayTimeline> getAllTimelines() {
		LinkedList <DayTimeline> findAll = new LinkedList <DayTimeline> ();
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet<DayTimeline> retriever = db.query(DayTimeline.class);
			if (retriever.hasNext()) { // check if there're any Timelines to retrieve
				while (retriever.hasNext()) { // retrieves all Timelines in the database

					DayTimeline day = retriever.next();
					if (day.getEventNTs().size() != 0) {
						for (EventNT eventNT : day.getEventNTs()) {
							eventNT.setDateOfEvent(new GregorianCalendar(eventNT.getStartYear(), eventNT.getStartMonth(), eventNT.getStartDay()));
						}
					}

					if (day.getEventTimes().size() != 0) {
						for (EventTime eventTime : day.getEventTimes()) {
							eventTime.setStartTime(new GregorianCalendar(eventTime.getStartYear(), eventTime.getStartMonth(), eventTime.getStartDay()));
							eventTime.setFinishTime(new GregorianCalendar(eventTime.getEndYear(), eventTime.getEndMonth(), eventTime.getEndDay()));
						}
					}
					GregorianCalendar gregorianStart = new GregorianCalendar(day.getStartYear(), day.getStartMonth(), day.getStartDay());
					GregorianCalendar gregorianEnd = new GregorianCalendar(day.getEndYear(), day.getEndMonth(), day.getEndDay());
					DayTimeline newDay = new DayTimeline(day.getTitle(), day.getDescription(), gregorianStart, gregorianEnd);
					findAll.add(newDay);
				}
					return findAll;
				}else{
					System.out.println("\nMessage: " + "The database is currently empty!.");
					return null;
				}

		}
		finally {
			db.close();
		}

	}
	/**
	 * Checks whether the database is empty.
	 * @return true if the database is empty.
	 */
	public boolean isEmpty() {
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try{
			ObjectSet <Timeline> retriever = db.query(Timeline.class);
			return !retriever.hasNext();
		}
		finally {
			db.close();
		}
	}
}