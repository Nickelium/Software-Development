package Model.Project;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Mail.Observer;
import Model.Mail.Subject;
import Model.Memento.Memento;
import Model.Memento.Originator;
import Model.Milestone.Milestone;
import Model.Milestone.MilestoneContainer;
import Model.Milestone.SetMilestoneHelper;
import Model.Roles.Lead;
import Model.Roles.Role;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *	This class represents a project object with all its related attributes.
 *
 *  This class provides getters and setters for most attributes.
 *	Consistency is provided thanks to the checker methods.
 */
public class Project extends Subject implements Observer<BugReport>, Originator<Project.ProjectMemento, Project>, MilestoneContainer
{

	private String name;
	private String description;
	private TheDate creationDate;
	private TheDate startingDate;
	private double budget;
	
	private double versionID = 1.0;
	private Milestone latestAchievedMilestone = null;
	private List<Milestone> milestones = new ArrayList<>();
	
	private List<SubSystem> subSystems = new ArrayList<>();
	private Lead leadRole;
	private List<Role> devsRoles = new ArrayList<>();


	//region Constructors

	/**
     * Default constructor for project.
     * 
     * @param name Name of this project.
     * @param description Description of this project.
     * @param startingDate starting date of this project.
     * @param budget Budget of this project.
     * @param leadRole The lead developer for this project.
     *
     * @throws ReportErrorToUserException One of the arguments given is not valid. (See setters of arguments for rules)
	 */
	Project(String name, String description, TheDate startingDate, double budget, Lead leadRole) throws ReportErrorToUserException
	{
		this.setName(name);
		this.setDescription(description);
		this.creationDate = TheDate.TheDateNow();
		this.setStartingDate(startingDate);
		this.setBudget(budget);
		this.setLeadRole(leadRole);
		this.latestAchievedMilestone = new Milestone();
	}

	//endregion

	//region Getters

	/**
	 * Getter to request the latest achieved milestone of the subsystem.
	 *
	 * @return The latest achieved milestone of the subsystem
	 */
	public Milestone getLatestAchievedMilestone(){
		return this.latestAchievedMilestone;
	}

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
	 * Getter to get all the subsystems of the project.
	 *
	 * @return An unmodifiable list of all the subsystems of the project. (recursively)
	 */
	public List<SubSystem> getAllSubSystems() {
		List<SubSystem> list = new ArrayList<>();
		for (SubSystem s : subSystems) {
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
	public List<BugReport> getAllBugReports() {
		List<BugReport> bugReports = new ArrayList<>();
		for (SubSystem subsystem : this.getAllSubSystems()) {
			bugReports.addAll(subsystem.getBugReports());
		}
		return Collections.unmodifiableList(bugReports);
	}

	/**
	 * Method that returns a list of all milestones added to the project and all
	 * the subsystems that it (recursively) contains.
	 *
	 * @return an unmodifiable list of all the milestones
	 */
	public List<Milestone> getAllMilestones() {

		List<Milestone> milestones = new ArrayList<>();
		milestones.add(this.getLatestAchievedMilestone());
		for (SubSystem subsystem : this.getAllSubSystems()) {
			milestones.addAll(subsystem.getCurrentSubsystemMilestones());
		}
		return Collections.unmodifiableList(milestones);
	}

	//TODO Documentation
	public List<Developer> getAllInvolvedDevelopers() {
		List<Developer> developers = new ArrayList<>();
		developers.add(this.getLeadRole().getDeveloper());
		for (Role role : getDevsRoles()) {
			if (!developers.contains(role.getDeveloper())) {
				developers.add(role.getDeveloper());
			}
		}

		return developers;
	}

	//endregion

	//region Setters
	
	/**
	 * Setter to set the name of the project.
     *
	 * @param name The name of the project
     *
     * @throws ReportErrorToUserException The given name is empty.
	 */
	void setName(String name) throws ReportErrorToUserException
	{
		if(!isValidName(name)) throw new ReportErrorToUserException("The given name is empty.");
		
		this.name = name;
	}
	
	/**
	 * Setter to set the description of the project
	 *
     * @param description The description of the project
     *
     * @throws ReportErrorToUserException The given description is empty.
	 */
	void setDescription(String description) throws ReportErrorToUserException
	{
		if(!isValidDescription(description)) throw new ReportErrorToUserException("The given description is empty.");
		
		this.description = description;
	}
	
	/**
	 * Setter to set the starting date of the project.
     *
     * @param date The starting date of the project.
     *
     * @throws ReportErrorToUserException The given date is before the creation date.
     * @throws IllegalArgumentException The given date is null.
	 */
	void setStartingDate(TheDate date) throws ReportErrorToUserException
	{
		if(date == null) throw new IllegalArgumentException("Date is null");
		if (!isValidStartingDate(date)) throw new ReportErrorToUserException("The date is before the creation date.");
        this.startingDate = date;
    }
	
	/**
	 * Setter to set the budget of the project.
     *
     * @param newBudget The budget of the project.
     *
     * @throws ReportErrorToUserException The budget is negative.
	 */
	void setBudget(double newBudget) throws ReportErrorToUserException
	{
		if (!isValidBudget(newBudget)) throw new ReportErrorToUserException("The budget cannot be negative.");

        budget = newBudget;
	}

    /**
     * Setter to set the versionId of the project.
     *
     * @param versionID The versionId to set the project to.
     *
     * @throws ReportErrorToUserException The given versionId is lower than or equal to the current one.
     */
	void setVersionID(double versionID) throws ReportErrorToUserException
	{
        if(!isValidVersionID(versionID)) throw new ReportErrorToUserException("The version cannot be lower than or equal to the previous one!");
		this.versionID = versionID;
	}

    /**
     * Setter to set the lead of the project.
     *
     * @param leadRole The lead to assign to the project.
     *
     * @throws IllegalArgumentException The given role is null.
     */
	void setLeadRole(Lead leadRole)
	{
		if(leadRole == null) throw new IllegalArgumentException("Role is null");

		this.leadRole = leadRole;
	}

	/**
	 * Method to set a new project milestone.
	 * <p>
	 * There occurs consistency checking:
	 * first pass: project milestone should not exceed any subsystem milestone
	 * second pass: project milestone should not exceed the target milestone of
	 * any related bug report with a non-final tag.
	 *
	 * @param newProjectMilestone the new project milestone that has to be set
	 * @throws ReportErrorToUserException is thrown in case that a constraint is broken.
	 */
	void setNewProjectMilestone(Milestone newProjectMilestone) throws ReportErrorToUserException {
		if (!SetMilestoneHelper.milestoneDoesNotExceedSubsystemMilestone(this, newProjectMilestone))
			throw new ReportErrorToUserException("The new milestone exceeds milestone of subsystem!");
		if (!SetMilestoneHelper.milestoneDoesNotExceedBugReportMilestone(this, newProjectMilestone))
			throw new ReportErrorToUserException("The new milestone exceeds the milestone of the projects target bug report!");

		setLatestAchievedMilestone(newProjectMilestone);
		addMilestoneToList(newProjectMilestone);
	}

	/**
	 * Method to set the latest achieved milestone
	 *
	 * @param latestAchievedMilestone the latest achieved milestone
	 */
	private void setLatestAchievedMilestone(Milestone latestAchievedMilestone) {
		this.latestAchievedMilestone = latestAchievedMilestone;
	}

	//endregion

	//region Checkers

    /**
     * Checker to check if the given name is valid.
     *
     * @param name The name to check.
     *
     * @return True if the give name is not null or empty.
     */
	public boolean isValidName(String name) {
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
	public boolean isValidDescription(String description) {
		if (description == null) return false;
        if (description.equals("")) return false;
        else return true;
    }

    /**
     * Checker to check if the given starting date is valid.
     *
     * @param startingDate The starting date to check.
     *
     * @return True if the startingdate is later than the creation date.
     */
	public boolean isValidStartingDate(TheDate startingDate) {
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
	public boolean isValidBudget(double budget) {
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
	public boolean isValidVersionID(double versionID) {
		if (versionID <= this.getVersionID()) return false;
        else return true;
    }

	//endregion

	// region Operations
	
	/**
	 * Method to add a subsystem to the list of subsystems.
     *
	 * @param subSystem The subsystem to add.
     *
     * @throws IllegalArgumentException The given subsystem is null.
	 */
	public void addSubSystem(SubSystem subSystem)
	{

		if(subSystem == null) throw new IllegalArgumentException("Subsystem is null");
		
		subSystems.add(subSystem);
		subSystem.addObserver(this);
	}

    /**
     * Method to add a role to the list of roles.
     *
     * @param role The role to add to the list of roles.
     *
     * @throws IllegalArgumentException The given role is null.
     */
	void addRole(Role role)
	{
		if(role == null) throw new IllegalArgumentException("Role is null");
		devsRoles.add(role.copy());
	}

	/**
	 * Method to fork a project.
	 * 
	 * @return The forked project.
	 * 
	 * @throws ReportErrorToUserException is thrown if one of attributes of the project could not be forked.
	 */
	//fork != clone
	Project fork() throws ReportErrorToUserException
	{
		Project forkedProject = new Project(name,description,startingDate.copy(),budget, (Lead)leadRole.copy());
		
		forkedProject.creationDate = creationDate.copy();
		forkedProject.versionID = versionID;
		
	
		for(SubSystem subsystem : subSystems)
			forkedProject.subSystems.add(subsystem.fork());
			
		for(Role role : devsRoles)
			forkedProject.devsRoles.add(role.copy());
		
		return forkedProject;
	}

	/**
	 * Method to add an older achieved milestone to the milestone list
	 *
	 * @param milestone the milestone that needs to be added to the list
     */
	private void addMilestoneToList(Milestone milestone) {
		this.milestones.add(milestone);
        Collections.sort(milestones);
    }
    
	/**
	 * Method to represent a project as a string.
	 * 
	 * @return The project as a string.
	 */
	@Override
	public String toString(){
		String string = "Project name: " + getName()
				+ "\nDescription: " + getDescription()
				+ "\nCreation Date: " + getCreationDate()
				+ "\nStarting Date: " + getStartingDate() + "\nBudget: " + getBudget()
				+ "\nVersionID: " + versionID
				+ "\nMilestone: " + this.getLatestAchievedMilestone()
				+ "\nLead developer: " + getLeadRole().getDeveloper() + "\n";

		for (Role role : devsRoles) {
			string += role.toString() + "\n";
		}

		return string;

	}

	//endregion

	//region Memento Functions

	 /**
     * Method called to notify any observers
     * 
     * @param s The subject
     * @param aspect The aspect that has changed
     */
	@Override
	public void update(Subject s, BugReport bugReport, Object aspect) {
		notifyObservers(bugReport, aspect);
		
	}

	/**
	 * Method to create and return a new memento object
	 *
	 * @return the new memento object for this project
     */
	@Override
	public ProjectMemento createMemento()
	{
		return new ProjectMemento(this);
	}

	/**
	 * Method to restore this object given the memento
	 * 
	 * @param memento The memento to restore to
	 */
	@Override
	public void restoreMemento(ProjectMemento memento)
	{
		this.name = memento.getName();
		this.description = memento.getDescription();
		this.startingDate = memento.getStartingDate();
		this.budget = memento.getBudget();
		this.versionID = memento.getVersionID();
		
		this.subSystems = memento.getSubsystems();
		
		for(SubSystem.SubSystemMemento subsystemMemento : memento.getSubsystemMementos())
			subsystemMemento.getOriginator().restoreMemento(subsystemMemento);
		
		this.devsRoles = memento.getDevsRoles();
		
		this.observers = memento.getObservers();
		
		this.latestAchievedMilestone = memento.getLatestAchievedMilestone();
		this.milestones = memento.getMilestones();
		
	}

	//endregion

	//region Innerclass Memento

	 /**
    * This class provides utility for saving the state of the system at a certain point in time
    * during execution of the Bug Trap software.
    *
    * The project memento saves the state of the following attributes of the project:
    * name, description, startingDate, budget, versionId, subsystems, devsRoles, latestAchievedMilestone,
    * milestones.
    *
    * This class provides private methods to request the values of the saved fields.
    * This wide interface (private getters + public constructor) is provided to the class ProjectService,
    * while the narrow interface (public constructor) is provided to any class.
    */
	public class ProjectMemento extends Memento<Project>
	{
		private String name;
		private String description;
		private TheDate startingDate;
		private double budget;
		private double versionID;
		
		private List<SubSystem> subsystems;
		
		private List<SubSystem.SubSystemMemento> subsystemMementos = new ArrayList<>();
		
		private List<Observer> observers;
		
		private List<Role> devsRoles;
		
		private Milestone latestAchievedMilestone;
		private List<Milestone> milestones;
		
		/**
    	 * Constructor 
    	 * 
    	 * @param originator The originator to build a memento from
    	 */
		public ProjectMemento(Project originator)
		{
			super(originator);
			this.name = originator.getName();
			this.description = originator.getDescription();
			this.startingDate = originator.getStartingDate();
			this.budget = originator.getBudget();
			this.versionID = originator.getVersionID();
			
			this.subsystems =  new ArrayList<>(originator.getSubSystems());
			
			for(SubSystem subsystem : subsystems)
				subsystemMementos.add(subsystem.createMemento());
			
			this.devsRoles =  new ArrayList<>(originator.getDevsRoles());
			
			this.observers =  new ArrayList<>(originator.getObservers());
			
			this.latestAchievedMilestone = originator.getLatestAchievedMilestone();
			this.milestones =  new ArrayList<>(originator.getAllMilestones());
		}
		
		
		private String getName()
		{
			return name;
		}
		
		private String getDescription()
		{
			return description;
		}
		
		private TheDate getStartingDate()
		{
			return startingDate;
		}
		
		private double getBudget()
		{
			return budget;
		}
		
		private double getVersionID()
		{
			return versionID;
		}
		
		private List<SubSystem> getSubsystems()
		{
			return new ArrayList<>(subsystems);
		}
		
		private List<SubSystem.SubSystemMemento> getSubsystemMementos()
		{
			return subsystemMementos;
		}
		
		private List<Role> getDevsRoles()
		{
			return new ArrayList<>(devsRoles);
		}
		
		private List<Observer> getObservers()
		{
			return new ArrayList<>(observers);
		}
		
		private Milestone getLatestAchievedMilestone()
		{
			return latestAchievedMilestone;
		}
		
		private List<Milestone> getMilestones()
		{
			return new ArrayList<>(milestones);
		}
	}

	//endregion


}
