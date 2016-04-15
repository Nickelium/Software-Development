package Model.Memento;

import Controller.UserController.UseCases.UseCase;
import Model.Mail.MailboxService;
import Model.Project.ProjectService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will save the system multiple times and keep these saves.
 * This class provides a mechanism to restore the system to one of the saves.
 * 
 */
public class Caretaker 
{
	private List<Snapshot> snapshots = new ArrayList<>();
	
	private ProjectService projectService;
	private MailboxService mailboxService;
	
	/**
	 * Constructor 
	 * 
	 * @param projectService The project service to use.
	 * @param mailboxService The mailbox service to use.
	 */
	public Caretaker(ProjectService projectService, MailboxService mailboxService)
	{
		this.projectService = projectService;
		this.mailboxService = mailboxService;
	}
	
	/**
	 * Method to save the system
	 * 
	 * @param usecase The usecase that lead to a save of the system
	 */
	public void saveState(UseCase usecase)
	{
		snapshots.add(new Snapshot(usecase, projectService, mailboxService));
	}
	
	/**
	 * Method to restore the system to the given snapshot
	 * 
	 * @param snapshot The snapshot to restore to
	 */
	public void restoreState(Snapshot snapshot)
	{
		snapshot.restore();
	}
	
	public List<Snapshot> getSnapshots(int number)
	{
		int startIndex = snapshots.size() - number;
		if(startIndex < 0)
			startIndex = 0;
		return snapshots.subList(startIndex, snapshots.size());
	}
}
