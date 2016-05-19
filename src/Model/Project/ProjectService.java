package Model.Project;


import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Memento.Memento;
import Model.Memento.Originator;
import Model.Milestone.Milestone;
import Model.Project.Project.ProjectMemento;
import Model.Roles.Lead;
import Model.Roles.Role;
import Model.User.Developer;
import Model.User.User;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used as a service to operate on projects and subsystems.
 */
public class ProjectService implements Originator<ProjectService.ProjectServiceMemento, ProjectService>
{
    private IListWrapper<Project> projectList;

    /**
     * Default constructor for a project service.
     */
    public ProjectService()
    {
        this.projectList = new ListWrapper<>();
    }

    //region Projects

    private boolean contains(Project projectCheck)
    {
    	for(Project project : getAllProjects())
    		if(projectCheck.equals(project)) return true;
    	return false;
    }
    
    private boolean contains(SubSystem subsystemCheck)
    {
    	for(Project project : getAllProjects())
    		for(SubSystem subsystem : project.getAllSubSystems())
    			if(subsystemCheck.equals(subsystem)) return true;
    	return false;
    }
    
    /**
     * Method to split a subsystem
     * 
     * @param sToSplit The subsystem to split
     * @param project The project of the subsystem
     * @param name1	The name of the first splitted subsystem
     * @param description1	The description of the first splitted subsystem
     * @param name2	The name of the second splitted subsystem
     * @param description2	The description of the second splitted subsystem
     * @param s1	The list of subsystem for the first splitted subsystem
     * @param b1	The list of bug report for the first splitted subsystem
     * @param s2	The list of subsystem for the second splitted subsystem
     * @param b2	The list of bug report for the second splitted subsystem
     * 
     * @throws ReportErrorToUserException
     * @throws IllegalArgumentException when one of the parameters is null, 
     * 		the subsystem is not from the project,
     * 		the list of subsystems/bug reports or not from the subsystem
     */
    public void split(SubSystem sToSplit, Project project, String name1, String description1, String name2, String description2,
    			List<SubSystem> s1, List<BugReport> b1, List<SubSystem> s2, List<BugReport> b2) throws ReportErrorToUserException
    {
    	//check
    	if(sToSplit == null) throw new IllegalArgumentException("SubSystem to split cannot be null");
    	if(project == null) throw new IllegalArgumentException("The project cannot be null");
    	if(s1 == null) throw new IllegalArgumentException("The first list of subsystem cannot be null"); 
    	if(b1 == null) throw new IllegalArgumentException("The first list of bug report cannot be null"); 
    	if(s2 == null) throw new IllegalArgumentException("The second list of subsystem cannot be null"); 
    	if(b2 == null) throw new IllegalArgumentException("The second list of bug report cannot be null"); 
    	
    	if(!project.getAllSubSystems().contains(sToSplit)) throw new IllegalArgumentException("The project or subsystem is incorrect");
    	
    	if(!sToSplit.getSubSystems().containsAll(s1)) throw new IllegalArgumentException("The first list of subsystem does not belongs to subsystem to split"); 
    	if(!sToSplit.getSubSystems().containsAll(s2)) throw new IllegalArgumentException("The second list of subsystem does not belongs to subsystem to split"); 

    	if(!sToSplit.getBugReports().containsAll(b1))throw new IllegalArgumentException("The first list of subsystem cannot be null"); 
    	if(!sToSplit.getBugReports().containsAll(b2))throw new IllegalArgumentException("The first list of subsystem cannot be null"); 

        SubSystem parent = getParentSubSystem(sToSplit);
        
        SubSystem subSystem1, subSystem2;
        if(parent != null)
        {
        	subSystem1 = createSubsystem(name1, description1, parent);
        	subSystem2 = createSubsystem(name2, description2, parent);
        }
        else
        {
        	subSystem1 = createSubsystem(name1, description1, project);
        	subSystem2 = createSubsystem(name2, description2, project);
        }
        
        for(SubSystem sub : s1)
        	subSystem1.addSubSystem(sub);
        for(BugReport bug : b1)
        	subSystem1.addBugReport(bug);
        
        for(SubSystem sub : s2)
        	subSystem2.addSubSystem(sub);
        for(BugReport bug : b2)
        	subSystem2.addBugReport(bug);
        
        setNewSubSystemMilestone(subSystem1, sToSplit.getCurrentSubsystemMilestones());
        setNewSubSystemMilestone(subSystem2, sToSplit.getCurrentSubsystemMilestones());
        
        removeSubSystem(project, sToSplit);
    }
    
    /**
     * Method to merge subsystems
     * 
     * @param project The project of the subsystems
     * @param origin	The subsystem to merge
     * @param related	The related subsystem 
     * @param name		The name for the merged subsystem
     * @param description	The description for the merged subsystem
     * 
     * @throws ReportErrorToUserException
     * @throws IllegalArgumentException One of the arguments is null,
     * 		subsystem is not from the project,
     * 		the related subsystem is not the origin's related,
     */
    public void merge(Project project, SubSystem origin, SubSystem related, String name, String description) throws ReportErrorToUserException
    {
    	if(project == null) throw new IllegalArgumentException("Project cannot be null");
    	if(origin == null) throw new IllegalArgumentException("SubSystem cannot be null");
    	if(related == null) throw new IllegalArgumentException("Related subSystem cannot be null");
    	  	
    	if(!project.getAllSubSystems().contains(origin)) throw new IllegalArgumentException("The project or subsystem is incorrect");

    	if(origin.equals(related))throw new IllegalArgumentException("Incorrect related subSystem");
    	if(!getRelated(project, origin).contains(related)) throw new IllegalArgumentException("Incorrect related subsystem");
    	
    	SubSystem reference = origin.getHeight() > related.getHeight() ? origin : related;
        SubSystem parentReferenceSubSystem = getParentSubSystem(reference);
        
        SubSystem newSubSystem;
        if(parentReferenceSubSystem != null)
        	newSubSystem = createSubsystem(name, description, parentReferenceSubSystem);
        else
        	newSubSystem = createSubsystem(name, description, project);
        	
	    for(SubSystem sub : origin.getSubSystems())
	    	if(!origin.equals(sub) && !related.equals(sub))
	    		newSubSystem.addSubSystem(sub);
	    for(SubSystem sub : related.getSubSystems())
	    	if(!origin.equals(sub) && !related.equals(sub))
	    		newSubSystem.addSubSystem(sub);
	   	
	    for(BugReport bug : origin.getBugReports())
	    	newSubSystem.addBugReport(bug);
	    for(BugReport bug : related.getBugReports())
	    	newSubSystem.addBugReport(bug);
        
    	Milestone lowerMilestone = 
    			origin.getLatestAchievedMilestone().compareTo(related.getLatestAchievedMilestone())
    			 < 0 ? origin.getLatestAchievedMilestone() : related.getLatestAchievedMilestone();
    	setNewSubSystemMilestone(newSubSystem, lowerMilestone);
        
    	removeSubSystem(project, origin);
    	removeSubSystem(project, related);
    }

    /**
     * Getter to request all the projects.
     *
     * @return An unmodifiable list of all the projects.
     */
    public List<Project> getAllProjects()
    {
        return Collections.unmodifiableList(projectList.getAll());
    }

    /**
     * Method to create a new project and add it to the project list.
     *
     * @param name The name of the project.
     * @param description The description of the project.
     * @param startingDate The starting date of the project.
     * @param budget The budget of the project.
     * @param leadRole The lead of the project.
     *
     * @return The newly created project.
     *
     * @throws ReportErrorToUserException One of the given arguments is not valid.
     */
    public Project createProject(String name, String description, TheDate startingDate, double budget, Lead leadRole) throws ReportErrorToUserException {
        Project project = new Project(name, description, startingDate, budget, leadRole);
        projectList.insert(project);
        return project;
    }
    
    /**
     * Method to fork a project and add it to the project list.
     * 
     * @param project The project that will be forked
     * 
     * @return The forked project
     * 
     * @throws ReportErrorToUserException One of attributes of the project could not be forked.
     * @throws IllegalArgumentException the project is null
     */
    public Project forkProject(Project project) throws ReportErrorToUserException
    {
    	if(project == null) throw new IllegalArgumentException("The project cannot be null");
    	
    	Project forkedProject = project.fork();
    	
    	projectList.insert(forkedProject);
		return forkedProject;	
    }

    /**
     * Method for removing a project from the list of projects.
     *
     * @param project The project to remove.
     * 
     * @throws IllegalArgumentException the project is null
     */
    public void deleteProject(Project project)
    {
    	if(project == null) throw new IllegalArgumentException("The project cannot be null");
        projectList.delete(project);
    }
    
    /**
     * Method for requesting all the projects with the given developer as lead.
     *
     * @param developer The developer of which to get de project he/she leads.
     *
     * @return An unmodifiable list of all the projects the given developer leads.
     * 
     * @throws IllegalArgumentException the developer is null
     */
    public List<Project> getProjectsOfLeadRole(Developer developer)
    {
    	if(developer == null) throw new IllegalArgumentException("The developer cannot be null");
        List<Project> prjList = projectList.getAllMatching(x -> x.getLeadRole().getDeveloper().equals(developer));
        return Collections.unmodifiableList(prjList);
    }

    /**
     * Method for requesting the project containing the bug report.
     *
     * @param bugReport Bug report to get the project of.
     *
     * @return The project containing the bug report.
     *
     * @throws ReportErrorToUserException There is no project containing the given bug report.
     * @throws IllegalArgumentException Bug report is null
     */
    public Project getProjectsContainingBugReport(BugReport bugReport) throws ReportErrorToUserException
    {
    	if(bugReport == null) throw new IllegalArgumentException("Bugreport is null");
    	
        for(Project project : this.getAllProjects()){
            if (project.getAllBugReports().contains(bugReport)){
                return project;
            }
        }
        throw new ReportErrorToUserException("There is no project containing the given bug report.");
    }

    //endregion

    //region Project Setters

    /**
     * Setter to set the name of the project.
     *
     * @param project The project to set the name of.
     * @param name The name of the project
     * 
     * @throws ReportErrorToUserException The given name is empty.
     * @throws IllegalArgumentException Project is null
     */
    public void setProjectName(Project project, String name) throws ReportErrorToUserException {
    	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setName(name);
    }

    /**
     * Setter to set the description of the project
     *
     * @param project The project to set the description of.
     * @param description The description of the project
     * 
     * @throws ReportErrorToUserException The given description is empty.
     * @throws IllegalArgumentException Project is null
     */
    public void setProjectDescription(Project project, String description) throws ReportErrorToUserException {
     	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setDescription(description);
    }

    /**
     * Setter to set the starting date of the project.
     *
     * @param project The project to set the date of.
     * @param date The starting date of the project.
     * 
     * @throws ReportErrorToUserException The given date is before the creation date.
     * @throws IllegalArgumentException Project is null
     */
    public void setProjectStartingDate(Project project, TheDate date) throws ReportErrorToUserException {
     	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setStartingDate(date);
    }

    /**
     * Setter to set the budget of the project.
     *
     * @param project The project to set the budget of.
     * @param newBudget The budget of the project.
     * 
     * @throws ReportErrorToUserException The budget is negative.
     * @throws IllegalArgumentException Project is null
     */
    public void setProjectBudget(Project project, double newBudget) throws ReportErrorToUserException {
     	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setBudget(newBudget);
    }

    /**
     * Setter to set the versionId of the project.
     *
     * @param project The project to set the version id of.
     * @param versionID The versionId to set the project to.
     * 
     * @throws ReportErrorToUserException The given versionId is lower than or equal to the current one.
     * @throws IllegalArgumentException Project is null
     */
    public void setProjectVersionID(Project project, double versionID) throws ReportErrorToUserException {
     	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setVersionID(versionID);
    }

    /**
     * Setter to set the lead of the project
     *
     * @param project The project to assign the lead to.
     * @param leadRole The lead to assign to the project.
     * 
     * @throws IllegalArgumentException The given role is null.
     * @throws IllegalArgumentException Project is null
     */
    public void setProjectLeadRole(Project project, Lead leadRole) {
     	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setLeadRole(leadRole);
    }


    /**
     * Method to set a new project milestone.
     * 
     * There occurs consistency checking:
     * first pass: project milestone should not exceed any subsystem milestone
     * second pass: project milestone should not exceed the target milestone of
     * any related bug report with a non-final tag.
     *
     * @param project The project to assign the new milestone to.
     * @param newProjectMilestone the new project milestone that has to be set
     * 
     * @throws ReportErrorToUserException is thrown in case that a constraint is broken.
     * @throws IllegalArgumentException Project is null
     */
    public void setNewProjectMilestone(Project project, Milestone newProjectMilestone) throws ReportErrorToUserException {
    	if(project == null) throw new IllegalArgumentException("Project is null");
    	project.setNewProjectMilestone(newProjectMilestone);
    }


    //endregion

    //region Subsystems

    /**
     * Method to create a subsystem and insert it into an existing subsystem.
     *
     * @param name        The name of the new subsystem.
     * @param description The description of the new subsystem.
     * 
     * @param subSystem   The subsystem to insert the new one in.
     * @return The newly created subsystem.
     * 
     * @throws ReportErrorToUserException One of the string arguments are wrong.
     * @throws IllegalArgumentException the subsystem is null
     */
    public SubSystem createSubsystem(String name, String description, SubSystem subSystem) throws ReportErrorToUserException {
        if (subSystem == null) throw new IllegalArgumentException("Subsystem is null");
        SubSystem newSubsystem = new SubSystem(name, description);
        subSystem.addSubSystem(newSubsystem);

        return newSubsystem;
    }

    /**
     * Method to create a subsystem and insert it into an existing project.
     *
     * @param name        The name of the new subsystem.
     * @param description The description of the new subsystem.
     * @param project     The project to insert the new subsystem in.
     * 
     * @return The newly created subsystem.
     * 
     * @throws ReportErrorToUserException One of the string arguments are wrong.
     * @throws IllegalArgumentException the project is null
     */
    public SubSystem createSubsystem(String name, String description, Project project) throws ReportErrorToUserException {
        if (project == null) throw new IllegalArgumentException("Project is null");
        SubSystem newSubsystem = new SubSystem(name, description);
        project.addSubSystem(newSubsystem);

        return newSubsystem;
    }
    
    /**
     * Remove a subsystem from a project recursively
     * 
     * @param project The project of origin
     * @param subSystem The subsystem to remove
     * 
     * @throws IllegalArgumentException project or subsystem is null
     */
    public void removeSubSystem(Project project, SubSystem subSystem)
    {
    	if(project == null) throw new IllegalArgumentException("Project is null");
    	if(subSystem == null) throw new IllegalArgumentException("Subsystem is null");
    	project.removeSubSystem(subSystem);
    }
 

    /**
     * Method for requesting all the subsystems of all the projects.
     *
     * @return List containing all the subsystems of all the projects.
     */
    public List<SubSystem> getAllSubSystems()
    {
        List<SubSystem> subSystems = new ArrayList<>();
        for (Project project: this.getAllProjects())
           subSystems.addAll(project.getAllSubSystems());
        return subSystems;
    }

    //endregion

    //region Subsystem setters

    /**
     * Setter to set the name of the subsystem.
     *
     * @param subSystem The subsystem to set the name of.
     * @param name The name of the subsystem
     * 
     * @throws ReportErrorToUserException The given name is empty.
     * throws IllegalArgumentException The subsystem is null.
     */
    public void setSubSystemName(SubSystem subSystem, String name) throws ReportErrorToUserException {
        if(subSystem == null) throw new IllegalArgumentException("The subsystem cannot be null");
    	subSystem.setName(name);}

    /**
     * Setter to set the description of the subsystem.
     *
     * @param subSystem The subsystem to set the description of.
     * @param description The description of the subsystem.
     * 
     * @throws ReportErrorToUserException The given description is empty.
     * throws IllegalArgumentException The subsystem is null.
     */
    public void setSubSystemDescription(SubSystem subSystem, String description) throws ReportErrorToUserException {
    	if(subSystem == null) throw new IllegalArgumentException("The subsystem cannot be null");
    	subSystem.setDescription(description);}

    /**
     * Method to set a new subsystem milestone.
     * 
     * There occurs consistency checking:
     * first pass: subsystem milestone should not exceed any recursive subsystem's milestone
     * second pass: subsystem milestone should not exceed the target milestone of
     * any related bug report with a non-final tag.
     *
     * @param subSystem The subsystem to set the new milestone of.
     * @param newSubsystemMilestone the new subsystem milestone that has to be set
     * 
     * @throws ReportErrorToUserException is thrown in case that a constraint is broken.
     * throws IllegalArgumentException The subsystem is null.
     */
    public void setNewSubSystemMilestone(SubSystem subSystem, Milestone newSubsystemMilestone) throws ReportErrorToUserException {
    	if(subSystem == null) throw new IllegalArgumentException("The subsystem cannot be null");
    	subSystem.setNewSubSystemMilestone(newSubsystemMilestone);
    }

    /**
     * Method to set milestones to a subsystem
     * 
     * @param subSystem The subsystem to set its milestone
     * @param milestoneList The list of milestones 
     * 
     * @throws IllegalArgumentException subsystem or milestoneList is null
     * @throws ReportErrorToUserException is thrown in case that a constraint is broken
     */
    public void setNewSubSystemMilestone(SubSystem subSystem, List<Milestone> milestoneList) throws ReportErrorToUserException
    {
    	if(subSystem == null) throw new IllegalArgumentException("The subsystem cannot be null");
    	if(milestoneList == null) throw new IllegalArgumentException("The milestone list cannot be null");
    	
    	for(Milestone milestone : milestoneList)
    		subSystem.setNewSubSystemMilestone(milestone);
    }
    
    //endregion

    //region Functions

    /**
     * Method to assign a role to a user for a specified project.
     *
     * @param project the project to which the role assignment is being made
     * @param role the role that is being set to the specified user
     * @param user the user to which the new role is being assigned
     *
     * @throws ReportErrorToUserException is thrown if the user cannot be assigned
     *                                    to the role in the project.
     * @throws IllegalArgumentException The project, role or user is null.
     */
    public void assignRole(Project project, Role role, User user) throws ReportErrorToUserException {
        if (project == null) throw new IllegalArgumentException("Project is null");
        if (role == null) throw new IllegalArgumentException("Role is null");
        if (user == null) throw new IllegalArgumentException("User is null");
        if (!canAssignRole(project, role, user))
            throw new ReportErrorToUserException("You cannot assign this developer to the project.");
        project.addRole(role);
    }

    //endregion

    //region Checker

    private boolean canAssignRole(Project project, Role role, User user) {
    	if(project == null) return false;
    	if(role == null) return false;
    	if(user == null) return false;
    	
        if (!project.getLeadRole().getDeveloper().equals(user)) {
            return false;
        } else if (isRoleAlreadyInProject(project, role)) {
            return false;
        }
        return true;
    }

    private boolean isRoleAlreadyInProject(Project project, Role role) {
    	if(project == null) throw new IllegalArgumentException("The project is null");
    	if(role == null) throw new IllegalArgumentException("The role is null");
    	
        return project.getDevsRoles().stream().anyMatch(x -> x.getDeveloper().equals(role.getDeveloper())
                && x.getClass().equals(role.getClass()));
    }

    //endregion

    //region Memento Functions

    /**
     * Method for requesting the subsystem containing the bug report.
     *
     * @param bugReport Bugreport to get the subsystem of.
     * @return The subsystem containing the bug report.
     * 
     * @throws ReportErrorToUserException There is no subsystem containing the given bug report.
     * @throws IllegalArgumentException The bug report is null.
     */
    public SubSystem getSubsystemWhichContainsBugReport(BugReport bugReport) throws ReportErrorToUserException {
        if(bugReport == null) throw new IllegalArgumentException("The bug report cannot be null");
        
    	for (SubSystem subSystem : this.getAllSubSystems()) {
            if (subSystem.getBugReports().contains(bugReport)) {
                return subSystem;
            }
        }
        throw new ReportErrorToUserException("Ther is no subsystem that contains the given bug report.");}

    
    /**
     * Method to get the subsystem parent of a given subsystem
     * 
     * @param subsystem The subsystem to find its parent
     * 
     * @return The subsystem parent
     */
    public SubSystem getParentSubSystem(SubSystem subsystem)
    {
    	for(SubSystem sub : getAllSubSystems())
    		if(sub.isParent(subsystem)) return sub;
    	return null;
    }
    
    /**
     * Method to get all related subsystem of the given subsystem
     * 
     * @param origin The project of origin
     * @param subsystem	The subsystem to get its related
     * 
     * @return The list of related subsystems
     */
    public List<SubSystem> getRelated(Project origin, SubSystem subsystem)
    {
    	List<SubSystem> related = new ArrayList<>();
    	related.addAll(subsystem.getSubSystems());
    	SubSystem parentSubSystem = getParentSubSystem(subsystem);
    	if(parentSubSystem != null)
    	{
    		related.add(parentSubSystem);
    		for(SubSystem sibling : parentSubSystem.getSubSystems())
    			if(!subsystem.equals(sibling))
    				related.add(sibling);
    	}			
    	else
    	{
    		for(SubSystem sibling : origin.getSubSystems())
    			if(!subsystem.equals(sibling))
    				related.add(sibling);
    	}
    	
    	return related;
    }
    
    /**
     * Method to create a memento of this object
     * 
     * @return The memento of this object
     */
	@Override
	public ProjectServiceMemento createMemento() 
	{
		return new ProjectServiceMemento(this);
	}
	
	/**
	 * Method to restore this object given the memento
	 * 
	 * @param memento The memento to restore to
	 * 
	 * @throws IllegalArgumentException the memento is null
	 */
	@Override
	public void restoreMemento(ProjectServiceMemento memento)
    {
		if(memento == null) throw new IllegalArgumentException("The memento cannot be null");
		
    	this.projectList = new ListWrapper<>(memento.getListProject());
    	
    	for(ProjectMemento projectMemento :	memento.getProjectMementos())
    		projectMemento.getOriginator().restoreMemento(projectMemento);
    	
    }

    //endregion

    //region Innerclass Memento

    /**
     * This class provides utility for saving the state of the system at a certain point in time
     * during execution of the Bug Trap software.
     *
     * The projectService memento saves the state of the following attributes of the projectService:
     * listProject.
     *
     * This class provides private methods to request the values of the saved fields.
     * This wide interface (private getters + public constructor) is provided to the class ProjectService,
     * while the narrow interface (public constructor) is provided to any class.
     */
	public class ProjectServiceMemento extends Memento<ProjectService>
	{
		private List<Project> listProject;
		private List<ProjectMemento> projectMementos = new ArrayList<>();
		
		/**
    	 * Constructor 
    	 * 
    	 * @param originator The originator to build a memento from
    	 * 
    	 * @throws IllegalArgumentException the originator is null
    	 */
		public ProjectServiceMemento(ProjectService originator)
		{
			super(originator);
			listProject = new ArrayList<>(originator.getAllProjects());
			for(Project p : listProject)
				projectMementos.add(p.createMemento());
		}
		
		/**
    	 * Returns the list project of the projectService memento
    	 * @return the list project of the projectService memento
    	 */
		private List<Project> getListProject()
		{
			return listProject;
		}
		
		/**
		 * Returns the list of project mementos.
		 * @return the list of project mementos.
		 */
		private List<ProjectMemento> getProjectMementos()
		{
			return projectMementos;
		}
	}

    //endregion
}