package Model.Memento;

import java.util.List;

import Controller.UserController.UseCases.UseCase;
import Model.Project.Project;
import Model.Project.ProjectMemento;
import Model.Project.ProjectService;
import Model.Project.ProjectServiceMemento;

public class Snapshot
{
	private UseCase usecase;
	
	private ProjectServiceMemento projectServiceMemento;
	
	public Snapshot(UseCase usecase, ProjectService projectService)
	{
		projectServiceMemento = projectService.createMemento();
		this.usecase = usecase;
	}
	
	public void restore()
	{
		projectServiceMemento.getOriginator().restoreMemento(projectServiceMemento);
	}
	
	@Override
	public String toString()
	{
		return "Before " + usecase.toString();
	}
}
