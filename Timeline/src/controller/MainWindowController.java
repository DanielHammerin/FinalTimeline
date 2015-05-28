package controller;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.DayTimeline;
import model.EventNT;
import model.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.*;
/**
 * Created by Alexander on 27/04/2015.
 * This is the controller class of the mainWindow
 */
public class MainWindowController implements Initializable{
	LoadTimelinePopOver popOverLoad;
	CreateTimelinePopOver popOverNew;
	EditTimelinePopOver popOverEditTimeline;
	AddNewEventPopOver popOverAddNewEvent;
	SQLDAO sqldao = new SQLDAO();
	public static MainWindowController mainWindowController;

	public static ArrayList<DayTimeline> allTheTimelines = new ArrayList<DayTimeline>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SQLDAO sqldao = new SQLDAO();
		mainWindowController = this;

		vBoxModules.prefWidthProperty().bind(mainAnchorPane.widthProperty());
		vBoxModules.prefHeightProperty().bind(mainAnchorPane.heightProperty());

		//This event opens the popOver to create a new timeline
		newTimelineRect.setOnMouseClicked(openPopOverNew -> {
			if (popOverNew != null && popOverNew.isShowing()) {
				popOverNew.hide();
				popOverNew = null;
			} else {
				if(popOverLoad != null && popOverLoad.isShowing()) {
					popOverLoad.hide();
					popOverLoad = null;
				}
				popOverNew = new CreateTimelinePopOver(vBoxModules);
				popOverNew.show(newTimelineRect);
			}
		});
		//This event opens the popOver to load an existing timeline from the database
		loadimelineRect.setOnMouseClicked(openPopOverLoad -> {
			if(popOverLoad != null && popOverLoad.isShowing()){
				popOverLoad.hide();
				popOverLoad = null;
			}else{
				if(popOverNew !=null && popOverNew.isShowing()) {
					popOverNew.hide();
					popOverNew = null;
				}
				popOverLoad = new LoadTimelinePopOver(vBoxModules);
				popOverLoad.show(loadimelineRect);
			}
		});

		editTimelineRect.setOnMouseClicked(editTimeline -> {
			if (popOverEditTimeline != null && popOverEditTimeline.isShowing()) {
				popOverEditTimeline.hide();
				popOverEditTimeline = null;
			} else {
				if (popOverNew != null && popOverNew.isShowing()) {
					popOverNew.hide();
					popOverNew = null;
				}
				if (popOverLoad != null && popOverLoad.isShowing()) {
					popOverLoad.hide();
					popOverLoad = null;
				}
				popOverEditTimeline = new EditTimelinePopOver(this);
				popOverEditTimeline.show(editTimelineRect);
			}
		});

		addNewEventRect.setOnMouseClicked(openAddEvent -> {
			if(popOverAddNewEvent != null && popOverAddNewEvent.isShowing()){
				popOverAddNewEvent.hide();
				popOverAddNewEvent = null;
			}else{
				if(popOverNew !=null && popOverNew.isShowing()) {
					popOverNew.hide();
					popOverNew = null;
				}
				if(popOverLoad != null && popOverLoad.isShowing()){
					popOverLoad.hide();
					popOverLoad = null;
				}
				try {
					if(sqldao.getAllTimelines().size() != 0 || sqldao.getAllTimelines() != null){
						popOverAddNewEvent = new AddNewEventPopOver(this);
						popOverAddNewEvent.show(addNewEventRect);
					}else{
						System.out.println("There are not databases to add an event to!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void redrawTimelines(){
		SQLDAO sqldao = new SQLDAO();
		vBoxModules.getChildren().clear();
		LinkedList<DayTimeline> allTheMothaFuckinTimelines = sqldao.getAllTimelines();
		for(DayTimeline t : allTheMothaFuckinTimelines){
			vBoxModules.getChildren().add(new NewDayTimelineGrid(t));
		}
	}

	public void redrawOneTimeline(NewDayTimelineGrid dayTimelineGrid){
		for(int i=0;i<vBoxModules.getChildren().size();i++){
			if(vBoxModules.getChildren().get(i) instanceof  NewDayTimelineGrid){
				NewDayTimelineGrid newDayTimelineGrid = (NewDayTimelineGrid)vBoxModules.getChildren().get(i);
				if(Objects.equals(newDayTimelineGrid.getDayTimeline().getTitle(), dayTimelineGrid.getDayTimeline().getTitle())){
					vBoxModules.getChildren().remove(vBoxModules.getChildren().get(i));
				}
			}
		}
		sqldao.deleteTimeline(dayTimelineGrid.getDayTimeline().getTitle());
		sqldao.saveTimeline(dayTimelineGrid.getDayTimeline());
		vBoxModules.getChildren().add(dayTimelineGrid);
	}
	@FXML
	private Rectangle addNewEventRect;
	@FXML
	private Rectangle newTimelineRect;
	@FXML
	private VBox vBoxModules;
	@FXML
	private Rectangle loadimelineRect;
	@FXML
	private AnchorPane mainAnchorPane;
	@FXML
	private Rectangle editTimelineRect;
	@FXML
	private ScrollPane mainScrollPane;
}