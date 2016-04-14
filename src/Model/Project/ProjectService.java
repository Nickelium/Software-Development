package Model.Project;


import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Memento.Memento;
import Model.Memento.Originator;
import Model.Project.Project.ProjectMemento;
import Model.Roles.Lead;
import Model.User.Developer;
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
     */
    public Project forkProject(Project project) throws ReportErrorToUserException
    {
    	Project forkedProject = project.fork();
    	projectList.insert(forkedProject);
		return forkedProject;	
    }

    /**
     * Method for removing a project from the list of projects.
     *
     * @param project The project to remove.
     */
    public void deleteProject(Project project)
    {
        projectList.delete(project);
    }

    /**
     * Method for requesting all the projects with the given developer as lead.
     *
     * @param developer The developer of which to get de project he/she leads.
     *
     * @return An unmodifiable list of all the projects the given developer leads.
     */
    public List<Project> getProjectsOfLeadRole(Developer developer)
    {
        List<Project> prjList = projectList.getAllMatching(x -> x.getLeadRole().getDeveloper().equals(developer));
        return Collections.unmodifiableList(prjList);
    }

    /**
     * Method for requesting the project containing the bug report.
     *
     * @param bugReport Bugreport to get the project of.
     *
     * @return The project containing the bug report.
     *
     * @throws ReportErrorToUserException There is no project containing the given bug report.
     */
    public Project getProjectsContainingBugReport(BugReport bugReport) throws ReportErrorToUserException
    {
        for(Project project : this.getAllProjects()){
            if (project.getAllBugReports().contains(bugReport)){
                return project;
            }
        }
        throw new ReportErrorToUserException("There is no project containing the given bug report.");
    }

    //endregion

    //region Project Setters


    //endregion

    //region Subsystems

    /**
     * Method to create a subsystem and insert it into an existing subsystem.
     *
     * @param name        The name of the new subsystem.
     * @param description The description of the new subsystem.
     * @param subSystem   The subsystem to insert the new one in.
     * @return The newly created subsystem.
     * @throws ReportErrorToUserException One of the string arguments are wrong.
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
     * @return The newly created subsystem.
     * @throws ReportErrorToUserException One of the string arguments are wrong.
     */
    public SubSystem createSubsystem(String name, String description, Project project) throws ReportErrorToUserException {
        if (project == null) throw new IllegalArgumentException("Project is null");
        SubSystem newSubsystem = new SubSystem(name, description);
        project.addSubSystem(newSubsystem);

        return newSubsystem;
    }

    /**
     * Method for requesting all the subsystems of all the projects.
     *
     * @return List containing all the subsystems of all the projects.
     */
    public List<SubSystem> getAllSubSystems(){
        List<SubSystem> subSystems = new ArrayList<>();
        for (Project project: this.getAllProjects()){
           subSystems.addAll(project.getAllSubSystems());
        }
        return subSystems;
    }

    //endregion

    //region Memento Functions

    /**
     * Method for requesting the subsystem containing the bug report.
     *
     * @param bugReport Bugreport to get the subsystem of.
     * @return The subsystem containing the bug report.
     * @throws ReportErrorToUserException There is no subsystem containing the given bug report.
     */
    public SubSystem getSubsystemWhichContainsBugReport(BugReport bugReport) throws ReportErrorToUserException {
        for (SubSystem subSystem : this.getAllSubSystems()) {
            if (subSystem.getBugReports().contains(bugReport)) {
                return subSystem;
            }
        }
        throw new ReportErrorToUserException("Ther is no subsystem that contains the given bug report.");
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
	 */
	@Override
	public void restoreMemento(ProjectServiceMemento memento)
    {
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