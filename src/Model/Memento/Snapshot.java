package Model.Memento;

import java.util.ArrayList;
import java.util.List;

import Controller.UserController.UseCases.UseCase;
import Model.Mail.Mailbox;
import Model.Mail.MailboxMemento;
import Model.Mail.MailboxService;
import Model.Project.Project;
import Model.Project.ProjectMemento;
import Model.Project.ProjectService;
import Model.Project.ProjectServiceMemento;
import Model.User.UserService;

public class Snapshot
{
	private UseCase usecase;
	
	private ProjectServiceMemento projectServiceMemento;
	private List<MailboxMemento> mailboxMementos = new ArrayList<>();
	
	public Snapshot(UseCase usecase, ProjectService projectService, MailboxService mailboxService)
	{
		projectServiceMemento = projectService.createMemento();
		for(Mailbox mailbox : mailboxService.getAllMailboxes())
			mailboxMementos.add(mailbox.createMemento());
		this.usecase = usecase;
	}
	
	public void restore()
	{
		projectServiceMemento.getOriginator().restoreMemento(projectServiceMemento);
		
		for(MailboxMemento memento : mailboxMementos)
			memento.getOriginator().restoreMemento(memento);
	}
	
	@Override
	public String toString()
	{
		return "Before " + usecase.toString();
	}
}
