package Model.Mail;

import Model.BugReport.BugReport;
import Model.Memento.Originator;

/**
 * Abstract class ObserverAspect that has one observed subject and can unbind to that subject.
 *
 */
public abstract class ObserverAspect implements Observer<BugReport>, Originator<ObserverAspectMemento,ObserverAspect>{

	protected Subject structure;
	
	/**
	 * Constructor of this ObserverAspect
	 * 
	 * @param s Subject to observe
	 */
	public ObserverAspect(Subject structure)
	{
		if(structure != null)
		{
			this.structure = structure;
			structure.addObserver(this);
		}
	}
	
	 /**
     * Method called to notify this observer
     * 
     * @param s The subject
     * @param aspect The aspect that has changed
     */
	@Override
	public abstract void update(Subject structure, BugReport bugreport, Object aspect);

	/**
	 * Method to destroy this object and at the same time unbind it from his subject
	 * 
	 */
	public void unbind()
	{
		if(structure != null)
			structure.removeObserver(this);
	}
	
	/**
	 * Method to represent this ObserverAspect as a string representation
	 * 
	 * @return The string representation of this object
	 */
	@Override
	public abstract String toString();
	
	@Override
	public ObserverAspectMemento createMemento()
	{
		return new ObserverAspectMemento(this);
	}
	
	@Override
	public void restoreMemento(ObserverAspectMemento memento)
	{
		this.structure = memento.getStructure();
		if(!structure.observers.contains(this))
			structure.addObserver(this);
	}

}
