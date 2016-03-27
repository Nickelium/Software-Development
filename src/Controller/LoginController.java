package Controller;

import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import Model.User.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 04.03.2016.
 */
public class LoginController {


    private UserService userService;
    private IUI ui;

    private User currentUser;

    public LoginController(IUI ui, UserService userService) {
        this.userService = userService;
        this.ui = ui;
    }

	/**
	 *
	 * Method to log a user in into BugTrap.
	 *
	 * 1. The user indicates if he wants to log in as an administrator, issuer or
	 * developer.
	 * 2. The system shows an overview of the users of the selected category.
	 * 3. The user selects one of the shown users4.
	 * 4. The system greets the user.
	 *
	 * @return 	user
	 * 			the user that will be logged in
	 * @throws ReportErrorToUserException in case the method encounters invalid input.
     */
	public User login() throws ReportErrorToUserException {
	        loginMessage();
			// Step 1
	        int userType = ui.readInt();
	
	        List<User> users = new ArrayList<User>();

			// Step 2
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
	               throw new ReportErrorToUserException("This in an invalid input");
	        }      
	
	        ui.display("Choose an user to log in as:");
	        String parsedTextUsers = Formatter.formatUserList(users);
	        ui.display(parsedTextUsers);
	
	        // Step 3
	        int selectedUserIndex = ui.readInt();
			if(selectedUserIndex >= users.size()) throw new ReportErrorToUserException("This user does not exist!");
	        User user = users.get(selectedUserIndex);
	        setCurrentUser(user);

			// Step 4
	        welcomeUserMessage(user);

			return user;
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
