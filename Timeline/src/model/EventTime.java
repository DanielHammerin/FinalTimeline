package model;

import java.util.Calendar;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class EventTime extends MyEvent{

	Calendar startTime;
	Calendar finishTime;
	
	public EventTime(String t, String d, Calendar st, Calendar ft) {
		super(t, d);
		this.setStartTime(st);
		this.setFinishTime(ft);
		
		Rectangle r = new Rectangle();
		geometricFigure.setStyle("-fx-background-color: coral;");
		Text text = new Text();
		text.setText(title);
		geometricFigure.setOnMouseClicked(event->{
			System.out.println("Nu klickas din mama");
		});
		
		geometricFigure.getChildren().addAll(r, text);
	}

	 public void setStartTime (Calendar date) {
		 this.startTime = date;
	 }
	 
	 public Calendar getStartTime (){
		 return startTime;
	 }
	 
	 public void setFinishTime (Calendar date) {
		 this.finishTime = date;
	 }
	 
	 public Calendar getFinishTime (){
		 return finishTime;
	 }

	public boolean equals(EventTime in) 
	{
		boolean descriptions = super.getDescription().equals(in.getDescription());
		boolean sameDate = in.getStartTime().equals(startTime) && in.getFinishTime().equals(finishTime);
		boolean sameTitle = in.getTitle().equals(super.title);
		return descriptions && sameDate && sameTitle;
	}
 
}
