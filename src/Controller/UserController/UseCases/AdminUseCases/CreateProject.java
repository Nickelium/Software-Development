package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class CreateProject extends UseCase {

    public CreateProject(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
    }

    /**
     *
     * Lets an administrator create a new project.
     *
     * 2. The system shows a form to enter the project details: name,
     * description, starting date and budget estimate.
     * 3. The administrator enters all the project details.
     * 4. The system shows a list of possible lead developers.
     * 5. The administrator selects a lead developer.
     * 6. The system creates the project and shows an overview.
     *
     * @throws ModelException
     *          in case that the method encounters invalid input.
     * @throws IndexOutOfBoundsException
     * 			thrown when a user puts an incorrect option index.
     *
     */
    @Override
    public void run() throws ModelException,IndexOutOfBoundsException {
        // Step 2 + 3
        getUi().display("Please enter the project information.");
        getUi().display("Name: ");
        String name = getUi().readString();
        getUi().display("Description (close with .): ");
        String description = getUi().readMultiline();

        TheDate startingDate = null;

        getUi().display("Starting date (dd/MM/yyyy): ");
        String stringStartingDate = getUi().readString();
        startingDate = new TheDate(stringStartingDate);

        getUi().display("Budget estimate: ");
        double budget = getUi().readDouble();

        // Step 4
        List<User> possibleLeadDevelopers = getUserService().getDevelopers();
        String parsedPossibleLeadDevelopers = Formatter.formatUserList(possibleLeadDevelopers);

        // Step 5
        getUi().display("Choose a lead developer for this project: ");
        getUi().display(parsedPossibleLeadDevelopers);
        int index = getUi().readInt();
        Developer leadDev = (Developer) possibleLeadDevelopers.get(index);
        Lead leadRole = new Lead(leadDev);

        // Step 6
        Project project = getProjectService().createProject(name, description, startingDate, budget, leadRole);
        getUi().display("Your project has been successfully created!\n");
        getUi().display(project.toString());

    }
}
