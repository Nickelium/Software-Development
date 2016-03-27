package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class CreateSubSystem extends UseCase {

    public CreateSubSystem(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
    }

    /**
     *
     * Lets an administrator create a subsystem.
     *
     * 2. The system shows a list of all projects.
     * 3. The user selects a project.
     * 4. The system shows a detailed overview of the selected project and all
     * its subsystems.
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
        getUi().display("List of all projects:");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        getUi().display("List of all subsystems:");
        List<SubSystem> subSystemList = getProjectService().getAllSubSystems();
        String parsedSubSystemList = Formatter.formatSubSystemList(subSystemList);
        getUi().display(parsedSubSystemList);

        // Step 3
        getUi().display("Project or subsystem (P/S) : ");
        String input = getUi().readString();

        int index;
        if (input.equalsIgnoreCase("p")) {
            getUi().display("Choose a project: ");
            index = getUi().readInt();
            Project project = projectList.get(index);

            // Step 4 + 5
            getUi().display("Please enter the subsystem information.");
            getUi().display("Name:");
            String name = getUi().readString();
            getUi().display("Description:");
            String description = getUi().readString();

            // Step 6
            getProjectService().createSubsystem(name, description, project);

        } else if (input.equalsIgnoreCase("s")) {
            getUi().display("Choose a subsystem: ");
            index = getUi().readInt();
            SubSystem subSystem = subSystemList.get(index);

            // Step 4 + 5
            getUi().display("Please enter the subsystem information.");
            getUi().display("Name:");
            String name = getUi().readString();
            getUi().display("Description:");
            String description = getUi().readString();

            // Step 6
            getProjectService().createSubsystem(name, description, subSystem);

        } else {
            throw new ReportErrorToUserException("This is an invalid input");
        }

        getUi().display("The subsystem has been successfully created.\n");
    }
}
