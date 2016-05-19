package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Mail.MailboxService;
import Model.Mail.Notification;
import Model.Project.ProjectService;
import Model.User.User;

import java.util.List;

/**
 * Class extending the issuer use case class, representing a show-notifications use case.
 */
public class ShowNotifications extends IssuerUseCase{

	private MailboxService mailboxService;
	
    public ShowNotifications(IUI ui, ProjectService projectService, BugReportService bugReportService, MailboxService mailboxService, User currentUser) {
        super(ui, null, projectService, bugReportService,null, currentUser);
        this.mailboxService = mailboxService;
        changeSystem = false;
    }

    /**
     *
     * Lets an Issuer look to his notifications.
     *
     * 2. The system asks for how many notifications to display
     * 3. The issuer indicate this number
     * 4. The system displays the specified amount of notifications
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

     
    	//Step 2
        getUi().display("Indicate how many notifications to display:");
        //Step 3
        int number = getUi().readInt();

        //Step 4
        List<Notification> notifications = mailboxService.getNotifications(getCurrentUser(), number);
        String stringNotifications = Formatter.formatNotificationList(notifications);
        
        getUi().display(stringNotifications);
    
    }
    
    @Override
	public String toString()
	{
		return "Show Notifications";
	}
}
