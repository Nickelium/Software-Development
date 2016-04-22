package Model.Memento;

import java.util.ArrayList;
import java.util.List;

import Controller.UserController.UseCases.UseCase;
import Model.Mail.Mailbox;
import Model.Mail.MailboxService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.User.UserService;

/**
 * Class that represent a snapshot moment of the system.
 * This class saves the projectservice and all mailboxes and can restore the system.
 *
 */
public class Snapshot
{
	private UseCase usecase;
	
	private ProjectService.ProjectServiceMemento projectServiceMemento;
	private List<Mailbox.MailboxMemento> mailboxMementos = new ArrayList<>();
	
	/**
	 * Constructor 
	 * 
	 * @param usecase The usecase that lead to snapshot save of the system
	 * @param projectService The project service in use.
	 * @param mailboxService The mailbox service in use.
	 * 
	 * @throws IllegalArgumentException the user, projectservice or mailboxservice is null
	 */
	Snapshot(UseCase usecase, ProjectService projectService, MailboxService mailboxService)
	{
		if(usecase == null) throw new IllegalArgumentException("The usecase cannot be null");
		if(projectService == null) throw new IllegalArgumentException("The projectService cannot be null");
		if(mailboxService == null) throw new IllegalArgumentException("The mailboxService cannot be null");

		projectServiceMemento = projectService.createMemento();
		for(Mailbox mailbox : mailboxService.getAllMailboxes())
			mailboxMementos.add(mailbox.createMemento());
		this.usecase = usecase;
	}
	
	/**
	 * Method to restore the system
	 * 
	 */
	void restore()
	{
		projectServiceMemento.getOriginator().restoreMemento(projectServiceMemento);
		
		for(Mailbox.MailboxMemento memento : mailboxMementos)
			memento.getOriginator().restoreMemento(memento);
	}
	
	/**
	 * Method to represent a snapshot as a string.
	 * 
	 * @return The snapshot as a string.
	 */
	@Override
	public String toString()
	{
		return "Before " + usecase.toString();
	}
}
