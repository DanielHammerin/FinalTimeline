package controller;

import javafx.scene.control.Alert;
import model.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Contains methods to interact with the data base.
 * Created by Alexander on 24/05/2015.
 */
public class SQLDAO
{

    /**
     * Saves the passed timeline into the database.
     * @param dayTimeline the timeline to be saved.
     */
    public void saveTimeline(DayTimeline dayTimeline) {
        Date startDate = new Date(dayTimeline.getStartDate().getTimeInMillis());
        Date endDate = new Date(dayTimeline.getEndDate().getTimeInMillis());

        String query2 = "insert into daytimelines(title, description, startDate, endDate)"
                + " values (?, ?, ?, ?)";

        Properties properties = new Properties();
        Connection conn = null;
        try {
            conn = openConnection();

            PreparedStatement preparedStmt = conn.prepareStatement(query2);
            preparedStmt.setString(1, dayTimeline.getTitle());
            preparedStmt.setString(2, dayTimeline.getDescription());
            preparedStmt.setDate(3, startDate);
            preparedStmt.setDate(4, endDate);

            preparedStmt.executeUpdate();
            preparedStmt.close();

            Date startDateEventNT;
            for(EventNT eventNT : dayTimeline.getEventNTs()){

                String queryEventNT = "insert into eventnotime(title, description, startDate, timeline)"
                        + " values (?, ?, ?, ?)";

                PreparedStatement pStatementEventNT = conn.prepareStatement(queryEventNT);
                startDateEventNT = new Date(eventNT.getDateOfEvent().getTimeInMillis());

                pStatementEventNT.setString(1, eventNT.getTitle());
                pStatementEventNT.setString(2, eventNT.getDescription());
                pStatementEventNT.setDate(3, startDateEventNT);
                pStatementEventNT.setString(4, dayTimeline.getTitle());

                pStatementEventNT.executeUpdate();
                pStatementEventNT.close();
            }

            Date startDateEventTime;
            Date endDateEventTime;
            for(EventTime eventTime : dayTimeline.getEventTimes()){
                String queryEventTime = "insert into eventtime(title, description, startDate, endDate, timeline)"
                        + " values (?, ?, ?, ?, ?)";

                startDateEventTime = new Date(eventTime.getStartTime().getTimeInMillis());
                endDateEventTime = new Date(eventTime.getFinishTime().getTimeInMillis());

                PreparedStatement pStatementEventTime = conn.prepareStatement(queryEventTime);
                pStatementEventTime.setString(1, eventTime.getTitle());
                pStatementEventTime.setString(2, eventTime.getDescription());
                pStatementEventTime.setDate(3, startDateEventTime);
                pStatementEventTime.setDate(4, endDateEventTime);
                pStatementEventTime.setString(5, dayTimeline.getTitle());
                pStatementEventTime.executeUpdate();

                pStatementEventTime.close();
            }
                conn.close();

        } catch (ClassNotFoundException| SQLException | IllegalAccessException | InstantiationException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Connection Error");
            alert.setHeaderText("Error!");
            alert.setContentText("The Timeline was not saved due to a Database Connection Error");
            alert.showAndWait();

            e.printStackTrace();
        }
    }

    public void saveEvent(Timeline timeline,MyEvent event) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {

        Connection conn = openConnection();
        if(event instanceof  EventNT){
            EventNT eventNT = (EventNT)event;
            Date startDate = new Date(eventNT.getDateOfEvent().getTimeInMillis());
            String myQuery = "insert into eventnotime(title, description, startDate, timeline)"
                    + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(myQuery);
            preparedStmt.setString(1, event.getTitle());
            preparedStmt.setString(2, event.getDescription());
            preparedStmt.setDate(3, startDate);
            preparedStmt.setString(4, timeline.getTitle());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }else{
            EventTime eventTime = (EventTime)event;
            Date startDate = new Date(eventTime.getStartTime().getTimeInMillis());
            Date endDate = new Date(eventTime.getFinishTime().getTimeInMillis());
            String myQuery = "insert into eventtime(title, description, startDate, enddate, timeline)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(myQuery);
            preparedStmt.setString(1, event.getTitle());
            preparedStmt.setString(2, event.getDescription());
            preparedStmt.setDate(3, startDate);
            preparedStmt.setDate(4, endDate);
            preparedStmt.setString(5, timeline.getTitle());
            preparedStmt.executeUpdate();
            preparedStmt.close();
        }
        conn.close();
    }

    /**
     * Retrieves all timelines from the day timelines table.
     * @return
     */
    public LinkedList<DayTimeline> getAllTimelines() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        String myQuery = "SELECT * FROM daytimelines";

        LinkedList<DayTimeline> allTimelines = new LinkedList<DayTimeline>();

        Connection c = openConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(myQuery);

        while (rs.next()) {
            DayTimeline dayTimeline = new DayTimeline();
            dayTimeline.setTitle(rs.getString("Title"));
            dayTimeline.setDescription(rs.getString("Description"));
            GregorianCalendar startDate = new GregorianCalendar();
            startDate.setTime(rs.getDate("startDate"));
            dayTimeline.setStartDate(startDate);
            GregorianCalendar endDate = new GregorianCalendar();
            endDate.setTime(rs.getDate("endDate"));
            dayTimeline.setEndDate(endDate);
            allTimelines.add(dayTimeline);

            dayTimeline.setEventNTs(getEventsNT(dayTimeline));
            dayTimeline.setEventTimes(getEventsTime(dayTimeline));
        }
        c.close();

        return allTimelines;
    }

    /**
     * This method retieves a time line with the passed title.
     * @param title
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public DayTimeline getTimeline(String title) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String myQuery = "SELECT *\n" +
                "FROM DayTimelines\n" +
                "WHERE Title = '"+title+"'" ;
        DayTimeline dayTimeline = new DayTimeline();
        Connection c = openConnection();
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(myQuery);
        while (rs.next()) {
            dayTimeline.setTitle(rs.getString("Title"));
            dayTimeline.setDescription(rs.getString("Description"));
            GregorianCalendar startDate = new GregorianCalendar();
            GregorianCalendar endDate = new GregorianCalendar();

            startDate.setTime(rs.getDate("startdate"));
            dayTimeline.setStartDate(startDate);
            endDate.setTime(rs.getDate("enddate"));
            dayTimeline.setEndDate(endDate);

            dayTimeline.setEventNTs(getEventsNT(dayTimeline));
            dayTimeline.setEventTimes(getEventsTime(dayTimeline));
        }
        c.close();

        return dayTimeline;
    }

    /**
     * This method returns the events with no duration of a timeline
     * @param dayTimeline
     * @return a treeset containing the events
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public TreeSet<EventNT> getEventsNT(DayTimeline dayTimeline) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        TreeSet<EventNT> events = new TreeSet<EventNT>();
        Connection conn = openConnection();

        String myQuery = "SELECT *\n" +
                "FROM eventnotime\n" +
                "WHERE timeline = '"+dayTimeline.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);

        while(rs.next()){
            GregorianCalendar g = new GregorianCalendar() ;
            g.setTime(rs.getDate("startdate"));
            EventNT eventNT = new EventNT(rs.getString("Title"),rs.getString("Description"),g);
            events.add(eventNT);
        }
        conn.close();
        return  events;
    }

    /**
     * Identical method for the events with duration for a timeline
     * @param dayTimeline
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public TreeSet<EventTime> getEventsTime(DayTimeline dayTimeline) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        TreeSet<EventTime> events = new TreeSet<EventTime>();
        String myQuery;
        Connection conn = openConnection();

        myQuery = "SELECT *\n" +
                "FROM eventtime\n" +
                "WHERE timeline = '"+dayTimeline.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);

        while(rs.next()){
            GregorianCalendar startDate = new GregorianCalendar() ;
            GregorianCalendar endDate = new GregorianCalendar() ;
            startDate.setTime(rs.getDate("startdate"));
            endDate.setTime(rs.getDate("enddate"));

            EventTime eventTime = new EventTime(rs.getString("Title"), rs.getString("Description"),startDate,endDate);
            events.add(eventTime);
        }
        conn.close();
        return events;
    }

    /**
     * Compares the timelines in the data base looking for duplicate timelines.
     * @param dayTimeline
     * @return
     * @throws Exception
     */
    public boolean isThereADuplicateTimeline(DayTimeline dayTimeline) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        LinkedList<DayTimeline> allTimelines2 = getAllTimelines();
        for(DayTimeline dtl : allTimelines2){
            if(Objects.equals(dtl.getTitle(), dayTimeline.getTitle())){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Duplicate Timeline");
                a.setContentText("There exists an timeline called"+dtl.getTitle());
                return true;
            }
        }
        return  false;
    }

    public boolean isThereADuplicateEvent(MyEvent myEvent) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        Connection conn = openConnection();
        String myQuery;

        if(myEvent instanceof  EventNT){
            myQuery= "SELECT *\n" +
                    "FROM eventnotime\n" +
                    "WHERE title = '"+myEvent.getTitle()+"'" ;
        }else{
            myQuery= "SELECT *\n" +
                    "FROM eventtime\n" +
                    "WHERE title = '"+myEvent.getTitle()+"'" ;
        }

        ResultSet rs = conn.createStatement().executeQuery(myQuery);
        while(rs.next()){
            String s = rs.getString("title");
           if(Objects.equals(s, myEvent.getTitle())){
               return true;
           }
        }
        return  false;
    }

    /**
     * This method returns the correct timeline that an event is argument is passed as.
     * @param eventNT
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public DayTimeline getTimelineFromEventNT(EventNT eventNT) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        String dayTimelineTitle = "";
        String myQuery = "SELECT timeline FROM eventnotime WHERE title = '"+eventNT.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);
        while(rs.next()){
            dayTimelineTitle = rs.getString(1);
        }
    conn.close();
        return  getTimeline(dayTimelineTitle);
    }

    /**
     * Identical method to the previous method
     * @param eventTime
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public DayTimeline getTimelineFromEventTime(EventTime eventTime) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        String dayTimelineTitle = "";
        String myQuery = "SELECT timeline FROM eventtime WHERE title = '"+eventTime.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);
        while(rs.next()){
            dayTimelineTitle = rs.getString(1);
        }
        conn.close();
        return getTimeline(dayTimelineTitle);

    }

    /**
     * Deletes a timeline from the database.
     * @param title the title of the timeline to be removed
     */
    public void deleteTimeline(String title) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
        String myQuery = "DELETE from daytimelines where title='"+title+"'";

        Connection c = openConnection();
        Statement s = c.createStatement();
        s.executeUpdate(myQuery);
        c.close();
    }

    public void deleteEvent(MyEvent event) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

        String query;
        if(event instanceof  EventNT){
            query= "DELETE from eventnotime where title='"+event.getTitle()+"'";
        }else{
            query="DELETE from eventtime where title='"+event.getTitle()+"'";
        }
        Connection c = openConnection();
        Statement s = c.createStatement();
        s.executeUpdate(query);
        c.close();
    }

    /**
     * This method updates an event with no duration
     * @param oldEventTitle the title of the event to be edited
     * @param eventNT is the new event that will be stored
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void updateEventNT(String oldEventTitle, EventNT eventNT) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        String myQuery = "UPDATE eventnotime SET title = ?, description = ? ,startdate = ? WHERE title ='"+oldEventTitle+"'";

        PreparedStatement ps = conn.prepareStatement(myQuery);
        ps.setString(1,eventNT.getTitle());
        ps.setString(2, eventNT.getDescription());
        Date startDateEventNT = new Date(eventNT.getDateOfEvent().getTimeInMillis());
        ps.setDate(3, startDateEventNT);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    /**
     * Identical method to the method above.
     * @param oldEventTitle
     * @param eventTime
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void updateEventTime(String oldEventTitle ,EventTime eventTime) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();

        String myQuery = "UPDATE eventtime SET title = ?, description = ? ,startdate = ?, enddate = ? WHERE title ='"+oldEventTitle+"'";

        PreparedStatement ps = conn.prepareStatement(myQuery);
        ps.setString(1,eventTime.getTitle());
        ps.setString(2, eventTime.getDescription());
        Date startDateEventNT = new Date(eventTime.getStartTime().getTimeInMillis());
        ps.setDate(3, startDateEventNT);
        Date endDateEventTime = new Date(eventTime.getFinishTime().getTimeInMillis());
        ps.setDate(4, endDateEventTime);
        ps.executeUpdate();
        ps.close();
        conn.close();
    }


    public Connection openConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {

        DriverManager.setLoginTimeout(5);
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/timeline","root","");;
        return conn;
    }

}
