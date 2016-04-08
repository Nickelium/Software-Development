package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.DeveloperUseCases.AssignToBugReport;
import Controller.UserController.UseCases.DeveloperUseCases.AssignToProject;
import Controller.UserController.UseCases.DeveloperUseCases.DeclareAchievedMilestone;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;
import Model.Mail.*;

/**
 * Created by Karina on 05.03.2016.
 */
public class DeveloperController extends IssuerController {

    private DeveloperAssignmentService developerAssignmentService;

    public DeveloperController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser, DeveloperAssignmentService developerAssignmentService, TagAssignmentService tagAssignmentService, MailboxService mailboxService) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        setDeveloperAssignmentService(developerAssignmentService);
        initializeUseCasesDeveloper();
    }

    private void initializeUseCasesDeveloper() {
        useCases.add(new FunctionWrap("Assign To Project", new AssignToProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Assign To Bug Report", new AssignToBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Declare Achieved Milestone", new DeclareAchievedMilestone(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser())));
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
