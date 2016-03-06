package Model.Project;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.Roles.Lead;
import Model.Roles.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Project
{

	private String name;
	private String description;
	private TheDate creationDate;
	private TheDate startingDate;
	private double budget;
	
	private double versionID = 1.0;
	
	private List<SubSystem> subSystems = new ArrayList<>();
	private Lead leadRole;
	private List<Role> devsRoles = new ArrayList<>();
	
	
	
	/**
	 * Constructoren
	*/
	
	/**
     * Default constructor for project.
     *
     * @throws ModelException One of the arguments given is not valid. (See setters of arguments for rules)
	 */
	public Project(String name, String description, TheDate startingDate, double budget, Lead leadRole) throws ModelException
	{
		this.setName(name);
		this.setDescription(description);
		this.creationDate = TheDate.TheDateNow();
		this.setStartingDate(startingDate);
		this.setBudget(budget);
		this.setLeadRole(leadRole);
		
	}
	
	/**
	 * Getters
	 */
	
	/**
	 * Getter to request the name of the project.
	 *
     * @return The name of the project
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Getter to request the description of the project.
     *
	 * @return The description of the project
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Getter to request the creation date of the project.
     *
	 * @return The creation date of the project
	 */
	public TheDate getCreationDate()
	{
		return creationDate;
	}

	/**
	 * Getter to request the starting date of the project.
     *
	 * @return The starting date of the project
	 */
	public TheDate getStartingDate()
	{
		return startingDate;
	}
	
	/**
	 * Getter to reqeust the budget of the project.
     *
	 * @return The budget of the project
	 */
	public double getBudget()
	{
		return budget;
	}
	
	/**
	 * Getter to request the versionId of the project.
     *
	 * @return The version ID of the project
	 */
	public double getVersionID()
	{
		return versionID;
	}
	
	/**
	 * Getter to request the subsystems of the project.
     *
	 * @return The subsystems of the project
	 */
	public List<SubSystem> getSubSystems()
	{
		return Collections.unmodifiableList(this.subSystems);
	}

    /**
     * Getter to request the lead of the project.
     *
     * @return The lead of the project.
     */
	public Lead getLeadRole()
	{
		return leadRole;
	}

    /**
     * Getter to request the roles with the developers of project.
     *
     * @return The roles with the developers of the project.
     */
	public List<Role> getDevsRoles()
	{
		return Collections.unmodifiableList(this.devsRoles);
	}
	
	/**
	 * Setters
	 */
	
	/**
	 * Setter to set the name of the project.
     *
	 * @param name The name of the project
     *
     * @throws ModelException The given name is empty.
	 */
	public void setName(String name) throws ModelException
	{
		if(!isValidName(name)) throw new ModelException("The given name is empty.");
		
		this.name = name;
	}
	
	/**
	 * Setter to set the description of the project
	 *
     * @param description The description of the project
     *
     * @throws ModelException The given description is empty.
	 */
	public void setDescription(String description) throws ModelException
	{
		if(!isValidDescription(description)) throw new ModelException("The given description is empty.");
		
		this.description = description;
	}
	
	/**
	 * Setter to set the starting date of the project.
     *
     * @param date The starting date of the project.
     *
     * @throws ModelException The given date is before the creation date.
     * @throws IllegalArgumentException The given date is null.
	 */
	public void setStartingDate(TheDate date) throws ModelException
	{
		if(date == null) throw new IllegalArgumentException("Date is null");
		if (!isValidStartingDate(date)) throw new ModelException("The date is before the creation date.");
        this.startingDate = date;
    }
	
	/**
	 * Setter to set the budget of the project.
     *
     * @param newBudget The budget of the project.
     *
     * @throws ModelException The budget is negative.
	 */
	public void setBudget(double newBudget) throws ModelException
	{
		if (!isValidBudget(budget)) throw new ModelException("The budget cannot be negative.");

        budget = newBudget;
	}

    /**
     * Setter to set the versionId of the project.
     *
     * @param versionID The versionId to set the project to.
     *
     * @throws ModelException The given versionId is lower than or equal to the current one.
     */
	public void setVersionID(double versionID) throws ModelException
	{
        if(!isValidVersionID(versionID)) throw new ModelException("The version cannot be lower than or equal to the previous one!");
		this.versionID = versionID;
	}

    /**
     * Setter to set the lead of the project.
     *
     * @param leadRole The lead to assign to the project.
     *
     * @throws IllegalArgumentException The given role is null.
     */
	public void setLeadRole(Lead leadRole)
	{
		if(leadRole == null) throw new IllegalArgumentException("Role is null");

		this.leadRole = leadRole;
	}

    /**
     * Checker to check if the given name is valid.
     *
     * @param name The name to check.
     *
     * @return True if the give name is not null or empty.
     */
    public boolean isValidName(String name){
        if (name == null) return false;
        if (name.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the given description is valid.
     *
     * @param description The description to check.
     *
     * @return True if the description is null or empty.
     */
    public boolean isValidDescription(String description){
        if (description == null) return false;
        else return true;
    }

    /**
     * Checker to check if the given starting date is valid.
     *
     * @param startingDate The starting date to check.
     *
     * @return True if the startingdate is later than the creation date.
     */
    public boolean isValidStartingDate(TheDate startingDate){
        if (this.getCreationDate().isAfter(startingDate)) return false;
        else return true;
    }

    /**
     * Checker to check if the budget is valid.
     *
     * @param budget The budget to check.
     *
     * @return True if the budget is bigger than or equal to 0.
     */
    public boolean isValidBudget(double budget){
        if (budget < 0) return false;
        else return true;
    }

    /**
     * Checker to check if  the version id is valid.
     *
     * @param versionID The version id to check.
     *
     * @return True if the versionId is higher than the current one.
     */
    public boolean isValidVersionID(double versionID){
        if (versionID <= this.getVersionID()) return false;
        else return true;
    }
	
	/**
	 * Operations
	 */
	
	/**
	 * Method to add a subsystem to the list of subsystems.
     *
	 * @param subSystem The subsystem to add.
     *
     * @throws IllegalArgumentException The given subsystem is null.
	 */
	void addSubSystem(SubSystem subSystem)
	{

		if(subSystem == null) throw new IllegalArgumentException("Subsystem is null");
		
		subSystems.add(subSystem);
	}

    /**
     * Method to add a role to the list of roles.
     *
     * @param role The role to add to the list of roles.
     *
     * @throws IllegalArgumentException The given role is null.
     */
	public void addRole(Role role)
	{
		if(role == null) throw new IllegalArgumentException("Role is null");
		devsRoles.add(role);
	}


    /**
     * Getter to get all the subsystems of the project.
     *
     * @return An unmodifiable list of all the subsystems of the project. (recursively)
     */
	public List<SubSystem> getAllSubSystems()
	{
		List<SubSystem> list = new ArrayList<>();
		for(SubSystem s : subSystems)
		{
			list.add(s);
			list.addAll(s.getAllSubSystems());
		}
		return Collections.unmodifiableList(list);
	}

    /**
     * Getter to request all the bugreports of the project.
     *
     * @return An unmodifiable list of all the bugreports of the project. (recursively)
     */
    public List<BugReport> getAllBugReports(){
        List<BugReport> bugReports = new ArrayList<>();
        for (SubSystem subsystem: this.getAllSubSystems()){
            bugReports.addAll(subsystem.getSubsystemBugReports());
        }
        return Collections.unmodifiableList(bugReports);
    }

	@Override
	public String toString(){
		return "Project name: " + getName() + "; Creation Date: " + getCreationDate() + "; Starting Date: " + getStartingDate() + "; Lead developer: " + getLeadRole().getDeveloper().toString();
	}

}
