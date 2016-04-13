package Model.BugReport;

import Model.Memento.Memento;
import Model.Milestone.TargetMilestone;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.List;

public class BugReportMemento extends Memento<BugReport>
{

	//TODO documentatie volledige klasse

	private Tag tag;
	private List<Developer> assignees;
	private List<Comment> comments;
	
	private TargetMilestone targetMilestone;
	
	private List<Test> tests;
	private List<Patch> patches;
	
	public BugReportMemento(BugReport originator)
	{
		super(originator);
		this.tag = originator.getTag();
		this.assignees = new ArrayList<>(originator.getAssignees());
		this.comments = new ArrayList<>(originator.getComments());
		
		this.targetMilestone = originator.getTargetMilestone();
		
		this.tests = new ArrayList<>(originator.getTests());
		this.patches = new ArrayList<>(originator.getPatches());
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
	
	TargetMilestone getTargetMilestone()
	{
		return targetMilestone;
	}
	
	List<Test> getTests()
	{
		return tests;
	}
	
	List<Patch> getPatches()
	{
		return patches;
	}
}
