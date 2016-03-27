

import CustomExceptions.ReportErrorToUserException;
import Model.Project.TheDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class TheDateTest {

		private int day;
		private int month;
		private int year;
		private String str;
		
		@Before
		public void initialization()
		{
			day = 24;
			month = 2;
			year = 2016;
			str = "24/02/2016";
		}
		
		@Test 
		public void constructor1Param_SUCCES() throws ReportErrorToUserException
		{
			TheDate d = new TheDate(str);
			assertEquals(str,d.toString());
		}
		
		@Test (expected = ReportErrorToUserException.class)
		public void constructor1Param_FAIL() throws ReportErrorToUserException
		{
			TheDate d = new TheDate("10/50/10");
		}


		@Test
		public void constructor3Param_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			
			assertEquals(d.getDay(),day);
			assertEquals(d.getMonth(),month);
			assertEquals(d.getYear(),year);
		}
		
		@Test (expected = ReportErrorToUserException.class)
		public void constructor3Param_FAIL() throws ReportErrorToUserException {
			TheDate d = new TheDate(50,0,10);
		}
		
		@Test
		public void toString_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			assertEquals(d.toString(),str);
		}
		
		@Test
		public void TheDateNow_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),LocalDate.now().getYear());
			assertEquals(TheDate.TheDateNow(),d);
		}
		
		@Test
		public void dateNow_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),LocalDate.now().getYear());
			assertEquals(TheDate.dateNow(),d.toString());
		}
		
		@Test
		public void equals_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate a = new TheDate(24,2,2016);
			
			assertEquals(d, a);
		}
		
		@Test
		public void equals_FAILVALUE() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate a = new TheDate(24,2,2015);
			
			assertFalse(d.equals(a));
		}
		
		@Test
		public void equals_FAILNULL() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate a = null;
			
			assertFalse(d.equals(a));
		}
		
		@Test
		public void equals_FAILINSTANCE() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			String a = new String("");
			
			assertFalse(d.equals(a));
		}
		
		@Test
		public void isAfter_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate dd = new TheDate(day,month,year+1);
			
			assertTrue(dd.isAfter(d));
		}
		
		@Test
		public void isAfter_FAIL() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate dd = new TheDate(day,month,year+1);
			
			assertFalse(d.isAfter(dd));
		}
		
		@Test
		public void isAfter_FAILNULL() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			
			assertFalse(d.isAfter(null));
		}
		
		@Test
		public void isBefore_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate dd = new TheDate(day,month,year+1);
			
			assertTrue(d.isBefore(dd));
		}
		
		@Test
		public void isBefore_FAIL() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate dd = new TheDate(day,month,year+1);
			
			assertFalse(dd.isBefore(d));
		}
		
		@Test
		public void isBefore_FAILNULL() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			
			assertFalse(d.isBefore(null));
		}
		
		
		@Test
		public void copy_SUCCES() throws ReportErrorToUserException {
			TheDate d = new TheDate(day,month,year);
			TheDate dd = d.copy();
			assertEquals(d,dd);
		}
		
		
		
}
