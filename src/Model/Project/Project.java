package Model.Project;
import java.util.ArrayList;
import java.util.List;

import Model.Roles.Lead;
import Model.Roles.Role;

public class Project
{

	private String name;
	private String description;
	private TheDate creationDate;
	private TheDate startingDate;
	private double budget;
	
	private double versionID; 
	
	private List<SubSystem> subSystems = new ArrayList<>();
	private Lead leadRole;
	private List<Role> devsRoles = new ArrayList<>();
	
	
	
	/**
	 * Constructoren
	*/
	
	/**
	 * Construct a new instance of Project with the given name and description
	 * @param newName The name of the project
	 * @param newDescription The description of the project
	 * @param newBudget The budget of the project
	 */
	public Project(String newName, String newDescription, TheDate newStartingDate, double newBudget, Lead newLeadRole)
	{
		setName(newName);
		setDescription(newDescription);
		this.startingDate = newStartingDate;
		this.budget = newBudget;
		setLeadRole(newLeadRole);
		
		this.creationDate = TheDate.TheDateNow();
	}
	
	/**
	 * Getters
	 */
	
	/**
	 * Returns the name of the project
	 * @return The name of the project
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the description of the project
	 * @return The description of the project
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Returns the creation date of the project
	 * @return The creation date of the project
	 */
	public TheDate getCreationDate()
	{
		return creationDate;
	}
	
	//Intern gebruik maken van TheDate type, afschermen op hoger niveau door strings te gebruiken
	/**
	 * Returns the starting date of the project
	 * @return The starting date of the project
	 */
	public TheDate getStartingDate()
	{
		return startingDate;
	}
	
	/**
	 * Returns the budget of the project
	 * @return The budget of the project
	 */
	public double getBudget()
	{
		return budget;
	}
	
	/**
	 * Returns the version ID of the project
	 * @return The version ID of the project
	 */
	public double getVersionID()
	{
		return versionID;
	}
	
	/**
	 * Returns the subsystems of the project
	 * @return The subsystems of the project
	 */
	public List<SubSystem> getSubSystems()
	{
		return new ArrayList<SubSystem>(subSystems);
	}
	
	public Lead getLeadRole()
	{
		return leadRole;
	}
	
	public List<Role> getDevRole()
	{
		return devsRoles;
	}
	
	/**
	 * Setters
	 */
	
	/**
	 * Set the name of the project
	 * @param newName The name of the project
	 */
	public void setName(String newName)
	{
		if(newName == null) throw new NullPointerException("The given name cannot be null.");
		
		name = newName;
	}
	
	/**
	 * Set the description of the project
	 * @param newDescription The description of the project
	 */
	public void setDescription(String newDescription)
	{
		if(newDescription == null)  throw new NullPointerException("The given description cannot be null.");
		
		description = newDescription;
	}
	
	/**
	 * Set the starting date of the project
	 * @param day The day of start
	 * @param month The month of start
	 * @param year The year of start
	 * @throws Exception 
	 */
	public void setStartingDate(int day, int month, int year) throws Exception
	{
		TheDate tmp =  new TheDate(day,month,year);
		if(creationDate.isAfter(tmp)) throw new Exception("The starting date must be after or on the same date as the creation date");
		startingDate = new TheDate(day,month,year);
	}
	
	/**
	 * Set the budget of the project
	 * @param newBudget The budget of the project
	 */
	public void setBudget(double newBudget) throws Exception
	{
		if(newBudget < 0) throw new Exception("A budget cannot be negative");
		budget = newBudget;
	}
	
	/**
	 * Set the version ID of the project
	 * @param newVersionID The version ID of the project
	 * @throws Exception 
	 */
	public void setVersionID(double newVersionID) throws Exception
	{
		if(newVersionID < 0.0) throw new Exception("A version ID cannot be negative");
		if(versionID >= newVersionID) throw new Exception("Setting to a previous/smaller versionID cannot be done"); 
		versionID = newVersionID;
	}
	
	public void setLeadRole(Lead newLeadRole)
	{
		if(newLeadRole == null) throw new NullPointerException("The given role cannot be null");
		leadRole = newLeadRole;
	}
	
	/**
	 * Operations
	 */
	
	/**
	 * Add a subsystem to this project
	 * @param s The subsystem to add
	 */
	public void addSubSystem(SubSystem s)
	{
		
		if(s == null) throw new NullPointerException("The given subsystem cannot be null.");
		
		subSystems.add(s);
//		if(subSystems == null )
//			subSystems = new ArrayList<SubSystem>();
//		subSystems.add(s);
	}
	
	/**
	 * Get recursively all the subsystems of the project
	 * @return A list of subsystems
	 */
	
	public List<SubSystem> getAllSubSystem()
	{
		List<SubSystem> list = new ArrayList<SubSystem>();
		for(SubSystem s : subSystems)
		{
			list.add(s);
			list.addAll(s.getAllSubSystems());
		}
		
		return list;
	}
	
	/**
	 * Clone a project
	 * @return A cloned project
	 */
	public Project clone()
	{
		Project p = new Project(name,description, startingDate, budget, leadRole);
		
		p.creationDate = creationDate;

		if(subSystems != null) p.subSystems =  new ArrayList<SubSystem>(subSystems);
		if(devsRoles != null)  p.devsRoles = new ArrayList<Role>(devsRoles);
		
		return p;
	}

	
	public void destructor()
	{
		name = null;
		description = null;
		creationDate = null;
		startingDate = null;
		budget = 0.0;
		versionID = 0.0;
		
		for(SubSystem s : subSystems)
			s.destructor();
		subSystems = null;
		
		leadRole.destructor();
		
		for(Role devRole : devsRoles)
			devRole.destructor();
		
		
	}

}
