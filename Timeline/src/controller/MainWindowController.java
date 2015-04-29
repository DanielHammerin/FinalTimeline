package controller;

import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.scene.shape.Rectangle;
import model.DayTimeline;
import model.MonthTimeline;
import model.YearTimeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.DayTimelineGrid;
import view.MonthTimelineGrid;
import view.MyPopOver;
import view.YearTimelineGrid;

public class MainWindowController implements Initializable{

	MyPopOver popOverLoad;
	MyPopOver popOverNew;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		vBoxModules.prefWidthProperty().bind(mainScrollPane.widthProperty());
		vBoxModules.prefHeightProperty().bind(mainScrollPane.heightProperty());
		

		YearTimelineGrid y = new YearTimelineGrid(new YearTimeline("Hello", "Description", new GregorianCalendar(2015, 04, 12), new GregorianCalendar(2025, 04, 12)));

		MonthTimelineGrid m = new MonthTimelineGrid(new MonthTimeline("Hello", "Description", new GregorianCalendar(2015, 04, 12), new GregorianCalendar(2016, 04, 12)));

		DayTimelineGrid d = new DayTimelineGrid(new DayTimeline("Hello","Description",new GregorianCalendar(2015,04,12),new GregorianCalendar(2015,07,19)));


		vBoxModules.getChildren().add(d.getTimeLineBlock());
		vBoxModules.getChildren().add(m.getTimeLineBlock());
		vBoxModules.getChildren().add(y.getTimeLineBlock());


		//WORK ON !!!
		newTimelineRect.setOnMouseClicked(openPopOverNew -> {
			if(popOverNew != null && popOverNew.isShowing()){
				popOverNew.hide();
				popOverNew = null;
			}else{
				popOverNew = new MyPopOver();
				popOverNew.show(newTimelineRect);
			}
		});

		loadimelineRect.setOnMouseClicked(openPopOverLoad -> {
			if(popOverLoad != null && popOverLoad.isShowing()){
				popOverLoad.hide();
				popOverLoad = null;
			}else{
				popOverLoad = new MyPopOver();
				popOverLoad.show(loadimelineRect);
			}
		});
}

	@FXML
	private Rectangle newTimelineRect;

	@FXML
	private VBox vBoxModules;

	@FXML
	private ScrollPane mainScrollPane;

	@FXML
	private Rectangle loadimelineRect;

}
