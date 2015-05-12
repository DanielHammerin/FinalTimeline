package controller;

import java.util.GregorianCalendar;
import model.MonthTimeline;
import model.YearTimeline;

public class Test {

	public static void main(String[] args) throws Exception {
		DAO dao = new DAO();
		GregorianCalendar start = new GregorianCalendar(1996, 1, 1);
		GregorianCalendar end1 = new GregorianCalendar(1997, 1, 1);
		GregorianCalendar end2 = new GregorianCalendar(1999, 1, 1);

		MonthTimeline month = new MonthTimeline("Hello", "wadup", start, end1);
		YearTimeline year = new YearTimeline("yo", "haha!", start, end2);
		dao.clearDatabase();
		dao.saveToDataBase(month);
		
		dao.getTimeline("hello"); 
				
		dao.updateTimelineV2(month, year);
		
		System.out.println(dao.getTimeline("hello"));
		System.out.println(dao.getTimeline("yo").getTitle());
	}

}
