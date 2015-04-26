package dbTesting;

public interface daoInterface {
	
	public void saveToDataBase (Book myBook); // saves Book into the database
	public void deleteFromDatabase (Book myBook); // deletes a Book from the database
	public Book readDataBase (); // retrieves Books in the database
	public boolean lookUp (Book myBook); // return true if the Book is in the database
	public Book getBook (Book myBook); // retrieves a specific book in the database
	public void updateBook (Book myBook, String newTitle, String newAuthor); // updates book info and saves is to the database
	public void printDatabase (); // prints out all books in the database
	public void clearDatabase (); // deletes all books in the database
	
}
