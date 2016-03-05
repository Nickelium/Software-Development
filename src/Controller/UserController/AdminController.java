package Controller.UserController;

import Controller.UI;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.UserService;

/**
 * Created by Karina on 05.03.2016.
 */
public class AdminController extends UserController {

    public AdminController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService){
        super(ui, userService, projectService, bugReportService);
        initializeUseCasesAdmin();
    }

    private void initializeUseCasesAdmin(){
        try {
            useCases.add(new FunctionWrap("Create Project", AdminController.class.getMethod("showProject")));
            useCases.add(new FunctionWrap("Update Project", AdminController.class.getMethod("showProject")));
            useCases.add(new FunctionWrap("Delete Project", AdminController.class.getMethod("showProject")));
            useCases.add(new FunctionWrap("Create Subsystem", AdminController.class.getMethod("showProject")));

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
