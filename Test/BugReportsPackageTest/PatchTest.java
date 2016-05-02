package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.Patch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 14/04/16.
 */
public class PatchTest extends BugReportInitializaton {
    private Patch patch;
    private Patch newPatch;

    @Before
    public void initialization() throws ReportErrorToUserException {
        super.initialization();
        developerAssignmentService.assignDeveloperToBugReport(project1.getLeadRole().getDeveloper(), dev3, bugReport1);
        bugReportService.createTest("Test", dev3, bugReport1);
        this.patch = bugReportService.createPatch("Patch", dev4, bugReport1);
    }

    @Test
    public void getValueTest() {
        assertEquals("Patch", this.patch.getValue());
    }

    @Test
    public void toStringTest() {
        assertEquals("Patch", this.patch.toString());
    }

    @Test
    public void getLinesTest() throws Exception {
        newPatch = bugReportService.createPatch("Hi\nLaurens\nDit\nis\neen\npatch.", dev4, bugReport1);
        assertEquals(6, this.newPatch.getLines());
    }

    @Test
    public void getLinesTest_Empty_OneLine() throws Exception {
        newPatch = bugReportService.createPatch("", dev4, bugReport1);
        assertEquals(1, this.newPatch.getLines());
    }
}
