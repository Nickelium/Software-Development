package UseCaseTests.IssuerControllerTest;

import Controller.UserController.IssuerController;
import Controller.UserController.UserController;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 14.04.2016.
 */
public class UnregisterFromNotification extends IssuerControllerInit {

    @Test
    public void successfullyUnregistered() throws Exception {

        String[] simulatedUserInputRegister = {
                "1",
                "0",
                "1"

        };
        ArrayList<String> inputRegister = new ArrayList<>(Arrays.asList(simulatedUserInputRegister));
        TestUI uiRegister = new TestUI(inputRegister);

        UserController issuerControllerRegister = new IssuerController(uiRegister, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerControllerRegister.getUseCase(7).run();

        String[] simulatedUserInput = {
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
        issuerController.getUseCase(8).run();
    }

    @Test
    public void unsuccessfullyUnregistered_NoRegistrations() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "0"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController issuerController = new IssuerController(ui, userService, projectService, bugReportService, tagAssignmentService, mailboxService, currentUser);
            issuerController.getUseCase(8).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 0, Size: 0");
        }
    }

}
