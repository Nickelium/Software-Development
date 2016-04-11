package Model.Memento;

import java.util.ArrayList;
import java.util.List;

import Model.Project.ProjectService;

public class Caretaker 
{
	private List<Snapshot> snapshots = new ArrayList<>();
	
	private ProjectService projectService;
	
	public Caretaker(ProjectService projectService)
	{
		this.projectService = projectService;
	}
	
	public void saveState()
	{
		snapshots.add(new Snapshot(projectService));
	}
	
	public void restoreState(int stateNumber)
	{
		snapshots.get(stateNumber).restore();
	}
}
