package Controller.UserController;

import Controller.UI;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.UserService;

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

    }

    public void selectBugReport(){

    }

    public void inspectBugReport(){

    }

    public void createComment(){

    }

}
