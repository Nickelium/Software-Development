package UseCaseTests.UserControllerTest;

import Controller.UserController.AdminController;
import Controller.UserController.UserController;
import Model.Memento.Caretaker;
import UseCaseTests.AdminControllerTest.AdminControllerInit;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Karina on 07.05.2016.
 */
public class ShowPerformanceMetrics extends AdminControllerInit {

    @Test
    public void successfullyShowedPerformanceMetrics() throws Exception {
        String[] simulatedUserInput = {
                "0"
        };
        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
        TestUI ui = new TestUI(input);

        UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
        adminController.getUseCase(UseCase.SHOW_PERFORMANCE_METRICS.value).run();
    }

    @Test
    public void unsuccessfullyShowedPerformanceMetrics_invalidInput() throws Exception {
        try {
            String[] simulatedUserInput = {
                    "2"
            };
            ArrayList<String> input = new ArrayList<>(Arrays.asList(simulatedUserInput));
            TestUI ui = new TestUI(input);

            UserController adminController = new AdminController(ui, userService, projectService, bugReportService, performanceMetricsService, new Caretaker(projectService, mailboxService), currentUser);
            adminController.getUseCase(UseCase.SHOW_PERFORMANCE_METRICS.value).run();
        } catch (IndexOutOfBoundsException e) {
            assert e.getMessage().equals("Index: 2, Size: 2");
        }
    }

}
