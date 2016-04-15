package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.*;
import Model.BugReport.SearchMethod.SearchOnAssigned;
import Model.BugReport.SearchMethod.SearchOnDescription;
import Model.BugReport.SearchMethod.SearchOnFiled;
import Model.BugReport.SearchMethod.SearchOnTitle;
import Model.Milestone.Milestone;
import Model.Milestone.TargetMilestone;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tom on 9/03/16.
 */
public class BugReportServiceTest extends BugReportInitializaton {

    @Before
    public void initialization() throws ReportErrorToUserException {
        super.initialization();
        developerAssignmentService.assignDeveloperToBugReport(project1.getLeadRole().getDeveloper(), dev3, bugReport1);
    }

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
        BugReport report = bugReportService.getOneBugReport(bugReport2.getId(), bugReport2.getCreator());
        assertEquals(bugReport2, report);
    }

    @Test(expected = ReportErrorToUserException.class)
    public void getBugReportTest_InValid() throws ReportErrorToUserException {
        bugReportService.getOneBugReport(new BugReportID(), issuer1);
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
        assertEquals(3, bugReportService.search(a, issuer1).size());
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
        assertEquals(3, bugReportService.search(a, issuer1).size());
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

    @Test
    public void createCommentTest() throws ReportErrorToUserException {
        Comment comment1 = bugReportService.createComment("Test Comment", issuer1, bugReport1);
        Comment comment2 = bugReportService.createComment("Test Comment on Comment", issuer2, comment1);
        assertTrue(comment1.getComments().contains(comment2));
    }

    @Test
    public void setTargetMilestoneTest_Valid() throws ReportErrorToUserException {
        TargetMilestone milestone = new TargetMilestone("M10");
        bugReportService.setTargetMilestone(bugReport1, milestone);
        assertEquals(milestone, bugReport1.getTargetMilestone());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void setTargetMilestoneTest_InValid() throws ReportErrorToUserException {
        projectService.setNewSubSystemMilestone(subSystem1B, new Milestone("M1"));
        TargetMilestone milestone = new TargetMilestone("M0");
        bugReportService.setTargetMilestone(bugReport1, milestone);
    }

    @Test
    public void setProcedureBugTest() throws ReportErrorToUserException {
        bugReportService.setProcedureBug(bugReport1, "Proc bug Test");
        assertEquals("Proc bug Test", bugReport1.getProcedureBug());
    }

    @Test
    public void setStackTraceTest() throws ReportErrorToUserException {
        bugReportService.setStackTrace(bugReport1, "StackTrace Test");
        assertEquals("StackTrace Test", bugReport1.getStackTrace());
    }

    @Test
    public void setErrorMessageTest() throws ReportErrorToUserException {
        bugReportService.setErrorMessage(bugReport1, "Error message test");
        assertEquals("Error message test", bugReport1.getErrorMessage());
    }

    @Test
    public void addDependencyTest_Valid() throws ReportErrorToUserException {
        bugReportService.addDependency(bugReport1, bugReport2);
        assertTrue(bugReport1.getDependencies().contains(bugReport2));
    }

    @Test(expected = ReportErrorToUserException.class)
    public void addDependencyTest_Invalid() throws ReportErrorToUserException {
        bugReportService.addDependency(bugReport1, bugReport3);
    }

    @Test
    public void createTestTest_Valid() throws ReportErrorToUserException {
        Model.BugReport.Test test = bugReportService.createTest("Test", dev3, bugReport1);
        assertTrue(bugReport1.getTests().contains(test));
    }

    @Test(expected = ReportErrorToUserException.class)
    public void createTestTest_InValid() throws ReportErrorToUserException {
        Model.BugReport.Test test = bugReportService.createTest("Test", dev1, bugReport1);
        assertTrue(bugReport1.getTests().contains(test));
    }

    @Test
    public void createPatchTest_Valid() throws ReportErrorToUserException {
        Model.BugReport.Test test = bugReportService.createTest("Test", dev3, bugReport1);
        Patch patch = bugReportService.createPatch("Patch", dev4, bugReport1);
        assertTrue(bugReport1.getPatches().contains(patch));
    }

    @Test(expected = ReportErrorToUserException.class)
    public void createPatchTest_InValid() throws ReportErrorToUserException {
        Patch patch = bugReportService.createPatch("Test", dev1, bugReport1);
        assertTrue(bugReport1.getPatches().contains(patch));
    }
}
