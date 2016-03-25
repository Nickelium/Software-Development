package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.AdminUseCases.*;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class AdminController extends UserController {

    public AdminController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesAdmin();
    }

    private void initializeUseCasesAdmin() {
        useCases.add(new FunctionWrap("Create Project", new CreateProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Fork Project", new ForkProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Update Project", new UpdateProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Delete Project", new DeleteProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Create Subsystem", new CreateSubSystem(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
    }

}
