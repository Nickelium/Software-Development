package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
import Model.Memento.Caretaker;
import Model.Project.TheDate;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Karina on 10.03.2016.
 */
public class UpdateProject extends AdminControllerInit{

    @Test
    public void successfullyUpdatedProject() throws Exception{

        assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test Name"));

        String[] simulatedUserInput = {
                "0",
                "Project Test Name",
                "Project Test Description",
                "11/12/2017",
                "200.0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(AdminUseCase.UPDATE_PROJECT.value).run();

        TheDate newDate = new TheDate(11, 12, 2017);
        assert projectService.getAllProjects().stream().filter(x -> x.getName().equals("Project Test Name") && x.getDescription().equals("Project Test Description")
                && x.getStartingDate().equals(newDate) && x.getBudget() == 200.0).collect(Collectors.toList()).size() == 1;

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

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.UPDATE_PROJECT.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The date is before the creation date.");
            assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test Name") && x.getDescription().equals("Project Test Description"));
        }

    }

    @Test
    public void unsuccessfulUpdatedProject_invalidBudget() throws Exception{
        try {
            String[] simulatedUserInput = {
                    "0",
                    "Project Test Name",
                    "Project Test Description",
                    "11/12/2017",
                    "-200.0"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(AdminUseCase.UPDATE_PROJECT.value).run();
        } catch (ReportErrorToUserException e) {
            assert e.getMessage().equals("The budget cannot be negative.");

            TheDate newDate = new TheDate(11, 12, 2017);
            assert projectService.getAllProjects().stream().noneMatch(x -> x.getName().equals("Project Test Name") && x.getDescription().equals("Project Test Description")
                    && x.getStartingDate().equals(newDate));
        }


    }
}
