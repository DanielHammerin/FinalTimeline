package TestGUI;

import java.util.ArrayList;

import model.Event;
import model.Timeline;
import model.YearTimeline;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class NewGrid extends Group{
	
	ArrayList<Rectangle> allEvents = new ArrayList<Rectangle>();
	YearTimeline yearTimeline;
	
	public NewGrid(YearTimeline timeline){	
		this.yearTimeline= timeline;
		VBox base = new VBox();
		HBox topLine = new HBox();
		int recWidth = 50;
		int topHeight = 30;
		
		
		//Header
		for (int i = timeline.getStartYear(); i < yearTimeline.getEndYear(); i++){
			Rectangle r = new Rectangle(recWidth,topHeight);
			r.setFill(Color.ANTIQUEWHITE);
			r.setStroke(Color.BLACK);
			
			allEvents.add(r);
			Text t = new Text(i + "");
			StackPane stack = new StackPane();			
			stack.getChildren().addAll(r, t);
			topLine.getChildren().add(stack);
		}
		
		base.getChildren().add(topLine);
		
		HBox calenderColumns = new HBox();
		int calColHeight = 100;
		Rectangle[] columns = new Rectangle[yearTimeline.getEndYear()-yearTimeline.getStartYear()];
		
		for (int i = 0; i < columns.length; i++){
			columns[i] = new Rectangle(recWidth, calColHeight);
			columns[i].setFill(Color.AZURE);
			columns[i].setStroke(Color.BLACK);
			calenderColumns.getChildren().add(columns[i]);
		}
		
		base.getChildren().add(calenderColumns);
	
		StackPane r = addEventToGrid(new Event("TITLE","DESCRIPTION") {
		});
		
		this.getChildren().add(base);
		this.getChildren().add(r);
	}
	
	
	//adding event rectangle
	public StackPane addEventToGrid(Event myEvent){
		Rectangle eventRec = new Rectangle(50, 40);
		eventRec.setFill(Color.RED);
		eventRec.setStroke(Color.BLACK);
		Text text = new Text("Event");
		StackPane temp = new StackPane();
		temp.getChildren().addAll(eventRec, text);
		temp.setLayoutX(7);
		temp.setLayoutY(35);
		temp.setOnMouseClicked(event->{
			System.out.println("Nu klickas din mama");
		});
		
		return temp;
	}
		
}