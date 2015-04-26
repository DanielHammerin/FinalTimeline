package dbTesting;

//import java.util.ArrayList;

public class daoTest {

	public static void main (String[] args) {
		 
		DAO myBooks = new DAO ();
		
//		ArrayList <Book> iterator = myBooks.getAllBooks();
		
		Book catcherInTheRye = new Book ("The Catcher in the Rye", "J.D. Salinger"); // create some random books
		Book inColdBlood = new Book ("In Cold Blood", "Truman Capote");
		Book theStranger = new Book ("The Stranger", "Albert Camus");
		Book animalFarm = new Book ("Animal Farm", "George Orwell");
		Book lolita = new Book ("Lolita","Vladimir Nabokov");
		Book brothersKaramazov = new Book ("Brothers Karamazov", "Fyodor Dostoyevsky");
		Book cienAnosDeSoledad = new Book ("Cien Años de Soledad", "Gabriel García Marquez");
		Book todosLosfuegos = new Book ("Todos los Fuegos el Fuego", "Julio Cortazar");
		Book elAleph = new Book ("Aleph", "Adolfo Bioy Casares");
		Book clockWorkOrange = new Book ("A Clockwork Orange", "Anthony Burgess");
		Book sieteLocos = new Book ("Los Siete Locos","Roberto Arlt");
		
		myBooks.saveToDataBase(cienAnosDeSoledad);
		myBooks.saveToDataBase(todosLosfuegos);
		myBooks.saveToDataBase(elAleph);
		myBooks.saveToDataBase(clockWorkOrange);
		myBooks.saveToDataBase(clockWorkOrange);
		myBooks.saveToDataBase(sieteLocos);
		myBooks.saveToDataBase(brothersKaramazov);
		myBooks.saveToDataBase(catcherInTheRye);
		myBooks.saveToDataBase(lolita);
		myBooks.saveToDataBase(inColdBlood);
		myBooks.saveToDataBase(animalFarm);
		myBooks.saveToDataBase(theStranger);
		
		myBooks.printDatabase();
		
		System.out.println("");
		
//		myBooks.deleteFromDatabase(sieteLocos);
		
		myBooks.updateBook(elAleph, "El Aleph", "Jorge Luis Borges");	
//		myBooks.updateBook(sieteLocos, "Seis Locos", "Silvina Ocampo");
//		myBooks.updateBook(elAleph, "Animal Farmm", "George Orwell");
		
		myBooks.printDatabase();
		
//		myBooks.clearDatabase();

	}

}
