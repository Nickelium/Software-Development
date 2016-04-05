package Model.Mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.BugReport.*;

/**
 * Class that represents a mailbox of a user.
 *
 */
public class Mailbox 
{
	private List<Notification> notifications = new ArrayList<>();
	
	private List<ObserverAspect> registrations = new ArrayList<>();
	
	/**
	 * Constructor of this mailbox object
	 * 
	 */
	public Mailbox() {}
	
	/**
	 * Method to get all notifications of this mailbox.
	 * 
	 * @return An unmodifiable list of notifications of this mailbox
	 */
	public List<Notification> getNotifications()
	{
		return Collections.unmodifiableList(this.notifications);
	}
	
	/**
	 * Method to get all registrations of this mailbox.
	 * 
	 * @return An unmodifiable list of ObserverAspect of this mailbox
	 */
	public List<ObserverAspect> getRegistrations()
	{
		return Collections.unmodifiableList(this.registrations);
	}
	
	/**
	 * Method to register for a creation of a new bugreport in the given subject
	 * 
	 * @param s
	 */
	public void registerBugReport(Subject s)
	{
		ObserverBugReport ObBug = new ObserverBugReport(s);
		registrations.add(ObBug);
	}
	
	public void registerComment(Subject s)
	{
		ObserverComment ObComm = new ObserverComment(s);
		registrations.add(ObComm);
	}
	
	public void registerTag(Subject s)
	{
		ObserverTag ObTag = new ObserverTag(s);
		registrations.add(ObTag);
	}
	
	public void registerSpecificTag(Subject s, Tag tag)
	{
		ObserverSpecificTag ObSTag = new ObserverSpecificTag(s, tag);
		registrations.add(ObSTag);
	}

	public void unregister(ObserverAspect registration)
	{
		if(registrations.contains(registration))
			registration.destructor();
	}
	
	private class ObserverBugReport extends ObserverAspect
	{
		public ObserverBugReport(Subject s)
		{
			super(s);
		}
		
		@Override
		public void update(Subject s, Object aspect) {
			if(super.s == s && aspect instanceof BugReport)
				notifications.add(new Notification("New bugreport in \n" + super.s));
		}

		@Override
		public String toString() {
			return "Registration for creation of new bugreport in \n" + super.s;
		}
		
	}
	
	private class ObserverTag extends ObserverAspect
	{		
		public ObserverTag(Subject s)
		{
			super(s);
		}
		
		@Override
		public void update(Subject s, Object aspect) 
		{
			if(super.s == s && aspect instanceof Tag)
				notifications.add(new Notification("Tag changed in \n" + super.s));
		}
		
		@Override
		public String toString() {
			return "Registration for change of tag in \n" + super.s;
		}
		
		
	}
	
	private class ObserverSpecificTag extends ObserverAspect
	{
		private Tag tag;
		
		public ObserverSpecificTag(Subject s, Tag tag)
		{
			super(s);
			this.tag = tag;
		}
		
		@Override
		public void update(Subject s, Object aspect) 
		{
			if(super.s == s && tag.getClass().isInstance(aspect))
				notifications.add(new Notification("Tag changed to " + tag + " in \n" + super.s));
			
		}
		
		@Override
		public String toString() {
			return "Registration for changed of tag to " + tag + " in \n" + super.s;
		}
		
	}
	
	private class ObserverComment extends ObserverAspect
	{
		private Subject s;
		
		public ObserverComment(Subject s)
		{
			super(s);
		}
		
		@Override
		public void update(Subject s, Object aspect)
		{
			if(super.s == s && aspect instanceof Comment)
				notifications.add(new Notification("New comment in \n" + super.s));	
		}
		
		@Override
		public String toString() {
			return "Registration for creation of new comment in \n" + super.s;
		}
	}
}
