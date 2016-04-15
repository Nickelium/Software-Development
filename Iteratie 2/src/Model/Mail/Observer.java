package Model.Mail;

/**
 * Interface of an observer
 *
 */
public interface Observer<T> {
	
	 /**
     * Method called to notify this observer
	 *
	 * @param structure The subject structure where the changed occured
	 * @param s The object where the change occurred
     * @param aspect The aspect that has changed
     */
	public void update(Subject structure, T s, Object aspect);

}
