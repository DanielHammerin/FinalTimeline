package view;

import controller.MainWindowController;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.DAO;
import model.YearTimeline;
import org.controlsfx.control.PopOver;

/**
 * Created by Alexander on 27/04/2015.
 */
public class MyPopOver extends PopOver {
    private VBox vbox;
    private TextField searchField;
    private DAO dao;

    public MyPopOver(){
        this.setHideOnEscape(true);
        this.setDetachable(false);
        this.hide();
        this.setWidth(400);
        this.setHeight(200);
        this.setContentNode(vbox);
        this.arrowLocationProperty().set(ArrowLocation.LEFT_TOP);
        vbox = new VBox();
        searchField = new TextField();
        Button b = new Button();
        b.setText("Load Timeline");
        b.prefWidthProperty().bind(vbox.widthProperty());

        /*
        b.setOnMouseClicked(openPopOverNew -> {
            dao =new DAO();
            dao.lookUp(searchField.getText());
        });*/
        searchField.prefWidthProperty().bind(vbox.widthProperty());

        vbox.prefWidthProperty().bind(this.widthProperty());
        vbox.prefHeightProperty().bind(this.heightProperty());

        vbox.getChildren().add(searchField);
        vbox.getChildren().add(b);
        this.setContentNode(vbox);
    }
}
