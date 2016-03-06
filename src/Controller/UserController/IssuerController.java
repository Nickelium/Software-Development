package Controller.UserController;

import Controller.Parser;
import Controller.UI;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.User.UserService;

import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public class IssuerController extends UserController{

    public IssuerController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService){
        super(ui, userService, projectService, bugReportService);
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
        // select a project
        ui.display("Select a project:");
        List<Project> projectList = projectService.getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
        ui.display(parsedProjectList);
        int indexP = ui.readInt();
        Project project = projectList.get(indexP);

        // select a subsystem
        ui.display("Select a subsystem:");
        List<SubSystem> subsystemList = project.getAllSubSystems();
        String subSystemsOfProject = Parser.parseSubSystemList(subsystemList);
        ui.display(subSystemsOfProject);
        int indexS = ui.readInt();
        SubSystem subSystem = subsystemList.get(indexS);

        // ask information for the bugreport
        ui.display("Please enter the bug report information.");
    }

    public void selectBugReport(){

    }

    public void inspectBugReport(){

    }

    public void createComment(){

    }

}
