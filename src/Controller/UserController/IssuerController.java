package Controller.UserController;

import Controller.IUI;
import Controller.Parser;
import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportService;
import Model.BugReport.Comment;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.Issuer;
import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public class IssuerController extends UserController {

    public IssuerController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesIssuer();
    }

    private void initializeUseCasesIssuer() {
        try {
            useCases.add(new FunctionWrap("Create Bug Report", IssuerController.class.getMethod("createBugReport")));
            useCases.add(new FunctionWrap("Inspect Bug Report", IssuerController.class.getMethod("inspectBugReport")));
            useCases.add(new FunctionWrap("Create Comment", IssuerController.class.getMethod("createComment")));

        } catch (Exception e) {
            e.printStackTrace();
        }
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
     */
    public void createBugReport() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        getUi().display("Select a project:");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
        getUi().display(parsedProjectList);

        // Step 3
        int indexP = getUi().readInt();
        Project project = projectList.get(indexP);

        // Step 4
        getUi().display("Select a subsystem:");
        List<SubSystem> subSystemList = project.getAllSubSystems();
        String subSystemsOfProject = Parser.parseSubSystemList(subSystemList);
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
        String possibleDependenciesStr = Parser.parseBugReportList(possibleDependencies);
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

    /**
     *
     * Lets an Issuer or Developer select a bug report. Bug reports are listed after
     * the execution of a search command.
     *
     * 1. The system shows a list of possible searching modes:
     *     Search for bug reports with a specic string in the title or description
     *     Search for bug reports led by some specic user
     *     Search for bug reports assigned to specic user
     *
     * 2. The issuer selects a searching mode and provides the required search
     *    parameters.
     * 3. The system shows an ordered list of bug reports that matched the
     *    search query.
     * 4. The issuer selects a bug report from the ordered list.
     *
     * @return BugReport the bug report that has been selected by the user.
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *
     */
    protected BugReport selectBugReport() throws ModelException, IndexOutOfBoundsException {
        int chosenNumber;
        List<BugReport> bugReportList = null;

        // Step 1
        getUi().display("Select the preferred search method: ");

        String searchMethods = "";
        searchMethods += "0 :Search for bug reports with a specific string in the title or description\n";
        searchMethods += "1 :Search for bug reports filed by some specific user\n";
        searchMethods += "2 :Search for bug reports assigned to specific user";

        getUi().display(searchMethods);

        // Step 2
        int methodIndex = getUi().readInt();

        if (methodIndex == 0) {
            // Search for bug reports with a specific string in the title or description
            getUi().display("Please enter a search string matching the title or description of the desired bug report.");
            String query = getUi().readString();

            // Make List with possible bug reports
            List<BugReport> list1 = getBugReportService().getBugReportsWithDescriptionContaining(query);
            List<BugReport> list2 = getBugReportService().getBugReportsWithTitleContaining(query);

            // Combine both lists
            bugReportList = new ArrayList<BugReport>(list1);
            for (BugReport b : list2) {
                bugReportList.add(b);
            }

            if (bugReportList.size() > 0) {
                // Step 3
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));

            } else {
                throw new ModelException("No bug reports found.");
            }


        } else if (methodIndex == 1) {

            // Search for bug reports filed by some specific user
            getUi().display("Please enter the username of the user that filed the desired bug report: ");
            String userName = getUi().readString();

            User user = getUserService().getUser(userName);
            bugReportList = getBugReportService().getBugReportsFiledByUser(user);
            if (bugReportList.size() > 0) {
                // Step 3
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));
            } else {
                throw new ModelException("No bug reports found.");

            }
        } else if (methodIndex == 2) {
            // Search for bug reports assigned to specific user
            getUi().display("Please enter the username of the user that the bug reports are assigned to: ");
            String userName = getUi().readString();

            User user = getUserService().getUser(userName);
            bugReportList = getBugReportService().getBugReportsAssignedToUser(user);

            if (bugReportList.size() > 0) {
                // Step 3
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));

            } else {
                throw new ModelException("No bug reports found.");
            }
        } else {
            throw new ModelException("Enter a valid number.");
        }

        // Step 4
        getUi().display("Please enter the number of the bug report that you would like to select: ");
        chosenNumber = getUi().readInt();
        return bugReportList.get(chosenNumber);

    }

    /**
     *
     * Lets an Issuer inspect a bug report.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a detailed overview of the selected bug report and
     * all its comments
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     */
    public void inspectBugReport() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        BugReport bugReport = selectBugReport();

        // Step 3
        String bugReportDetails = Parser.parseDetailBugReport(bugReport);
        getUi().display(bugReportDetails);

    }

    /**
     *
     * Lets an Issuer create a comment onto a bug report or an other comment.
     *
     * 2. Include use case Select Bug Report.
     * 3. The system shows a list of all comments of the selected bug report.
     * 4. The issuer indicates if he wants to comment directly on the bug report
     *    or on some other comment.
     * 5. The system asks for the text of the comment.
     * 6. The issuer writes his comment.
     * 7. The system adds the comment to the selected use case.
     *
     *
     * @throws ModelException
     *          in case that the method encounters invalid input
     * @throws IndexOutOfBoundsException
     *
     */
    public void createComment() throws ModelException, IndexOutOfBoundsException {

        // Step 2
        BugReport bugReport = selectBugReport();

        // Step 3
        List<Comment> listComment = bugReport.getAllComments();
        if (listComment.size() > 0) {
            getUi().display("List of all comments of this bugreport:");
            String parsedListComment = Parser.parseCommentList(listComment);
            getUi().display(parsedListComment);
        }

        // Step 4
        getUi().display("Create a comment of the bugreport or on one of the comments (B/C) : ");
        String input = getUi().readString();

        if (input.equalsIgnoreCase("b")) {

            // Step 5
            getUi().display("Comment (Terminate with a . on a new line):");

            // Step 6
            String text = getUi().readMultiline();

            // Step 7
            Comment newComment = getBugReportService().createComment(text, (Issuer) getCurrentUser(), bugReport);
            getUi().display("The comment was:\n"
                    + "-------------------------\n"
                    + newComment
                    + "\n-------------------------\n"
                    + "It has successfully been created.\n");

        } else if (input.equalsIgnoreCase("c")) {
            int index;
            getUi().display("Choose a comment from one of above: ");
            index = getUi().readInt();
            Comment comm = listComment.get(index);

            // Step 5
            getUi().display("Comment (Sluit af met . op nieuwe lijn):");

            // Step 6
            String text = getUi().readMultiline();

            // Step 7
            Comment newComment = getBugReportService().createComment(text, (Issuer) getCurrentUser(), comm);
            getUi().display("The comment was:\n" + newComment + "\nIt has successfully been created.\n");
        } else {
            throw new ModelException("This is an invalid input");
        }
    }

}
