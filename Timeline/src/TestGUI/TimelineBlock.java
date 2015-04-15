package TestGUI;


import java.util.GregorianCalendar;

import model.YearTimeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class TimelineBlock extends ScrollPane {

	private AnchorPane myAnchorPane;
	private NewGrid myNewGrid;
	
	public TimelineBlock(int amountColumns){
		this.setPrefHeight(300);
		this.setPrefWidth(amountColumns*50);
		this.setMinWidth(amountColumns*50);
		this.setMinHeight(300);
		
		myAnchorPane= new AnchorPane();
		myAnchorPane.prefHeightProperty().bind(this.heightProperty());
		myAnchorPane.prefWidthProperty().bind(this.widthProperty());	
		
		myNewGrid = new NewGrid(new YearTimeline("TITLE", "DESCRIPTION", new GregorianCalendar(2010, 12, 1), new GregorianCalendar(2030, 2, 15)));
		myNewGrid.prefHeight(myAnchorPane.getHeight());
		myNewGrid.prefWidth(myAnchorPane.getWidth());
		
//		myGridPane = new MyGridPane();
//		myGridPane.addColumns(amountColumns);
//		myGridPane.setGridLinesVisible(true);//		
//		myGridPane.addEvent(new GraphicalEvent("dede"), 4, 1, 2);
		
		myAnchorPane.getChildren().add(myNewGrid);
		AnchorPane.setBottomAnchor(myNewGrid, 0.0);
		AnchorPane.setLeftAnchor(myNewGrid, 0.0);
		AnchorPane.setTopAnchor(myNewGrid, 0.0);
		AnchorPane.setRightAnchor(myNewGrid, 0.0);
		this.setContent(myAnchorPane);
		this.getChildren().add(myAnchorPane);
	}
	
}