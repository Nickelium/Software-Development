package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.BugReport.BugReportService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 9/03/16.
 */
public class BugReportServiceTest extends BugReportInitializaton {

    @Test
    public void constructorTest_Valid(){
        BugReportService brs = new BugReportService(projectService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorTest_InValid(){
        BugReportService brs = new BugReportService(null);
    }

    @Test
    public void getAllBugReportsTest(){
        assertEquals(3, bugReportService.getAllBugReports().size());
    }

    @Test
    public void getBugReportTest_Valid() throws ModelException{
        BugReport report = bugReportService.getBugReport(bugReport2.getId());
        assertEquals(bugReport2, report);
    }

    @Test(expected = ModelException.class)
    public void getBugReportTest_InValid() throws ModelException{
        bugReportService.getBugReport(new BugReportID());
    }

    @Test
    public void getBugReportsForProjectTest(){
        assertEquals(2, bugReportService.getBugReportsForProject(project1).size());
    }

    @Test
    public void getBugReportAssignedToUserTest(){
        assertEquals(2, bugReportService.getBugReportsAssignedToUser(dev2).size());
        assertEquals(1, bugReportService.getBugReportsAssignedToUser(dev1).size());
    }

    @Test
    public void getBugReportsFiledByUserTest(){
        assertEquals(2, bugReportService.getBugReportsFiledByUser(issuer1).size());
        assertEquals(1, bugReportService.getBugReportsFiledByUser(issuer2).size());
    }

    @Test
    public void getBugReportsWithTitleContainingTest_Valid() throws ModelException{
        assertEquals(3, bugReportService.getBugReportsWithTitleContaining("Bug").size());
        assertEquals(1, bugReportService.getBugReportsWithTitleContaining("Bug1").size());
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithTitleContainingTest_Invalid_EmptyString() throws ModelException{
        bugReportService.getBugReportsWithTitleContaining("");
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithTitleContainingTest_Invalid_WhiteSpace() throws ModelException{
        bugReportService.getBugReportsWithTitleContaining(" ");
    }

    @Test
    public void getBugReportsWithDescriptionContainingTest_Valid() throws ModelException{
        assertEquals(3, bugReportService.getBugReportsWithDescriptionContaining("Bug").size());
        assertEquals(1, bugReportService.getBugReportsWithDescriptionContaining("Des Bug1").size());
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_EmptyString() throws ModelException{
        bugReportService.getBugReportsWithDescriptionContaining("");
    }

    @Test(expected = ModelException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_WhiteSpace() throws ModelException{
        bugReportService.getBugReportsWithDescriptionContaining(" ");
    }

    @Test
    public void isValidTitleString_Test(){
        assertEquals(false, bugReportService.isValidTitleString(null));
        assertEquals(false, bugReportService.isValidTitleString(""));
        assertEquals(false, bugReportService.isValidTitleString(" "));
        assertEquals(true, bugReportService.isValidTitleString("Test"));
    }

    @Test
    public void isValidDescriptionString_Test(){
        assertEquals(false, bugReportService.isValidDescription(null));
        assertEquals(false, bugReportService.isValidDescription(""));
        assertEquals(false, bugReportService.isValidDescription(" "));
        assertEquals(true, bugReportService.isValidTitleString("Test"));
    }
}
