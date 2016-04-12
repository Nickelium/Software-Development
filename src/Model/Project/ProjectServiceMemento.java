package Model.Project;

import java.util.ArrayList;
import java.util.List;

import Model.Memento.Memento;

public class ProjectServiceMemento extends Memento<ProjectService>
{
	private List<Project> listProject;
	private List<ProjectMemento> projectMementos = new ArrayList<>();
	
	public ProjectServiceMemento(ProjectService projectService)
	{
		super(projectService);
		listProject = new ArrayList<>(projectService.getAllProjects());
		for(Project p : listProject)
			projectMementos.add(p.createMemento());
	}
	
	List<Project> getListProject()
	{
		return listProject;
	}
	
	List<ProjectMemento> getProjectMementos()
	{
		return projectMementos;
	}
}
