package Controller.UserController;

import Controller.IUI;
import Controller.UserController.UseCases.UseCase;
import Controller.UserController.UseCases.UserUseCases.ExitProgram;
import Controller.UserController.UseCases.UserUseCases.ShowProject;
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

    protected ArrayList<UseCase> useCases = new ArrayList<>();

    public UserController(IUI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser){
        setUi(ui);
        setUserService(userService);
        setProjectService(projectService);
        setBugReportService(bugReportService);
        setCurrentUser(currentUser);
        initializeUseCasesUser();
    }

    //region UseCases

    public ArrayList<UseCase> getUseCases() {
        return useCases;
    }

    public void showAllUseCases(){
        int i;
        for (i = 0; i < useCases.size(); i++) {
            String show = i + " : " + useCases.get(i).toString();
            ui.display(show);
        }
        ui.display(i + " : Log Out");
    }

    public UseCase getUseCase(int number) {
        try {
            return getUseCases().get(number);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void initializeUseCasesUser(){
        useCases.add(new ShowProject(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
        useCases.add(new ExitProgram(getUi(), getUserService(), getProjectService(), getBugReportService(), getCurrentUser()));
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
        return this.ui;
    }

    public UserService getUserService() {
        return this.userService;
    }

    public ProjectService getProjectService() {
        return this.projectService;
    }

    public BugReportService getBugReportService() {
        return this.bugReportService;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    //endregion

}
