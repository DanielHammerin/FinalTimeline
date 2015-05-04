package view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.*;
import org.controlsfx.control.PopOver;

/**
 * Created by Alexander on 27/04/2015.
 */
public class MyPopOver extends PopOver {
    private VBox vbox;
    private TextField searchField;
    private DAO dao;

    public MyPopOver(VBox mainVBox){
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

        b.setOnMouseClicked(loadTimeline -> {
            dao =new DAO();
            if(dao.lookUp(searchField.getText())==true) {
                System.out.println(searchField.getText());
                Timeline t = dao.getTimeline(searchField.getText());
                if (t.isDayTimeline() == true && t.isMonthTimeline() == false && t.isYearTimeline() == false) {
                    DayTimelineGrid d = new DayTimelineGrid((DayTimeline) t);
                    mainVBox.getChildren().add(d.getTimeLineBlock());
                    this.hide();
                } else if (t.isMonthTimeline() == true && t.isDayTimeline() == false && t.isYearTimeline() == false) {
                    MonthTimelineGrid m = new MonthTimelineGrid((MonthTimeline) t);
                    mainVBox.getChildren().add(m.getTimeLineBlock());
                    this.hide();
                } else if (t.isYearTimeline() == true && t.isMonthTimeline() == false && t.isDayTimeline() == false) {
                    YearTimelineGrid y = new YearTimelineGrid((YearTimeline) t);
                    mainVBox.getChildren().add(y.getTimeLineBlock());
                    this.hide();
                }
            }else{
                System.out.print("Timeline is not there");
            }
        });
        vbox.getChildren().add(searchField);
        vbox.getChildren().add(b);
        this.setContentNode(vbox);
    }
}
