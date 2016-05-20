package UseCaseTests.AdminControllerTest;

import Controller.IUI;
import Controller.InitializerPkg.AssignmentInitializer;
import Controller.MainController;
import Model.BugReport.BugReport;
import UseCaseTests.DeveloperControllerTest.DeveloperControllerInit;
import UseCaseTests.IssuerControllerTest.IssuerControllerInit;
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
        		"0",
                "3",
                "0",
                String.valueOf(IssuerControllerInit.IssuerUseCase.CREATE_BUGREPORT.value),
                "1",
                "2",
                "Test",
                "Testing",
                ".",
                ".",
                ".",
                "1",
                "2",
                "-1",
                String.valueOf(DeveloperControllerInit.DeveloperUseCase.LOGOUT.value),
                "0",
                "1",
                "0",
                String.valueOf(AdminUseCase.UNDO.value),
                "0",
        };

        ArrayList<String> input = new ArrayList<>(Arrays.asList(simulateUserInput));
        IUI ui = new TestUI(input);
        AssignmentInitializer initializer = new AssignmentInitializer();
        MainController controller = new MainController(ui, initializer);
        try 
        {
            controller.run();
        } 
        catch (IndexOutOfBoundsException e)
        {}
        List<BugReport> bugReportList = initializer.getBugReportService().getAllBugReports(initializer.getUserService().getUser("major"));
        assert (!initializer.getBugReportService().getAllBugReports(initializer.getUserService().getUser("major")).stream().anyMatch(x -> x.getTitle().equals("Test")));
    }

}
