package ProjectPackageTest;

import CustomExceptions.ModelException;
import Model.Project.TheDate;
import org.junit.Before;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
		public void constructor3Param_SUCCES() throws ModelException {
			TheDate d = new TheDate(day,month,year);
			
			assertEquals(d.getDay(),day);
			assertEquals(d.getMonth(),month);
			assertEquals(d.getYear(),year);
		}
		
		@Test (expected = DateTimeException.class)
		public void constructor3Param_FAIL() throws ModelException {
			TheDate d = new TheDate(50,0,10);
		}
		
		@Test
		public void toString_SUCCES() throws ModelException {
			TheDate d = new TheDate(day,month,year);
			assertEquals(d.toString(),str);
		}
		
		@Test
		public void TheDateNow_SUCCES() throws ModelException {
			TheDate d = new TheDate(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),LocalDate.now().getYear());
			assertEquals(TheDate.TheDateNow(),d);
		}
		
		@Test
		public void dateNow_SUCCES() throws ModelException {
			TheDate d = new TheDate(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),LocalDate.now().getYear());
			assertEquals(TheDate.dateNow(),d.toString());
		}
		
		@Test
		public void equals_SUCCES() throws ModelException {
			TheDate d = new TheDate(day,month,year);
			TheDate a = new TheDate(24,2,2016);
			
			assertEquals(d, a);
		}
		
		@Test
		public void equals_FAILVALUE() throws ModelException {
			TheDate d = new TheDate(day,month,year);
			TheDate a = new TheDate(24,2,2015);
			
			assertFalse(d.equals(a));
		}
		
		@Test
		public void equals_FAILNULL() throws ModelException {
			TheDate d = new TheDate(day,month,year);
			TheDate a = null;
			
			assertFalse(d.equals(a));
		}
		
		@Test
		public void equals_FAILINSTANCE() throws ModelException {
			TheDate d = new TheDate(day,month,year);
			String a = new String("");
			
			assertFalse(d.equals(a));
		}
		
		
		
		
}
