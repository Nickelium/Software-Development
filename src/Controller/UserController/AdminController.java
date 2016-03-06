package Controller.UserController;

import Controller.Parser;
import Controller.UI;
import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.Project.Project;
import Model.Project.ProjectService;
import Model.Project.SubSystem;
import Model.Project.TheDate;
import Model.Roles.Lead;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.List;

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
            useCases.add(new FunctionWrap("Create Project", AdminController.class.getMethod("createProject")));
            useCases.add(new FunctionWrap("Update Project", AdminController.class.getMethod("updateProject")));
            useCases.add(new FunctionWrap("Delete Project", AdminController.class.getMethod("deleteProject")));
            useCases.add(new FunctionWrap("Create Subsystem", AdminController.class.getMethod("createSubSystem")));

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void createProject()
    {
    	ui.display("Please enter the project information.");
    	ui.display("Name: ");
    	String name = ui.readString();
    	ui.display("Description: ");
    	String description = ui.readString();

		TheDate startingDate = null;
    	while(true) {
			try {
				ui.display("Starting date (dd/MM/yyyy): ");
				String stringStartingDate = ui.readString();
				startingDate = new TheDate(stringStartingDate);
				break;
			} catch (ModelException e1) {
				//invalid date or dateformat
				ui.errorDisplay(e1.getMessage());
				ui.display("Please use the given format (dd/MM/yyyy)");
			}
		}
    	ui.display("Budget estimate: ");
    	double budget = ui.readDouble();
    	
    	List<User> possibleLeadDevelopers = userService.getDevelopers();
       	String parsedPossibleLeadDevelopers = Parser.parseUserList(possibleLeadDevelopers);
		ui.display("Choose a lead developer for this project: ");
       	ui.display(parsedPossibleLeadDevelopers);
       	int index = ui.readInt();
       	Developer leadDev = (Developer) possibleLeadDevelopers.get(index);
    	Lead leadRole = new Lead(leadDev);
    	
    	try {
			Project project = projectService.createProject(name, description, startingDate, budget, leadRole);
			ui.display("Your project has been successfully created!");
			ui.display(project.toString());
		} catch (ModelException e) {
			ui.errorDisplay(e.getMessage());
			ui.display("Enter 1 if you want to retry.");
			if (ui.readInt() == 1) createProject();
		}
    }
    
    public void updateProject()
    {
		ui.display("Select a project you want to update: ");
    	List<Project> projectList = projectService.getAllProjects();
    	String parsedProjectList = Parser.parseProjectList(projectList);
    	ui.display(parsedProjectList);
    	
    	int index = ui.readInt();
    	Project project = projectList.get(index);

    	ui.display("Enter new values.");
    	ui.display("Name (current value: "+ project.getName() + "): ");
    	String name = ui.readString();
    	ui.display("Description (current value: "+ project.getDescription() +"): ");
    	String description = ui.readString();
    	ui.display("Starting date (current value: "+ project.getStartingDate() + "): ");
    	String stringStartingDate = ui.readString();
    	TheDate startingDate = null;
    	
		try 
		{
			startingDate = new TheDate(stringStartingDate);
		} 
		catch (ModelException e1) 
		{
			//invalid date or dateformat
			e1.printStackTrace();
			System.exit(1);
		}
    	
    	ui.display("Budget estimate (current value: "+ project.getBudget() + "): ");
    	double budget = ui.readDouble();
    	
    	try 
    	{
    		project.setName(name);
        	project.setDescription(description);
			project.setStartingDate(startingDate);
			project.setBudget(budget);

			ui.display("The project has been successfully updated.");
			ui.display(project.toString());
		} 
    	catch (ModelException e) 
    	{
			ui.display(e.getMessage());
			ui.display("Enter 1 if you want to retry.");
			if (ui.readInt() == 1) updateProject();
		}

    }

    public void deleteProject()
    {
		ui.display("Select a project you want to delete: ");
    	List<Project> projectList = projectService.getAllProjects();
    	String parsedProjectList = Parser.parseProjectList(projectList);
    	ui.display(parsedProjectList);
    	
    	int index = ui.readInt();
    	Project project = projectList.get(index);
    	
    	projectService.deleteProject(project);
		ui.display("The project has been successfully deleted.");
    }

    public void createSubSystem()
    {
		ui.display("List of all projects:");
    	List<Project> projectList = projectService.getAllProjects();
    	String parsedProjectList = Parser.parseProjectList(projectList);
    	ui.display(parsedProjectList);

		ui.display("List of all subsystems:");
    	List<SubSystem> subSystemList = projectService.getAllSubSystems();
    	String parsedSubSystemList = Parser.parseSubSystemList(subSystemList);
    	ui.display(parsedSubSystemList);

		while(true) {
			ui.display("Project or subsystem (P/S) : ");
			String input = ui.readString();

			try {
				int index;
				if (input.equalsIgnoreCase("p")) {
					ui.display("Choose a project: ");
					index = ui.readInt();
					Project project = projectList.get(index);
					//creator voor subsytem of niet ?
					String name = ui.readString();
					String description = ui.readString();
					SubSystem newSubSystem = new SubSystem(name, description);
					project.addSubSystem(newSubSystem);
					break;
				} else if (input.equalsIgnoreCase("s")) {
					ui.display("Choose a subsystem: ");
					index = ui.readInt();
					SubSystem subSystem = subSystemList.get(index);
					//creator voor subsytem of niet ?
					String name = ui.readString();
					String description = ui.readString();
					SubSystem newSubSystem = new SubSystem(name, description);
					subSystem.addSubSystem(newSubSystem);
					break;

				}
			} catch (ModelException e) {
				ui.display(e.getMessage());
				ui.display("Press 1 to retry.");
				if(ui.readInt() != 1) break;
			}
		}
    }

}
