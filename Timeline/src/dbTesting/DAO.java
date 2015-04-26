package dbTesting;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class DAO implements daoInterface { // Data Access Object
	
	public void saveToDataBase (Book myBook) {  // saves Book into the database
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");
		try {
			
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			
			if (!retriever.hasNext()){
				db.store(myBook);
				db.commit();	
				System.out.println ("\nMessage: " + myBook.returnTitle() + " by " + myBook.returnAuthor() + " succesfully saved in the book database!.");
			} else {
				System.out.println ("\nError!: "+myBook.returnTitle() + " by " + myBook.returnAuthor() + " has already been saved in the book database!.");
			}
			
		} finally {
			db.close();
		}
	}
	
	public Book readDataBase () { // retrieves Books in the database
			
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
			
			try{
				
				ObjectSet <Book> retriever = db.query(Book.class);
				
				if (retriever.hasNext()){
					return retriever.next();
				} else {
					System.out.println ("\nMessage: " + "The database is currently empty!.");
					return null;
				}
			}
			finally {
				db.close();
			}
		}
	
	public Book getBook (Book myBook){
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");		
		
		try{
			
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			
			if (retriever.hasNext()){
				return retriever.next();
				
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
	public boolean lookUp (Book myBook) {
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
	public void deleteFromDatabase(Book myBook) {
		
		ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data");
		
		try {
			
			ObjectSet <Book> retriever = db.queryByExample(myBook);
			
			if (retriever.hasNext()){
			
				db.delete(retriever.next());
				db.commit();
				System.out.println ("\nMessage: " + myBook.returnTitle() + " by " + myBook.returnAuthor() + " has been deleted from the book database!.");
				
			} else {
				System.out.println ("\nError!: "+ myBook.returnTitle() + " by " + myBook.returnAuthor() + " not found in the book database!.");
			}
		}	finally {
			db.close();
		}
	}

	@Override
	public void clearDatabase() {
		
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
	public void updateBook(Book myBook, String newTitle, String newAuthor) {
		
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
	public void printDatabase() {
		
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
}

