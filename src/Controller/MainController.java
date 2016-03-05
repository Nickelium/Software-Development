package Controller;


import Controller.UserController.AdminController;
import Controller.UserController.DeveloperController;
import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.Issuer;
import Model.User.User;

import java.util.Arrays;
import java.util.stream.Stream;

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
        initializer.init();

    }

    public void run() {
        startMsg();
        LoginController loginController = new LoginController(ui, initializer.getUserService(),initializer.getProjectService(),initializer.getBugReportService());
        //create Controller

        UserController userController = loginController.run();
        this.currentUser = loginController.getCurrentUser();

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

