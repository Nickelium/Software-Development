package Model.Mail;


public class Notification 
{
	String content;
	
	public Notification(String str)
	{
		content = str;
	}
	
	public String toString()
	{
		return content;
	}
}
