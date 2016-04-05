package Model.Mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class subject that will be observed by the class observer
 *
 */
public abstract class Subject 
{
	List<Observer> observers = new ArrayList<>();
	
	/**
	 * Method to add an observer that will observe this subject
	 * 
	 * @param obs The observer to add
	 */
	public void addObserver(Observer obs)
	{
		observers.add(obs);
	}
	
	/**
	 * Method to remove an observer, the given observer will no longer observe this subject
	 * 
	 * @param obs The observer to be removed
	 */
	public void removeObserver(Observer obs)
	{
		observers.remove(obs);
	}
	
	/**
	 * Method to notify all observer of this subject
	 * 
	 * @param aspect The aspect that has changed
	 */
	public void notifyObservers(Object aspect)
	{
		for(Observer obs : observers)
		{
			obs.update(this, aspect);
		}
	}
}
