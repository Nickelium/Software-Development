package Controller;


import Controller.UserController.AdminController;
import Controller.UserController.DeveloperController;
import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import CustomExceptions.ModelException;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.logging.Handler;

/**
 * Created by Karina on 26.02.2016.
 */
public class MainController {
    private IUI ui;
    private IInitializer initializer;
    private User currentUser;

    public MainController() {
        ui = new UI();
        initializer = new Initializer();

    }

    public void run() {
        startMsg();
        LoginController loginController = new LoginController(ui, initializer.getUserService());
        while(true) {
            try {
                this.currentUser = loginController.run();
                break;
            } catch (ModelException e) {
                ui.display(e.getMessage());
                ui.display("Enter 1 if you want to retry. Any other key if not.");
                if (ui.readInt() != 1) break;
            }
        }

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

        chooseUseCase(userController);

    }

    private void startMsg() {
        String msg = "Welcome to the bug tracking system!";
        ui.display(msg);
    }

    public void chooseUseCase(UserController userController){
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

        }
    }


}

