package Model.Mail;

import Model.BugReport.BugReport;

/**
 * Abstract class ObserverAspect that has one observed subject structure and can unbind that subject.
 *
 */
public abstract class ObserverAspect implements Observer
{

	protected Subject structure;
	
	/**
	 * Constructor of this ObserverAspect
	 *
	 * @param structure Subject to observe
	 * 
	 * @throws IllegalArgumentException the structure is null
	 */
	public ObserverAspect(Subject structure)
	{
		if(structure == null) throw new IllegalArgumentException("Subject structure cannot be null");
		this.structure = structure;
		structure.addObserver(this);
	}
	
	 /**
     * Method called to notify this observer
     *
	 * @param structure The subject
	 * @param bugReport The bug report that has change within
	 * @param aspect The aspect that has changed
	 * 
	 * @throws IllegalArgumentException the structure, bug report or aspect is null
     */
	@Override
	public abstract void update(Subject structure, Subject subject, Object aspect);

	/**
	 * Method to unbind this object from his subject
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
