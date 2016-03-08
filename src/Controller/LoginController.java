package Controller;

import Controller.UserController.AdminController;
import Controller.UserController.DeveloperController;
import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ModelException;
import Model.BugReport.BugReportService;
import Model.Project.ProjectService;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 04.03.2016.
 */
public class LoginController {


    private UserService userService;
    private ProjectService projectService;
    private BugReportService bugReportService;
    private UI ui;

    private User currentUser;

    public LoginController(UI ui, UserService userService, ProjectService projectService, BugReportService bugReportService) {
        this.userService = userService;
        this.projectService = projectService;
        this.bugReportService = bugReportService;
        this.ui = ui;
    }

    public UserController run() 
    {
    	try
    	{
	        loginMessage();
	        int userType = ui.readInt();
	
	        List<User> users = new ArrayList<User>();
	
	        switch (userType)
	        {
	        	case 1://Admin
	        		users = userService.getAdministrators();
	                break;
	            case 2://Issuer
	                users = userService.getIssuers();
	                break;
	            case 3://Developer
	                users = userService.getDevelopers();
	                break;
	            default:
	               throw new ModelException("This in an invalid input");
	        }      
	
	        ui.display("Choose an user to log in as:");
	        String parsedTextUsers = Parser.parseUserList(users);
	        ui.display(parsedTextUsers);
	
	        //select & set User
	        int selectedUserIndex = ui.readInt();
	        User user = users.get(selectedUserIndex);
	        setCurrentUser(user);
	
	        welcomeUserMessage(user);
	
	        // choose controller
	        UserController userController;
	        if (currentUser instanceof Admin) 
	        {
	            userController = new AdminController(ui, this.userService, this.projectService, this.bugReportService, user);
	        } 
	        else if (currentUser instanceof Developer) 
	        {
	            userController = new DeveloperController(ui, this.userService, this.projectService, this.bugReportService, user);
	        }
	        else 
	        {
	            userController = new IssuerController(ui, this.userService, this.projectService, this.bugReportService, user);
	        }
	
	        return userController;
        
    	} 
    	catch (ModelException e) 
    	{
    		ui.errorDisplay(e.getMessage());
            return run();
        }
    }

    private void loginMessage() {
        String str = "You can log in as: 1 = administrator, 2 = issuer, 3 = developer.";
        ui.display(str);
    }

    private void welcomeUserMessage(User user) {
        String userText = user.getFirstName();
        String str = "Welcome " + userText + "!";
        ui.display(str);
    }

    private void setCurrentUser(User user){
        this.currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
    }

}
