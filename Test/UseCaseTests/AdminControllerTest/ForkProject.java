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
public class ForkProject  extends AdminControllerInit {

    @Test
    public void successfulForkedProject() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "1.1",
                "11/11/2016",
                "200.0",
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);


        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(3).run();
    }

    @Test
    public void unsuccessfullyForkProject_invalidVersionID() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "0.3",
                    "11/11/2016",
                    "200.0",
                    "1"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(3).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The version cannot be lower than or equal to the previous one!");
        }

    }

    @Test
    public void unsuccessfullyForkProject_invalidStartingDate() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "1.1",
                    "11/11/2014",
                    "200.0",
                    "1"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(3).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The date is before the creation date.");
        }

    }

    @Test
    public void unsuccessfullyForkProject_invalidBudget() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "1.1",
                    "11/11/2016",
                    "-200.0",
                    "1"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(3).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The budget cannot be negative.");
        }

    }

    @Test
    public void unsuccessfullyForkProject_invalidLead() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "1.1",
                    "11/11/2016",
                    "200.0",
                    "5"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(3).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 5, Size: 2");
        }

    }

}
