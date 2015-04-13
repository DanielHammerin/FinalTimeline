package model;

public class EventNT extends Event {
	
	public EventNT (String t, String d){
		super (t,d);
	}
	
	@Override
	public String toString (){
		return (this.title + ": " + this.description);
		
	}
	

}
