package Model.Mail;

import java.util.ArrayList;
import java.util.List;

import Model.BugReport.Comment;
import Model.Tags.Tag;

public class Mailbox 
{
	List<Notification> notifications = new ArrayList<>();
	Mailbox() {}
	
	public void registerComment(Subject s)
	{
		s.addObserver(new ObserverCommment());
	}
	
	public void registerTag(Subject s)
	{
		s.addObserver(new ObserverTag());
	}
	
	public void registerSpecificTag(Subject s, Tag tag)
	{
		s.addObserver(new ObserverSpecificTag(tag));
	}

	public void unregister()
	{
		
	}
	
	private class ObserverTag implements Observer<Tag>
	{

		@Override
		public void update(Subject s, Object aspect) 
		{
			if(aspect instanceof Tag)
				notifications.add(new Notification("TAG MAIL"));
			
		}
		
	}
	
	private class ObserverSpecificTag implements Observer<Tag>
	{
		private Tag tag;
		
		ObserverSpecificTag(Tag tag)
		{
			this.tag = tag;
		}
		
		@Override
		public void update(Subject s, Object aspect) 
		{
			if(tag.getClass().isInstance(aspect))
				notifications.add(new Notification("TAG SPECIFIC MAIL"));
			
		}
		
	}
	
	private class ObserverCommment implements Observer<Comment>
	{

		@Override
		public void update(Subject s, Object aspect)
		{
			if(aspect instanceof Comment)
				notifications.add(new Notification("COMMENT MAIL"));
			
		}
		
	}
}
