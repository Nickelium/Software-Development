package Model.Mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.BugReport.*;
import Model.Memento.Memento;
import Model.Memento.Originator;

/**
 * Class that represents a mailbox of a user.
 * A mailbox contains all notifications of which a user is registred to.
 * //TODO Documenteren van Mail package !!!
 */
public class Mailbox implements Originator<MailboxMemento, Mailbox>
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
	 * @param s The subject to observe
	 */
	public void registerBugReport(Subject structure)
	{
		ObserverBugReport ObBug = new ObserverBugReport(structure);
		registrations.add(ObBug);
	}
	
	/**
	 * Method to register for a creation of a new comment in the given subject
	 * 
	 * @param s The subject to observe
	 */
	public void registerComment(Subject structure)
	{
		ObserverComment ObComm = new ObserverComment(structure);
		registrations.add(ObComm);
	}
	
	/**
	 * Method to register for a change of a tag of a bugreport in the given subject
	 * 
	 * @param s The subject to observe
	 */
	public void registerTag(Subject structure)
	{
		ObserverTag ObTag = new ObserverTag(structure);
		registrations.add(ObTag);
	}
	
	/**
	 * Method to register for a change of a tag to a specific tag of a bugreport in the given subject
	 * 
	 * @param s The subject to observe
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
		
		for(ObserverAspectMemento registrationMemento : memento.getRegistrationMementos())
			registrationMemento.getOriginator().restoreMemento(registrationMemento);
			
		
	}
	
	
	
	/**
	 * Inner class observer of a creation of a bugreport
	 *
	 */
	private class ObserverBugReport extends ObserverAspect
	{
		public ObserverBugReport(Subject structure)
		{
			super(structure);
		}
		
		@Override
		public void update(Subject structure, BugReport bugreport, Object aspect) {
			if(super.structure == structure && aspect instanceof BugReport)
				notifications.add(new Notification("New bugreport",bugreport, super.structure));
			
		}

		@Override
		public String toString() {
			return "Registration for creation of new bugreport in \n" + super.structure;
		}
		
	}
	
	/**
	 * Inner class observer of a change of tag of a bugreport
	 *
	 */
	private class ObserverTag extends ObserverAspect
	{		
		public ObserverTag(Subject structure)
		{
			super(structure);
		}
		
		@Override
		public void update(Subject structure, BugReport bugreport, Object aspect) 
		{
			if(super.structure == structure && aspect instanceof Tag)
				notifications.add(new Notification("Tag changed", bugreport, super.structure));
		}
		
		@Override
		public String toString() {
			return "Registration for change of tag in \n" + super.structure;
		}
		
		
	}
	
	/**
	 * Inner class observer of a change of tag to a specific tag of a bugreport
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
		public void update(Subject structure, BugReport bugreport, Object aspect) 
		{
			if(super.structure == structure && tag.getClass().isInstance(aspect))
				notifications.add(new Notification("Tag changed to " + tag, bugreport, super.structure));
			
		}
		
		@Override
		public String toString() {
			return "Registration for changed of tag to " + tag + " in \n" + super.structure;
		}
		
	}
	
	/**
	 * Inner class observer of a creation of a comment in a bugreport
	 *
	 */
	private class ObserverComment extends ObserverAspect
	{
		
		public ObserverComment(Subject structure)
		{
			super(structure);
		}
		
		@Override
		public void update(Subject structure, BugReport bugreport, Object aspect)
		{
			if(super.structure == structure && aspect instanceof Comment)
				notifications.add(new Notification("New comment", bugreport, super.structure));	
		}
		
		@Override
		public String toString() {
			return "Registration for creation of new comment in \n" + super.structure;
		}
	}
}
