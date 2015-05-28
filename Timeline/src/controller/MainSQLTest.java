package controller;

import model.DayTimeline;
import model.EventNT;
import model.EventTime;

import java.sql.SQLException;
import java.util.GregorianCalendar;

/**
 * Created by Alexander on 25/05/2015.
 */
public class MainSQLTest {

    public static void main(String args[]){

        SQLDAO sqldao = new SQLDAO();

            DayTimeline dtl = new DayTimeline("Test","Description", new GregorianCalendar(2015, 12, 2), new GregorianCalendar(2016, 3, 30));
            EventNT eventNT = new EventNT("EventNT","DescriptionEvent", new GregorianCalendar(2015, 12, 5));
            EventTime eventTime = new EventTime("EventTime","EUHGHHHSAUHS", new GregorianCalendar(2016, 1, 5),new GregorianCalendar(2016, 1, 20));
            dtl.addEventNT(eventNT);
            dtl.addEventTime(eventTime);
           // sqldao.saveTimeline(dtl);

            //DayTimeline dayTimeline = sqldao.getTimeline("Test");
            //System.out.println(sqldao.getTimeline("Test").getDescription());
    }
}
