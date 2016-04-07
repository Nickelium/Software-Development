package Model.Mail;

import Model.BugReport.BugReport;

/**
 * Abstract class ObserverAspect that has one observed subject and can unbind to that subject.
 *
 */
public abstract class ObserverAspect implements Observer<BugReport>{

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
	public void destructor()
	{
		if(structure != null)
		{
			structure.removeObserver(this);
			structure = null;
		}
	}
	
	/**
	 * Method to represent this ObserverAspect as a string representation
	 * 
	 * @return The string representation of this object
	 */
	@Override
	public abstract String toString();

}
