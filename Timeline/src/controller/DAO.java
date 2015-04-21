package controller;
/** This class is the implementation of the database access object implementation **/

import java.util.ArrayList;
import java.util.Iterator;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import model.MyEvent;
import model.Timeline;

public class DAO implements DaoInterface { // Data Access Objects

	private ArrayList<Timeline> timelines;

	
	@Override
	public void addData (ObjectContainer database, Timeline newTimeline) {

		//		boolean flag = true;
		//
		//		//		ObjectSet<Timeline> retrieve = database.query (Timeline.class); // created object to retrieve info from the database
		//
		//		//		while (retrieve.hasNext()){// THERE IS AN ERROR IN THIS LOOP
		//		//			if (addTimeline.equals(retrieve.next())){
		//		//				flag = false;
		//		//			}
		//		//		}
		//
		//		if (flag){
		//			database.store(newTimeline);
		//			database.commit();
		//		} else {
		//			System.out.println( "The Timeline has been already added in the database!");
		//		}	

		//Hatem's implementation
		ObjectContainer db = null;
		try {
			db = Db4o.openFile("timelineDatabase.data");
			db.store(newTimeline);
			db.commit();
		}
		finally{
			if(db != null) {
				db.close();
			}
		}
	}

	@Override
	public ArrayList<ObjectSet<Timeline>> getTimeline(String title) {

		ObjectContainer data = Db4o.openFile("timelineDatabase.data");
		ObjectSet<Timeline> retrievedData = data.get(new Timeline(title, "") {});

		ArrayList<ObjectSet<Timeline>> sameTimelines = new ArrayList<>();
		sameTimelines.add(retrievedData);
		return sameTimelines;
	}

	@Override
	public void deleteAll() {
		timelines.clear();
		System.out.println("All data has been deleted.");
	}

	@Override
	public void deleteTimeline(Timeline data) {
		if(timelines.remove(data)) {System.out.println("Timeline is removed."); }
		else { throw new IllegalArgumentException ("Timeline is not removed.");}
	}

	@Override
	public void deleteEvent(MyEvent data) {
		// TODO Auto-generated method stub

	}

	private Iterator<Timeline> iterator() {
		return new TimelineIterator();
	}

	private class TimelineIterator implements Iterator<Timeline> {
		private int position = 0;

		public boolean hasNext() {
			return position < timelines.size();
		}

		public Timeline next() {
			return timelines.get(position++);
		}
	}

}
