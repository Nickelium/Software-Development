package Model.Mail;

/**
 * Abstract class ObserverAspect that has one observed subject and can unbind to that subject.
 *
 */
public abstract class ObserverAspect implements Observer{

	protected Subject s;
	
	/**
	 * Constructor of this ObserverAspect
	 * 
	 * @param s Subject to observe
	 */
	public ObserverAspect(Subject s)
	{
		if(s != null)
		{
			this.s = s;
			s.addObserver(this);
		}
	}
	
	 /**
     * Method called to notify this observer
     * 
     * @param s The subject
     * @param aspect The aspect that has changed
     */
	@Override
	public abstract void update(Subject s, Object aspect);

	/**
	 * Method to destroy this object and at the same time unbind it from his subject
	 * 
	 */
	public void destructor()
	{
		if(s != null)
		{
			s.removeObserver(this);
			s = null;
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
