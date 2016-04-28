package Model.Mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class subject that will be observed by the class observer
 *
 */
public abstract class Subject 
{
	protected List<Observer> observers = new ArrayList<>();
	
	/**
	 * Method to add an observer that will observe this subject
	 * 
	 * @param obs The observer to add
	 * 
	 * @throws IllegalArgumentException the observer is null
	 */
	public void addObserver(Observer obs)
	{
		if(obs == null) throw new IllegalArgumentException("The observer cannot be null");
		observers.add(obs);
	}
	
	/**
	 * Method to remove an observer, the given observer will no longer observe this subject
	 * 
	 * @param obs The observer to be removed
	 * 
	 * @throws IllegalArgumentException the observer is null
	 */
	public void removeObserver(Observer obs)
	{
		if(obs == null) throw new IllegalArgumentException("The observer cannot be null");
		observers.remove(obs);
	}
	
	/**
	 * Method to notify all observer of this subject
	 *
	 * @param s The subject that sends the notification.
	 * @param aspect The aspect that has changed
	 * 
	 * @throws IllegalArgumentException the subject or aspect is null
	 * 
	 */
	public void notifyObservers(Subject s, Object aspect)
	{
		if(s == null) throw new IllegalArgumentException("The subject cannot be null");
		if(aspect == null) throw new IllegalArgumentException("The aspect cannot be null");
		
		for(Observer obs : observers)
			obs.update(this, s, aspect);
		
	}
	
	/**
	 * Getter to get all observer of this object
	 * 
	 * @return The list of observer
	 */
	public List<Observer> getObservers()
	{
		return observers;
	}
}
