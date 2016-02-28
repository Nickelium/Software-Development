package Model.Project;


import Model.Wrapper.IListWrapper;
import Model.Wrapper.ListWrapper;
import Model.Roles.Lead;
import Model.User.Developer;

import java.util.Collections;
import java.util.List;

// TODO documentatie
public class ProjectService
{
		private IListWrapper<Project> projectRepository;
		
		public ProjectService()
	 	{
			IListWrapper<Project> pRepository = new ListWrapper<>();
			setProjectRepository(pRepository);
	    }

	    /**
	     * Returns an list with all users.
	     * @return An list with all users.
	     */
	    public List<Project> getProjectList() {
	        return Collections.unmodifiableList(projectRepository.getAll());
	    }

	    /**
	     * Sets the IRepository object with all users.
	     * @param projectRepository The new IRepository object with users.
	     */
	    private void setProjectRepository(IListWrapper<Project> projectRepository) {
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
	    public void addSubSystemToProject(Project project, SubSystem subsystem)
	    {
	    	if(project != null && subsystem != null) project.addSubSystem(subsystem);
	    }
	    
	    /**
	     * Adds a new user to the user repository if there doesn't exist a user object with
	     * the same username in the User Repository yet.
	     *
	     * @throws IllegalArgumentException
	     * Throws an IllegalArgumentException in case there already exists a User object
	     * in the User Repository with the same username.
	     *
	     * @param s The user object that needs to be added to the user repository.
	     * @throws Exception 
	     */
	    public void addSubSystemToSubSystem(SubSystem s, SubSystem toAdd) throws Exception
	    {
	    	if(s != null && toAdd != null)	s.addSubSystem(toAdd);
	    }

	    /**
	     * Updates a user object with given username with the instance of the user object
	     * in the user repository by calling an update statement of the repository.
	     *
	     * @param oldProject the user object that needs to be updated in the user repository.
	     */
	    //public void updateProject(Project oldProject, Project newProject)
	    //{
	    //    projectRepository.update(((s)-> s.equals(oldProject)), newProject);
	    //}

	    /**
	     * Deletes a given user object from the user repository by calling a delete
	     * statement of the repository.
	     *
	     * @param project the user object that needs to be deleted from the user repository.
	     */
	    void deleteProject(Project project)
	    {
	        projectRepository.delete(project);
	        project.destructor();
	    }
	    
	    public List<SubSystem> getAllSubSystemFromProject(Project p)
	    {
	    	return p != null ? p.getAllSubSystem() : null;
	    }
	    
	    public List<Project> getProjectsOfLeadRole(Developer dev)
	    {
	    	List<Project> prjList = projectRepository.getAllMatching((x)-> x.getLeadRole() != null && x.getLeadRole().getDeveloper().equals(dev));
	    	return Collections.unmodifiableList(prjList);
	    }

		public List<Project> getProjectsWithDeveloper(Developer dev){
			//TODO
			return null;
		}
	}