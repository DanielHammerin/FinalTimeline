package controller;

import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import model.YearTimeline;
import TestGUI.TimelineBlock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindowController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vBoxModules.prefWidthProperty().bind(mainScrollPane.widthProperty());
		vBoxModules.prefHeightProperty().bind(mainScrollPane.heightProperty());
		
		TimelineBlock tb = new TimelineBlock(new YearTimeline("Hello", "Description", new GregorianCalendar(2015, 04, 12), new GregorianCalendar(2025, 04, 12)));
//		TimelineBlock tb2 = new TimelineBlock(30);
		vBoxModules.getChildren().add(tb);
//		vBoxModules.getChildren().add(tb2);
	}
	
	@FXML
    private MenuItem timeineDialogMenu;

    @FXML
    void openTimelineDialog(ActionEvent event) throws IOException {        
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddTimeLineWindowGUI.fxml"));
         Stage stage = new Stage();
         stage.setScene(new Scene((Parent) loader.load()) );  
         AddTimelineController controller = loader.<AddTimelineController>getController();
         controller.initData(vBoxModules);	
		 stage.show();
    }

    public void updateVBox(Node a){
    	vBoxModules.getChildren().add(a);
    }
    
    @FXML
    private VBox vBoxModules;
	
    public VBox getvBoxModules() {
		return vBoxModules;
	}

	public void setvBoxModules(VBox vBoxModules) {
		this.vBoxModules = vBoxModules;
	}
	
	@FXML
    private ScrollPane mainScrollPane;
}
