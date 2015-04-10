package controller;

import java.net.URL;
import java.util.ResourceBundle;

import TestGUI.TimelineBlock;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainWindowController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBoxModules.prefWidthProperty().bind(mainScrollPane.widthProperty());
		vBoxModules.prefHeightProperty().bind(mainScrollPane.heightProperty());
		TimelineBlock tb = new TimelineBlock(6);
		TimelineBlock tb2 = new TimelineBlock(30);
		vBoxModules.getChildren().add(tb);
		vBoxModules.getChildren().add(tb2);
	}
	

    @FXML
    private VBox vBoxModules;
	
    @FXML
    private ScrollPane mainScrollPane;
}
