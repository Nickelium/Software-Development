package Controller.UserController;

import Controller.Parser;
import Controller.UI;
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
 * Created by Karina on 05.03.2016.
 */
public class IssuerController extends UserController{

    public IssuerController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser){
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesIssuer();
    }

    private void initializeUseCasesIssuer(){
        try {
            useCases.add(new FunctionWrap("Create Bug Report", IssuerController.class.getMethod("createBugReport")));
            useCases.add(new FunctionWrap("Select Bug Report", IssuerController.class.getMethod("selectBugReport")));
            useCases.add(new FunctionWrap("Inspect Bug Report", IssuerController.class.getMethod("inspectBugReport")));
            useCases.add(new FunctionWrap("Create Comment", IssuerController.class.getMethod("createComment")));

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createBugReport(){
        try {
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
            while(true){
                getUi().display("\n");
                getUi().display("Select a possible dependency (enter -1 to exit): ");
                getUi().display(possibleDependenciesStr);
                int indexDep = getUi().readInt();
                if (indexDep ==-1) break;
                // add dependency
                BugReport newDependency = possibleDependencies.get(indexDep);
                bugReport.addDependency(newDependency);
                getUi().display("Dependency is successfully added!");
                getUi().display(newDependency.toString());
            }

            // show all dependencies of this bug report
        }catch(ModelException | IndexOutOfBoundsException e){
            getUi().errorDisplay(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) createBugReport();
        }

    }

    public BugReport selectBugReport() throws ModelException{

        try {

            int chosenNumber;
            List<BugReport> bugReportList;

            // return for usage in DeveloperController

            getUi().display("Select the preferred search method: ");
            getUi().display(Parser.parseSearchMethods());

            int methodIndex = getUi().readInt();

            if(methodIndex == 0){
                // Search for bug reports with a specific string in the title or description
                getUi().display("Please enter a search string matching the title or description of the desired bug report.");
                String query = getUi().readString();

                // Make List with possible bug reports
                List<BugReport> list1 = getBugReportService().getBugReportsWithDescriptionContaining(query);
                List<BugReport> list2 = getBugReportService().getBugReportsWithTitleContaining(query);

                // Combine both lists
                bugReportList = list1;
                for(BugReport b : list2){
                    bugReportList.add(b);
                }

                // Show Results
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));

            }

            else if (methodIndex == 1){
                // Search for bug reports filed by some specific user
                getUi().display("Please enter the username of the user that filed the desired bug report: ");
                String userName = getUi().readString();

                User user = getUserService().getUser(userName);
                bugReportList = getBugReportService().getBugReportsFiledByUser(user);

                // Show Results
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));
            }

            else {
                // Search for bug reports assigned to specific user
                getUi().display("Please enter the username of the user that the bug reports are assigned to: ");
                String userName = getUi().readString();

                User user = getUserService().getUser(userName);
                bugReportList = getBugReportService().getBugReportsAssignedToUser(user);

                // Show Results
                getUi().display("The search result for your query is: ");
                getUi().display(Parser.parseBugReportList(bugReportList));
            }

            // Make choice
            getUi().display("Please enter the number of the bug report that you would like to select: ");
            chosenNumber = getUi().readInt();

            return bugReportList.get(chosenNumber);


        } catch (/*ModelException | */IndexOutOfBoundsException e) {
            getUi().errorDisplay(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) createBugReport();
        }

        return null;

    }

    public void inspectBugReport(){

    }

    public void createComment(){

    }

}
