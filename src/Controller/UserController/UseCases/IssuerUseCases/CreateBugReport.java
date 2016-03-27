package Controller.UserController.UseCases.IssuerUseCases;

import Controller.Formatter;
import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.Issuer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 24.03.2016.
 */
public class CreateBugReport extends IssuerUseCase{

    public CreateBugReport(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService,null, currentUser);
    }

    /**
     *
     * Lets an Issuer create a bug report.
     *
     * 2. The system shows a list of projects.
     * 3. The issuer selects a project.
     * 4. The system shows a list of subsystems of the selected project.
     * 5. The issuer selects a subsystem.
     * 6. The system shows the bug report creation form.
     * 7. The issuer enters the bug report details: title and description.
     * 8. The system shows a list of possible dependencies of this bug report.
     *    These are the bug reports of the same project.
     * 9. The issuer selects the dependencies.
     * 10. The system creates the bug report.
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     */
    @Override
    public void run() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Select a project:");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Formatter.formatProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int indexP = getUi().readInt();
        Project project = projectList.get(indexP);

        // Step 4
        getUi().display("Select a subsystem:");
        List<SubSystem> subSystemList = project.getAllSubSystems();
        String subSystemsOfProject = Formatter.formatSubSystemList(subSystemList);
        getUi().display(subSystemsOfProject);

        // Step 5
        int indexS = getUi().readInt();
        SubSystem subSystem = subSystemList.get(indexS);

        // Step 6 + 7
        getUi().display("\n");
        getUi().display("Please enter the bug report information.");
        getUi().display("Title:");
        String title = getUi().readString();
        getUi().display("Description:");
        String description = getUi().readString();

        Issuer issuer = (Issuer) getCurrentUser();

        // Step 10
        BugReport bugReport = getBugReportService().createBugReport(title, description, issuer, subSystem);

        // Step 8
        List<BugReport> possibleDependencies = getBugReportService().getBugReportsForProject(project);
        String possibleDependenciesStr = Formatter.formatBugReportList(possibleDependencies);
        while (true) {

            // Step 9
            getUi().display("\n");
            getUi().display("Select a possible dependency (enter -1 to exit): ");
            getUi().display(possibleDependenciesStr);
            int indexDep = getUi().readInt();
            if (indexDep == -1) break;

            BugReport newDependency = possibleDependencies.get(indexDep);
            bugReport.addDependency(newDependency);
            getUi().display("Dependency is successfully added!");
            getUi().display(newDependency.toString());
        }

    }
}