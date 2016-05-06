package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.IssuerUseCases.*;
import Model.BugReport.BugReportService;
import Model.BugReport.PerformanceMetrics.PerformanceMetricsService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class IssuerController extends UserController {

    private TagAssignmentService tagAssignmentService;
    private MailboxService mailboxService;

    public IssuerController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, PerformanceMetricsService performanceMetricsService, TagAssignmentService tagAssignmentService, MailboxService mailboxService, User currentUser) {
        super(ui, userService, projectService, bugReportService, performanceMetricsService, currentUser);
        setTagAssignmentService(tagAssignmentService);
        setMailboxService(mailboxService);
        initializeUseCasesIssuer();
    }

	private void initializeUseCasesIssuer() {
        useCases.add(new CreateBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new InspectBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new CreateComment(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new UpdateBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getCurrentUser()));
        useCases.add(new ShowNotifications(getUi(), getProjectService(), getBugReportService(), getMailboxService(), getCurrentUser()));
        useCases.add(new RegisterNotifications(getUi(), getProjectService(), getBugReportService(), getMailboxService(), getCurrentUser()));
        useCases.add(new UnregisterNotifications(getUi(), getProjectService(), getBugReportService(), getMailboxService(), getCurrentUser()));
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

    private void setMailboxService(MailboxService mailboxService)
    {
		this.mailboxService = mailboxService;
	}
}
