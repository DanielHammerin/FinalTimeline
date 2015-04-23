package TestGUI;


import model.YearTimeline;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * This class represents a graphical Timeline-Container
 * The class inherited a ScrollPane to make the content scrollable.
 * The next inner element is an AnchorPane which contains the Timeline grid itself
 * @author Alexander
 *
 */
public class TimelineBlock extends ScrollPane {

	private AnchorPane myAnchorPane;
	private NewGrid myNewGrid;
	
	/**
	 * Constructor for the yearTimeline block
	 * 
	 * @param yTimeline will be used to build a graphical block
	 */
	public TimelineBlock(YearTimeline yTimeline){
		this.setPrefHeight(300);
		this.setPrefWidth((yTimeline.getEndYear()-yTimeline.getStartYear())*50);		
		this.setWidth((yTimeline.getEndYear()-yTimeline.getStartYear())*50);
		this.setMinWidth((yTimeline.getEndYear()-yTimeline.getStartYear())*50);
		this.setMinHeight(300);
		this.setHeight(300);
		this.setVisible(true);
		
		System.out.println("Width of scrollPane: "+this.getWidth());
		
		myAnchorPane= new AnchorPane();
		myAnchorPane.prefHeightProperty().bind(this.heightProperty());
		myAnchorPane.prefWidthProperty().bind(this.widthProperty());
		
		System.out.println(myAnchorPane.getWidth());
		myNewGrid = new NewGrid(yTimeline, this.getHeight());
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
	
//	/**
//	 * Constructor for the monthTimeline block
//	 * @param mTimeline
//	 */
//	public TimelineBlock(MonthTimeline mTimeline){
//		this.setPrefHeight(500);
//		this.setPrefWidth((mTimeline.getEndMonth()-mTimeline.getStartMonth())*50);
//		this.setMinWidth((mTimeline.getEndMonth()-mTimeline.getStartMonth())*50);
//		this.setMinHeight(500);
//		
//		myAnchorPane= new AnchorPane();
//		myAnchorPane.prefHeightProperty().bind(this.heightProperty());
//		myAnchorPane.prefWidthProperty().bind(this.widthProperty());	
//		
//		myNewGrid = new NewGrid(mTimeline, this.getPrefHeight());
//		myNewGrid.prefHeight(myAnchorPane.getHeight());
//		myNewGrid.prefWidth(myAnchorPane.getWidth());
//				
//		myAnchorPane.getChildren().add(myNewGrid);
//		AnchorPane.setBottomAnchor(myNewGrid, 0.0);
//		AnchorPane.setLeftAnchor(myNewGrid, 0.0);
//		AnchorPane.setTopAnchor(myNewGrid, 0.0);
//		AnchorPane.setRightAnchor(myNewGrid, 0.0);
//		this.setContent(myAnchorPane);
//		this.getChildren().add(myAnchorPane);
//	}
//	
//	/**
//	 * Constructor for the dayTimeline block
//	 * @param dTimeline
//	 */
//	public TimelineBlock(DayTimeline dTimeline){
//		this.setPrefHeight(500);
//		long diff = dTimeline.getEndDate().getTime().getTime() -dTimeline.getStartDate().getTime().getTime();		
//		long diffDays = diff / (24 * 60 * 60 * 1000);
//		this.setPrefWidth((int)diffDays*50);
//		this.setMinWidth((int)diffDays*50);
//		this.setMinHeight(500);
//		
//		myAnchorPane= new AnchorPane();
//		myAnchorPane.prefHeightProperty().bind(this.heightProperty());
//		myAnchorPane.prefWidthProperty().bind(this.widthProperty());	
//		
//		myNewGrid = new NewGrid(dTimeline, this.getPrefHeight());
//		myNewGrid.prefHeight(myAnchorPane.getHeight());
//		myNewGrid.prefWidth(myAnchorPane.getWidth());
//				
//		myAnchorPane.getChildren().add(myNewGrid);
//		AnchorPane.setBottomAnchor(myNewGrid, 0.0);
//		AnchorPane.setLeftAnchor(myNewGrid, 0.0);
//		AnchorPane.setTopAnchor(myNewGrid, 0.0);
//		AnchorPane.setRightAnchor(myNewGrid, 0.0);
//		this.setContent(myAnchorPane);
//		this.getChildren().add(myAnchorPane);
//	}
	
}