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
 * Class extending the use case class, representing a fork project use case.
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

        // Step 1a.1
        getUi().display("Select a project you want to fork: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 1a.2
        int index = getUi().readInt();
        Project project = projectList.get(index);
 
        // Step 1a.3 + 1a.4
        getUi().display("Please enter new values.");
        getUi().display("VersionID (has to be larger than the current value: " + project.getVersionID() + "): ");
        double versionID = getUi().readDouble();
        if(!project.isValidVersionID(versionID)) throw new ReportErrorToUserException("The version cannot be lower than or equal to the previous one!");
        
        getUi().display("Starting date (current value: " + project.getStartingDate() + "): ");
        String stringStartingDate = getUi().readString();
        TheDate startingDate = new TheDate(stringStartingDate);
        if (!project.isValidStartingDate(startingDate)) throw new ReportErrorToUserException("The date is before the creation date.");
        
        getUi().display("Budget estimate (current value: " + project.getBudget() + "): ");
        double budget = getUi().readDouble();
        if (!project.isValidBudget(budget)) throw new ReportErrorToUserException("The budget cannot be negative.");

        Project forkProject = getProjectService().forkProject(project);
        getProjectService().setProjectVersionID(forkProject, versionID);
        getProjectService().setProjectStartingDate(forkProject, startingDate);
        getProjectService().setProjectBudget(forkProject, budget);

        // Step 1a.5
        List<User> possibleLeadDevelopers = getUserService().getDevelopers();
        String parsedPossibleLeadDevelopers = Formatter.formatUserList(possibleLeadDevelopers);
        getUi().display("Choose a lead developer for this forked project: ");
        getUi().display(parsedPossibleLeadDevelopers);
        int index2 = getUi().readInt();
        Developer leadDev = (Developer) possibleLeadDevelopers.get(index2);
        Lead leadRole = new Lead(leadDev);

        getProjectService().setProjectLeadRole(forkProject, leadRole);

        getUi().display("The project has been successfully forked.\n");
        getUi().display(forkProject.toString());
    }
    
    @Override
	public String toString()
	{
		return "Fork Project";
	}
}
