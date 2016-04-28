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
	 * 
	 * @throws IllegalArgumentException the projectservice or mailboxservice is null
	 */
	public Caretaker(ProjectService projectService, MailboxService mailboxService)
	{
		if(projectService == null) throw new IllegalArgumentException("The projectService cannot be null");
		if(mailboxService == null) throw new IllegalArgumentException("The mailboxService cannot be null");
		
		this.projectService = projectService;
		this.mailboxService = mailboxService;
	}
	
	/**
	 * Method to save the system
	 * 
	 * @param usecase The usecase that lead to a save of the system
	 * 
	 * @throws IllegalArgumentException the usecase is null
	 */
	public void saveState(UseCase usecase)
	{
		if(usecase == null) throw new IllegalArgumentException("The usecase cannot be null");
		snapshots.add(new Snapshot(usecase, projectService, mailboxService));
	}
	
	/**
	 * Method to restore the system to the given snapshot
	 * 
	 * @param snapshot The snapshot to restore to
	 * 
	 * @throws IllegalArgumentException the snapshot is null
	 */
	public void restoreState(Snapshot snapshot)
	{
		if(snapshot == null) throw new IllegalArgumentException("The snapshot cannot be null");
		snapshot.restore();
	}
	
	/**
	 * Getter to request a number  of snapshots
	 * 
	 * @param number The amount of snapshots to get
	 * 
	 * @return The list of snapshots
	 * 
	 * @throws IllegalArgumentException the number cannot be negative
	 */
	public List<Snapshot> getSnapshots(int number)
	{
		if(number  < 0) throw new IllegalArgumentException("The number cannot be negative");
		
		int startIndex = snapshots.size() - number;
		if(startIndex < 0)
			startIndex = 0;
		return snapshots.subList(startIndex, snapshots.size());
	}
}
