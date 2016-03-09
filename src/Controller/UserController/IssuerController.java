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

    public BugReport selectBugReport() {
        try {

            // show all dependencies of this bug report
            // return for usage in DeveloperController
            return null;

        } catch (/*ModelException | */IndexOutOfBoundsException e) {
            getUi().errorDisplay(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) createBugReport();
        }

    }

    public void inspectBugReport(){

    }

    public void createComment(){

    }

}
