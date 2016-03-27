package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.UserUseCases.ExitProgram;
import Controller.UserController.UseCases.UserUseCases.ShowProject;
import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;

/**
 * Created by Karina on 05.03.2016.
 */
public abstract class UserController {

    private IUI ui;
    private UserService userService;
    private ProjectService projectService;
    private BugReportService bugReportService;
    private User currentUser;

    protected ArrayList<FunctionWrap> useCases = new ArrayList<FunctionWrap>();

    public UserController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser){
        setUi(ui);
        setUserService(userService);
        setProjectService(projectService);
        setBugReportService(bugReportService);
        setCurrentUser(currentUser);
        initializeUseCasesUser();
    }

    //region UseCases

    public ArrayList<FunctionWrap> getUseCases(){
        return useCases;
    }

    public void showAllUseCases(){
        for(int i=0; i<useCases.size(); i++){
            String show = i + " : " + useCases.get(i).getName();
            ui.display(show);
        }
    }

    public void callUseCase(int number) throws ReportErrorToUserException, IndexOutOfBoundsException {
        getUseCases().get(number).getUseCase().run();
    }

    private void initializeUseCasesUser(){
        useCases.add(new FunctionWrap("Show Project", new ShowProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
        useCases.add(new FunctionWrap("Exit Program", new ExitProgram(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser())));
    }

    //endregion

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

    //endregion

}
