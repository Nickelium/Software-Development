package Controller.UserController.UseCases.DeveloperUseCases;

import Controller.Formatter;
import Controller.IUI;
import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.BugReport.DeveloperAssignmentService;
import Model.BugReport.TagAssignmentService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Programmer;
import Model.Roles.Role;
import Model.Roles.Tester;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Karina on 25.03.2016.
 */
public class AssignToProject extends DeveloperUseCase {

    public AssignToProject(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, TagAssignmentService tagAssignmentService, DeveloperAssignmentService developerAssignmentService, User currentUser) {
        super(ui, userService, projectService, bugReportService, tagAssignmentService, developerAssignmentService, currentUser);
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
     * @throws ModelException
     *          if something goes wrong during execution, give user the
     *          chance of retrying.
     *
     */
    @Override
    public void run() throws ModelException, IndexOutOfBoundsException {
        // Get projects with currentUser as Lead Developer.
        List<Project> developerProjectList = getProjectService().getProjectsOfLeadRole((Developer) getCurrentUser());

        // Check if there are any projects with currentUser as Lead Developer.
        if (developerProjectList.size() == 0) {

            // Step 2a
            throw new ModelException("You are not assigned as lead developer in any project. You are not allowed to assign a new Developer to any project.");
        } else {

            // Step 2
            getUi().display("Select the project that you want to assign a new developer to: ");

            String parsedDeveloperProjectList = Formatter.formatProjectList(developerProjectList);
            getUi().display(parsedDeveloperProjectList);

            // Step 3
            int projectIndex = getUi().readInt();
            Project project = developerProjectList.get(projectIndex);

            // Step 4
            getUi().display("Please select the a developer that you want to assign to the selected project: ");
            List<User> developerList = getUserService().getDevelopers();
            String parsedDevelopersList = Formatter.formatUserList(developerList);
            getUi().display(parsedDevelopersList);

            // Step 5
            int developerIndex = getUi().readInt();
            User developer = developerList.get(developerIndex);

            // Step 6
            getUi().display("Please select the role that you want to assign to the developer of the project: ");

            List<Class<? extends Role>> roles = Arrays.asList(Programmer.class, Tester.class);
            getUi().display(Formatter.formatProjectRoles(roles));

            // Step 7
            int selectedIndex = getUi().readInt();
            Class<? extends Role> selectedClass = roles.get(selectedIndex);

            // Step 8
            try {
                Role role = selectedClass.getDeclaredConstructor(Developer.class).newInstance(developer);
                project.addRole(role);
            } catch (NoSuchMethodException | InstantiationException |
                    IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }



            getUi().display("The new developer has been successfully assigned to a new role in the project.\n");

        }
    }
}
