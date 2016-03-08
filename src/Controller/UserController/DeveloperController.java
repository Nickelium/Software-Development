package Controller.UserController;

import Controller.UI;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class DeveloperController extends IssuerController {

    public DeveloperController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser){
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesDeveloper();
    }

    private void initializeUseCasesDeveloper(){
        try {
            useCases.add(new FunctionWrap("Assign To Project", DeveloperController.class.getMethod("assignToProject")));
            useCases.add(new FunctionWrap("Assign To Bug Report", DeveloperController.class.getMethod("assignToBugReport")));
            useCases.add(new FunctionWrap("Update Bug Report", DeveloperController.class.getMethod("updateBugReport")));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void assignToProject(){

    }

    public void assignToBugReport(){

    }

    public void updateBugReport(){

    }
}
