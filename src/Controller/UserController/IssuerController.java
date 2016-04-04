package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.IssuerUseCases.CreateBugReport;
import Controller.UserController.UseCases.IssuerUseCases.CreateComment;
import Controller.UserController.UseCases.IssuerUseCases.InspectBugReport;
import Controller.UserController.UseCases.IssuerUseCases.RegisterNotifications;
import Controller.UserController.UseCases.IssuerUseCases.ShowNotifications;
import Controller.UserController.UseCases.IssuerUseCases.UnregisterNotifications;
import Controller.UserController.UseCases.IssuerUseCases.UpdateBugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;
import Model.Mail.*;

/**
 * Created by Karina on 05.03.2016.
 */
public class IssuerController extends UserController {

    private TagAssignmentService tagAssignmentService;
    private MailboxService mailboxService;

    public IssuerController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, MailboxService mailboxService , User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        setTagAssignmentService(tagAssignmentService);
        setMailboxService(mailboxService);
        initializeUseCasesIssuer();
    }

	private void initializeUseCasesIssuer() {
        useCases.add(new FunctionWrap("Create Bug Report", new CreateBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Inspect Bug Report", new InspectBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Create Comment", new CreateComment(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Update Bug Report", new UpdateBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Show Notifications", new ShowNotifications(getUi(), getProjectService(), getBugReportService(), getMailboxService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Register for Notifications", new RegisterNotifications(getUi(), getProjectService(), getBugReportService(), getMailboxService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Unregister for Notifications", new UnregisterNotifications(getUi(), getProjectService(), getBugReportService(), getMailboxService(), getCurrentUser())));
    }

    public TagAssignmentService getTagAssignmentService() {
        return tagAssignmentService;
    }

    public void setTagAssignmentService(TagAssignmentService tagAssignmentService) {
        this.tagAssignmentService = tagAssignmentService;
    }
    
    public MailboxService getMailboxService()
    {
    	return mailboxService;
    }
    public void setMailboxService(MailboxService mailboxService)
    {
		this.mailboxService = mailboxService;
	}
}
