package Model.Mail;

/**
 * Class that represents a notification
 *
 */
public class Notification 
{
	String content;
	
	/**
	 * Constructor of this notification
	 * 
	 * @param str The content of this notification
	 */
	public Notification(String str)
	{
		content = str;
	}
	
	/**
	 * Getter to get the content of this notification
	 * 
	 * @return The content
	 */
	public String getContent()
	{
		return content;
	}
	
	/**
	 * Method to represent this object as a string representation
	 * 
	 * @return The string representation of this object
	 */
	@Override
	public String toString()
	{
		return content;
	}
}
