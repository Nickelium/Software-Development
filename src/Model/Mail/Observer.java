package Model.Mail;

/**
 * Interface of an observer
 *
 */
public interface Observer<T> {
	
	 /**
     * Method called to notify this observer
	  *
	  * @param s The subject
     * @param aspect The aspect that has changed
     */
	public void update(Subject structure, T s, Object aspect);

}
