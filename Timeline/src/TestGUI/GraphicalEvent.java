package TestGUI;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class GraphicalEvent extends Label {

	
	public GraphicalEvent(String title){
		this.maxHeight(50);
		this.maxWidth(50);
		this.minHeight(50);
		this.minWidth(50);
		
		this.setStyle("-fx-background-color: coral; -fx-padding: 10px;");
	}
}
