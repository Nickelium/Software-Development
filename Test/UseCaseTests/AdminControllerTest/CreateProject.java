package UseCaseTests.AdminControllerTest;

import Controller.IUI;
import Controller.LoginController;
import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import CustomExceptions.ModelException;
import UseCaseTests.InitializerTest;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

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

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.callUseCase(2);
    }

    @Test(expected = ModelException.class)
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

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.callUseCase(2);
    }


    @Test(expected = ModelException.class)
    public void unsuccessfulCreateProject_invalidBudget() throws Exception{
        String[] simulatedUserInput = {
                "Project Test",
                "Project Test Discription",
                ".",
                "11/12/2016",
                "-200.0",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui,userService,projectService,bugReportService,loginController.getCurrentUser());
        adminController.callUseCase(2);
    }

}
