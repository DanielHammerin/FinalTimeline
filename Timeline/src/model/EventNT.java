package model;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class EventNT extends MyEvent {
		
	public EventNT (String t, String d){
		super (t,d);
		Circle c = new Circle(20);		
		geometricFigure.setStyle("-fx-background-color: coral;");
		geometricFigure.getChildren().add(c);		
	}
	
	@Override
	public String toString (){
		return (this.title + ": " + this.description);
		
	}

}
