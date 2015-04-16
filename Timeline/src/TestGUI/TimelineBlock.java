package TestGUI;


import java.util.GregorianCalendar;

import model.YearTimeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * This class represents a graphical Timeline-Container
 * The class inheritet a ScrollPane to make the content scrollable.
 * The next inner element is an AnchorPane which contains the Timeline grid itself
 * @author Alexander
 *
 */
public class TimelineBlock extends ScrollPane {

	private AnchorPane myAnchorPane;
	private NewGrid myNewGrid;
	
	public TimelineBlock(int amountColumns){
		this.setPrefHeight(500);
		this.setPrefWidth(amountColumns*50);
		this.setMinWidth(amountColumns*50);
		this.setMinHeight(500);
		
		myAnchorPane= new AnchorPane();
		myAnchorPane.prefHeightProperty().bind(this.heightProperty());
		myAnchorPane.prefWidthProperty().bind(this.widthProperty());	
		
		myNewGrid = new NewGrid(new YearTimeline("TITLE", "DESCRIPTION", new GregorianCalendar(2010, 12, 1), new GregorianCalendar(2030, 2, 15)), this.getPrefHeight());
		myNewGrid.prefHeight(myAnchorPane.getHeight());
		myNewGrid.prefWidth(myAnchorPane.getWidth());
				
		myAnchorPane.getChildren().add(myNewGrid);
		AnchorPane.setBottomAnchor(myNewGrid, 0.0);
		AnchorPane.setLeftAnchor(myNewGrid, 0.0);
		AnchorPane.setTopAnchor(myNewGrid, 0.0);
		AnchorPane.setRightAnchor(myNewGrid, 0.0);
		this.setContent(myAnchorPane);
		this.getChildren().add(myAnchorPane);
	}
	
}