package UseCaseTests.AdminControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import CustomExceptions.ReportErrorToUserException;
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

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.callUseCase(4);
    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfulUpdatedProject_invalidDate() throws Exception{
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

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.callUseCase(4);

    }


    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfulUpdatedProject_invalidBudget() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "Project Test Name",
                "Project Test Description",
                "11/12/2016",
                "-200.0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.callUseCase(4);
    }
}
