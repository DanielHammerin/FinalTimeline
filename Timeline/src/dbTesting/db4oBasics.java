package dbTesting;
import java.io.File;

import com.db4o.*;

public class db4oBasics { // Hello my name is Johnny Knoxville and this is db40 101
	
	public static void main(String[] args) {
	
    new File (".", "newDatabase.data").delete(); // creates the .data file
    
    ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data"); // open file in the object container, this is how we iterate with the database. It will be replaced by the class DAO methods but it is good that you know how it works in case that Oskar asks
	
		try { // the infamous try-catch error handling part
			
			Book catcherInTheRye = new Book ("The Catcher in the Rye", "William Faulkner"); // create some random books
			Book inColdBlood = new Book ("In Cold Blood", "Truman Capote");
			Book theStranger = new Book ("The Stranger", "Albert Camus");
			Book animalFarm = new Book ("Animal Farm", "George Orwell");
			Book lolita = new Book ("Lolita","Vladimir Nabokov");
			Book brothersKaramazov = new Book ("Brothers Karamazov", "Fyodor Dostoyevsky");
			
			db.store(catcherInTheRye); // add object to database
			db.store(inColdBlood);
			db.store(theStranger);
			db.store(animalFarm);
			db.store(lolita);
			db.store(brothersKaramazov);
			db.commit(); // saves changes in the database, this is very important!
			
			db.delete(brothersKaramazov); // deletes object from database
			db.commit(); // saves changes in the database, again very important!

			ObjectSet <Book> retriever = db.query(Book.class); // retrieves objects of a specific Class determined in the parameters, in this case 'Book'
			
			while (retriever.hasNext()){						// let's test the retriever
				System.out.println (retriever.next().returnTitle());
			}
			
			retriever = db.queryByExample(inColdBlood); // retrieves an specific Object determined in the parameters, in this case 'inColdBlood'
			System.out.println ("\n" + retriever.next().returnAuthor());
			
			 												// How to update an object in the database:
			retriever = db.queryByExample(catcherInTheRye); // first we retrieve the object we want to update
			retriever.next().setAuthor("J.D. Salinger"); // we now modify the object using it's private methods in this case setAuthor(String)
			
			db.store(catcherInTheRye); // just save the same object again and db40 will overwrite it in the database, saving all the changes
			db.commit(); // once again very important to commit changes
			
			System.out.println("");
			
			retriever = db.query(Book.class); // let's try this out, Notice: it very is important to reset the retriever in order to get all books again, I don't know if there is a better way to do this but it works!.
			
			while (retriever.hasNext()){ // let's retrieve that sweet sweet database, oh yea
				System.out.println (retriever.next().returnAuthor());
			}
			
			retriever = db.queryByExample(catcherInTheRye); // let's double check that the object has been updated properly
			System.out.println ("\n" + retriever.next().returnAuthor());
	
		} finally {
		db.close(); // Always close the database file at the end, this is very important!.
		}
	}

} // Ok I think that would be everything you will need to know about db4o for now, this is just the basics of db4o so everyone is in the known, remember that we will not use this methods but the methods from the class DAO.
