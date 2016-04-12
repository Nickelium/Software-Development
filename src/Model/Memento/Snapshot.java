package Model.Memento;

import java.util.List;

import Model.Project.Project;
import Model.Project.ProjectMemento;
import Model.Project.ProjectService;
import Model.Project.ProjectServiceMemento;

public class Snapshot
{
	private ProjectServiceMemento projectServiceMemento;
	
	public Snapshot(ProjectService projectService)
	{
		projectServiceMemento = projectService.createMemento();
	}
	
	public void restore()
	{
		projectServiceMemento.getOriginator().restoreMemento(projectServiceMemento);
	}
	
	@Override
	public String toString()
	{
		return null;
	}
}
