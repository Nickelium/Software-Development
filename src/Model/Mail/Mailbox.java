package Model.Mail;

import Model.BugReport.BugReport;
import Model.BugReport.Comment;
import Model.BugReport.Tag;
import Model.Memento.Memento;
import Model.Memento.Originator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that represents a mailbox of a user.
 * A mailbox contains all notifications of which a user is registred to.
 * //TODO Documenteren van Mail package !!!
 */
public class Mailbox implements Originator<Mailbox.MailboxMemento, Mailbox>
{
	private List<Notification> notifications;
	private List<ObserverAspect> registrations;
	
	/**
	 * Constructor of this mailbox object
	 * 
	 */
	public Mailbox() {
		this.notifications = new ArrayList<>();
		this.registrations = new ArrayList<>();
	}
	
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
	 * Method to register for a creation of a new bug report in the given subject
	 *
	 * @param structure The subject to observe
	 */
	public void registerBugReport(Subject structure)
	{
		ObserverBugReport ObBug = new ObserverBugReport(structure);
		registrations.add(ObBug);
	}
	
	/**
	 * Method to register for a creation of a new comment in the given subject
	 *
	 * @param structure The subject to observe
	 */
	public void registerComment(Subject structure)
	{
		ObserverComment ObComm = new ObserverComment(structure);
		registrations.add(ObComm);
	}
	
	/**
	 * Method to register for a change of a tag of a bug report in the given subject
	 *
	 * @param structure The subject to observe
	 */
	public void registerTag(Subject structure)
	{
		ObserverTag ObTag = new ObserverTag(structure);
		registrations.add(ObTag);
	}
	
	/**
	 * Method to register for a change of a tag to a specific tag of a bug report in the given subject
	 *
	 * @param structure The subject to observe
	 * @param tag The specific tag 
	 */
	public void registerSpecificTag(Subject structure, Tag tag)
	{
		ObserverSpecificTag ObSTag = new ObserverSpecificTag(structure, tag);
		registrations.add(ObSTag);
	}

	/**
	 * Method to unregister from a registration
	 * 
	 * @param registration The observeraspect to unregister
	 */
	public void unregister(ObserverAspect registration)
	{
		if(registrations.contains(registration))
			registration.unbind();
		
		registrations.remove(registration);
	}
	
	@Override
	public MailboxMemento createMemento() 
	{
		return new MailboxMemento(this);
	}

	@Override
	public void restoreMemento(MailboxMemento memento)
	{
		this.registrations	= new ArrayList<>(memento.getRegistrations());	
	}
	
    //Innerclass Memento
    /**
     * This class provides utility for saving the state of the system at a certain point in time
     * during execution of the Bug Trap software.
     *
     * The mailbox memento saves the state of the following attributes of the mailbox:
     * registrations.
     *
     * This class provides private methods to request the values of the saved fields.
     * This wide interface (private getters) is provided to the class Bugreport,
     * while the narrow interface (public constructor) is provided to any class.
     */
	public class MailboxMemento extends Memento<Mailbox>
	{
		private List<ObserverAspect> registrations;
		
		/**
    	 * Constructor 
    	 * 
    	 * @param originator The originator to build a memento from
    	 */
		public MailboxMemento(Mailbox originator) 
		{
			super(originator);
			registrations = originator.getRegistrations();
		}
		
		private List<ObserverAspect> getRegistrations()
		{
			return new ArrayList<>(registrations);
		}
	}

	
	/**
	 * Inner class observer of a creation of a bug report
	 *
	 */
	private class ObserverBugReport extends ObserverAspect
	{
		public ObserverBugReport(Subject structure)
		{
			super(structure);
		}
		
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect) {
			if(super.structure == structure && aspect instanceof BugReport)
				notifications.add(new Notification("New bug report",bugReport, super.structure));
			
		}

		@Override
		public String toString() {
			return "Registration for creation of new bug report in \n" + super.structure;
		}
		
	}
	
	/**
	 * Inner class observer of a change of tag of a bug report
	 *
	 */
	private class ObserverTag extends ObserverAspect
	{		
		public ObserverTag(Subject structure)
		{
			super(structure);
		}
		
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect)
		{
			if(super.structure == structure && aspect instanceof Tag)
				notifications.add(new Notification("Tag changed", bugReport, super.structure));
		}
		
		@Override
		public String toString() {
			return "Registration for change of tag in \n" + super.structure;
		}
		
		
	}
	
	/**
	 * Inner class observer of a change of tag to a specific tag of a bug report
	 *
	 */
	private class ObserverSpecificTag extends ObserverAspect
	{
		private Tag tag;
		
		public ObserverSpecificTag(Subject structure, Tag tag)
		{
			super(structure);
			this.tag = tag;
		}
		
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect)
		{
			if(super.structure == structure && tag.getClass().isInstance(aspect))
				notifications.add(new Notification("Tag changed to " + tag, bugReport, super.structure));
			
		}
		
		@Override
		public String toString() {
			return "Registration for changed of tag to " + tag + " in \n" + super.structure;
		}
		
	}
	
	/**
	 * Inner class observer of a creation of a comment in a bug report
	 *
	 */
	private class ObserverComment extends ObserverAspect
	{
		
		public ObserverComment(Subject structure)
		{
			super(structure);
		}
		
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect)
		{
			if(super.structure == structure && aspect instanceof Comment)
				notifications.add(new Notification("New comment", bugReport, super.structure));
		}
		
		@Override
		public String toString() {
			return "Registration for creation of new comment in \n" + super.structure;
		}
	}
}
