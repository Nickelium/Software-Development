package Model.Mail;

import Model.BugReport.BugReport;

/**
 * Class that represents a notification
 *
 */
public class Notification 
{
	String changeDescription;
	Subject subject;
	Subject structure;
	
	/**
	 * Constructor of this notification
	 *
	 * @param changeDescription A description of this notification
	 * @param bugReport The bug report that has changed
	 * @param structure The subject structure where the change happened
	 * 
	 * @throws IllegalArgumentException the description, bureport or structure is null
	 */
	Notification(String changeDescription, Subject subject, Subject structure)
	{
		if(changeDescription == null) throw new IllegalArgumentException("The description cannot be null");
		if(subject == null) throw new IllegalArgumentException("The subject cannot be null");
		if(structure == null) throw new IllegalArgumentException("The structure cannot be null");
		
		this.changeDescription = changeDescription;
		this.subject = subject;
		this.structure = structure;
	}
	
	/**
	 * Getter to get the description of this notification
	 * 
	 * @return The description of this notification
	 */
	public String getChangeDescription()
	{
		return changeDescription;
	}
	
	/**
	 * Getter to get the bug report that has changed
	 * 
	 * @return The bug report that has changed
	 */
	public Subject getSubject()
	{
		return subject;
	}
	
	/**
	 * Getter to get the structure where the change occurred
	 * 
	 * @return The subject structure where the change occured
	 */
	public Subject getStructure()
	{
		return structure;
	}
	
	
	/**
	 * Method to represent this object as a string representation
	 * 
	 * @return The string representation of this object
	 */
	@Override
	public String toString()
	{
		return  changeDescription + "\n"  + subject  + "\n in \n" + structure +"\n";
	}
}
