package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.DeveloperUseCases.AssignToBugReport;
import Controller.UserController.UseCases.DeveloperUseCases.AssignToProject;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class DeveloperController extends IssuerController {

    private DeveloperAssignmentService developerAssignmentService;
    private TagAssignmentService tagAssignmentService;

    public DeveloperController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser, DeveloperAssignmentService developerAssignmentService, TagAssignmentService tagAssignmentService) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, currentUser);
        initializeUseCasesDeveloper();
        setDeveloperAssignmentService(developerAssignmentService);
    }

    private void initializeUseCasesDeveloper() {
        useCases.add(new FunctionWrap("Assign To Project", new AssignToProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Assign To Bug Report", new AssignToBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getDeveloperAssignmentService(), getCurrentUser())));
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
