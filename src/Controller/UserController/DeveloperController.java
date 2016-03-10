package Controller.UserController;

import Controller.IUI;
import Controller.Parser;
import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Programmer;
import Model.Roles.Role;
import Model.Roles.Tester;
import Model.Tags.Tag;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public class DeveloperController extends IssuerController {

    private DeveloperAssignmentService developerAssignmentService;
    private TagAssignmentService tagAssignmentService;

    public DeveloperController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser, DeveloperAssignmentService developerAssignmentService, TagAssignmentService tagAssignmentService) {
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesDeveloper();
        setDeveloperAssignmentService(developerAssignmentService);
        setTagAssignmentService(tagAssignmentService);
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

    public void assignToProject() throws Exception {
        // Get projects with currentUser as Lead Developer.
        List<Project> developerProjectList = getProjectService().getProjectsOfLeadRole((Developer) getCurrentUser());

        // Check if there are any projects with currentUser as Lead Developer.
        if (developerProjectList.size() == 0) {
            // Use case ends here
            getUi().display("You are not assigned as lead developer in any project. You are not allowed to assign a new Developer to any project.");
        } else {
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

            List<Class<? extends Role>> roles = Arrays.asList(Programmer.class, Tester.class);
            getUi().display(Parser.parseProjectRoles(roles));

            int selectedIndex = getUi().readInt();
            Class<? extends Role> selectedClass = roles.get(selectedIndex);

            Role role = selectedClass.getDeclaredConstructor(Developer.class).newInstance(developer);

            project.addRole(role);

            getUi().display("The new developer has been successfully assigned to a new role in the project.\n");

        }
    }

    public void assignToBugReport() throws ModelException, IndexOutOfBoundsException {
        // Use Case Select Bug Report
        getUi().display("Please select the bug report that you want to assign a new developer to: ");
        BugReport bugReport = selectBugReport();
        while (true) {
            bugReport = selectBugReport();
            if (getDeveloperAssignmentService().canUserAssignDevelopers(getCurrentUser(), bugReport)) {
                break;
            }
            getUi().errorDisplay("You don't have the permission to assing developers to this BugReport.");
        }
        // Show developers that are involved in the project.
        getUi().display("Please select the developer(s) that you want to assign to the chosen bug report. Type -1 to continue");
        List<Developer> developerList = bugReport.getAssignees();
        String parsedList = Parser.parseDeveloperList(developerList);
        getUi().display(parsedList);

        while (true) {
            if (getUi().readInt() == -1) {
                getUi().display("Assignment stopped by user. Continuing...");
                break;
            } else if (developerList.size() == 0) {
                getUi().display("No more users are available to be assigned. Continuing...");
                break;
            } else {
                int developerIndex = getUi().readInt();
                getDeveloperAssignmentService().assignDeveloperToBugReport(getCurrentUser(), developerList.get(developerIndex), bugReport);
                developerList.remove(developerIndex);
            }
        }
    }

    public void updateBugReport() throws Exception {
        // Use Case Select Bug Report
        getUi().display("Please select the bug report that you want to update: ");
        BugReport bugReport = selectBugReport();

        getUi().display("Please specify the new tag for the bug report: ");
        String input = getUi().readString();
        Class<?> tag;
        try {
            tag = Class.forName("Model.Tags.TagTypes." + input);
            if (input == "-1") return;
        } catch (ClassNotFoundException e) {
            throw new ModelException("The given tag does not exist!");
        }

        Tag newTag = (Tag) tag.newInstance();
        getTagAssignmentService().assignTag(getCurrentUser(), bugReport, newTag);

        getUi().display("The tag has successfully been changed.");
    }

    //region Getters & Setters

    public DeveloperAssignmentService getDeveloperAssignmentService() {
        return developerAssignmentService;
    }

    public void setDeveloperAssignmentService(DeveloperAssignmentService developerAssignmentService) {
        this.developerAssignmentService = developerAssignmentService;
    }

    public TagAssignmentService getTagAssignmentService() {
        return tagAssignmentService;
    }

    public void setTagAssignmentService(TagAssignmentService tagAssignmentService) {
        this.tagAssignmentService = tagAssignmentService;
    }

    //endregion
}
