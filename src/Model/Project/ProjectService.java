package Model.Project;


import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is used as a service to operate on projects and subsystems.
 *
 */
public class ProjectService
{
    private IListWrapper<Project> projectList;

    /**
     * Default constructor for a project service.
     */
    public ProjectService()
    {
        this.projectList = new ListWrapper<>();
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
     */
    public Project forkProject(Project project) throws ReportErrorToUserException
    {
    	Project forkedProject = project.fork();
    	projectList.insert(forkedProject);
		return forkedProject;	
    }

    /**
     * Method to create a subsystem and insert it into an existing subsystem.
     *
     * @param name The name of the new subsystem.
     * @param description The description of the new subsystem.
     * @param subSystem The subsystem to insert the new one in.
     *
     * @return The newly created subsystem.
     *
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
     * @param name The name of the new subsystem.
     * @param description The description of the new subsystem.
     * @param project The project to insert the new subsystem in.
     *
     * @return The newly created subsystem.
     *
     * @throws ReportErrorToUserException One of the string arguments are wrong.
     */
    public SubSystem createSubsystem(String name, String description, Project project) throws ReportErrorToUserException {
        if (project == null) throw new IllegalArgumentException("Project is null");
        SubSystem newSubsystem = new SubSystem(name, description);
        project.addSubSystem(newSubsystem);

        return newSubsystem;
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
     * Method for requesting the project containing the bugreport.
     *
     * @param bugReport Bugreport to get the project of.
     *
     * @return The project containing the bugreport.
     *
     * @throws ReportErrorToUserException There is no project containing the given bugreport.
     */
    public Project getProjectsContainingBugReport(BugReport bugReport) throws ReportErrorToUserException
    {
        for(Project project : this.getAllProjects()){
            if (project.getAllBugReports().contains(bugReport)){
                return project;
            }
        }
        throw new ReportErrorToUserException("There is no project containing the given bugreport.");
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
}