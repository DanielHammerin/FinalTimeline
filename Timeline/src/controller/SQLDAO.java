package controller;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import model.*;

import java.applet.Applet;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by Alexander on 24/05/2015.
 */
public class SQLDAO {

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
                PreparedStatement pStatementEventTime= conn.prepareStatement(queryEventTime);

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
            e.printStackTrace();
        }
    }

    public LinkedList<DayTimeline> getAllTimelines() {
        String myQuery = "SELECT * FROM daytimelines";

        Connection c = null;
        ;
        LinkedList<DayTimeline> allTimelines = new LinkedList<DayTimeline>();
        try {
            c = openConnection();

            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(myQuery);

            while (rs.next()) {
                DayTimeline dayTimeline = new DayTimeline();
                dayTimeline.setTitle(rs.getString("Title"));
                dayTimeline.setDescription(rs.getString("Description"));
                GregorianCalendar startDate = new GregorianCalendar();
                startDate.setTime(rs.getDate(3));
                dayTimeline.setStartDate(startDate);
                GregorianCalendar endDate = new GregorianCalendar();
                endDate.setTime(rs.getDate(4));
                dayTimeline.setEndDate(endDate);
                System.out.println(dayTimeline.getTitle() + "\t" + dayTimeline.getDescription() +
                        "\t" + startDate.toString() + "\t" + endDate.toString());
                allTimelines.add(dayTimeline);

                dayTimeline.setEventNTs(getEventsNT(dayTimeline));
                dayTimeline.setEventTimes(getEventsTime(dayTimeline));
            }
            c.close();
        } catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return allTimelines;
    }


    public DayTimeline getTimeline(String title) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String myQuery = "SELECT *\n" +
                "FROM DayTimelines\n" +
                "WHERE Title = '"+title+"'" ;
        DayTimeline dayTimeline = new DayTimeline();
        Connection c = openConnection();
        try {
            c = openConnection();
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
                System.out.println(dayTimeline.getTitle() + "\t" + dayTimeline.getDescription() +
                        "\t" + startDate.toString() + "\t" + endDate.toString());

                dayTimeline.setEventNTs(getEventsNT(dayTimeline));
                dayTimeline.setEventTimes(getEventsTime(dayTimeline));
            }
            c.close();
        }catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return dayTimeline;
    }

    public TreeSet<EventNT> getEventsNT(DayTimeline dayTimeline) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        TreeSet<EventNT> events = new TreeSet<EventNT>();
        String myQuery;
        Connection conn = openConnection();

        myQuery = "SELECT *\n" +
                "FROM eventnotime\n" +
                "WHERE timeline = '"+dayTimeline.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);

        while(rs.next()){
            GregorianCalendar g = new GregorianCalendar() ;
            g.setTime(rs.getDate("startdate"));
            EventNT eventNT = new EventNT(rs.getString("Title"),rs.getString("Description"),g);
            events.add(eventNT);
        }
        return  events;
    }

    public TreeSet<EventTime> getEventsTime(DayTimeline dayTimeline) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        TreeSet<EventTime> events = new TreeSet<EventTime>();
        String myQuery;
        Connection conn = openConnection();

        myQuery = "SELECT *\n" +
                "FROM eventtime\n" +
                "WHERE timeline = '"+dayTimeline.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);


        GregorianCalendar startDate = new GregorianCalendar() ;
        GregorianCalendar endDate = new GregorianCalendar() ;
        while(rs.next()){
            startDate.setTime(rs.getDate("startdate"));
            endDate.setTime(rs.getDate("enddate"));

            EventTime eventTime = new EventTime(rs.getString("Title"), rs.getString("Description"),startDate,endDate);
            events.add(eventTime);
        }
        return events;
    }

    public boolean isThereADuplicate(DayTimeline dayTimeline){
        LinkedList<DayTimeline> allTimelines2 = getAllTimelines();
        for(DayTimeline dtl : allTimelines2){
            System.out.println(dtl.getTitle()+" = "+dayTimeline.getTitle());
            if(Objects.equals(dtl.getTitle(), dayTimeline.getTitle())){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Duplicate Timeline");
                a.setContentText("There exists an timeline called"+dtl.getTitle());
                return true;
            }
        }
        return  false;
    }

    public String getTimelineFromEventNT(EventNT eventNT) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        String dayTimelineTitle = "";
        String myQuery = "SELECT timeline FROM eventnotime WHERE title = '"+eventNT.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);
        while(rs.next()){
            dayTimelineTitle = rs.getString(1);
        }
        return dayTimelineTitle;
    }

    public String getTimelineFromEventTime(EventTime eventTime) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        String dayTimelineTitle = "";
        String myQuery = "SELECT timeline FROM eventtime WHERE title = '"+eventTime.getTitle()+"'" ;

        ResultSet rs = conn.createStatement().executeQuery(myQuery);
        while(rs.next()){
            dayTimelineTitle = rs.getString(1);
        }
        //J
        return dayTimelineTitle;
    }

    public void deleteTimeline(String title){
        String myQuery = "DELETE from daytimelines where title='"+title+"'";

        Statement s = null;
        try {
            Connection c = openConnection();
            s = c.createStatement();
            s.executeUpdate(myQuery);
        }catch (ClassNotFoundException | SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void updateEventNT(String oldEventTitle, EventNT eventNT) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        DayTimeline dayTimeline = new DayTimeline();

        String myQuery = "UPDATE eventnotime SET title = ?, description = ? ,startdate = ? WHERE title ='"+oldEventTitle+"'";

        PreparedStatement ps = conn.prepareStatement(myQuery);
        ps.setString(1,eventNT.getTitle());
        ps.setString(2, eventNT.getDescription());
        Date startDateEventNT = new Date(eventNT.getDateOfEvent().getTimeInMillis());
        ps.setDate(3, startDateEventNT);
        ps.executeUpdate();
        ps.close();
    }

    public void updateEventTime(String oldEventTitle ,EventTime eventTime) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Connection conn = openConnection();
        DayTimeline dayTimeline = new DayTimeline();

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
    }



    public Connection openConnection() throws ClassNotFoundException, SQLException, IllegalAccessException, InstantiationException {
        Properties properties = new Properties();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/timeline","root","");
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return conn;
    }

}
