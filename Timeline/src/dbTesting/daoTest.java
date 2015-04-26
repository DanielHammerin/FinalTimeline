package dbTesting;

public class daoTest {

	public static void main(String[] args) {
		
//	    new File (".", "newDatabase.data").delete(); // creates the .data file
	    
	    DAO myBooks = new DAO ();
	    
		Book cienAnosDeSoledad = new Book ("Cien Años de Soledad", "Gabriel García Marquez");
		Book todosLosfuegos = new Book ("Todos los Fuegos el Fuego", "Julio Cortazar");
		Book elAleph = new Book ("Aleph", "Adolfo Bioy Casares");
		Book amorLocuraMuerte = new Book ("Cuentos de Amor, Locura y Muerte", "Horacio Quiroga");
		Book sieteLocos = new Book ("Los Siete Locos","Roberto Arlt");
		
		myBooks.saveToDataBase(cienAnosDeSoledad);
		myBooks.saveToDataBase(todosLosfuegos);
		myBooks.saveToDataBase(elAleph);
		myBooks.saveToDataBase(amorLocuraMuerte);
		myBooks.saveToDataBase(amorLocuraMuerte);
		myBooks.saveToDataBase(sieteLocos);
		
		myBooks.printDatabase();
		
		myBooks.deleteFromDatabase(sieteLocos);
		myBooks.deleteFromDatabase(sieteLocos);

		
		myBooks.printDatabase();
		
		myBooks.updateBook(sieteLocos, "Los Seis Locos", "Roberto Arlt");
		
		myBooks.updateBook(elAleph, "El Aleph", "Jorge Luis Borges");
		
		myBooks.printDatabase();

		
//		myBooks.clearDatabase();
//		myBooks.printDatabase();


	}

}
