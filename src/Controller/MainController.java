package Controller;


import Controller.InitializerPkg.AssignmentInitializer;
import Controller.InitializerPkg.DemoInitializer;
import Controller.InitializerPkg.IInitializer;
import Controller.UserController.AdminController;
import Controller.UserController.DeveloperController;
import Controller.UserController.IssuerController;
import Controller.UserController.UseCases.UseCase;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import Model.User.Admin;
import Model.User.Developer;
import Model.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karina on 26.02.2016.
 */
public class MainController {
    private IUI ui;
    private List<IInitializer> initializerList = new ArrayList<>();
    private IInitializer initializer;
    private User currentUser;

    public MainController() {
        ui = new UI();
        initializerList.add(new AssignmentInitializer());
        initializerList.add(new DemoInitializer());
    }

    public MainController(IUI ui, IInitializer initializer) {
        this.ui = ui;
        initializerList.add(initializer);
    }

    public void run() throws Exception {
        startMsg();
        while (true) {
            while (true) {
                try {
                    ui.display("Choose an initialization:");
                    for (int i = 0; i < initializerList.size(); i++) {
                        ui.display(i + ": " + initializerList.get(i).getName());
                    }
                    int chosenInit = ui.readInt();
                    initializer = initializerList.get(chosenInit);

                    break;
                } catch (ReportErrorToUserException e) {
                    ui.display(e.getMessage());
                    ui.display("Enter 1 if you want to retry. Any other key if not.");
                    if (ui.readInt() != 1) {
                        ui.display("Bye!");
                        System.exit(0);
                    }
                }
            }

            LoginController loginController = new LoginController(ui, initializer.getUserService());
            while (true) {
                try {
                    this.currentUser = loginController.login();
                    break;
                } catch (ReportErrorToUserException e) {
                    ui.display(e.getMessage());
                    ui.display("Enter 1 if you want to retry. Any other key if not.");
                    if (ui.readInt() != 1) {
                        ui.display("Bye!");
                        System.exit(0);
                    }
                }
            }

            // choose controller
            UserController userController;
            if (currentUser instanceof Admin) {
                userController = new AdminController(ui, initializer.getUserService(), initializer.getProjectService(), initializer.getBugReportService(), initializer.getPerformanceMetricsService(), initializer.getCaretaker(), currentUser);
            } else if (currentUser instanceof Developer) {
                userController = new DeveloperController(ui, initializer.getUserService(), initializer.getProjectService(), initializer.getBugReportService(), initializer.getPerformanceMetricsService(), currentUser, initializer.getDeveloperAssignmentService(), initializer.getTagAssignmentService(), initializer.getMailboxService());
            } else {
                userController = new IssuerController(ui, initializer.getUserService(), initializer.getProjectService(), initializer.getBugReportService(), initializer.getPerformanceMetricsService(), initializer.getTagAssignmentService(), initializer.getMailboxService(), currentUser);
            }
   
            boolean logingOut = false;
            do {
                logingOut = chooseUseCase(userController);
            } while (!logingOut);
        }
    }

    private void startMsg() {
        String msg = "Welcome to the bug tracking system!";
        ui.display(msg);
    }

    private boolean chooseUseCase(UserController userController) throws Exception 
    {
        int chosenUseCase;
        // Step 1 in most Use Cases

        while (true) {
            try {
                ui.display("Choose one of the actions below:");
                userController.showAllUseCases();
                chosenUseCase = ui.readInt();
                if (chosenUseCase < userController.getUseCases().size() + 1) break;
                else {
                    ui.display("This is not a valid input! Please retry:");
                }
            } catch (ReportErrorToUserException e) {
                ui.errorDisplay(e.getMessage());
                continue;
            }
        }
        
        while (true) 
        {
            try
            {
                // call function
                UseCase useCase = userController.getUseCase(chosenUseCase);
                if (useCase == null) 
                {
                    return true;
                } 
                else
                {
                	if(useCase.changeSystem())
                		initializer.getCaretaker().saveState(useCase);
                    useCase.run();
                }
                break;
            } 
            catch (ReportErrorToUserException | IndexOutOfBoundsException e) 
            {
                ui.errorDisplay(e.getMessage());
                ui.display("Enter 1 if you want to retry.");
                if (!ui.readString().equals("1")) break;
            }
        }
        return false;

    }
}

