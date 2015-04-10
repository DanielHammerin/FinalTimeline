package model;

import java.io.File;

import com.db4o.*;

public class databaseTest {

	public static void main(String[] args) {

	        new File (".", "persons.data").delete();
	        
	        ObjectContainer db = null;
	        
	        try
	        {
	            db = Db4o.openFile("persons.data");
	
				EventNT firstSemester = new EventNT ("First Semester", "First Semerster for Software Technology Program");
				EventNT secondSemester = new EventNT ("Second Semester", "Second Semerster for Software Technology Program");
				EventNT thirdSemester = new EventNT ("Third Semester", "Third Semerster for Software Technology Program");
				EventNT fourthSemester = new EventNT ("Fourth Semester", "Fourth Semerster for Software Technology Program");

				
				db.store(firstSemester);
				db.store(secondSemester);
				db.store(thirdSemester);
				db.store(fourthSemester);
				
				db.commit();
	        }
	        
	        finally
	        
	        {
	            if (db != null)
	                db.close(); 
	        }
	    }
	}