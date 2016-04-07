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

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.getUseCase(3).run();
    }


    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyForkProject_invalidVersionID() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "0.3",
                "11/11/2016",
                "200.0",
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.getUseCase(3).run();

    }

    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyForkProject_invalidStartingDate() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "1.1",
                "11/11/2014",
                "200.0",
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.getUseCase(3).run();

    }


    @Test(expected = ReportErrorToUserException.class)
    public void unsuccessfullyForkProject_invalidBudget() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "1.1",
                "11/11/2016",
                "-200.0",
                "1"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.getUseCase(3).run();

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unsuccessfullyForkProject_invalidLead() throws Exception{
        String[] simulatedUserInput = {
                "0",
                "1.1",
                "11/11/2016",
                "200.0",
                "5"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.getUseCase(3).run();

    }

}
