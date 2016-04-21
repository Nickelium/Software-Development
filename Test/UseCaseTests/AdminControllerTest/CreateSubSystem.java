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
public class CreateSubSystem extends AdminControllerInit {


    @Test
    public void successfullyCreatedSubSystem_forProject() throws Exception{
        String[] simulatedUserInput = {
                "P",
                "0",
                "Sub System Name Test",
                "Sub System Description Test",
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(6).run();
    }

    @Test
    public void unsuccessfulCreateSubSystem_forProject_wrongSelection() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "P",
                    "10"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(6).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 10, Size: 2");
        }
    }

    @Test
    public void successfullyCreatedSubSystem_forSubSystem() throws Exception{
        String[] simulatedUserInput = {
                "S",
                "0",
                "Sub System Name Test",
                "Sub System Description Test",
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(6).run();
    }

    @Test
    public void unsuccessfulCreateSubSystem_forSubSystem_wrongSelection() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "S",
                    "10"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(6).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 10, Size: 8");
        }

    }

    @Test
    public void unsuccessfulCreateSubSystem_wrongSelection() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "X"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(6).run();
        } catch (ReportErrorToUserException e) {
            e.getMessage().equals("This is an invalid input");
        }
    }
}
