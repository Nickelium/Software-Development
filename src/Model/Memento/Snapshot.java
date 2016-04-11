package Model.Memento;

import java.util.List;

import Model.Project.Project;
import Model.Project.ProjectMemento;
import Model.Project.ProjectService;
import Model.Project.ProjectServiceMemento;

public class Snapshot
{
	private ProjectServiceMemento projectServiceMemento;
	private List<ProjectMemento> projectMementos;
	
	
	public Snapshot(ProjectService projectService)
	{
		projectServiceMemento = projectService.createMemento();
		
		for(Project project : projectService.getAllProjects())
			projectMementos.add(project.createMemento());
	}
	
	public void restore()
	{
		projectServiceMemento.getOriginator().restoreMemento(projectServiceMemento);
		
		for(ProjectMemento projectMemento : projectMementos)
			projectMemento.getOriginator().restoreMemento(projectMemento);
	}
}
