package Controller;

import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.UserService;

/**
 * Created by Karina on 26.02.2016.
 */
public class InitializerController {

    private UserService userService;
    private ProjectService projectService;
    private BugReportService bugReportService;

    public InitializerController(){
        initilize();
    }

    private void initilize(){
        this.userService = new UserService();
        // refactor ProjectService class first
        //this.projectService = new ProjectService();
        this.bugReportService = new BugReportService();

        // init users
        getUserService().addAdmin("Frederick","Curtis","curt", "Sam");
        getUserService().addIssuer("John","","Doctor","doc");
        getUserService().addIssuer("Charles","Arnold","Berg","charlie");
        getUserService().addDeveloper("Joseph","","Mays","major");
        getUserService().addDeveloper("Maria","","Carney","maria");

        // creat projects
        // TODO zal er een creator zijn voor Projects? zo ja, hier gebruiken dan

        // create bugreports
        // TODO na het aanmaken van de projects

    }

    public UserService getUserService() {
        return userService;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public BugReportService getBugReportService() {
        return bugReportService;
    }



}
