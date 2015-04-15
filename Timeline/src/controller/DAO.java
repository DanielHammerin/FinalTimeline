package controller;
/** This class is the implementation of the database access object implementation **/
import java.util.List;
import com.db4o.ObjectContainer;
import model.MyEvent;
import model.Timeline;

public class DAO implements DaoInterface {
	private List<Timeline> timelines;
	
	@Override
	public void addData(ObjectContainer database, Timeline addTimeline) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Timeline> getData() {
		if(timelines.isEmpty()) {
			throw new IllegalArgumentException("There is no data to retrieve");
		}
		return timelines;
	}
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
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
	
}
