package model;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class databaseTest2 {

	public static void main(String[] args) {
		   ObjectContainer db = null; // creates the object data base
	        
	        try
	        {
	            db = Db4o.openFile("yoloBase420.data"); // open file in the object
				
				ObjectSet retrieve = db.query(EventNT.class);
				
				while (retrieve.hasNext()){
					System.out.println (retrieve.next().toString());
								
				}
				
	        }
	        
	        finally
	        
	        {
	            if (db != null)
	                db.close(); 
	        }
	    }

	}
