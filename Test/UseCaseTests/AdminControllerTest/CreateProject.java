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
public class CreateProject extends AdminControllerInit {

    @Test
    public void successfullyCreatedProject() throws Exception{
        String[] simulatedUserInput = {
                "Project Test",
                "Project Test Discription",
                ".",
                "11/12/2016",
                "200.0",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(2).run();
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfulCreateProject_invalidDate() throws Exception{
        String[] simulatedUserInput = {
                "Project Test",
                "Project Test Discription",
                ".",
                "11/12/2014",
                "200.0",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(2).run();
    }


    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfulCreateProject_invalidBudget() throws Exception{
        String[] simulatedUserInput = {
                "Project Test",
                "Project Test Discription",
                ".",
                "11/12/2016",
                "-200.0",
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(2).run();

    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfulCreateProject_invalidLead() throws Exception{
        String[] simulatedUserInput = {
                "Project Test",
                "Project Test Discription",
                ".",
                "11/12/2016",
                "200.0",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(2).run();
    }

}
