package TestGUI;


import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TimelineBlock extends ScrollPane {

	private int amountColumns;
	private AnchorPane myAnchorPane;
	private MyGridPane myGridPane;
	
	public TimelineBlock(int amountColumns){
		this.amountColumns = amountColumns;
		this.setPrefHeight(300);
		this.setPrefWidth(amountColumns*50);
		this.setMinWidth(amountColumns*50);
		this.setMinHeight(300);
		
		myAnchorPane= new AnchorPane();
		myAnchorPane.prefHeightProperty().bind(this.heightProperty());
		myAnchorPane.prefWidthProperty().bind(this.widthProperty());	
		
		myGridPane = new MyGridPane();
		myGridPane.addColumns(amountColumns);
		myGridPane.setGridLinesVisible(true);
		
		myGridPane.addEvent(new GraphicalEvent("dede"), 4, 1, 2);
//		Rectangle r = new Rectangle(200, 50);
//		r.setFill(Color.RED);
//		
//		Rectangle r2 = new Rectangle(100, 50);
//		r.setFill(Color.BLUE);
//		myGridPane.addEvent(r, 4, 1, 2);
//		myGridPane.addEvent(r2, 2, 1, 1);
		
		myAnchorPane.getChildren().add(myGridPane);
		AnchorPane.setBottomAnchor(myGridPane, 0.0);
		AnchorPane.setLeftAnchor(myGridPane, 0.0);
		AnchorPane.setTopAnchor(myGridPane, 0.0);
		AnchorPane.setRightAnchor(myGridPane, 0.0);
		this.setContent(myAnchorPane);
		this.getChildren().add(myAnchorPane);
	}
	
}