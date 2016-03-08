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

    public AdminController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService, User currentUser) {
        super(ui, userService, projectService, bugReportService, currentUser);
        initializeUseCasesAdmin();
    }

    private void initializeUseCasesAdmin() {
        try {
            useCases.add(new FunctionWrap("Create Project", AdminController.class.getMethod("createProject")));
            useCases.add(new FunctionWrap("Update Project", AdminController.class.getMethod("updateProject")));
            useCases.add(new FunctionWrap("Delete Project", AdminController.class.getMethod("deleteProject")));
            useCases.add(new FunctionWrap("Create Subsystem", AdminController.class.getMethod("createSubSystem")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createProject() {
    	try
    	{
	        getUi().display("Please enter the project information.");
            getUi().display("Name: ");
	        String name = getUi().readString();
            getUi().display("Description: ");
	        String description = getUi().readString();
	
	        TheDate startingDate = null;

            getUi().display("Starting date (dd/MM/yyyy): ");
	        String stringStartingDate = getUi().readString();
	        startingDate = new TheDate(stringStartingDate);

            getUi().display("Budget estimate: ");
	        double budget = getUi().readDouble();
	
	        List<User> possibleLeadDevelopers = getUserService().getDevelopers();
	        String parsedPossibleLeadDevelopers = Parser.parseUserList(possibleLeadDevelopers);
            getUi().display("Choose a lead developer for this project: ");
            getUi().display(parsedPossibleLeadDevelopers);
	        int index = getUi().readInt();
	        Developer leadDev = (Developer) possibleLeadDevelopers.get(index);
	        Lead leadRole = new Lead(leadDev);
	        
	        Project project = getProjectService().createProject(name, description, startingDate, budget, leadRole);
            getUi().display("Your project has been successfully created!");
            getUi().display(project.toString());
        } 
    	catch (ModelException e) 
    	{
            getUi().errorDisplay(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) createProject();
        }
    }

    public void updateProject() {
    	try
    	{
            getUi().display("Select a project you want to update: ");
	        List<Project> projectList = getProjectService().getAllProjects();
	        String parsedProjectList = Parser.parseProjectList(projectList);
            getUi().display(parsedProjectList);
	
	        int index = getUi().readInt();
	        Project project = projectList.get(index);

            getUi().display("Please enter new values.");
            getUi().display("Name (current value: " + project.getName() + "): ");
	        String name = getUi().readString();
            getUi().display("Description (current value: " + project.getDescription() + "): ");
	        String description = getUi().readString();
	        TheDate startingDate = null;

            getUi().display("Starting date (current value: " + project.getStartingDate() + "): ");
	        String stringStartingDate = getUi().readString();
	        startingDate = new TheDate(stringStartingDate);

            getUi().display("Budget estimate (current value: " + project.getBudget() + "): ");
	        double budget = getUi().readDouble();
	
	        project.setName(name);
	        project.setDescription(description);
	        project.setStartingDate(startingDate);
	        project.setBudget(budget);

            getUi().display("The project has been successfully updated.");
            getUi().display(project.toString());
        
        } 
    	catch (ModelException e) 
    	{
            getUi().display(e.getMessage());
            getUi().display("Enter 1 if you want to retry.");
            if (getUi().readInt() == 1) updateProject();
        }
    }

    public void deleteProject() 
    {
        getUi().display("Select a project you want to delete: ");
        List<Project> projectList = getProjectService().getAllProjects();
        String parsedProjectList = Parser.parseProjectList(projectList);
        getUi().display(parsedProjectList);

        int index = getUi().readInt();
        Project project = projectList.get(index);

        getProjectService().deleteProject(project);
        getUi().display("The project has been successfully deleted.");
    }

    public void createSubSystem() 
    {
    	try
    	{
            getUi().display("List of all projects:");
	        List<Project> projectList = getProjectService().getAllProjects();
	        String parsedProjectList = Parser.parseProjectList(projectList);
            getUi().display(parsedProjectList);

            getUi().display("List of all subsystems:");
	        List<SubSystem> subSystemList = getProjectService().getAllSubSystems();
	        String parsedSubSystemList = Parser.parseSubSystemList(subSystemList);
            getUi().display(parsedSubSystemList);

            getUi().display("Project or subsystem (P/S) : ");
	        String input = getUi().readString();
          
            int index;
            if (input.equalsIgnoreCase("p")) 
            {
                getUi().display("Choose a project: ");
                index = getUi().readInt();
                Project project = projectList.get(index);
                getUi().display("Please enter the subsystem information.");
                getUi().display("Name:");
                String name = getUi().readString();
                getUi().display("Description:");
                String description = getUi().readString();
                getProjectService().createSubsystem(name, description, project);

            } 
            else if (input.equalsIgnoreCase("s")) 
            {
                getUi().display("Choose a subsystem: ");
                index = getUi().readInt();
                SubSystem subSystem = subSystemList.get(index);
                getUi().display("Please enter the subsystem information.");
                getUi().display("Name:");
                String name = getUi().readString();
                getUi().display("Description:");
                String description = getUi().readString();
                getProjectService().createSubsystem(name, description, subSystem);
             }
            else
            {
               throw new ModelException("This is an invalid input");
            }
        } 
    	catch (ModelException e) 
    	{
            getUi().display(e.getMessage());
            getUi().display("Press 1 to retry.");
            if (getUi().readInt() == 1) createSubSystem();
        }
    }

}
