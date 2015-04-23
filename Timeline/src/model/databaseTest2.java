package model;

import java.util.Date;
import java.util.GregorianCalendar;

import com.db4o.*;

public class databaseTest2 {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		ObjectContainer  db = Db4o.openFile(Db4o.newConfiguration(), "timelineDatabase.data"); // open file in the object
		try
		{	
			GregorianCalendar sol = new GregorianCalendar();
			sol.setTime(new Date(1997, 10, 10));
			GregorianCalendar sol1 = new GregorianCalendar();
			sol1.setTime(new Date(1999, 10, 10));
			
			YearTimeline str = new YearTimeline("Hatem", "fed", sol, sol1);
			db.store(str);
			db.commit();
			ObjectSet<YearTimeline> retrieve = db.queryByExample(str);
			System.out.println (retrieve.toString());
		}

		finally
		{
			if (db != null)
				db.close(); 
		}
	}
}
