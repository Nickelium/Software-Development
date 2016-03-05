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
	
	public MainController()
	{
		ui = new UI();
		initializer = new Initializer();
		initializer.init();
	}
	
	public void run()
	{
		startMsg();
		LoginController loginController = new LoginController(initializer.getUserService(), ui);
		loginController.run();

	}
	
	private void startMsg()
	{
		String msg = "Welcome! \n";
		ui.display(msg);
	}
	

    // talks to the UI
    // converts to strings
}
