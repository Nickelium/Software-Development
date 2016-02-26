package Model.Project;


import java.util.Collections;
import java.util.List;


public class ProjectService
{
		private IRepository<Project> projectRepository;
		
		public ProjectService(IRepository<Project> projectRepository)
	 	{
	        setProjectRepository(projectRepository);
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
	     * @param userRepository The new IRepository object with users.
	     */
	    private void setProjectRepository(IRepository<Project> projectRepository) {
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
	     * @param user The user object that needs to be added to the user repository.
	     */
	    public void addProject(Project project)
	    {
	            projectRepository.insert(project); 
	    }

	    /**
	     * Adds a new user to the user repository if there doesn't exist a user object with
	     * the same username in the User Repository yet.
	     *
	     * @throws IllegalArgumentException
	     * Throws an IllegalArgumentException in case there already exists a User object
	     * in the User Repository with the same username.
	     *
	     * @param user The user object that needs to be added to the user repository.
	     */
	    public void addSubSystemToProject(Project project, SubSystem subsystem)
	    {
	            project.addSubSystem(subsystem);
	    }
	    
	    /**
	     * Adds a new user to the user repository if there doesn't exist a user object with
	     * the same username in the User Repository yet.
	     *
	     * @throws IllegalArgumentException
	     * Throws an IllegalArgumentException in case there already exists a User object
	     * in the User Repository with the same username.
	     *
	     * @param user The user object that needs to be added to the user repository.
	     * @throws Exception 
	     */
	    public void addSubSystemToSubSystem(SubSystem s, SubSystem toAdd) throws Exception
	    {
	    	s.addSubSystem(toAdd);
	    }
	    
	    /**
	     * Updates a user object with given username with the instance of the user object
	     * in the user repository by calling an update statement of the repository.
	     *
	     * @param user the user object that needs to be updated in the user repository.
	     */
	    public void updateProject(Project oldProject, Project newProject)
	    {
	        projectRepository.update(((s)-> s.equals(oldProject) ), newProject);
	    }

	    /**
	     * Deletes a given user object from the user repository by calling a delete
	     * statement of the repository.
	     *
	     * @param user the user object that needs to be deleted from the user repository.
	     */
	    public void deleteProject(Project project){
	    	//also delete bugreport
	        projectRepository.delete(project);
	    }
	    
	    public List<SubSystem> getAllSubSystemFromProject(Project p)
	    {
	    	return p.getAllSubSystem();
	    }
	    
	    public List<Project> getProjectsOfLeadRole(Developer dev)
	    {
	    	List<Project> prjList = projectRepository.getAllMatching((x)-> x.getLeadRole() != null && dev.equals(x.getLeadRole().getDeveloper()));
	    	return Collections.unmodifiableList(prjList);
	    }
	}