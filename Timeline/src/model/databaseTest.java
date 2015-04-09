package model;

import java.io.File;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

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
				
				db.set(firstSemester);
				db.set(secondSemester);
				db.set(thirdSemester);
	        }
	        finally
	        {
	            if (db != null)
	                db.close(); 
	        }
	    }
	}