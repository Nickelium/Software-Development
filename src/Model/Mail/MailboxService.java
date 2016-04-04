package Model.Mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.*;
import Model.Project.Project;
import Model.Project.SubSystem;

import Model.User.User;

public class MailboxService 
{
	public List<Notification> getNotifications(User user, int number) throws ReportErrorToUserException
	{
		List<Notification> listOriginal = user.getMailbox().getNotifications();
		List<Notification> listOrderRecent = reverse(listOriginal);
		try
		{
			return listOrderRecent.subList(0, number);
		}
		catch(IllegalArgumentException e)
		{
			throw new ReportErrorToUserException("Invalid index input");
		}
	}
	
	public List<ObserverAspect> getRegistrations(User user)
	{
		return user.getMailbox().getRegistrations();
	}
	
	public void registerCreationBugReport(User user, Subject s)
	{
		user.getMailbox().registerBugReport(s);
	}
	
	public void registerTag(User user, Subject s)
	{
		user.getMailbox().registerTag(s);
	}
	
	public void registerSpecificTag(User user, Subject s, Tag tag)
	{
		user.getMailbox().registerSpecificTag(s,tag);
	}
	
	public void registerComment(User user, Subject s)
	{
		user.getMailbox().registerComment(s);
	}
	
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
	
}
