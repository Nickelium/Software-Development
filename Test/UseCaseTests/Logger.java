package UseCaseTests;

import Controller.LoginController;
import CustomExceptions.ReportErrorToUserException;
import Model.User.User;
import Model.User.UserService;
import UseCaseTests.UseCasesUI.TestUI;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 14.04.2016.
 */
public class Logger {

    public static User adminLogger(UserService userService) throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "1",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.login();
        return loginController.getCurrentUser();
    }

    public static User issuerLogger(UserService userService) throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "2",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.login();
        return loginController.getCurrentUser();
    }

    public static User developerLogger(UserService userService) throws ReportErrorToUserException {
        String[] simulatedUserInput = {
                "3",
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);
        LoginController loginController = new LoginController(ui, userService);
        loginController.login();
        return loginController.getCurrentUser();
    }

}
