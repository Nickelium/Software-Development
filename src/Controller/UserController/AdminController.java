package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.AdminUseCases.*;
import Model.BugReport.BugReportService;
import Model.Memento.Caretaker;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class AdminController extends UserController {
	
	private Caretaker caretaker;

    public AdminController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, Caretaker caretaker, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        this.caretaker = caretaker;
        initializeUseCasesAdmin();
    }

    private void initializeUseCasesAdmin() {
        useCases.add(new CreateProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new ForkProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new UpdateProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new DeleteProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new CreateSubSystem(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new Undo(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser(), this.caretaker));

    }

}
