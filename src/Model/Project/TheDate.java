package Model.Project;

import CustomExceptions.ModelException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Class for uniform dateformats
public class TheDate
{
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate date;


	/**
	 * Constructor for the date that accepts a string representation of the date. (dd/MM/yyyy)
	 *
	 * @param stringDate String representation of the date. (dd/MM/yyyy)
	 *
	 * @throws ModelException The given string date cannot be parsed to a date.
     */
	public TheDate(String stringDate) throws ModelException {
		try {
			String[] parts = retrieveParts(stringDate);
			this.date = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
		} catch (Exception e){
			throw new ModelException("Incorrect date format!");
		}
	}
	
	private String[] retrieveParts(String stringDate)
	{
		return stringDate.split("/");
	}

	/**
	 * Constuctor to create a date instance with given arguments.
	 *
	 * @param day The day of the date.
	 * @param month The month of the date.
	 * @param year The year of the date.
	 * @throws ModelException
	 *
	 * @throws DateTimeException The given attributes are not valid.
	 */
	public TheDate(int day, int month, int year) throws ModelException
	{
		this.date = LocalDate.of(year,month,day);
	}

    private TheDate(){
        this.date = LocalDate.now();
    }
	
	/**
	 * Getter to request the day of the date.
	 *
     * @return Int value of the day
	 */
	public int getDay()
	{
		return date.getDayOfMonth();
	}
	
	/**
	 * Getter to request the month of the date.
     *
	 * @return Int value of the month
	 */
	public int getMonth()
	{
		return date.getMonthValue();
	}
	
	/**
	 * Getter to request the year of the date.
     *
	 * @return Int value of the year
	 */
	public int getYear()
	{
		return date.getYear();
	}

	/**
	 * Overrided string method.
     *
	 * @return String format of this object
	 */
	@Override
	public String toString()
	{
		return  date.format(formatter);
	}


    /**
     * Overrided equals method to make two dates with same value equal.
     *
     * @param obj The object to check the equality with.
     *
     * @return True if the dates have the same value.
     */
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if( !(obj instanceof TheDate)  ) return false;
		return date.equals(((TheDate)obj).date);
	}

    /**
     * Checker to check if the current date is later than the given one.
     *
     * @param date The date to check.
     *
     * @return True if the current date is later than the given one.
     */
	public boolean isAfter(TheDate date)
	{
		return this.date.isAfter(date.date);
	}

    /**
     * Checker to check if the current date is earlier than the give one.
     *
     * @param date The date to check.
     *
     * @return True if the current date is earlier than the given one.
     */
	public boolean isBefore(TheDate date)
	{
		return this.date.isBefore(date.date);
	}

	/**
	 * Method to get the string format of the current date.
     *
	 * @return String format of current time zone date
	 */
	public static String dateNow()
	{
		return TheDateNow().toString();
	}

	/**
	 * Method to get a new date object of the current date.
     *
	 * @return TheDate of current time zone date
	 */
	public static TheDate TheDateNow()
	{
		return new TheDate();
	}
	
	/**
	 * Method to copy a thedate object
	 * 
	 * @return The copied date
	 * 
	 * @throws ModelException The attributes of thedate object are not valid.
	 */
	public TheDate copy() throws ModelException
	{
		return new TheDate(getDay(),getMonth(),getYear());
	}
}