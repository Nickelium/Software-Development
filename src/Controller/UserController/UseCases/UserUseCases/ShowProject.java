package Controller.UserController.UseCases.UserUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class ShowProject extends UseCase {


    public ShowProject(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
    }

    //TODO documentatie
    public void run() throws ReportErrorToUserException,IndexOutOfBoundsException{
        // Step 2
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int index = getUi().readInt();
        Project project = projectList.get(index);

        // Step 4
        String projectDetails = Formatter.formatDetailedProject(project);
        getUi().display(projectDetails);
    }


}
