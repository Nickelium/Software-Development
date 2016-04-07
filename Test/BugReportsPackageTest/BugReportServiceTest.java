package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.BugReport.SearchMethod.SearchOnAssigned;
import Model.BugReport.SearchMethod.SearchOnDescription;
import Model.BugReport.SearchMethod.SearchOnFiled;
import Model.BugReport.SearchMethod.SearchOnTitle;
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
    public void getBugReportTest_Valid() throws ReportErrorToUserException {
        BugReport report = bugReportService.getBugReport(bugReport2.getId(), bugReport2.getCreator());
        assertEquals(bugReport2, report);
    }

    @Test(expected = ReportErrorToUserException.class)
    public void getBugReportTest_InValid() throws ReportErrorToUserException {
        bugReportService.getBugReport(new BugReportID(), issuer1);
    }

    @Test
    public void getBugReportsForProjectTest(){
        assertEquals(2, bugReportService.getBugReportsForProject(project1).size());
    }

    @Test
    public void getBugReportAssignedToUserTest() throws ReportErrorToUserException {
    	Search a = new SearchOnAssigned(dev1);
        assertEquals(1, bugReportService.search(a, dev1).size());
        Search b = new SearchOnAssigned(dev2);
        assertEquals(2, bugReportService.search(b, dev2).size());
    }

    @Test
    public void getBugReportsFiledByUserTest() throws ReportErrorToUserException {
    	Search a = new SearchOnFiled(issuer1);
        assertEquals(2, bugReportService.search(a, issuer1).size());
        Search b = new SearchOnFiled(issuer2);
        assertEquals(1, bugReportService.search(b, issuer2).size());
    }

    @Test
    public void getBugReportsWithTitleContainingTest_Valid() throws ReportErrorToUserException {
    	Search a = new SearchOnTitle("Bug");
        assertEquals(2, bugReportService.search(a, issuer1).size());
        Search b = new SearchOnTitle("Bug1");
        assertEquals(1, bugReportService.search(b, issuer1).size());
    }

    @Test (expected = ReportErrorToUserException.class)
    public void getBugReportswithTitleContainingTest_Invalid_EmptyString() throws ReportErrorToUserException {
    	Search a = new SearchOnTitle("");
        bugReportService.search(a, issuer1);
    }

    @Test (expected = ReportErrorToUserException.class)
    public void getBugReportswithTitleContainingTest_Invalid_WhiteSpace() throws ReportErrorToUserException {
    	Search a = new SearchOnTitle(" ");
        bugReportService.search(a, issuer1);
    }

    @Test
    public void getBugReportsWithDescriptionContainingTest_Valid() throws ReportErrorToUserException {
    	Search a = new SearchOnDescription("Bug");
        assertEquals(2, bugReportService.search(a, issuer1).size());
        Search b = new SearchOnDescription("Des Bug1");
        assertEquals(1, bugReportService.search(b, issuer1).size());
    }

    @Test (expected = ReportErrorToUserException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_EmptyString() throws ReportErrorToUserException {
    	Search a = new SearchOnDescription("");
        bugReportService.search(a, issuer1);
    }

    @Test (expected = ReportErrorToUserException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_WhiteSpace() throws ReportErrorToUserException {
    	Search a = new SearchOnDescription(" ");
        bugReportService.search(a, issuer1);
    }
}
