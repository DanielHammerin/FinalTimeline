package dbTesting;
import java.io.File;

import com.db4o.*;

public class db4oBasics {
	
	public static void main(String[] args) {
	
    new File (".", "newDatabase.data").delete(); // creates the .data file
    
    ObjectContainer db = Db4o.openFile(Db4o.newConfiguration(), "newDatabase.data"); // open file in the object
	
		try {
			
			Book catcherInTheRye = new Book ("The Catcher in the Rye", "William Faulkner");
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
			db.commit(); // saves changes
			
			db.delete(brothersKaramazov); // deletes object from database
			db.commit(); // saves changes

			ObjectSet <Book> retriever = db.query(Book.class); // retrieves objects of a specific Class
			
			while (retriever.hasNext()){						// test
				System.out.println (retriever.next().returnTitle());
			}
			
			retriever = db.queryByExample(inColdBlood); // retrieves an specific Object
			System.out.println ("\n" + retriever.next().returnAuthor());
			
			retriever = db.queryByExample(catcherInTheRye); // update an object in the database
			retriever.next().setAuthor("J.D. Salinger"); // we now modify the object in the database
			
			db.store(catcherInTheRye); // just save the same object again and db40 will overwrite it saving the changes
			db.commit();
			
			System.out.println("");
			
			retriever = db.query(Book.class);
			
			while (retriever.hasNext()){						// test
				System.out.println (retriever.next().returnAuthor());
			}
			
			retriever = db.queryByExample(catcherInTheRye); // retrieves an specific Object
			System.out.println ("\n" + retriever.next().returnAuthor());
	
		} finally {
		db.close();
		}
	}

}
