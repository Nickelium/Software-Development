package Model.BugReport;

import java.util.ArrayList;
import java.util.List;

import Model.Memento.Memento;
import Model.User.Developer;

public class BugReportMemento extends Memento<BugReport>
{

	private Tag tag;
	private List<Developer> assignees;
	private List<Comment> comments;
	
	public BugReportMemento(BugReport originator)
	{
		super(originator);
		this.tag = originator.getTag();
		this.assignees = new ArrayList<>(originator.getAssignees());
		this.comments = new ArrayList<>(originator.getComments());
	}
	
	Tag getTag()
	{
		return tag;
	}

	List<Developer> getAssignees()
	{
		return assignees;
	}
	
	List<Comment> getComments()
	{
		return comments;
	}
}
