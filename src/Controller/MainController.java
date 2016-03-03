package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.User.User;
import Model.User.UserService;

/**
 * Created by Karina on 26.02.2016.
 */
public class MainController 
{
	private UI ui;
	private IInitializer initializer;
	private Parser parser;
	private UserService userService;
	
	public MainController()
	{
		ui = new UI();
		initializer = new Initializer();
		initializer.init();
	}
	
	public void run()
	{
		startMsg();
	
		loginMessage();
		int mode = ui.readInt();
		
		List<User> users = new ArrayList<User>();
		
		try
		{
			switch (mode)
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
					throw new Exception("Invalid input");
			}
		}
		catch(Exception e)
		{
			ui.errorDisplay(e.getMessage());
		}
		
		String parsedTextUsers = parser.parseUserList(users);
		ui.display(parsedTextUsers);
		//select User
		int selectedUserIndex = ui.readInt();
		//Waar beheren van get index uit lijst ? In controller of service ?
		User user = users.get(selectedUserIndex);
		
		//welcome User
		welcomeUser(user);
	
		while(true)
		{
			//use cases
			switch (mode)
			{
				case 1://Admin
					//propose Project usecases
					break;
				case 2://Issuer
					//propose bugreport usecases
				case 3://Developer
					//propose developer usecases
	
			}
		}
	}
	
	private void startMsg()
	{
		String msg = "Welcome! \n";
		ui.display(msg);
	}
	
	private void loginMessage()
	{
		String str = "Login as 1) administrator 2) issuer 3) developer\n";
		ui.display(str);
	}
	
	private void welcomeUser(User user)
	{
		String userText = user.toString();
		String str = "Welcome " + userText;
		ui.display(str);
	}
    // talks to the UI
    // converts to strings
}
