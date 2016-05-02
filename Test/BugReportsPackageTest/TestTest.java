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
    private Model.BugReport.Test newTest;

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

    @Test
    public void getLinesTest() throws Exception {
        newTest = bugReportService.createTest("Hi\nLaurens\nDit\nis\neen\ntest.", dev3, bugReport1);
        assertEquals(6, this.newTest.getLines());
    }

    @Test
    public void getLinesTest_Empty_OneLine() throws Exception {
        newTest = bugReportService.createTest("", dev3, bugReport1);
        assertEquals(1, this.newTest.getLines());
    }
}