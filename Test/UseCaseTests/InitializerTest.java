package UseCaseTests;

import Controller.InitializerPkg.AssignmentInitializer;
import Controller.InitializerPkg.IInitializer;
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
public class InitializerTest {

    public enum UseCase {
        SHOW_PROJECT(0), SHOW_PERFORMANCE_METRICS(2);

        public final int value;

        UseCase(int value) {
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
    }

}
