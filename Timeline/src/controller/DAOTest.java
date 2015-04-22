package controller;

import com.db4o.*;

public class DAOTest {

	public String readData(String title) {
		ObjectContainer database = Db4o.openFile(Db4o.newConfiguration(), "testDatabase.data");
		try{
			ObjectSet<String> retrievedData = database.queryByExample(String.class);
			return retrievedData.toString();
		}
		finally {
			database.close();
		}
	}

	public void createText(String title) {
		ObjectContainer database = Db4o.openFile(Db4o.newConfiguration(), "testDatabase.data");
		try{
			database.store(title);
			database.commit();
		}
		finally{
			database.close();
		}
	}
}
