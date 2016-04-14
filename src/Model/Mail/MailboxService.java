package Model.Mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.*;
import Model.Project.Project;
import Model.Project.SubSystem;

import Model.User.User;
import Model.User.UserService;

/**
 * Class that provides methods to handle with mailbox and registrations operations
 *
 */
public class MailboxService 
{
	private BugReportService bugReportService;
	private UserService userService;
	
	/**
	 * Constructor 
	 * 
	 * @param bugReportService
	 * @param userService
	 */
	public MailboxService(BugReportService bugReportService, UserService userService)
	{
		this.bugReportService = bugReportService;
		this.userService = userService;
	}
	/**
	 * Method to get a list of notifications
	 * 
	 * @param user The user's mailbox
	 * @param number The number of notifications to get 
	 * 
	 * @return The list of notifications
	 * 
	 * @throws ReportErrorToUserException when the given number is incorrect
	 */
	public List<Notification> getNotifications(User user, int number) throws ReportErrorToUserException
	{
		List<Notification> listOriginal = user.getMailbox().getNotifications();
		List<Notification> listOrderRecent = reverse(listOriginal);
		try
		{
			 List<Notification> listSubbed = listOrderRecent.subList(0, number);
			 List<Notification> listFiltered = new ArrayList<>();
			 for(Notification not : listSubbed)
				 if(bugReportService.isVisibleByUser(user, not.getBugReport()))
					 listFiltered.add(not);
			 return listFiltered;
		}
		catch(IllegalArgumentException e)
		{
			throw new ReportErrorToUserException("Invalid index input");
		}
	}
	
	/**
	 * Method to get all registrations of a given user
	 *  
	 * @param user The user to retrieve it's registrations 
	 * 
	 * @return The list of registrations
	 */
	public List<ObserverAspect> getRegistrations(User user)
	{
		return user.getMailbox().getRegistrations();
	}
	
	/**
	 * Method to register for a bug report creation in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 */
	public void registerCreationBugReport(User user, Subject s)
	{
		user.getMailbox().registerBugReport(s);
	}
	
	/**
	 * Method to register for a changed tag in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 */
	public void registerTag(User user, Subject s)
	{
		user.getMailbox().registerTag(s);
	}
	
	/**
	 * Method to register for a change to a specific tag in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 * @param tag The tag change to be notified
	 */
	public void registerSpecificTag(User user, Subject s, Tag tag)
	{
		user.getMailbox().registerSpecificTag(s,tag);
	}
	
	/**
	 * Method to register for a comment creation in the given subject
	 * 
	 * @param user The user that wants to register
	 * 
	 * @param s The subject to observe
	 */
	public void registerComment(User user, Subject s)
	{
		user.getMailbox().registerComment(s);
	}
	
	/**
	 * Method to unregister for a given registration
	 * 
	 * @param user The user that wants to register
	 * 
	 * @param registration The registration to unregister
	 */
	public void unregister(User user, ObserverAspect registration)
	{
		user.getMailbox().unregister(registration);
	}
	
	private List<Notification> reverse(List<Notification> list)
	{
	    List<Notification> nList = new ArrayList<>();
	    for(int index = list.size() - 1; index >= 0; index--)
	    	nList.add(list.get(index));
	    
	    return nList;
	}
	
	/**
	 * Method to get all mailboxes of the system
	 * 
	 * @return The list of mailboxes
	 */
	public List<Mailbox> getAllMailboxes()
	{
		List<Mailbox> mailboxes = new ArrayList<>();
		for(User user : userService.getUserList())
			mailboxes.add(user.getMailbox());
		return mailboxes;
	}
	
}
