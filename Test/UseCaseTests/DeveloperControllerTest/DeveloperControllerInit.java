package UseCaseTests.DeveloperControllerTest;

import Controller.IInitializer;
import Controller.Initializer;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Project.ProjectService;
import Model.User.UserService;
import org.junit.Before;

/**
 * Created by Karina on 10.03.2016.
 */
public class DeveloperControllerInit {

    protected IInitializer initializer;
    protected ProjectService projectService;
    protected BugReportService bugReportService;
    protected UserService userService;
    protected DeveloperAssignmentService developerAssignmentService;
    protected TagAssignmentService tagAssignmentService;
    protected MailboxService mailboxService;

    @Before
    public void initialization() throws ReportErrorToUserException {
        this.initializer = new Initializer();
        this.projectService = initializer.getProjectService();
        this.bugReportService = initializer.getBugReportService();
        this.userService = initializer.getUserService();
        this.developerAssignmentService = initializer.getDeveloperAssignmentService();
        this.tagAssignmentService = initializer.getTagAssignmentService();
        this.mailboxService = initializer.getMailboxService();
        this.userService.createDeveloper("Test", "T", "Testing", "test1");
        this.userService.createDeveloper("Test2", "T", "Testing", "test2");
    }

}