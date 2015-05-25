package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DayTimeline;
import model.EventNT;
import model.EventTime;

import java.util.GregorianCalendar;

/**
 * Created by Jakob on 2015-05-18.
 */
public class NewDTGMain extends Application
{
    public void start(Stage stage)
    {
        try
        {

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        GregorianCalendar startDate = new GregorianCalendar(2012, 10, 20);
        GregorianCalendar endDate = new GregorianCalendar(2012, 12, 30);
        DayTimeline d1 = new DayTimeline("Title", "Description of the timeline", startDate, endDate);

        GregorianCalendar startOfEvent1 = new GregorianCalendar(2012, 10, 25);
        GregorianCalendar endOfEvent1 = new GregorianCalendar(2012, 11, 5);
        EventTime ev1 = new EventTime("Title1", "Description", startOfEvent1, endOfEvent1);
        d1.addEventTime(ev1);

        GregorianCalendar startOfEvent2 = new GregorianCalendar(2012, 11, 2);
        GregorianCalendar endOfEvent2 = new GregorianCalendar(2012, 11, 15);
        EventTime ev2 = new EventTime("Title2", "Description", startOfEvent2, endOfEvent2);
        d1.addEventTime(ev2);

        GregorianCalendar startOfEvent3 = new GregorianCalendar(2012, 11, 1);
        GregorianCalendar endOfEvent3 = new GregorianCalendar(2012, 11, 15);
        EventTime ev3 = new EventTime("Title3", "Description", startOfEvent3, endOfEvent3);
        d1.addEventTime(ev3);

        GregorianCalendar startOfEvent4 = new GregorianCalendar(2012, 11, 7);
        GregorianCalendar endOfEvent4 = new GregorianCalendar(2012, 11, 15);
        EventTime ev4 = new EventTime("Title4", "Description", startOfEvent4, endOfEvent4);
        d1.addEventTime(ev4);

        GregorianCalendar startOfEvent5 = new GregorianCalendar(2012, 10, 24);
        GregorianCalendar endOfEvent5 = new GregorianCalendar(2012, 11, 15);
        EventTime ev5 = new EventTime("Title5", "Description", startOfEvent5, endOfEvent5);
        d1.addEventTime(ev5);

        GregorianCalendar dateEventNT = new GregorianCalendar(2012, 11, 15);
        EventNT evNT1 = new EventNT("EventNT!!!!!!!", "Description", dateEventNT);
        d1.addEventNT(evNT1);

        EventNT evNT2 = new EventNT("EventNT!!!!!", "Description", dateEventNT);
        d1.addEventNT(evNT2);


        Button removeEvent = new Button("Remove event");


        NewDayTimelineGrid dtg = new NewDayTimelineGrid(d1);

        removeEvent.setOnAction(event -> {
            dtg.redrawEventsRemoveEvent(ev5);
            dtg.redrawEventsRemoveEvent(evNT2);
            dtg.redrawEventsRemoveEvent(ev2);
        });

        Button addEvents = new Button("Re add the events");

        addEvents.setOnAction(event -> {
            dtg.redrawEventsAddEvent(evNT2);
            dtg.redrawEventsAddEvent(ev5);
            dtg.redrawEventsAddEvent(ev2);
        });

        HBox buttons = new HBox(removeEvent, addEvents);

        VBox all = new VBox(dtg, buttons);
        System.out.println("Almost everything working");

        Scene scene = new Scene(all, 1000,500);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
