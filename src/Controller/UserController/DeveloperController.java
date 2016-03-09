package Controller.UserController;

import Controller.Parser;
import Controller.UI;
import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.TheDate;
import Model.Roles.Role;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public class DeveloperController extends IssuerController {

    public DeveloperController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesDeveloper();
    }

    private void initializeUseCasesDeveloper() {
        try {
            useCases.add(new FunctionWrap("Assign To Project", DeveloperController.class.getMethod("assignToProject")));
            useCases.add(new FunctionWrap("Assign To Bug Report", DeveloperController.class.getMethod("assignToBugReport")));
            useCases.add(new FunctionWrap("Update Bug Report", DeveloperController.class.getMethod("updateBugReport")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignToProject(){
        try {
            // Get projects with currentUser as Lead Developer.
            List<Project> developerProjectList = getProjectService().getProjectsOfLeadRole((Developer) getCurrentUser());

            // Check if there are any projects with currentUser as Lead Developer.
            if(developerProjectList.size() == 0){
                // Use case ends here
                getUi().display("You are not assigned as lead developer in any project. You are not allowed to assign a new Developer to any project.");
            }

            else {
                // Developer has projects with Lead Role.
                getUi().display("Select the project that you want to assign a new developer to: ");

                String parsedDeveloperProjectList = Parser.parseProjectList(developerProjectList);
                getUi().display(parsedDeveloperProjectList);

                int projectIndex = getUi().readInt();
                Project project = developerProjectList.get(projectIndex);

                getUi().display("Please select the a developer that you want to assign to the selected project: ");
                List<User> developerList = getUserService().getDevelopers();
                String parsedDevelopersList = Parser.parseUserList(developerList);
                getUi().display(parsedDevelopersList);

                int developerIndex = getUi().readInt();
                User developer = developerList.get(developerIndex);

                getUi().display("Please select the role that you want to assign to the developer of the project: ");
                //TODO stap 6+7+8
                





                getUi().display("The new developer has been successfully assigned to a new role in the project.\n");

            }

        } catch (ModelException | IndexOutOfBoundsException e) {
            getUi().display(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) assignToProject();
        }
    }

    public void assignToBugReport() {
        try {
            // Use Case Select Bug Report
            BugReport bugReport = selectBugReport();


        }

        catch (ModelException | IndexOutOfBoundsException e) {
            getUi().display(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) assignToBugReport();
        }
    }

    public void updateBugReport() {

    }
}
