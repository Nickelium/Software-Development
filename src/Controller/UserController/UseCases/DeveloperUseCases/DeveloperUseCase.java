package Controller.UserController.UseCases.DeveloperUseCases;

import Controller.IUI;
import Controller.UserController.UseCases.IssuerUseCases.IssuerUseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Abstract class extending the Issuer use case class, representing a developer use case object.
 */
public abstract class DeveloperUseCase extends IssuerUseCase {

    private DeveloperAssignmentService developerAssignmentService;

    public DeveloperUseCase(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService,DeveloperAssignmentService developerAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, currentUser);
        setDeveloperAssignmentService(developerAssignmentService);
        changeSystem = true;
    }

    public abstract void run() throws ReportErrorToUserException, IndexOutOfBoundsException;

    public DeveloperAssignmentService getDeveloperAssignmentService() {
        return developerAssignmentService;
    }

    public void setDeveloperAssignmentService(DeveloperAssignmentService developerAssignmentService) {
        this.developerAssignmentService = developerAssignmentService;
    }
}
