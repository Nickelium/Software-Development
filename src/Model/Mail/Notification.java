package Model.Mail;

import Model.BugReport.BugReport;

/**
 * Class that represents a notification
 *
 */
public class Notification 
{
	String changeDescription;
	BugReport bugReport;
	Subject structure;
	
	/**
	 * Constructor of this notification
	 *
	 * @param changeDescription A description of this notification
	 * @param bugReport The bug report that has changed
	 * @param structure The subject structure where the change happened
	 */
	Notification(String changeDescription, BugReport bugReport, Subject structure)
	{
		this.changeDescription = changeDescription;
		this.bugReport = bugReport;
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
	public BugReport getBugReport()
	{
		return bugReport;
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
		if(changeDescription != null & bugReport != null & structure != null)
			return  changeDescription + "\n"  + bugReport  + "\n in \n" + structure +"\n";
		return "";
	}
}
