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
        changeSystem = false;
    }

    /**
     * Lets a user of any type view a project.
     *
     * 2. The system shows a list of all projects.
     * 3. The user selects a project.
     * 4. The system shows a detailed overview of the selected project and all
     *    its subsystems.
     *
     * @throws ReportErrorToUserException is thrown in case that the method encounters invalid input.
     * @throws IndexOutOfBoundsException is thrown when a user puts an incorrect option index.
     */
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
        getUi().display(projectDetails + "\n");
    }
    
    @Override
	public String toString()
	{
		return "Show Project";
	}
}
