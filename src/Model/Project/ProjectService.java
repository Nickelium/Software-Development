package Model.Project;


import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;
import Model.Roles.Lead;
import Model.Roles.Role;
import Model.User.Developer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO documentatie
public class ProjectService
{
		private IListWrapper<Project> projectRepository;
		private BugReportService bugReportService;
		
		public ProjectService(BugReportService bugReportService)
	 	{
			this.bugReportService = bugReportService;
			
			IListWrapper<Project> pRepository = new ListWrapper<>();
			setProjectRepository(pRepository);
	    }

	    /**
	     * Returns an list with all users.
	     * @return An list with all users.
	     */
	    public List<Project> getProjectList() 
	    {
	        return Collections.unmodifiableList(projectRepository.getAll());
	    }

	    /**
	     * Sets the IRepository object with all users.
	     * @param projectRepository The new IRepository object with users.
	     */
	    private void setProjectRepository(IListWrapper<Project> projectRepository)
	    {
	        this.projectRepository = projectRepository;
	    }

	    /**
	     * Adds a new user to the user repository if there doesn't exist a user object with
	     * the same username in the User Repository yet.
	     *
	     * @throws IllegalArgumentException
	     * Throws an IllegalArgumentException in case there already exists a User object
	     * in the User Repository with the same username.
	     *
	     * @param project The user object that needs to be added to the user repository.
	     */
	    public Project addProject(String newName, String newDescription, TheDate newStartingDate, double newBudget, Lead newLeadRole)
	    {
	    	Project prj = new Project(newName, newDescription, newStartingDate, newBudget, newLeadRole);
	    	projectRepository.insert(prj); 
	    	return prj;
	    }

	   /*
	    public void addSubSystemToProject(Project project, SubSystem subsystem)
	    {
	    	if(project != null && subsystem != null) project.addSubSystem(subsystem);
	    }
	    */
	    
	    /*
	    public void addSubSystemToSubSystem(SubSystem s, SubSystem toAdd) throws Exception
	    {
	    	if(s != null && toAdd != null)	s.addSubSystem(toAdd);
	    }
		*/

	    /**
	     * Deletes a given user object from the user repository by calling a delete
	     * statement of the repository.
	     *
	     * @param project the user object that needs to be deleted from the user repository.
	     */
	    void deleteProject(Project project)
	    {
	        projectRepository.delete(project);
	    }
	    
	    /*
	    public List<SubSystem> getAllSubSystemFromProject(Project p)
	    {
	    	return p != null ? Collections.unmodifiableList(p.getAllSubSystem()) : null;
	    }
	    */
	    
	    public List<Project> getProjectsOfLeadRole(Developer dev)
	    {
	    	List<Project> prjList = projectRepository.getAllMatching((x)-> x.getLeadRole() != null && x.getLeadRole().getDeveloper().equals(dev));
	    	return Collections.unmodifiableList(prjList);
	    }

		public List<Project> getProjectsWithDeveloper(Developer dev)
		{
			List<Project> pList = new ArrayList<>();
			for(Project project : projectRepository.getAll())
				for(Role role : project.getDevsRoles())
					if(dev.equals(role.getDeveloper()) && !pList.contains(project) )
					{
						pList.add(project);
						break;
					}
				
			return pList != null ? Collections.unmodifiableList(pList) : null;
		}

		public Project getProjectContainingBugReport(BugReport bugReport)
		{
			for(Project project : projectRepository.getAll())
				for(SubSystem subSystem : project.getAllSubSystem())
					if (subSystem.getBugReports().contains(bugReport))
						return project;
			
			return null;
		}
	}