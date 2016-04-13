package Model.Mail;

import Model.BugReport.BugReport;

/**
 * Abstract class ObserverAspect that has one observed subject and can unbind to that subject.
 *
 */
public abstract class ObserverAspect implements Observer<BugReport>
{

	protected Subject structure;
	
	/**
	 * Constructor of this ObserverAspect
	 *
	 * @param structure Subject to observe
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
	  * @param structure The subject
	  * @param aspect The aspect that has changed
     */
	@Override
	public abstract void update(Subject structure, BugReport bugReport, Object aspect);

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
		
}
