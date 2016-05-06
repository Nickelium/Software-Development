package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.DeveloperUseCases.*;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.PerformanceMetrics.PerformanceMetricsService;
import Model.BugReport.TagAssignmentService;
import Model.Mail.MailboxService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class DeveloperController extends IssuerController {

    private DeveloperAssignmentService developerAssignmentService;

    public DeveloperController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, PerformanceMetricsService performanceMetricsService, User currentUser, DeveloperAssignmentService developerAssignmentService, TagAssignmentService tagAssignmentService, MailboxService mailboxService) {
        super(ui, userService, projectService, bugReportService, performanceMetricsService, tagAssignmentService, mailboxService, currentUser);
        setDeveloperAssignmentService(developerAssignmentService);
        initializeUseCasesDeveloper();
    }

    private void initializeUseCasesDeveloper() {
        useCases.add(new AssignToProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser()));
        useCases.add(new AssignToBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser()));
        useCases.add(new DeclareAchievedMilestone(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser()));
        useCases.add(new ProposePatch(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser()));
        useCases.add(new ProposeTest(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser()));
    }

    //region Getters & Setters

    public DeveloperAssignmentService getDeveloperAssignmentService() {
        return developerAssignmentService;
    }

    public void setDeveloperAssignmentService(DeveloperAssignmentService developerAssignmentService) {
        this.developerAssignmentService = developerAssignmentService;
    }

    //endregion
}
