package BugReportsPackageTest;

import CustomExceptions.ModelException;
import Model.BugReport.BugReportID;
import Model.BugReport.Comment;
import Model.Project.TheDate;
import Model.Tags.TagTypes.Assigned;
import Model.Tags.TagTypes.Closed;
import Model.Tags.TagTypes.New;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tom on 28/02/16.
 */
public class BugReportTest extends BugReportInitializaton {

    @Test
    public void getIdTest() {
        assertFalse(bugReport1.getId() == null);
        assertFalse(bugReport1.getId().equals(""));
        assertEquals(bugReport1.getId(), bugReport1.getId());
        assertFalse(bugReport1.getId().equals(new BugReportID()));
    }

    @Test
    public void getTitleTest() {
        assertEquals("Bug1", bugReport1.getTitle());
        assertFalse(bugReport1.getTitle().equals("Bug2"));
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("Des Bug1", bugReport1.getDescription());
        assertFalse(bugReport1.getDescription().equals("Bug2"));
    }

    @Test
    public void getCreationDateTest() throws ModelException {
        assertEquals(TheDate.TheDateNow(), bugReport2.getCreationDate());
    }

    @Test
    public void getCreatorTest() throws ModelException {
        assertEquals(issuer1, bugReport1.getCreator());
        assertFalse(bugReport1.getCreator().equals(issuer2));
    }

    @Test
    public void getTagTest() throws ModelException {
        assertEquals(New.class, bugReport1.getTag().getClass());
        assertEquals(Closed.class, bugReport2.getTag().getClass());
        assertEquals(Assigned.class, bugReport3.getTag().getClass());
    }

    @Test
    public void getAssigneesTest() throws ModelException {
        assertEquals(0, bugReport1.getAssignees().size());
        assertEquals(1, bugReport2.getAssignees().size());
        assertEquals(2, bugReport3.getAssignees().size());
    }

    @Test
    public void getCommentsTest() throws ModelException {
        assertEquals(1, bugReport1.getComments().size());
        bugReportService.createComment("TestComment", issuer1, bugReport1);
        assertEquals(2, bugReport1.getComments().size());
    }

    @Test
    public void getAllCommentsTest() throws ModelException {
        Comment comment = bugReportService.createComment("Test 3 comment", issuer1, bugReport1);
        Comment subComment = bugReportService.createComment("Test 4 comment", issuer2, comment);
        assert bugReport1.getAllComments().contains(subComment);
        assert bugReport1.getAllComments().contains(comment);
    }

    @Test
    public void getDependencies() throws ModelException {
        assertEquals(0, bugReport1.getDependencies().size());
        bugReport1.addDependency(bugReport2);
        assertEquals(1, bugReport1.getDependencies().size());
    }

    @Test
    public void isValidTitleTest() {
        assertFalse(bugReport1.isValidTitle(null));
        assertFalse(bugReport1.isValidTitle(""));
        assertTrue(bugReport1.isValidTitle("Test"));
    }

    @Test
    public void isValidDescriptionTest() {
        assertFalse(bugReport1.isValidTitle(null));
        assertFalse(bugReport1.isValidTitle(""));
        assertTrue(bugReport1.isValidTitle("Test"));
    }

    @Test
    public void addDependencyTest() {
        bugReport3.addDependency(bugReport2);
        assertTrue(bugReport3.getDependencies().contains(bugReport2));
    }

    @Test
    public void equalsTest() {
        assertTrue(bugReport2.equals(bugReport2));
        assertFalse(bugReport1.equals(bugReport2));
        assertFalse(bugReport1.equals(comment1));
    }
}

