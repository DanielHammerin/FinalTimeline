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
 * This is the controller class of the mainWindow, the main window is the
 * interface where the user interacts with the program. Initializable because
 * the Main class needs to initialize this class without a constructor.
 */
public class MainWindowController implements Initializable {
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

		/* Popover methods for Hatem to finish, to get rid of redundant code.
		* The best solution is to have one popover object that changes depending
		* on which buttons that is clicked but you solve it how you want to.*/

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
				try {
					popOverLoad = new LoadTimelinePopOver(vBoxModules);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
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
				try {
					popOverEditTimeline = new EditTimelinePopOver(this);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
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
						//If error, handle it Mauro, lol
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Redraws the time line passed as an argument.
	 * @param dayTimelineGrid
	 */
	public void redrawOneTimeline(NewDayTimelineGrid dayTimelineGrid)
	{
		for(int i=0;i<vBoxModules.getChildren().size();i++){
			if(vBoxModules.getChildren().get(i) instanceof  NewDayTimelineGrid){
				NewDayTimelineGrid newDayTimelineGrid = (NewDayTimelineGrid)vBoxModules.getChildren().get(i);
				if(Objects.equals(newDayTimelineGrid.getDayTimeline().getTitle(), dayTimelineGrid.getDayTimeline().getTitle())){
					vBoxModules.getChildren().remove(vBoxModules.getChildren().get(i));
				}
			}
		}
		vBoxModules.getChildren().add(dayTimelineGrid);
	}

	/* These are auto generated properties created for the FXML, needed for
	* the GUI to work.*/
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