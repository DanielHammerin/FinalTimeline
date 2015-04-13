package TestGUI;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GraphicalEvent extends StackPane {	
	
	public GraphicalEvent(String title){
		this.maxHeight(50);//To be implemented when the event class is working
		this.maxWidth(50);
		this.minHeight(50);
		this.minWidth(50);
		
		Rectangle r = new Rectangle();
//		r.widthProperty().bind(this.widthProperty());
//		r.heightProperty().bind(this.heightProperty());
//		
		this.setStyle("-fx-background-color: coral;");
		Text t = new Text();
		t.setText(title);
		this.getChildren().addAll(r, t);
	}
}
