package Controller.UserController;

import Controller.Parser;
import Controller.UI;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.User.UserService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public abstract class UserController {

    protected UI ui;
    protected UserService userService;
    protected ProjectService projectService;
    protected BugReportService bugReportService;

    protected ArrayList<FunctionWrap> useCases = new ArrayList<FunctionWrap>();

    public UserController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService){
        setUi(ui);
        setUserService(userService);
        setProjectService(projectService);
        setBugReportService(bugReportService);
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

    public void callUseCase(int number){
        try {
            getUseCases().get(number).getFunction().invoke(this);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
    }

    private void initializeUseCasesUser(){
        try {
            useCases.add(new FunctionWrap("Show Project", UserController.class.getMethod("showProject")));
            useCases.add(new FunctionWrap("Exit Program", UserController.class.getMethod("exitProgram")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //endregion

    //region Getters & setters

    public UI getUi() {
        return ui;
    }

    public void setUi(UI ui) {
        this.ui = ui;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setBugReportService(BugReportService bugReportService) {
        this.bugReportService = bugReportService;
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

    //endregion

    //region use cases methods

    public void showProject()
    {
    	List<Project> projectList = projectService.getAllProjects();
    	String parsedProjectList = Parser.parseProjectList(projectList);
    	ui.display(parsedProjectList);
    	
    	int index = ui.readInt();
    	Project project = projectList.get(index);
    	
    	String projectDetails = Parser.parseDetailedProject(project);
    	
    	ui.display(projectDetails);
    }

    public void exitProgram(){
        ui.display("Bye!");
        System.exit(1);
    }

    //endregion

}
