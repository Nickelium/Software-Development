package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.TheDate;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class UpdateProject extends UseCase {

    public UpdateProject(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
    }

    /**
     *
     * Method that lets an administrator update a project.
     *
     * 2. The system shows a list of all projects.
     * 3. The administrator selects a project.
     * 4. The system shows a form to update the project details: name,
     * description, starting date and budget estimate.
     * 5. The administrator modies the details as he sees t.
     * 6. The system updates the project.
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
        getUi().display("Select a project you want to update: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int index = getUi().readInt();
        Project project = projectList.get(index);

        // Step 4 + 5
        getUi().display("Please enter new values.");
        getUi().display("Name (current value: " + project.getName() + "): ");
        String name = getUi().readString();
        getUi().display("Description (current value: " + project.getDescription() + "): ");
        String description = getUi().readString();
        TheDate startingDate = null;

        getUi().display("Starting date (current value: " + project.getStartingDate() + "): ");
        String stringStartingDate = getUi().readString();
        startingDate = new TheDate(stringStartingDate);

        getUi().display("Budget estimate (current value: " + project.getBudget() + "): ");
        double budget = getUi().readDouble();

        // Step 6
        project.setName(name);
        project.setDescription(description);
        project.setStartingDate(startingDate);
        project.setBudget(budget);

        getUi().display("The project has been successfully updated.\n");
        getUi().display(project.toString());
    }
}
