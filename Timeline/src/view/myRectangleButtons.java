package view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Created by Alexander on 12/05/2015.
 */
public class myRectangleButtons extends StackPane {

    public myRectangleButtons(String text, Color color){

        Text buttonText = new Text(text);
        Rectangle rect = new Rectangle();
        rect.setFill(color);
        rect.setWidth(100);
        rect.setHeight(100);
        this.getChildren().addAll(rect, buttonText);


    }
}
