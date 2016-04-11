package Model.Project;

import java.util.ArrayList;
import java.util.List;

import Model.Memento.Memento;

public class ProjectMemento extends Memento<Project>
{
	private String name;
	private String description;
	private TheDate startingDate;
	private double budget;
	private double versionID;
	
	private List<SubSystem> subsystems;
	
	public ProjectMemento(Project project)
	{
		super(project);
		this.name = project.getName();
		this.description = project.getDescription();
		this.startingDate = project.getStartingDate();
		this.budget = project.getBudget();
		this.versionID = project.getVersionID();
		
		this.subsystems = new ArrayList<>(project.getSubSystems());
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
}
