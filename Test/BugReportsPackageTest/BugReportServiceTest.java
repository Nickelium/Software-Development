package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.BugReport.BugReportService;
import Model.BugReport.Search;
import Model.BugReport.SearchOnAssigned;
import Model.BugReport.SearchOnDescription;
import Model.BugReport.SearchOnFiled;
import Model.BugReport.SearchOnTitle;

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
    public void getBugReportAssignedToUserTest() throws ModelException{
    	Search a = new SearchOnAssigned(dev1);
        assertEquals(1, bugReportService.search(a).size());
        Search b = new SearchOnAssigned(dev2);
        assertEquals(2, bugReportService.search(b).size());
    }

    @Test
    public void getBugReportsFiledByUserTest() throws ModelException{
    	Search a = new SearchOnFiled(issuer1);
        assertEquals(2, bugReportService.search(a).size());
        Search b = new SearchOnFiled(issuer2);
        assertEquals(1, bugReportService.search(b).size());
    }

    @Test
    public void getBugReportsWithTitleContainingTest_Valid() throws ModelException{
    	Search a = new SearchOnTitle("Bug");
        assertEquals(3, bugReportService.search(a).size());
        Search b = new SearchOnTitle("Bug1");
        assertEquals(1, bugReportService.search(b).size());
    }

    @Test (expected = ModelException.class)
    public void getBugReportswithTitleContainingTest_Invalid_EmptyString() throws ModelException {
    	Search a = new SearchOnTitle("");
        bugReportService.search(a);
    }

    @Test (expected = ModelException.class)
    public void getBugReportswithTitleContainingTest_Invalid_WhiteSpace() throws ModelException {
    	Search a = new SearchOnTitle(" ");
        bugReportService.search(a);
    }

    @Test
    public void getBugReportsWithDescriptionContainingTest_Valid() throws ModelException {
    	Search a = new SearchOnDescription("Bug");
        assertEquals(3, bugReportService.search(a).size());
        Search b = new SearchOnDescription("Des Bug1");
        assertEquals(1, bugReportService.search(b).size());
    }

    @Test (expected = ModelException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_EmptyString() throws ModelException {
    	Search a = new SearchOnDescription("");
        bugReportService.search(a);
    }

    @Test (expected = ModelException.class)
    public void getBugReportswithDescriptionContainingTest_Invalid_WhiteSpace() throws ModelException {
    	Search a = new SearchOnDescription(" ");
        bugReportService.search(a);
    }
}
