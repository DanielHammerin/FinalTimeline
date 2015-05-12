package dbTesting;

import java.util.ArrayList;
public interface daoInterface {
	
	void saveToDataBase(Book myBook); // saves Book in the database
	void deleteFromDatabase(Book myBook); // deletes Book from the database
	boolean lookUp(Book myBook); // returns true if the Book is found in the database
	Book getBook(Book myBook); // retrieves a specific book from the database
	void updateBook(Book myBook, String newTitle, String newAuthor); // updates book info and saves is to the database
	void printDatabase(); // prints out all books in the database
	void clearDatabase(); // deletes all books in the database
	boolean isEmpty(); // returns true is the database is currently empty
	ArrayList <Book> getAllBooks(); // Retrieves ArrayList containing all books in the database
	
}