package Model.Mail;

import java.util.ArrayList;
import java.util.List;

import Model.Memento.Memento;

public class MailboxMemento extends Memento<Mailbox>
{
	private List<ObserverAspect> registrations;
	private List<ObserverAspectMemento> registrationMementos = new ArrayList<>();
	
	public MailboxMemento(Mailbox originator) 
	{
		super(originator);
		registrations = new ArrayList<>(originator.getRegistrations());
		
		for(ObserverAspect registration : registrations)
			registrationMementos.add(registration.createMemento());
	}
	
	List<ObserverAspect> getRegistrations()
	{
		return registrations;
	}
	
	List<ObserverAspectMemento> getRegistrationMementos()
	{
		return registrationMementos;
	}
	
	

}
