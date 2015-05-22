package controller;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.DayTimeline;
import model.MonthTimeline;
import model.Timeline;
import model.YearTimeline;
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

	ArrayList<Timeline> allTheTimelines = new ArrayList<Timeline>();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		NewTimelineGrid a = new NewTimelineGrid(new DayTimeline("Day  Timline ", "Description", new GregorianCalendar(2015, 5, 20), new GregorianCalendar(2015, 6, 30)));
		//NewTimelineGrid b = new NewTimelineGrid(new MonthTimeline("Month Timline","description",new GregorianCalendar(2015, 4, 1), new GregorianCalendar(2016, 9, 15)));
		//NewTimelineGrid c = new NewTimelineGrid(new YearTimeline("Year Timeline", "Description", new GregorianCalendar(2015, 12, 01), new GregorianCalendar(2030, 12, 01)));
		//vBoxModules.getChildren().add(a);
		vBoxModules.getChildren().add(a);
		//vBoxModules.getChildren().add(c);
		DAO d = new DAO();
		try {
			d.clearDatabase();
			d.saveV2(a.getDayTimeline());
//       d.saveV2(new YearTimeline("a", "Description", new GregorianCalendar(2015, 12, 01), new GregorianCalendar(2030, 12, 01)
//       d.saveV2(new MonthTimeline("m","description",new GregorianCalendar(2015, 4, 01), new GregorianCalendar(2015, 9, 15)));
			d.saveV2(new DayTimeline("MothafuckinTamelane ", "Description", new GregorianCalendar(2015, 12, 2), new GregorianCalendar(2015, 12, 30)));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				popOverLoad = new LoadTimelinePopOver(vBoxModules,allTheTimelines);
				popOverLoad.show(loadimelineRect);
			}
		});

		addEventRect.setOnMouseClicked(openAddEvent -> {
			if(popOverEditTimeline != null && popOverEditTimeline.isShowing()){
				popOverEditTimeline.hide();
				popOverEditTimeline = null;
			}else{
				if(popOverNew !=null && popOverNew.isShowing()) {
					popOverNew.hide();
					popOverNew = null;
				}
				if(popOverLoad != null && popOverLoad.isShowing()){
					popOverLoad.hide();
					popOverLoad = null;
				}
				DAO dao = new DAO();
				for(DayTimeline timeline : dao.getAllTimelines()) {
					allTheTimelines.add(timeline);
				}
				popOverEditTimeline = new EditTimelinePopOver(this);
				popOverEditTimeline.show(addEventRect);
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
					popOverAddNewEvent = new AddNewEventPopOver(this);

					popOverAddNewEvent.show(addEventRect);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});


	}

	public void redrawTimelines(){
		DAO dao = new DAO();
		vBoxModules.getChildren().clear();
		LinkedList<DayTimeline> allTheMothaFuckinTimelines = dao.getAllTimelines();
		for(Timeline t : allTheMothaFuckinTimelines){
			vBoxModules.getChildren().add(new NewTimelineGrid(t));
		}
	}
	@FXML
	private Rectangle addNewEventRect;

	@FXML
	private Rectangle newTimelineRect;
	@FXML
	private  VBox vBoxModules;
	@FXML
	private Rectangle loadimelineRect;
	@FXML
	private AnchorPane mainAnchorPane;
	@FXML
	private Rectangle addEventRect;
	@FXML
	private ScrollPane mainScrollPane;
}