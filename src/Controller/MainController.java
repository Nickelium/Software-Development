package Controller;


import Controller.UserController.AdminController;
import Controller.UserController.DeveloperController;
import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ModelException;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.User;

/**
 * Created by Karina on 26.02.2016.
 */
public class MainController {
    private UI ui;
    private IInitializer initializer;
    private User currentUser;

    public MainController() {
        ui = new UI();
        initializer = new Initializer();
        
        try {
			initializer.init();
		} catch (ModelException e) {
			e.printStackTrace();
		}

    }

    public void run() {
        startMsg();
        LoginController loginController = new LoginController(ui, initializer.getUserService());
        this.currentUser = loginController.run();
        //create Controller


        // choose controller
        UserController userController;
        if (currentUser instanceof Admin)
        {
            userController = new AdminController(ui, initializer.getUserService(), initializer.getProjectService(), initializer.getBugReportService(), currentUser);
        }
        else if (currentUser instanceof Developer)
        {
            userController = new DeveloperController(ui, initializer.getUserService(), initializer.getProjectService(), initializer.getBugReportService(), currentUser, initializer.getDeveloperAssignmentService(), initializer.getTagAssignmentService());
        }
        else
        {
            userController = new IssuerController(ui, initializer.getUserService(), initializer.getProjectService(), initializer.getBugReportService(), currentUser);
        }

        while (true) {
            // give all possibilities
            ui.display("Choose one of the actions below:");
            userController.showAllUseCases();

            int chosenUseCase;
            // ask to choose
            chosenUseCase = ui.readInt();

            // call function
            try {
                userController.callUseCase(chosenUseCase);
            } catch (IndexOutOfBoundsException e) {
                ui.errorDisplay("This is an invalid choice.");
                continue;
            }

            //repeat
        }

    }

    private void startMsg() {
        String msg = "Welcome to the bug tracking system!";
        ui.display(msg);
    }


}

