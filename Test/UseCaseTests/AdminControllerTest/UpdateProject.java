package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import Model.Memento.Caretaker;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 10.03.2016.
 */
public class UpdateProject extends AdminControllerInit{

    @Test
    public void successfullyUpdatedProject() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "Project Test Name",
                "Project Test Description",
                "11/12/2016",
                "200.0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(4).run();
    }

    @Test
    public void unsuccessfulUpdatedProject_invalidDate() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Project Test Name",
                    "Project Test Description",
                    "11/12/2014",
                    "200.0",
                    "0"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(4).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The date is before the creation date.");
        }

    }

    @Test
    public void unsuccessfulUpdatedProject_invalidBudget() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Project Test Name",
                    "Project Test Description",
                    "11/12/2016",
                    "-200.0"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(4).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The budget cannot be negative.");
        }
    }
}
