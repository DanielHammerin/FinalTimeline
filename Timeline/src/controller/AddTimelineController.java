package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import TestGUI.TimelineBlock;
import model.DayTimeline;
import model.MonthTimeline;
import model.YearTimeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddTimelineController implements Initializable{

	ToggleGroup tg = new ToggleGroup();
	VBox vb;
	@FXML
    void addTimeline(ActionEvent event) throws IOException {
		LocalDate localStart = startDatePicker.getValue();
		LocalDate localEnd = endDatePicker.getValue();
		
		GregorianCalendar gregorianStart = new GregorianCalendar();
		gregorianStart.set(localStart.getYear(), localStart.getMonthValue(),localStart.getDayOfMonth());
		
		GregorianCalendar gregorianEnd = new GregorianCalendar();
		gregorianEnd.set(localEnd.getYear(),localEnd.getMonthValue(),localEnd.getDayOfMonth());
		
		System.out.println(gregorianStart.getTime());
		System.out.println(gregorianEnd.getTime());
		
    	if(tg.getSelectedToggle() == annualBtn){
    		
    		YearTimeline y = new YearTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
    		TimelineBlock block = new TimelineBlock(y);
    		
    		vb.getChildren().add(block);
    		
    		Stage stage = (Stage) cancelBtn.getScene().getWindow();        
            stage.close();
    	}else if(tg.getSelectedToggle() == monthlyBtn){
    		MonthTimeline m = new MonthTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
    	}else{
    		DayTimeline d = new DayTimeline(titleTxt.getText(), descriptionTxt.getText(), gregorianStart, gregorianEnd);
    	}
    }

	public void initData(VBox vb){
		this.vb = vb;
	}
	
	
    @FXML
    void cancelTimeline(ActionEvent event) {
    	Stage stage = (Stage) cancelBtn.getScene().getWindow();        
        stage.close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		annualBtn.setToggleGroup(tg);
		monthlyBtn.setToggleGroup(tg);
		dailyBtn.setToggleGroup(tg);		
	}
	
	public VBox testBox;

  @FXML
    private TextField titleTxt;

    @FXML
    private Button cancelBtn;

    @FXML
    private RadioButton annualBtn;
    
    @FXML
    private RadioButton monthlyBtn;

    @FXML
    private RadioButton dailyBtn;
  
    @FXML
    private Button addBtn;

    @FXML
    private TextField descriptionTxt;
    
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private DatePicker startDatePicker;

}
