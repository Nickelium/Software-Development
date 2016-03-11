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

    /**
     *
     * Lets a Developer assign another developer to a project of which the current
     * Developer is lead developer.
     *
     * 2. The system shows a list of the projects in which the logged in user is
     *    assigned as lead developer.
     * 3. The lead developer selects one of his projects.
     * 4. The system shows a list of other developers to assign.
     * 5. The lead developer selects one of these other developers.
     * 6. The system shows a list of possible (i.e. not yet assigned) roles for the
     *    selected developer.
     * 7. The lead developer selects a role.
     * 8. The systems assigns the selected role to the selected developer.
     *
     * @throws Exception
     *          if something goes wrong during execution, give user the
     *          chance of retrying.
     *
     */
    public void assignToProject() throws Exception {

        // Get projects with currentUser as Lead Developer.
        List<Project> developerProjectList = getProjectService().getProjectsOfLeadRole((Developer) getCurrentUser());

        // Check if there are any projects with currentUser as Lead Developer.
        if (developerProjectList.size() == 0) {

            // Step 2a
            throw new ModelException("You are not assigned as lead developer in any project. You are not allowed to assign a new Developer to any project.");
        } else {

            // Step 2
            getUi().display("Select the project that you want to assign a new developer to: ");

            String parsedDeveloperProjectList = Parser.parseProjectList(developerProjectList);
            getUi().display(parsedDeveloperProjectList);

            // Step 3
            int projectIndex = getUi().readInt();
            Project project = developerProjectList.get(projectIndex);

            // Step 4
            getUi().display("Please select the a developer that you want to assign to the selected project: ");
            List<User> developerList = getUserService().getDevelopers();
            String parsedDevelopersList = Parser.parseUserList(developerList);
            getUi().display(parsedDevelopersList);

            // Step 5
            int developerIndex = getUi().readInt();
            User developer = developerList.get(developerIndex);

            // Step 6
            getUi().display("Please select the role that you want to assign to the developer of the project: ");

            List<Class<? extends Role>> roles = Arrays.asList(Programmer.class, Tester.class);
            getUi().display(Parser.parseProjectRoles(roles));

            // Step 7
            int selectedIndex = getUi().readInt();
            Class<? extends Role> selectedClass = roles.get(selectedIndex);

            // Step 8
            Role role = selectedClass.getDeclaredConstructor(Developer.class).newInstance(developer);

            project.addRole(role);

            getUi().display("The new developer has been successfully assigned to a new role in the project.\n");

        }
    }

    /**
     *
     * Lets a Developer assign one or more developers to a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a list of developers that are involved in the project.
     * 4. The logged in developer selects one or more of the developers to assign
     *    to the selected bug report on top of those already assigned.
     * 5. The systems assigns the selected developers to the selected bug report.
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *
     */
    public void assignToBugReport() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Please select the bug report that you want to assign a new developer to: ");
        BugReport bugReport = selectBugReport();
        if (!getDeveloperAssignmentService().canUserAssignDevelopers(getCurrentUser(), bugReport)) {
            throw new ModelException("You are not allowed to assign developers to this bugreport.");
        }

        // Step 3
        getUi().display("Please select the developer(s) that you want to assign to the chosen bug report. Type -1 to continue");
        List<Developer> developerList = bugReport.getAssignees();
        String parsedList = Parser.parseDeveloperList(developerList);
        getUi().display(parsedList);
        // Step 4
        int selectedValue = getUi().readInt();

        if (selectedValue == -1) {
            getUi().display("Assignment of developer canceled.");
        } else {
            //Step 5
            Developer developer = developerList.get(selectedValue);
            developerAssignmentService.assignDeveloperToBugReport(getCurrentUser(), developer, bugReport);
            getUi().display("The developer has successfully been assigned to the bugreport.");
        }
    }

    /**
     *
     * Lets a Developer update the tag of a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The developer suggests a new tag for the bug report.
     * 4. The system gives the selected bug report the new tag.
     *
     * @throws Exception
     *          if something goes wrong during execution, give user the
     *          chance of retrying.
     *
     */
    public void updateBugReport() throws Exception {

        // Step 2
        getUi().display("Please select the bug report that you want to update: ");
        BugReport bugReport = selectBugReport();

        // Step 3
        getUi().display("Please specify the new tag for the bug report: ");
        String input = getUi().readString();
        Class<?> tag;
        try {
            tag = Class.forName("Model.Tags.TagTypes." + input);
            if (input == "-1") return;
        } catch (ClassNotFoundException e) {
            throw new ModelException("The given tag does not exist!");
        }

        // Step 4
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
