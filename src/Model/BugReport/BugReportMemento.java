package Model.BugReport;

import Model.Memento.Memento;
import Model.Milestone.TargetMilestone;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility for saving the state of the system at a certain point in time
 * during execution of the Bug Trap software.
 *
 * The bug report memento saves the state of the following attributes:
 * tag, assignees, comments, targetMilestone, tests & patches.
 *
 * This class provides package-visible methods to request the values of the saved fields.
 */
public class BugReportMemento extends Memento<BugReport>
{

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

	/**
	 * Returns the tag object of the bug report memento
	 * @return the tag object of the bug report memento
     */
	Tag getTag()
	{
		return tag;
	}

	/**
	 * Returns the list with assignees of the bug report memento
	 * @return the list with assignees of the bug report memento
	 */
	List<Developer> getAssignees()
	{
		return assignees;
	}

	/**
	 * Returns the list with comments of the bug report memento
	 * @return the list with comments of the bug report memento
     */
	List<Comment> getComments()
	{
		return comments;
	}

	/**
	 * Returns the target milestone object of the bug report memento
	 * @return the target milestone object of the bug report memento
     */
	TargetMilestone getTargetMilestone()
	{
		return targetMilestone;
	}

	/**
	 * Returns the list with tests of the bug report memento
	 * @return the list with tests of the bug report memento
     */
	List<Test> getTests()
	{
		return tests;
	}

	/**
	 * Returns the list with patches of the bug report memento
	 * @return the list with patches of the bug report memento
     */
	List<Patch> getPatches()
	{
		return patches;
	}
}
