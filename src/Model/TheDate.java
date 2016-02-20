package Model;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TheDate
{
	static Locale locale = Locale.getDefault();
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date date;

	public TheDate(int day, int month, int year)
	{
		date = new Date(day,month,year);	
	}
	
	@Override
	public String toString()
	{
		return dateFormat.format(date);
	}
}