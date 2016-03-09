package Controller.UserController;

import Controller.Parser;
import Controller.UI;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.User.User;
import Model.User.UserService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 05.03.2016.
 */
public abstract class UserController {

    private UI ui;
    private UserService userService;
    private ProjectService projectService;
    private BugReportService bugReportService;
    private User currentUser;

    protected ArrayList<FunctionWrap> useCases = new ArrayList<FunctionWrap>();

    public UserController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser){
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

    private void setUi(UI ui) {
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

    public UI getUi() {
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

    //region use cases methods

    public void showProject()
    {
    	try
    	{
	    	List<Project> projectList = projectService.getAllProjects();
	    	String parsedProjectList = Parser.parseProjectList(projectList);
	    	getUi().display(parsedProjectList);
	    	
	    	int index = ui.readInt();
	    	Project project = projectList.get(index);
	    	
	    	String projectDetails = Parser.parseDetailedProject(project);
	    	
	    	getUi().display(projectDetails);
    	}
    	catch(IndexOutOfBoundsException e)
    	{
    		 getUi().display(e.getMessage());
             getUi().display("Press 1 to retry.");
             if (getUi().readInt() == 1) showProject();
    	}
    }

    public void exitProgram(){
        getUi().display("Bye!");
        System.exit(1);
    }

    //endregion

}
