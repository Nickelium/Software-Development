package UseCaseTests.AdminControllerTest;

import Controller.IUI;
import Controller.Initializer;
import Controller.MainController;
import Model.BugReport.BugReport;
import UseCaseTests.UseCasesUI.TestUI;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Karina on 14.04.2016.
 */
public class Undo extends AdminControllerInit {

    @Test
    public void undoTest() throws Exception {
        String[] simulateUserInput = {
                "3",
                "0",
                "2",
                "1",
                "2",
                "Test",
                "Testing",
                ".",
                ".",
                ".",
                "1",
                "-1",
                "14",
                "1",
                "0",
                "7",
                "0",
        };

        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulateUserInput));
        IUI ui = new TestUI(input);
        Initializer initializer = new Initializer();
        MainController controller = new MainController(ui, initializer);
        try {
            controller.run();
        } catch (IndexOutOfBoundsException e) {
        }
        List<BugReport> bugReportList = initializer.getBugReportService().getAllBugReports(initializer.getUserService().getUser("major"));
        assert (!initializer.getBugReportService().getAllBugReports(initializer.getUserService().getUser("major")).stream().anyMatch(x -> x.getTitle().equals("Test")));
    }

}
