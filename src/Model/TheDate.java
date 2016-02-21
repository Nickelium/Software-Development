package Model;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

//Class for uniform dateformats
public class TheDate
{
	static Locale locale = Locale.getDefault();
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	//Deprecrated but who cares ?
	private Date date;

	/**
	 * Construct a new instance of date
	 */
	public TheDate()
	{
		date = new Date();
	}
	
	/**
	 * Construct a new instance of date with given day, month and year
	 * @param day
	 * @param month
	 * @param year
	 */
	public TheDate(int day, int month, int year)
	{
		date = new Date(day,month,year);	
	}
	
	/**
	 * Returns the string representation of this object
	 * @Returns String format of this object
	 */
	@Override
	public String toString()
	{
		return dateFormat.format(date);
	}
}