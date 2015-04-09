package model;

import java.util.Calendar;

import com.db4o.*;
import com.tedneward.model.*;

public class databaseTest {

	public static void main(String[] args) {
		
		Calendar start = new Calendar ();
		Calendar finish = new Calendar (THURSDAY);
		
		EventTime firstSemester = new EventTime ("First Semester", "First Semerster for Software Technology Program", start, finish);
		
		Calendar start2 = new Calendar (2015-3-15);
		Calendar finish2 = new Calendar (2015-6-7);
		
		EventTime secondSemester = new EventTime ("Second Semester", "Second Semerster for Software Technology Program", start2, finish2);
		
		
		}
	}