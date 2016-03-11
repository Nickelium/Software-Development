package Controller.UserController;

import Controller.IUI;
import Controller.Parser;
import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public class AdminController extends UserController {

    public AdminController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesAdmin();
    }

    private void initializeUseCasesAdmin() {
        try {
            useCases.add(new FunctionWrap("Create Project", AdminController.class.getMethod("createProject")));
            useCases.add(new FunctionWrap("Fork Project", AdminController.class.getMethod("forkProject")));
            useCases.add(new FunctionWrap("Update Project", AdminController.class.getMethod("updateProject")));
            useCases.add(new FunctionWrap("Delete Project", AdminController.class.getMethod("deleteProject")));
            useCases.add(new FunctionWrap("Create Subsystem", AdminController.class.getMethod("createSubSystem")));

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void createProject() throws ModelException, IndexOutOfBoundsException {

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
        String parsedPossibleLeadDevelopers = Parser.parseUserList(possibleLeadDevelopers);

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
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     * 			thrown when a user puts an incorrect option index.
     *
     */
    public void forkProject() throws ModelException, IndexOutOfBoundsException {

        // Step 1a.1
        getUi().display("Select a project you want to fork: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
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
        String parsedPossibleLeadDevelopers = Parser.parseUserList(possibleLeadDevelopers);
        getUi().display("Choose a lead developer for this forked project: ");
        getUi().display(parsedPossibleLeadDevelopers);
        int index2 = getUi().readInt();
        Developer leadDev = (Developer) possibleLeadDevelopers.get(index2);
        Lead leadRole = new Lead(leadDev);
        forkProject.setLeadRole(leadRole);

        getUi().display("The project has been successfully forked.\n");
        getUi().display(forkProject.toString());
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
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    public void updateProject() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Select a project you want to update: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
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

    /**
     *
     * Method that lets an administrator delete a project.
     *
     * 2. The system shows a list of all projects.
     * 3. The administrator selects a project.
     * 4. The system deletes a project and recursively all subsystems that are
     * part of the project. All bug reports fore those subsystem are also
     * removed from BugTrap.
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    public void deleteProject() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Select a project you want to delete: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int index = getUi().readInt();
        Project project = projectList.get(index);

        // Step 4
        getProjectService().deleteProject(project);
        getUi().display("The project has been successfully deleted.\n");
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
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *		   thrown when a user puts an incorrect option index.
     *
     */
    public void createSubSystem() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("List of all projects:");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
        getUi().display(parsedProjectList);

        getUi().display("List of all subsystems:");
        List<SubSystem> subSystemList = getProjectService().getAllSubSystems();
        String parsedSubSystemList = Parser.parseSubSystemList(subSystemList);
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
            throw new ModelException("This is an invalid input");
        }

        getUi().display("The subsystem has been successfully created.\n");
    }

}
