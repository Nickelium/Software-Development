package Model.Memento;

import java.util.ArrayList;
import java.util.List;

import Controller.UserController.UseCases.UseCase;
import Model.Project.ProjectService;

public class Caretaker 
{
	private List<Snapshot> snapshots = new ArrayList<>();
	
	private ProjectService projectService;
	
	public Caretaker(ProjectService projectService)
	{
		this.projectService = projectService;
	}
	
	public void saveState(UseCase usecase)
	{
		snapshots.add(new Snapshot(usecase, projectService));
	}
	
	public void restoreState(int stateNumber)
	{
		snapshots.get(stateNumber).restore();
	}
	
	public List<Snapshot> getSnapshots()
	{
		return snapshots;
	}
}
