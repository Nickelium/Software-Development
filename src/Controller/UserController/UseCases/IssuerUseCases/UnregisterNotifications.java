package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Mail.MailboxService;
import Model.Mail.Notification;
import Model.Mail.ObserverAspect;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.Issuer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;


public class UnregisterNotifications extends IssuerUseCase{

	private MailboxService mailboxService;
	
    public UnregisterNotifications(IUI ui, ProjectService projectService, BugReportService bugReportService, MailboxService mailboxService, User currentUser) {
        super(ui, null, projectService, bugReportService,null, currentUser);
        this.mailboxService = mailboxService;
    }

    /**
     *
     * Lets an Issuer create a bug report.
     *
     * 2. The system shows a list of projects.
     * 3. The issuer selects a project.
     * 4. The system shows a list of subsystems of the selected project.
     * 5. The issuer selects a subsystem.
     * 6. The system shows the bug report creation form.
     * 7. The issuer enters the bug report details: title and description.
     * 8. The system shows a list of possible dependencies of this bug report.
     *    These are the bug reports of the same project.
     * 9. The issuer selects the dependencies.
     * 10. The system creates the bug report.
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

     
        getUi().display("The list of all active registrations:");

        List<ObserverAspect> registrations = mailboxService.getRegistrations(getCurrentUser());
        String stringRegistrations = Formatter.formatRegistrationList(registrations);
        getUi().display(stringRegistrations);
        
        getUi().display("Choose one of the registration to unregister : ");
        int index = getUi().readInt();
        
        ObserverAspect registrationToUnregister = registrations.get(index);
        mailboxService.unregister(getCurrentUser(), registrationToUnregister);
        
        getUi().display("Unregister completed !");
    }
}
