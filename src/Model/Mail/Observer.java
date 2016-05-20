package Model.Mail;

/**
 * Interface of an observer
 *
 */
public interface Observer 
{
	
	 /**
     * Method called to notify this observer
	 *
	 * @param structure The subject structure where the changed occured
	 * @param subject The object where the change occurred
     * @param aspect The aspect that has changed
     * 
     * @throws IllegalArgumentException the structure, s or aspect is null
     */
	public void update(Subject structure, Subject subject, Object aspect);

}
