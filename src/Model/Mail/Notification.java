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
	 * @param str The content of this notification
	 */
	Notification(String changeDescription, BugReport bugReport, Subject structure)
	{
		this.changeDescription = changeDescription;
		this.bugReport = bugReport;
		this.structure = structure;
	}
	
	/**
	 * Getter to get the content of this notification
	 * 
	 * @return The content
	 */
	public String getChangeDescription()
	{
		return changeDescription;
	}
	
	public BugReport getBugReport()
	{
		return bugReport;
	}
	
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
