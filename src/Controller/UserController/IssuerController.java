package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.IssuerUseCases.CreateBugReport;
import Controller.UserController.UseCases.IssuerUseCases.CreateComment;
import Controller.UserController.UseCases.IssuerUseCases.InspectBugReport;
import Controller.UserController.UseCases.IssuerUseCases.UpdateBugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.TagAssignmentService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class IssuerController extends UserController {

    private TagAssignmentService tagAssignmentService;

    public IssuerController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        setTagAssignmentService(tagAssignmentService);
        initializeUseCasesIssuer();
    }

    private void initializeUseCasesIssuer() {
        useCases.add(new FunctionWrap("Create Bug Report", new CreateBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Inspect Bug Report", new InspectBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Create Comment", new CreateComment(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Update Bug Report", new UpdateBugReport(getUi(), getUserService(), getProjectService(), getBugReportService(), getTagAssignmentService(), getCurrentUser())));
    }

    public TagAssignmentService getTagAssignmentService() {
        return tagAssignmentService;
    }

    public void setTagAssignmentService(TagAssignmentService tagAssignmentService) {
        this.tagAssignmentService = tagAssignmentService;
    }
}
