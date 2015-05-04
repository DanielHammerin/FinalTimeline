package model;

import java.util.LinkedList;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class DAO implements daoInterface { // This is the DAO or 'Data Access Object' is works as an iterator for the Database, it includes methods to save, delete and update the database among others.
	//For a better understanding of the code check out db4oBasics.java file

	public void saveToDataBase(Timeline newTimeline) throws Exception {  // This method saves object 'Timeline' in the database

		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");
		try {
			ObjectSet <Timeline> retriever = db.query(Timeline.class);
			boolean flag = false;
			while (retriever.hasNext()){
				if (newTimeline.getTitle().equalsIgnoreCase(retriever.next().getTitle())){
					flag = true;
				}
			}
			if (flag == false){ // this line checks if the Timeline is already in the database
				db.store(newTimeline);
				db.commit();	// info about these methods and more on the db4oBasics.java file inside the dbTesting package
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

	public Timeline getTimeline (String title){ // This method retrieves a specific Timeline from the database

		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");		
		try{
			Timeline aux = new Timeline(title, null) {};
			ObjectSet <Timeline> retriever = db.query(Timeline.class);
			for(int i = 0; i<retriever.size(); i++) {
				if(retriever.get(i).getTitle().equalsIgnoreCase(aux.getTitle())) {
					return retriever.get(i);
				}
			}
			return null;
		}
		finally {
			db.close();
		}

	}

	@Override
	public boolean lookUp(String title) { // This method returns true if a specific Timeline is found in the database
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");	
		try {
			ObjectSet <Timeline> retriever = db.query(Timeline.class);
			boolean flag = false;
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

	@Override
	public void deleteFromDatabase(Timeline myTimeline) { // This method deletes a specific Book from the database

		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");

		try {
			ObjectSet <Timeline> retriever = db.queryByExample(myTimeline);
			if (retriever.hasNext()){ // check if the Timeline is in the database
				db.delete(retriever.next());
				db.commit(); // info about these methods and more on db4oBasics.java file
				System.out.println ("\nMessage: The timeline has been deleted from the database!.");
			} else {
				System.out.println ("\nError!: Timeline not found in the database!.");
			}
		}	finally {
			db.close();
		}
	}

	@Override
	public void clearDatabase() { // This method deletes all the Timelines in the database

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

	@Override
	public void updateTimeline (Timeline myTimeline, String newTitle, String newDescription) { // This method updates a Book in the database with a new title and author

		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");
		boolean flag = false;

		try {
			ObjectSet <Timeline> retriever = db.queryByExample(myTimeline);

			if (retriever.hasNext()){

				retriever = db.queryByExample(new Timeline (newTitle, newDescription) {});

				if (!retriever.hasNext()){
					System.out.println ("\nMessage: " + myTimeline.getTitle() + " updated to " + newTitle + " by " + newDescription);

					db.store(new Timeline (newTitle,newDescription) {});
					db.commit();
					flag = true;

				} else {
					System.out.println ("\nError!: "+ newTitle + " is already in the the database!.");	
				}	
			} else {
				System.out.println ("\nError!: "+ myTimeline.getTitle() + " not found in the database!.");
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

	@Override
	public void printDatabase() { // This method prints out all Books currently in the database

		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data");		

		try {
			ObjectSet <Timeline> retriever = db.query(Timeline.class);

			Timeline aux = new Timeline () {};

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

	@Override
	public boolean isEmpty() { //This method return true if the database is empty
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

	public LinkedList <Timeline> getAllTimelines () { // This method retrieves an ArrayList containing all Books currently in the database

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
				return findAll;
			}

		}
		finally {
			db.close();
		}
	}
}


