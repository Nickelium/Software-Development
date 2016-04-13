package Controller.UserController.UseCases.AdminUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ReportErrorToUserException;
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
public class ForkProject extends UseCase {

    public ForkProject(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        changeSystem = true;
    }

    /**
     *
     * Method that lets an administrator create a next version of an
     * existing project. Forked projects are independent and start without
     * any bug reports.
     *
     * 2. The administrator selects an existing project.
     * 3. The system shows a form to enter the missing project details:
     * version number, starting date and budget estimate.
     * 4. The administrator enters all the missing project details.
     * 5. The use case returns to step 4 of the normal flow
     *
     * @throws ReportErrorToUserException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     * 			thrown when a user puts an incorrect option index.
     *
     */
    @Override
    public void run() throws ReportErrorToUserException,IndexOutOfBoundsException {

        //TODO Milestone op M0 zetten!

        // Step 1a.1
        getUi().display("Select a project you want to fork: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 1a.2
        int index = getUi().readInt();
        Project project = projectList.get(index);
        Project forkProject = getProjectService().forkProject(project);

        // Step 1a.3 + 1a.4
        getUi().display("Please enter new values.");
        getUi().display("VersionID (current value: " + forkProject.getVersionID() + "): ");
        double versionID = getUi().readDouble();

        getUi().display("Starting date (current value: " + forkProject.getStartingDate() + "): ");
        String stringStartingDate = getUi().readString();
        TheDate startingDate = new TheDate(stringStartingDate);

        getUi().display("Budget estimate (current value: " + project.getBudget() + "): ");
        double budget = getUi().readDouble();

        forkProject.setVersionID(versionID);
        forkProject.setStartingDate(startingDate);
        forkProject.setBudget(budget);

        // Step 1a.5
        List<User> possibleLeadDevelopers = getUserService().getDevelopers();
        String parsedPossibleLeadDevelopers = Formatter.formatUserList(possibleLeadDevelopers);
        getUi().display("Choose a lead developer for this forked project: ");
        getUi().display(parsedPossibleLeadDevelopers);
        int index2 = getUi().readInt();
        Developer leadDev = (Developer) possibleLeadDevelopers.get(index2);
        Lead leadRole = new Lead(leadDev);
        forkProject.setLeadRole(leadRole);

        getUi().display("The project has been successfully forked.\n");
        getUi().display(forkProject.toString());
    }
    
    @Override
	public String toString()
	{
		return "Fork Project";
	}
}
