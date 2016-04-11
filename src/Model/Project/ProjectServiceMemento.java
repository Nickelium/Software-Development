package Model.Project;

import java.util.ArrayList;
import java.util.List;

import Model.Memento.Memento;

public class ProjectServiceMemento extends Memento<ProjectService>
{
	private List<Project> listProject;
	
	public ProjectServiceMemento(ProjectService projectService)
	{
		super(projectService);
		listProject = new ArrayList<>(projectService.getAllProjects());
	}
	
	List<Project> getListProject()
	{
		return listProject;
	}
}
