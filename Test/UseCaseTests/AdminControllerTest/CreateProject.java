package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import Model.Memento.Caretaker;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Karina on 10.03.2016.
 */
public class CreateProject extends AdminControllerInit {

    @Test
    public void successfullyCreatedProject() throws Exception {

        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test"));

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

        assert projectService.getAllProjects().stream().filter(x -> x.getName().equals("Project Test")).collect(Collectors.toList()).size() == 1;
    }

    @Test
    public void unsuccessfulCreateProject_invalidDate() throws Exception {
        try {
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
        } catch (ReportErrorToUserException e) {
            assert (e.getMessage().equals("The date is before the creation date."));
            assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test"));
        }
    }

    @Test
    public void unsuccessfulCreateProject_invalidBudget() throws Exception {
        try {
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
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The budget cannot be negative.");
            assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test"));
        }

    }

    @Test
    public void unsuccessfulCreateProject_invalidLead() throws Exception {
        try {
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
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 5, Size: 2");
            assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test"));
        }
    }

}
