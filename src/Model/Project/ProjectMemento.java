package Model.Project;

import java.util.ArrayList;
import java.util.List;

import Model.Memento.Memento;
import Model.Milestone.Milestone;
import Model.Roles.Role;
import Model.Mail.Observer;

public class ProjectMemento extends Memento<Project>
{
	private String name;
	private String description;
	private TheDate startingDate;
	private double budget;
	private double versionID;
	
	private List<SubSystem> subsystems;
	
	private List<SubSystemMemento> subsystemMementos = new ArrayList<>();
	
	private List<Observer> observers;
	
	private List<Role> devsRoles;
	
	private Milestone latestAchievedMilestone;
	private List<Milestone> milestones;
	
	public ProjectMemento(Project originator)
	{
		super(originator);
		this.name = originator.getName();
		this.description = originator.getDescription();
		this.startingDate = originator.getStartingDate();
		this.budget = originator.getBudget();
		this.versionID = originator.getVersionID();
		
		this.subsystems = new ArrayList<>(originator.getSubSystems());
		
		for(SubSystem subsystem : subsystems)
			subsystemMementos.add(subsystem.createMemento());
		
		this.devsRoles = new ArrayList<>(originator.getDevsRoles());
		
		this.observers = new ArrayList<>(originator.getObservers());
		
		this.latestAchievedMilestone = originator.getLatestAchievedMilestone();
		this.milestones = new ArrayList<>(originator.getAllMilestones());
	}
	
	
	String getName()
	{
		return name;
	}
	
	String getDescription()
	{
		return description;
	}
	
	TheDate getStartingDate()
	{
		return startingDate;
	}
	
	double getBudget()
	{
		return budget;
	}
	
	double getVersionID()
	{
		return versionID;
	}
	
	List<SubSystem> getSubsystems()
	{
		return subsystems;
	}
	
	List<SubSystemMemento> getSubsystemMementos()
	{
		return subsystemMementos;
	}
	
	List<Role> getDevsRoles()
	{
		return devsRoles;
	}
	
	List<Observer> getObservers()
	{
		return observers;
	}
	
	Milestone getLatestAchievedMilestone()
	{
		return latestAchievedMilestone;
	}
	
	List<Milestone> getMilestones()
	{
		return milestones;
	}
}
