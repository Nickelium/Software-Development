package Controller.UserController;

import java.util.List;

import Controller.Parser;
import Controller.UI;
import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.User;
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
    public void createProject()
    {
    	ui.display("Project information");
    	ui.display("Name: ");
    	String name = ui.readString();
    	ui.display("Description: ");
    	String description = ui.readString();
    	ui.display("Starting date: ");
    	String startingDate = ui.readString();
    	ui.display("Budget estimate: ");
    	double budget = ui.readDouble();
    	
    	List<User> possibleLeadDevelopers = userService.getDevelopers();
       	String parsedPossibleLeadDevelopers = Parser.parseUserList(possibleLeadDevelopers);
       	ui.display(parsedPossibleLeadDevelopers);
       	int index = ui.readInt();
       	Developer leadDev = (Developer) possibleLeadDevelopers.get(index);
    	Lead leadRole = new Lead(leadDev);
    	
    	try {
			Project project = projectService.createProject(name, description, startingDate, budget, leadRole);
			ui.display(project.toString());
		} catch (ModelException e) {
			
			e.printStackTrace();
		}
    }
    
    public void updateProject()
    {
    	
    }

    


}
