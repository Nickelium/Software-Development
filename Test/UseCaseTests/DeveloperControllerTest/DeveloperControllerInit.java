package UseCaseTests.DeveloperControllerTest;

import Controller.Initializer.AssignmentInitializer;
import Controller.Initializer.IInitializer;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.PerformanceMetrics.PerformanceMetricsService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Project.ProjectService;
import Model.User.UserService;
import org.junit.Before;

/**
 * Created by Karina on 10.03.2016.
 */
public class DeveloperControllerInit {

    public enum DeveloperUseCase {
        ASSIGN_TO_PROJECT(10), ASSIGN_TO_BUGREPORT(11), DECLARE_ACHIEVED_MILESTONE(12),
        PROPOSE_PATCH(13), PROPOSE_TEST(14), LOGOUT(15);

        public final int value;

        DeveloperUseCase(int value) {
            this.value = value;
        }
    }

    protected IInitializer initializer;
    protected ProjectService projectService;
    protected BugReportService bugReportService;
    protected UserService userService;
    protected DeveloperAssignmentService developerAssignmentService;
    protected TagAssignmentService tagAssignmentService;
    protected PerformanceMetricsService performanceMetricsService;
    protected MailboxService mailboxService;

    @Before
    public void initialization() throws ReportErrorToUserException {
        this.initializer = new AssignmentInitializer();
        this.projectService = initializer.getProjectService();
        this.bugReportService = initializer.getBugReportService();
        this.userService = initializer.getUserService();
        this.developerAssignmentService = initializer.getDeveloperAssignmentService();
        this.tagAssignmentService = initializer.getTagAssignmentService();
        this.performanceMetricsService = initializer.getPerformanceMetricsService();
        this.mailboxService = initializer.getMailboxService();
        this.userService.createDeveloper("Test", "T", "Testing", "test1");
        this.userService.createDeveloper("Test2", "T", "Testing", "test2");
    }

}
