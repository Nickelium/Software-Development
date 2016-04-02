package Model.Mail;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject 
{
	List<Observer> observers = new ArrayList<>();
	
	public void addObserver(Observer obs)
	{
		observers.add(obs);
	}
	
	public void removeObserver(Observer obs)
	{
		observers.remove(obs);
	}
	
	public void notifyObservers(Object aspect)
	{
		for(Observer obs : observers)
		{
			obs.update(this, aspect);
		}
	}
}
