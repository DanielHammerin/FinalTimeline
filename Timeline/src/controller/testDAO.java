package controller;

import java.util.GregorianCalendar;
import model.DayTimeline;
import model.MonthTimeline;
import model.YearTimeline;

public class testDAO {

	public static void main(String[] args) {
		
		try {
		
		DAO timeline = new DAO ();
		
		GregorianCalendar start = new GregorianCalendar(1917,11,24);// dates for the yeartimeline
		GregorianCalendar finish = new GregorianCalendar(1989,11,24);
		
		GregorianCalendar start2 = new GregorianCalendar(2014,6,15); // dates for the monthtimeline
		GregorianCalendar finish2 = new GregorianCalendar(2014,7,25);
		
		GregorianCalendar start3 = new GregorianCalendar(2015,5,10); // dates for the daytimelines
		GregorianCalendar finish3 = new GregorianCalendar(2015,5,17);
		
		YearTimeline soviet = new YearTimeline ("Soviet Union", "Time line of the extension of the Soviet Union in Europe", start, finish);
		MonthTimeline brazil = new MonthTimeline ("FIFA World Cup Schedule", "Fixture of the World Cup Brazil 2014", start2, finish2);
		DayTimeline week26 = new DayTimeline ("Schedule for Week 26", "Calendar with the schedule for week 26 for students in the Software Technology program", start3, finish3);
		
		timeline.printDatabase();
		timeline.clearDatabase();
		
		timeline.saveToDataBase(soviet);
		timeline.saveToDataBase(brazil);
		timeline.saveToDataBase(week26);
		
		timeline.printDatabase();
		
//		timeline.deleteFromDatabase(week26);
//		timeline.printDatabase();
		
		System.out.println (timeline.lookUp("Soviet Union"));
		System.out.println (timeline.lookUp("Schedule for Week 26"));
		System.out.println (timeline.lookUp("FIFA World Cup Schedulelll"));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
