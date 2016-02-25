package Model.Project;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Class for uniform dateformats
public class TheDate
{
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate date;
	
	/**
	 * Construct a new instance of date with given day, month and year
	 * @param day
	 * @param month
	 * @param year
	 */
	public TheDate(int day, int month, int year) throws DateTimeException
	{
		date = LocalDate.of(year,month,day);	
	}
	
	/**
	 * Return the day of the date
	 * @return Int value of the day
	 */
	public int getDay()
	{
		return date.getDayOfMonth();
	}
	
	/**
	 * Return the month of the date
	 * @return Int value of the month
	 */
	public int getMonth()
	{
		return date.getMonthValue();
	}
	
	/**
	 * Return the year of the date
	 * @return Int value of the year
	 */
	public int getYear()
	{
		return date.getYear();
	}
	
	/**
	 * Return string format of current time zone date
	 * @return String format of current time zone date
	 */
	public static String dateNow()
	{
		return TheDateNow().toString();
	}
	
	/**
	 * Return the date of current time zone date
	 * @return TheDate of current time zone date
	 */
	public static TheDate TheDateNow()
	{
		return new TheDate(LocalDate.now().getDayOfMonth(),LocalDate.now().getMonthValue(),LocalDate.now().getYear());
	}
	/**
	 * Returns the string representation of this object
	 * @Returns String format of this object
	 */
	@Override
	public String toString()
	{
		return  date.format(formatter);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if( !(obj instanceof TheDate) ) return false;
		
		
		return getDay() == ((TheDate) obj).getDay() 
				&& getMonth() == ((TheDate) obj).getMonth() 
				&& getYear() == ((TheDate) obj).getYear();
		
	}
}