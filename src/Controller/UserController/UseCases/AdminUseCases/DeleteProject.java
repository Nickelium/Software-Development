package Controller.UserController.UseCases.AdminUseCases;

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
 * Class extending the use case class, representing a delete project use case.
 */
public class DeleteProject extends UseCase {

    public DeleteProject(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        changeSystem = true;
    }

    /**
     *
     * Method that lets an administrator delete a project.
     *
     * 2. The system shows a list of all projects.
     * 3. The administrator selects a project.
     * 4. The system deletes a project and recursively all subsystems that are
     * part of the project. All bug reports fore those subsystem are also
     * removed from BugTrap.
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    @Override
    public void run() throws ReportErrorToUserException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Select a project you want to delete: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int index = getUi().readInt();
        Project project = projectList.get(index);

        // Step 4
        getProjectService().deleteProject(project);
        getUi().display("The project has been successfully deleted.\n");
    }
    
    @Override
	public String toString()
	{
		return "Delete Project";
	}
}
