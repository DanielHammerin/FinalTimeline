package model;

import java.util.LinkedList;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

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
	 * @param The timeline to be added.
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
				System.out.println ("\nMessage: Timeline is succesfully saved in the database!");
			} else {
				throw new Exception("A timeline with the same title already exists! Please change your "
						+ "timeline title.");
			}
		}
		finally {
			db.close();
		}
	}

	/**
	 * Gets the timeline from the database.
	 * @param The title of the timeline to be retrieved.
	 * @return The required timeline.
	 * @throws Exception 
	 */
	public Timeline getTimeline(String title) throws Exception {
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");		
		try{
			Timeline aux = new Timeline(title, "", ""){};	//Creates an auxiliary timeline with the required title.
			ObjectSet <Timeline> retriever = db.query(Timeline.class); //Puts all the timelines from the database in an ObjectSet.
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

	/**
	 * Searches the database for a specific timeline.
	 * @param The title of the timeline to be retrieved.
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
	 * @param The timeline to be deleted.
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
		}	finally {
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
			ObjectSet <Timeline> retriever = db.query(Timeline.class);

			while (retriever.hasNext()){
				db.delete(retriever.next());
			}
			System.out.println ("\nMessage: All timelines have been deleted from the database!");
		}
		finally {
			db.close();
		}
	}

	/**
	 * Updates a specific timeline.
	 * @param The timeline to be updated, its title and description. 
	 * @throws Exception 
	 */
	@Override
	public void updateTimeline (Timeline myTimeline, String newTitle, String newDescription, String typeOfTimeline) throws Exception {

		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");
		boolean flag = false;

		try {
			ObjectSet <Timeline> retriever = db.queryByExample(myTimeline);

			if (retriever.hasNext()){
				Timeline updatedTimeline = new Timeline(newTitle, newDescription, typeOfTimeline) {};
				retriever = db.queryByExample(updatedTimeline);

				if (!retriever.hasNext()){
					System.out.println ("\nMessage: " + myTimeline.getTitle() + " updated to " + newTitle + " by " + newDescription);

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
					System.out.println (aux.getTitle() + ": " + aux.getDescription());
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
	public LinkedList <Timeline> getAllTimelines() { 

		LinkedList <Timeline> findAll = new LinkedList <Timeline> ();
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");		

		try{
			ObjectSet <Timeline> retriever = db.query(Timeline.class);

			if (retriever.hasNext()){ // check if there're any Timelines to retrieve
				while (retriever.hasNext()){ // retrieves all Timelines in the database
					findAll.add(retriever.next());
				}
				return findAll;

			} else {
				System.out.println ("\nMessage: " + "The database is currently empty!.");
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
			if (retriever.hasNext()){ // check if the database is empty
				return false; 
			} else {
				return true;
			}
		}
		finally {
			db.close();
		}
	}
}


