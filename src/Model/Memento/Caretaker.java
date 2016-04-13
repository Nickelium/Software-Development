package Model.Memento;

import Controller.UserController.UseCases.UseCase;
import Model.Mail.MailboxService;
import Model.Project.ProjectService;

import java.util.ArrayList;
import java.util.List;

/**
 * //TODO Documenteren van Mail package !!!
 */
public class Caretaker 
{
	private List<Snapshot> snapshots = new ArrayList<>();
	
	private ProjectService projectService;
	private MailboxService mailboxService;
	
	public Caretaker(ProjectService projectService, MailboxService mailboxService)
	{
		this.projectService = projectService;
		this.mailboxService = mailboxService;
	}
	
	public void saveState(UseCase usecase)
	{
		snapshots.add(new Snapshot(usecase, projectService, mailboxService));
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
