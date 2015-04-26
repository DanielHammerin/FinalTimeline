package dbTesting;

import java.util.ArrayList;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class DAO implements daoInterface { // This is the DAO or 'Data Access Object' is works as an iterator for the Database, it includes methods to save, delete and update the database among others.
										  //For a better understanding of the code check out db4oBasics.java file
	
	public void saveToDataBase (Book myBook) {  // This method saves object 'Book' to the database
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");
		try {
			
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			
			if (!retriever.hasNext()){ // this line check if the objects is already in the database
				db.store(myBook);
				db.commit();	// info about this methods and more on db4oBasics.java file
				System.out.println ("\nMessage: " + myBook.returnTitle() + " by " + myBook.returnAuthor() + " succesfully saved in the book database!.");
			} else {
				System.out.println ("\nError!: "+myBook.returnTitle() + " by " + myBook.returnAuthor() + " has already been saved in the book database!.");
			}
			
		} finally {
			db.close();
		}
	}
	
	public Book getBook (Book myBook){ // This method retrieves a specific Book from the database
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try{
			
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			
			if (retriever.hasNext()){ // check if the book is in the database
				return retriever.next(); // retrieves book
				
			} else {
				System.out.println ("\nError!: "+myBook.returnTitle() + " by " + myBook.returnAuthor() + " not found in the book database!.");
				return null;
			}
		}
		finally {
			db.close();
		}
		
	}
	
	@Override
	public boolean lookUp (Book myBook) { // This method returns true if a specific Book is found in the database
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try {
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			if (retriever.hasNext()){
				return true;
			} else {
				return false;
			}
		}
		finally {
			db.close();
		}
	}

	@Override
	public void deleteFromDatabase(Book myBook) { // This method deletes a specific Book from the database
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");
		
		try {
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			if (retriever.hasNext()){ // check if the book is on the database
				db.delete(retriever.next());
				db.commit(); // info about this methods and more on db4oBasics.java file
				System.out.println ("\nMessage: " + myBook.returnTitle() + " by " + myBook.returnAuthor() + " has been deleted from the book database!.");
			} else {
				System.out.println ("\nError!: "+ myBook.returnTitle() + " by " + myBook.returnAuthor() + " not found in the book database!.");
			}
		}	finally {
			db.close();
		}
	}

	@Override
	public void clearDatabase() { // This method deletes all Books in the database
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try {
			ObjectSet <Book> retriever = db.query(Book.class);
			
			while (retriever.hasNext()){
				db.delete(retriever.next());
			}
			System.out.println ("\nMessage: All Books have been deleted from the database!");
		}
		finally {
			db.close();
		}
	}

	@Override
	public void updateBook(Book myBook, String newTitle, String newAuthor) { // This method updates a Book in the database with new title and author
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try {
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			
			if (retriever.hasNext()){
				
				System.out.println ("\nMessage: " + myBook.returnTitle() + " by " + myBook.returnAuthor() + " updated to " + newTitle + " by " + newAuthor);
				
				myBook.setAuthor(newAuthor);
				myBook.setTitle(newTitle);
				
				db.store(myBook);
				db.commit();
				
			} else {
				System.out.println ("\nError!: "+ myBook.returnTitle() + " by " + myBook.returnAuthor() + " not found in the book database!.");
			}
		}
		finally {
			db.close();
		}
	}

	@Override
	public void printDatabase() { // This method prints out all Books currently in the database
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try {
			ObjectSet <Book> retriever = db.query(Book.class);
			
			Book aux = new Book ();
			
			if (!retriever.hasNext()){
				System.out.println ("\nMessage: The database is currently empty!");
			} else {
				System.out.println ("\nMY BOOKS");

				while (retriever.hasNext()){
					aux = retriever.next();
					System.out.println (aux.returnTitle() + " by " + aux.returnAuthor());
				}
			}
		}
		finally {
			db.close();
		}
	}

	@Override
	public boolean isEmpty() { //This method return true if the database is empty
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try{
			ObjectSet <Book> retriever = db.query(Book.class);
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
	
	public ArrayList <Book> getAllBooks () { // This method retrieves Books from the database
		
		ArrayList <Book> findAll = new ArrayList <Book> ();
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
			
			try{
				ObjectSet <Book> retriever = db.query(Book.class);
				
				if (retriever.hasNext()){ // check if there's an Object to retrieve
					 while (retriever.hasNext()){
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


