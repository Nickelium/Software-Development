package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 14/04/16.
 */
public class TestTest extends BugReportInitializaton {
    private Model.BugReport.Test test;

    @Before
    public void initialization() throws ReportErrorToUserException {
        super.initialization();
        developerAssignmentService.assignDeveloperToBugReport(project1.getLeadRole().getDeveloper(), dev3, bugReport1);
        test = bugReportService.createTest("Test", dev3, bugReport1);
    }

    @Test
    public void getValueTest() {
        assertEquals("Test", this.test.getValue());
    }

    @Test
    public void toStringTest() {
        assertEquals("Test", this.test.toString());
    }
}