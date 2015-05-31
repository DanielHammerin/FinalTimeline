package controller;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.DayTimeline;
import model.EventNT;
import model.Timeline;
import model.*;
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

		newTimelineButton.setOnMouseEntered(event -> {
			newTimelineButton.setTooltip(new Tooltip("Create a timeline"));
		});
		addNewEventButton.setOnMouseEntered(event -> {
			addNewEventButton.setTooltip(new Tooltip("Add a new event"));
		});
		loadimelineButton.setOnMouseEntered(event -> {
			loadimelineButton.setTooltip(new Tooltip("Load timeline"));
		});
		editTimelineButton.setOnMouseEntered(event -> {
			editTimelineButton.setTooltip(new Tooltip("Edit timeline"));
		});
      /* Popover methods for Hatem to finish, to get rid of redundant code.
      * The best solution is to have one popover object that changes depending
      * on which buttons that is clicked but you solve it how you want to.*/
		//This event opens the popOver to create a new timeline
		newTimelineButton.setOnMouseClicked(openPopOverNew -> {
			if (popOverNew != null && popOverNew.isShowing()) {
				popOverNew.hide();
				popOverNew = null;
			} else {
				if (popOverLoad != null && popOverLoad.isShowing()) {
					popOverLoad.hide();
					popOverLoad = null;
				}
				if (popOverAddNewEvent != null && popOverAddNewEvent.isShowing()) {
					popOverAddNewEvent.hide();
					popOverAddNewEvent = null;
				}
				if (popOverEditTimeline != null && popOverEditTimeline.isShowing()) {
					popOverEditTimeline.hide();
					popOverEditTimeline = null;
				}
				popOverNew = new CreateTimelinePopOver(vBoxModules);
				popOverNew.show(newTimelineButton);
			}
		});
		//This event opens the popOver to load an existing timeline from the database
		loadimelineButton.setOnMouseClicked(openPopOverLoad -> {
			if(popOverLoad != null && popOverLoad.isShowing()){
				popOverLoad.hide();
				popOverLoad = null;
			}else{
				if(popOverNew !=null && popOverNew.isShowing()) {
					popOverNew.hide();
					popOverNew = null;
				}
				if (popOverAddNewEvent != null && popOverAddNewEvent.isShowing()) {
					popOverAddNewEvent.hide();
					popOverAddNewEvent = null;
				}
				if (popOverEditTimeline != null && popOverEditTimeline.isShowing()) {
					popOverEditTimeline.hide();
					popOverEditTimeline = null;
				}
				try {
					popOverLoad = new LoadTimelinePopOver(vBoxModules);
					popOverLoad.show(loadimelineButton);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Database Error Connection");
					alert.setHeaderText("Error!");
					alert.setContentText("There was an error trying to connect to the database");
					alert.showAndWait();
					e.printStackTrace();
				} catch (InstantiationException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Database Error Connection");
					alert.setHeaderText("Error!");
					alert.setContentText("There was an error trying to connect to the database");
					alert.showAndWait();
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Database Error Connection");
					alert.setHeaderText("Error!");
					alert.setContentText("There was an error trying to connect to the database");
					alert.showAndWait();
					e.printStackTrace();
				}
			}
		});
		editTimelineButton.setOnMouseClicked(editTimeline -> {
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
				if (popOverAddNewEvent != null && popOverAddNewEvent.isShowing()) {
					popOverAddNewEvent.hide();
					popOverAddNewEvent = null;
				}
				try {
					popOverEditTimeline = new EditTimelinePopOver(this);
					popOverEditTimeline.show(editTimelineButton);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Database Error Connection");
					alert.setHeaderText("Error!");
					alert.setContentText("There was an error trying to connect to the database\\" + e.getMessage());
					alert.showAndWait();
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
		addNewEventButton.setOnMouseClicked(openAddEvent -> {
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
				if (popOverEditTimeline != null && popOverEditTimeline.isShowing()) {
					popOverEditTimeline.hide();
					popOverEditTimeline = null;
				}
				try {
					if(sqldao.getAllTimelines().size() != 0 || sqldao.getAllTimelines() != null){
						popOverAddNewEvent = new AddNewEventPopOver(this);
						popOverAddNewEvent.show(addNewEventButton);
					}else{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("No existing timelines");
						alert.setHeaderText("Error!");
						alert.setContentText("There are currently no timelines in  the database");
						alert.showAndWait();
					}
				} catch (Exception e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Database Error Connection");
					alert.setHeaderText("Error!");
					alert.setContentText("There was an error trying to connect to the database\\"+e.getMessage());
					alert.showAndWait();
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Redraws the time line passed as an argument.
	 * @param dayTimeline
	 */
	public void redrawOneTimelineAddEvent(DayTimeline dayTimeline, MyEvent myEvent){
		for(int i=0;i<vBoxModules.getChildren().size();i++){
			if(vBoxModules.getChildren().get(i) instanceof  NewDayTimelineGrid){
				NewDayTimelineGrid newDayTimelineGrid = (NewDayTimelineGrid)vBoxModules.getChildren().get(i);
				if(Objects.equals(newDayTimelineGrid.getDayTimeline().getTitle(), dayTimeline.getTitle())){
					if(myEvent instanceof  EventNT){
						EventNT eventNT = (EventNT)myEvent;
						newDayTimelineGrid.redrawEventsAddEvent(eventNT);
					}else{
						EventTime eventTime = (EventTime)myEvent;
						newDayTimelineGrid.redrawEventsAddEvent(eventTime);
					}
					break;
				}

			}
		}
	}

	public void redrawOneTimelineRemoveEvent(DayTimeline dayTimeline, MyEvent myEvent){
		for(int i=0;i<vBoxModules.getChildren().size();i++){
			if(vBoxModules.getChildren().get(i) instanceof  NewDayTimelineGrid){
				NewDayTimelineGrid newDayTimelineGrid = (NewDayTimelineGrid)vBoxModules.getChildren().get(i);
				if(Objects.equals(newDayTimelineGrid.getDayTimeline().getTitle(), dayTimeline.getTitle())){
					if(myEvent instanceof  EventNT){
						EventNT eventNT = (EventNT)myEvent;
						newDayTimelineGrid.redrawEventsRemoveEvent(eventNT);
					}else{
						EventTime eventTime = (EventTime)myEvent;
						newDayTimelineGrid.redrawEventsRemoveEvent(eventTime);
					}
					break;
				}

			}
		}
	}

	public void redrawOneTimeline(String oldTimelineTitle, DayTimeline newTimeline){
		for(int i=0; i < vBoxModules.getChildren().size(); i++) {
			if (vBoxModules.getChildren().get(i) instanceof NewDayTimelineGrid) {
				NewDayTimelineGrid newDayTimelineGrid = (NewDayTimelineGrid)vBoxModules.getChildren().get(i);
				DayTimeline comparedTimeline = newDayTimelineGrid.getDayTimeline();
				if(comparedTimeline.getTitle().equals(oldTimelineTitle)) {
					vBoxModules.getChildren().remove(newDayTimelineGrid);
					vBoxModules.getChildren().add(new NewDayTimelineGrid(newTimeline));
					break;
				}
			}
		}
	}
	/* These are auto generated properties created for the FXML, needed for
       * the GUI to work.*/
	@FXML
	private Button addNewEventButton;
	@FXML
	private Button newTimelineButton;
	@FXML
	private VBox vBoxModules;
	@FXML
	private Button loadimelineButton;
	@FXML
	private AnchorPane mainAnchorPane;
	@FXML
	private Button editTimelineButton;
	@FXML
	private ScrollPane mainScrollPane;
	public Stage mainStage;
}