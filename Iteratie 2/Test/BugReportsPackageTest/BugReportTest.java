package BugReportsPackageTest;

import CustomExceptions.ReportErrorToUserException;
import Model.BugReport.BugReport;
import Model.BugReport.BugReportID;
import Model.BugReport.Comment;
import Model.BugReport.TagTypes.Assigned;
import Model.BugReport.TagTypes.Closed;
import Model.BugReport.TagTypes.New;
import Model.Milestone.TargetMilestone;
import Model.Project.TheDate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tom on 28/02/16.
 */
public class BugReportTest extends BugReportInitializaton {
    private BugReport extraBugReport1;

    @Before
    public void initialization() throws ReportErrorToUserException {
        super.initialization();
        extraBugReport1 = bugReportService.createBugReport("Test bug", "Des Test Bug", issuer1, subSystem1B, BugReport.PUBLIC);
    }

    @Test
    public void getIdTest() {
        assertFalse(this.bugReport1.getId() == null);
        assertFalse(this.bugReport1.getId().equals(""));
        assertEquals(this.bugReport1.getId(), this.bugReport1.getId());
        assertFalse(this.bugReport1.getId().equals(new BugReportID()));
    }

    @Test
    public void getTitleTest() {
        assertEquals("Bug1", this.bugReport1.getTitle());
        assertFalse(this.bugReport1.getTitle().equals("Bug2"));
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("Des Bug1", this.bugReport1.getDescription());
        assertFalse(this.bugReport1.getDescription().equals("Bug2"));
    }

    @Test
    public void getCreationDateTest() throws ReportErrorToUserException {
        assertEquals(TheDate.TheDateNow(), this.bugReport2.getCreationDate());
    }

    @Test
    public void getCreatorTest() throws ReportErrorToUserException {
        assertEquals(this.issuer1, this.bugReport1.getCreator());
        assertFalse(this.bugReport1.getCreator().equals(this.issuer2));
    }

    @Test
    public void getTagTest() throws ReportErrorToUserException {
        assertEquals(New.class, this.bugReport1.getTag().getClass());
        assertEquals(Closed.class, this.bugReport2.getTag().getClass());
        assertEquals(Assigned.class, this.bugReport3.getTag().getClass());
    }

    @Test
    public void getAssigneesTest() throws ReportErrorToUserException {
        assertEquals(0, this.bugReport1.getAssignees().size());
        assertEquals(1, this.bugReport2.getAssignees().size());
        assertEquals(2, this.bugReport3.getAssignees().size());
    }

    @Test
    public void getCommentsTest() throws ReportErrorToUserException {
        assertEquals(1, this.bugReport1.getComments().size());
        this.bugReportService.createComment("TestComment", this.issuer1, this.bugReport1);
        assertEquals(2, this.bugReport1.getComments().size());
    }

    @Test
    public void getAllCommentsTest() throws ReportErrorToUserException {
        Comment comment = this.bugReportService.createComment("Test 3 comment", this.issuer1, this.bugReport1);
        Comment subComment = this.bugReportService.createComment("Test 4 comment", this.issuer2, comment);
        assert this.bugReport1.getAllComments().contains(subComment);
        assert this.bugReport1.getAllComments().contains(comment);
    }

    @Test
    public void getDependencies() throws ReportErrorToUserException {
        assertEquals(0, this.bugReport1.getDependencies().size());
        bugReportService.addDependency(bugReport1, this.bugReport2);
        assertEquals(1, this.bugReport1.getDependencies().size());
    }

    @Test
    public void getAndSetProcedureBugTest() throws ReportErrorToUserException {
        bugReportService.setProcedureBug(extraBugReport1, "This is a test procedure bug");
        assertEquals("This is a test procedure bug", this.extraBugReport1.getProcedureBug());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void setProcedureBugTest_InValid() throws ReportErrorToUserException {
        bugReportService.setProcedureBug(extraBugReport1, null);
    }

    @Test
    public void getAndSetStackTraceTest() throws ReportErrorToUserException {
        bugReportService.setStackTrace(extraBugReport1, "This is a test stacktrace");
        assertEquals("This is a test stacktrace", this.extraBugReport1.getStackTrace());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void setStackTrace_InValid() throws ReportErrorToUserException {
        bugReportService.setStackTrace(extraBugReport1, null);
    }

    @Test
    public void getAndSetErrorMessageTest() throws ReportErrorToUserException {
        this.bugReportService.setErrorMessage(extraBugReport1, "This is a test error message");
        assertEquals("This is a test error message", this.extraBugReport1.getErrorMessage());
    }

    @Test(expected = ReportErrorToUserException.class)
    public void setErrorMessage_InValid() throws ReportErrorToUserException {
        bugReportService.setErrorMessage(extraBugReport1, null);
    }

    @Test
    public void getSolutionScoreTestWhenSet() {
        assertEquals(1, bugReport2.getSolutionScore());
    }

    @Test
    public void getSolutionScoreTestDefaultValue() {
        assertEquals(0, bugReport1.getSolutionScore());
    }

    @Test
    public void getSelectedPatchTestDefaultValue() {
        assertEquals(null, bugReport1.getSelectedPatch());
    }

    @Test
    public void getSelectedPatchTestWhenSet() {
        assertEquals(bugReport2.getPatches().get(0), bugReport2.getSelectedPatch());
    }

    @Test
    public void getTargetMilestoneTestDefaultValue() {
        assertEquals(null, extraBugReport1.getTargetMilestone());
    }

    @Test
    public void getAndSetTargetMilestoneTest() throws ReportErrorToUserException {
        TargetMilestone milestone = new TargetMilestone("M10");
        bugReportService.setTargetMilestone(extraBugReport1, milestone);
        assertEquals(milestone, extraBugReport1.getTargetMilestone());
    }

    @Test
    public void isPublicTest_False() {
        assertEquals(false, this.bugReport1.isPublic());
    }

    @Test
    public void getPublicTest_True() {
        assertEquals(true, this.bugReport2.isPublic());
    }

    @Test
    public void isValidTitleTest() {
        assertFalse(this.bugReport1.isValidTitle(null));
        assertFalse(this.bugReport1.isValidTitle(""));
        assertTrue(this.bugReport1.isValidTitle("Test"));
    }

    @Test
    public void isValidDescriptionTest() {
        assertFalse(this.bugReport1.isValidTitle(null));
        assertFalse(this.bugReport1.isValidTitle(""));
        assertTrue(this.bugReport1.isValidTitle("Test"));
    }

    @Test
    public void addDependencyTest() throws ReportErrorToUserException {
        this.bugReportService.addDependency(bugReport1, bugReport2);
        assertTrue(this.bugReport1.getDependencies().contains(this.bugReport2));
    }

    @Test
    public void equalsTest() {
        assertTrue(this.bugReport2.equals(this.bugReport2));
        assertFalse(this.bugReport1.equals(this.bugReport2));
        assertFalse(this.bugReport1.equals(this.comment1));
    }
}

