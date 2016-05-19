package Controller.UserController.UseCases;

import Controller.IUI;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

/**
 * Abstract class representing a use case object
 */
public abstract class UseCase {

    private IUI ui;
    private UserService userService;
    private ProjectService projectService;
    private BugReportService bugReportService;
    private User currentUser;

    protected boolean changeSystem;

    public UseCase(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        setUi(ui);
        setUserService(userService);
        setProjectService(projectService);
        setBugReportService(bugReportService);
        setCurrentUser(currentUser);
    }

    //region Getters & setters

    private void setUi(IUI ui) {
        this.ui = ui;
    }

    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    private void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    private void setBugReportService(BugReportService bugReportService) {
        this.bugReportService = bugReportService;
    }

    private void setCurrentUser(User currentUser){
        this.currentUser = currentUser;
    }

    public IUI getUi() {
        return ui;
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

    public User getCurrentUser(){
        return currentUser;
    }
    
    public boolean changeSystem()
    {
    	return changeSystem;
    }

    //endregion

    public abstract void run() throws ReportErrorToUserException,IndexOutOfBoundsException;
    
    @Override
    public abstract String toString();

}
