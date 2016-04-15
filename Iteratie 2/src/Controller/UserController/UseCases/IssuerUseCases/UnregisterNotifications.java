package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Mail.MailboxService;
import Model.Mail.ObserverAspect;
import Model.Project.ProjectService;
import Model.User.User;

import java.util.List;


public class UnregisterNotifications extends IssuerUseCase{

	private MailboxService mailboxService;
	
    public UnregisterNotifications(IUI ui, ProjectService projectService, BugReportService bugReportService, MailboxService mailboxService, User currentUser) {
        super(ui, null, projectService, bugReportService,null, currentUser);
        this.mailboxService = mailboxService;
        changeSystem = true;
    }

    /**
     *
     * Lets an Issuer unregister a registration.
     *
     * 2. The system shows a list of all active registrations.
     * 3. The issuer selects a registration to unregister.
     * 4. The system unregister the given registration.
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

    	//Step 2
        getUi().display("The list of all active registrations:");

        List<ObserverAspect> registrations = mailboxService.getRegistrations(getCurrentUser());
        String stringRegistrations = Formatter.formatRegistrationList(registrations);
        getUi().display(stringRegistrations);
        
        //Step 3
        getUi().display("Choose one of the registration to unregister : ");
        int index = getUi().readInt();
        
        ObserverAspect registrationToUnregister = registrations.get(index);
        
        //Step 4
        mailboxService.unregister(getCurrentUser(), registrationToUnregister);

        getUi().display("Unregister completed!");
    }
    
    @Override
	public String toString()
	{
		return "Unregister for Notifications";
	}
}
