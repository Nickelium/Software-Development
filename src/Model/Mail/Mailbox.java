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
 * A mailbox contains all notifications of which a user is registered to.
 * A mailbox also contains all his registrations.
 * 
 */
public class Mailbox implements Originator<Mailbox.MailboxMemento, Mailbox>
{
	private List<Notification> notifications;
	private List<ObserverAspect> registrations;
	
	/**
	 * Constructor of this mailbox object
	 * 
	 */
	public Mailbox() 
	{
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
	 * 
	 * @throws IllegalArgumentException the structure is null
	 */
	public void registerBugReport(Subject structure)
	{
		if(structure == null) throw new IllegalArgumentException("Subject structure cannot be null");
		ObserverBugReport ObBug = new ObserverBugReport(structure);
		registrations.add(ObBug);
	}
	
	/**
	 * Method to register for a creation of a new comment in the given subject
	 *
	 * @param structure The subject to observe
	 * 
	 * @throws IllegalArgumentException the structure is null
	 */
	public void registerComment(Subject structure)
	{
		if(structure == null) throw new IllegalArgumentException("Subject structure cannot be null");
		ObserverComment ObComm = new ObserverComment(structure);
		registrations.add(ObComm);
	}
	
	/**
	 * Method to register for a change of a tag of a bug report in the given subject
	 *
	 * @param structure The subject to observe
	 * @throws IllegalArgumentException the structure is null
	 */
	public void registerTag(Subject structure)
	{
		if(structure == null) throw new IllegalArgumentException("Subject structure cannot be null");
		ObserverTag ObTag = new ObserverTag(structure);
		registrations.add(ObTag);
	}
	
	/**
	 * Method to register for a change of a tag to a specific tag of a bug report in the given subject
	 *
	 * @param structure The subject to observe
	 * @param tag The specific tag 
	 * 
	 * @throws IllegalArgumentException the structure or tag is null
	 */
	public void registerSpecificTag(Subject structure, Class tag)
	{
		if(structure == null) throw new IllegalArgumentException("Subject structure cannot be null");
		if(tag == null) throw new IllegalArgumentException("Tag cannot be null");
		ObserverSpecificTag ObSTag = new ObserverSpecificTag(structure, tag);
		registrations.add(ObSTag);
	}

	/**
	 * Method to unregister from a registration
	 * 
	 * @param registration The observeraspect to unregister
	 * 
	 * @throws IllegalArgumentException the registration is null
	 */
	public void unregister(ObserverAspect registration)
	{
		if(registration == null) throw new IllegalArgumentException("The registration cannot be null");
		if(registrations.contains(registration))
			registration.unbind();
		
		registrations.remove(registration);
	}
	
	/**
	 * Method to create and return a new memento object
	 *
	 * @return the new memento object for this Mailbox
     */
	@Override
	public MailboxMemento createMemento() 
	{
		return new MailboxMemento(this);
	}

	/**
	 * Method to restore this object given the memento
	 * 
	 * @param memento The memento to restore to
	 * 
	 * @throws IllegalArgumentException the memento is null
	 */
	@Override
	public void restoreMemento(MailboxMemento memento)
	{
		if(memento == null) throw new IllegalArgumentException("The memento cannot be null");
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
    	 * 
    	 * @throws IllegalArgumentException the originator is null
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

	
	//Innerclass observer bug report
	/**
	 * Inner class observer of a creation of a new bug report
	 *
	 */
	private class ObserverBugReport extends ObserverAspect
	{
		/**
		 * Constructor 
		 * 
		 * @param structure The subject to observe
		 */
		public ObserverBugReport(Subject structure)
		{
			super(structure);
		}
		
		/**
		 * Method to use when a change occurred in the subject structure.
		 * 
		 * @param structure The subject
		 * @param bugReport The bug report that has change within
		 * @param aspect The aspect that has changed
		 * 
		 * @throws IllegalArgumentException the structure, bug report or aspect is null
		 */
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect) {
			if(structure == null) throw new IllegalArgumentException("The structure cannot be null");
			if(bugReport == null) throw new IllegalArgumentException("The bugreport cannot be null");
			if(aspect == null) throw new IllegalArgumentException("The aspect cannot be null");
			
			if(super.structure == structure && aspect instanceof BugReport)
				notifications.add(new Notification("New bug report", bugReport, super.structure));
			
		}

		/**
		 * Method to get textual representation of this object
		 * 
		 * @return The String representation of this object
		 */
		@Override
		public String toString() {
			return "Registration for creation of new bug report in \n" + super.structure;
		}
		
	}
	
	//Innerclass observer bug report
	/**
	 * Inner class observer of a tag change of a bug report
	 *
	 */
	private class ObserverTag extends ObserverAspect
	{		
		
		/**
		 * Constructor 
		 * 
		 * @param structure The subject to observe
		 */
		public ObserverTag(Subject structure)
		{
			super(structure);
		}
		
		/**
		 * Method to use when a change occurred in the subject structure.
		 * @param structure The subject
		 * @param bugReport The bug report that has change within
		 * @param aspect The aspect that has changed
		 * 
		 * @throws IllegalArgumentException the structure, bug report or aspect is null
		 */
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect)
		{
			if(structure == null) throw new IllegalArgumentException("The structure cannot be null");
			if(bugReport == null) throw new IllegalArgumentException("The bugreport cannot be null");
			if(aspect == null) throw new IllegalArgumentException("The aspect cannot be null");
			
			if(super.structure == structure && aspect instanceof Tag)
				notifications.add(new Notification("Tag changed", bugReport, super.structure));
		}
		
		/**
		 * Method to get textual representation of this object
		 * 
		 * @return The String representation of this object
		 */
		@Override
		public String toString() {
			return "Registration for change of tag in \n" + super.structure;
		}
		
		
	}
	
	//Innerclass observer bug report
	/**
	 * Inner class observer of a specific tag change of a bug report
	 *
	 */
	private class ObserverSpecificTag extends ObserverAspect
	{
		private Class tag;
		
		/**
		 * Constructor 
		 * 
		 * @param structure The subject to observe
		 * @param tag The specific tag
		 * 
		 * @throws IllegalArgumentException the tag class is null
		 */
		public ObserverSpecificTag(Subject structure, Class tag)
		{
			super(structure);
			
			if(tag == null) throw new IllegalArgumentException("The tag class cannot be null");
			this.tag = tag;
		}
		
		/**
		 * Method to use when a change occurred in the subject structure.
		 * @param structure The subject
		 * @param bugReport The bug report that has change within
		 * @param aspect The aspect that has changed
		 * 
		 * @throws IllegalArgumentException the structure, bug report or aspect is null
		 */
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect)
		{
			if(structure == null) throw new IllegalArgumentException("The structure cannot be null");
			if(bugReport == null) throw new IllegalArgumentException("The bugreport cannot be null");
			if(aspect == null) throw new IllegalArgumentException("The aspect cannot be null");
			
			if(super.structure == structure && tag.isInstance(aspect))
				notifications.add(new Notification("Tag changed to " + tag, bugReport, super.structure));
			
		}
		
		/**
		 * Method to get textual representation of this object
		 * 
		 * @return The String representation of this object
		 */
		@Override
		public String toString() {
			return "Registration for changed of tag to " + tag + " in \n" + super.structure;
		}
		
	}
	
	//Innerclass observer bug report
	/**
	 * Inner class observer of a new comment in a bug report
	 *
	 */
	private class ObserverComment extends ObserverAspect
	{
		
		/**
		 * Constructor 
		 * 
		 * @param structure The subject to observe
		 */
		public ObserverComment(Subject structure)
		{
			super(structure);
		}
		
		/**
		 * Method to use when a change occurred in the subject structure.
		 * @param structure The subject
		 * @param bugReport The bug report that has change within
		 * @param aspect The aspect that has changed
		 * 
		 * @throws IllegalArgumentException the structure, bug report or aspect is null
		 */
		@Override
		public void update(Subject structure, BugReport bugReport, Object aspect)
		{
			if(structure == null) throw new IllegalArgumentException("The structure cannot be null");
			if(bugReport == null) throw new IllegalArgumentException("The bugreport cannot be null");
			if(aspect == null) throw new IllegalArgumentException("The aspect cannot be null");
			
			if(super.structure == structure && aspect instanceof Comment)
				notifications.add(new Notification("New comment", bugReport, super.structure));
		}
		
		/**
		 * Method to get textual representation of this object
		 * 
		 * @return The String representation of this object
		 */
		@Override
		public String toString() {
			return "Registration for creation of new comment in \n" + super.structure;
		}
	}
}
