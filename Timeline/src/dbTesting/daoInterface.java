package dbTesting;

import java.util.ArrayList;
public interface daoInterface {
	
	public void saveToDataBase (Book myBook); // saves Book in the database
	public void deleteFromDatabase (Book myBook); // deletes Book from the database
	public boolean lookUp (Book myBook); // returns true if the Book is found in the database
	public Book getBook (Book myBook); // retrieves a specific book from the database
	public void updateBook (Book myBook, String newTitle, String newAuthor); // updates book info and saves is to the database
	public void printDatabase (); // prints out all books in the database
	public void clearDatabase (); // deletes all books in the database
	public boolean isEmpty (); // returns true is the database is currently empty
	public ArrayList <Book> getAllBooks (); // Retrieves ArrayList containing all books in the database
	
}