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
	 * @param bugReportService The bugreport service to use.
	 * @param userService The user service to use.
	 * 
	 * @throws IllegalArgumentException the bugreportservice or userservice is null
	 */
	public MailboxService(BugReportService bugReportService, UserService userService)
	{
		if(bugReportService == null) throw new IllegalArgumentException("The bugreportservice cannot be null");
		if(userService == null) throw new IllegalArgumentException("The userservice cannot be null");

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
	 * @throws IllegalArgumentException the user is null or negative number
	 * 
	 * @throws IndexOutOfBoundsException when the given number is incorrect
	 */
	public List<Notification> getNotifications(User user, int number) throws IndexOutOfBoundsException
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		if(number < 0) throw new IllegalArgumentException("The number cannot be negative");
		
		List<Notification> listOriginal = user.getMailbox().getNotifications();
		List<Notification> listOrderRecent = reverse(listOriginal);
		List<Notification> listSubbed = listOrderRecent.subList(0, number);
		
		List<Notification> listFiltered = new ArrayList<>();
		for(Notification not : listSubbed)
			if(bugReportService.isVisibleByUser(user, not.getBugReport()))
				listFiltered.add(not);
		return Collections.unmodifiableList(listFiltered);

	}
	
	/**
	 * Method to get all registrations of a given user
	 *  
	 * @param user The user to retrieve it's registrations 
	 * 
	 * @return The list of registrations
	 * 
	 * @throws IllegalArgumentException the user is null
	 */
	public List<ObserverAspect> getRegistrations(User user)
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		return Collections.unmodifiableList(user.getMailbox().getRegistrations());
	}
	
	/**
	 * Method to register for a bug report creation in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 * 
	 * @throws IllegalArgumentException the user or subject is null
	 */
	public void registerCreationBugReport(User user, Subject s)
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		if(s == null) throw new IllegalArgumentException("Subject cannot be null");
		user.getMailbox().registerBugReport(s);
	}
	
	/**
	 * Method to register for a changed tag in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 * 
	 * @throws IllegalArgumentException the user or subject is null
	 */
	public void registerTag(User user, Subject s)
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		if(s == null) throw new IllegalArgumentException("Subject cannot be null");
		user.getMailbox().registerTag(s);
	}
	
	/**
	 * Method to register for a change to a specific tag in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 * @param tag The tag change to be notified
	 * 
	 * @throws IllegalArgumentException the user or subject or tag is null
	 */
	public void registerSpecificTag(User user, Subject s, Tag tag)
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		if(s == null) throw new IllegalArgumentException("Subject cannot be null");
		if(tag == null) throw new IllegalArgumentException("Tag cannot be null");
		user.getMailbox().registerSpecificTag(s,tag);
	}
	
	/**
	 * Method to register for a comment creation in the given subject
	 * 
	 * @param user The user that wants to register
	 * @param s The subject to observe
	 * 
	 * @throws IllegalArgumentException the user or subject is null
	 */
	public void registerComment(User user, Subject s)
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		if(s == null) throw new IllegalArgumentException("Subject cannot be null");
		user.getMailbox().registerComment(s);
	}
	
	/**
	 * Method to unregister for a given registration
	 * 
	 * @param user The user that wants to register
	 * @param registration The registration to unregister
	 * 
	 * @throws IllegalArgumentException the user or registration is null
	 */
	public void unregister(User user, ObserverAspect registration)
	{
		if(user == null) throw new IllegalArgumentException("User cannot be null");
		if(registration == null) throw new IllegalArgumentException("The registration cannot be null");
		user.getMailbox().unregister(registration);
	}
	
	private List<Notification> reverse(List<Notification> list)
	{
		if(list == null) throw new IllegalArgumentException("List cannot be null");
		
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
		return Collections.unmodifiableList(mailboxes);
	}
	
}
