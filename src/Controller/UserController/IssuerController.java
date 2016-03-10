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


    public void createBugReport() throws ModelException, IndexOutOfBoundsException {
        // select a project
        getUi().display("Select a project:");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
        getUi().display(parsedProjectList);
        int indexP = getUi().readInt();
        Project project = projectList.get(indexP);

        // select a subsystem
        getUi().display("Select a subsystem:");
        List<SubSystem> subSystemList = project.getAllSubSystems();
        String subSystemsOfProject = Parser.parseSubSystemList(subSystemList);
        getUi().display(subSystemsOfProject);
        int indexS = getUi().readInt();
        SubSystem subSystem = subSystemList.get(indexS);

        // ask information for the bugreport
        getUi().display("\n");
        getUi().display("Please enter the bug report information.");
        getUi().display("Title:");
        String title = getUi().readString();
        getUi().display("Description:");
        String description = getUi().readString();

        Issuer issuer = (Issuer) getCurrentUser();

        // create bugreport
        BugReport bugReport = getBugReportService().createBugReport(title, description, issuer, subSystem);

        // show all dependencies
        List<BugReport> possibleDependencies = getBugReportService().getBugReportsForProject(project);
        String possibleDependenciesStr = Parser.parseBugReportList(possibleDependencies);
        while (true) {
            getUi().display("\n");
            getUi().display("Select a possible dependency (enter -1 to exit): ");
            getUi().display(possibleDependenciesStr);
            int indexDep = getUi().readInt();
            if (indexDep == -1) break;
            // add dependency
            BugReport newDependency = possibleDependencies.get(indexDep);
            bugReport.addDependency(newDependency);
            getUi().display("Dependency is successfully added!");
            getUi().display(newDependency.toString());
        }

    }

    protected BugReport selectBugReport() throws ModelException, IndexOutOfBoundsException {
        int chosenNumber;
        List<BugReport> bugReportList = null;

        // return for usage in DeveloperController

        getUi().display("Select the preferred search method: ");

        String searchMethods = "";
        searchMethods += "0 :Search for bug reports with a specific string in the title or description\n";
        searchMethods += "1 :Search for bug reports filed by some specific user\n";
        searchMethods += "2 :Search for bug reports assigned to specific user";

        getUi().display(searchMethods);

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
                // Show Results
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
                // Show Results
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
                // Show Results
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));

            } else {
                throw new ModelException("No bug reports found.");
            }
        } else {
            throw new ModelException("Enter a valid number.");
        }

        // Make choice
        getUi().display("Please enter the number of the bug report that you would like to select: ");
        chosenNumber = getUi().readInt();
        return bugReportList.get(chosenNumber);

    }

    public void inspectBugReport() throws ModelException, IndexOutOfBoundsException {
        BugReport bugReport = selectBugReport();
        String bugReportDetails = Parser.parseDetailBugReport(bugReport);
        getUi().display(bugReportDetails);

    }

    public void createComment() throws ModelException, IndexOutOfBoundsException {
        BugReport bugReport = selectBugReport();
        List<Comment> listComment = bugReport.getAllComments();
        if (listComment.size() > 0) {
            getUi().display("List of all comments of this bugreport:");
            String parsedListComment = Parser.parseCommentList(listComment);
            getUi().display(parsedListComment);
        }

        //whaaat
        getUi().display("Create a comment of the bugreport or on one of the comments (B/C) : ");
        String input = getUi().readString();

        if (input.equalsIgnoreCase("b")) {
            getUi().display("Comment (Terminate with a . on a new line):");
            String text = getUi().readMultiline();
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
            getUi().display("Comment (Sluit af met . op nieuwe lijn):");
            String text = getUi().readMultiline();

            Comment newComment = getBugReportService().createComment(text, (Issuer) getCurrentUser(), comm);
            getUi().display("The comment was:\n" + newComment + "\nIt has successfully been created.\n");
        } else {
            throw new ModelException("This is an invalid input");
        }
    }

}
